package lk.mcc.megacitycab.service;

import lk.mcc.megacitycab.bean.ResponseBean;
import lk.mcc.megacitycab.bean.request.LoginRequestBean;
import lk.mcc.megacitycab.bean.request.SignUpRequestBean;
import lk.mcc.megacitycab.bean.respnse.LoginResponseBean;
import org.springframework.http.ResponseEntity;

/**
 * Title: Mega-City-Cab
 * Description: UserService Class
 * Created by Abhishek Ashinsa on 1/9/2025
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
public interface UserService {
    void userSignUp(SignUpRequestBean signUpRequestBean);

    ResponseEntity<ResponseBean<LoginResponseBean>> authenticatUser(LoginRequestBean loginRequestBean);

    String LoggedInUser();
}
