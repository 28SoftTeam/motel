package com.example.motel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TableRow;
import android.widget.TextView;

public class java_otel_tanim extends Activity{

	private Bundle extras = null;
	final static String server_respond="";
	classYonlen Yonlen =new classYonlen(); 
	Context myc=java_otel_tanim.this;
	static String gelen_mesaj="";
	classSessionManager sessionManager;
	classJasonParserOtelInfo otelInfo=new classJasonParserOtelInfo();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.sayfa_otel_tanim);
		sessionManager=new classSessionManager(myc);
		try {
			  gelen_mesaj=veri_al();
			String hotel_ad=otelInfo.getOtelName(gelen_mesaj);
			TextView tv_name=(TextView)findViewById(R.id.textView1);
			tv_name.setText(hotel_ad);
			//String hotel_id=otelInfo.getOtelID(gelen_mesaj);
			//TextView tv_id=(TextView)findViewById(R.id.kod);
			//tv_id.setText(hotel_id);
			String bolge=otelInfo.getOtelCity(gelen_mesaj);
			TextView tv_bolge=(TextView)findViewById(R.id.bolge);
			tv_bolge.setText(bolge);
			ImageView img_stars=(ImageView)findViewById(R.id.imageView1);
			int otel_stars=otelInfo.getOtelStars(gelen_mesaj);
			if(otel_stars!=0)
				img_stars.setImageResource(otel_stars);
			else
				img_stars.setVisibility(View.INVISIBLE);
			//avatar resmini yükle
			String hotel_resimler=otelInfo.getOtelImages(gelen_mesaj);
			String[] arr_resim=hotel_resimler.split("-");
			DownloadImageTask img_url=new DownloadImageTask((ImageView) findViewById(R.id.imageView2));
			//img_url.execute("http://www.m-otel.com/img.php?path="+arr_resim[0]+"=&rx=500&ry=500");
			img_url.execute(arr_resim[0]);
			//room count
			String room_no=otelInfo.getOtelRoomCount(gelen_mesaj);
			TextView tv_tanim=(TextView)findViewById(R.id.textView4);
			tv_tanim.setText("Oda Sayýsý:\n"+room_no);
			
			
			String hotel_tanim=otelInfo.getOtelDescriptions(gelen_mesaj);
			String tanim="";
			String[] arr1=hotel_tanim.split("-");
			LinearLayout layout=(LinearLayout)findViewById(R.id.desclayout);
			int tvid=0;
			for (int i = 0; i < arr1.length; i++)
			{
				tanim="-"+arr1[i]+":\n"+ otelInfo.getOtelEachDescription(veri_al(),arr1[i]);
				//TextView tv = (TextView)getLayoutInflater().inflate(R.layout.template_textview, null);
				TextView tv=new TextView(myc);
				LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				tv.setId(tvid);
				//tv.requestLayout();				
			    tv.setText(tanim);
			    layout.addView(tv);
			    tvid++;
			    tanim="";
			}
			
		} catch (Exception e) {
			e.getStackTrace();
		}			
		
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
