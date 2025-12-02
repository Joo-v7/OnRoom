package egovframework.home.pg.service;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

public interface PgHomeRoomService {
    /**
     * 사용가능한 회의실 조건 조회
     */
    List<EgovMap> getRoomList(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 사용가능한 회의실 전체 개수
     */
    Double getRoomTotalCnt(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 회의실 단일 조회
     */
    EgovMap getRoomById(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 관리자 - 회의실 전체 조회
     */
    List<EgovMap> getRoomListForAdmin(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 관리자 - 회의실 전체 개수 조회
     */
    Double getRoomTotalCntForAdmin(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 관리자 - 회의실 Merge
     */
    boolean setMergeRoom(HashMap<String, Object> param) throws DataAccessException;

    /**
     * 관리자 - 회의실 사용여부 수정
     */
    boolean setRoomUseYnUpdate(HashMap<String, Object> param) throws DataAccessException;

}
