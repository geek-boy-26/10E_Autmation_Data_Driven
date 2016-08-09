package com.qtpselenium.suiteA;
/*
 * This test is used to check patient look up window
 * Buttons, drop downs and search functionality 
 * 
 * 
 * 
 */
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.taskdefs.Sync.MyCopy;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;
import com.sun.jna.Function;


public class New_Patient extends TestSuiteBase{
	String runmodes[]=null;
	static int count=-1;
	 static boolean fail=false;
	static boolean skip=false;
	static boolean isTestPass=true;
	
	// Runmode of test case in a suite
		@BeforeTest
		public void checkTestSkip(){
			
			if(!TestUtil.isTestCaseRunnable(suiteAxls,this.getClass().getSimpleName())){
				APP_LOGS.debug("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
				throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
			}
			runmodes=TestUtil.getDataSetRunmodes(suiteAxls, this.getClass().getSimpleName());
		}
	

			
	
	@Test(dataProvider="getTestData")
	public void new_Patient(
			String Last_name,
			String First_name,
			String Date_of_birth,
			String Previous_Name,
			String Address_Line_1,
			String Address_Line_2,
			String SSN,
			String Home_Phone,
			String Email,
			String City,
			String Sex,
			String Cell_no,
			String Work_Phone,
			String Ext,
			String MI,
			String Country
			
			
			
			) throws InterruptedException{
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing TestCase_A2");
		APP_LOGS.debug(Last_name +" -- "+First_name +" -- "+Date_of_birth);	
		try
		{
			
		/*****This block will login user into the system****/
		 	
		openBrowser();
		login(config.getProperty("default_username"), config.getProperty("default_password"));
		Thread.sleep(8000);

		/******-END-----*/
		
		/****-Open patient look up module--***///
		wait_until_element_present_to_click("patient_button");
		js_click("patient_button");
		wait_until_element_present("lookup_window");
		verify_title("lookup_window", "Patient Lookup");
		wait_until_element_present_to_click("new");
		js_click("new");
		verify_title("new_patient_title", "New Patient Information");
		write_input("First_name", First_name+str);
		write_input("Last_name", Last_name+str);
		write_input("DOB", Date_of_birth);
		click("DOB_label");
		write_input("previous_name",Previous_Name);
		write_input("Add_1", Address_Line_1);
		write_input("Add_2", Address_Line_2);
		write_input("SSN", SSN+integer);
		write_input("Home_phone", Home_Phone);
		write_input("Email", Email);
		write_input("City", City);
		click("Sex_click");
	
		/**
		 * The below function would take into consideration the Gender selection as 
		 * per the data in excel
		 * 
		 **/
			
	WebElement ex = driver.findElement(By.xpath(OR.getProperty("Sex_click")));
	if(Sex.equalsIgnoreCase("Male"))
		{
			
			ex.sendKeys(Keys.ARROW_DOWN);
			ex.sendKeys(Keys.ENTER);
		}
		
		else if(Sex.equalsIgnoreCase("Female"))
		{
			ex.sendKeys(Keys.ARROW_DOWN);
			ex.sendKeys(Keys.ARROW_DOWN);
			ex.sendKeys(Keys.ENTER);
		}
		else if(Sex.equalsIgnoreCase(""))
		{
			ex.sendKeys(Keys.ARROW_DOWN);
			ex.sendKeys(Keys.ARROW_DOWN);
			ex.sendKeys(Keys.ARROW_DOWN);
			ex.sendKeys(Keys.ENTER);
		}
		
		
		/****
		 * Below code would add work, Ext, Cell and Country
		 * 
		 */
			
	write_input("Cell_No", Cell_no);
	write_input("Work_phone", Work_Phone);
	write_input("Ext", Ext);
	write_input("MI", MI);
	write_input("Country", Country);
	js_click("dropdown");
	dropdown_select("PCP_1",0);
	js_click("dropdown_rcp");
	dropdown_select("Referring_provider", 0);
	js_click("dropwdown_rendering");
	dropdown_select("rendering_provider", 0);
//	patient_registartion_dropdown("ptmaritalstatus");
	write_input("Marital_status_x", "Married");
	write_input("Patient_lang", "English");
	write_input("Signature_date", "05/03/1970"); //mm//dd//yyyy
	write_input("Ethnicity", "Refused to Report");
	write_input("Characteristic", "STREET");
	write_input("Birth_order", "1");
	js_click("Signature_date_icon");
	write_input("Gestational_age", "25");
	driver.findElement(By.xpath(OR.getProperty("Signature_date"))).sendKeys(Keys.ESCAPE);
	write_input("Prefix", "Mr.");
	write_input("Suffix", "II");
	click("Select_resp_party");
	click("Select_resp_party_ok");
	 /*Alert alert=driver.switchTo().alert();
	System.out.println(alert.getText());*/
	 js_click("new_pt_ok");
	 
	 custom_alert();
		}//try block
		
		catch(ElementNotFoundException e)
		{
			ErrorUtil.addVerificationFailure(e);
			APP_LOGS.debug("Element not found");
			fail=true;
			softAssert.fail("Element not found");
		}
		catch(Exception i)
		{
			System.out.println("Exception"+i);
		}
	
	}
	
	
	@AfterMethod
	public void reportDataSetResult(){
		if(skip)
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteAxls, this.getClass().getSimpleName(), count+2, "PASS");
		
		skip=false;
		fail=false;
		

	}
	
	
	
	@AfterTest
	public void reportTestResult(){
		if(isTestPass)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL");
	
	
	}
	
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteAxls, this.getClass().getSimpleName()) ;
	}
}
