package com.qtpselenium.base;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.monte.screenrecorder.ScreenRecorder;
//import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument.Config;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriver.Options;
//import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestRecorder;
import com.qtpselenium.util.Xls_Reader;


public class TestBase {
	public static Logger APP_LOGS=null;
	public static Properties config=null;
	public static Properties OR=null;
	public static Xls_Reader suiteXls=null;
	public static Xls_Reader suiteAxls=null;
	public static Xls_Reader suiteBxls=null;
	public static Xls_Reader suiteCxls=null;
	public static Xls_Reader Result_Xls =null;
	public static boolean isInitalized=false;
	public static boolean isBrowserOpened = false; //if exceute test in 1 browser
	public static boolean browser_alive=false;
	public static WebDriver driver;
	public static ATUTestRecorder recorder;
	public String str = RandomStringUtils.randomAlphabetic(5);
	public String integer = RandomStringUtils.randomNumeric(3);
	public static String ten_num = RandomStringUtils.randomNumeric(10);
	//For use of java script execution
	public JavascriptExecutor js=(JavascriptExecutor) driver;
	public static int  Screenshot_counter = 0;
	public static Robot r;
	
	// initializing the Tests
	public static void initialize() throws Exception{
		// logs
		if(!isInitalized){
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		// config
		APP_LOGS.debug("Loading Property files");
		config = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//qtpselenium//config/config.properties");
		config.load(ip);
			
		OR = new Properties();
		ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//qtpselenium//config/OR.properties");
		OR.load(ip);
		APP_LOGS.debug("Loaded Property files successfully");
		APP_LOGS.debug("Loading XLS Files");

		
		
		// xls file
		suiteAxls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//A suite.xlsx");
		suiteBxls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//B suite.xlsx");
		suiteCxls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//C suite.xlsx");
		suiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//Suite.xlsx");
		Result_Xls=  new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//Result.xlsx");
		APP_LOGS.debug("Loaded XLS Files successfully");
		isInitalized=true;
		
		}
		
		
		// selenium RC/ Webdriver
		
	}
	
		public void capturevideo(ITestResult result, String status)
		{
			String Method = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
			try {
				recorder = new ATUTestRecorder("D:\\Aakar\\10E_Selenium_automation\\10E_Autmation_Data_Driven\\ScriptVideos\\",Method+dateFormat.format(date),false);
				
			} catch (ATUTestRecorderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	
	//Assertions 
	/***
	 * Two kinds of assertions we are using here
	 * 1. Hard (Stop test immediately)
	 * 2. Soft (Continue further)
	 * **/
	
	public static Assertion  hardAssert = new Assertion();
	public static SoftAssert softAssert = new SoftAssert();
	

	 
	public static void openBrowser()  {
		// TODO Auto-generated method stub
		if(!isBrowserOpened)
		{
			browser_alive=true;
			System.out.println("browser open");
				if(config.getProperty("browserType").equals("Mozilla"))
				{
				driver = new FirefoxDriver();
				
				}
			else if(config.getProperty("browserType").equals("IE"))
				{
				driver = new InternetExplorerDriver();
				}
			else if(config.getProperty("browserType").equals("Chrome"))
				{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments(Arrays.asList("--test-type"));
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				driver = new ChromeDriver(capabilities);
				
				}
			driver.manage().window().maximize();
			isBrowserOpened=true;
			String wait_Time= config.getProperty("default_implicit");
			driver.manage().timeouts().implicitlyWait(Long.valueOf(wait_Time), TimeUnit.SECONDS);
			browser_alive=false;
			}
		
		else if (browser_alive=false)
		{
			System.out.println("browser crashed");
		}
			
}
	
	public void click_dynamic_pop_up(String tagname)
	{
		List<WebElement> el = driver.findElements(By.tagName(tagname));
		java.util.Iterator<WebElement> i = el.iterator();
		while(i.hasNext()) {
		    WebElement row = i.next();
		    String y = row.getAttribute("id");
		      int len = y.length();
		     if (len >35){
		      	driver.findElement(By.id(y)).click();
		      }
		 } 
	}
	
	
	
	
	
	//get the current method running name
	public static String get_current_method_name(){
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	

	//to check if any element present or not
		public boolean check_element_present(String xpath)
		{
			try
			{
				int count = driver.findElements(By.xpath(OR.getProperty(xpath))).size();
				Assert.assertTrue(count>0,"No Element Present");
			}
			catch(Throwable T)
			{
				ErrorUtil.addVerificationFailure(T);
				APP_LOGS.debug("No Element Present");
				//fail=true;  //for reporting error we need to use the try and catch block with verification failure to report error in excel file
				//return;  if you don't want to continue test ahead App_LOS.debug("Titles do not match")
				return false;
			}
			return true;
		}
	
		public static boolean login(String username, String password) throws InterruptedException
		{
			//selenium code for the specific user to login
			try
			{
				//System.out.println(isBrowserOpened+":"+"isBrowserOpened");
				if(isBrowserOpened==false)
				{
					openBrowser();
				}
			driver.get(config.getProperty("testSiteName"));
		
			WebElement user =driver.findElement(By.xpath(OR.getProperty("doctorID")));
			WebElement pass =driver.findElement(By.xpath(OR.getProperty("password")));
			user.sendKeys(username); 
			pass.sendKeys(password);
			Thread.sleep(2000);
			driver.findElement(By.xpath(OR.getProperty("Submitbutton"))).click();
				
			}
			catch(Throwable t)
			{
				
				ErrorUtil.addVerificationFailure(t);
				APP_LOGS.debug("Invalid login");
				return false;
			}
			return true;
		}
		
		
		public void dropdown_select(String xpath, int i)
		{
			try
			{
				APP_LOGS.debug("Selecting from drop down" +":"+xpath);
				List<WebElement> ele = driver.findElements(By.xpath(OR.getProperty(xpath)));
				ele.get(i).click();
			}
			catch(Exception e)
			{
				ErrorUtil.addVerificationFailure(e);
				softAssert.fail("No element found in dropdown");
				
			}
		}
		
		
		public static void click(String xpath)
		{
			WebElement ele1 = (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpath))));
			try
			{ 	
				APP_LOGS.debug("Clicking button"+":"+xpath );
				if(driver.findElements(By.xpath(OR.getProperty(xpath))).size() != 0)
				driver.findElement(By.xpath(OR.getProperty(xpath))).click();
						
			}
			catch(Exception e)
			{
				APP_LOGS.debug("element not found"+get_current_method_name()+xpath);
				softAssert.fail("element not found", e);
				
			}
		}
		
