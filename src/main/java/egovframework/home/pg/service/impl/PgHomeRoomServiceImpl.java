package egovframework.home.pg.service.impl;

import egovframework.home.pg.service.PgHomeRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PgHomeRoomServiceImpl implements PgHomeRoomService {

    private final PgHomeRoomMapper pgHomeRoomMapper;

    /**
     * 사용 가능한 회의실 조건 조회
     * @param param
     * @return 회의실 데이터 리스트
     * @throws DataAccessException
     */
    @Override
    public List<EgovMap> getRoomList(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeRoomMapper.getRoomList(param);
    }

    /**
     * 회의실 단일 조회 by ID (PK)
     * @param param
     * @return 회의실 데이터
     * @throws DataAccessException
     */
    @Override
    public EgovMap getRoomById(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeRoomMapper.getRoomById(param);
    }

    /**
     * 사용 가능한 회의실 전체 개수 조회
     * @param param
     * @return 사용 가능한 회의실 전체 개수
     * @throws DataAccessException
     */
    @Override
    public Double getRoomTotalCnt(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeRoomMapper.getRoomTotalCnt(param);
    }

    /**
     * 회의실 관리 - 회의실 전체 조회
     * @param param
     * @return 회의실 데이터 리스트
     * @throws DataAccessException
     */
    @Override
    public List<EgovMap> getRoomListForAdmin(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeRoomMapper.getRoomListForAdmin(param);
    }

    /**
     * 회의실 관리 - 회의실 전체 개수 조회
     * @param param
     * @return 회의실 전체 개수
     * @throws DataAccessException
     */
    @Override
    public Double getRoomTotalCntForAdmin(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeRoomMapper.getRoomTotalCntForAdmin(param);
    }

    /**
     * 회의실 관리 - 회의실 Merge
     * @param param
     * @return 회의실
     * @throws DataAccessException
     */
    @Override
    public boolean setMergeRoom(HashMap<String, Object> param) throws DataAccessException {
        boolean result = false;

        if (pgHomeRoomMapper.setMergeRoom(param) > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public boolean setRoomUseYnUpdate(HashMap<String, Object> param) throws DataAccessException {
        boolean result = false;

        if (pgHomeRoomMapper.setRoomUseYnUpdate(param) > 0) {
            result = true;
        }

        return result;
    }
}
