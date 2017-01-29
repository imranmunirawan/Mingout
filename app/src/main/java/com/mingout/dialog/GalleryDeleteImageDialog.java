package com.mingout.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mingout.activities.R;

public class GalleryDeleteImageDialog extends Dialog implements
		View.OnClickListener {
	Button BCamera, BGallery,BDelete;
	Activity context;
	Fragment frag;

	public GalleryDeleteImageDialog(Activity context, Fragment frag) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.frag = frag;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_delete_image_layout);

		BCamera = (Button) findViewById(R.id.BCamera);
		BGallery = (Button) findViewById(R.id.BGallery);
		BDelete = (Button) findViewById(R.id.BDelete);

		BCamera.setOnClickListener(this);
		BGallery.setOnClickListener(this);
		BDelete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.BCamera:
			((onDeleteClickListner) frag).onDeleteCameraButtonPressed();
			dismiss();
			break;
		case R.id.BGallery:
			((onDeleteClickListner) frag).onDeleteGalleryButtonPressed();
			dismiss();
			break;
		case R.id.BDelete:
			((onDeleteClickListner) frag).onDeleteButtonPressed();
			dismiss();
			break;
		}
	}

	public interface onDeleteClickListner {
		void onDeleteCameraButtonPressed();

		void onDeleteGalleryButtonPressed();

		void onDeleteButtonPressed();

	}

}
