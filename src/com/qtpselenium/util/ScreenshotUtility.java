//Find More Tutorials On WebDriver at -> http://software-testing-tutorials-automation.blogspot.com
package com.qtpselenium.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.media.rtp.rtcp.Report;

import org.apache.commons.io.FileUtils;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IClass;
import org.testng.IConfigurable;
import org.testng.IConfigureCallBack;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IResultMap;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

import atu.testrecorder.exceptions.ATUTestRecorderException;

import com.qtpselenium.base.TestBase;
import com.qtpselenium.base.TestBase;
import com.qtpselenium.util.Xls_Reader;

public class ScreenshotUtility implements ITestListener, IInvokedMethodListener,ISuiteListener{

	
	public static int count = -1;
	TestBase test = new TestBase();
	//This method will execute before starting of Test suite.

	public void onStart(ITestContext tr) {	
		
		TestBase.APP_LOGS.debug("Test Starts"+":"+tr.getName());
		Reporter.log("About to begin executing Test " + tr.getName(), true);
		
	}

	//This method will execute, Once the Test suite is finished.
	public void onFinish(ITestContext tr) {
		Reporter.log("About to begin executing Test " + tr.getName(), true);
		try
			{
				test.closeBrowser();
				System.out.println("closing browser");
				TestBase.APP_LOGS.debug("Closing browser as test is finished");
			}catch(Exception e)
			{
				System.out.println(e.getStackTrace());
				}
			
		
	}

	//This method will execute only when the test is pass.
	public void onTestSuccess(ITestResult tr) {
		Reporter.log("Count value before count++"+":"+count);
		Reporter.log("Test Passed" +":"+ tr.getName(), true);
		TestBase.Result_Xls.setCellData("Result", "Script_name", count+3, tr.getName());
		TestBase.Result_Xls.setCellData("Result", "Result", count+3, "PASS");
	    count++;
	    Reporter.log("Count value after count++"+":"+count);
		//If screenShotOnPass = yes, call captureScreenShot.
		if(TestBase.config.getProperty("screenShotOnPass").equalsIgnoreCase("yes"))
		{
			captureScreenShot(tr,"pass");
		}
		
		try
		{
			test.closeBrowser();
			System.out.println("closing browser");
			TestBase.APP_LOGS.debug("Closing browser as test is finished");
		}catch(Exception e)
		{
			System.out.println(e.getStackTrace());
			}
	}

	
	//This method will execute only on the event of fail test.
	public void onTestFailure(ITestResult tr) {		
		Reporter.log("Test Failed" +":"+ tr.getName(), true);
		TestBase.Result_Xls.setCellData("Result", "Script_name", count+3, tr.getName());
		TestBase.Result_Xls.setCellData("Result", "Result", count+3, "FAIL");
		//If screenShotOnFail = yes, call captureScreenShot.
		if(TestBase.config.getProperty("screenShotOnFail").equalsIgnoreCase("yes"))
		{
			captureScreenShot(tr,"fail");
		}
		
		try
		{
			Thread.sleep(2000);
			test.closeBrowser();
			System.out.println("closing browser");
			TestBase.APP_LOGS.debug("Closing browser as test is finished");
		}catch(Exception e)
		{
			System.out.println(e.getStackTrace());
			}
	}

	
	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
	}
	
	//Function to capture screenshot.
	public void captureScreenShot(ITestResult result, String status){	
		String destDir = "";
		String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
		//To capture screenshot.
		File scrFile = ((TakesScreenshot) TestBase.driver).getScreenshotAs(OutputType.FILE);
    	DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
    	//If status = fail then set folder name "screenshots/Failures"
    	if(status.equalsIgnoreCase("fail")){
    		destDir = "screenshots/Failures";
    	}
    	//If status = pass then set folder name "screenshots/Success"
    	else if (status.equalsIgnoreCase("pass")){
    		destDir = "screenshots/Success";
    	}
    	
    	//To create folder to store screenshots
    	new File(destDir).mkdirs();
    	//Set file name with combination of test class name + date time.
    	String destFile = passfailMethod+" - "+dateFormat.format(new Date()) + ".png";
    	
        try {
        	//Store file at destination folder location
     	   FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
        }
        catch (IOException e) {
             e.printStackTrace();
        }
       
        
   }

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult testResult) {
		// TODO Auto-generated method stub
		
		String textMsg = "About to begin executing following method : " + returnMethodName(arg0.getTestMethod());
		 
		Reporter.log(textMsg, true);
	}

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult testResult) {
		// TODO Auto-generated method stub
		
		// This will return method names to the calling function
		String textMsg = "Completed executing following method : " + returnMethodName(arg0.getTestMethod());

		Reporter.log(textMsg, true);
	
	}

	private String returnMethodName(ITestNGMethod method) {

		return method.getRealClass().getSimpleName() + "." + method.getMethodName();

	}

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		Reporter.log("About to begin executing Suite " + suite.getName(), true);
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		Reporter.log("About to end executing Suite " + suite.getName(), true);
	}


	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		Reporter.log("Test Skipped " + result.getName(), true);
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		Reporter.log("Test Started " + result.getName(), true);
	}




}