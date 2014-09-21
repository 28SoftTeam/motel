package com.example.motel;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;



public class otel_bilgi extends Activity  {
	
	private Bundle extras = null;
	final static String server_respond="";
	Context myc=otel_bilgi.this;
	String gelen_mesaj="";
	classJasonParserOtelInfo otelInfo=new classJasonParserOtelInfo();
	classSessionManager sessionManager;
	classYonlen Yonlen =new classYonlen();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_bilgi);
		 ActionBar actionBar = getActionBar();
		 actionBar.show();
	     // Enabling Up / Back navigation
		 sessionManager=new classSessionManager(myc);
		
		
		 Button button1=(Button)findViewById(R.id.button1);
		button1.setClickable(true);
		Button button2=(Button)findViewById(R.id.button2);
		button2.setClickable(true);		
		Button button3=(Button)findViewById(R.id.button3);
		button3.setClickable(true);		
		Button button4=(Button)findViewById(R.id.button4);
		button4.setClickable(true);
		Button button5=(Button)findViewById(R.id.button5);
		TextView tv_otel_ad=(TextView)findViewById(R.id.textView1);
		TextView tv_otel_adres=(TextView)findViewById(R.id.textView2);
		TextView tv_otel_bolge=(TextView)findViewById(R.id.bolge);
		ImageView img_stars=(ImageView)findViewById(R.id.imageView1);
		
		
		//Diðer sayfadan gelen veriyi al ve parse et
		gelen_mesaj=veri_al();
		if(!gelen_mesaj.equals(null) | !gelen_mesaj.equals(""))
		{
		try {
			String hotel_ad=otelInfo.getOtelName(gelen_mesaj);
			tv_otel_ad.setText(hotel_ad);
			//String hotel_id=otelInfo.getOtelID(gelen_mesaj);
			//tv_otel_kod.setText(hotel_id);
			String hotel_adres=otelInfo.getOtelAdres(gelen_mesaj);
			tv_otel_adres.setText(hotel_adres);
			String hotel_city=otelInfo.getOtelCity(gelen_mesaj);
			tv_otel_bolge.setText(hotel_city);			
			
			int hotel_stars=otelInfo.getOtelStars(gelen_mesaj);
			if(hotel_stars!=0)
				img_stars.setImageResource(hotel_stars);
			else
				img_stars.setVisibility(View.INVISIBLE);
			
			//resimleri al
			String hotel_resimler=otelInfo.getOtelImages(gelen_mesaj);
			String[] arr_resim=hotel_resimler.split("-");
			//ImageView image_holder=(ImageView)findViewById(R.id.imageView2);
			//if(arr_resim.length!=0)
			//{
			com.example.motel.DownloadImageTask img_url=new com.example.motel.DownloadImageTask((ImageView)findViewById(R.id.imageView2));
			img_url.execute(arr_resim[0]);
			//img_url.execute("http://www.m-otel.com/img.php?path="+arr_resim[0]+"=&rx=500&ry=500");
			//}
			//else
				//image_holder.setImageResource(R.drawable.ic_launcher);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Hata:", e.getMessage());
		}
		
		}
	
	//button1-iletiþim bilgileri týklandýðýnda
	button1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			classYonlen yonlen=new classYonlen();
			yonlen.yonlen(java_otel_adres.class, myc,veri_al());		
		}
	} );
	
	
	//button2-harita bilgileri týklandýðýnda
	button2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {			
			classYonlen yonlen=new classYonlen();
			yonlen.yonlen(java_otel_harita.class, myc,gelen_mesaj);					
		}
	} );
			
	//button3-resim bilgileri týklandýðýnda
	button3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			classYonlen yonlen=new classYonlen();
			yonlen.yonlen(java_otel_resim.class, myc,gelen_mesaj);	
								}
	} );
			
	//button4-taným bilgileri týklandýðýnda
	button4.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			classYonlen yonlen=new classYonlen();
			yonlen.yonlen(java_otel_tanim.class, myc,veri_al());	
		}
	});
			
	//buton rezervasyon týklandýðýnda
	button5.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			classYonlen yonlen=new classYonlen();
			yonlen.yonlen(java_otel_rezervasyon.class, myc,"otel==="+gelen_mesaj);	
		}
	});
				
	}
	
	
	private String veri_al()
	{
		extras=getIntent().getExtras();
		String bilgi=extras.getString(server_respond);
		return bilgi;
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
