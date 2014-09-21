package com.example.motel;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

public class TCPClient {
	
	private String serverMessage;
	public static final String SERVERIP="159.253.45.121";
	//public static final String SERVERIP="194.27.101.122";
	public static final int SERVERPORT=8000;
	private OnMessageReceived mMessageListener=null;
	private boolean mRun=false;
	
	PrintWriter out;
	BufferedReader in;
	
	//Constructor
	public TCPClient(OnMessageReceived listener){
		mMessageListener=listener;
	}
	
	//Send MEssage
	public void sendMessage(String message){
		if(out!=null && out.checkError()){
			out.println(message);
			out.flush();
		}
	}
	
	public void stopClient(){
		mRun=false;
	}
	
	public void run(){
		mRun=true;
		//my computer ip
		try{
		InetAddress serverAddr=InetAddress.getByName(SERVERIP);
		Log.e("TCP Client", "C: Connecting...");
		
		//create a socket
		Socket socket=new Socket(serverAddr, SERVERPORT);
		try
		{
			//send message to server
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			Log.e("TCP Client", "C: Sent.");			 
            Log.e("TCP Client", "C: Done.");
            
            //receive the message which the server sends back
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
          //in this while the client listens for the messages sent by the server
            while(mRun)
            {
            	serverMessage= in.readLine();            	 
                if (serverMessage != null && mMessageListener != null) {
                    //call the method messageReceived from MyActivity class
                    mMessageListener.messageReceived(serverMessage);
                }
                serverMessage = null;
            }
           Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
		}//try ends
		catch (Exception e) {			 
			Log.e("TCP", "S: Error"+ e.toString());
        } 
		finally {
            //the socket must be closed. It is not possible to reconnect to this socket
            // after it is closed, which means a new socket instance has to be created.
            socket.close();
        }
	}//first try ends
		catch (Exception e) {			 
			Log.e("TCP", "C: Error"+e); 
        }
	
	}
	//Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}