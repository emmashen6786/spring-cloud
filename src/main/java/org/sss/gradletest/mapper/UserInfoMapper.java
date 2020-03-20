package org.sss.gradletest.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {
    String getUserName(@Param("user_cellphone") String user_cellphone);

    void addUserInfo(@Param("user_name") String user_name, @Param("user_cellphone") String user_cellphone);

    void updateUserInfo(@Param("user_name") String user_name, @Param("user_cellphone") String user_cellphone);

    void deleteUserInfo(@Param("user_cellphone") String user_cellphone);
}
