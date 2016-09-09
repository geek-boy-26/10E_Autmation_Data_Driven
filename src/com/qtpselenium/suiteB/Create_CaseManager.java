package com.qtpselenium.suiteB;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;
import com.qtpselenium.util.Xls_Reader;

public class Create_CaseManager extends TestSuiteBase{
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
	public void create_CaseManager(
			String col1,
			String col2,
			String col3,
			String col4)throws InterruptedException, IOException, AWTException{
		// test the runmode of current dataset
				count++;
				if(!runmodes[count].equalsIgnoreCase("Y")){
					skip=true;
					throw new SkipException("Runmode for test set data set to no "+count);
				}
				
				
				APP_LOGS.debug(" Executing create_CaseManager");
				APP_LOGS.debug(col1 +" -- "+col2 +" -- "+col3);
				APP_LOGS.debug("create_CaseManager");
		
				
				
				try{
		 			openBrowser();
		 			login(config.getProperty("default_username"), config.getProperty("default_password"));
		 			Thread.sleep(10000);
		 			/******-END-----*/
			
		 			/**
		 			 * Go to Navigation
		 			 * Click on  Menu
		 			 * Select Case Manager
		 			 * */
		 			Navigation_file();
		 			wait_until_element_present("navigation_bar_case_manager");
		 			js_click("navigation_bar_case_manager");
		 			wait_until_element_present_to_click("Case_Manager_new");
		 			js_click("Case_Manager_new");
		 			write_input("Case_Manager_lname", col1+str);
		 			write_input("Case_Manager_fname", col2);
		 			write_input("Case_Manager_date", "08/03/1990");
		 			hit_escape();
		 			write_input("Case_Manager_Add1", str);
		 			write_input("Case_Manager_Add2", str+str+col3);
		 			write_input("Case_Manager_Zip", "5864");
		 			write_input("Case_Manager_Tel", ten_num); 
		 			write_input("Case_Manager_Fax", ten_num);
		 			write_input("Case_Manager_Email", random_mail("casemanager"));
		 			js_click("Case_Manager_OK");
		 			
		 			
		 			
				}
				catch(Exception e)
				{
					ErrorUtil.addVerificationFailure(e);
					APP_LOGS.debug("Element not found"+e);
					suiteBxls.setCellData(this.getClass().getSimpleName(), "Error", count+2, e.getMessage().toString());
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
