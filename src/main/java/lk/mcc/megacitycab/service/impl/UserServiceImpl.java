package lk.mcc.megacitycab.service.impl;

import lk.mcc.megacitycab.bean.ResponseBean;
import lk.mcc.megacitycab.bean.request.SignUpRequestBean;
import lk.mcc.megacitycab.exception.MccRunTimeException;
import lk.mcc.megacitycab.persistence.entity.UserEntity;
import lk.mcc.megacitycab.persistence.repo.UserRepo;
import lk.mcc.megacitycab.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Title: Mega-City-Cab
 * Description: UserServiceImpl Class
 * Created by Abhishek Ashinsa on 1/9/2025
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void userSignUp(SignUpRequestBean signUpRequestBean) {
        log.debug("User Service | User Sign Up");

        if (userRepo.existsByEmail(signUpRequestBean.getEmail())) {
            log.debug("User Service | User already exists");
            throw new MccRunTimeException("User is already exists!");
//            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
//                    .body(ResponseBean.createWithOutContent("User Already Exists", signUpRequestBean.getUsername()));
        }

        UserEntity entity = UserEntity.builder()
                .username(signUpRequestBean.getUsername())
                .email(signUpRequestBean.getEmail())
                .role(signUpRequestBean.getRole())
                .password(passwordEncoder.encode(signUpRequestBean.getPassword()))
                .build();

        userRepo.save(entity);
    }
}
