package com.example.motel;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.drawable;
import android.util.Log;

public class classJasonParserOtelInfo<K> {

	//get otel id
	public String getOtelID(String gelen_mesaj)
	{
		String hotel_id="";
		try {					
				JSONObject obj = new JSONObject(gelen_mesaj);
				JSONObject hotel=obj.getJSONObject("hotel");
				JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
				hotel_id=hotel_info.getString("id");
			}
		catch (JSONException e) {
			e.getStackTrace();
			}
		return hotel_id;
	}//end of getOtelId
	
	//get otelName
	public String getOtelName(String gelen_mesaj)
	{
		String hotel_name="";
		try {					
				JSONObject obj = new JSONObject(gelen_mesaj);
				JSONObject hotel=obj.getJSONObject("hotel");
				JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
				hotel_name=hotel_info.getString("n");
			}
		catch (JSONException e) {
			e.getStackTrace();
			}
		return hotel_name;
	}//end of getOtelName
	
	//get otelAdres
		public String getOtelAdres(String gelen_mesaj)
		{
			String hotel_adres="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_adres=hotel_info.getString("a");
				}
			catch (JSONException e) {
				e.getStackTrace();
				}
			return hotel_adres;
		}//end of getOtelAdres
		
		//get otelCity
		public String getOtelCity(String gelen_mesaj)
		{
			String hotel_city="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_city=hotel_info.getString("c");
				}
			catch (JSONException e) {
				e.getStackTrace();
				}
			return hotel_city;
		}//end of getOtelCity
		
		//get otelCountryId
		public String getOtelCountry(String gelen_mesaj)
		{
			String hotel_coid="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_coid=hotel_info.getString("coid");
				}
			catch (JSONException e) {
				e.getStackTrace();
				}
			return hotel_coid;
		}//end of getOtelCountryId
		
		//get otelPhone
		public String getOtelPhone(String gelen_mesaj)
		{
			String hotel_p="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_p=hotel_info.getString("p");
				}
			catch (JSONException e) {
				e.getStackTrace();
				}
			return hotel_p;
		}//end of getOtelPhone
		
		//get otelStars
		public int getOtelStars(String gelen_mesaj)
		{
			String hotel_stars="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_stars=hotel_info.getString("s");
				}
			catch (JSONException e) {
				e.getStackTrace();
				}
			if (hotel_stars.equalsIgnoreCase("2"))
	        	return R.drawable.ic_2stars;
	        else if(hotel_stars.equalsIgnoreCase("3"))
	        	return R.drawable.ic_3stars;
	        else if(hotel_stars.equalsIgnoreCase("4"))
	        	return R.drawable.ic_4stars;
	        else if(hotel_stars.equalsIgnoreCase("5"))
	        	return R.drawable.ic_5stars;
	        else
			return 0;
		}//end of getOtelStars
		
		//get otelImages
		public String getOtelImages(String gelen_mesaj)
		{
			String hotel_images="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_images=hotel_info.getString("i");
				}
			catch (JSONException e) {
				e.getStackTrace();
				}
			return hotel_images;
		}//end of getOtelImages
		
		//get otelFaks
		public String getOtelFax(String gelen_mesaj)
		{
			String hotel_fax="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_fax=hotel_info.getString("fx");
				}
			catch (JSONException e) {
				e.getStackTrace();
				}
			return hotel_fax;
		}//end of getOtelFax

		//get otelFaks
		public String getOtelMail(String gelen_mesaj)
		{
			String hotel_mail="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_mail=hotel_info.getString("e");
				}
			catch (JSONException e) {
					e.getStackTrace();
				}
			return hotel_mail;
		}//end of getOtelFax
		
		//get otelTanimlar
		public String getOtelDescriptions(String gelen_mesaj)
		{
			String hotel_tanim="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_tanim=hotel_info.getString("d");
				}
			catch (JSONException e) {
					e.getStackTrace();
				}
			return hotel_tanim;
		}//end of getOtelTanimlar
		
		//get her otelTanim
		public String getOtelEachDescription(String gelen_mesaj,String desc)
		{
			String hotel_tanim="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					hotel_tanim=hotel_info.getString(desc);
				}
			catch (JSONException e) {
					e.getStackTrace();
				}
			return hotel_tanim;
		}//endof her getOtelTanim
		
		//get otelRoomCount
		public String getOtelRoomCount(String gelen_mesaj)
		{
			String rc="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					rc=hotel_info.getString("rc");
				}
			catch (JSONException e) {
					e.getStackTrace();
				}
			return rc;
		}//endof getOtelRoomCount
	
		//get otelRoomCount
		public String getOtelWeb(String gelen_mesaj)
		{
			String web="";
			try {					
					JSONObject obj = new JSONObject(gelen_mesaj);
					JSONObject hotel=obj.getJSONObject("hotel");
					JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
					web=hotel_info.getString("w");
				}
			catch (JSONException e) {
					e.getStackTrace();
				}
			return web;
		}//endof getOtelRoomCount
		
		
		//get otelCommerge
		public HashMap getOtelCommerge(String gelen_mesaj)
		{
			HashMap commerges=new HashMap<String, String>();
			try {			
				JSONObject obj = new JSONObject(gelen_mesaj);
				JSONObject hotel=obj.getJSONObject("hotel");
				JSONObject hotel_info=hotel.getJSONObject("HotelInfo");
				JSONArray arr = hotel_info.getJSONArray("hotel-commerge");
				for(int i=0;i<arr.length();i++)
				{
					JSONObject com=arr.getJSONObject(i);
					commerges.put(com.getString("providerName"), com.getString("providerUrl"));
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			return commerges;
		}//endof getOtelRoomCount

		
}
