package egovframework.home.pg.service;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

public interface PgHomeBoardService {

    double getBoardTotalCnt(HashMap<String, Object> param) throws DataAccessException;

    List<EgovMap> getBoardList(HashMap<String, Object> param) throws DataAccessException;

    EgovMap getBoard(HashMap<String, Object> param) throws DataAccessException;

    boolean setBoardMerge(HashMap<String, Object> param) throws DataAccessException;

    boolean setBoardDelete(HashMap<String, Object> param) throws DataAccessException;

    int increaseViewCount(HashMap<String, Object> param) throws DataAccessException;

}
