package egovframework.home.pg.service.impl;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PgHomeReservationMapper {
    List<EgovMap> getReservationList(HashMap<String, Object> param) throws Exception;

}
