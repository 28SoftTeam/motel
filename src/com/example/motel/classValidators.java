package com.example.motel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;

public class classValidators {

	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	public  static boolean isPasswordMatch(String p1,String p2) {
	    boolean isMatch = false;
	    if(p1.length()>=6 & p1!="" & p2!="")
	    {
	    	if(p1.equals(p2) )
	    		isMatch=true;
	    }
	    return isMatch;
	}
	
	public  boolean isEmpty(EditText etText) {
	    if (etText.getText().toString().trim().length() > 0) {
	        return false;
	    } else {
	        return true;
	    }
	}
}
