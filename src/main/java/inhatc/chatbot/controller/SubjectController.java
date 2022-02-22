package inhatc.chatbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import inhatc.chatbot.domain.Subject;
import inhatc.chatbot.domain.response.Output;
import inhatc.chatbot.domain.response.SimpleText;
import inhatc.chatbot.domain.response.Template;
import inhatc.chatbot.domain.response.UserSubjectResponse;
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

    @PostConstruct
    public void init() {
        subjectService.join(
                new Subject("김태간", "오픈소스프로그래밍", "11주차과제",
                        LocalDateTime.of(2022, 02, 8, 0, 0),
                        LocalDateTime.of(2022, 02, 27, 23, 59))
        );
        subjectService.join(
                new Subject("김태간1", "오픈소스프로그래밍1", "11주차과제1",
                        LocalDateTime.of(2022, 02, 8, 0, 0),
                        LocalDateTime.of(2022, 02, 27, 23, 59))
        );
        subjectService.join(
                new Subject("김태간12", "오픈소스프로그래밍12", "11주차과제12",
                        LocalDateTime.of(2022, 02, 8, 0, 0),
                        LocalDateTime.of(2022, 02, 27, 23, 59))
        );
    }

    private final SubjectService subjectService;

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    //과제 확인
    @PostMapping(value = "/subject/list")
    public UserSubjectResponse subjects() {
        UserSubjectResponse userSubjectResponse = new UserSubjectResponse();

        List<Subject> subjects = subjectService.findAll();
        Template template = new Template();
        List<Output> outputs = template.getOutputs();
        if (subjects.size() == 0) {
            outputs.add(new Output(new SimpleText("현재 진행중인 과제가 없습니다.")));
        } else {
            for (Subject subject : subjects) {
                outputs.add(new Output(new SimpleText(subject.toString())));
            }
        }
        userSubjectResponse.setTemplate(template);

        return userSubjectResponse;
    }

    @PutMapping("/modify")
    public List<Subject> modify(@RequestBody String name) {
        Subject subject = subjectService.findByName(name);
        subject.modifySubject("교수명","과목명","과제명", LocalDateTime.now(),LocalDateTime.now());
        return subjectService.findAll();
    }

    //과제 추가
    @PostMapping(value = "/subject/add", headers = {"Accept=application/json"})
    public HashMap add(@RequestBody Map<String, Object> params) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        HashMap<String, Object> resultJson = new HashMap<>();
        List<HashMap<String, Object>> outputs = new ArrayList<>();
        HashMap<String, Object> template = new HashMap<>();
        HashMap<String, Object> simpleText = new HashMap<>();
        HashMap<String, Object> text = new HashMap<>();

        HashMap action = (HashMap) params.get("action");
        HashMap actionParams = (HashMap) action.get("params");
        String addSubject = (String) actionParams.get("subject");

        try {
            String[] values = addSubject.split(",");

            String subProf = values[0];
            String subName = values[1];
            String subTitle = values[2];
            LocalDateTime subStart = LocalDateTime.parse(values[3], dateTimeFormatter);
            LocalDateTime subEnd = LocalDateTime.parse(values[4], dateTimeFormatter);

            Subject subject = new Subject(subProf, subName, subTitle, subStart, subEnd);

            Long join = subjectService.join(subject);

            Subject findByIdSubject = subjectService.findById(join);

            text.put("text", findByIdSubject.toString());

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

