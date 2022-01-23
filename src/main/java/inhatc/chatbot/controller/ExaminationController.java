package inhatc.chatbot.controller;

import inhatc.chatbot.domain.Examination;
import inhatc.chatbot.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController("/exam")
@RequiredArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;
    HashMap<String,Object> template = new HashMap<>();


    @PostMapping("/exam/list")
    public HashMap list(@RequestParam Map<String,Object> params) {
        HashMap<String, Object> resultJson = new HashMap<>();

        List<Examination> examinations = examinationService.findAll();
        List<HashMap<String,Object>> outputs = new ArrayList<>();
        HashMap<String,Object> carousel = new HashMap<>();
        HashMap<String,Object> template = new HashMap<>();
        HashMap<String,Object> test = new HashMap<>();
        List<HashMap<String,Object>> items = new ArrayList<>();
        resultJson.put("version","2.0");
        for (Examination examination : examinations) {
            HashMap<String,Object> item = new HashMap<>();
            item.put("description",examination.toString());
            item.put("title",examination.getSubName());
            items.add(item);
        }
        carousel.put("items",items);
        carousel.put("type", "basicCard");
        test.put("carousel", carousel);
        outputs.add(test);
        template.put("outputs", outputs);
        resultJson.put("template",template);
        return resultJson;
    }

    @PostMapping(value = "/exam/add", headers = {"Accept=application/json"})
    public HashMap add(@RequestBody Map<String, Object> params) {

        HashMap<String, Object> resultJson = new HashMap<>();
        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> template = new HashMap<>();
        HashMap<String, Object> simpleText = new HashMap<>();
        HashMap<String, Object> text = new HashMap<>();

        HashMap action = (HashMap) params.get("action");
        HashMap actionParams = (HashMap) action.get("params");
        String examAdd = (String) actionParams.get("exam");

        try {
            String[] values = examAdd.split(",");

            String subName = values[0];
            String subProf = values[1];
            String subMethod = values[2];
            String subStart = values[3];
            String subLoc = values[4];

            Examination examination = new Examination(subName, subProf, subMethod, subStart, subLoc);

            examinationService.join(examination);

            text.put("text", examination.toString());

        } catch (Exception e) {
            text.put("text", "잘못된 정보가 들어왔습니다.");
        }
        finally {

            simpleText.put("simpleText", text);
            outputs.add(simpleText);
            template.put("outputs", outputs);

            resultJson.put("version", "2.0");
            resultJson.put("template", template);
        }

        return resultJson;
    }
}
