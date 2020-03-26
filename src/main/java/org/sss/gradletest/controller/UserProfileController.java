package org.sss.gradletest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sss.gradletest.service.UserProfileInfo;
import org.sss.gradletest.service.UserProfileService;

@RestController
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/v1/user/info")
    public UserProfileInfo getUserProfileInfo(@RequestParam(name = "cellphone") final String cellphone) {
        return userProfileService.getuserProfileInfo(cellphone);
    }
}
