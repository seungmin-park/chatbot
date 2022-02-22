package inhatc.chatbot.domain;

import inhatc.chatbot.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeleniumCrawling {

    private WebDriver webDriver;
    private final SubjectService subjectService;

    @Scheduled(fixedDelay = 1000*60*10)
    public void subjectCrawling() {
        //크롤링시 과제 기간 String을 LocalDataTime으로 변환하기 위한 DateTimeFormatter
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.setProperty("webdriver.chrome.driver", "C:/repo/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("headless");
        chromeOptions.addArguments("no-sandbox");
        webDriver = new ChromeDriver(chromeOptions);
        try {
            webDriver.get("https://cyber.inhatc.ac.kr/Main.do?cmd=viewHome");

            //팝업창 닫기
            String main = webDriver.getWindowHandle();
            for (String handle : webDriver.getWindowHandles()) {

                if (!handle.equals(main)) {

                    webDriver.switchTo().window(handle).close();

                }
            }
            //메인 페이지 이동
            webDriver.switchTo().window(main);
            //홈페이지 내부 팝업 창 닫기
            webDriver.findElement(By.cssSelector("#imagePop > div.inner-box > div.head-box > a:nth-child(1)")).click();

            //아이디와 비밀번호 입력하기
            WebElement id = webDriver.findElement(By.cssSelector("#id"));
            id.clear();
            id.sendKeys("id");
            WebElement pw = webDriver.findElement(By.cssSelector("#pw"));
            pw.clear();
            pw.sendKeys("password");

            //로그인 버튼 클릭
            WebElement loginBtn = webDriver.findElement(By.cssSelector("#loginForm > fieldset > p > a"));
            loginBtn.click();

            //로그인 될 때 까지 기다리기
            new WebDriverWait(webDriver, 10).until(ExpectedConditions.urlToBe("https://cyber.inhatc.ac.kr/Main.do?cmd=viewHome"));
            new WebDriverWait(webDriver, 10).until((ExpectedCondition<Boolean>) d ->
                    ((JavascriptExecutor) d).executeScript("return jQuery.active == 0").equals(Boolean.TRUE));

            //select내에 option 가져오기
            Select select = new Select(webDriver.findElement(By.cssSelector("#\\# > fieldset > select")));

            /**
             * courseIds:강의코드 목록
             * profNames: 교수명 목록
             * options:option 목록
             */
            List<String> courseIds = new ArrayList<>();
            ArrayList<String> profNames = new ArrayList<>();
            List<WebElement> options = select.getOptions();
            for (int i = 1; i < options.size(); i++) {
                String option = options.get(i).getAttribute("value");
                courseIds.add(option.split(",")[0]);
                profNames.add(option.split(",")[1]);
            }
            int i = 1; //인덱스 0 value 강의실 선택
            for (String courseId : courseIds) {
                    //강의실 과제 탭
                    webDriver.get("https://cyber.inhatc.ac.kr/Report.do?cmd=viewReportInfoPageList&boardInfoDTO.boardInfoGubun=report&courseDTO.courseId="+courseId+"&mainDTO.parentMenuId=menu_00104&mainDTO.menuId=menu_00063");
                    String profName = profNames.get(i-1);
                    String subName = webDriver.findElement(By.cssSelector("#headerContent > h1 > a")).getText().split(" ")[1];

                    //과제 목록들
                    WebElement element = webDriver.findElement(By.cssSelector("#listBox"));
                    List<WebElement> listContent = element.findElements(By.className("listContent"));
                    int j = 1;
                    for (WebElement webElement : listContent) {
                        if (webElement.findElement(By.className("f12")).getText().equals("[진행중]")) {
                            String subTitle = webElement.findElement(By.className("f14")).getText();
                            String subDate = webElement.findElement(By.cssSelector("#listBox > div:nth-child(" + j + ") > dl > dd:nth-child(3) > table > tbody > tr > td.first")).getText();
                            String subStart = subDate.substring(0, 16);
                            String subEnd = subDate.substring(19, subDate.length());
                            Subject subject = new Subject(profName, subName, subTitle, LocalDateTime.parse(subStart, dateTimeFormatter), LocalDateTime.parse(subEnd, dateTimeFormatter));
                            subjectService.join(subject);
                        } else {
                            break;
                        }
                        j++;
                    }
                i++;
            }
        } catch (Exception e) {
            log.info("에러 {}",e.getMessage());
        }finally {
            //브라우저 종료
            webDriver.quit();
        }
    }
}
