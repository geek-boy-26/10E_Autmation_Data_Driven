package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex {
	

	
public static void main(String[] args) {
		// TODO Auto-generated method stub

System.out.println(Pattern.matches("[a-zA-Z0-9]{7}", "kabir14"));		// alpha numeric with length 7
System.out.println(Pattern.matches("[789][0-9]{9}", "7772421042")); //
		
	}

}
