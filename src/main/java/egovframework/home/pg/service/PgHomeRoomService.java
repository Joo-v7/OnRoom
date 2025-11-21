package egovframework.home.pg.service;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

public interface PgHomeRoomService {
    List<EgovMap> getRoomList(HashMap<String, Object> param) throws Exception;
    EgovMap getRoom(HashMap<String, Object> param) throws Exception;
}
