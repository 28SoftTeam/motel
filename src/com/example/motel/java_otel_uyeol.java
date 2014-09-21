package com.example.motel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class java_otel_uyeol extends Activity{

	public static String response="";
	final classAlertDialog alert = new classAlertDialog();
	classValidators validate=new classValidators();
	private String gelen_mesaj;
	private Bundle extras = null;
	final static String server_respond="";
	classYonlen Yonlen;
	Context myc=java_otel_uyeol.this;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_kayitol);
		Button btn=(Button)findViewById(R.id.button2);
		Yonlen=new classYonlen();
		gelen_mesaj=veri_al();
		
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					EditText ed_name=(EditText)findViewById(R.id.editText4);
					EditText ed_email=(EditText)findViewById(R.id.editText1);
					EditText ed_password1=(EditText)findViewById(R.id.editText3);
					EditText ed_password2=(EditText)findViewById(R.id.editText5);
					String nameSurname=ed_name.getText().toString();
					String email=ed_email.getText().toString();
					if(classValidators.isEmailValid(email))
					{
						String password1=ed_password1.getText().toString();
						String password2=ed_password2.getText().toString();
						if(classValidators.isPasswordMatch(password1, password2))
						{
							new AsyncCallWebServices().execute(email,nameSurname,password1);
						}
						else
						{
							alert.showAlertDialog(java_otel_uyeol.this, "HATA!","Þifre uyuþmazlýðý. Þifre min. 6 karakter", false);
						}
					}
					else
					{
						alert.showAlertDialog(java_otel_uyeol.this, "HATA!","Yanlýþ e-mail formatý", false);
					}

					
				}
				catch (Exception e)
				{
					alert.showAlertDialog(java_otel_uyeol.this, "HATA!", "Hata oluþtu :"+e.getMessage(), false);
				} finally {
				}
				
			}
		});
		
		
	}
	
	private class AsyncCallWebServices extends AsyncTask<String,Void,String>{

		
		ProgressDialog dialog = new ProgressDialog(java_otel_uyeol.this);
		protected String doInBackground(String...params) {
			classWebServiceCallers cs=new classWebServiceCallers();
			String email=params[0];
			String nameSurname=params[1];
			String password=params[2];
			response=cs.CallAddUser(email, nameSurname, password);
	        return response.toString();
	     }	

	    @Override
	    protected void onPreExecute() {
	    	dialog.setMessage("Kaydýnýz Alýnýyor...");
	        dialog.show();
	    }
	    
	    @Override
	    protected void onProgressUpdate(Void... values) {
	        
	    }
	    
	    protected void onPostExecute(String response) {
	    	alert.showAlertDialog(java_otel_uyeol.this, "Bilgi!", response, false);
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(response.equals("Sisteme zaten kayýtlýsýnýz.Giriþ yapmak için yönlendiriliyorsunuz...") 
	    			| response.equals("Yeni kullanýcý oluþturuldu.Giriþ yapmak için yönlendiriliyorsunuz..."));
	    		
	    			Yonlen.yonlen(java_otel_usertani.class, myc,gelen_mesaj);
	    	
	    }
	}
	
	private String veri_al()
	{
		extras=this.getIntent().getExtras();
		String bilgi=extras.getString(server_respond);
		return bilgi;
	}
}
