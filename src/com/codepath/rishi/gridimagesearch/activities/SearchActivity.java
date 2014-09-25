package com.codepath.rishi.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.rishi.gridimagesearch.R;
import com.codepath.rishi.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.rishi.gridimagesearch.models.EndlessScrollListener;
import com.codepath.rishi.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;





public class SearchActivity extends Activity {

	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private final int REQUEST_CODE = 100;
	String searchUrl;
	String query;
	
	String image_size;
	String image_color;
	String image_type;
	String site_filter;
	int values_set=0;
	
	int start_load=1;
	int new_search = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        // Creates the data source
        imageResults = new ArrayList<ImageResult>();
        // Attaches the data source to an adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        // Link the adapter to the adapterview (gridView)
        gvResults.setAdapter(aImageResults);
        //checkNetworkStatus();
        
 
    }
    
    private void setuponscrolllistener()
    {   gvResults.setOnScrollListener(new EndlessScrollListener(){


    	
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
		        //customLoadMoreDataFromApi(page); 
		    	//Toast.makeText(SearchActivity.this, "Scrolled, page = " + page +" , total_items = " + totalItemsCount, Toast.LENGTH_SHORT).show();
		    	
		    	start_load = totalItemsCount*page;
		       	searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query +"&rsz=8&start="+ start_load;
	        	if(values_set == 1)
	            searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8&imgsz=" 
	            		+ image_size + "&imgcolor=" + image_color + "&imgtype=" + image_type + "&as_sitesearch="+ site_filter +"&start="+ start_load; 
		    	onImageSearch(null);
			}

     	
     });
}

    private void setupViews(){
    	checkNetworkStatus();
    	
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults = (GridView) findViewById(R.id.gvResults);
    	gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Launch the image display activity
				
				// Create an intent
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				
				// Get the image result to display
				ImageResult result = imageResults.get(position);
				
				// Pass the image result into intent
				i.putExtra("result", result); // need to either be serializable or parcelable
				
				// Launch the new activity.
				startActivity(i);
				
			}
    		
    		
    		
    		
		});
    	
    }

    
	private Boolean checkNetworkStatus() {
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
        if( networkInfo != null && networkInfo.isConnectedOrConnecting()){
    		//Toast.makeText(this, "NETWORK CONNECTIVITY EXISTS", Toast.LENGTH_LONG).show();
        	return true;
        }
        else {
    		Toast.makeText(this, "Lost Network Connectivity, Please Check !", Toast.LENGTH_LONG).show();
        	return false;

        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_query_settings, menu);
        return true;
    }   
    
    
    
    
    // Fired whenever button has been clicked
    public void onImageSearch(View v) {
    	query = etQuery.getText().toString();
    	
    	//Toast.makeText(this, "Searching for : " + query, Toast.LENGTH_SHORT).show();
    	
        AsyncHttpClient client = new AsyncHttpClient();
                
       /* searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query +"&rsz=8";
        if(values_set == 1)
            searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8&imgsz=" 
            		+ image_size + "&imgcolor=" + image_color + "&imgtype=" + image_type + "&as_sitesearch="+ site_filter; 
        */           		  
        //searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8";
    
        if(!checkNetworkStatus())
        {
			imageResults.clear(); // Clear existing images in the array (in case of a new search)        	
        	return;
        }
        
        if(v != null)
        {
        	new_search = 1;
        	setuponscrolllistener();
        	//start_load = 1;
        }
        
        
        if(new_search==1)
        {	
        	//Toast.makeText(this, "start_load = 1", Toast.LENGTH_SHORT).show();

        	searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query +"&rsz=8";
        	if(values_set == 1)
            searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8&imgsz=" 
            		+ image_size + "&imgcolor=" + image_color + "&imgtype=" + image_type + "&as_sitesearch="+ site_filter; 
        } 
        
        
        
        
        client.get(searchUrl, new JsonHttpResponseHandler(){
        	
        	@Override
        	public void onFailure(int statusCode, Header[] headers,
        			Throwable throwable, JSONObject errorResponse) {
        		Log.d("DEBUG error", errorResponse.toString());
        	}
        	
        	@Override
        	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        		Log.d("DEBUG success", response.toString());
        		
        		JSONArray imageResultsJson = null;
        		try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					
					if(new_search == 1){
						imageResults.clear(); // Clear existing images in the array (in case of a new search)
						aImageResults.notifyDataSetChanged();
					}
					new_search = 0;
					//imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
					
					//aImageResults.notifyDataSetChanged(); OR >>>>
					// When you make to the adapter, it does modify the underlying data
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		Log.i("INFO",imageResults.toString());
        		
        	}     	
        	
        });    	
    	
    	//Toast.makeText(this, "Done with : " + query, Toast.LENGTH_SHORT).show();

    	
	}
    
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	switch(item.getItemId())
    	{
    	case R.id.menuSettings:
    		
    		//Handle the click
    		
    		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    		
    		
    		Intent i = new Intent(this, CreateExpenseActivity.class);
    		//Encode any parameters
    		
    		
    		//Execute the intent
    		startActivity(i);
    		
    		return true;
    		
    	}
    	
    	return false;
    }*/
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.menuSettings) {
		
			//Handle the click
			
			//Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
			
			/*
			Intent i = new Intent(this, CreateExpenseActivity.class);
			//Encode any parameters
			
			
			//Execute the intent
			startActivity(i);*/
			
			 Intent i = new Intent( this , SearchOptionsActivity.class);
			 /*i.putExtra("to_be_edited_item",tobedeleted.todostring);
			 i.putExtra("position", position);
			 i.putExtra("priorityincoming",tobedeleted.priority);
			 i.putExtra("date", tobedeleted.date);*/
			 				 
			 startActivityForResult(i, REQUEST_CODE); // brings up the EditItem Activity	
			
			
			return true;
		
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // REQUEST_CODE is defined above

 		//Log.e("back from edit menu","from onActivityResult");

    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		
			//Toast.makeText(this, "back to search activity", Toast.LENGTH_SHORT).show();
			
	    	 // Extract name value from result extras
	         image_size = data.getExtras().getString("image_size");
	         image_color = data.getExtras().getString("image_color");
	         image_type = data.getExtras().getString("image_type");
	         site_filter = data.getExtras().getString("site_filter");
	         values_set = 1;

	 		//Toast.makeText(this, image_size, Toast.LENGTH_SHORT).show();
	 		//Toast.makeText(this, image_color, Toast.LENGTH_SHORT).show();
	 		//Toast.makeText(this, image_type, Toast.LENGTH_SHORT).show();
	 		//Toast.makeText(this, site_filter, Toast.LENGTH_SHORT).show();

    		
    	}
    
    }
}
