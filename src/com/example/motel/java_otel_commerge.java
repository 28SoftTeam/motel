package com.example.motel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SearchView;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class java_otel_commerge extends Activity {

	final static String server_respond="";	
	private Bundle extras = null;
	Context myc=java_otel_commerge.this;
	WebView webview;
	classSessionManager sessionManager;
	classYonlen Yonlen =new classYonlen();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_commerge);
		 ActionBar actionBar = getActionBar();
		 actionBar.show();
		 sessionManager=new classSessionManager(myc);
		  webview = (WebView) findViewById(R.id.webview);
	      webview.getSettings().setJavaScriptEnabled(true);
	      webview.getSettings().setLoadWithOverviewMode(true);	      
	      webview.getSettings().setUseWideViewPort(true);
	      webview.getSettings().setBuiltInZoomControls(true);
	      
	      //rezervasyon verileri
	      SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(myc);
	      //String user_email=pref.getString("email", null).toString();
	     
	      classReservationInfo reservedInfo=new classReservationInfo(myc);
	      String user_email=reservedInfo.getEmail();
	      String hotel_ad=reservedInfo.getReservedHotel();
	      String city=reservedInfo.getReservedCity();
	      String country=reservedInfo.getReservedCountry();
	      String reserved_commerge=reservedInfo.getReservedCommerge();
	      String reserved_in=reservedInfo.getReservedIn();
	      String reserved_out=reservedInfo.getReservedOut();
	      
	      new AsyncCallWebServices().execute(user_email,hotel_ad,city,country,reserved_commerge,reserved_in,reserved_out);
	      
	      String gelen_veri=veri_al();
	      
	      String commerge=gelen_veri.split("===")[0];
	      Log.i("ClassGoToCommergeSite : Gelen commerge:", commerge);
	      String jsonMsg=gelen_veri.split("===")[1];
	      Log.i("ClassGoToCommergeSite : Gelen jason:", jsonMsg);
	      classGoToCommergeSite goto_commergesite=new classGoToCommergeSite();
	      goto_commergesite.execute(jsonMsg,commerge);
	      
	 
	      final ProgressDialog progress = ProgressDialog.show(this, "Rezervasyon Sayfasý", "Yükleniyor....", true);
	      progress.show();
	      webview.setWebViewClient(new WebViewClient() {
	 
	         @Override
	         public void onPageFinished(WebView view, String url) {
	            super.onPageFinished(view, url);
	            Toast.makeText(getApplicationContext(), "Sayfa yüklendi", Toast.LENGTH_SHORT).show();
	            progress.dismiss();
	         }
	 
	         public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
	            Toast.makeText(getApplicationContext(), "Bir hata oluþtu", Toast.LENGTH_SHORT).show();
	            progress.dismiss();
	         }
	      });
	      
	      
	}
	
	private String veri_al()
	{
		extras=this.getIntent().getExtras();
		String bilgi=extras.getString(server_respond);
		return bilgi;
	}
	public class classGoToCommergeSite extends AsyncTask<String, String, String> {
		  
		String result=null;
		
		final static String    SERVERIP = "159.253.45.121";  
	    final static  int       SERVERPORT   = 8000;
	    String commerge;

	   /* public classGoToCommergeSite(Context context) {
	       this.myc=context;
	    }*/
	    
	    @Override
	   	protected String doInBackground(String... params) {
	   		
	    	String jsonMsg=params[0];
	    	commerge=params[1];
	    	try
	        {
	       		   Socket socket = new Socket(SERVERIP,SERVERPORT);// Baðlanýlacak Server'un IP'si  ve Portu 
	       	       PrintStream  cikis   = new PrintStream(socket.getOutputStream(),true);// Client için Çýkýþ Kanalý oluþturuluyor.
	       	       BufferedReader  giris   = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Client için Giriþ Kanalý oluþturuluyor.
	       	       cikis.println(jsonMsg);//Client'in çýkýþ kanalýna atýyor sonra da Server'un giriþ kanalýna giriyor.
	       	       result = giris.readLine();//Server'dan gelen veri okunuyor ve deðiþkene atýlýyor.
					   //Yonlen.yonlen(otel_bilgi.class,myc,svr_gelen_msg);
	                  cikis.close();//Çýkýþ kanalýný kapatýyor.
	                  giris.close();//Giriþ kanalýný kapatýyor.
	                  socket.close();//Soket'i kapatýyor.
	                  Log.i("ClassGoToCommergeSite : doinBackground içi", "buraya geldi:"+result);
	         }
	    	catch (Throwable e)
	        {
	              Log.i("ClassGoToCommergeSite : Baðlantý Mesajý", e.getMessage());
	        }
	   		return result;
	   	}

	   	@Override
		protected void onPostExecute(String result) {
	   		
	   		
	   		Log.i("ClassGoToCommergeSite : onPostExecute içi commerge", commerge);
			try {			
				JSONObject obj = new JSONObject(result);
				JSONObject hotel=obj.getJSONObject("hotel");
				JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
				JSONArray arr = hotel_info.getJSONArray("hotel-commerge");
				int i=0;
				boolean found=false;			
				while(i<arr.length() & !found)
				{
					JSONObject com=arr.getJSONObject(i);
					result=com.getString("providerName");
					Log.i("ClassGoToCommergeSite :döngü commerge", result);
					if(result.equals(commerge))
					{
						found=true;
						result=com.getString("providerUrl");
						Log.i("ClassGoToCommergeSite : Bulunan commerge:", commerge);
						
					}

					i++;
				}
				
				webview.loadUrl(result);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			super.onPostExecute(result);
		}

		@Override
	      	protected void onPreExecute() {
	      		// TODO Auto-generated method stub
	      		super.onPreExecute();
	      	}
	}
	
	 //connect to server and send reservation informations
    public String CallSendUserReservationInfo(String user_email,String hotel_ad,String city, String country,
    		String commerge, String reserved_in,String reserved_out)
	{
    	
    	final String SOAP_ACTION = "http://28Soft.com/addUserActivity";
    	final String OPERATION_NAME = "addUserActivity"; 
    	final String WSDL_TARGET_NAMESPACE = "http://28Soft.com/";
    	//public  final String SOAP_ADDRESS = "http://172.17.196.73:49898/addUserActivity.asmx";
    	//final String SOAP_ADDRESS = "http://192.168.2.73/addUserActivity.asmx";
    	final String SOAP_ADDRESS ="http://coeus.28soft.org/addUserActivity.asmx";
    	
    String result=""; //response of server
	SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
    
    request.addProperty("user_email",user_email);
    request.addProperty("reserved_hotel",hotel_ad);
    request.addProperty("reserved_city",city);
    request.addProperty("reserved_country",country);
    request.addProperty("reserved_commerge",commerge);
    request.addProperty("reserved_in",reserved_in);
    request.addProperty("reserved_out",reserved_out);

	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	envelope.dotNet = true;
	envelope.setOutputSoapObject(request);
	HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);	 
	try {
		httpTransport.call(SOAP_ACTION, envelope);
	} catch (IOException e) {
		  Log.e("kSOAP" ,"io exception"+e.getMessage());
	} catch (XmlPullParserException e) {
		Log.e("kSOAP" , "XmlPullParserException"+e.getMessage());
	}
	 try {

         result = "" + envelope.getResponse();
         Log.i("response of server", "result"+result);

     } catch (Exception e) {

         Log.e("error on response of server", "Soap Parsing Exception"+e.getMessage());
         result="Soap Parsing Exception"+e.getMessage();
   
     }
        
	
	return  result;
	}

