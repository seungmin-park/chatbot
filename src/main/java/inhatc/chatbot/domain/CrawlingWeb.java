package inhatc.chatbot.domain;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlingWeb {

    public Elements crawl() throws IOException {
        Connection.Response main = Jsoup.connect("https://cyber.inhatc.ac.kr/Main.do")
                .data("cmd","viewHome")
                .method(Connection.Method.GET)
                .execute();

        Document mainPage = main.parse();

        Document doc = Jsoup.connect("https://cyber.inhatc.ac.kr/Main.do?cmd=viewHome").post();

        String RSAModulus = mainPage.select("input#RSAModulus").val();
        String RSAExponent = mainPage.select("input#RSAExponent").val();

        Document testPage = Jsoup.connect("https://cyber.inhatc.ac.kr/User.do")
                .data("RSAModulus",RSAModulus)
                .data("RSAExponent",RSAExponent)
                .data("cmd", "loginUser")
                .data("userId", "201844050")
                .data("password", "ddd1648215")
                .post();


        Connection.Response evaluationPage = Jsoup.connect("https://cyber.inhatc.ac.kr/Study.do")
                .followRedirects(true)
                .data("cmd","viewLearnerCourseList")
                .data("boardInfoDTO.boardInfoGubun","jungcourse")
                .cookies(main.cookies())
                .execute();

        Elements element = doc.select("div");


        return element;
    }
}
