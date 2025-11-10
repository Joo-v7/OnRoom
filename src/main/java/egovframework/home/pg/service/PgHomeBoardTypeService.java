package egovframework.home.pg.service;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PgHomeBoardTypeService {
    List<EgovMap> getBoardTypeList() throws DataAccessException;
}