private class AsyncCallWebServices extends AsyncTask<String,String,String>{

		String response="";
		protected String doInBackground(String...params) {
			String user_email=params[0];
			String reserved_hotel=params[1];
			String reserved_city=params[2];
			String reserved_country=params[3];
			String reserved_commerge=params[4];
			String reserved_in=params[5];
			String reserved_out=params[6];
			response=CallSendUserReservationInfo(user_email,reserved_hotel,reserved_city,reserved_country,reserved_commerge,
					reserved_in,reserved_out);
	        return response.toString();
	     }	

	    @Override
	    protected void onPreExecute() {
	    	
	    }
	    
	    
	    @Override
		protected void onPostExecute(String result) {
	    	Log.i("response of server",response);
			super.onPostExecute(result);
		}

		
	}
	
		@Override
		public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
    	 MenuItem logout=menu.findItem(R.id.action_person);
         if(sessionManager.isLoggedIn())
         {
        	 
        	 logout.setTitle("Logout");
        	 
         }
         else
         {
        	 logout.setVisible(false);
         }
         return super.onPrepareOptionsMenu(menu);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);
		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		
		searchView.setOnSearchClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				Yonlen.yonlen(java_otel_searchresults.class,myc);
				
			}
		});
		return super.onCreateOptionsMenu(menu);
		}

		/**
		* On selecting action bar icons
		* */
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_search:
			 Yonlen.yonlen(java_otel_searchresults.class,myc);
		    return false;
		case R.id.action_person:
			 
			 
			 if(sessionManager.isLoggedIn())
			 {
				 item.setTitle("logout");
				 sessionManager.logoutUser();
			 }
			 else
				 item.setVisible(false);
		    return true;
		
		default:
		    return super.onOptionsItemSelected(item);
		}
		}}