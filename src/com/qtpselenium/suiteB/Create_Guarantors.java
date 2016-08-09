package com.qtpselenium.suiteB;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import atu.testrecorder.exceptions.ATUTestRecorderException;

import com.qtpselenium.base.TestBase;
import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;

public class Create_Guarantors extends TestSuiteBase{
	String runmodes[]=null;
	static int count=-1;
	 static boolean fail=false;
	static boolean skip=false;
	static boolean isTestPass=true;
	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip(){
				
				if(!TestUtil.isTestCaseRunnable(suiteBxls,this.getClass().getSimpleName())){
					APP_LOGS.debug("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
					throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
				}
				
				runmodes=TestUtil.getDataSetRunmodes(suiteBxls, this.getClass().getSimpleName());	
			}
				
	@Test(dataProvider="getTestData")
	public void Guarantors(
			String col1,
			String col2,
			String col3
			)throws InterruptedException, IOException, AWTException{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing TestCase_B1");
		APP_LOGS.debug(col1 +" -- "+col2 +" -- "+col3);
		APP_LOGS.debug("TestCase_B2");
		
		
		try{
 			openBrowser();
 			login(config.getProperty("default_username"), config.getProperty("default_password"));
 			Thread.sleep(10000);
 			/******-END-----*/
	
 			/**
 			 * Go to Navigation
 			 * Click on  Menu
 			 * Select Gaurantors
 			 * */
 			Navigation_file();
 			wait_until_element_present_to_click("Navigation_bar_menu_Gaurantor");
 			js_click("Navigation_bar_menu_Gaurantor");
 			js_click("Gaurantor_new");
 			wait_until_element_present("Gaurantor_type");
 			Select obj = new Select(driver.findElement(By.xpath(OR.getProperty("Gaurantor_type"))));
 			obj.selectByIndex(1);
 			write_input("Gaurantor_name_last", str+col1);
 			write_input("Gaurantor_MI", str+col2);
 			write_input("Gaurantor_First", str+col3);
 			write_input("Gaurantor_date", "12/10/2020");
 			Robot r1 = new Robot();
 			r1.keyPress(KeyEvent.VK_ESCAPE);
 			r1.keyRelease(KeyEvent.VK_ESCAPE);
 			write_input("Gaurantor_email", random_mail(str));
 			write_input("Gaurantor_SSN", ten_num);
 			write_input("Gaurantor_tel", ten_num);
 			select("Gaurantor_sex", 2);
 			js_click("_Address_tab");
 			write_input("Gaurantor_emp_add1", str);
 			write_input("Gaurantor_emp_add2", str);
 			write_input("Gaurantor_emp_city", str);
 			write_input("Gaurantor_emp_state", "AK");
 			write_input("Gaurantor_emp_zip", str);
 			js_click("Employment_tab");
 			write_input("Employment_tab_state", "AK");
 			write_input("Employment_tab_Workphone", ten_num);
 			
 			//js_click("Guarantor_ok");
 			
		}
		catch(Exception e)
		{
			ErrorUtil.addVerificationFailure(e);
			APP_LOGS.debug("Element not found"+e);
			fail=true;
			softAssert.fail("Element not found");
		}
	}
	

	
	@AfterMethod
	public void reportDataSetResult(){
	
	if(skip)
		TestUtil.reportDataSetResult(suiteBxls, this.getClass().getSimpleName(), count+2, "SKIP");
	else if(fail){
		isTestPass=false;
		TestUtil.reportDataSetResult(suiteBxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
	else
		TestUtil.reportDataSetResult(suiteBxls, this.getClass().getSimpleName(), count+2, "PASS");
		
	skip=false;
	fail=false;
	

}


@AfterTest
public void reportTestResult() throws ATUTestRecorderException{
	if(isTestPass)
		TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "PASS");
		
	else
		TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "FAIL");

}



@DataProvider
public Object[][] getTestData(){
	return TestUtil.getData(suiteBxls, this.getClass().getSimpleName()) ;
}

}
