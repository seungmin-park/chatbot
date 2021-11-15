package inhatc.chatbot.domain;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlingWeb {

    public Elements crawl() throws IOException {
        Connection.Response initial = Jsoup.connect("https://cyber.inhatc.ac.kr/Main.do")
                .data("cmd","viewHome")
                .method(Connection.Method.GET)
                .execute();

        Document key = initial.parse();

        Document doc = Jsoup.connect("https://cyber.inhatc.ac.kr/Main.do")
                .data("cmd","viewHome")
                .cookies(initial.cookies())
                .get();

        Elements element = doc.select("div.twoDep");
        
        return element;
    }
}
