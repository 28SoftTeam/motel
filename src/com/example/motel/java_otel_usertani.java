package com.example.motel;
import java.io.IOException;
import java.util.Arrays;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class java_otel_usertani extends FragmentActivity{
	
	public static String response="";
	private MainFragment mainFragment;
	private String TAG = "MOTEL_USERS";
	TextView lblEmail;
	private String gelen_mesaj;
	Context myc=java_otel_usertani.this;
	final static String server_respond="";
	private Bundle extras = null;
	// Alert Dialog Manager
    
    classSessionManager sessionManager;
	@Override
		public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sayfa_otel_usertani);
	    gelen_mesaj=veri_al();
	    sessionManager=new classSessionManager(getApplicationContext());
	    
	    Button btn_uyeol=(Button)findViewById(R.id.button2);
	    Button btn_login=(Button)findViewById(R.id.button1);
	    btn_uyeol.setClickable(true);
	    Button btn_sifreunuttum=(Button)findViewById(R.id.button3);
	    btn_sifreunuttum.setClickable(true);
		final LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		final classAlertDialog alert = new classAlertDialog();
		alert.showAlertDialog(java_otel_usertani.this, "Dikkat!", "Devam edebilmek için sisteme giriþ yapmanýz gerekiyor", false);
		
		//login týklandýðýnda
		btn_login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText tv_user_email=(EditText)findViewById(R.id.editText1);
				EditText tv_user_password=(EditText)findViewById(R.id.editText2);
				String user_email=tv_user_email.getText().toString();
				String user_password=tv_user_password.getText().toString();
				if(classValidators.isEmailValid(user_email))
				{
					new AsyncCallWebServicesForEmail().execute(user_email,user_password);
				}
				else
				{
					alert.showAlertDialog(java_otel_usertani.this, "HATA!","Yanlýþ e-mail formatý", false);
				}
			
				
			}
		});
		
		btn_uyeol.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				classYonlen yonlen=new classYonlen();
				yonlen.yonlen(java_otel_uyeol.class, myc,gelen_mesaj);
				
			}
		});
		
		btn_sifreunuttum.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				classYonlen yonlen=new classYonlen();
				yonlen.yonlen(java_otel_sifregonder.class, myc,gelen_mesaj);
				
			}
		});
		
				/*authButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(sessionManager.isLoggedIn())
							sessionManager.logoutUser();
						
					}
				});*/
		
				authButton.setOnErrorListener(new  OnErrorListener() {		    
			 public void onError(FacebookException error) {
		     Log.i(TAG, "Error " + error.getMessage());  }
		  							});
		
		  // set permission list, Don't forget to add email
		  authButton.setReadPermissions(Arrays.asList("basic_info","email"));
		  
		  // session state call back event
		  authButton.setSessionStatusCallback(new Session.StatusCallback() 
		{
		    
			  			@SuppressWarnings("deprecation")
						public void call(final Session session, SessionState state, Exception exception) 
			  		{
			  				
			  				
					   		if (session.isOpened()) {
					   			//final classSessionManager sessionManager=new classSessionManager(getApplicationContext());
				            Log.i(TAG,"Access Token"+ session.getAccessToken());
				            Request.executeMeRequestAsync(session,new Request.GraphUserCallback() {
				                  @Override
		                  public void onCompleted(GraphUser user,Response response) {
		                  if (user != null) 
		                {  Log.i(TAG,"User ID "+ user.getId());
                           Log.i(TAG,"Email "+ user.asMap().get("email")); 
                           Log.i(TAG,"Name"+user.getName());
                           Log.i(TAG,"Surname"+user.getLastName());   
                           Session.getActiveSession().closeAndClearTokenInformation();
                           
                            sessionManager.createLoginSession(user.asMap().get("email").toString());	
                           //servere bilgilerini gönder
                           new AsyncCallWebServices().execute(user.asMap().get("email").toString()
                        		   ,user.getName()+user.getLastName(),"");
                           // Starting rezervation
                           //Intent i = new Intent(getApplicationContext(), java_otel_uyeol.class);
                           //startActivity(i);
                           finish();
                          }
		                  
						}
				                  
									            });
						}	else if(session.isClosed())
	                  {
							//classYonlen Yonlen=new classYonlen();
							//Yonlen.yonlen(java_otel_commerge.class, myc,gelen_mesaj);
	                	  /*sessionManager.logoutUser();
	                	  Intent i = new Intent(getApplicationContext(), MainActivity.class);
                          startActivity(i);
                          finish();*/
	                  }
					   		
					   }
					  });
					 }

		 @Override
		 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		     super.onActivityResult(requestCode, resultCode, data);
		     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		 }
		 
		 public String CallSearchForUser(String user_email,String user_password) //üyelik iþlemleri kontrol ediliyor
			{
			 	final String SOAP_ACTION = "http://tempuri.org/findUserByEmailAndPassword";
				final String OPERATION_NAME = "findUserByEmailAndPassword"; 
				final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
				//final String SOAP_ADDRESS = "http://172.17.196.73/findUser.asmx";
				//final String SOAP_ADDRESS = "http://192.168.2.73/findUser.asmx";
				final String SOAP_ADDRESS = "http://coeus.28soft.org/findUser.asmx";
				String result="";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
		    
		    request.addProperty("email",user_email);
		    request.addProperty("password",user_password);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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

		         result = "" + envelope.getResponse();
		         Log.i(TAG, "result"+result);

		     } catch (Exception e) {

		         Log.i(TAG, "Soap Parsing Exception"+e.getMessage());
		         result="Soap Parsing Exception"+e.getMessage();
		   
		     }
		        
			
			return  result;
			}

		 
		private class AsyncCallWebServicesForEmail extends AsyncTask<String,Void,String>{

				String response="";
				String user_email;
				ProgressDialog dialog = new ProgressDialog(java_otel_usertani.this);
				protected String doInBackground(String...params) {
					user_email=params[0];
					String user_password=params[1];
					response=CallSearchForUser(user_email,user_password);
			        return response.toString();
			     }	

			    @Override
			    protected void onPreExecute() {
			    	dialog.setMessage("Üyeliðiniz Ýnceleniyor...");
			        dialog.show();
			    }
			    
			   
				@Override
				protected void onPostExecute(String result) {
					classAlertDialog alert=new classAlertDialog();
					classYonlen Yonlen=new classYonlen();
					if(response.equals("bulundu"))
					{
						//session aç
						sessionManager.createLoginSession(user_email);
						Log.i("session_mail", user_email);
						Yonlen.yonlen(java_otel_commerge.class, myc,gelen_mesaj);
						
					}
					else
					{
						alert.showAlertDialog(java_otel_usertani.this, "Bilgi!", "Email veya þifre hatasý. Facebook ile kayýtlý olabilirsiniz.", false);				
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
		
		private class AsyncCallWebServices extends AsyncTask<String,Void,String>{
			
			String email;
			classAlertDialog alert = new classAlertDialog();
			classYonlen Yonlen=new classYonlen();
			classSessionManager sessionManager=new classSessionManager(getApplicationContext());
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
		    	
		    }
		    
		    @Override
		    protected void onProgressUpdate(Void... values) {
		        
		    }

			@Override
			protected void onPostExecute(String result) {
				if(response.equals("Sisteme zaten kayýtlýsýnýz.Giriþ yapmak için yönlendiriliyorsunuz...") | response.equals("Yeni kullanýcý oluþturuldu.Giriþ yapmak için yönlendiriliyorsunuz..."))
				{
								
					Yonlen.yonlen(java_otel_commerge.class, myc,gelen_mesaj);
					
				}
				else
				{
					alert.showAlertDialog(java_otel_usertani.this, "Bilgi!", "Bir hata oluþtu tekrar deneyiniz...", false);				
					
				}
				super.onPostExecute(result);
			}
		    
		   
		}
		}

