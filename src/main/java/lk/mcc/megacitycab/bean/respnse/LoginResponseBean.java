package lk.mcc.megacitycab.bean.respnse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseBean {

    private String username;
    private String role;
    private String token;
    private LocalDateTime lastLoginDate;
}
