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
import org.testng.asserts.SoftAssert;

import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;

public class New_Insurance extends TestSuiteBase{
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
	public void new_Insurance(
			String Ins_Name,
			String Address_1,
			String Address_2,
			String City,
			String State,
			String payerID,
			String MedigapID,
			String payment,
			String Ins_type,
			String email
			
			) throws InterruptedException, IOException{
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		APP_LOGS.debug(" Executing TestCase_A2");
		APP_LOGS.debug(Ins_Name +" -- "+Address_1 +" -- "+Address_2 +City +" -- "+State +" -- "+payerID+" -- "+MedigapID+" -- "+payment+" -- "+Ins_type +" -- "+email);	
		
	//	
		
		openBrowser();
		login(config.getProperty("default_username"), config.getProperty("default_password"));
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
	
		click("Add_insurance");
		js_click("load_insurance");
	click("New_Lookup");
	
	
	/*	 *  * Add Insurance detials ***/
	write_input("Insurance_name", Ins_Name+str);
	write_input("Address_1", Address_1);
	write_input("Address_2", Address_2);
	write_input("City_insurance", City);
	write_input("State_insurance", State);
	write_input("Medigap_id", MedigapID);
	write_input("Payer_id", payerID);
	click("Payment");
	js_click("Type_insurance_select");
	click("Type_insurance_second");
	js_click("Type_insurance_select_table");
	write_input("Eligibility_payerID", "dummy");
	write_input("Liability_code", "132456");
	write_input("website", "www.eclinicalworks.com");
	write_input("country_insurance", "India");
write_input("Telephone_Insurance", "fdsfdsfsdfsd");
write_input("Zip_Insurance", "132dsf");
write_input("Alt_telephone_insurance", "dsfdsfsdf");	
write_input("fax_insurance", "dfdsfds");
	write_input("email", email);
	write_input("Fee_insurance", "Master Fee Schedule");
	
	/**
	 * 
	 * Below code will add the Associate Insurance group
	 * 
	 */
	
	click("Associate_ins_groups");
	click("Associate_ins_groups_add");
	click("Associate_Select");
	js_click("Associate_Select_ok");
	js_click("Associate_group_ok");
	
	
	/**
	 * 
	 * Below code will Add Insurance class in the Insurance
	 * 
	 */
	
	click("Isnurance_class_elipse");
	click("Isnurance_class__add");
	write_input("Isnurance_class__add_code", "1234"+str);
	write_input("Isnurance_class__add_name", "test"+str);
	js_click("Isnurance_class__add_ok");
	js_click("Insurance_class_select");
	js_click("Insurance_class_window_ok");
	
	/**
	 * 
	 * get the name of insurance created
	 */
	get_value("Insurance_name");
	
	
	js_click("Insurance_paper");
	js_click("insurance_ok");
		
	String name_ins=driver.findElement(By.xpath(OR.getProperty("Insurance_name"))).getAttribute("value");
	write_input("insurance_search", name_ins);
	
	/**
	 * Select the newly created Insurance
	 */
		click("select_insurance_ok");
	//	click("Select_insurance_ok_details");
		
	//driver.findElement(By.xpath("//*[@id='addUpdateInsuranceModal']/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/table/tbody/tr/td/div[2]/div[6]/label/input")).click();
		js_click("Primary_checkbox");
		write_input("Sub_No", "Testing");
		click("Insured_name_dots");
		js_click("Insured_name_self");
		js_click("Insured_name_self_ok");
		js_click("Insurance_select_ok_last");
		 js_click("new_pt_ok");
	
		}
	
		catch(Exception t)  
		{
			ErrorUtil.addVerificationFailure(t);
			fail=true;
			softAssert.fail("Unable to proceed ahead", t);
						
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
