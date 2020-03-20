package org.sss.gradletest.service;

public interface UserInfoService {
    String getUserName(String user_cellphone);

//    void addUserInfo(String user_name, String user_cellphone);

    void updateUserInfo(String user_name, String user_cellphone);

    void deleteUserInfo(String user_cellphone);
}
