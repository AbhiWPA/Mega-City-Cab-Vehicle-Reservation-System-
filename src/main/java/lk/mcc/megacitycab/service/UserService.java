package lk.mcc.megacitycab.service;

import lk.mcc.megacitycab.bean.request.SignUpRequestBean;

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
}
