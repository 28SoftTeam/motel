package com.example.motel;

import java.util.List;

import com.example.motel.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class CustomListViewAdapter extends ArrayAdapter<RowItem>{

	Context context;
	 
    public CustomListViewAdapter(Context context, int resourceId,
            List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }
     
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        ImageView imageStar;
        TextView txtOtel;
        TextView txtKod;
        TextView txtBolge;
    }
     
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.txtOtel = (TextView) convertView.findViewById(R.id.title);
            //holder.txtKod = (TextView) convertView.findViewById(R.id.kod);
            holder.txtBolge = (TextView) convertView.findViewById(R.id.bolge);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            holder.imageStar = (ImageView) convertView.findViewById(R.id.stars);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
                 
        holder.txtOtel.setText(rowItem.getOtel());
        holder.txtBolge.setText(rowItem.getBolge());
        //holder.txtKod.setText(rowItem.getKod());
        //set image from url 
       if(!rowItem.getImageId().equals("") & !rowItem.getImageId().equals(null))
        {
        com.example.motel.DownloadImageTask img_url=new com.example.motel.DownloadImageTask((ImageView)convertView.findViewById(R.id.list_image));
		img_url.execute(rowItem.getImageId());
       }
       else
       holder.imageView.setImageResource(R.drawable.ic_launcher);
      
        
        if (rowItem.getStars().equalsIgnoreCase("2"))
        	holder.imageStar.setImageResource(R.drawable.ic_2stars);
        else if(rowItem.getStars().equalsIgnoreCase("3"))
        	holder.imageStar.setImageResource(R.drawable.ic_3stars);
        else if(rowItem.getStars().equalsIgnoreCase("4"))
        	holder.imageStar.setImageResource(R.drawable.ic_4stars);
        else if(rowItem.getStars().equalsIgnoreCase("5"))
        	holder.imageStar.setImageResource(R.drawable.ic_5stars);
        else
        	holder.imageStar.setVisibility(View.INVISIBLE);
        
        
        return convertView;
    }
   
}
