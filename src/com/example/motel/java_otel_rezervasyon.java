package com.example.motel;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

@SuppressLint({ "SimpleDateFormat", "ResourceAsColor" })
public class java_otel_rezervasyon extends Activity{
	
	
	private String hotel_ad="";
	private String bolge="";
	private String country="";
	final static String server_respond="";	
	Button btn_rezervasyon;
	private Bundle extras = null;
	Context context=java_otel_rezervasyon.this;
	RadioGroup rg;
	private String otel_bilgi;
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private int startYear, startMonth, startDay;
	EditText giris,cikis;
	DatePicker dpStartDate,dpEndDate;
	classYonlen Yonlen =new classYonlen();
	Context myc=java_otel_rezervasyon.this;
	String gelen_mesaj;
	classJasonParserOtelInfo<Object> otelInfo=new classJasonParserOtelInfo<Object>();
	 // Session Manager Class
    classSessionManager sessionManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		sessionManager=new classSessionManager(myc);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_rezervasyon);
		btn_rezervasyon=(Button)findViewById(R.id.btn_rezervasyon);
		giris=(EditText)findViewById(R.id.editText1);
		cikis=(EditText)findViewById(R.id.editText2);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String currentDateandTime = sdf.format(new Date());
		giris.setText(currentDateandTime);
		cikis.setText(currentDateandTime);
		btn_rezervasyon.setClickable(true);
		gelen_mesaj=veri_al();
		String[] veri=veri_al().split("===");
		if(veri[0].equalsIgnoreCase("otel"))
		{
			otel_bilgi=veri[1];
		   baslikEkle(otel_bilgi);
		}else
		{
			otel_bilgi=veri[3];
			baslikEkle(otel_bilgi);
			giris.setText(veri[0]);
			cikis.setText(veri[1]);
		}
				
		giris.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePicker("Giriþ");
			}
		});
		
		
		cikis.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePicker("Çýkýþ");
			}
		});
		
		
		
		btn_rezervasyon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				classYonlen Yonlen =new classYonlen();
				//Yonlen.yonlen(java_otel_commerge.class,context);
				// Session Manager				
				String jsonMsg="{\"text\":{\"request\":\"1\",\"getPrice\":\"true\",\"hotelId\":\""+otelInfo.getOtelID(veri_al().split("===")[1])+"\",\"provider\":\"00003\",\"lang\":\"TR\",\"country\":\""+otelInfo.getOtelCountry(gelen_mesaj.split("===")[1])+"\",\"datein\"=\""+giris.getText().toString()+"\",\"dateout\"=\""+cikis.getText().toString()+"\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
		        
		       
		        int id = rg.getCheckedRadioButtonId();
				RadioButton radioButton = (RadioButton) findViewById(id);
				String commerge = radioButton.getText().toString(); 
				
				
				//get dates
				String reserved_in=giris.getText().toString();
				String reserved_out=cikis.getText().toString();
				
				classReservationInfo reservationInfo=new classReservationInfo(myc);
				reservationInfo.setReservedIn(reserved_in);
				reservationInfo.setReservedOut(reserved_out);
				reservationInfo.setReservedCity(otelInfo.getOtelCity(veri_al().split("===")[1]));
				reservationInfo.setReservedCountry(otelInfo.getOtelCountry(veri_al().split("===")[1]));
				reservationInfo.setReservedHotel(otelInfo.getOtelName(veri_al().split("===")[1]));
				reservationInfo.setReservedCommerge(commerge);
				
				
				if(sessionManager.isLoggedIn())
				{
				
				//get values for server registration
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(myc);
				String user_email=pref.getString("email", null);
				
				//selected commerge
					
				
				Log.i("reservation", "email:"+user_email+",\n"+"hotel:"+hotel_ad+",\n"+"city:"+bolge+",\n"+"country:"+country+",\n"
				+"commerge:"+commerge+",\n"+"date_in:"+reserved_in+",\n"+"date_out:"+reserved_out);
					
				//hareket bilgilerini servera yazýyor
				//new AsyncCallWebServices().execute(user_email,hotel_ad,bolge,country,commerge,reserved_in,reserved_out);
				
				
				//burda commerge sayfasýna gitmek için async çaðýr
				Yonlen.yonlen(java_otel_commerge.class, myc,commerge+"==="+jsonMsg);
				
				
				}
				else
				{
					Yonlen.yonlen(java_otel_usertani.class,context,commerge+"==="+jsonMsg);					
					
				}
			}
		});
		
		
	}
	
	//url image
	 public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		    ImageView bmImage;

		    public DownloadImageTask(ImageView bmImage) {
		        this.bmImage = bmImage;
		    }

		    protected Bitmap doInBackground(String... urls) {
		        String urldisplay = urls[0];
		        Bitmap mIcon11 = null;
		        try {
		            InputStream in = new java.net.URL(urldisplay).openStream();
		            mIcon11 = BitmapFactory.decodeStream(in);
		        } catch (Exception e) {
		            Log.e("Error", e.getMessage());
		            e.printStackTrace();
		        }
		        return mIcon11;
		    }

		    protected void onPostExecute(Bitmap result) {
		        bmImage.setImageBitmap(result);
		    }
		}
	 //end of url image
	
	private String veri_al()
	{
		extras=this.getIntent().getExtras();
		String bilgi=extras.getString(server_respond);
		return bilgi;
	}
	
	public void baslikEkle(String veri)
	{
		try {
			
			hotel_ad=otelInfo.getOtelName(veri);
			TextView tv_name=(TextView)findViewById(R.id.textView1);
			tv_name.setText(hotel_ad);
			//String hotel_id=otelInfo.getOtelID(veri);
			//TextView tv_id=(TextView)findViewById(R.id.kod);
			//tv_id.setText(hotel_id);
			bolge=otelInfo.getOtelCity(veri);
			TextView tv_bolge=(TextView)findViewById(R.id.bolge);
			tv_bolge.setText(bolge);
			country=otelInfo.getOtelCountry(veri);
			ImageView img_stars=(ImageView)findViewById(R.id.imageView1);
			int otel_stars=otelInfo.getOtelStars(veri);
			if(otel_stars!=0)
				img_stars.setImageResource(otel_stars);
			else
				img_stars.setVisibility(View.INVISIBLE);
			//avatar resmini yükle
			String hotel_resimler=otelInfo.getOtelImages(veri);
			String[] arr_resim=hotel_resimler.split("-");
			DownloadImageTask img_url=new DownloadImageTask((ImageView) findViewById(R.id.imageView2));
			//img_url.execute("http://www.m-otel.com/img.php?path="+arr_resim[0]+"=&rx=500&ry=500");
			img_url.execute(arr_resim[0]);
			//rezervasyon yapýlacak yerler
			LinearLayout layout=(LinearLayout)findViewById(R.id.radioLayout);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			
			HashMap<?, ?> commerges=otelInfo.getOtelCommerge(veri);
			final RadioButton[] rb = new RadioButton[commerges.size()];
		    rg = new RadioGroup(this); 
			Set comset=commerges.entrySet();
			Iterator iter=comset.iterator();
			if(iter.hasNext()){
			int no=0;
			while(iter.hasNext())
			{
				@SuppressWarnings("rawtypes")
				Map.Entry me = (Map.Entry)iter.next();
				rb[no]  = new RadioButton(this);
		        rg.addView(rb[no]); //the RadioButtons are added to the radioGroup instead of the layout
		        rb[no].setText(me.getKey().toString());
		        no++;
			}
			rb[0].setChecked(true);
			layout.addView(rg);
			}else
			{
				btn_rezervasyon.setActivated(false);
				btn_rezervasyon.setClickable(false);
				classAlertDialog alert = new classAlertDialog();
				alert.showAlertDialog(java_otel_rezervasyon.this, "Dikkat!", "Bu otel için rezervasyon yapýlamamaktadýr.", false);
				alert.wait(3000);
				classYonlen Yonlen =new classYonlen();
				Yonlen.yonlen(MainActivity.class,context);
			}
		} catch (Exception e) {
			Log.e("Hata:", "burda");
		}
	}	
	
	//datepicker starter
	public void showDatePicker(final String durum) {
	    // Inflate your custom layout containing 2 DatePickers
	    LayoutInflater inflater = (LayoutInflater)getLayoutInflater();
	    View customView = inflater.inflate(R.layout.custom_calendar, null); 
	    

	    // Define your date pickers
	    
	    dpStartDate = (DatePicker) customView.findViewById(R.id.dpStartDate);
	    if(durum.equalsIgnoreCase("giriþ"))
	    {
		    
		    try {
		    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String currentDateandTime = sdf.format(new Date());
				dpStartDate.setMinDate(sdf.parse(currentDateandTime).getTime());
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else if(durum.equalsIgnoreCase("çýkýþ"))
	    {
		    try {
				dpStartDate.setMinDate(sdf.parse(giris.getText().toString()).getTime());
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    // Build the dialog
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setView(customView); // Set the view of the dialog to your custom layout
	    builder.setTitle(durum+" Tarihini Seçiniz");
	    builder.setPositiveButton("Seçiniz", new DialogInterface.OnClickListener(){
	        @SuppressWarnings("deprecation")
			@Override
	        public void onClick(DialogInterface dialog, int which) {
	        	
	            startYear = dpStartDate.getYear()-1900;
	            startMonth = dpStartDate.getMonth();
	            startDay = dpStartDate.getDayOfMonth();
	            String formatedDate = sdf.format(new Date(startYear, startMonth, startDay));
	            if(durum.equals("Giriþ"))
	            	giris.setText(formatedDate);
	            else
	            	cikis.setText(formatedDate);
	            dialog.dismiss();
	            datePickerSetMinDate();
	        }});
	    
	    // Create and show the dialog
	    builder.create().show();
	}
	
	public void datePickerSetMinDate()
	{
		        
		try {
			long girisTarih = sdf.parse(giris.getText().toString()).getTime();
			long cikisTarih=sdf.parse(cikis.getText().toString()).getTime();
			 if(girisTarih>cikisTarih)
			 {
		        	cikis.setText(sdf.format(new Date(girisTarih)));
			 }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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



