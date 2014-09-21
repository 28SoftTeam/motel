package com.example.motel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class classAsyncTCPConnection extends AsyncTask<String,String,String> {	   
	 
	final static String    SERVERIP = "159.253.45.121";  
    final static  int       SERVERPORT   = 8000;
    private String response_of_json;
    
    Context myc;
    Class<?> myClass;

	public classAsyncTCPConnection(Context myc2, Class<?> myClass2) {
		 myc=myc2;
		 myClass=myClass2;
	}
	@Override
	 protected void onPreExecute() 
	 {
		 
	 }
	 
	 
    @Override
    protected String doInBackground(String... message) {	  
   	 
   	 try
        {
       		   Socket socket = new Socket(SERVERIP,SERVERPORT);// Baðlanýlacak Server'un IP'si  ve Portu 
       	       PrintStream  cikis   = new PrintStream(socket.getOutputStream(),true);// Client için Çýkýþ Kanalý oluþturuluyor.
       	       BufferedReader  giris   = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Client için Giriþ Kanalý oluþturuluyor.
       	       cikis.println(message[0]);//Client'in çýkýþ kanalýna atýyor sonra da Server'un giriþ kanalýna giriyor.
       	    response_of_json = giris.readLine();//Server'dan gelen veri okunuyor ve deðiþkene atýlýyor.
       	    Log.i("doinbackground", response_of_json);
	       	   cikis.close();//Çýkýþ kanalýný kapatýyor.
	           giris.close();//Giriþ kanalýný kapatýyor.
	           socket.close();//Soket'i kapatýyor.
	           //classYonlen Yonlen =new classYonlen();
	    		//Yonlen.yonlen(myClass, myc, svr_gelen_msg);
         }
        catch (Throwable e)
        {
              Log.i("Baðlantý Mesajý", e.getMessage());
        }
       

        return null;
    }
    
 	@Override
	protected void onPostExecute(String result) {
   	 
 		classYonlen Yonlen =new classYonlen();
 		Yonlen.yonlen(myClass, myc, response_of_json);
 		Log.i("asyntask", response_of_json);
		super.onPostExecute(result);
	}

	
}