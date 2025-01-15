package lk.mcc.megacitycab.exception;

import lk.mcc.megacitycab.bean.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static lk.mcc.megacitycab.util.constatnt.AppConstant.NOT_FOUND;


/**
 * Title: orange-backend
 * Description: GlobalExceptionHandler Class
 * Created by Abhishek Ashinsa on 11/6/2024
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseBean> handleExceptions(Exception e) {
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseBean("06", e.getLocalizedMessage(), null));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseBean> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Error: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBean("06", "Validation Failed", null));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ResponseBean> handleNotFoundExceptions(NoDataFoundException ex) {
        log.error("Error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseBean(NOT_FOUND, ex.getMessage(), null));
    }


}
