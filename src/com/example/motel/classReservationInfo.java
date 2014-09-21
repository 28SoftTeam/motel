package com.example.motel;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class classReservationInfo {
	
	private Context context;
	SharedPreferences ReservePref;
	Editor editor ;
	
	public classReservationInfo(Context myc)
	{
		this.context=myc;
		ReservePref = context.getSharedPreferences("ReserveInfo", 0);
		editor = ReservePref.edit();
	}
	
	public void setEmail(String email)
	{
		editor.putString("reserve_email", email);
		editor.commit();
	}
	
	public String getEmail()
	{
		return ReservePref.getString("reserve_email", null);
	}
	
	public void setReservedIn(String reserved_in)
	{
		editor.putString("reserved_in", reserved_in);
		editor.commit();
	}
	
	public String getReservedIn()
	{
		return ReservePref.getString("reserved_in", null);
	}
	
	public void setReservedOut(String reserved_out)
	{
		editor.putString("reserved_out", reserved_out);
		editor.commit();
	}
	
	public String getReservedOut()
	{
		return ReservePref.getString("reserved_out", null);
	}
	
	public void setReservedHotel(String reserved_hotel)
	{
		editor.putString("reserved_hotel", reserved_hotel);
		editor.commit();
	}
	
	public String getReservedHotel()
	{
		return ReservePref.getString("reserved_hotel", null);
	}
	
	public void setReservedCity(String reserved_city)
	{
		editor.putString("reserved_city", reserved_city);
		editor.commit();
	}
	
	public String getReservedCity()
	{
		return ReservePref.getString("reserved_city", null);
	}
	
	public void setReservedCountry(String reserved_country)
	{
		editor.putString("reserved_country", reserved_country);
		editor.commit();
	}
	
	public String getReservedCountry()
	{
		return ReservePref.getString("reserved_country", null);
	}
	
	public void setReservedCommerge(String reserved_commerge)
	{
		editor.putString("reserved_commerge", reserved_commerge);
		editor.commit();
	}
	
	public String getReservedCommerge()
	{
		return ReservePref.getString("reserved_commerge", null);
	}

}
