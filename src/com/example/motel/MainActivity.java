package com.example.motel;



import java.io.BufferedReader;

import android.widget.SearchView.OnQueryTextListener;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import android.R.menu;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;


public class MainActivity extends Activity implements OnQueryTextListener{
	
			final static String    SERVERIP = "159.253.45.121";  
		    final static  int       SERVERPORT   = 8000;
		    final static String server_respond=""; 
		    String svr_gelen_msg="";
		    Context myc=MainActivity.this;
		    classSessionManager sessionManager;
		    classYonlen Yonlen =new classYonlen();
		    // action bar
	         private ActionBar actionBar;
		    //String gelen_mesaj="{\"hotel\":{\"HotelInfo\":{\"a\":\"CORNICHE SUD RUE DU COMMERCE  BP287\",\"Hotel.Description\":\"Further information about this hotel will be available shortly\n\",\"c\":\"CONAKRY\",\"d\":\"Hotel.Description-Location-Note-\",\"e\":\"CONTACT@NFSENEGAL.COM\",\"f\":\"179-108-152-141-260-244-246-198-131-129-14-37\",\"i\":\"L2QvMS9oZGRzL0dOLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMS5qcGc=-L2QvMS9oZGRzL0dOLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMi5qcGc=\",\"coid\":\"GN\",\"air\":\"CKY\",\"n\":\"Novotel Ghi Conakry\",\"hotel-commerge\":[],\"p\":\"221339570770\",\"rc\":\"196\",\"fx\":\"CKY\",\"s\":\"3\",\"w\":\"www.novotel.com/gb/hotel-0509-novotel-ghi-conakry/\",\"Note\":\"Extra-person charges may apply and vary depending on hotel policy. Government-issued photo identification and a credit card or cash deposit are required at check-in for incidental charges. Special requests are subject to availability upon check-in and may incur additional charges. Special requests cannot be guaranteed.\n\",\"x\":\"09.53702900000000000\",\"y\":\"-13.6784700000000610\",\"id\":\"000001\",\"Location\":\"The Novotel Ghi Conakry is a 3 star hotel in the centre of Conakry in a residential area close to the sea.\n\"}}}";
	     	//String gelen_mesaj="{\"hotel\":{\"HotelInfo\":{\"a\":\"TURHAN CEMAL BERIKER BULV NO:173 A SEYHAN MERKEZ ADANA\",\"Hotel.Description\":\"This 3 star hotel is located within the city of Adana. All 42 rooms are equipped with minibar, hairdryer, safe and air conditioning.\n\",\"c\":\"ADANA\",\"d\":\"Hotel.Description-Location-\",\"e\":\"\",\"f\":\"105-157-100-131\",\"i\":\"L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMS5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMi5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMy5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwNC5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwNS5qcGc=\",\"coid\":\"TR\",\"air\":\"ADA\",\"n\":\"ADANA AIRPORT HOTEL\",\"hotel-commerge\":[{\"provider\":\"booking\",\"url\":\"http://94.102.77.210:8080/Hotel/Commerce?commerge-data=aHR0cDovL3d3dy5ib29raW5nLmNvbS9ob3RlbC90ci9haXJwb3J0LmVuLWdiLmh0bWw/YWlkPTM0NzM2MyZob3RlbGlkcz0yODIwODEmc2VsZWN0ZWRfY3VycmVuY3k9VFJZJiZsYWJlbD1tb3RlbF8=\"},{\"provider\":\"expedia\",\"url\":\"http://94.102.77.210:8080/Hotel/Commerce?commerge-data=aHR0cDovL3d3dy50cmF2ZWxub3cuY29tL3RlbXBsYXRlcy8zODI5OTYvaG90ZWxzLzMzNjcwNC9yb29tcz9yb29tc0NvdW50PTEmcm9vbXNbMF0uYWR1bHRzQ291bnQ9MiZyb29tc1swXS5jaGlsZHJlbkNvdW50PTAmY3VycmVuY3k9VFJZJmN1cnJlbmN5U3ltYm9sPVRSWSZsYW5nPVRy\"}],\"p\":\"903224322626\",\"rc\":\"43\",\"fx\":\"ADA\",\"s\":\"\",\"w\":\"\",\"x\":\"36,99470900000000000\",\"y\":\"35,21337600000000000\",\"id\":\"000001\",\"Location\":\"Within city\n\"}}}";
	     	 	
