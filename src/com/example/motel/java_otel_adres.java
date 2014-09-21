package com.example.motel;

import org.json.JSONException;
import org.json.JSONObject;











import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class java_otel_adres extends Activity {
	
	private Bundle extras = null;
	final static String server_respond="";
	Context myc=java_otel_adres.this;
	String gelen_mesaj="";
	classJasonParserOtelInfo otelInfo=new classJasonParserOtelInfo();
	classYonlen Yonlen =new classYonlen();
	classSessionManager sessionManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_adres);
		ActionBar actionBar = getActionBar();
		sessionManager=new classSessionManager(myc);
	    // Enabling Up / Back navigation
	    actionBar.show();
		
		gelen_mesaj=veri_al();
		try {
			
			String hotel_ad=otelInfo.getOtelName(gelen_mesaj);
			TextView tv_name=(TextView)findViewById(R.id.textView1);
			tv_name.setText(hotel_ad);
			//String hotel_id=otelInfo.getOtelID(gelen_mesaj);
			//TextView tv_id=(TextView)findViewById(R.id.kod);
			//tv_id.setText(hotel_id);
			String bolge=otelInfo.getOtelCity(gelen_mesaj);
			TextView tv_bolge=(TextView)findViewById(R.id.bolge);
			if(!bolge.equals(""))
				tv_bolge.setText(bolge);
			else
				tv_bolge.setVisibility(View.INVISIBLE);
			String hotel_adres=otelInfo.getOtelAdres(gelen_mesaj);
			String hotel_country=otelInfo.getOtelCountry(gelen_mesaj);
			TextView tv_adres=(TextView)findViewById(R.id.textView4);
			tv_adres.setText("-Adres :\n "+hotel_adres+" / "+bolge+" / "+hotel_country);
			String hotel_phone=otelInfo.getOtelPhone(gelen_mesaj);
			TextView tv_p=(TextView)findViewById(R.id.textView6);
			if(!hotel_phone.equals(""))
				tv_p.setText("-Tel :\n "+hotel_phone);
			else
				tv_p.setVisibility(View.INVISIBLE);
			
			String hotel_fax=otelInfo.getOtelFax("\n "+gelen_mesaj);
			TextView tv_fax=(TextView)findViewById(R.id.textView7);
			if(!hotel_fax.equals(""))
				tv_fax.setText("-Faks :\n "+hotel_fax);
			else
				tv_fax.setVisibility(View.INVISIBLE);			
			String hotel_mail=otelInfo.getOtelMail(gelen_mesaj);
			TextView tv_mail=(TextView)findViewById(R.id.textView8);
			if(!hotel_mail.equals(""))
				tv_mail.setText("-E-mail :\n "+hotel_mail);
			else
				tv_mail.setVisibility(View.INVISIBLE);
			
			
			//get web
			String hotel_web=otelInfo.getOtelWeb(gelen_mesaj);
			TextView tv_web=(TextView)findViewById(R.id.textView9);
			if(!hotel_web.equals(""))
				tv_web.setText("-Web :\n "+hotel_web);
			else
				tv_web.setVisibility(View.INVISIBLE);
			
			
			
			
			//stars
			ImageView img_stars=(ImageView)findViewById(R.id.imageView1);
			int otel_stars=otelInfo.getOtelStars(gelen_mesaj);
			if(otel_stars!=0)
				img_stars.setImageResource(otel_stars);
			else
				img_stars.setVisibility(View.INVISIBLE);
			
			//avatar resmini yükle
			String hotel_resimler=otelInfo.getOtelImages(gelen_mesaj);
			String[] arr_resim=hotel_resimler.split("-");
			ImageView hotel_image_holder=(ImageView) findViewById(R.id.imageView2);
			//if(arr_resim.length!=0)
			//{
		    com.example.motel.DownloadImageTask img_url=new com.example.motel.DownloadImageTask((ImageView) findViewById(R.id.imageView2));
			//img_url.execute("http://www.m-otel.com/img.php?path="+arr_resim[0]+"=&rx=500&ry=500");
			img_url.execute(arr_resim[0]);
			//}
			//else
				//hotel_image_holder.setImageResource(R.drawable.ic_launcher);
		} catch (Exception e) {
			Log.e("Hata:", "burda");
		}
		
	}
	
	
	//diðer sayfadan gelen verileri alma
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
