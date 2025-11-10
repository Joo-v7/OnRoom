package egovframework.home.pg.service.impl;

import egovframework.home.pg.service.PgHomeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PgHomeBoardServiceImpl extends EgovAbstractServiceImpl implements PgHomeBoardService {

    private final PgHomeBoardMapper pgHomeBoardMapper;

    private final PgHomeBoardTypeMapper pgHomeBoardTypeMapper;

    @Override
    public List<EgovMap> getBoardTypeList() throws DataAccessException {
        return pgHomeBoardTypeMapper.getBoardTypeList();
    }

    @Override
    public double getBoardTotalCnt(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeBoardMapper.getBoardTotalCnt(param);
    }

    @Override
    public List<EgovMap> getBoardList(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeBoardMapper.getBoardList(param);
    }

    @Override
    public EgovMap getBoard(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeBoardMapper.getBoard(param);
    }

    @Override
    public boolean setBoardMerge(HashMap<String, Object> param) throws DataAccessException {
        boolean result = false;

        if (pgHomeBoardMapper.setBoardMerge(param) > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public boolean setBoardDelete(HashMap<String, Object> param) throws DataAccessException {
        boolean result = false;

        if (pgHomeBoardMapper.setBoardDelete(param) > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public int increaseViewCount(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeBoardMapper.increaseViewCount(param);
    }
}
