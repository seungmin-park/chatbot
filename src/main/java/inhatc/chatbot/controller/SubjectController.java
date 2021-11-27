package inhatc.chatbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import inhatc.chatbot.domain.SeleniumCrawling;
import inhatc.chatbot.domain.Subject;
import inhatc.chatbot.domain.SubjectDto;
import inhatc.chatbot.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final SeleniumCrawling seleniumCrawling;

    @PostConstruct
    public void add() {
        subjectService.join(
                new Subject("윤경섭", "시스템분석설계", "11주차과제",
                        LocalDateTime.of(2021, 11, 8, 0, 0),
                        LocalDateTime.of(2021, 11, 23, 23, 59))
        );
        subjectService.join(
                new Subject("김태간", "오픈소스프로그래밍", "11주차과제",
                LocalDateTime.of(2021, 11, 8, 0, 0),
                LocalDateTime.of(2021, 11, 15, 23, 59))
        );
    }

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    //과제 확인
    @PostMapping(value = "/subject/list", headers = {"Accept=application/json"})
    public HashMap subjects(@RequestParam Map<String, String> params) {
        HashMap<String, Object> resultJson = new HashMap<>();

        List<Subject> subjects = subjectService.findAll();
        String json = new Gson().toJson(subjects);

        List<HashMap<String,Object>> outputs = new ArrayList<>();
        HashMap<String,Object> template = new HashMap<>();
        HashMap<String, Object> simpleText = new HashMap<>();
        HashMap<String, Object> text = new HashMap<>();
        for (Subject subject : subjects) {
            text.put("text",subject.toString());
            simpleText.put("simpleText",text);
            outputs.add(simpleText);
        }
        template.put("outputs",outputs);

        resultJson.put("version","2.0");
        resultJson.put("template",template);
        return resultJson;
    }

    @PostMapping("/list")
    public Map<String, String> list(@RequestParam Map<String, String> params) {
        return params;
    }

    @PutMapping("/modify")
    public List<Subject> modify(@RequestBody String name) {
        Subject subject = subjectService.findByName(name);
        subject.modifySubject("교수명","과목명","과제명", LocalDateTime.now(),LocalDateTime.now());
        return subjectService.findAll();
    }

    //과제 추가
    @PostMapping("/add")
    public Subject add(@RequestBody Map<String, Object> params) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        HashMap userRequest = (HashMap) params.get("userRequest");
        String utterance = (String) userRequest.get("utterance");
        utterance = utterance.substring(2);
        utterance = utterance.substring(0, utterance.length() - 1);

        String[] values = utterance.split(",");

        String subProf = values[0];
        String subName = values[1];
        String subTitle = values[2];
        LocalDateTime subStart = LocalDateTime.parse(values[3], dateTimeFormatter);
        LocalDateTime subEnd = LocalDateTime.parse(values[4], dateTimeFormatter);

        Subject subject = new Subject(subProf, subName, subTitle, subStart, subEnd);

        Long join = subjectService.join(subject);

        return subjectService.findById(join);
    }

    @DeleteMapping("delete")
    public List<Subject> delete(@RequestParam String name) {
        subjectService.deleteByName(name);
        List<Subject> subjects = subjectService.findAll();
        return subjects;
    }

    @RequestMapping(value = "/kkoChat/v1" , method= {RequestMethod.POST , RequestMethod.GET },headers = {"Accept=application/json"})
    public String callAPI(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {

        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(params);
            System.out.println(jsonInString);
            int x = 0;
        }catch (Exception e){

        }
        return "index";
    }
}