		public void dropdown(String xpath,int i)
		{
			try
			{
				APP_LOGS.debug("Selecting dropdown");
				WebElement ele1 = (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(xpath))));
				System.out.println(ele1.isDisplayed());
				WebElement ele = driver.findElement(By.id(OR.getProperty(xpath)));
				JavascriptExecutor exe = (JavascriptExecutor)driver;
				exe.executeScript("arguments[0].click();", ele);
				/*Select list = new Select(ele);
				list.getFirstSelectedOption().click();*/
				
				
			}
			catch(Exception e)
			{
				APP_LOGS.debug("Unable to find element");
				softAssert.fail("dropdown not selected");
				
				
			}
		}
		
		public static long Thread(long i) throws InterruptedException
		{
			APP_LOGS.debug("Executing thread"+i+get_current_method_name());
			Thread.sleep(i);
			return i;
			
		}
		
		public boolean checkElementPresence(String xpathKey){
			int count =driver.findElements(By.xpath(OR.getProperty(xpathKey))).size();
			
			try{
			Assert.assertTrue(count>0, "No element present");
			}catch(Throwable t){
				ErrorUtil.addVerificationFailure(t);			
				APP_LOGS.debug("No element present");
				return false;
			}
			return true;
		}
		public static void wait_until(String locator)
		{
			try{
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locator))));
		System.out.println(driver.findElement(By.xpath(OR.getProperty(locator))).getText());
			
			}
			catch(Exception r)
			{
				System.out.println("wait done"+ r);
			}
		}
		
		public static void expliciti_wait_click(String locator,int seconds)
		{
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			try
			{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(locator))));
			
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		
		public static void logout() throws InterruptedException
		{
			
			wait_until("profile");
			boolean v= driver.findElements( By.xpath(OR.getProperty("profile")) ).size() != 0;
			Thread.sleep(5000);
	try
	{
			if(v==true)
			{
			click("profile");
			//click log out button
			Thread(2000);
			click("logout");
			APP_LOGS.debug("Logout succesfully");
			Thread(5000);
			}
	}catch(Exception e)
	{
		ErrorUtil.addVerificationFailure(e);
		Assert.fail("Logout button not found"+":" +get_current_method_name());
	}
		}
		
		public void default_login(String username, String password) throws InterruptedException
		{
			login(config.getProperty("default_username"), config.getProperty("default_password"));
			wait_until("Continue");
			Thread(5000);
			click(OR.getProperty("Continue"));
		}
		
		public void input(String identifier,String data){
			try{
			if(identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(OR.getProperty(identifier))).sendKeys(data);
			else if(identifier.endsWith("_id"))
				driver.findElement(By.id(OR.getProperty(identifier))).sendKeys(data);
			else if(identifier.endsWith("_name"))
				driver.findElement(By.name(OR.getProperty(identifier))).sendKeys(data);
			}catch(NoSuchElementException e){
				Assert.fail("Element not found - "+identifier);
			}
		}
		
		
		public void write_input(String xpath,String data)
		{
			try
			{
				APP_LOGS.debug("Xpath"+":"+xpath+":"+"Data"+":"+data);
				driver.findElement(By.xpath(OR.getProperty(xpath))).sendKeys(data);
				
			}
			catch(Exception e)
			{
				APP_LOGS.debug("Unable to insert text into element"+":"+e);
				softAssert.fail("Text not written");
			}
			
		}
		
		public void patient_registartion_dropdown(String id)
		{
			try
			{
				APP_LOGS.debug("clicking on scroll down button of"+":"+id);
				Thread.sleep(2000);
				WebElement el = driver.findElement(By.id(id));
				el.click();
				el.sendKeys(Keys.ARROW_DOWN);
				el.sendKeys(Keys.ENTER);
		//		el.sendKeys(Keys.TAB);
				
			}
			catch(Exception e)
			{
				APP_LOGS.debug("Unable to scroll down "+ ":" + id);
				ErrorUtil.addVerificationFailure(e);
				softAssert.fail("ID not found");
				
			}
		}
		
		
		
		
		
		public  void capturescreenshot(String filename) throws IOException{
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\screenshots\\"+filename+".jpg"));
		    

		}
		
		// your own functions
				// getObjectByID(String id) //getObkect(OR property name)
