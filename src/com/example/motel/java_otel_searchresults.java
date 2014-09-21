package com.example.motel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class java_otel_searchresults extends Activity implements OnItemClickListener, OnQueryTextListener{

	
	private Bundle extras = null;
	final static String server_respond="";
	private static String svr_gelen_msg="{\"seaarch-results\":[]}";
	ListView listView;
	classSessionManager sessionManager;
    List<classSearchedRows> rowItems;
    Context myc=java_otel_searchresults.this;
    String query="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sayfa_otel_searchresults);
		
		sessionManager=new classSessionManager(myc);
		handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
       
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            intent.getStringExtra(SearchManager.QUERY);
            
            
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //searchView.setQuery("", false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(this);
        
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
    
    //diðer sayfadan gelen verileri alma
	private String veri_al()
	{
		extras=getIntent().getExtras();
		String bilgi=extras.getString(server_respond);
		return bilgi;
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		String jsonMsg="";
		Log.i("týklanan tipi",rowItems.get(position).getType().trim());
		if (rowItems.get(position).getType().trim().equals("0"))
		{
			jsonMsg="{\"text\":{\"request\":\"1\",\"provider\":\"00003\",\"lang\":\"TR\",\"country\":\""+rowItems.get(position).getCountry().trim()
			+"\",\"hotelId\":\""+rowItems.get(position).getKod().trim()
			+"\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
			new classAsyncTCPConnection(myc, otel_bilgi.class).execute(jsonMsg);
		}
		else if(rowItems.get(position).getType().trim().equals("c"))
		{
			jsonMsg="{\"text\":{\"request\":\"0\",\"getPrice\":\"true\",\"kType\":\"0\",\"currency\":\"TRY\",\"priceType\":\"t\",\"city\":\""+rowItems.get(position).getKod().trim()+"\",\"provider\":\"00003\",\"lang\":\"EN\",\"country\":\""+rowItems.get(position).getCountry().trim()+"\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
			Log.i("týklanN",rowItems.get(position).getCountry()+"-"+ rowItems.get(position).getKod().trim());
			new classAsyncTCPConnection(myc, java_otel_grup.class).execute(jsonMsg);
		}
		else if(rowItems.get(position).getType().trim().equals("e"))
		{
			jsonMsg="{\"text\":{\"request\":\"6\",\"getPrice\":\"true\",\"kType\":\"0\",\"currency\":\"TRY\",\"priceType\":\"t\",\"county\":\""+rowItems.get(position).getKod().trim()+"\",\"provider\":\"00003\",\"lang\":\"TR\",\"country\":\""+rowItems.get(position).getCountry().trim()+"\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
			new classAsyncTCPConnection(myc, java_otel_grup.class).execute(jsonMsg);
		}
		else if(rowItems.get(position).getType().trim().equals("f"))
		{
			jsonMsg="{\"text\":{\"request\":\"7\",\"getPrice\":\"true\",\"kType\":\"0\",\"currency\":\"TRY\",\"priceType\":\"t\",\"region\":\""+rowItems.get(position).getKod().trim()+"\",\"provider\":\"00003\",\"lang\":\"TR\",\"country\":\""+rowItems.get(position).getCountry().trim()+"\",},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
			new classAsyncTCPConnection(myc, java_otel_grup.class).execute(jsonMsg);
		}
		else if(rowItems.get(position).getType().trim().equals("g"))
		{
			jsonMsg="{\"text\":{\"request\":\"8\",\"getPrice\":\"true\",\"kType\":\"0\",\"currency\":\"TRY\",\"priceType\":\"t\",\"popular\":\""+rowItems.get(position).getKod().trim()+"\",\"provider\":\"00003\",\"lang\":\"TR\",\"country\":\""+rowItems.get(position).getCountry().trim()+"\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
			new classAsyncTCPConnection(myc, java_otel_grup.class).execute(jsonMsg);
		}
	}
		
	 					
	
	//
	public void sendRequestForSearch(String gelen_mesaj)
	{
		classJasonParserGeneralSearch parser=new classJasonParserGeneralSearch();
		String[] names=parser.getSearchedNames(gelen_mesaj);
		String[] bolgeler=parser.getSearchedBolge(gelen_mesaj);
		String[] countries=parser.getSearchedCountries(gelen_mesaj);
		String[] counts=parser.getSearchedCounts(gelen_mesaj);
		String[] kod=parser.getSearchedIDs(gelen_mesaj);
		String[] type=parser.getSearchedTypes(gelen_mesaj);
		rowItems = new ArrayList<classSearchedRows>();
		for (int i = 0; i < names.length; i++) {
			classSearchedRows item = new classSearchedRows(names[i], countries[i],counts[i],kod[i],type[i],bolgeler[i]);
            rowItems.add(item);}
		 listView = (ListView) findViewById(R.id.searchList);
		 classSearchAdapter adapter = new classSearchAdapter(this,R.layout.result_search_item, rowItems);
		 listView.setAdapter(adapter);
	     listView.setOnItemClickListener(this);
	}
	
	//searchview listening each type from keyboard
	@Override
	public boolean onQueryTextSubmit(String submitted) {
		 
		return true;
	}
                                                                               
	@Override
	public boolean onQueryTextChange(String newText) {
		query=newText;
		String jsonMsg="{\"text\":{\"request\":\"5\",\"provider\":\"00003\",\"country\":\"TR\",\"key\":\""+query+"\",\"language\":\"tr\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
		 if(query.length()>1 & query.length()<14)
		 {			
			 new classTCPConnection().execute(jsonMsg);			
		 }
		return true;
	}
	
	public class classTCPConnection extends AsyncTask<String,String,String>{

		
		final static String    SERVERIP = "159.253.45.121";  
	    final static  int       SERVERPORT   = 8000;
		 private ProgressDialog dialog=new ProgressDialog(myc);
		 protected void onPreExecute() {
			 
	        dialog.setMessage("Otel Aranýyor...");
	        dialog.show();
	    }
		 
	    protected String doInBackground(String... message) {	  	   	 
	   	 try
	        {
	       		   Socket socket = new Socket(SERVERIP,SERVERPORT);// Baðlanýlacak Server'un IP'si  ve Portu 
	       	       PrintStream  cikis   = new PrintStream(socket.getOutputStream(),true);// Client için Çýkýþ Kanalý oluþturuluyor.
	       	       BufferedReader  giris   = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Client için Giriþ Kanalý oluþturuluyor.
	       	       //String jsonMsg="{\"text\":{\"request\":\"5\",\"provider\":\"00003\",\"country\":\"TR\",\"key\":\""+query+"\",\"language\":\"tr\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
					   
				  cikis.println(message[0]);//Client'in çýkýþ kanalýna atýyor sonra da Server'un giriþ kanalýna giriyor.
				  svr_gelen_msg = giris.readLine();//Server'dan gelen veri okunuyor ve deðiþkene atýlýyor.

			   		 cikis.close();//Çýkýþ kanalýný kapatýyor.
		             giris.close();//Giriþ kanalýný kapatýyor.
		             socket.close();//Soket'i kapatýyor.
	        } 
	        catch (Throwable e)
	        {
	              Log.i("Baðlantý Mesajý hatasý", e.getMessage());
	        }
	   	
	       

	        return svr_gelen_msg;
	    }
	    
	    @Override
		protected void onPostExecute(String result) {
	    	 dialog.dismiss();
	    	 sendRequestForSearch(svr_gelen_msg);
			super.onPostExecute(result);
		}

	}
}