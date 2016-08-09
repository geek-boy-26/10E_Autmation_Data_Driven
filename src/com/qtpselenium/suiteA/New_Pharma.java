package com.qtpselenium.suiteA;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;

public class New_Pharma extends TestSuiteBase{
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
	public void new_Pharma(
			String Display_Name,
			String Email,
			String Store_Name,
			String Address,
			String Address2,
			String City,
			String State,
			String Phone,
			String Zip_code,
			String Fax
			
			) throws InterruptedException, IOException{
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing TestCase_A2");
		APP_LOGS.debug(Display_Name +" -- "+Email +" -- "+Store_Name+Address +" -- "+Address2 +" -- "+City+" -- "+State+" -- "+Phone+" -- "+Zip_code+" -- "+Fax  );	
		
	//
		openBrowser();
		login("sam", "password$1");
		Thread.sleep(8000);
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
		
		/***
		 * 
		 * Below code would open pharmacy and add the values into it
		 * 
		 */
		
		click("Pharma_bar");
		click("Pharma_add");
		click("Pharma_new");
		
		write_input("Display_Name", Display_Name+str);
		write_input("Email", Email);
		write_input("Store_Name", Store_Name);
		write_input("Address", Address);
		write_input("Address2", Address2);
		write_input("City", City);
		write_input("State", State);
		write_input("Phone", Phone);
		write_input("Zip_code", Zip_code);
		write_input("Fax", Fax);
		click("New_pharma_ok");
		click("New_pharma_ok_alert");
		
		/***
		 * 
		 * Below code will search the pharmacy enter in the pharma name column
		 */
	
		
		String name_pharma=driver.findElement(By.xpath(OR.getProperty("Display_Name"))).getAttribute("value");
		write_input("Pharma_name_Search", name_pharma);
		Thread.sleep(5000);
		/***
		 * Now clear the search and select the pharma from the list. (search pharma will not be selected as ID changes for every new)
		 * 
		 */
		
		driver.findElement(By.xpath(OR.getProperty("Pharma_name_Search"))).clear();
		click("Select_pharma");
		js_click("Pharma_final_ok");
		
		 js_click("new_pt_ok");
		
		}
		
	catch(Exception t)
	{
	
		ErrorUtil.addVerificationFailure(t);
		fail=true;
		capturescreenshot(this.getClass().getSimpleName()+"_"+count);
		Assert.fail("Unable to proceed ahead", t);
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
