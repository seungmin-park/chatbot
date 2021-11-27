package inhatc.chatbot.jsontest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class KakaoJsonTest {
    private Object HashMap;

    @Test
    @DisplayName("Test name")
    void testName() throws Exception {
        //given
       String test = "{\"bot\":{\"id\":\"61a080c432f1604582d029a6!\",\"name\":\"inhatc_subject\"},\"intent\":{\"id\":\"61a080c432f1604582d029aa\",\"name\":\"폴백 블록\",\"extra\":{\"reason\":{\"code\":1,\"message\":\"OK\"}}},\"action\":{\"id\":\"61a2253a32f1604582d02db0\",\"name\":\"풀백블럭\",\"params\":{},\"detailParams\":{},\"clientExtra\":{}},\"userRequest\":{\"block\":{\"id\":\"61a080c432f1604582d029aa\",\"name\":\"폴백 블록\"},\"user\":{\"id\":\"7a668e8e35915add475d00fcf38f6b680e755f8480d80f794fee2631732514e1c0\",\"type\":\"botUserKey\",\"properties\":{\"botUserKey\":\"7a668e8e35915add475d00fcf38f6b680e755f8480d80f794fee2631732514e1c0\",\"bot_user_key\":\"7a668e8e35915add475d00fcf38f6b680e755f8480d80f794fee2631732514e1c0\"}},\"utterance\":\"!(윤경섭,시스템분석설계,13주차과제,2021-11-27,2021-12-01\\n)\",\"params\":{\"surface\":\"BuilderBotTest\",\"ignoreMe\":\"true\"},\"lang\":\"kr\",\"timezone\":\"Asia/Seoul\"},\"contexts\":[]}";
       ObjectMapper objectMapper = new ObjectMapper();
        HashMap hashMap = objectMapper.readValue(test, HashMap.class);
        HashMap userRequest = (java.util.HashMap) hashMap.get("userRequest");
        String addSubject = (String) userRequest.get("utterance");
        System.out.println("addSubject = " + addSubject);
        addSubject = addSubject.substring(2);
        addSubject = addSubject.substring(0, addSubject.length() - 1);
        //then
        System.out.println("addSubject = " + addSubject);
    }
}
