package com.qtpselenium.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Retry implements IRetryAnalyzer{
	private int retryCount = 0;
    private int maxRetryCount = 2;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
	      if (retryCount <maxRetryCount) {
	            retryCount++;
	            Reporter.log("Failed Test retrying"+"for"+retryCount+"time" + result.getName(), true);
	            return true;
	        }
	        return false;
	}
	

}
