package com.qtpselenium.suiteC;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qtpselenium.util.TestUtil;

public class TestCase_C2 extends TestSuiteBase{

	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip(){
				
				if(!TestUtil.isTestCaseRunnable(suiteCxls,this.getClass().getSimpleName())){
					APP_LOGS.debug("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
					throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
				}
			}
			
	@Test
	public void testcaseC2(){
		System.out.println("TestCase_C2");
	}
}
