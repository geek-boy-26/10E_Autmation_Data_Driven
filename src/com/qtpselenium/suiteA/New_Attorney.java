package com.qtpselenium.suiteA;

import java.io.IOException;

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

public class New_Attorney extends TestSuiteBase{
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
	public void new_Attorney(
			String First_Name,
			String Last_Name,
			String Address_1,
			String Address_2,
			String Office,
			String City,
			String State,
			String Zip_Code,
			String Tel,
			String Fax,
			String Contact_Details,
			String Contact_info
			) throws InterruptedException, IOException{
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing TestCase_A2");
		APP_LOGS.debug(First_Name +" -- "+Last_Name+" -- "+Address_1+" -- "+Address_2+" -- "+Office+" -- "+City+" -- "+State+" -- "+Zip_Code+" -- "+Tel+" -- "+Fax+" -- "+Contact_Details+" -- "+Contact_info);	
		
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
		
			click("Attorney_bar");
			js_click("Attorney_add");
			click("Attorney_new");
			
			
			write_input("First_name_Attorney", First_Name+str);
			write_input("Last_name_Attorney", Last_Name);
			write_input("Office_Attorney", Office);
			write_input("Add_1_Attorney", Address_1);
			write_input("Add_2_Attorney", Address_2);
			write_input("City_Attorney", City);
			write_input("State_Attorney", State);
			write_input("Zip_code_Attorney", Zip_Code);
			write_input("Tel_Attorney", Tel);
			write_input("Fax_Attorney", Fax);
			write_input("Contact_Details_Attorney", Contact_Details);
			write_input("Contact_info_Attorney", Contact_info);
			
			String random = RandomStringUtils.randomAlphanumeric(125);
			write_input("Notes_Attorney", random);
			js_click("Attorney_ok");
			
			String name_Attorney=driver.findElement(By.xpath(OR.getProperty("First_name_Attorney"))).getAttribute("value");
			write_input("Attorney_search", name_Attorney);
			Thread.sleep(5000);
			/***
			 * Now clear the search and select the Attorney from the list. (search Attorney will not be selected as ID changes for every new)
			 * 
			 */
			
			driver.findElement(By.xpath(OR.getProperty("Attorney_search"))).clear();
			click("Select_Attorney");
			js_click("Attorney_final_ok");
			
			 js_click("new_pt_ok");
			
			
			
		}
		catch(Exception t)
		{
			ErrorUtil.addVerificationFailure(t);
			fail=true;
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
	
		closeBrowser();
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteAxls, this.getClass().getSimpleName()) ;
	}
}
