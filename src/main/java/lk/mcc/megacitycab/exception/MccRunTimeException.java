package lk.mcc.megacitycab.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Title: Mega-City-Cab
 * Description: MccRunTimeException Class
 * Created by Abhishek Ashinsa on 1/9/2025
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class MccRunTimeException extends RuntimeException {
    private String statusCode;

    public MccRunTimeException(String message) {
        super(message);
    }
    public MccRunTimeException(String message,String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
