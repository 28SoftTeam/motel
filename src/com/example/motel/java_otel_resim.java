package com.example.motel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;




@SuppressWarnings("deprecation")
public class java_otel_resim extends Activity{

	private Gallery gallery;
    private ImageView imgView;
    private String[] Imgid;
    private Bundle extras = null;
	final static String server_respond="";
	Context myc=java_otel_resim.this;
	classSessionManager sessionManager;
	static String gelen_mesaj="";
	classJasonParserOtelInfo otelInfo=new classJasonParserOtelInfo();
	classYonlen Yonlen =new classYonlen();
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_resim);
		  imgView = (ImageView)findViewById(R.id.secilen_resim);
		  sessionManager=new classSessionManager(myc);
		  String gelen_mesaj=veri_al();
		  String resimler=otelInfo.getOtelImages(gelen_mesaj);
		  Imgid=resimler.split("-");
		  com.example.motel.DownloadImageTask img_url=new com.example.motel.DownloadImageTask((ImageView) findViewById(R.id.secilen_resim));
			img_url.execute(Imgid[0]);
	         gallery = (Gallery) findViewById(R.id.gallery);
	         gallery.setAdapter(new AddImgAdp(this));
	         
	         gallery.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView parent, View v, int position, long id) {
	            	com.example.motel.DownloadImageTask img_url=new com.example.motel.DownloadImageTask((ImageView) findViewById(R.id.secilen_resim));
	    			//img_url.execute("http://www.m-otel.com/img.php?path="+Imgid[position]+"=&rx=500&ry=500");
	            	img_url.execute(Imgid[position]);

	            }

	        });
	}


	public class AddImgAdp extends BaseAdapter {
        private Context cont;
        int GalItemBg;
        public AddImgAdp(Context c) {

            cont = c;
            TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);
            GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
            typArray.recycle();

        }


        public int getCount() {
            return Imgid.length;
        }


        public Object getItem(int position) {
            return position;
        }


        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imgView = new ImageView(cont);
            com.example.motel.DownloadImageTask img_url=new com.example.motel.DownloadImageTask(imgView);
			//img_url.execute("http://www.m-otel.com/img.php?path="+Imgid[position]+"=&rx=500&ry=500");
            img_url.execute(Imgid[position]);
            imgView.setLayoutParams(new Gallery.LayoutParams(250, 250));
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);      
            return imgView;

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
