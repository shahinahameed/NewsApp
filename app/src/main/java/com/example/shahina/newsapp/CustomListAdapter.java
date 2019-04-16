package com.example.shahina.newsapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String>{
	private final Activity context;  
    private final String[] maintitle;  
    private ArrayList<HashMap<String, String>> newsList;
  
    public CustomListAdapter(Activity context2, String[] maintitle,ArrayList<HashMap<String, String>> newsList) {  
    	super(context2, R.layout.activity_listview, maintitle);    
  
        this.context=context2;  
        this.maintitle=maintitle;  
        this.newsList=newsList;
    }  
  
    public View getView(int position,View view,ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = view;
		if (rowView == null)
			rowView = inflater.inflate(R.layout.activity_listview, null);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);  
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);  
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle); 
        TextView datetime = (TextView) rowView.findViewById(R.id.datetime);
  
        titleText.setText(maintitle[position]);  
        datetime.setText(((HashMap<String, String>)newsList.get(position)).get("publishedAt").toString());  
        subtitleText.setText(((HashMap<String, String>)newsList.get(position)).get("description").toString());  
        new DownloadImageTask(imageView).execute(((HashMap<String, String>)newsList.get(position)).get("urlToImage").toString());
        
        return rowView;  
  
    };  
    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	  ImageView bmImage;

    	  public DownloadImageTask(ImageView bmImage) {
    	      this.bmImage = bmImage;
    	  }

    	  protected Bitmap doInBackground(String... urls) {
    	      String urldisplay = urls[0];
    	      Bitmap mIcon11 = null;
    	      try {
    	      	//decode large bitmap to avoid memory exception
    	    	  mIcon11=decodeFile(urldisplay);
    	      
    	      } catch (Exception e) {
    	         
    	          e.printStackTrace();
    	      }
    	      return mIcon11;
    	  }

    	  protected void onPostExecute(Bitmap result) {
    	      bmImage.setImageBitmap(result);
    	  }
    	  
    	  private Bitmap decodeFile(String url) {
    		    try {
    		        // Decode image size
    		        BitmapFactory.Options o = new BitmapFactory.Options();
    		        o.inJustDecodeBounds = true;
    		        BitmapFactory.decodeStream(new java.net.URL(url).openStream(), null, o);

    		        // The new size we want to scale to
    		        final int REQUIRED_SIZE=70;

    		        int scale = 1;
    		        while(o.outWidth / scale / 2 >= REQUIRED_SIZE && 
    		              o.outHeight / scale / 2 >= REQUIRED_SIZE) {
    		            scale *= 2;
    		        }

    		        BitmapFactory.Options o2 = new BitmapFactory.Options();
    		        o2.inSampleSize = scale;
    		        return BitmapFactory.decodeStream(new java.net.URL(url).openStream(), null, o2);
    		    } catch (Exception e) {}
    		    return null;
    		}
    	}
}
