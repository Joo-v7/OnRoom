package egovframework.home.pg.web;

import egovframework.home.pg.service.PgHomeBoardService;
import egovframework.home.pg.service.PgHomeBoardTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PgHomeBoardController {

    private final PgHomeBoardService pgHomeBoardService;
    private final PgHomeBoardTypeService pgHomeBoardTypeService;

    /**
     * 게시판 - 리스트 화면
     * @param req
     * @param res
     * @param model
     * @param param
     * @return boardList.jsp
     * @throws Exception
     */
    @RequestMapping("/boardList.do")
    public String boardList(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        List<EgovMap> boardTypeList = pgHomeBoardTypeService.getBoardTypeList();
        model.addAttribute("boardTypeList", boardTypeList);

        return "home/pg/boardList";
    }

    /**
     * 게시판 - 데이터 리스트
     * @param req
     * @param res
     * @param model
     * @param param
     * @return 게시판 데이터 리스트
     * @throws Exception
     */
    @RequestMapping("/getBoardList.do")
    @ResponseBody
    public ResponseEntity<?> getBoardList(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> retMap = new HashMap<>();
        HashMap<String, Object> listMap = new HashMap<>();

        try {
            // 페이지 번호
            Object movePageObject = param.get("movePage");
            int movePage = ObjectUtils.isEmpty(movePageObject) ? 1 : NumberUtils.toInt(movePageObject.toString());
            param.put("movePage", movePage);

            // 한페이지 레코드 개수
            double recordCnt = NumberUtils.toDouble((String) param.get("recordCnt"),10);
            if (StringUtils.isNotEmpty((String) param.get("recordCnt"))) recordCnt = NumberUtils.toDouble((String) param.get("recordCnt"));
            param.put("recordCnt", (int)recordCnt);

            // limit 시작 개수 (Mariadb, mySql)
            param.put("limitStart", ((movePage - 1) * (int)recordCnt));

            // 전체 개수
            double totalCnt = pgHomeBoardService.getBoardTotalCnt(param);

            // 전체 페이지수
            double pageCnt = Math.ceil(totalCnt / recordCnt);
            int totalPage = (int)(pageCnt > 0 ? pageCnt : 1);

            // 페이지 유효성 체크
            if (movePage > totalPage) {
                movePage = 1;
                param.put("movePage", movePage);
                param.put("limitStart", (movePage - 1) * (int)recordCnt);
            }

            List<EgovMap> boardList = pgHomeBoardService.getBoardList(param);
            List<EgovMap> boardTypeList = pgHomeBoardTypeService.getBoardTypeList();
            model.addAttribute("boardTypeList", boardTypeList);

            listMap.put("list", boardList);
            listMap.put("boardTypeList", boardTypeList);
            listMap.put("page", movePage);
            listMap.put("pageCnt", pageCnt);
            listMap.put("totalCnt", totalCnt);

            retMap.put("error", "N");
            retMap.put("dataMap", listMap);

            // 전역 에러
        } catch (DataAccessException | NullPointerException e) {
            log.error("PgHomeBoardController:getBoardList.do error={}", e.getMessage());
            throw e;
        }

        return ResponseEntity.status(HttpStatus.OK).body(retMap);
    }

    @RequestMapping("/boardView.do")
    public String boardView(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        pgHomeBoardService.increaseViewCount(param);
        EgovMap board = pgHomeBoardService.getBoard(param);
        model.addAttribute("board", board);

        // TODO: 회원 구현하면 작성자 model에 넣어주기.

        // TODO: 댓글 기능 구현
        model.addAttribute("writer", "홍길동");

        return "home/pg/boardView";
    }

    /**
     * 게시판 - 등록 페이지
     * @param req
     * @param res
     * @param model
     * @param param
     * @return board.jsp
     * @throws Exception
     */
    @RequestMapping("/board.do")
    public String boardForm(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        List<EgovMap> boardTypeList = pgHomeBoardTypeService.getBoardTypeList();
        model.addAttribute("boardTypeList", boardTypeList);
        return "home/pg/board";
    }

    /**
     * 게시판 - 등록
     * @param req
     * @param res
     * @param model
     * @param param
     * @return 게시판 등록 결과
     * @throws Exception
     */
    @RequestMapping("/setBoardMerge.do")
    @ResponseBody
    public ResponseEntity<?> setBoardMerge(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> retMap = new HashMap<>();

        // TODO: 반드시 회원가입/로그인 구현 후 수정.
        param.put("memberId", 1);

        if (pgHomeBoardService.setBoardMerge(param)) {
            retMap.put("error", "N");
            retMap.put("successTitle", "Success");
            retMap.put("successMsg", "성공적으로 저장 되었습니다.");
        } else {
            retMap.put("error", "Y");
            retMap.put("errorTitle", "게시판 저장");
            retMap.put("errorMsg", "데이터 처리 중 오류가 발생했습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(retMap);

    }

    // TODO: 게시판 - 수정/삭제(소프트 삭제)

}