public WebElement getObject(String xpathKey){
			
			try{
			WebElement x = driver.findElement(By.xpath(OR.getProperty(xpathKey)));
			return x;
			}catch(Throwable t){
				// report error
				ErrorUtil.addVerificationFailure(t);			
				APP_LOGS.debug("Cannot find object with key -- " +xpathKey+":" +get_current_method_name());
				return null;
			}
			
		}
		
public boolean verify_title(String xpath,String expected)
{
	
	try
	{
		WebElement ele = (new WebDriverWait(driver, 15)).until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(xpath))));
		String actual = driver.findElement(By.xpath(OR.getProperty(xpath))).getText();
		Assert.assertTrue(actual.contains(expected), "true");
		APP_LOGS.debug("Title Verified"+":"+"Actual"+":"+actual+":"+"Expected"+":"+expected);
	}
	catch(Throwable t)
	{
		ErrorUtil.addVerificationFailure(t);
		APP_LOGS.debug("Unable to verify title"+":" +get_current_method_name());
		softAssert.fail("Title not match");
		//Assert.fail("Title not matching");
		return false;
	}
	
	return true;
}

public void Checkbox(String xpath)
{
	try
	{
		APP_LOGS.debug("Checkbox selecting");
		WebElement checkbox = driver.findElement(By.xpath(OR.getProperty(xpath)));
		new WebDriverWait(driver, 35).until(ExpectedConditions.visibilityOf(checkbox));
		Select_The_Checkbox(checkbox);
		/*if(!driver.findElement(By.xpath(OR.getProperty(xpath))).isSelected())
		{
			System.out.println(driver.findElement(By.xpath(OR.getProperty(xpath))).getAttribute("value")+":"+"Value of Checkbox");
			driver.findElement(By.xpath(OR.getProperty(xpath))).click();
		}*/
	}
	catch(Exception e)
	{
		ErrorUtil.addVerificationFailure(e);
		APP_LOGS.debug("Unable to locate Checkbox"+":" +get_current_method_name());
		Assert.fail("Checkbox not located");
		
	}
}

public void get_value(String xpath)
{
	String str = driver.findElement(By.xpath(OR.getProperty(xpath))).getAttribute("value");
	System.out.println(str);
	APP_LOGS.debug("Value of "+":"+str);
	
}

public void Select_The_Checkbox(WebElement element) {
	try {
        if (element.isSelected()) {
           System.out.println("Checkbox: " + element + "is already selected");
        } else {
        	// Select the checkbox
            element.click();
        }
    } catch (Exception e) {
    	System.out.println("Unable to select the checkbox: " + element);
    }
	
}



