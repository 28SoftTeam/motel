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
       		   Socket socket = new Socket(SERVERIP,SERVERPORT);// Ba�lan�lacak Server'un IP'si  ve Portu 
       	       PrintStream  cikis   = new PrintStream(socket.getOutputStream(),true);// Client i�in ��k�� Kanal� olu�turuluyor.
       	       BufferedReader  giris   = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Client i�in Giri� Kanal� olu�turuluyor.
       	       cikis.println(message[0]);//Client'in ��k�� kanal�na at�yor sonra da Server'un giri� kanal�na giriyor.
       	    response_of_json = giris.readLine();//Server'dan gelen veri okunuyor ve de�i�kene at�l�yor.
       	    Log.i("doinbackground", response_of_json);
	       	   cikis.close();//��k�� kanal�n� kapat�yor.
	           giris.close();//Giri� kanal�n� kapat�yor.
	           socket.close();//Soket'i kapat�yor.
	           //classYonlen Yonlen =new classYonlen();
	    		//Yonlen.yonlen(myClass, myc, svr_gelen_msg);
         }
        catch (Throwable e)
        {
              Log.i("Ba�lant� Mesaj�", e.getMessage());
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