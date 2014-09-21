package com.example.motel;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class classJasonParser {

	//getOtelIds
		public String[] getOtelIDs(String gelen_mesaj)
		{
			String[] kodlar=new String[1000];
			ArrayList<String> list = new ArrayList<String>();
			try {			
				JSONObject obj = new JSONObject(gelen_mesaj);
				JSONArray arr = obj.getJSONArray("hotels");
				for(int i=0;i<arr.length();i++)
				{
					JSONObject id=arr.getJSONObject(i);
					list.add(id.getString("id"));
				}
				kodlar=list.toArray(new String[list.size()]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			return kodlar;
		}
		//end of getOtelIds
		
		//getOtelNames
			public String[] getOtelNames(String gelen_mesaj)
			{
				String[] kodlar=new String[1000];
				ArrayList<String> list = new ArrayList<String>();
				try {			
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONArray arr = obj.getJSONArray("hotels");
					for(int i=0;i<arr.length();i++)
					{
						JSONObject id=arr.getJSONObject(i);
						list.add(id.getString("n"));
					}
					kodlar=list.toArray(new String[list.size()]);
				} catch (JSONException e) {
					e.printStackTrace();
				}		
				
				return kodlar;
			}
		//end of getOtelNames
			
			//getOtelImages
					public String[] getOtelImages(String gelen_mesaj)
					{
						String[] kodlar=new String[1000];
						ArrayList<String> list = new ArrayList<String>();
						try {			
							JSONObject obj = new JSONObject(gelen_mesaj);
							JSONArray arr = obj.getJSONArray("hotels");
							for(int i=0;i<arr.length();i++)
							{
								JSONObject id=arr.getJSONObject(i);
								list.add(id.getString("i"));
							}
							kodlar=list.toArray(new String[list.size()]);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		
						
						return kodlar;
					}
				//end of getOtelImages
					
					//getOtelCity
					public String[] getOtelCity(String gelen_mesaj)
					{
						String[] kodlar=new String[1000];
						ArrayList<String> list = new ArrayList<String>();
						try {			
							JSONObject obj = new JSONObject(gelen_mesaj);
							JSONArray arr = obj.getJSONArray("hotels");
							for(int i=0;i<arr.length();i++)
							{
								JSONObject id=arr.getJSONObject(i);
								list.add(id.getString("c"));
							}
							kodlar=list.toArray(new String[list.size()]);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		
						
						return kodlar;
					}
				//end of getOtelCity
					
					//getOtelStars
					public String[] getOtelStars(String gelen_mesaj)
					{
						String[] stars=new String[1000];
						ArrayList<String> list = new ArrayList<String>();
						try {			
							JSONObject obj = new JSONObject(gelen_mesaj);
							JSONArray arr = obj.getJSONArray("hotels");
							for(int i=0;i<arr.length();i++)
							{
								JSONObject id=arr.getJSONObject(i);
								list.add(id.getString("s"));
							}
							stars=list.toArray(new String[list.size()]);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		
						
						return stars;
					}
				//end of getOtelCity

		//getOtelCountires
		public String[] getOtelCountry(String gelen_mesaj)
		{
			String[] countries=new String[1000];
			ArrayList<String> list = new ArrayList<String>();
			try {			
				JSONObject obj = new JSONObject(gelen_mesaj);
				JSONArray arr = obj.getJSONArray("hotels");
				for(int i=0;i<arr.length();i++)
				{
					JSONObject id=arr.getJSONObject(i);
					list.add(id.getString("coid"));
				}
				countries=list.toArray(new String[list.size()]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			return countries;
		}
	//end of getOtelCity
	}



