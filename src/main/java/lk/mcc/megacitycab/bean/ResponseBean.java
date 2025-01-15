package lk.mcc.megacitycab.bean;

import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * Title: orange-backend
 * Description: ResponseBean Class
 * Created by Abhishek Ashinsa on 11/4/2024
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
public record ResponseBean<T>(String code, String message, T content) implements Serializable {

    public static ResponseBean createWithOutContent(String status, String message) {
        return new ResponseBean(status, message, null);
    }

    public static <T> ResponseEntity<ResponseBean<T>> success(String message, T content) {
        ResponseBean<T> responseBean = new ResponseBean<>("00", message, content);
        return ResponseEntity.ok(responseBean);
    }

    public static <T> ResponseEntity<ResponseBean<T>> success(T content) {
        ResponseBean<T> responseBean = new ResponseBean<>("00", "Success", content);
        return ResponseEntity.ok(responseBean);
    }
    public static <T> ResponseEntity<ResponseBean<T>> success() {
        ResponseBean<T> responseBean = new ResponseBean<>("00", "Success", null);
        return ResponseEntity.ok(responseBean);
    }

    public static <T> ResponseBean<T> failed(String code, String message) {
        return new ResponseBean<>(code, message, null);
    }

    public static <T> ResponseBean<T> unauthorized(String code, String message) {
        return new ResponseBean<>(code, message, null);
    }
}
