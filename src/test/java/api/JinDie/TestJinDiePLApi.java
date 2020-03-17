package api.JinDie;

import com.jayway.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.IdCardGenerator;
import utils.JindiePLDataProvider;
import utils.RandomDataPortal;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.testng.AssertJUnit.assertEquals;


public class TestJinDiePLApi {
    protected static String token;
    protected static String personalRealName = RandomDataPortal.randomName();
    protected static String personalSsn = '"' + new IdCardGenerator().appoint(22, 50) + '"';
    protected static String personalCellphone = '"' + new RandomDataPortal().randomPhone() + '"';

    //    protected static String personalRealName = "张三丰";
//    protected static String personalCellphone = "13862112000";
//    protected static String personalSsn = "110101198803077516";
    protected static String companyName = '"' + personalRealName + "有限公司\"";
    //    protected static String companySsn = "91510100743648218G";  //18位
    protected static String companySsn = "91510100" + RandomDataPortal.getRandomNineNo() + "G";  //18位
//    protected static String companyCardType = "UNIFORM_CREDIT_CODE";

//    protected static String businessLicenseNum = "91510" + RandomDataPortal.getRandomNineNo() + "G";  //15位
//    protected static String orgCodeCert = RandomDataPortal.getRandomNineNo(); //9位
//    protected static String companyCardType="BUSINESS_LICENSE";

    protected static Integer appAmount = 60000;
//    protected static Integer maturity = 3;   //借款期限 3个月
//    protected static String paymentMethod = "PAY_AT_THE_END";   //还款方式-每月还息，到期一次性还本

    protected static Integer maturity = 12;   //借款期限 6/9/12个月
    protected static String paymentMethod = "AMORTIZATION";   //还款方式-等额本息

    protected static String externalId = RandomDataPortal.getRandomSerialNo();
//    protected static String externalId = "JD20191031009230100";

    @BeforeTest(enabled = true, groups = {"JinDieSmallNormalTestGroup", "PLPrecheckAbnomalTestGroup"})
//    获取授权Token
    public void getPLChannelToken() {
        String data = "{" +
                "\"appId\":\"22141aed4f4344d6a9635f68215de2b2\"," +
                "\"appSecret\":\"$5odAscV05xdn0$y\"" +
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

    //  个人订单预检查
    @Test(enabled = true, groups = {"JinDieSmallNormalTestGroup"})
    public void testPLPrecheck() {
        String data = "{" +
                "\"userSubjectType\":\"PERSONAL\"," +
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
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/kingdeepl/precheck");
        String res = resp.asString();
        String result = from(res).get("result").toString();

        assertEquals(200, resp.getStatusCode());
        Assert.assertEquals(result, "true");
    }

    //    小额推送预订单
    @Test(enabled = true, dataProvider = "smallPreApplicationInfo", dataProviderClass = JindiePLDataProvider.class, groups = {"JinDieSmallNormalTestGroup"}, dependsOnMethods = {"testPLPrecheck"})
    public void testSmallPreApplicationInfo(String getSmallPreApplicationRequestBody) {
        String data = getSmallPreApplicationRequestBody;
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v5/channels/kingdeepl/pre-application-info");
        String res = resp.asString();
        System.out.println(res);
        String result = from(res).get("success").toString();

        assertEquals(200, resp.getStatusCode());
        Assert.assertEquals(result, "true");
    }

    //    获取授权页面链接
    @Test(enabled = true, dependsOnMethods = {"testSmallPreApplicationInfo"})
    public void testGetPLAuthPage() {
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                params("externalId", externalId).
                when().
                get("https://api-demo.dianrong.com/gw/borrow-channel/v5/channels/kingdeepl/pre-application-info");
        String res = resp.asString();
        String url = from(res).get("url").toString();
        System.out.println("授权页面链接:" + url);

        assertEquals(200, resp.getStatusCode());
    }

    //    获取贷款协议链接
    @Test(enabled = true, dependsOnMethods = {"testGetPLAuthPage"})
    public void testGetSignatureUrl() {
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                params("externalId", externalId).
                when().
                get("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/kingdeepl/applications/signature-url");
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
                get("/v4/channels/kingdeepl/application/payment-plans");
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
                get("/v4/channels/kingdeepl/application/repayments");
        String res = resp.asString();
        System.out.println("还款信息:" + res);

        assertEquals(200, resp.getStatusCode());
    }
}
