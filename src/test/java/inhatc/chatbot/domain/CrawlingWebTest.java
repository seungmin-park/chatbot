package inhatc.chatbot.domain;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrawlingWebTest {

    @Test
    @DisplayName("크롤링테스트")
    void 크롤링테스트() throws Exception {
        //given
        CrawlingWeb crawlingWeb = new CrawlingWeb();
        //when
        Elements elements = crawlingWeb.crawl();
        //then
        for (Element element : elements) {
            System.out.println("element = " + element);
        }
    }
}