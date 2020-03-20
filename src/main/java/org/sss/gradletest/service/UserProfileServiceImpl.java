package org.sss.gradletest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sss.gradletest.mapper.UserProfileMapper;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    UserProfileMapper userProfileMapper;

    @Override
    public UserProfileInfo getuserProfileInfo(String cellphone) {
        UserProfileInfo userProfileInfo = new UserProfileInfo();
        String name = userProfileMapper.getMtCustomerInfo(cellphone);
        userProfileInfo.setName(name);
        return userProfileInfo;
    }
}
