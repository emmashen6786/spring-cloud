package org.sss.gradletest.mapper;

import org.apache.ibatis.annotations.Param;


public interface UserProfileMapper {
    String getMtCustomerInfo(@Param("cellphone") String cellphone);
}
