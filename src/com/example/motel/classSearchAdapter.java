package com.example.motel;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


 
 
public class classSearchAdapter extends ArrayAdapter<classSearchedRows>{

	Context context;
	 
    public classSearchAdapter(Context context, int resourceId,
            List<classSearchedRows> items) {
        super(context, resourceId, items);
        this.context = context;
    }
     
    /*private view holder class*/
    private class ViewHolder {
        TextView txtName;
        TextView txtBolge;
    }
     
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        classSearchedRows rowItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.result_search_item, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.search_result_text_view);
            holder.txtBolge = (TextView) convertView.findViewById(R.id.search_result_kod);
            convertView.setTag(holder);
        } else
        	
            holder = (ViewHolder) convertView.getTag();
                 
        holder.txtName.setText(rowItem.getName().trim());
        String sag_kisim="";
              
        if(rowItem.getType().trim().equals("0"))
        	sag_kisim="Otel";
        else sag_kisim=rowItem.getCount();
        holder.txtBolge.setText(sag_kisim.trim());
                
        return convertView;
    }
   
}
