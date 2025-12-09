package egovframework.home.pg.web;

import egovframework.home.pg.common.security.user.PrincipalDetails;
import egovframework.home.pg.service.PgHomeCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PgHomeCommentController {
    private final PgHomeCommentService pgHomeCommentService;

    /**
     * 댓글 - 해당 게시글의 댓글 데이터 리스트
     * @param req
     * @param res
     * @param model
     * @param param
     * @return 댓글 데이터 리스트
     * @throws Exception
     */
    @RequestMapping("/getCommentList.do")
    @ResponseBody
    public ResponseEntity<?> getCommentList(HttpServletRequest req, HttpServletResponse res, ModelMap model, @RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> retMap = new HashMap<>();
        HashMap<String, Object> listMap = new HashMap<>();

        try {
            // SecurityContext 에서 직접 Authentication 꺼내기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 회원이 로그인 했다면, memberId 같이 전송
            // 로그인 하지 않은 사용자도 Authentication이 anonymousUser로 들어와서 타입 캐스팅 에러 터져서 이렇게 함.
            // if (authentication != null && authentication.isAuthenticated()) {
            if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {

                PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
                listMap.put("currentMemberId", principal.getId());
            }

            // 댓글 전체 개수
            Long totalCnt = pgHomeCommentService.getCommentTotalCnt(param);

            // 댓글 리스트
            List<EgovMap> commentList = pgHomeCommentService.getCommentList(param);

            listMap.put("list", commentList);
            listMap.put("totalCnt", totalCnt);

            retMap.put("error", "N");
            retMap.put("dataMap", listMap);

        } catch (DataAccessException | MultipartException | NullPointerException | IllegalArgumentException e) {
            log.error("PgHomeCommentController:getCommentList.do error={}", e.getMessage());
            throw e;
        }
        return ResponseEntity.status(HttpStatus.OK).body(retMap);
    }

    /**
     * 댓글 - 댓글 Merge
     * @param req
     * @param res
     * @param model
     * @param param
     * @param authentication
     * @return 댓글 Merge 결과
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/setCommentMerge.do")
    @ResponseBody
    public ResponseEntity<?> setCommentMerge(
            HttpServletRequest req,
            HttpServletResponse res,
            ModelMap model,
            @RequestParam HashMap<String, Object> param,
            Authentication authentication
    ) throws Exception {
        HashMap<String, Object> retMap = new HashMap<>();

        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            param.put("memberId", principalDetails.getId());

            if (pgHomeCommentService.setCommentMerge(param)) {
                retMap.put("error", "N");
                retMap.put("successTitle", "Success");
                retMap.put("successMsg", "성공적으로 저장되었습니다.");
            } else {
                retMap.put("error", "Y");
                retMap.put("errorTitle", "댓글 저장");
                retMap.put("errorMsg", "데이터 처리 중 오류가 발생했습니다.");
            }
        } catch (DataAccessException | MultipartException | NullPointerException | IllegalArgumentException e) {
            log.error("PgHomeCommentController:setCommentMerge.do error={}", e.getMessage());
            throw e;
        }
        return ResponseEntity.status(HttpStatus.OK).body(retMap);
    }

    /**
     * 댓글 - 삭제: visible_yn을 Y로
     * @param req
     * @param res
     * @param model
     * @param param
     * @param authentication
     * @return 삭제 여부 결과
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/setCommentDelete.do")
    @ResponseBody
    public ResponseEntity<?> setCommentDelete(
            HttpServletRequest req,
            HttpServletResponse res,
            ModelMap model,
            @RequestParam HashMap<String, Object> param,
            Authentication authentication
    ) throws Exception {
        HashMap<String, Object> retMap = new HashMap<>();

        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            Long memberId = principalDetails.getId();

            String commentMemberId = StringUtils.stripToEmpty((String) param.get("commentMemberId"));
            Long commentMemberIdLong = Long.valueOf(commentMemberId);

            // 댓글 작성자가 아니라면
            if (!commentMemberIdLong.equals(memberId)) {
                retMap.put("error", "Y");
                retMap.put("errorTitle", "댓글 삭제");
                retMap.put("errorMsg", "댓글 작성자가 아닙니다.");
            } else {

                param.put("memberId", memberId);
                param.put("visibleYn", 'N');

                if (pgHomeCommentService.setCommentVisibleUpdate(param)) {
                    retMap.put("error", "N");
                    retMap.put("successTitle", "Success");
                    retMap.put("successMsg", "댓글이 삭제되었습니다.");
                } else {
                    retMap.put("error", "Y");
                    retMap.put("errorTitle", "댓글 저장");
                    retMap.put("errorMsg", "데이터 처리 중 오류가 발생했습니다.");
                }

            }
        } catch (DataAccessException | MultipartException | NullPointerException | IllegalArgumentException e) {
            log.error("PgHomeCommentController:setCommentDelete.do error={}", e.getMessage());
            throw e;
        }
        return ResponseEntity.status(HttpStatus.OK).body(retMap);
    }




}
