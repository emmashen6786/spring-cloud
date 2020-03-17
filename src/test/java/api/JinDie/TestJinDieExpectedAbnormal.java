package api.JinDie;

import com.jayway.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.IdCardGenerator;
import utils.RandomDataPortal;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.testng.AssertJUnit.assertEquals;

public class TestJinDieExpectedAbnormal {
    protected static String token;
    //protected static String token;
    protected static String personalRealName = RandomDataPortal.randomName();
    protected static String personalSsn = '"' + new IdCardGenerator().appoint(22, 50) + '"';
    protected static String personalCellphone = '"' + new RandomDataPortal().randomPhone() + '"';

//    protected static String personalRealName = "张三丰";
//    protected static String personalCellphone = "13862112000";
//    protected static String personalSsn = "110101198803077516";

    protected static String companyName = '"' + personalRealName + "有限公司\"";
    protected static String companySsn = "91510100743648211G";  //18位
    protected static String companyCardType = "UNIFORM_CREDIT_CODE";

//    protected static String companySsn = "91510100743648G";  //15位
//    protected static String companyCardType="BUSINESS_LICENSE";

    protected static Integer appAmount = 12000;
    protected static Integer maturity = 3;   //借款期限 3个月
    protected static String paymentMethod = "PAY_AT_THE_END";   //还款方式-每月还息，到期一次性还本

//    protected static Integer maturity = 3;   //借款期限 6/9/12个月
//    protected static String paymentMethod="AMORTIZATION";   //还款方式-等额本息

    //    protected static String externalId = '"' + RandomDataPortal.getRandomSerialNo() + '"';
    protected static String externalId = "JD20191031009230100";

    @BeforeTest(groups = {"JinDieSmallNormalTestGroup", "JinDieBigNormalTestGroup", "PLPrecheckAbnomalTestGroup"})
//    获取授权Token
    public void getChannelToken() {
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
        String token = from(res).get("token").toString();
        if (resp.getStatusCode() == 200) {
            this.token = token;
            System.out.println("获取授权Token成功：" + token);
        } else {
            System.out.println("获取授权Token失败，错误返回是：" + res);
        }
        assertEquals(200, resp.getStatusCode());
    }

    //  个人订单预检查
    @Test(groups = {"PLPrecheckAbnomalTestGroup"})
    public void testTokenOverDue() {
        String data = "{" +
                "\"userSubjectType\":\"PERSONAL\"," +
                "\"personalRealName\":\"" + personalRealName + "\"," +
                "\"personalCellphone\":" + personalCellphone + "," +
                "\"personalSsn\":" + personalSsn + "," +
                "\"personalCardType\":\"SSN\"" +
                "}";
        Response resp = given().
                header("X-Channel-Authorization", "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJDSEFOTkVMX0FVVEhFTlRJQ0FUSU9OIiwiQVBQX0lEIjoiMjIxNDFhZWQ0ZjQzNDRkNmE5NjM1ZjY4MjE1ZGUyYjIiLCJpc3MiOiJBVVRIX1NFUlZFUiIsImV4cCI6MTU3MjU4Njc1OSwiaWF0IjoxNTcyNTAwMzU5LCJDSEFOTkVMX0FQUF9OQU1FIjoi5rex5Zyz6YeR6J225LqS6IGU572R6YeR6J6N5pyN5Yqh5pyJ6ZmQ5YWs5Y-4IiwianRpIjoiZjFiOTYyZmUtYjkyYi00MWJmLWE3M2MtMmRlOTQ3MmFiZDBkIn0.iNWuj-rRliQxjvx_cnkVv8pNfCZdLD0ergWO_5ja0Pui-rQ8IgwL-3E2HC7bMoxvdMzPR9ZAnXiVJmwjsQNehYBwSqFpLHQtfCKBrUb4mB_5c9lPF_1PtdoTVeyqjlvo_HfG4cxDRstA97t36Zd4uaSXDy8rea6cnu8sEtokcjE").
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/jindiepl/precheck");
        String res = resp.asString();
        String errorCode = from(res).get("errorCode").toString();
        String errorMessage = from(res).get("errorMessage").toString();
        Assert.assertEquals(errorCode, "0400002");
        Assert.assertEquals(errorMessage, "token过期");
    }

