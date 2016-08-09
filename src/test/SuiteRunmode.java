package test;

import com.qtpselenium.util.Xls_Reader;

public class SuiteRunmode {


	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		Xls_Reader x = new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\xls\\Suite.xlsx");
		System.out.println(isSuiteRunnable(x,"A Suite"));
		System.out.println(isSuiteRunnable(x,"B Suite"));

		System.out.println(isSuiteRunnable(x,"C Suite"));

	}
	
	// finds if the test suite is runnable 
	public static boolean isSuiteRunnable(Xls_Reader xls , String suiteName){
		boolean isExecutable=false;
		for(int i=2; i <= xls.getRowCount("Test Suite") ;i++ ){
			String suite = xls.getCellData("Test Suite", "TSID", i);
			String runmode = xls.getCellData("Test Suite", "Runmode", i);
		
			if(suite.equalsIgnoreCase(suiteName)){
				if(runmode.equalsIgnoreCase("Y")){
					isExecutable=true;
				}else{
					isExecutable=false;
				}
			}

		}
		xls=null; // release memory
		return isExecutable;
		
	}

}
