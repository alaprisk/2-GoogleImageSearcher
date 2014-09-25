package com.codepath.rishi.gridimagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.codepath.rishi.gridimagesearch.R;
import com.codepath.rishi.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

	ImageButton backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		
		
		// Removing the action bar
		getActionBar().hide();
		
		
		backButton = (ImageButton) findViewById(R.id.btnBack);
		/*Pull out the url from the intent.
		String url = getIntent().getStringExtra("url");
		
		// Find the image view
		ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
		
		// Load the image url inton the imageview using picasso
		Picasso.with(this).load(url).into(ivImageResult);*/
		
		
		//Pull out the entire results from the intent.
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");

		// Find the image view
		ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
		
		Log.i("ImageDisplayActivity", "About to load full image using Picasso");
		// Load the image url inton the imageview using picasso
		Picasso.with(this).load(result.fullUrl).resize(600, 600).centerInside().into(ivImageResult);
		Log.i("ImageDisplayActivity", "Done with full image loading using Picasso");

		
	}

	
	public void clickedBackButton(View v) {
		this.finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
