package egovframework.home.pg.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class KakaoWorkUtil {

    private final RestTemplate restTemplate = new RestTemplate();

    // TODO 암호화하기
    private final String APP_KEY = "6c4238d4.376e0ed6d1834a34bf1de553e962e1d1";
    // 내 채팅방 ID
    private final String CONVERSATION_ID = "918067765681437";
    // 특정 채팅방에 메시지를 전송하는데 사용하는 URL
    private final String KAKAO_URL = "https://api.kakaowork.com/v1/messages.send";
    // 이메일로 특정 유저에게 메시지를 전송하는데 사용되는 URL
    private final String KAKAO_URL_BY_EMAIL = "https://api.kakaowork.com/v1/messages.send_by_email";


    /**
     * 카카오워크에서 제공받은 채팅창(관리자 용)에 메시지 전송
     * @param message
     */
    public void sendMessageToChatRoom(String message, JSONArray blocks) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + APP_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject body = new JSONObject();
            body.put("conversation_id", CONVERSATION_ID);
            body.put("text", message);
            body.put("blocks", blocks);

            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
            restTemplate.exchange(KAKAO_URL, HttpMethod.POST, entity, String.class);

        } catch (Exception e) {
            log.error("카카오워크 채팅창 알림 전송 실패: " + e.getMessage());
        }
    }


    /**
     * 카카워워크에 등록된 이메일로 메시지 전송
     * @param email
     * @param message
     */
    public void sendMessageToMemberByEmail(String email, String message, JSONArray blocks) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + APP_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject body = new JSONObject();
            body.put("email", email);
            body.put("text", message);
            body.put("blocks", blocks);

            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
            restTemplate.exchange(KAKAO_URL_BY_EMAIL, HttpMethod.POST, entity, String.class);

        } catch (Exception e) {
            log.error("카카오워크 유저 알림 전송 실패: " + e.getMessage());
        }
    }

    /**
     * 카카오워크 - 헤더 블럭
     */
    public JSONObject headerBlock(String text) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "header");
        jsonObject.put("text", text);
        jsonObject.put("style", "blue");
        return jsonObject;
    }

    /**
     * 카카오워크 - 본문 - 제목
     */
    public JSONObject bodyTitleBlock(String text) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "text");
        jsonObject.put("text", "text sample");

        // inlines 배열
        JSONArray inlines = new JSONArray();

        JSONObject inline = new JSONObject();
        inline.put("type", "styled");
        inline.put("text", text);
        inline.put("bold", true);
        inline.put("color", "blue");

        inlines.add(inline);

        jsonObject.put("inlines", inlines);

        return jsonObject;
    }

    /**
     * 카카오워크 - 본문 - 내용   ex) 상태  |  승인대기
     */
    public JSONObject bodyContentBlock(String label, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "text");
        jsonObject.put("text", "text sample");

        // inlines 배열
        JSONArray inlines = new JSONArray();

        // 라벨 inline
        JSONObject labelInline = new JSONObject();
        labelInline.put("type", "styled");
        labelInline.put("text", label);
        labelInline.put("bold", true);
        labelInline.put("color", "grey");

        // 값 inline
        JSONObject valueInline = new JSONObject();
        valueInline.put("type", "styled");
        valueInline.put("text", "     " + value);

        // inline들 병합
        inlines.add(labelInline);
        inlines.add(valueInline);

        jsonObject.put("inlines", inlines);

        return jsonObject;
    }

    /**
     * divider
     */
    public JSONObject dividerBlock() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "divider");
        return jsonObject;
    }

    /**
     * button
     */
    public JSONObject buttonBlock(String text, String url) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "button");
        jsonObject.put("text", text);
        jsonObject.put("style", "default");

        // action
        JSONObject actionObject = new JSONObject();
        actionObject.put("type", "open_system_browser");
        actionObject.put("name", "button1");
        actionObject.put("value", url);

        jsonObject.put("action", actionObject);

        return jsonObject;
    }

}