	     @Override	
	     public void onCreate(Bundle savedInstanceState) {	
	         super.onCreate(savedInstanceState);
	         setContentView(R.layout.sayfa_otel_main);
	         Button btnAra=(Button)findViewById(R.id.button1);
	         Button btnGrp=(Button)findViewById(R.id.button2);
	         sessionManager=new classSessionManager(myc);
	         
	         // Associate searchable configuration with the SearchView
	         SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	         SearchView searchView = (SearchView) findViewById(R.id.searchView1);
	         searchView.setSubmitButtonEnabled(true);
	         searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, java_otel_searchresults.class)));
	         searchView.setOnQueryTextListener(this);
	        
	         searchView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Yonlen.yonlen(java_otel_searchresults.class,myc);
					
				}
			});
	         
	                  
	         //ge�ici olarak yaz�lm��
	         //classSessionManager session=new classSessionManager(myc);
	        // session.logoutUser();
	         
	      // connect to the server
	         
	         btnAra.setOnClickListener(new View.OnClickListener() {
	             @Override
	             public void onClick(View view) {	  
	                                 
	               
	               //String jsonMsg="{\"text\":{\"request\":\"1\",\"provider\":\"00003\",\"country\":\"GN\",\"hotelId\":\"000001\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
	               //new connectTask().execute(jsonMsg);
	            	 //String gelen_mesaj="otel==="+"{\"hotel\":{\"HotelInfo\":{\"a\":\"TURHAN CEMAL BERIKER BULV NO:173 A SEYHAN MERKEZ ADANA\",\"Hotel.Description\":\"This 3 star hotel is located within the city of Adana. All 42 rooms are equipped with minibar, hairdryer, safe and air conditioning.\n\",\"c\":\"ADANA\",\"d\":\"Hotel.Description-Location-\",\"e\":\"\",\"f\":\"105-157-100-131\",\"i\":\"L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMS5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMi5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMy5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwNC5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwNS5qcGc=\",\"coid\":\"TR\",\"air\":\"ADA\",\"n\":\"ADANA AIRPORT HOTEL\",\"hotel-commerge\":[{\"provider\":\"booking\",\"url\":\"http://94.102.77.210:8080/Hotel/Commerce?commerge-data=aHR0cDovL3d3dy5ib29raW5nLmNvbS9ob3RlbC90ci9haXJwb3J0LmVuLWdiLmh0bWw/YWlkPTM0NzM2MyZob3RlbGlkcz0yODIwODEmc2VsZWN0ZWRfY3VycmVuY3k9VFJZJiZsYWJlbD1tb3RlbF8=\"},{\"provider\":\"expedia\",\"url\":\"http://94.102.77.210:8080/Hotel/Commerce?commerge-data=aHR0cDovL3d3dy50cmF2ZWxub3cuY29tL3RlbXBsYXRlcy8zODI5OTYvaG90ZWxzLzMzNjcwNC9yb29tcz9yb29tc0NvdW50PTEmcm9vbXNbMF0uYWR1bHRzQ291bnQ9MiZyb29tc1swXS5jaGlsZHJlbkNvdW50PTAmY3VycmVuY3k9VFJZJmN1cnJlbmN5U3ltYm9sPVRSWSZsYW5nPVRy\"}],\"p\":\"903224322626\",\"rc\":\"43\",\"fx\":\"ADA\",\"s\":\"\",\"w\":\"\",\"x\":\"36,99470900000000000\",\"y\":\"35,21337600000000000\",\"id\":\"000001\",\"Location\":\"Within city\n\"}}}";
	               //Yonlen.yonlen(java_otel_rezervasyon.class,myc,gelen_mesaj);        	
	            	 	  
	             }
	         });
	         
	         btnGrp.setOnClickListener(new View.OnClickListener() {
	             @Override
	             public void onClick(View view) {		                                 
	                
	            	 //String jsonMsg="{\"text\":{\"request\":\"0\",\"provider\":\"00003\",\"country\":\"TR\",\"city\":\"0001\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
	            	 //new connectTask().execute(jsonMsg);
		              // Yonlen.yonlen(otel_bilgi.class,myc,svr_gelen_msg);       	
	            	 	  
	             }
	         });
	         	        
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

		public class connectTask extends AsyncTask<String,String,String> {	   
	    	 
	    	
	    	 private ProgressDialog dialog=new ProgressDialog(MainActivity.this);
	    	 
	    	 @Override
	    	 protected void onPreExecute() {
	             dialog.setMessage("Server'e ba�lan�yor...");
	             dialog.show();
	         }
	    	 
	         @Override
	         protected String doInBackground(String... message) {	  
	        	 
	        	 try
	             {
		        		   Socket socket = new Socket(SERVERIP,SERVERPORT);// Ba�lan�lacak Server'un IP'si  ve Portu 
		        	       PrintStream  cikis   = new PrintStream(socket.getOutputStream(),true);// Client i�in ��k�� Kanal� olu�turuluyor.
		        	       BufferedReader  giris   = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Client i�in Giri� Kanal� olu�turuluyor.

						   //dialog.setMessage("Server'e ba�land�.");
		                   //String line = "{\"text\":{\"request\":\"1\",\"provider\":\"00003\",\"country\":\"GN\",\"hotelId\":\"000001\"},\"auth\":{\"userName\":\"ahmet\",\"password\":\"k\"}}\r\n";
		                   //String jsonMsg="{\"text\":{\"request\":\"1\",\"provider\":\"00003\",\"country\":\"GN\",\"hotelId\":\"000001\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
						   //String jsonMsg="{\"text\":{\"request\":\"0\",\"provider\":\"00003\",\"country\":\"TR\",\"city\":\"0001\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
						   //String jsonMsg="{\"text\":{\"request\":\"5\",\"provider\":\"00003\",\"country\":\"TR\",\"key\":\"ADA\",\"language\":\"tr\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";		  
						   cikis.println(message);//Client'in ��k�� kanal�na at�yor sonra da Server'un giri� kanal�na giriyor.
						   //svr_gelen_msg="{\"hotel\":{\"HotelInfo\":{\"a\":\"CORNICHE SUD RUE DU COMMERCE  BP287\",\"Hotel.Description\":\"Further information about this hotel will be available shortly\n\",\"c\":\"CONAKRY\",\"d\":\"Hotel.Description-Location-Note-\",\"e\":\"CONTACT@NFSENEGAL.COM\",\"f\":\"179-108-152-141-260-244-246-198-131-129-14-37\",\"i\":\"L2QvMS9oZGRzL0dOLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMS5qcGc=-L2QvMS9oZGRzL0dOLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMi5qcGc=\",\"coid\":\"GN\",\"air\":\"CKY\",\"n\":\"Novotel Ghi Conakry\",\"hotel-commerge\":[],\"p\":\"221339570770\",\"rc\":\"196\",\"fx\":\"CKY\",\"s\":\"3\",\"w\":\"www.novotel.com/gb/hotel-0509-novotel-ghi-conakry/\",\"Note\":\"Extra-person charges may apply and vary depending on hotel policy. Government-issued photo identification and a credit card or cash deposit are required at check-in for incidental charges. Special requests are subject to availability upon check-in and may incur additional charges. Special requests cannot be guaranteed.\n\",\"x\":\"09.53702900000000000\",\"y\":\"-13.6784700000000610\",\"id\":\"000001\",\"Location\":\"The Novotel Ghi Conakry is a 3 star hotel in the centre of Conakry in a residential area close to the sea.\n\"}}}";
		                   svr_gelen_msg = giris.readLine();//Server'dan gelen veri okunuyor ve de�i�kene at�l�yor.
						   //Yonlen.yonlen(otel_bilgi.class,myc,svr_gelen_msg);
		                   cikis.close();//��k�� kanal�n� kapat�yor.
		                   giris.close();//Giri� kanal�n� kapat�yor.
		                   socket.close();//Soket'i kapat�yor.
	              }
	             catch (Throwable e)
	             {
	                   Log.i("Ba�lant� Mesaj�", e.getMessage());
	             }
	            
	  
	             return null;
	         }
	         
	         @Override
			protected void onPostExecute(String result) {
	        	 dialog.dismiss();
				super.onPostExecute(result);
			}

			
	     }

	     
		@Override
		public boolean onQueryTextSubmit(String query) {
			Yonlen.yonlen(java_otel_searchresults.class,myc); 
			return true;
		}


                                                                                   
		@Override
		public boolean onQueryTextChange(String newText) {
			Yonlen.yonlen(java_otel_searchresults.class,myc); 
			return true;
		}
	     
		 @Override
         public boolean onCreateOptionsMenu(Menu menu) {
             MenuInflater inflater = getMenuInflater();
             inflater.inflate(R.menu.activity_main_actions, menu);
          // Associate searchable configuration with the SearchView
             SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
             SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                     .getActionView();
            
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
	     }
	     
}
