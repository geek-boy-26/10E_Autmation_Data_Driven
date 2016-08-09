package com.qtpselenium.suiteA;

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

import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;

public class Alerts extends TestSuiteBase{
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
	public void alerts(
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
		
	//
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
		 * Click on Alert window and add detials
		 * 
		 * 
		 */
		
		
		click("Alert_button");
		click("Alert_button_settings");
		 for(int i=1;i<=3;i++)
		 {
		click("Alert_button_new");
		write_input("Alert_button_new_name", str+i);
		write_input("Alert_button_new_name_description", str+str);
		click("Alert_button_new_ok");
		Thread.sleep(3000);
		  }
		 
		 click("Alert_Clinical_Tab");
		 for(int i=1;i<=3;i++)
		 {
			 Thread.sleep(3000);
		click("Alert_button_new");
		write_input("Alert_button_new_name", str+i);
		write_input("Alert_button_new_name_description", str+str);
		click("Alert_button_new_ok");
		
		  }
		 
		 js_click("Alert_Both_Tab");
		 for(int i=1;i<=3;i++)
		 {
			 Thread.sleep(1000);
		click("Alert_button_new");
		write_input("Alert_button_new_name", str+i);
		write_input("Alert_button_new_name_description", str+str);
		click("Alert_button_new_ok");
		  }

		 js_click("Alert_config_ok");
		 js_click("Alert_timestamp");
		 String desc = RandomStringUtils.randomAlphanumeric(500);
		 write_input("Alert_timestamp_textarea", desc);
		 js_click("Alert_final_ok");
		 js_click("new_pt_ok");
		 
		} 
		
		catch(Exception e)
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
