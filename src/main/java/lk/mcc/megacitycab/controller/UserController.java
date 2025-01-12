package lk.mcc.megacitycab.controller;

import lk.mcc.megacitycab.bean.ResponseBean;
import lk.mcc.megacitycab.bean.request.SignUpRequestBean;
import lk.mcc.megacitycab.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: Mega-City-Cab
 * Description: UserController Class
 * Created by Abhishek Ashinsa on 1/9/2025
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
@RestController
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signUp",consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseBean> userSignUp(@RequestBody SignUpRequestBean signUpRequestBean) {
        userService.userSignUp(signUpRequestBean);
        return ResponseEntity.ok(ResponseBean.success("User Sign Up Successful"));
    }

}
