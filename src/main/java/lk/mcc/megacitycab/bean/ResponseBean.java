package lk.mcc.megacitycab.bean;

import java.io.Serializable;

import static lk.mcc.megacitycab.util.AppConstant.*;

/**
 * Title: orange-backend
 * Description: ResponseBean Class
 * Created by Abhishek Ashinsa on 11/4/2024
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
public record ResponseBean(String status, String message, Object content) implements Serializable {
    public static ResponseBean createWithOutContent(String status, String message) {
        return new ResponseBean(status, message, null);
    }

    public static ResponseBean success(Object content) {
        return new ResponseBean(SUCCESS, "Success", content);
    }

    public static ResponseBean notfound(String message) {
        return new ResponseBean(NOT_FOUND, message, null);
    }

    public static ResponseBean unauthorized(String message) {
        return new ResponseBean(UNAUTHORIZED, message, null);
    }
}
