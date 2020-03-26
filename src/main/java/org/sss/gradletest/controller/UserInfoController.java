package org.sss.gradletest.controller;

import com.dianrong.ftc.loanapp.spi.domain.loan.LoanAppVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sss.gradletest.service.UserInfoService;


@RestController
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/v1/user/{user_cellphone}")
    public String getUserName(@PathVariable("user_cellphone") String user_cellphone) {

        return userInfoService.getUserName(user_cellphone);
    }

//    @PostMapping("v1/user")
//    public String addUserInfo(@RequestParam String user_name, @RequestParam String user_cellphone) {
//        userInfoService.addUserInfo(user_name, user_cellphone);
//        return "添加成功";
//    }

    @PostMapping("/v1/user")
    public String updateUserInfo(@RequestParam String user_name, @RequestParam String user_cellphone) {
        userInfoService.updateUserInfo(user_name, user_cellphone);
        return "更新成功";
    }

    @PostMapping("/v1/delete_user")
    public String deleteUserInfo(@RequestParam String user_cellphone) {
        userInfoService.deleteUserInfo(user_cellphone);
        return "删除成功";
    }

    @GetMapping("/v1/get_user_loan_info")
    public LoanAppVo getUserLoanInfo(@RequestParam Long loanAppId) {
        return userInfoService.getUserLoanInfo(loanAppId);
    }

}
