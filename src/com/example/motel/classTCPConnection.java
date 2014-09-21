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
		 
        dialog.setMessage("Otel Aranýyor...");
        dialog.show();
    }
	 
    protected String doInBackground(String... message) {	  
   	 
   	 try
        {
       		   Socket socket = new Socket(SERVERIP,SERVERPORT);// Baðlanýlacak Server'un IP'si  ve Portu 
       	       PrintStream  cikis   = new PrintStream(socket.getOutputStream(),true);// Client için Çýkýþ Kanalý oluþturuluyor.
       	       BufferedReader  giris   = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Client için Giriþ Kanalý oluþturuluyor.

				   dialog.setMessage("Otel Bulundu.");
                  
				   String jsonMsg="{\"text\":{\"request\":\"5\",\"provider\":\"00003\",\"country\":\"TR\",\"key\":\""
				   		+message+"\",\"language\":\"tr\"},\"auth\":{\"userName\":\"ogz\",\"password\":\"1234\"}}\r\n";
				   
				   cikis.println(jsonMsg);//Client'in çýkýþ kanalýna atýyor sonra da Server'un giriþ kanalýna giriyor.
                  svr_gelen_msg = giris.readLine();//Server'dan gelen veri okunuyor ve deðiþkene atýlýyor.
			
                  dialog.dismiss();
                  cikis.close();//Çýkýþ kanalýný kapatýyor.
                  giris.close();//Giriþ kanalýný kapatýyor.
                  socket.close();//Soket'i kapatýyor.
         }
        catch (Throwable e)
        {
              Log.i("Baðlantý Mesajý", e.getMessage());
        }
       

        return svr_gelen_msg;
    }
    
    protected void onPostExecute() {
        dialog.dismiss();
        	                        
    }
}