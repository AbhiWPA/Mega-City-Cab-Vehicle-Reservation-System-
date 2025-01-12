package lk.mcc.megacitycab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: Mega-City-Cab
 * Description: TestController Class
 * Created by Abhishek Ashinsa on 1/9/2025
 * Email: abhishek_a@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/sample")
    public String sample() {
        return "sample";
    }
}
