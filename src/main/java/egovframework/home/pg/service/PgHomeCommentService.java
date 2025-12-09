package egovframework.home.pg.service;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

public interface PgHomeCommentService {
    /**
     * 댓글 - 게시글의 댓글 전체 조회
     */
    List<EgovMap> getCommentList(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 댓글 - 게시글의 댓글 전체 개수 조회
     */
    Long getCommentTotalCnt(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 댓글 - 해당 게시글에 Merge
     */
    boolean setCommentMerge(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 댓글 - 댓글 공개 여부 수정
     */
    boolean setCommentVisibleUpdate(HashMap<String, Object> param) throws DataAccessException;

}
