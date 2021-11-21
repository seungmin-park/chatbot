package inhatc.chatbot.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumExample {

    private WebDriver webDriver;

    private String url2 = "https://cyber.inhatc.ac.kr/Study.do?cmd=viewLearnerCourseList&boardInfoDTO.boardInfoGubun=jungcourse";
    public static String TEST_URL = "https://cyber.inhatc.ac.kr/User.do?cmd=loginUser";

    public void ttt() {
        System.setProperty("webdriver.chrome.driver", "C:/repo/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        webDriver = new ChromeDriver();
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
        //이상한 페이지들 닫기
        webDriver.findElement(By.cssSelector("body > div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-draggable > div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix.ui-draggable-handle > button")).click();
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
        
        //정규교과목 클릭하여 이동
        webDriver.findElement(By.cssSelector("#mainContent > form > div.conFirst > div.center_box > ul > li:nth-child(1) > a")).click();
        System.out.println(webDriver.getPageSource());
        
        //브라우저 종료
        webDriver.quit();
    }
}
