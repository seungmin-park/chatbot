package inhatc.chatbot.domain.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Template {

    private List<Output> outputs = new ArrayList<>();

}
