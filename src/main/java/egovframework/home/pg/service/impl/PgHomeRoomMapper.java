package egovframework.home.pg.service.impl;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PgHomeRoomMapper {
    List<EgovMap> getRoomList(HashMap<String, Object> param) throws Exception;
    EgovMap getRoom(HashMap<String, Object> param) throws Exception;
}
