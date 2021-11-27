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
       String test = "{\"bot\":{\"id\":\"61a080c432f1604582d029a6!\",\"name\":\"inhatc_subject\"},\"intent\":{\"id\":\"61a083d3f303916b2cb9da4d\",\"name\":\"과제추가명령어\",\"extra\":{\"reason\":{\"code\":0,\"message\":\"OK\"}}},\"action\":{\"id\":\"61a2253a32f1604582d02db0\",\"name\":\"풀백블럭\",\"params\":{\"subject\":\"1추가\"},\"detailParams\":{\"subject\":{\"groupName\":\"\",\"origin\":\"1 추가\",\"value\":\"1추가\"}},\"clientExtra\":{}},\"userRequest\":{\"block\":{\"id\":\"61a083d3f303916b2cb9da4d\",\"name\":\" 과제추가명령어\"},\"user\":{\"id\":\"7a668e8e35915add475d00fcf38f6b680e755f8480d80f794fee2631732514e1c0\",\"type\":\"botUserKey\",\"properties\":{\"botUserKey\":\"7a668e8e35915add475d00fcf38f6b680e755f8480d80f794fee2631732514e1c0\",\"bot_user_key\":\"7a668e8e35915add475d00fcf38f6b680e755f8480d80f794fee2631732514e1c0\"}},\"utterance\":\"!과제추가\\n\",\"params\":{\"surface\":\"BuilderBotTest\",\"ignoreMe\":\"true\"},\"lang\":\"kr\",\"timezone\":\"Asia/Seoul\"},\"contexts\":[]}";
       ObjectMapper objectMapper = new ObjectMapper();
        HashMap hashMap = objectMapper.readValue(test, HashMap.class);
        System.out.println("hashMap = " + hashMap);
        HashMap userRequest = (java.util.HashMap) hashMap.get("action");
        HashMap params = (java.util.HashMap) userRequest.get("params");
        String subject = (String) params.get("subject");
        System.out.println("addSubject = " + subject);
//        subject = subject.substring(2);
//        subject = subject.substring(0, subject.length() - 1);
        //then
        System.out.println("addSubject = " + subject);
    }
}
