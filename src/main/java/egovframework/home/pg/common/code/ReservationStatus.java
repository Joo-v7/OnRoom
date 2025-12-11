package egovframework.home.pg.common.code;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    PENDING("승인대기"),
    APPROVED("승인완료"),
    REJECTED("반려"),
    CANCELLED("취소");

    private final String kor;

    ReservationStatus(String kor) {
        this.kor = kor;
    }

    public String getKor() {
        return kor;
    }

    // 값 -> kor
    public static String toKor(String status) {
        if (status == null) {
            return null;
        }

        try {
            return ReservationStatus
                    .valueOf(status.toUpperCase())
                    .getKor();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}


