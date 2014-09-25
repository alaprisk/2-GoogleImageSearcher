package com.codepath.rishi.gridimagesearch.activities;

import com.codepath.rishi.gridimagesearch.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchOptionsActivity extends Activity implements OnItemSelectedListener {
	
	Spinner spImageSize;
	Spinner spColor;
	Spinner spImageType;
	EditText etSiteFilter;

	String image_size;
	String image_color;
	String image_type;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		spColor = (Spinner) findViewById(R.id.spColor);
		spImageType = (Spinner) findViewById(R.id.spImageType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.size_arrays, R.layout.spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
		
		spImageSize.setAdapter(adapter1);
        spImageSize.setOnItemSelectedListener(this);

		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.color_arrays, R.layout.spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        
        spColor.setAdapter(adapter2);
        spColor.setOnItemSelectedListener(this);
        
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.type_arrays, R.layout.spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        
        spImageType.setAdapter(adapter3);
        spImageType.setOnItemSelectedListener(this);
		
	}

	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
        image_size =  spImageSize.getSelectedItem().toString();
		//Toast.makeText(this, image_size, Toast.LENGTH_SHORT).show();
		
        image_color =  spColor.getSelectedItem().toString();
		//Toast.makeText(this, image_color, Toast.LENGTH_SHORT).show();
		
        image_type =  spImageType.getSelectedItem().toString();
		//Toast.makeText(this, image_type, Toast.LENGTH_SHORT).show();
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
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
	
	public void onSavingItem(View v) {
		//Toast.makeText(this, "clicked save, going back to the search activity", Toast.LENGTH_SHORT).show();
		
		// Prepare data intent 
		Intent data = new Intent();

		// Pass relevant data back as a result
		
		if(image_size.startsWith("small"))
			data.putExtra("image_size", "icon");
		else if(image_size.startsWith("medium"))
			data.putExtra("image_size", "medium");
		else if(image_size.startsWith("large"))
			data.putExtra("image_size", "xxlarge");
		else if(image_size.startsWith("extra-large"))
			data.putExtra("image_size", "huge");
		
		//Toast.makeText(this, spImageSize.getItemAtPosition(0).toString(), Toast.LENGTH_SHORT).show();

		
		data.putExtra("image_color",image_color);

		//Toast.makeText(this, spColor.getItemAtPosition(0).toString(), Toast.LENGTH_SHORT).show();

		data.putExtra("image_type", image_type);

		//Toast.makeText(this, spImageType.getItemAtPosition(0).toString(), Toast.LENGTH_SHORT).show();

		data.putExtra("site_filter",etSiteFilter.getText().toString());
		
		//Toast.makeText(this, etSiteFilter.getText().toString(), Toast.LENGTH_SHORT).show();
		
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		
		this.finish(); // closes the activity, pass data to parent
		
		
		

	}



}
