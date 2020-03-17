package utils;

import api.JinDie.TestJinDiePLApi;
import org.testng.annotations.DataProvider;

public class JindiePLDataProvider extends TestJinDiePLApi {

    //public class JindiePLDataProvider extends TestJinDiePLApi {
    @DataProvider(name = "smallPreApplicationInfo")
    public Object[][] smallPreApplicationInfo() {
        String body = getSmallPreApplicationRequestBody();
        return new Object[][]{{body}};
    }

    public String getSmallPreApplicationRequestBody() {
        String smallPreApplicationRequestBody = smallUserInfo() + smallAppInfo();
        smallPreApplicationRequestBody = smallPreApplicationRequestBody.replaceAll("\\n", "");
        return smallPreApplicationRequestBody;
    }

    public String smallUserInfo() {
        String smallUserInfo = "{" +
                "\"user\": {\n" +
                "\"subjectType\":\"PERSONAL\",\n" +
                "\"borrowerProperty\":\"NATURAL_PERSON\",\n" +
                "\"realName\":\"" + personalRealName + "\"," +
//                "\"personalCellphone\":" + personalCellphone + "," +
                "\"cardNum\":" + personalSsn + "," +
                "\"cardType\":\"ID_CARD\"" +
                "},";
        return smallUserInfo;
    }

    public String smallAppInfo() {
        String smallAppInfo = smallPerson() + smallCompany() + loan() + documentInfo() + compliance() + contact() + job() + finance();
        return smallAppInfo;
    }

    public String smallPerson() {
        String smallPerson =
                "\"app\": {\n" +
                        "\"person\": {\n" +
                        "\"annualIncome\":\"450000\",\n" +
                        "\"mobilePhone\":" + personalCellphone + ",\n" +
                        "\"maritalStatus\":\"MARRIED\",\n" +
                        "\"residenceAddr\": {\n" +
                        "\"province\": \"湖南省\",\n" +
                        "\"city\": \"长沙市\",\n" +
                        "\"district\": \"滨江区\",\n" +
                        "\"detailedAddress\": \"伟业路481号银色港湾5-2-1603\"\n" +
                        "}," +
                        "\"permanentAddr\": {\n" +
                        "\"province\": \"湖南省\",\n" +
                        "\"city\": \"长沙市\",\n" +
                        "\"district\": \"滨江区\",\n" +
                        "\"detailedAddress\": \"伟业路481号银色港湾5-2-1603\"\n" +
                        "}," +
                        "\"relation\":[\"CONTROLLER\", \"LEGAL_REPRESENTATIVE\"]\n" + //必须且只能同时选择法定代表人及实际控制人
                        "},";
        return smallPerson;
    }


    public String smallCompany() {
        String smallCompany =
                "\"company\": {\n" +
                        "\"threeCertificateTogether\": \"YES\",\n" +
                        "\"uniformCreditCode\":\"" + companySsn + "\",\n" +

//                        "\"threeCertificateTogether\": \"NO\",\n" +
//                        "\"businessLicenseNum\":\"" + businessLicenseNum + "\",\n" +   //工商注册号15位
//                        "\"orgCodeCert\":\"" + orgCodeCert + "\",\n" +  //组织机构码9位
//                        "\"taxRegCertNum\":\"" + businessLicenseNum + "\",\n" +  //纳税码

                        "\"segment\": \"COMMUNICATION_ELECTRONICS\",\n" +
                        "\"name\":" + companyName + ",\n" +
                        "\"operationAddress\": {\n" +
                        "\"province\": \"湖南省\",\n" +
                        "\"city\": \"长沙市\",\n" +
                        "\"district\": \"滨江区\",\n" +
                        "\"detailedAddress\": \"伟业路481号银色港湾5-2-1603\"\n" +
                        "}," +
                        "\"businessScope\":\"电子电器制造\"\n" +
                        "},";
        return smallCompany;
    }


