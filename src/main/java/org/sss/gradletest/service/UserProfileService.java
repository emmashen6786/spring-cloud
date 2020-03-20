package org.sss.gradletest.service;

import org.springframework.stereotype.Service;

public interface UserProfileService {
    UserProfileInfo getuserProfileInfo(String cellphone);
}
