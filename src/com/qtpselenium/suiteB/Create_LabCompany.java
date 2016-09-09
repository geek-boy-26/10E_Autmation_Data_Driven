package com.qtpselenium.suiteB;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;

public class Create_LabCompany extends TestSuiteBase{
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
	public void create_LabCompany(
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
				APP_LOGS.debug(" Executing create_LabCompany");
				APP_LOGS.debug(col1 +" -- "+col2 +" -- "+col3);
				APP_LOGS.debug("create_LabCompany");
				
				
				try{
		 			openBrowser();
		 			login(config.getProperty("default_username"), config.getProperty("default_password"));
		 			Thread.sleep(10000);
		 			/******-END-----*/
			
		 			/**
		 			 * Go to Navigation
		 			 * Click on  Menu
		 			 * Select Lab Company 
		 			 * */
		 			Navigation_file();
		 			wait_until_element_present_to_click("Navigation_bar_menu_Lab_Company");
		 			js_click("Navigation_bar_menu_Lab_Company");
		 			wait_until_element_present_to_click("Lab_Company_new");
		 			js_click("Lab_Company_new");
		 		/**
		 		 * Fill the details of the Lab Company 
		 		 * 
		 		 */
		 			write_input("Lab_Company_name", str);
		 			String name = get_name("Lab_Company_name");
		 			select("Lab_Company_Type_",1);
		 			write_input("Lab_Company_Add1", str);
		 			write_input("Lab_Company_Add2", str);
		 			write_input("Lab_Company_city", str);
		 			write_input("Lab_Company_zip", str);
		 			select("Lab_Company_state", 2);
		 			write_input("Lab_Company_phone", ten_num);
		 			write_input("Lab_Company_fax", str);
		 			Select obj = new Select(driver.findElement(By.xpath(OR.getProperty("Lab_Company_InHouse"))));
		 			obj.selectByValue("string:Y");
		 			write_input("Lab_Company_email", str);
		 			js_click("Lab_Company_Conf_lables");
		 			js_click("Lab_Company_ok");
		 			write_input("Lab_Company_search", name);
		 				
		 			
		 			
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
