package com.qtpselenium.suiteA;
/*
 * This test case covers the log in of 10e application
 * In this various login scenarios can be tested such as 
 * correct username,wrong password, blank username etc.
 * 
 */


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;
import com.qtpselenium.util.Xls_Reader;


public class Login_10E extends TestSuiteBase{
	String runmodes[]=null;
	static int count=-1;
	static int TestcaseID=1;
	//static boolean pass=false;
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
		// load the runmodes off the tests
		runmodes=TestUtil.getDataSetRunmodes(suiteAxls, this.getClass().getSimpleName());
	}
	
	@Test(dataProvider="getTestData")
	public void login_10E(String username,String password) throws InterruptedException, IOException
	{
		
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		
		APP_LOGS.debug(" Executing TestCase_A1");
		APP_LOGS.debug(username +" -- "+password);
		
		// selenium code
		
		
			openBrowser();
			login(username, password);
			Thread.sleep(5000);
			
			try
	{
		
		wait_until("welcome_link");
		boolean a = driver.findElements( By.xpath(OR.getProperty("welcome_link")) ).size() != 0;
		if(a==true)
		{
			APP_LOGS.debug("Valid login");
			/*wait_until("Continue");
			Thread(5000);
			click("Continue");*/
			logout();
		}
		else
		{
			fail=true;
			capturescreenshot(this.getClass().getSimpleName()+"_"+count);
			APP_LOGS.debug("Invalid login");
			
		}
	}
	catch(Exception e)
	{
		ErrorUtil.addVerificationFailure(e);
		fail=true;
		APP_LOGS.debug("Element not found");
		softAssert.fail("Unable to execute"+":"+e);
		
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
