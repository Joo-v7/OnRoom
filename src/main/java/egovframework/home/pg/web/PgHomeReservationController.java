package egovframework.home.pg.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.faces.annotation.RequestMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PgHomeReservationController {

    @RequestMapping("/reservationList.do")
    public String reservationList(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        return "home/pg/reservation";
    }

    @RequestMapping("/getReservationList.do")
    public ResponseEntity<?> getReservationList(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        return ResponseEntity.ok(reservationList(req, res, model, param));
    }

}
