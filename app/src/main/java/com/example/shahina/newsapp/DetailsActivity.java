package com.example.shahina.newsapp;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity{
	TextView content,headline,moredetails,source,datetime;
	ImageView imgvw;
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_details);
	        
	        headline = (TextView) findViewById(R.id.headline);
	        datetime = (TextView) findViewById(R.id.datetime);
	        content = (TextView) findViewById(R.id.content);
	        imgvw = (ImageView) findViewById(R.id.news_icon);
	        source = (TextView) findViewById(R.id.source);
	        moredetails = (TextView) findViewById(R.id.moredetails);
	        
	        Bundle newsBundle = getIntent().getExtras();
	        HashMap<String, String> newsDetails =(HashMap<String, String>) newsBundle.get("details");
	       
	        headline.setText("null".equalsIgnoreCase(newsDetails.get("title"))?"":newsDetails.get("title"));
	        source.setText("null".equalsIgnoreCase(newsDetails.get("source_name"))?"":(newsDetails.get("source_name")+("null".equalsIgnoreCase(newsDetails.get("author"))?"":" - "+newsDetails.get("author"))));
	        datetime.setText("null".equalsIgnoreCase(newsDetails.get("publishedAt"))?"":newsDetails.get("publishedAt"));
	        content.setText("null".equalsIgnoreCase(newsDetails.get("content"))?"":newsDetails.get("content"));
	        moredetails.setText("null".equalsIgnoreCase(newsDetails.get("url"))?"":"For more details please visit: "+newsDetails.get("url"));
	        new DownloadImageTask(imgvw).execute(newsDetails.get("urlToImage"));
	        
	        moredetails.setClickable(true);
	        moredetails.setMovementMethod(LinkMovementMethod.getInstance());
	        String text ="null".equalsIgnoreCase(newsDetails.get("url"))?"":"For more details, Please click "+"<a href='"+newsDetails.get("url")+"'> here </a> ";
	        moredetails.setText(Html.fromHtml(text));
	        
	  }
	  private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	  ImageView bmImage;

    	  public DownloadImageTask(ImageView bmImage) {
    	      this.bmImage = bmImage;
    	  }

    	  protected Bitmap doInBackground(String... urls) {
    	      String urldisplay = urls[0];
    	      Bitmap mIcon11 = null;
    	      try {
    	    	
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

  		        // Find the correct scale value. It should be the power of 2.
  		        int scale = 1;
  		        while(o.outWidth / scale / 2 >= REQUIRED_SIZE && 
  		              o.outHeight / scale / 2 >= REQUIRED_SIZE) {
  		            scale *= 2;
  		        }

  		        // Decode with inSampleSize
  		        BitmapFactory.Options o2 = new BitmapFactory.Options();
  		        o2.inSampleSize = scale;
  		        return BitmapFactory.decodeStream(new java.net.URL(url).openStream(), null, o2);
  		    } catch (Exception e) {}
  		    return null;
  		}
    	 
    	}
}
