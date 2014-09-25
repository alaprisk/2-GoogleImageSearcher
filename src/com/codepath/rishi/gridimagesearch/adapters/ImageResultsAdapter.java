package com.codepath.rishi.gridimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.rishi.gridimagesearch.R;
import com.codepath.rishi.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultsAdapter(Context context,  List<ImageResult> images) {
		super(context, R.layout.item_image_result, images );
		// TODO Auto-generated constructor stub
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	// TODO Auto-generated method stub
    	ImageResult imageInfo = getItem(position);
    	if(convertView == null) {
    		convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
    	}
    	ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);    	
    	TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
    	
    	//Clear out image from last time
    	ivImage.setImageResource(0);
    	
    	// Populate title and remote download image url
    	tvTitle.setText(Html.fromHtml(imageInfo.title) );

    	// Remotely download the image data in the background (with Picasso)   	
    	Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
    	
    	// Return the completed view to be displayed
    	return convertView;

    }
	
	
}
