import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Assignment1 {
    public static void main(String[] args) {
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\drivers\\chromedriver.exe");

        // Initialize ChromeDriver & Launch the browser
        WebDriver driver = new ChromeDriver();
                
        driver.get("https://flipkart.com/");

        // Find all anchor elements on the page
        List<WebElement> linkElements = driver.findElements(By.tagName("a"));

        // 1.1 Using forEach loop
        System.out.println("Links using forEach loop:");
        for (WebElement element : linkElements) {
            System.out.println(element.getAttribute("href"));
        }

        // 1.2 Using Stream
        System.out.println("\nLinks using Stream:");
        linkElements.stream()
                .map(element -> element.getAttribute("href"))
                .filter(href -> href != null && !href.isEmpty())
                .forEach(System.out::println);

        // 1.3 Using Parallel Stream
        System.out.println("\nLinks using Parallel Stream:");
        linkElements.parallelStream()
                .map(element -> element.getAttribute("href"))
                .filter(href -> href != null && !href.isEmpty())
                .forEach(System.out::println);

        // 1.4 Using Lambda Expression
        System.out.println("\nLinks using Lambda Expression:");
        AtomicInteger counter = new AtomicInteger(1);
        linkElements.forEach(element -> {
            String href = element.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                System.out.println("Link " + counter.getAndIncrement() + ": " + href);
            }
        });

        // Close the browser
        driver.quit();
    }
}