    public String loan() {
        String loan =
                "\"loan\": {\n" +
                        "\"externalId\":\"" + externalId + "\",\n" +
                        "\"appAmount\":" + appAmount + ",\n" +
                        "\"purpose\": \"FUND_FLOW\",\n" +  //固定值：FUND_FLOW
                        "\"maturity\": \"MONTH_" + maturity + "\",\n" +
                        "\"paymentMethod\":\"" + paymentMethod + "\",\n " +
                        "\"maturityType\": \"MONTHLY\",\n" + //固定值：MONTHLY
                        "\"loanAddType\":\"NORMAL_ADD\",\n" + //NORMAL_ADD 正常新增；CLEAR_ADD 结清再借
                        "\"channelDataOrigin\":[\"ERP\"]" + //ERP erp；TAX_COLLECTION 税采
                        "},";
        return loan;
    }

    public String documentInfo() {
        String documentInfo =
                "\"documentInfo\": [\n" +
                        "{\"location\":\"http://img.test.qiyego.com/images/gjsidcard/2019/01/15/9c47641a-152e-4e52-ab77-c3bc357d7dea.jpg\",\n" +
                        "\"type\":\"IDENTITY_CARD\"},\n" +
                        "{\"location\":\"http://img.test.qiyego.com/images/gjsidcard/2019/01/15/e5f51aeb-9a0a-4bba-a32f-bdd50c61b53c.jpg\",\n" +
                        "\"type\":\"IDENTITY_CARD_BACK\"},\n" +
                        "{\"location\":\"http://img.test.qiyego.com/images/other/7bb24eb2-950c-4e3e-94e6-92b0e9b91b7d.jpg\",\n" +
                        "\"type\":\"COM_REVENUE_LICENSE\"},\n" +
                        "{\"name\":\"1568969931800.pdf?e=1569574732&token=MFOtYi02R2QZzVVvFALkn0pdiiE5dOdTXJqdRw5I:Ph_zB1v5quxqumgWO8ZDV6z18tM=\",\n" +
                        "\"type\":\"OTHER_DOCUMENTS\",\n" +
                        "\"location\":\"http://dianyd.qiyego.com/1568969931800.pdf?e=1569574732&token=MFOtYi02R2QZzVVvFALkn0pdiiE5dOdTXJqdRw5I:Ph_zB1v5quxqumgWO8ZDV6z18tM=\"},\n" +
                        "{\"name\":\"泸州市建萍商贸有限公司.zip\",\n" +
                        "\"type\":\"OTHER_DOCUMENTS\",\n" +
                        "\"location\":\"http://rep.qiyego.com/datamanager/downloadcreditfile?companyName=%E6%B3%B8%E5%B7%9E%E5%B8%82%E5%BB%BA%E8%90%8D%E5%95%86%E8%B4%B8%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8\"}\n" +
                        "],";
        return documentInfo;
    }

    public String compliance() {
        String compliance =
                "\"compliance\": {\n" +
                        "\"provideCreditReport\":\"TRUE\"\n" +
                        "},";
        return compliance;
    }

    public String contact() {
        String contact =
                "\"contact\": [\n" +
                        "{\"phone\":\"17072873998\",\n" +
                        "\"name\":\"阿狸\",\n" +
                        "\"relation\":\"COUPLE\"\n" +
                        "}," +
                        "{\"phone\":\"17072873994\",\n" +
                        "\"name\":\"张强\",\n" +
                        "\"relation\":\"COLLEAGUE\"\n" +
                        "}" +
                        "],";
        return contact;
    }

    public String job() {
        String job =
                "\"job\":{\n" +
                        "\"occupation\":\"BUSINESSMAN\"\n" +
                        "},";
        return job;
    }

    public String finance() {
        String finance =
                "\"finance\":{\n" +
                        "\"allDeptAmt\":\"0\",\n" +
                        "\"legalPersonOtherLoans\":\"NO\",\n" +
                        "\"applicantOtherLoans\":\"NO\",\n" +
                        "\"incomeSource\":[\"OPERATING_INCOME\"],\n" +
                        "\"otherLoanPlatformAmt\":\"0\",\n" +
                        "\"otherLoanPlatformNum\":\"0\",\n" +
                        "\"delinquent180Num\":\"0\",\n" +
                        "\"delinquent180Amt\":\"0\",\n" +
                        "\"delinquent180AccountNum\":\"0\",\n" +
                        "\"companyOtherLoans\":\"NO\"\n" +
                        "}}}";
        return finance;
    }
}
