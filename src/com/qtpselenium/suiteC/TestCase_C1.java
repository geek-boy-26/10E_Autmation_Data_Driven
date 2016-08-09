package com.qtpselenium.suiteC;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.TestUtil;


public class TestCase_C1 extends TestSuiteBase{
	String runmodes[]=null;
	static int count=-1;
	 static boolean fail=false;
	static boolean skip=false;
	static boolean isTestPass=true;
	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip(){
				
				if(!TestUtil.isTestCaseRunnable(suiteCxls,this.getClass().getSimpleName())){
					APP_LOGS.debug("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
					throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
				}
				runmodes=TestUtil.getDataSetRunmodes(suiteCxls, this.getClass().getSimpleName());
			}
			
	 @Test(dataProvider="getTestData")
	 public void testcaseC1(
										String col1,
										String col2,
										String col3,
										String col4
								  ){
		 count++;
			if(!runmodes[count].equalsIgnoreCase("Y")){
				skip=true;
				throw new SkipException("Runmode for test set data set to no "+count);
			}
		APP_LOGS.debug(" Executing TestCase_C1");
		APP_LOGS.debug(col1 +" -- "+col2 +" -- "+col3 +" -- "+col4);
	}
	

	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteCxls, this.getClass().getSimpleName()) ;
	}

}
