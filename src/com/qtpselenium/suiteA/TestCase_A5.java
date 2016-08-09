package com.qtpselenium.suiteA;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.TestUtil;

public class TestCase_A5 extends TestSuiteBase{
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
	public void testcaseA2(
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
		login("sam", "password$1");
		
	
	
	
	
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
