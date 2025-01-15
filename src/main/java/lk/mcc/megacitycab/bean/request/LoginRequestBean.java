package lk.mcc.megacitycab.bean.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoginRequestBean {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
