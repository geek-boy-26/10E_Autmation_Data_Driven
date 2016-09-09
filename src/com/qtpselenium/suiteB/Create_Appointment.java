package com.qtpselenium.suiteB;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;




public class Create_Appointment extends TestSuiteBase{
	String runmodes[]=null;
	static int count=-1;
	 static boolean fail=false;
	static boolean skip=false;
	static boolean isTestPass=true;
	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip() {
			
				if(!TestUtil.isTestCaseRunnable(suiteBxls,this.getClass().getSimpleName())){
					APP_LOGS.debug("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
					throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
				}
	runmodes=TestUtil.getDataSetRunmodes(suiteBxls, this.getClass().getSimpleName());	

			}
	@Test(dataProvider="getTestData")
	public void create_Appointment(
								String col1,
								String col2,
								String col3,
								String col4
						  ) throws InterruptedException, IOException, AWTException{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing Create_Appointment");
		APP_LOGS.debug(col1 +" -- "+col2 +" -- "+col3 +" -- "+col4);
		
		
		/*****This block will login user into the system****/
	 	try
	 	{
		openBrowser();
		login(config.getProperty("default_username"), config.getProperty("default_password"));
		Thread.sleep(10000);
		/******-END-----*/
		
		wait_until_element_present("Right_click_slot");
		WebElement ele = driver.findElement(By.xpath(OR.getProperty("Right_click_slot")));
		Actions action = new Actions(driver);
		Thread.sleep(3000);
		action.click();
		action.contextClick(ele).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
		//action.moveToElement(ele);
		
		wait_until_element_present("Appointment_creation_patient_name");
		write_input("Appointment_creation_patient_name", "Aakar");
		js_click("Appointment_creation_patient_name_select");
		
					
		wait_until_element_present("Appointment_creation_patient_name_alert");
		boolean x=  driver.findElements(By.xpath(OR.getProperty("Appointment_creation_patient_name_alert"))).size() > 0;
		if(x==true)
		js_click("Appointment_creation_patient_name_alert");
		//wait_until_element_present("Appointment_creation_Visit_Type");
		
		write_input("Appointment_creation_Visit_Type", "ANN VISIT");
		driver.findElement(By.xpath(OR.getProperty("Appointment_creation_Visit_Type"))).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		write_input("Appointment_creation_Reason", RandomStringUtils.randomAlphabetic(10));
		write_input("Appointment_creation_Diagnosis", RandomStringUtils.randomAlphabetic(10));
		write_input("Appointment_creation_Billing_notes", RandomStringUtils.randomAlphabetic(10));
		write_input("Appointment_creation_General_notes", RandomStringUtils.randomAlphabetic(10));
		js_click("Appointment_creation_Encounter_appointment_screen");
		js_click("Appointment_creation_resourcename");
	
		driver.findElement(By.xpath(OR.getProperty("Appointment_creation_resourcename"))).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		Thread.sleep(2000);
		js_click("Appointment_creartion_encounter_facility");
		wait_until_element_present("Appointment_creartion_encounter_facility_All");
		driver.findElement(By.xpath(OR.getProperty("Appointment_creartion_encounter_facility"))).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		
		
		//click("Appointment_creartion_encounter_facility_All");
		Robot r = new Robot();
		
		
		wait_until_element_present_to_click("Appointment_creartion_encounter_button");
		click("Appointment_creartion_encounter_button");
		Select select = new Select(driver.findElement(By.xpath(OR.getProperty("Appointment_creartion_encounter_button"))));
		select.selectByValue("0");
		js_click("Appointment_creartion_encounter_button");
		
	/**Check the opening of calender by clicking on calender button*/

		js_click("Appointment_creation_calender_from");
		js_click("Appointment_creation_calender_to");
		r.keyPress(java.awt.event.KeyEvent.VK_ESCAPE);
		r.keyRelease(java.awt.event.KeyEvent.VK_ESCAPE);
		
	/**
	 * Diagnosis not working ICD-9 8175
	 * 
	 * */	
		
		/**
		 * Open case ellipse
		 *Open Case Type
		 *Select an encounter and check the button View logs, View, Print, Fax, View Appointment, 
		 * 
		 */
		 
		js_click("Appointment_creartion_case");
		js_click("Appointment_creartion_case_ok");
		
		js_click("Appointment_creation_enc_select_patient");
		
		js_click("Appointment_creation_view_logs");
		wait_until_element_present("Appointment_creation_view_logs_x_button");
		js_click("Appointment_creation_view_logs_x_button");
		js_click("Appointment_creation_view");
		js_click("Appointment_creation_view_print");
	
	
		r.delay(3000);

		r.keyPress(java.awt.event.KeyEvent.VK_ESCAPE);
		r.keyRelease(java.awt.event.KeyEvent.VK_ESCAPE);
		r.delay(3000);
		js_click("Appointment_creation_view_print_close");
		r.delay(3000);		
		js_click("Appointment_creation_view_print_dropdown");
		r.delay(3000);
		r.keyPress(java.awt.event.KeyEvent.VK_ESCAPE);
		r.keyRelease(java.awt.event.KeyEvent.VK_ESCAPE);
				
		js_click("Appointment_creation_enc_fax");
		write_input("Appointment_creation_enc_fax_input", RandomStringUtils.randomNumeric(10));
		wait_until_element_present_to_click("Appointment_creation_send_fax");
		js_click("Appointment_creation_send_fax");
		r.delay(3000);
		js_click("Appointment_creation_enc_close_window");
		
		
		
		/**
		 * Click on the Find button to check multiple appointment of patient
		 * */
		
		js_click("Appointment_creation_Find_open");
		js_click("Appointment_creation_Find_visit");
		driver.findElement(By.xpath(OR.getProperty("Appointment_creation_Find_visit"))).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
	
		js_click("Appointment_creation_Reason");
		write_input("Appointment_creation_Reason", "testing purpose");
		
		
		driver.findElement(By.xpath(OR.getProperty("Appointment_creation_Find_visit"))).sendKeys(Keys.TAB,str);
		js_click("Appointment_creation_Find_Facility");
		js_click("Appointment_creation_Find_Facility_all");
		js_click("Appointment_creation_Find_pro_res");
		js_click("Appointment_creation_Find_pro_res_all");
		js_click("Appointment_creation_Find_pro_res_all_rule");
		js_click("Appointment_creation_Find_pro_select");
		driver.findElement(By.xpath(OR.getProperty("Appointment_creation_Find_pro_select"))).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		
		String handle = driver.getWindowHandle();
		
		write_input("Appointment_creation_Find_package_name", str);
		js_click("Appointment_creation_Find_package_name_save");
		//action.keyDown(Keys.ESCAPE).perform();
	
		r.delay(1000);
		r.keyPress(java.awt.event.KeyEvent.VK_ESCAPE);
		r.keyRelease(java.awt.event.KeyEvent.VK_ESCAPE);
				
		driver.switchTo().window(handle);
		
		js_click("Appointment_creation_Find_search");
		Thread.sleep(3000);
		
	/**
	 * Below code is used when you have dynamic ID for the xpath , 
	 * Find the lenght of xpath and compare it with all the ID of the particular pae
	 * 
	 * Condition is left to validate if the pop up exists or not and then click on ok
	 * */
		
		
			click_dynamic_pop_up("button");
		
			wait_until_element_present_to_click("Appointment_creation_Find_Add_to_list");
			js_click("Appointment_creation_Find_Add_to_list");
			r.delay(2000);
			click_dynamic_pop_up("button");
			r.delay(3000);
			js_click("Appointment_creation_find_Multiple_Appt_wailist");
			js_click("Appointment_creation_Multiple_Appt_close");
			//	js_click("Appointment_creation_Find_info");
	//		js_click("Close_new_x");
	//		js_click("Close_Anyway");
		//		js_click("Appointment_creation_Multiple_Appt_close");
	/*		wait_until_element_present_to_click("Appointment_creation_info");
		click("Appointment_creation_info");
		click("Close_new_x");
		js_click("Close_Anyway");*/
		/*	Thread.sleep(2000);
		driver.findElement(By.xpath(OR.getProperty("Appointment_creation_Find_package_name_save"))).sendKeys(Keys.ESCAPE);
	*/	
			//dropdown_select("Appointment_creation_Find_pro_select", 1);
		/*WebElement reason = driver.findElement(By.xpath(OR.getProperty("Appointment_creation_Find_reason")));
		action.click();
		action.contextClick(reason).sendKeys(Keys.TAB);
		*/		
		//click("Appointment_creation_Find_select");
		//click("Appointment_creation_Find_info");
		//click("Appointment_creation_Facility");
			/***After checking all the buttons click on OK button ***/
	//	click("Appointment_creation_ok");
		/*driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//Thread.sleep(3000);
		if(driver.findElement(By.xpath(OR.getProperty("Appointment_creation_not_working_hours"))).isDisplayed())
		{
		String read = driver.findElement(By.xpath(OR.getProperty("Appointment_creation_not_working_hours"))).getText();
		System.out.println(read);
		}*/
	 	}
	 	catch(UnhandledAlertException alert)
	 	{
	 		ErrorUtil.addVerificationFailure(alert);
			APP_LOGS.debug("Alert not found");
			fail=true;
			softAssert.fail("Alert not found");
	 	}
	 	catch (Exception e) {
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
	public void reportTestResult()  {
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
