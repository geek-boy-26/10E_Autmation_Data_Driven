package test;

import com.qtpselenium.util.Xls_Reader;

public class TestRunmode {

	
	public static void main(String[] args) {
		Xls_Reader x = new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\xls\\A suite.xlsx");
		System.out.println(isTestCaseRunnable(x,"TestCase_A1"));
		Xls_Reader x1 = new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\xls\\B suite.xlsx");
		System.out.println(isTestCaseRunnable(x1,"TestCase_B1"));
	}
	
	// returns true if runmode of the test is equal to Y
	public static boolean isTestCaseRunnable(Xls_Reader xls, String testCaseName){
		boolean isExecutable=false;
		for(int i=2; i<= xls.getRowCount("Test Cases") ; i++){
			//String tcid=xls.getCellData("Test Cases", "TCID", i);
			//String runmode=xls.getCellData("Test Cases", "Runmode", i);
			//System.out.println(tcid +" -- "+ runmode);
			
			
			if(xls.getCellData("Test Cases", "TCID", i).equalsIgnoreCase(testCaseName)){
				if(xls.getCellData("Test Cases", "Runmode", i).equalsIgnoreCase("Y")){
					isExecutable= true;
				}else{
					isExecutable= false;
				}
			}
		}
		
		return isExecutable;
		
	}

}
