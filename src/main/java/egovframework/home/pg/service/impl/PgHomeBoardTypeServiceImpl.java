package egovframework.home.pg.service.impl;

import egovframework.home.pg.service.PgHomeBoardTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PgHomeBoardTypeServiceImpl extends EgovAbstractServiceImpl implements PgHomeBoardTypeService {
    private final PgHomeBoardTypeMapper pgHomeBoardTypeMapper;

    @Override
    public List<EgovMap> getBoardTypeList() throws DataAccessException {
        return pgHomeBoardTypeMapper.getBoardTypeList();
    }

}
