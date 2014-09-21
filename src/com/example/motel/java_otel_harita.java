package com.example.motel;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

public class java_otel_harita extends Activity{
	
	private Bundle extras = null;
	private GoogleMap aMap;
	final static String server_respond="";
	classYonlen Yonlen =new classYonlen();
	Context myc=java_otel_harita.this;
	classSessionManager sessionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_harita);
		ActionBar actionBar = getActionBar();	
		sessionManager=new classSessionManager(myc);
	    // Enabling Up / Back navigation
	    actionBar.show();
		//Diðer sayfadan gelen veriyi al ve parse et
				final String veri=veri_al();
				try {
					
					JSONObject obj = new JSONObject(veri);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					String hotel_ad=hotel_info.getString("n")+" Oteli";
					double X=hotel_info.getDouble("x");
					double Y=hotel_info.getDouble("y");
					//haritayý çaðýr
					setUpMapIfNeeded(X,Y,hotel_ad);
					
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.getStackTrace();
				}
		
	}
	
	private String veri_al()
	{
		extras=getIntent().getExtras();
		String bilgi=extras.getString(server_respond);
		return bilgi;
	}
	
	//haritanýn varlýðýný kontrol eden method
	private void setUpMapIfNeeded(Double X, Double Y, String hotel_ad) {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (aMap == null) {
	        aMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	                            .getMap();
	        // Check if we were successful in obtaining the map.
	        if (aMap != null) {
	            // The Map is verified. It is now safe to manipulate the map.
	        	LatLng hotel = new LatLng(X,Y);
	        	MarkerOptions marker = new MarkerOptions().position(new LatLng(X, Y)).title(hotel_ad);
	        	 
	        	// adding marker
	        	aMap.addMarker(marker);
	            //aMap.addMarker(new MarkerOptions().position(hotel).title(hotel_ad));
	            //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hotel, 13));  	        	

	        }
	    }
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