    @Test(groups = {"PLPrecheckAbnomalTestGroup"})
    public void testParamVerifiFail() {
        String data = "{" +
                "\"userSubjectType\":\"PERSONAL\"," +
                "\"personalRealName\":\"123\"," +
                "\"personalCellphone\":\"abc\"," +
                "\"personalSsn\":" + personalSsn + "," +
                "\"personalCardType\":\"SSN\"" +
                "}";
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/jindiepl/precheck");
        String res = resp.asString();
        String errorCode = from(res).get("errorCode").toString();
        String errorMessage = from(res).get("errorMessage").toString();
        Assert.assertEquals(errorCode, "0400004");
        Assert.assertEquals(errorMessage, "参数校验失败");
    }

    @Test(groups = {"PLPrecheckAbnomalTestGroup"})
    public void testThreeElementVerifiFail() {
        String data = "{" +
                "\"userSubjectType\":\"PERSONAL\"," +
                "\"personalRealName\":\"方一饭\"," +
                "\"personalCellphone\":\"131000000001\"," +
                "\"personalSsn\":\"342623199502036789," +
                "\"personalCardType\":\"SSN\"" +
                "}";
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/jindiepl/precheck");
        String res = resp.asString();
        String errorCode = from(res).get("errorCode").toString();
        String errorMessage = from(res).get("errorMessage").toString();
        Assert.assertEquals(errorCode, "0400104");
        Assert.assertEquals(errorMessage, "三要素（姓名、手机号码、身份证号码）校验不通过");
    }

    @Test(groups = {"PLPrecheckAbnomalTestGroup"})
    public void testCellphoneRegisteredNameOrSsnAtypism() {
        String data = "{" +
                "\"userSubjectType\":\"PERSONAL\"," +
                "\"personalRealName\":\"方一饭\"," + //点融库-张涉
                "\"personalCellphone\":\"17200001011\"," +
                "\"personalSsn\":\"342623199502036789," +  //点融库-440102197009203616
                "\"personalCardType\":\"SSN\"" +
                "}";
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/jindiepl/precheck");
        String res = resp.asString();
        String errorCode = from(res).get("errorCode").toString();
        String errorMessage = from(res).get("errorMessage").toString();
        Assert.assertEquals(errorCode, "0400110");
        Assert.assertEquals(errorMessage, "手机号码已注册，已注册对应的证件号码或姓名和入参不一致");
    }

    @Test(groups = {"PLPrecheckAbnomalTestGroup"})
    public void testSsnRegisteredNameOrCellphoneAtypism() {
        String data = "{" +
                "\"userSubjectType\":\"PERSONAL\"," +
                "\"personalRealName\":\"方一饭\"," + //点融库-张涉
                "\"personalCellphone\":\"131000000001\"," + //点融库-17200001011
                "\"personalSsn\":\"440102197009203616," +
                "\"personalCardType\":\"SSN\"" +
                "}";
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/jindiepl/precheck");
        String res = resp.asString();
        String errorCode = from(res).get("errorCode").toString();
        String errorMessage = from(res).get("errorMessage").toString();
        Assert.assertEquals(errorCode, "0400111");
        Assert.assertEquals(errorMessage, "身份证号码已注册，已注册对应手机号码或姓名和入参不一致");
    }

    @Test(groups = {"PLPrecheckAbnomalTestGroup"})
    public void testCannotBorrow() {
        String data = "{" +
                "\"userSubjectType\":\"PERSONAL\"," +
                "\"personalRealName\":\"张庭文\"," +
                "\"personalCellphone\":\"17200001008\"," +
                "\"personalSsn\":\"362302199607127539," +
                "\"personalCardType\":\"SSN\"" +
                "}";
        Response resp = given().
                header("X-Channel-Authorization", token).
                header("Content-Type", "application/json").
                request().
                body(data).
                when().
                post("https://api-demo.dianrong.com/gw/borrow-channel/v4/channels/jindiepl/precheck");
        String res = resp.asString();
        String errorCode = from(res).get("errorCode").toString();
        String errorMessage = from(res).get("errorMessage").toString();
        Assert.assertEquals(errorCode, "0400209");
        Assert.assertEquals(errorMessage, "不可借款");
    }
}

