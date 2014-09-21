package com.example.motel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class classYonlen{
	
	//private Bundle extras = null;
	final static String server_respond="";
	

	public void yonlen(Class<?> sayfa, Context context,String veri)
	{
		try{
	    	 Bundle bilgi_gonder=new Bundle();
	    	 bilgi_gonder.putString(server_respond,veri);
	    	 Intent intent=new Intent();
	    	 intent.putExtras(bilgi_gonder);
	    	 intent.setClass(context, sayfa);
	    	 context.startActivity(intent);	  
	    	}
	    	catch (Exception e) {
		         e.printStackTrace();
		       }
	}
	
	//override yonlern
	public void yonlen(Class<?> sayfa, Context context)
	{
		try{
	    	 Intent intent=new Intent();
	    	 intent.setClass(context, sayfa);
	    	 context.startActivity(intent);	  
	    	}
	    	catch (Exception e) {
		         e.printStackTrace();
		       }
	}
	
	/*private String veri_al()
	{
		extras=this.getIntent().getExtras();
		String bilgi=extras.getString(server_respond);
		return bilgi;
	}*/
}
