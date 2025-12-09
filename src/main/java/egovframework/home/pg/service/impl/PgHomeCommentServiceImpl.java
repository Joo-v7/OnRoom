package egovframework.home.pg.service.impl;

import egovframework.home.pg.service.PgHomeCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PgHomeCommentServiceImpl implements PgHomeCommentService {
    private final PgHomeCommentMapper pgHomeCommentMapper;

    /**
     * 댓글 - 게시글의 댓글 전체 조회
     * @param param
     * @return 댓글 데이터 리스트
     * @throws DataAccessException
     */
    @Override
    public List<EgovMap> getCommentList(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeCommentMapper.getCommentList(param);
    }

    /**
     * 댓글 - 게시글의 댓글 전체 개수 조회
     * @param param
     * @return 댓글 개수
     * @throws DataAccessException
     */
    @Override
    public Long getCommentTotalCnt(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeCommentMapper.getCommentTotalCnt(param);
    }

    /**
     * 댓글 - Merge
     * @param param
     * @return Merge 결과 여부
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public boolean setCommentMerge(HashMap<String, Object> param) throws DataAccessException {
        boolean result = false;

        if (pgHomeCommentMapper.setCommentMerge(param) > 0) {
            result = true;
        }

        return result;
    }

    /**
     * 댓글 - 공개 여부 수정
     * @param param
     * @return 수정 결과 여부
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public boolean setCommentVisibleUpdate(HashMap<String, Object> param) throws DataAccessException {
        boolean result = false;

        if (pgHomeCommentMapper.setCommentVisibleUpdate(param) > 0) {
            result = true;
        }

        return result;
    }
}
