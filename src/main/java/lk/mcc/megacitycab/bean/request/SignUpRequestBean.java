package lk.mcc.megacitycab.bean.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Title: Mega-City-Cab
 * Description: SignUpRequestBean Class
 * Created by Abhishek Ashinsa on 1/9/2025
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpRequestBean {

    @NotBlank(message = "Merchant Username should not be blank or null")
    @NotEmpty(message = "Merchant Username is required")
    private String username;

    @NotBlank(message = "Merchant Password should not be blank or null")
    @NotEmpty(message = "Merchant Password is required")
    private String password;

    @NotBlank(message = "Merchant Email should not be blank or null")
    @NotEmpty(message = "Merchant Email is required")
    @Email
    private String email;

    @NotBlank(message = "Merchant User Role should not be blank or null")
    @NotEmpty(message = "Merchant User Role is required")
    private String role;
}
