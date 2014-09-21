package com.example.motel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class classTCPConnection extends AsyncTask<String,String,String>{

	private String svr_gelen_msg="";
	private Context myc;
	final static String    SERVERIP = "159.253.45.121";  
    final static  int       SERVERPORT   = 8000;
	 private ProgressDialog dialog;
	 public classTCPConnection (Context context){
         myc = context;
         dialog=new ProgressDialog(myc);
    }
	 protected void onPreExecute() {
		 
        dialog.setMessage("Otel Aran�yor...");
        dialog.show();
    }
	 
    protected String doInBackground(String... message) {	  
   	 
   	 try
        {
       		   Socket socket = new Socket(SERVERIP,SERVERPORT);// Ba�lan�lacak Server'un IP'si  ve Portu 
       	       PrintStream  cikis   = new PrintStream(socket.getOutputStream(),true);// Client i�in ��k�� Kanal� olu�turuluyor.
       	       BufferedReader  giris   = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Client i�in Giri� Kanal� olu�turuluyor.

				   dialog.setMessage("Otel Bulundu.");
                  
				   String jsonMsg="{\"text\":{\"request\":\"5\",\"provider\":\"00003\",\"country\":\"TR\",\"key\":\""
				   		+message+"\",\"language\":\"tr\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
				   
				   cikis.println(jsonMsg);//Client'in ��k�� kanal�na at�yor sonra da Server'un giri� kanal�na giriyor.
                  svr_gelen_msg = giris.readLine();//Server'dan gelen veri okunuyor ve de�i�kene at�l�yor.
			
                  dialog.dismiss();
                  cikis.close();//��k�� kanal�n� kapat�yor.
                  giris.close();//Giri� kanal�n� kapat�yor.
                  socket.close();//Soket'i kapat�yor.
         }
        catch (Throwable e)
        {
              Log.i("Ba�lant� Mesaj�", e.getMessage());
        }
       

        return svr_gelen_msg;
    }
    
    protected void onPostExecute() {
        dialog.dismiss();
        	                        
    }
}