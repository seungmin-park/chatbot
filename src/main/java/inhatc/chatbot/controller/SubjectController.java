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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    //과제 확인
    @PostMapping(value = "/subject/list")
    public Object subjects() {

        List<Subject> subjects = subjectService.findAll();
        Template template = new Template();
        if (subjects.size() == 0) {
            template.getOutputs().add(new Output(new SimpleText("현재 진행중인 과제가 없습니다.")));
        } else {
            for (Subject subject : subjects) {
                template.getOutputs().add(new Output(new SimpleText(subject.toString())));
            }
        }

        return new UserSubjectResponse(template);
    }

    @PutMapping("/modify")
    public List<Subject> modify(@RequestBody String name) {
        Subject subject = subjectService.findBySubName(name);
        subject.modifySubject("교수명","과목명","과제명", LocalDateTime.now(),LocalDateTime.now());
        return subjectService.findAll();
    }

    //과제 추가
    @PostMapping(value = "/subject/add")
    public UserSubjectResponse add(@RequestBody Map<String, Object> params) {

        HashMap action = (HashMap) params.get("action");
        HashMap actionParams = (HashMap) action.get("params");
        String addSubject = (String) actionParams.get("subject");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Template template = new Template();
        try {
            String[] values = addSubject.split(",");

            String subProf = values[0];
            String subName = values[1];
            String subTitle = values[2];
            LocalDateTime subStart = LocalDateTime.parse(values[3], dateTimeFormatter);
            LocalDateTime subEnd = LocalDateTime.parse(values[4], dateTimeFormatter);

            Subject subject = new Subject(subProf, subName, subTitle, subStart, subEnd);
            subjectService.join(subject);


            template.getOutputs().add(new Output(new SimpleText(subject.toString())));

        } catch (Exception e) {
            template.getOutputs().add(new Output(new SimpleText("잘못된 정보가 들어왔습니다.")));
        }
        finally {

            return new UserSubjectResponse(template);
        }
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

