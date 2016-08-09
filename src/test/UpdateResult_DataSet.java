package test;

import com.qtpselenium.util.Xls_Reader;

public class UpdateResult_DataSet {
	
	public static void main(String[] args) {

		Xls_Reader x = new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\xls\\B suite.xlsx");		
		reportDataSetResult(x,"TestCase_B1",3,"Pass");
		reportDataSetResult(x,"TestCase_B1",4,"Fail");
		
	}

    // update results for a particular data set	
	public static void reportDataSetResult(Xls_Reader xls, String testCaseName, int rowNum,String result){	
		xls.setCellData(testCaseName, "Results", rowNum, result);
	}
}
