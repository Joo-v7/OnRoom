package egovframework.home.pg.service.impl;

import egovframework.home.pg.common.code.ReservationStatus;
import egovframework.home.pg.common.utils.AES256Util;
import egovframework.home.pg.common.utils.KakaoWorkUtil;
import egovframework.home.pg.exception.ConflictException;
import egovframework.home.pg.service.PgHomeReservationService;
import egovframework.home.pg.service.PgHomeRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PgHomeReservationServiceImpl implements PgHomeReservationService {

    private final PgHomeReservationMapper pgHomeReservationMapper;
    private final KakaoWorkUtil kakaoWorkUtil;

    /**
     * 예약 - 예약 전체 조회 (status가 'APPROVED', 'PENDING' 인 예약만)
     * @param param
     * @return 예약 데이터 리스트
     * @throws DataAccessException
     */
    @Override
    public List<EgovMap> getReservationList(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeReservationMapper.getReservationList(param);
    }

    /**
     * 예약 - 예약 단일 조회 by reservation_id (PK)
     * @param param
     * @return 예약 단일 데이터
     * @throws DataAccessException
     */
    @Override
    public EgovMap getReservation(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeReservationMapper.getReservation(param);
    }

    /**
     * 예약 - 예약 merge
     * @param param
     * @return 성공 여부
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public boolean setReservationMerge(HashMap<String, Object> param) throws Exception {
        boolean result = false;

        String action = StringUtils.stripToEmpty((String) param.get("action"));

        // 예약 중복 체크
        if (pgHomeReservationMapper.isDuplicatedReservation(param)) {
            throw new ConflictException("중복된 예약입니다.");
        }

        // 예약 Merge
        if (pgHomeReservationMapper.setReservationMerge(param) > 0) {
            result = true;

            EgovMap mergedReservation = pgHomeReservationMapper.getReservation(param);

            try {
                JSONArray userJsonArray = new JSONArray();
                JSONArray adminJsonArray = new JSONArray();

                String message = "OnRoom - 회의실 예약이 저장 알림";
                String adminMessage = "관리자 - 회원의 예약 저장";

                if (action.equals("insert")) {
                    message = "OnRoom - 회의실 예약이 등록 알림";
                    adminMessage = "관리자 - 회원의 예약 등록";
                } else if (action.equals("update")) {
                    message = "OnRoom - 회의실 예약이 수정 알림";
                    adminMessage = "관리자 - 회원의 예약 수정";
                }

                // ========================= 사용자 ========================

                // 카카오워크 블록킷 헤더
                userJsonArray.add(kakaoWorkUtil.headerBlock(message));
                // 카카오워크 블록킷 본문 제목
                userJsonArray.add(kakaoWorkUtil.bodyTitleBlock((String) mergedReservation.get("name")));
                // 본문 - 장소
                userJsonArray.add(kakaoWorkUtil.bodyContentBlock("장소", (String) mergedReservation.get("roomName")));
                // 본문 - 시간
                String startAt = mergedReservation.get("startAt").toString();
                String endAt = mergedReservation.get("endAt").toString();
                userJsonArray.add(kakaoWorkUtil.bodyContentBlock("시간", startAt.substring(0, 5) + " ~ " + endAt.substring(0, 5)));
                // 본문 - 일자
                String type = (String) mergedReservation.get("type");
                if (type.equals("D")) {
                    userJsonArray.add(kakaoWorkUtil.bodyContentBlock("일자", mergedReservation.get("startDate").toString()));
                } else if (type.equals("R")) {
                    userJsonArray.add(kakaoWorkUtil.bodyContentBlock("정기", mergedReservation.get("startDate").toString() + " ~ " + mergedReservation.get("endDate").toString()));
                }
                // 본문 - 상태
                String status = (String) mergedReservation.get("status");
                userJsonArray.add(kakaoWorkUtil.bodyContentBlock("상태", ReservationStatus.toKor(status)));
                // divider
                userJsonArray.add(kakaoWorkUtil.dividerBlock());
                // url 버튼
                userJsonArray.add(kakaoWorkUtil.buttonBlock("예약 확인하기", "http://onroom.site/reservationList.do"));

                // 카카오워크에 등록된 유저의 이메일로 메시지 전송
                String email = (String) mergedReservation.get("memberEmail");
                email = AES256Util.decrypt(email);

                kakaoWorkUtil.sendMessageToMemberByEmail(email, message, userJsonArray);


                // ============================= 관리자 ===============================

                adminJsonArray.add(kakaoWorkUtil.headerBlock(adminMessage));
                adminJsonArray.add(kakaoWorkUtil.bodyTitleBlock((String) mergedReservation.get("memberName") + " 님의 예약 요청"));
                adminJsonArray.add(kakaoWorkUtil.bodyContentBlock("회의실", (String) mergedReservation.get("roomName")));
                adminJsonArray.add(kakaoWorkUtil.bodyContentBlock("회의명", (String) mergedReservation.get("name")));
                adminJsonArray.add(kakaoWorkUtil.buttonBlock("예약 관리하기", "http://localhost:8080/admin/reservationList.do"));

                // 카카오워크 채팅창(관리자 용)에 전송
                kakaoWorkUtil.sendMessageToChatRoom(message, adminJsonArray);


            } catch (Exception e) {
                // 카카오워크 알림 실패해도 로그만찍고, 로직에는 영향 주지 않기
                log.error("PgHomeReservationController:setReservationMerge.KAKAO 알림 error={}", e.getMessage());
            }

        }

        return result;
    }

    /**
     * 예약 - 예약 전체 개수 조회
     * @param param
     * @return 예약 전체 개수
     * @throws DataAccessException
     */
    @Override
    public double getReservationTotalCnt(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeReservationMapper.getReservationTotalCnt(param);
    }

    /**
     * 예약 - 내 예약 데이터 리스트
     * @param param
     * @return 내 예약 데이터 리스트
     * @throws DataAccessException
     */
    @Override
    public List<EgovMap> getReservationListForMember(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeReservationMapper.getReservationListForMember(param);
    }

    /**
     * 관리자 - 예약 전체 조회
     * @param param
     * @return 예약 데이터 리스트
     * @throws DataAccessException
     */
    @Override
    public List<EgovMap> getReservationListForAdmin(HashMap<String, Object> param) throws DataAccessException {
        return pgHomeReservationMapper.getReservationListForAdmin(param);
    }

    /**
     * 관리자 - 예약 상태 업데이트
     * @param param
     * @return 상태 업데이트 결과 여부
     * @throws DataAccessException
     */
    @Transactional
    @Override
    public boolean setUpdateReservationStatus(HashMap<String, Object> param) throws DataAccessException {
        boolean result = false;

        if (pgHomeReservationMapper.setUpdateReservationStatus(param) > 0) {
            result = true;

            EgovMap updatedReservation = pgHomeReservationMapper.getReservation(param);

            try {
                JSONArray userJsonArray = new JSONArray();

                String status = (String) updatedReservation.get("status");
                status = ReservationStatus.toKor(status);

                String message = "OnRoom - 예약 " + status + " 알림";
                String title = (String) updatedReservation.get("memberName") + " 님의 회의실 예약이 " + status + " 되었습니다.";

                // 카카오워크 블록킷 헤더
                userJsonArray.add(kakaoWorkUtil.headerBlock(message));
                // 카카오워크 블록킷 본문 제목
                userJsonArray.add(kakaoWorkUtil.bodyTitleBlock(title));

                String email = (String) updatedReservation.get("memberEmail");
                email = AES256Util.decrypt(email);

                kakaoWorkUtil.sendMessageToMemberByEmail(email, message, userJsonArray);


            } catch (Exception e) {
                // 카카오워크 알림 실패해도 로그만찍고, 로직에는 영향 주지 않기
                log.error("PgHomeReservationController:setUpdateReservationStatus.KAKAO 알림 error={}", e.getMessage());
            }
        }

        return result;
    }
}
