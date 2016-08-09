package com.qtpselenium.suiteA;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;

public class Additional_information extends TestSuiteBase{
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
	public void additional_information(
			String Address_1,
			String Address_2,
			String City,
			String State,
			String Zip,
			String MemberID,
			String MRN,
			String Notes
			
			) throws InterruptedException, IOException{
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing TestCase_A2");
		APP_LOGS.debug(Address_1 +" -- "+Address_2 +" -- "+City+" -- "+State+" -- "+Zip+" -- "+MemberID+" -- "+MRN+" -- "+Notes );	
		
		/*****This block will login user into the system****/
	 	
		openBrowser();
		login(config.getProperty("default_username"), config.getProperty("default_password"));
		Thread.sleep(8000);
		/******-END-----*/
		
		try
		{
		wait_until_element_present_to_click("patient_button");
		js_click("patient_button");
		wait_until_element_present("lookup_window");
		verify_title("lookup_window", "Patient Lookup");
		wait_until_element_present_to_click("new");
		js_click("new");
		String str = RandomStringUtils.randomAlphabetic(5);
		verify_title("new_patient_title", "New Patient Information");
		write_input("First_name", "Aakar11"+str);
		write_input("Last_name", "Gupte");
		write_input("DOB", "05/02/1990");
		click("DOB_label");
		
		
		js_click("Sex_click");
		String Sex= "Male";
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
		
		/**
		 * Click on Additional info and fill the details
		 * 
		 * **/
			
			click("Additional_info_button");
			write_input("Address_line_1_Additional_info", Address_1);
			write_input("Address_line_2_Additional_info", Address_2);
			write_input("city_Additional_info", City);
			write_input("state_Additional_info", State);
			write_input("zip_Additional_info", Zip);
			write_input("memberId_Additional_info", MemberID);
			write_input("MRN_Additional_info", MRN);
			write_input("notes_Additional_info", Notes+str);
			click("Additional_info_home");
			write_input("Additional_info_home_dropdown", "Brief");
			js_click("Additional_info_cell");
			write_input("Additional_info_cell_dropdown", "Brief");
			write_input("Additional_info_VFC", "Not VFC Eligible");
			js_click("Additional_info_Residene_click");
		   click("Additional_info_Residene_click_select");
		   js_click("Additional_info_fix_date_icon");
		   click("Additional_info_fix_date");
		   click("Additional_info_plan_type");
		   click("Additional_info_plan_type_select");
		   write_input("Additional_info_notes_above", str);
		   click("Additional_info_custom");
		   click("Additional_info_custom_Add");
		   write_input("Additional_info_custom_Add_name",str);
		   write_input("Additional_info_custom_Type", "Structured Text");
		   click("Additional_info_custom_ok");
		   click("Additional_info_custom_ok_last");
		   
		   
		   /********
		    * 
		    * Now click on the patient ok button to compelete the Additonal info
		    * Checkboxes on the right hand side is remaining
		    * 
		    * 
		    */
		   /***Patient Docs***/
		   click("Additional_info_Patient_Docs");
		   click("Additional_info_Patient_Docs_Add");
		   
		   Runtime.getRuntime().exec((System.getProperty("user.dir")+"/upload_doc.exe"));
		
		 
		   
		//   closeBrowser();
		   /****
		    * Due to error on the local machine, this script is paused and the above code is working fine
		    * 
		    * **/
		   
		   
		   //click("Addtional_info_ok");
		//   write_input("Additional_info_Deceased_date", "04/12/2016");
		
		
		} 
		catch(ElementNotFoundException e)
		{
			ErrorUtil.addVerificationFailure(e);
			APP_LOGS.debug("Element not found");
			fail=true;
			softAssert.fail("Element not found");
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
	
		closeBrowser();
	
	}
	
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteAxls, this.getClass().getSimpleName()) ;
	}
}