package org.sss.gradletest.service;

import com.dianrong.common.http.Get;
import com.dianrong.ftc.loanapp.spi.client.LoanAppClient;
import com.dianrong.ftc.loanapp.spi.domain.loan.LoanAppVo;
import com.jayway.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sss.gradletest.mapper.UserInfoMapper;

import static com.jayway.restassured.RestAssured.given;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    LoanAppClient loanAppClient;

    @Override
    public String getUserName(String user_cellphone) {
        return userInfoMapper.getUserName(user_cellphone);
    }

//    @Override
//    public void addUserInfo(String user_name, String user_cellphone) {
//        userInfoMapper.addUserInfo(user_name, user_cellphone);
//    }

    @Override
    public void updateUserInfo(String user_name, String user_cellphone) {
        if (userInfoMapper.getUserName(user_cellphone) != null) {
            userInfoMapper.updateUserInfo(user_name, user_cellphone);
        } else userInfoMapper.addUserInfo(user_name, user_cellphone);
    }

    @Override
    public void deleteUserInfo(String user_cellphone) {
        userInfoMapper.deleteUserInfo(user_cellphone);
    }

    @Override
    public LoanAppVo getUserLoanInfo(Long loanAppId) {
        return loanAppClient.getApplication(loanAppId);
    }

    private static final String loanAppHost = "http://loanapp-demo.b8.dr.dianrong.io";
    private static final String authorizations = "/v1/borrowers/{aid}/authorizations";

    public String getAuthorizations(Long aid, Long channelId) {
        String url = loanAppHost.concat(authorizations);
        try {
            Response resp = given().
                    header("Content-Type", "application/json").
                    pathParam("aid", aid).
                    param("channelId", channelId).
                    when().get(url);
            return resp.asString();

        } catch (Exception e) {
//            log.error("Get documents error, loanAppId: {}, message: {}", loanAppId, e.getMessage(), e);
            return null;
        }
    }
}
