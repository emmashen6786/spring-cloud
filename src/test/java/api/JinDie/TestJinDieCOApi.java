package api.JinDie;

import com.jayway.restassured.response.Response;
import com.test.utils.IdCardGenerator;
import com.test.utils.JindieCODataProvider;
import com.test.utils.RandomDataPortal;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.testng.AssertJUnit.assertEquals;


public class TestJinDieCOApi {
    protected static String token;
    protected static String personalRealName = RandomDataPortal.randomName();
    protected static String personalSsn = '"' + new IdCardGenerator().appoint(22, 50) + '"';
    protected static String personalCellphone = '"' + new RandomDataPortal().randomPhone() + '"';
    //
    protected static String companyName = '"' + personalRealName + "有限公司\"";
    //    protected static String companySsn = "91510100" + RandomDataPortal.getRandomNineNo() + "G";  //18位
//    protected static String companyCardType = "UNIFORM_CREDIT_CODE";
    protected static String externalId = RandomDataPortal.getRandomSerialNo();

    protected static String companySsn = "91510" + RandomDataPortal.getRandomNineNo() + "G";  //15位
    protected static String orgCodeCert = RandomDataPortal.getRandomNineNo(); //9位
    protected static String companyCardType = "BUSINESS_LICENSE";


    protected static Integer appAmount = 230000;
//    protected static Integer maturity = 3;   //借款期限 3个月
//    protected static String paymentMethod = "PAY_AT_THE_END";   //还款方式-每月还息，到期一次性还本

    protected static Integer maturity = 12;   //借款期限 6/9/12个月
    protected static String paymentMethod = "AMORTIZATION";   //还款方式-等额本息


//    protected static String personalRealName = "黄泰德";
//    protected static String personalCellphone = "13333262009";
//    protected static String personalSsn = "530381199111188712";
//    protected static String companyName = '\"' + "黄泰德有限公司" + '\"';
//    protected static String companySsn = "91510100743648217G";  //18位

    @BeforeTest(enabled = true, groups = {"JinDieBigNormalTestGroup", "PLPrecheckAbnomalTestGroup"})

    public void getCOChannelToken() {
        String data = "{" +
                "\"appId\":\"e2a6d72470ec4a278e38fea1f6280859\"," +
                "\"appSecret\":\"@8btFneA87vee9$g\"" +
                "}";
        Response resp = given().
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/auth/query/channel-token");
        String res = resp.asString();
        Integer status = resp.getStatusCode();
        String token = from(res).get("token").toString();
        if (resp.getStatusCode() == 200) {
            this.token = token;
            System.out.println("获取授权Token成功：" + token);
        } else {
            System.out.println("获取授权Token失败，错误返回是：" + res);
        }
    }

    //  企业订单预检查
    @Test(enabled = true, groups = {"JinDieBigNormalTestGroup"})
//    @Test(enabled = false)
    public void testCOPrecheck() {
        String data = "{" +
                "\"userSubjectType\":\"BUSINESS\"," +
                "\"companyName\":" + companyName + "," +
                "\"companySsn\":\"" + companySsn + "\"," +
                "\"personalRealName\":\"" + personalRealName + "\"," +
                "\"personalCellphone\":" + personalCellphone + "," +
                "\"personalSsn\":" + personalSsn + "," +
                "\"personalCardType\":\"SSN\"" +
                "}";

        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/kingdeeco/precheck");
        String res = resp.asString();
        String result = from(res).get("result").toString();

        assertEquals(200, resp.getStatusCode());
        Assert.assertEquals(result, "true");
    }


    //    大额推送预订单
    @Test(enabled = true, dataProvider = "bigPreApplicationInfo", dataProviderClass = JindieCODataProvider.class, groups = {"JinDieBigNormalTestGroup"}, dependsOnMethods = {"testCOPrecheck"})
    public void testBigPreApplicationInfo(String getBigPreApplicationRequestBody) {
        String data = getBigPreApplicationRequestBody;
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v5/channels/kingdeeco/pre-application-info");
        String res = resp.asString();
        System.out.println(res);
        String result = from(res).get("success").toString();

        assertEquals(200, resp.getStatusCode());
        Assert.assertEquals(result, "true");
    }

    //    获取授权页面链接
    @Test(enabled = true, dependsOnMethods = {"testBigPreApplicationInfo"})
    public void testGetCOAuthPage() {
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                params("externalId", externalId).
                when().
                get("https://api-demo.dianrong.com/gw/borrow-channel/v5/channels/kingdeeco/pre-application-info");
        String res = resp.asString();
        String url = from(res).get("url").toString();
        System.out.println("授权页面链接:" + url);

        assertEquals(200, resp.getStatusCode());
    }

    //    获取贷款协议链接
    @Test(enabled = true, dependsOnMethods = {"testGetCOAuthPage"})
    public void testGetSignatureUrl() {
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                params("externalId", externalId).
                when().
                get("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/kingdeeco/applications/signature-url");
        String res = resp.asString();
        String url = from(res).get("url").toString();
        System.out.println("贷款协议链接:" + url);

        assertEquals(200, resp.getStatusCode());
    }

    //    查询还款计划
    @Test(enabled = true, dependsOnMethods = {"testGetSignatureUrl"})
    public void testGetPaymentPlans() {
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                params("externalId", externalId).
                when().
                get("/v4/channels/kingdeeco/application/payment-plans");
        String res = resp.asString();
        System.out.println("还款计划:" + res);

        assertEquals(200, resp.getStatusCode());
    }

    //    查询还款信息
    @Test(enabled = true, dependsOnMethods = {"testGetPaymentPlans"})
    public void testGetRayments() {
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                params("externalId", externalId).
                when().
                get("/v4/channels/kingdeeco/application/repayments");
        String res = resp.asString();
        System.out.println("还款信息:" + res);

        assertEquals(200, resp.getStatusCode());
    }

}
