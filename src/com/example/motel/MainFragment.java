package com.example.motel;

import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.sayfa_otel_usertani, container, false);

	    return view;
	}
	
	

}
