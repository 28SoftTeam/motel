package com.example.motel;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class classJasonParserGeneralSearch {

	//getSearched Name
	public String[] getSearchedNames(String gelen_mesaj)
	{
		String[] names=new String[1000];
		ArrayList<String> list = new ArrayList<String>();
		try {			
			JSONObject obj = new JSONObject(gelen_mesaj);
			JSONArray arr = obj.getJSONArray("seaarch-results");
			for(int i=0;i<arr.length();i++)
			{
				JSONObject id=arr.getJSONObject(i);
				list.add(id.getString("name"));
			}
			names=list.toArray(new String[list.size()]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return names;
	}//end of getSearchedName
	
	//getSearched Country
		public String[] getSearchedCountries(String gelen_mesaj)
		{
			String[] countries=new String[1000];
			ArrayList<String> list = new ArrayList<String>();
			try {			
				JSONObject obj = new JSONObject(gelen_mesaj);
				JSONArray arr = obj.getJSONArray("seaarch-results");
				for(int i=0;i<arr.length();i++)
				{
					JSONObject id=arr.getJSONObject(i);
					String[] desc=id.getString("description").split("-");
					list.add(desc[0]);
				}
				countries=list.toArray(new String[list.size()]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			return countries;
		}//end of getSearcheddescriptions
		
		//getSearched counts
		public String[] getSearchedCounts(String gelen_mesaj)
		{
			String[] counts=new String[1000];
			ArrayList<String> list = new ArrayList<String>();
			try {			
				JSONObject obj = new JSONObject(gelen_mesaj);
				JSONArray arr = obj.getJSONArray("seaarch-results");
				for(int i=0;i<arr.length();i++)
				{
					JSONObject id=arr.getJSONObject(i);
					list.add(id.getString("hotel-count"));
				}
				counts=list.toArray(new String[list.size()]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			return counts;
		}//end of getSearchedCounts
		
	//getSearchedIDs
	public String[] getSearchedIDs(String gelen_mesaj)
	{
		String[] ids=new String[1000];
		ArrayList<String> list = new ArrayList<String>();
		try {			
			JSONObject obj = new JSONObject(gelen_mesaj);
			JSONArray arr = obj.getJSONArray("seaarch-results");
			for(int i=0;i<arr.length();i++)
			{
				JSONObject id=arr.getJSONObject(i);
				list.add(id.getString("id"));
			}
			ids=list.toArray(new String[list.size()]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return ids;
	}//end of getSearchedIDs
	
	//getSerachedTypes
	public String[] getSearchedTypes(String gelen_mesaj)
	{
		String[] types=new String[1000];
		ArrayList<String> list = new ArrayList<String>();
		try {			
			JSONObject obj = new JSONObject(gelen_mesaj);
			JSONArray arr = obj.getJSONArray("seaarch-results");
			for(int i=0;i<arr.length();i++)
			{
				JSONObject id=arr.getJSONObject(i);
				list.add(id.getString("type"));
			}
			types=list.toArray(new String[list.size()]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return types;
	}//end of getSearchedTypes
	
	//getSerachedBolge
	public String[] getSearchedBolge(String gelen_mesaj)
	{
		String[] bolgeler=new String[1000];
		ArrayList<String> list = new ArrayList<String>();
		try {			
			JSONObject obj = new JSONObject(gelen_mesaj);
			JSONArray arr = obj.getJSONArray("seaarch-results");
			for(int i=0;i<arr.length();i++)
			{
				JSONObject id=arr.getJSONObject(i);
				String[] bolge=id.getString("description").split("-");
				list.add(bolge[1].substring(0, bolge[1].length()-3));
			}
			bolgeler=list.toArray(new String[list.size()]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return bolgeler;
	}//end of getSearchedBolge
}
