package com.mingout.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mingout.activities.R;

public class GalleryUploadImageDialog extends Dialog implements
		View.OnClickListener {
	Button BCamera, BGallery;
	Activity context;
	Fragment frag;
	boolean ActivityFlag = false;

	public GalleryUploadImageDialog(Activity context, Fragment frag) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.frag = frag;
	}

	public GalleryUploadImageDialog(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		ActivityFlag = true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_upload_image_layout);

		BCamera = (Button) findViewById(R.id.BCamera);
		BGallery = (Button) findViewById(R.id.BGallery);

		BCamera.setOnClickListener(this);
		BGallery.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.BCamera:
			if (!ActivityFlag) {
				((onButtonClickListner) frag).onCameraButtonPressed();
				dismiss();
			} else {
				((onButtonClickListner) context).onCameraButtonPressed();
				dismiss();
			}
			break;
		case R.id.BGallery:
			if (!ActivityFlag) {
				((onButtonClickListner) frag).onGalleryButtonPressed();
				dismiss();
			} else {
				((onButtonClickListner) context).onGalleryButtonPressed();
				dismiss();
			}
			break;
		}
	}

	public interface onButtonClickListner {
		void onCameraButtonPressed();

		void onGalleryButtonPressed();
	}

}
