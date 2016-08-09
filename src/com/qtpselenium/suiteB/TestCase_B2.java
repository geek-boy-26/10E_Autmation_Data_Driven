package com.qtpselenium.suiteB;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import atu.testrecorder.exceptions.ATUTestRecorderException;

import com.qtpselenium.util.TestUtil;

public class TestCase_B2 extends TestSuiteBase{
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
	public void testcaseB2(
			String col1,
			String col2,
			String col3)throws InterruptedException, IOException, AWTException{
		// test the runmode of current dataset
				count++;
				if(!runmodes[count].equalsIgnoreCase("Y")){
					skip=true;
					throw new SkipException("Runmode for test set data set to no "+count);
				}
				APP_LOGS.debug(" Executing TestCase_B1");
				APP_LOGS.debug(col1 +" -- "+col2 +" -- "+col3);
				APP_LOGS.debug("TestCase_B2");
		
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
public void reportTestResult() throws ATUTestRecorderException{
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
