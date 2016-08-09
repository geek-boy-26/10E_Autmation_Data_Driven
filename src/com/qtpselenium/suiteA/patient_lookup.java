package com.qtpselenium.suiteA;
/*
 * This test is used to check patient look up window
 * Buttons, drop downs and search functionality 
 * 
 * 
 * 
 */
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;
import com.sun.jna.Function;


public class patient_lookup extends TestSuiteBase{
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
	public void Patient_lookup(
			String col1,
			String col2,
			String col3
			) throws InterruptedException{
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing TestCase_A2");
		APP_LOGS.debug(col1 +" -- "+col2 +" -- "+col3 );	
		try
		{

			/*****This block will login user into the system****/

			openBrowser();
			login(config.getProperty("default_username"), config.getProperty("default_password"));
			Thread.sleep(8000);
			/******-END-----*/

			/****-Open patient look up module--***///
			//wait_until_element_present("welcome_link");
			wait_until_element_present_to_click("patient_button");
			js_click("patient_button");
			wait_until_element_present("lookup_window");
			verify_title("lookup_window", "Patient Lookup");
			wait_until_element_present_to_click("new_copy");
			js_click("new_copy");
			wait_until_element_present("select_patient");
			verify_title("select_patient", "Please select a patient and try again !");
			js_click("pop_ok");		
			wait_until_element_present_to_click("new");
			js_click("new");
			verify_title("new_patient_title", "New Patient Information");
			js_click("Close_new_x");
			js_click("drop_down_new");
			js_click("delete");
			verify_title("select_patient", "Please select a patient and try again !");
			js_click("pop_ok");
			js_click("settings");
			js_click("dropdown_facilty");
			js_click("dropdown_facilty");
			js_click("dropdown_facilty_ok");
			js_click("patient_demographics");
			verify_title("select_patient", "Please select a patient and try again !");
			js_click("pop_ok");
			js_click("Lookup_ok");
			verify_title("select_patient", "Please select a patient and try again !");
			js_click("pop_ok");
			js_click("lookup_Cancel");
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
