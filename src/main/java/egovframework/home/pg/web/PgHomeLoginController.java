package egovframework.home.pg.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PgHomeLoginController {

    @RequestMapping("/login.do")
    public String login() {
        return "home/pg/login";
    }

}