public static void custom_alert()
{
	if (driver instanceof JavascriptExecutor) {
		((JavascriptExecutor) driver).executeScript("alert('test Case Execution done');");
	}
}




public void js_click(String locator)
{
	APP_LOGS.debug("Function called"+":" +get_current_method_name());
	/****
	 * Below is the syntax to check if the element is present on the DOM of a page and visible. 
	 * Visibility means that the element is not just displayed but also should also has a height and width that is greater than 0.** */
	
	try
	{ 			
		driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
		WebElement ele1 = (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(locator))));
		if(ele1.isDisplayed())
		{
		JavascriptExecutor exe = (JavascriptExecutor)driver;
		exe.executeScript("arguments[0].click();", ele1);
		
		}
	
	}
	catch(Exception e)
	{
		APP_LOGS.debug("element not found"+":" +get_current_method_name()+":"+locator);
		softAssert.fail("Element not found",e);
//		Assert.fail("element not found", e);
	}

}


public void js_click_id(String locator)
{
	APP_LOGS.debug("Function called"+":" +get_current_method_name());
	/****
	 * Below is the syntax to check if the element is present on the DOM of a page and visible. 
	 * Visibility means that the element is not just displayed but also should also has a height and width that is greater than 0.** */
	
	try
	{ 			
		driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
		WebElement ele1 = (new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(locator))));
		if(ele1.isDisplayed())
		{
		JavascriptExecutor exe = (JavascriptExecutor)driver;
		exe.executeScript("arguments[0].click();", ele1);
		
		}
	
	}
	catch(Exception e)
	{
		APP_LOGS.debug("element not found"+get_current_method_name());
		softAssert.fail("Element not found",e);
//		Assert.fail("element not found", e);
	}

}


public void wait_until_element_present(String element)
{
	
	try
	{
		APP_LOGS.debug("Waiting for Element to visible"+":"+element);
		WebElement ele1 = (new WebDriverWait(driver, 15)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(element))));
		if(ele1.isDisplayed()==true)
		System.out.println(ele1.isDisplayed()+":"+element);
	}
	catch(Exception e)
	{
		APP_LOGS.debug("Element unable to locate"+ ":"+get_current_method_name());
		softAssert.fail("Element not found"+":"+element);
		
	}
	}

public void wait_until_element_present_to_click(String element)
{
	
	try
	{
		APP_LOGS.debug("Waiting for Element to visible");
			WebElement ele1 = (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(element))));
			
	}
	catch(Exception e)
	{
		APP_LOGS.debug("Element unable to locate"+ ":"+get_current_method_name()+":"+element);
		softAssert.fail("Element not present");
		//Assert.fail(e.getMessage());
		
	}
	}

//impicit wait
public void implicit_wait(int seconds)
{
	driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
}


		//close browser and re open again
		public void closeBrowser()
		{
			try
			{
			APP_LOGS.debug("close browser function called"+":" +get_current_method_name());
			isBrowserOpened=false;
			driver.quit();
			}
			catch(Exception e)
			{
				System.out.println("Exception "+":"+e.getMessage());
			}
			
		}
		
		
public void accept_alert()
{
	String alertText = "";
	Alert alert = driver.switchTo().alert();
	alertText = alert.getText();
	alert.accept();
	System.out.println(alertText);
	
}

public void Navigation_file()
{
		wait_until_element_present_to_click("Navigation_bar");
		js_click("Navigation_bar");
		wait_until_element_present_to_click("Navigation_bar_menu");
		js_click("Navigation_bar_menu");
}

public String random_mail(String mail)
{
	
	 int ran;
	    ran = 100 + (int)(Math.random() * ((10000 - 100) + 1));
	  String  mail_id = mail + ran + "@"+str+integer+".com";
	  return mail_id;
}

public static void hit_escape() throws AWTException
{
		Robot r1 = new Robot();
		r1.keyPress(KeyEvent.VK_ESCAPE);
		r1.keyRelease(KeyEvent.VK_ESCAPE);
}

public static void select(String xpath, int i)
{
	Select obj = new Select(driver.findElement(By.xpath(OR.getProperty(xpath))));
		obj.selectByIndex(i);
}


		//close browser and not reopen
		public void quitbrowser()
		{
			APP_LOGS.debug("Browser won't re open again"+":" +get_current_method_name());
			driver.quit();
		}
		
	
		
		
		
		
}





