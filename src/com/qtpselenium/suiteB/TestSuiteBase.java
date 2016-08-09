package com.qtpselenium.suiteB;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import com.qtpselenium.base.TestBase;
import com.qtpselenium.util.TestUtil;

public class TestSuiteBase extends TestBase{

	// check if the suite ex has to be skiped
		@BeforeSuite
		public void checkSuiteSkip() throws Exception{
			initialize();
			APP_LOGS.debug("Checking Runmode of Suite B");
			if(!TestUtil.isSuiteRunnable(suiteXls, "B Suite")){
				APP_LOGS.debug("Skipped Suite B as the runmode was set to NO");
				throw new SkipException("RUnmode of Suite B set to no. So Skipping all tests in Suite A");
			}
			
		}
}
