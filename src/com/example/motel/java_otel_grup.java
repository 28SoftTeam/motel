package com.example.motel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class java_otel_grup extends Activity implements OnItemClickListener  {

	private Bundle extras = null;
	final static String server_respond="";
	final static String    SERVERIP = "159.253.45.121";  
    final static  int       SERVERPORT   = 8000;
    classYonlen Yonlen =new classYonlen(); 
    classSessionManager sessionManager;
    Context myc=java_otel_grup.this;
	ListView listView;
    List<RowItem> rowItems;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_grup);
		String gelen_mesaj=veri_al();
		 sessionManager=new classSessionManager(myc);
		classJasonParser parser=new classJasonParser();
		String[] ids=parser.getOtelIDs(gelen_mesaj);
		String[] names=parser.getOtelNames(gelen_mesaj);
		String[] images=parser.getOtelImages(gelen_mesaj);
		String[] cities=parser.getOtelCity(gelen_mesaj);
		String[] stars=parser.getOtelStars(gelen_mesaj);
		String[] countries=parser.getOtelCountry(gelen_mesaj);
		rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < ids.length; i++) {
            RowItem item = new RowItem(images[i],names[i], cities[i], stars[i],ids[i],countries[i]);
            rowItems.add(item);}
        listView = (ListView) findViewById(R.id.list);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,R.layout.list_row, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        
	}

	
	//diðer sayfadan gelen verileri alma
		private String veri_al()
		{
			extras=getIntent().getExtras();
			String bilgi=extras.getString(server_respond);
			return bilgi;
		}
		
		public void onItemClick(AdapterView<?> parent, View view, int position,
	            long id) {
	        
	        //String jsonMsg="{\"hotel\":{\"HotelInfo\":{\"a\":\"TURHAN CEMAL BERIKER BULV NO:173 A SEYHAN MERKEZ ADANA\",\"Hotel.Description\":\"This 3 star hotel is located within the city of Adana. All 42 rooms are equipped with minibar, hairdryer, safe and air conditioning.\n\",\"c\":\"ADANA\",\"d\":\"Hotel.Description-Location-\",\"e\":\"\",\"f\":\"105-157-100-131\",\"i\":\"L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMS5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMi5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMy5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwNC5qcGc=-L2QvMS9oZGRzL1RSLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwNS5qcGc=\",\"coid\":\"TR\",\"air\":\"ADA\",\"n\":\"ADANA AIRPORT HOTEL\",\"hotel-commerge\":[{\"provider\":\"booking\",\"url\":\"http://94.102.77.210:8080/Hotel/Commerce?commerge-data=aHR0cDovL3d3dy5ib29raW5nLmNvbS9ob3RlbC90ci9haXJwb3J0LmVuLWdiLmh0bWw/YWlkPTM0NzM2MyZob3RlbGlkcz0yODIwODEmc2VsZWN0ZWRfY3VycmVuY3k9VFJZJiZsYWJlbD1tb3RlbF8=\"},{\"provider\":\"expedia\",\"url\":\"http://94.102.77.210:8080/Hotel/Commerce?commerge-data=aHR0cDovL3d3dy50cmF2ZWxub3cuY29tL3RlbXBsYXRlcy8zODI5OTYvaG90ZWxzLzMzNjcwNC9yb29tcz9yb29tc0NvdW50PTEmcm9vbXNbMF0uYWR1bHRzQ291bnQ9MiZyb29tc1swXS5jaGlsZHJlbkNvdW50PTAmY3VycmVuY3k9VFJZJmN1cnJlbmN5U3ltYm9sPVRSWSZsYW5nPVRy\"}],\"p\":\"903224322626\",\"rc\":\"43\",\"fx\":\"ADA\",\"s\":\"\",\"w\":\"\",\"x\":\"36,99470900000000000\",\"y\":\"35,21337600000000000\",\"id\":\"000001\",\"Location\":\"Within city\n\"}}}";
	        String jsonMsg="{\"text\":{\"request\":\"1\",\"provider\":\"00003\",\"lang\":\"TR\",\"country\":\""+rowItems.get(position).getCountry().trim()
	    			+"\",\"hotelId\":\""+rowItems.get(position).getKod().trim()
	    			+"\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
	        		
	        
	        new connectTask().execute(jsonMsg);
	        //Yonlen.yonlen(otel_bilgi.class, myc,jsonMsg);
	    }
		
		public class connectTask extends AsyncTask<String,String,String> {	   
	    	 
	    	 String svr_gelen_msg="";
	    	 private ProgressDialog dialog=new ProgressDialog(java_otel_grup.this);
	    	 
	    	 @Override
	    	 protected void onPreExecute() {
	             dialog.setMessage("Server'e baðlanýyor...");
	             dialog.show();
	         }
	    	

			@Override
	         protected String doInBackground(String... params) {	  
	        	 
	        	 try
	             {
		        		   Socket socket = new Socket(SERVERIP,SERVERPORT);// Baðlanýlacak Server'un IP'si  ve Portu 
		        	       PrintStream  cikis   = new PrintStream(socket.getOutputStream(),true);// Client için Çýkýþ Kanalý oluþturuluyor.
		        	       BufferedReader  giris   = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Client için Giriþ Kanalý oluþturuluyor.
		        	       String message=params[0];
						   //dialog.setMessage("Server'e baðlandý.");
						   cikis.println(message);//Client'in çýkýþ kanalýna atýyor sonra da Server'un giriþ kanalýna giriyor.
						   Log.i("grup_gönderilen", message);
						   svr_gelen_msg= giris.readLine();//"{\"hotel\":{\"HotelInfo\":{\"a\":\"CORNICHE SUD RUE DU COMMERCE  BP287\",\"Hotel.Description\":\"Further information about this hotel will be available shortly\n\",\"c\":\"CONAKRY\",\"d\":\"Hotel.Description-Location-Note-\",\"e\":\"CONTACT@NFSENEGAL.COM\",\"f\":\"179-108-152-141-260-244-246-198-131-129-14-37\",\"i\":\"L2QvMS9oZGRzL0dOLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMS5qcGc=-L2QvMS9oZGRzL0dOLzAwLzAwLzAxL2hvdGVsc3Byby9pbWFnZS9vXzAwMDAwMS4wMDAwMi5qcGc=\",\"coid\":\"GN\",\"air\":\"CKY\",\"n\":\"Novotel Ghi Conakry\",\"hotel-commerge\":[],\"p\":\"221339570770\",\"rc\":\"196\",\"fx\":\"CKY\",\"s\":\"3\",\"w\":\"www.novotel.com/gb/hotel-0509-novotel-ghi-conakry/\",\"Note\":\"Extra-person charges may apply and vary depending on hotel policy. Government-issued photo identification and a credit card or cash deposit are required at check-in for incidental charges. Special requests are subject to availability upon check-in and may incur additional charges. Special requests cannot be guaranteed.\n\",\"x\":\"09.53702900000000000\",\"y\":\"-13.6784700000000610\",\"id\":\"000001\",\"Location\":\"The Novotel Ghi Conakry is a 3 star hotel in the centre of Conakry in a residential area close to the sea.\n\"}}}";
						   Log.i("grup_gelen", svr_gelen_msg);
		                   //dialog.dismiss();
		                   cikis.close();//Çýkýþ kanalýný kapatýyor.
		                   giris.close();//Giriþ kanalýný kapatýyor.
		                   socket.close();//Soket'i kapatýyor.
	              }
	             catch (Throwable e)
	             {
	                   Log.i("Baðlantý Mesajý", e.getMessage());
	             }
	            
	  
	             return null;
	         }


			@Override
			protected void onPostExecute(String result) {
				Yonlen.yonlen(otel_bilgi.class,myc,svr_gelen_msg);
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
