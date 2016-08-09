package test;

import java.util.Arrays;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class NewTest {
	
	public static WebDriver driver;
  @Test
  public void f() {
	  	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
	  	System.setProperty("webdriver.chrome.logfile", "D:\\chromedriver.log");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments(Arrays.asList("--test-type"));
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
		 capabilities.setCapability("chrome.switches", "--verbose");
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		driver = new ChromeDriver(capabilities);	
		driver.get("http://www.worldbank.org");
	Dimension	defaultDim=driver.manage().window().getSize();
		System.out.println(defaultDim);
  }
  

}
