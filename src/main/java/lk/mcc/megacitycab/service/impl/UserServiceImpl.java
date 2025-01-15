package lk.mcc.megacitycab.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lk.mcc.megacitycab.bean.ResponseBean;
import lk.mcc.megacitycab.bean.request.LoginRequestBean;
import lk.mcc.megacitycab.bean.request.SignUpRequestBean;
import lk.mcc.megacitycab.bean.respnse.LoginResponseBean;
import lk.mcc.megacitycab.exception.MccRunTimeException;
import lk.mcc.megacitycab.exception.NoDataFoundException;
import lk.mcc.megacitycab.persistence.entity.UserEntity;
import lk.mcc.megacitycab.persistence.repo.UserRepo;
import lk.mcc.megacitycab.service.JwtService;
import lk.mcc.megacitycab.service.UserService;
import lk.mcc.megacitycab.util.constatnt.AppConstant;
import lk.mcc.megacitycab.util.constatnt.ResponseMessageConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
                .role(signUpRequestBean.getRole().name())
                .password(passwordEncoder.encode(signUpRequestBean.getPassword()))
                .build();

        userRepo.save(entity);
    }

    @Override
    public ResponseEntity<ResponseBean<LoginResponseBean>> authenticatUser(LoginRequestBean loginRequestBean) {
        if (userRepo.existsByUsername(loginRequestBean.getUsername())) {
            log.debug("User Service | Login | Starting find user");

            UserEntity userEntity = userRepo.findByUsername(loginRequestBean.getUsername()).orElseThrow(() -> {
                log.debug("User Service | Login | User not found");
                return new NoDataFoundException(ResponseMessageConstant.USER_NOT_FOUND);
            });

            log.debug("Starting User Authentication");
            return isAuthenticated(loginRequestBean, userEntity);
        } else {
            log.debug("User Service | Login | User not found");
            throw new NoDataFoundException(ResponseMessageConstant.USER_NOT_FOUND);
        }

    }

    private ResponseEntity<ResponseBean<LoginResponseBean>> isAuthenticated(@NotNull LoginRequestBean loginRequestBean, @NotNull UserEntity userEntity) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestBean.getUsername(),
                        loginRequestBean.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            log.debug("User Service | Login | Login Success");
            String accessToken = jwtService.generateJwtAccessToken(userEntity.getUsername(), userEntity.getRole());

            LocalDateTime lastloginDate = LocalDateTime.now();

            return saveAccessTokenAndUpdateLastLoginDate(userEntity, accessToken)
                    ? ResponseBean.success(
                            AppConstant.SUCCESS,
                            new LoginResponseBean(
                                    userEntity.getUsername(),
                                    userEntity.getRole(),
                                    accessToken,
                                    lastloginDate
            )) : ResponseEntity.ok(ResponseBean.unauthorized(AppConstant.UNAUTHORIZED, ResponseMessageConstant.FAILED_TO_AUTH));

        } else {
            log.debug("User Service | Login | Login Failed");
            return ResponseEntity.ok(ResponseBean.unauthorized(AppConstant.UNAUTHORIZED, ResponseMessageConstant.FAILED_TO_AUTH));
        }
    }

    private boolean saveAccessTokenAndUpdateLastLoginDate(@NotNull UserEntity userEntity, String accessToken) {
        userEntity.setLastLoginDate(LocalDateTime.now());
        userEntity.setPortalToken(accessToken);
        userRepo.save(userEntity);
        return true;
    }

    @Override
    @Transactional
    public String LoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String username = null;

            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                username = userDetails.getUsername();
                log.debug("User Service | LoggedInUser | Username : " + username);
            } else if (authentication.getPrincipal() instanceof String) {
                username = (String) authentication.getPrincipal();
            }

            if (username != null && !username.isEmpty()) {
                if (userRepo.existsByUsername(username)) {
                    log.debug("User Service | LoggedInUser | Username : " + username);
                    return username;
                }
                throw new NoDataFoundException(ResponseMessageConstant.USER_NOT_FOUND);
            }
            throw new NoDataFoundException(ResponseMessageConstant.INVALID_USER_NAME);
        }
        throw new NoDataFoundException(ResponseMessageConstant.TOKEN_FETCH_FAILED);
    }
}
