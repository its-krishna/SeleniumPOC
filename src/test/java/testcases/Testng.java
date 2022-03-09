package testcases;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class Testng {
	WebDriver driver;
  @Test (priority = 1)
  public void verifyheader() {
	  String expectedHeading = "The Selenium Browser Automation Project";
	    String heading = driver.findElement(By.xpath("//div[@class='td-content']/h1")).getText();  
	    Assert.assertEquals(expectedHeading, heading);
  }
  
  @Test (priority = 2)
  public void verifylinks() {
	  List<WebElement> links = driver.findElements(By.tagName("a"));
	  for(int i=0;i<links.size();i++)
      {
          WebElement El= links.get(i);
          String url= El.getAttribute("href");
          try
          {
              
    			URL urllink = new URL(url);

              //Now we will be creating url connection and getting the response code
              HttpURLConnection httpURLConnect=(HttpURLConnection)urllink.openConnection();
              httpURLConnect.setConnectTimeout(5000);
              httpURLConnect.connect();
              if(httpURLConnect.getResponseCode()>=400)
              {
            	  Assert.assertTrue(false,"broken links");
              	
              }    
              //Fetching and Printing the response code obtained
              else{
            	  Assert.assertTrue(true,"no broken links");
              }
          }catch (Exception e) {
          }
          
      }
  }
 
  @BeforeTest
  @Parameters("browser")
  
  public void beforeTest(String browser) {
	  
	  if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "/Users/psb/Documents/eclipse-workspace/geckodriver"); 
//			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(); 
		} else {
			System.out.println("incorrect browser name");
			}
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/documentation/");
		
  }
  @AfterTest
  public void afterTest()
  {
	driver.quit();  
  }
  }

