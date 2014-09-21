package com.example.motel;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class java_otel_sifregonder extends Activity{

	classValidators validate=new classValidators();
	final classAlertDialog alert = new classAlertDialog();
	private String TAG="hatalar";
	
	public final String SOAP_ACTION = "http://tempuri.org/findUserEmailSendMail";
	public  final String OPERATION_NAME = "findUserEmailSendMail"; 
	public  final String WSDL_TARGET_NAMESPACE = "http://tempuri.org";
	//public  final String SOAP_ADDRESS = "http://192.168.2.73/findUser.asmx";
	//public  final String SOAP_ADDRESS = "http://172.17.196.73/findUser.asmx";
	public  final String SOAP_ADDRESS = "http://coeus.28soft.org/findUser.asmx";
	final static String server_respond="";
	private String gelen_mesaj;
	private Bundle extras = null;
	Context myc=java_otel_sifregonder.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_sifreunuttum);
		Button btn=(Button)findViewById(R.id.button2);
		final EditText ed_email=(EditText)findViewById(R.id.ed_email);
		gelen_mesaj=veri_al();
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String email=ed_email.getText().toString();
				if(classValidators.isEmailValid(email))
				{
					new AsyncCallWebServices().execute(email);
				}
				else
				{
					alert.showAlertDialog(java_otel_sifregonder.this, "HATA!","Yanlýþ e-mail formatý", false);
				}
				
			}
		});
	}
	
	public String CallSendUserEmail(String email)
	{
	SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
	String result="";
    request.addProperty("email",email);
    Log.i(TAG, "gönderilen mail :"+email);
	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	envelope.dotNet = true;
	envelope.setOutputSoapObject(request);
	HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);	 
	try {
		httpTransport.call(SOAP_ACTION, envelope);
	} catch (IOException e) {
		  Log.e(TAG, "io exception"+e.getMessage());
	} catch (XmlPullParserException e) {
		Log.e(TAG, "XmlPullParserException"+e.getMessage());
	}
	 try {

         result = envelope.getResponse().toString();
         Log.i(TAG, "result"+result);

     } catch (Exception e) {

         Log.i(TAG, "Soap Parsing Exception"+e.getMessage());
         result="Soap Parsing Exception"+e.getMessage();
   
     }
        
	
	return  result;
	}

private class AsyncCallWebServices extends AsyncTask<String,Void,String>{

		String response="";
		ProgressDialog dialog = new ProgressDialog(java_otel_sifregonder.this);
		protected String doInBackground(String...params) {
			String email=params[0];
			response=CallSendUserEmail(email);
	        return response.toString();
	     }	

	    @Override
	    protected void onPreExecute() {
	    	dialog.setMessage("Üyeliðiniz Ýnceleniyor...");
	        dialog.show();
	    }
	    
	    @Override
	    protected void onProgressUpdate(Void... values) {
	        
	    }

		@Override
		protected void onPostExecute(String result) {
			if(response.equalsIgnoreCase("Þifreniz mail adresinize gönderildi"))
			{
				classYonlen Yonlen=new classYonlen();
				Yonlen.yonlen(java_otel_usertani.class, myc, gelen_mesaj);
			}
			else
			{
				alert.showAlertDialog(java_otel_sifregonder.this, "Bilgi!", result, false);
		    	dialog.dismiss();
			}
			super.onPostExecute(result);
		}
	    
	   
	}

private String veri_al()
{
	extras=this.getIntent().getExtras();
	String bilgi=extras.getString(server_respond);
	return bilgi;
}
}
