package com.qtpselenium.suiteB;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;

public class Create_Employer extends TestSuiteBase{
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
	public void create_employer(
			String Name,
			String State,
			String Address_1,
			String Email)throws InterruptedException, IOException, AWTException{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing Create_Employer");
		APP_LOGS.debug(Name +" -- "+State +" -- "+Address_1 +" -- "+Email);
		APP_LOGS.debug("Create_Employer");
		
		/*****This block will login user into the system****/
	 	try{
	 			openBrowser();
	 			login(config.getProperty("default_username"), config.getProperty("default_password"));
	 			Thread.sleep(10000);
	 			/******-END-----*/
		
	 			/**
	 			 * Go to Navigation
	 			 * Click on  Menu
	 			 * Select Employers
	 			 * */
	 			Navigation_file();
	 			wait_until_element_present_to_click("Navigation_bar_menu_employer");
	 			js_click("Navigation_bar_menu_employer");
	 			wait_until_element_present_to_click("Employer_New_button");
	 			js_click("Employer_New_button");
	 			write_input("Employer_name", Name+str+str+integer);
	 			write_input("Employer_name_state", State);
	 			write_input("Employer_name_Address1", Address_1);
	 			write_input("Employer_name_Email", Email);
	 			write_input("Employer_name_Zip", str);
	 			write_input("Employer_name_Address2", str+str);
	 			write_input("Employer_name_tel", str+integer);
	 			write_input("Employer_name_City", str);
	 			write_input("Employer_name_Fax", integer+str);
	 			write_input("Employer_name_Contact", str);
	 			write_input("Employer_name_notes", str);
	 			js_click("Employer_New_ok");
	 			
	 			
	 			
		
	 	}
	 	catch(Exception e){
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
public void reportTestResult() {
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
