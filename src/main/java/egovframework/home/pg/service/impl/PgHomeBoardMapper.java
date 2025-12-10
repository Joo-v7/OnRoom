package egovframework.home.pg.service.impl;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PgHomeBoardMapper {

    /**
     * 게시판 - 전체 개수
     */
    double getBoardTotalCnt(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 게시판 - 리스트
     */
    List<EgovMap> getBoardList(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 게시판 - 단일 조회
     */
    EgovMap getBoard(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 게시판 - Merge
     */
    int setBoardMerge(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 게시판 - 삭제
     */
    int setBoardStatusUpdate(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 게시판 - 조회수 + 1
     */
    int increaseViewCount(HashMap<String, Object> param) throws DataAccessException;
}
