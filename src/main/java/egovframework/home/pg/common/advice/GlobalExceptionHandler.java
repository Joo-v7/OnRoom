package egovframework.home.pg.common.advice;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;


/**
 * 전역 예외를 처리하는 핸들러 클래스
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataAccessException.class, MultipartException.class})
    public ResponseEntity<?> handleDataAccessException() {
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("error", "Y");
        retMap.put("errorTitle", "DB Error");
        retMap.put("errorMsg", "데이터 처리 중 오류가 발생했습니다.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(retMap);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException() {
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("error", "Y");
        retMap.put("errorTitle", "Error");
        retMap.put("errorMsg", "시스템 오류가 발생했습니다.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(retMap);
    }


}

