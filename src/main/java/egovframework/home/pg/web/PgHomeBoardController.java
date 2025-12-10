package egovframework.home.pg.web;

import egovframework.home.pg.common.security.user.PrincipalDetails;
import egovframework.home.pg.service.PgHomeBoardService;
import egovframework.home.pg.service.PgHomeBoardTypeService;
import egovframework.home.pg.service.PgHomeMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PgHomeBoardController {

    private final PgHomeBoardService pgHomeBoardService;
    private final PgHomeBoardTypeService pgHomeBoardTypeService;
    private final PgHomeMemberService pgHomeMemberService;

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
            listMap.put("recordCnt", recordCnt);

            retMap.put("error", "N");
            retMap.put("dataMap", listMap);

            // 전역 에러
        } catch (DataAccessException | NullPointerException e) {
            log.error("PgHomeBoardController:getBoardList.do error={}", e.getMessage());
            throw e;
        }

        return ResponseEntity.status(HttpStatus.OK).body(retMap);
    }

    /**
     * 게시판 - 단일 게시판 뷰 페이지
     * @param req
     * @param res
     * @param model
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping("/boardView.do")
    public String boardView(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        // SecurityContext 에서 직접 Authentication 꺼내기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 회원이 로그인 했다면, memberId 같이 전송
        // 로그인 하지 않은 사용자도 Authentication이 anonymousUser로 들어와서 타입 캐스팅 에러 터져서 이렇게 함.
        // if (authentication != null && authentication.isAuthenticated()) {
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {

            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("currentMemberId", principal.getId());
        }

        // 뷰 카운트 + 1
        pgHomeBoardService.increaseViewCount(param);
        // 게시글 데이터
        EgovMap board = pgHomeBoardService.getBoard(param);

        model.addAttribute("board", board);

        return "home/pg/boardView";
    }

    /**
     * 게시판 - 등록/수정 페이지
     * @param req
     * @param res
     * @param model
     * @param param
     * @return board.jsp
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/board.do")
    public String boardForm(
            HttpServletRequest req,
            HttpServletResponse res,
            ModelMap model,
            @RequestParam HashMap<String, Object> param,
            Authentication authentication
    ) throws Exception {

        // 관리자 체크
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        boolean isAdmin = principal.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        // 게시판 타입 리스트
        List<EgovMap> boardTypeList = pgHomeBoardTypeService.getBoardTypeList();
        model.addAttribute("boardTypeList", boardTypeList);

        String action = StringUtils.stripToEmpty((String) param.get("action"));

        if(action.equals("update")) {
            EgovMap board = pgHomeBoardService.getBoard(param);
            model.addAttribute("board", board);
        }

        return "home/pg/board";
    }

    /**
     * 게시판 - 등록
     * @param req
     * @param res
     * @param param
     * @return 게시판 등록 결과
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/setBoardMerge.do")
    @ResponseBody
    public ResponseEntity<?> setBoardMerge(
            HttpServletRequest req,
            HttpServletResponse res,
            @RequestParam HashMap<String, Object> param,
            Authentication authentication
    ) throws Exception {
        HashMap<String, Object> retMap = new HashMap<>();

        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            param.put("memberId", principalDetails.getId());

            if (pgHomeBoardService.setBoardMerge(param)) {
                retMap.put("error", "N");
                retMap.put("successTitle", "Success");
                retMap.put("successMsg", "성공적으로 저장되었습니다.");
            } else {
                retMap.put("error", "Y");
                retMap.put("errorTitle", "예약 저장");
                retMap.put("errorMsg", "데이터 처리 중 오류가 발생했습니다.");
            }

        } catch (DataAccessException | NullPointerException e) {
        log.error("PgHomeBoardController:setBoardMerge.do error={}", e.getMessage());
        throw e;
    }

        return ResponseEntity.status(HttpStatus.OK).body(retMap);
    }

    /**
     * 게시글 - 삭제 (상태 변경: 소프트 삭제)
     * @param req
     * @param res
     * @param model
     * @param param
     * @return 게시글 삭제(상태 변경) 결과
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/setBoardDelete.do")
    @ResponseBody
    public ResponseEntity<?> setBoardDelete(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> retMap = new HashMap<>();

        try {
            param.put("status", "DELETED");

            if (pgHomeBoardService.setBoardStatusUpdate(param)) {
                retMap.put("error", "N");
                retMap.put("successTitle", "Success");
                retMap.put("successMsg", "게시판이이 삭제되었습니다.");
            } else {
                retMap.put("error", "Y");
                retMap.put("errorTitle", "게시글 삭제");
                retMap.put("errorMsg", "데이터 처리 중 오류가 발생했습니다.");
            }
        } catch (DataAccessException | MultipartException | NullPointerException | IllegalArgumentException e) {
            log.error("PgHomeCommentController:setBoardDelete.do error={}", e.getMessage());
            throw e;
        }
        return ResponseEntity.status(HttpStatus.OK).body(retMap);

    }


}
