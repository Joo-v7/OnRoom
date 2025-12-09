package egovframework.home.pg.service.impl;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PgHomeCommentMapper {

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
    int setCommentMerge(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 댓글 - 댓글 공개 여부 수정
     */
    int setCommentVisibleUpdate(HashMap<String, Object> param) throws DataAccessException;
}
