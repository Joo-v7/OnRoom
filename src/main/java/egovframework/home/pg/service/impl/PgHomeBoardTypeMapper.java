package egovframework.home.pg.service.impl;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface PgHomeBoardTypeMapper {
    /**
     * 게시판 타입 - 게시판 타입 리스트 조회
     */
    List<EgovMap> getBoardTypeList() throws DataAccessException;
}
