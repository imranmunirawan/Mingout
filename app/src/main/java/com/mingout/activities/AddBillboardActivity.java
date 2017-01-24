package com.mingout.activities;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

import com.mingout.dialog.GalleryUploadImageDialog;
import com.mingout.dialog.GalleryUploadImageDialog.onButtonClickListner;
import com.mingout.models.PreviewBillboardModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;
import com.squareup.picasso.Picasso;

public class AddBillboardActivity extends Activity implements ResultJSON,
		onButtonClickListner {

	ImageView IV_logo;
	TextView TV_save;
	int CAMERA_REQUEST = 1111223, PICK_IMAGE = 12123211, billboardDetailFlag, CROP_PIC = 112211;
	String base64 = null;
	PreviewBillboardModel billboardData;
    Uri selectedImagee;

    EditText ET_subject, ET_category, ET_price, ET_detail;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_billboard_layout);

        ET_subject = (EditText) findViewById(R.id.ET_subject);
        ET_category = (EditText) findViewById(R.id.ET_category);
        ET_price = (EditText) findViewById(R.id.ET_price);
        ET_detail = (EditText) findViewById(R.id.ET_detail);

		TV_save = (TextView) findViewById(R.id.TV_save);
		IV_logo = (ImageView) findViewById(R.id.IV_logo);
		IV_logo.setScaleType(ScaleType.CENTER_CROP);

		try {
			billboardData = (PreviewBillboardModel) getIntent().getSerializableExtra("data");
			billboardDetailFlag = getIntent().getIntExtra("detail flag", 0);
		} catch (Exception e) {}

		if (String.valueOf(billboardDetailFlag).equals("1")) {
			TV_save.setVisibility(View.INVISIBLE);
			Picasso.with(AddBillboardActivity.this).load(Constants.URL_GET_BILLBOARD_IMAGE + billboardData.getImgUrl()).into(IV_logo);

            ET_subject.setText(billboardData.getName());
            ET_category.setText(billboardData.getCategory());
            ET_price.setText(billboardData.getPrice());
            ET_detail.setText(billboardData.getDesc());
		}

		TV_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                if(TextUtils.isEmpty(ET_subject.getText())){
                    ET_subject.setError("Mandatory Field");
                    return;
                }

                if(TextUtils.isEmpty(ET_category.getText())){
                    ET_category.setError("Mandatory Field");
                    return;
                }

                if(TextUtils.isEmpty(ET_price.getText())){
                    ET_price.setError("Mandatory Field");
                    return;
                }

                if(TextUtils.isEmpty(ET_detail.getText())){
                    ET_detail.setError("Mandatory Field");
                    return;
                }

                addData(Constants.QR_CODE,
                        ET_subject.getText().toString(),
                        ET_category.getText().toString(),
                        ET_price.getText().toString(),
                        ET_detail.getText().toString());
			}
		});

		IV_logo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			if (!String.valueOf(billboardDetailFlag).equals("1")) {
				GalleryUploadImageDialog dialog = new GalleryUploadImageDialog(AddBillboardActivity.this);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_IMAGE) {
				Uri selectedImageUri = data.getData();
				if (Build.VERSION.SDK_INT < 19) {
					Uri selectedImage = data.getData();
					String[] filePath = { MediaStore.Images.Media.DATA };
					Cursor c = getContentResolver().query(selectedImage,
							filePath, null, null, null);
					c.moveToFirst();
					int columnIndex = c.getColumnIndex(filePath[0]);
					String picturePath = c.getString(columnIndex);
					c.close();

					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;

					int desiredWidth = 800;
					BitmapFactory.decodeFile(picturePath, options);
					int srcWidth = options.outWidth;
					int srcHeight = options.outHeight;
					if (desiredWidth > srcWidth)
						desiredWidth = srcWidth;

					int inSampleSize = 1;
					while (srcWidth / 2 > desiredWidth) {
						srcWidth /= 2;
						srcHeight /= 2;
						inSampleSize *= 2;
					}
					float desiredScale = (float) desiredWidth / srcWidth;

					// Decode with inSampleSize
					options.inJustDecodeBounds = false;
					options.inDither = false;
					options.inSampleSize = inSampleSize;
					options.inScaled = false;
					options.inPreferredConfig = Bitmap.Config.ARGB_8888;

					ExifInterface exif;
					try {
						exif = new ExifInterface(picturePath);
						int orientation = exif.getAttributeInt(
								ExifInterface.TAG_ORIENTATION, 1);
						Log.d("EXIF", "Exif: " + orientation);
						Matrix matrix = new Matrix();
						matrix.postScale(desiredScale, desiredScale);
						if (orientation == 6) {
							matrix.postRotate(90);
						} else if (orientation == 3) {
							matrix.postRotate(180);
						} else if (orientation == 8) {
							matrix.postRotate(270);
						}
						Bitmap thumbnail = (BitmapFactory.decodeFile(
								picturePath, options));
						Bitmap photoBitMap = Bitmap.createBitmap(thumbnail, 0,
								0, thumbnail.getWidth(), thumbnail.getHeight(),
								matrix, true);
						base64 = Utilities.imageToBase64(photoBitMap);
						IV_logo.setImageBitmap(photoBitMap);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						Uri selectedImage = data.getData();
						String[] filePath = { MediaStore.Images.Media.DATA };
						int desiredWidth = 800;
						Cursor c = getContentResolver().query(selectedImage,
								filePath, null, null, null);
						c.moveToFirst();
						int columnIndex = c.getColumnIndex(filePath[0]);
						String picturePath = c.getString(columnIndex);
						c.close();

						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inJustDecodeBounds = true;
						BitmapFactory.decodeFile(picturePath, options);
						int srcWidth = options.outWidth;
						int srcHeight = options.outHeight;
						if (desiredWidth > srcWidth)
							desiredWidth = srcWidth;

						int inSampleSize = 1;
						while (srcWidth / 2 > desiredWidth) {
							srcWidth /= 2;
							srcHeight /= 2;
							inSampleSize *= 2;
						}
						float desiredScale = (float) desiredWidth / srcWidth;

						// Decode with inSampleSize
						options.inJustDecodeBounds = false;
						options.inDither = false;
						options.inSampleSize = inSampleSize;
						options.inScaled = false;
						options.inPreferredConfig = Bitmap.Config.ARGB_8888;

						ExifInterface exif;
						try {
							exif = new ExifInterface(picturePath);
							int orientation = exif.getAttributeInt(
									ExifInterface.TAG_ORIENTATION, 1);
							Log.d("EXIF", "Exif: " + orientation);
							Matrix matrix = new Matrix();
							matrix.postScale(desiredScale, desiredScale);
							if (orientation == 6) {
								matrix.postRotate(90);
							} else if (orientation == 3) {
								matrix.postRotate(180);
							} else if (orientation == 8) {
								matrix.postRotate(270);
							}
							Bitmap image = (BitmapFactory.decodeFile(
									picturePath, options));
							Bitmap photoBitMap = Bitmap.createBitmap(image, 0,
									0, image.getWidth(), image.getHeight(),
									matrix, true);
							base64 = Utilities.imageToBase64(photoBitMap);
							IV_logo.setImageBitmap(photoBitMap);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            selectedImagee = data.getData();
            performCrop(selectedImagee);
		}

		if (requestCode == CROP_PIC && resultCode == RESULT_OK && selectedImagee != null) {
            Uri selectedImage = selectedImagee;
            String[] filePath = { MediaStore.Images.Media.DATA };
			int desiredWidth = 800;
			Cursor c = getContentResolver().query(selectedImage,
					filePath, null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePath[0]);
			String picturePath = c.getString(columnIndex);
			c.close();

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(picturePath, options);
			int srcWidth = options.outWidth;
			int srcHeight = options.outHeight;

			if (desiredWidth > srcWidth)
				desiredWidth = srcWidth;

			int inSampleSize = 1;
			while (srcWidth / 2 > desiredWidth) {
				srcWidth /= 2;
				srcHeight /= 2;
				inSampleSize *= 2;
			}
			float desiredScale = (float) desiredWidth / srcWidth;

			// Decode with inSampleSize
			options.inJustDecodeBounds = false;
			options.inDither = false;
			options.inSampleSize = inSampleSize;
			options.inScaled = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;

			ExifInterface exif;
			try {
				exif = new ExifInterface(picturePath);
				int orientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION, 1);
				Log.d("EXIF", "Exif: " + orientation);
				Matrix matrix = new Matrix();
				matrix.postScale(desiredScale, desiredScale);
				if (orientation == 6) {
					matrix.postRotate(90);
				} else if (orientation == 3) {
					matrix.postRotate(180);
				} else if (orientation == 8) {
					matrix.postRotate(270);
				}
				Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath,
						options));

				Bitmap photoBitMap = Bitmap.createBitmap(thumbnail, 0, 0,
						thumbnail.getWidth(), thumbnail.getHeight(), matrix,
						true);
			    IV_logo.setImageBitmap(photoBitMap);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	private void performCrop(Uri picUri) {
		// take care of exceptions
		try {
			// call the standard crop action intent (the user device may not
			// support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 250);
			cropIntent.putExtra("outputY", 250);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, CROP_PIC);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			Toast toast = Toast.makeText(AddBillboardActivity.this,
					"This device doesn't support the crop action!",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private void addData(String qrCode, String subject, String category, String price, String detail){
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("profile_id", Constants.QR_LOGIN_PROFILE_ID);
			jsonobj.put("qr_code", qrCode);
			jsonobj.put("subject", subject);
			jsonobj.put("category", category);
			jsonobj.put("price", price);
			jsonobj.put("detail", detail);
			jsonobj.put("image", base64);
			new ConnectionTask(this, jsonobj).execute(Constants.API_ADD_BILLBOARD);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			String string = (String) obj;
			Log.e("Response :", string);
			JSONObject jData = new JSONObject(string);
			if (jData.getString("status_code").equals("1")) {
				Toast.makeText(AddBillboardActivity.this,
						"BillBoard has been added!", Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(AddBillboardActivity.this,
						jData.getString("status_message"), Toast.LENGTH_LONG)
						.show();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onCameraButtonPressed() {
		// TODO Auto-generated method stub
		Intent cameraIntent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	@Override
	public void onGalleryButtonPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PICK_IMAGE);
	}

}
