package com.mingout.fragments;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mingout.activities.R;
import com.mingout.dialog.GalleryDeleteImageDialog;
import com.mingout.dialog.GalleryDeleteImageDialog.onDeleteClickListner;
import com.mingout.dialog.GalleryUploadImageDialog;
import com.mingout.dialog.GalleryUploadImageDialog.onButtonClickListner;
import com.mingout.dialog.MyAlertDialog;
import com.mingout.models.GalleryModel;
import com.mingout.util.ConnectionTaskSupportFragment;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileGallerySocialFragment extends Fragment implements
		onButtonClickListner, ResultJSON, onDeleteClickListner {

	ImageView IV_mainLogo, IV_1, IV_2, IV_4, IV_3;
	int CAMERA_REQUEST = 1123, PICK_IMAGE = 1211, CROP_PIC = 923;
	String base64, picPlace, i1ImageAvailable = null, i2ImageAvailable = null,
			i3ImageAvailable = null, i4ImageAvailable = null,
			i5ImageAvailable = null, deleteImageId;
	boolean deleteImageFlag = false, swapImageFlag = false;
	Uri selectedImagee;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.fragment_profile_gallery_social_layout, null);

		IV_mainLogo = (ImageView) view.findViewById(R.id.IV_mainLogo);
		IV_1 = (ImageView) view.findViewById(R.id.IV_1);
		IV_2 = (ImageView) view.findViewById(R.id.IV_2);
		IV_3 = (ImageView) view.findViewById(R.id.IV_3);
		IV_4 = (ImageView) view.findViewById(R.id.IV_4);

		IV_mainLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setImage("1");
				picPlace = "1";
			}
		});
		IV_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setImage("2");
				picPlace = "2";
			}
		});
		IV_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setImage("3");
				picPlace = "3";
			}
		});

		IV_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setImage("4");
				picPlace = "4";
			}
		});
		IV_4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setImage("5");
				picPlace = "5";
			}
		});

		IV_mainLogo.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					break;
				case DragEvent.ACTION_DRAG_ENTERED:

					break;
				case DragEvent.ACTION_DRAG_EXITED:
					break;
				case DragEvent.ACTION_DRAG_LOCATION:
					Log.e("Message :", "drag location");
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					Log.e("Message :", "Drag ended");
					// Do nothing
					break;
				case DragEvent.ACTION_DROP:
					// Do nothing
					View view = (View) event.getLocalState();
					Log.e("Message :", "Drop called");
					switch (view.getId()) {
					case R.id.IV_1: // First Image
						if (v.getId() == R.id.IV_mainLogo) {
							SwapPic(i2ImageAvailable, i1ImageAvailable);
						}
						break;
					case R.id.IV_2: // Second Image
						if (v.getId() == R.id.IV_mainLogo) {
							SwapPic(i3ImageAvailable, i1ImageAvailable);
						}
						break;
					case R.id.IV_3: // Third Image
						if (v.getId() == R.id.IV_mainLogo) {
							SwapPic(i4ImageAvailable, i1ImageAvailable);
						}
						break;
					case R.id.IV_4: // Forth Image
						if (v.getId() == R.id.IV_mainLogo) {
							SwapPic(i5ImageAvailable, i1ImageAvailable);
						}
						break;

					default:
						break;
					}
					break;
				default:
					break;
				}
				return true;
			}
		});

		IV_1.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (i2ImageAvailable != null) {
					ClipData.Item item = new ClipData.Item((CharSequence) v
							.getTag());

					ClipData dragData = ClipData.newPlainText("", "");
					DragShadowBuilder myShadow = new DragShadowBuilder(v);
					v.startDrag(dragData, myShadow, v, 0);
				}
				return true;
			}
		});

		IV_2.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (i3ImageAvailable != null) {
					ClipData.Item item = new ClipData.Item((CharSequence) v
							.getTag());

					ClipData dragData = ClipData.newPlainText("", "");
					DragShadowBuilder myShadow = new DragShadowBuilder(v);
					v.startDrag(dragData, myShadow, v, 0);
				}
				return true;
			}
		});
		IV_3.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (i4ImageAvailable != null) {
					ClipData.Item item = new ClipData.Item((CharSequence) v
							.getTag());

					ClipData dragData = ClipData.newPlainText("", "");
					DragShadowBuilder myShadow = new DragShadowBuilder(v);
					v.startDrag(dragData, myShadow, v, 0);
				}
				return true;
			}
		});
		IV_4.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (i5ImageAvailable != null) {
					ClipData.Item item = new ClipData.Item((CharSequence) v
							.getTag());

					ClipData dragData = ClipData.newPlainText("", "");
					DragShadowBuilder myShadow = new DragShadowBuilder(v);
					v.startDrag(dragData, myShadow, v, 0);
				}
				return true;
			}
		});

		return view;
	}

	public void setImage(String picturePlace) {
		if (picturePlace.equals("2")) {
			if (i2ImageAvailable == null) {
				GalleryUploadImageDialog dialog = new GalleryUploadImageDialog(
						getActivity(), ProfileGallerySocialFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else {
				GalleryDeleteImageDialog dialog = new GalleryDeleteImageDialog(
						getActivity(), ProfileGallerySocialFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
				deleteImageId = i2ImageAvailable;
			}
		}
		if (picturePlace.equals("3")) {
			if (i3ImageAvailable == null) {
				GalleryUploadImageDialog dialog = new GalleryUploadImageDialog(
						getActivity(), ProfileGallerySocialFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else {
				GalleryDeleteImageDialog dialog = new GalleryDeleteImageDialog(
						getActivity(), ProfileGallerySocialFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
				deleteImageId = i3ImageAvailable;
			}
		}
		if (picturePlace.equals("4")) {
			if (i4ImageAvailable == null) {
				GalleryUploadImageDialog dialog = new GalleryUploadImageDialog(
						getActivity(), ProfileGallerySocialFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else {
				GalleryDeleteImageDialog dialog = new GalleryDeleteImageDialog(
						getActivity(), ProfileGallerySocialFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
				deleteImageId = i4ImageAvailable;
			}
		}
		if (picturePlace.equals("5")) {
			if (i5ImageAvailable == null) {
				GalleryUploadImageDialog dialog = new GalleryUploadImageDialog(
						getActivity(), ProfileGallerySocialFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else {
				GalleryDeleteImageDialog dialog = new GalleryDeleteImageDialog(
						getActivity(), ProfileGallerySocialFragment.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
				deleteImageId = i5ImageAvailable;
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == PICK_IMAGE) {
				selectedImagee = data.getData();
				performCrop(selectedImagee);
			}
		}

		if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
			selectedImagee = data.getData();
			performCrop(selectedImagee);

		}

		if (requestCode == CROP_PIC && resultCode == getActivity().RESULT_OK) {
			Uri selectedImage = selectedImagee;
			String[] filePath = { MediaStore.Images.Media.DATA };
			int desiredWidth = 800;
			Cursor c = getActivity().getContentResolver().query(selectedImage,
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
				base64 = Utilities.imageToBase64(photoBitMap);
				uploadPic();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	public void uploadPic() {
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("profile_id", Constants.PROFILE_ID_SOCIAL);
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("pic_id", "");
			jsonobj.put("place", picPlace);
			jsonobj.put("pic", base64);
			new ConnectionTaskSupportFragment(
					ProfileGallerySocialFragment.this, jsonobj)
					.execute(Constants.API_PICTURE_UPLOAD);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void setGalleryData(GalleryModel socialGallery) {
		// TODO Auto-generated method stub
		if (!socialGallery.getPicUrl1().equals("")) {
			Picasso.with(getActivity()).load(socialGallery.getPicUrl1()).fit()
					.centerCrop().into(IV_mainLogo);
			i1ImageAvailable = socialGallery.getPicId1();
		} else {
			IV_mainLogo.setImageDrawable(getResources().getDrawable(
					R.drawable.profile_gallery_main_img_bg));
			i1ImageAvailable = null;
		}

		if (!socialGallery.getPicUrl2().equals("")) {
			Picasso.with(getActivity()).load(socialGallery.getPicUrl2()).fit()
					.centerCrop().into(IV_1);
			i2ImageAvailable = socialGallery.getPicId2();
		} else {
			IV_1.setImageDrawable(getResources().getDrawable(
					R.drawable.profile_gallery_main_img_bg));

			i2ImageAvailable = null;
		}

		if (!socialGallery.getPicUrl3().equals("")) {
			Picasso.with(getActivity()).load(socialGallery.getPicUrl3()).fit()
					.centerCrop().into(IV_2);
			i3ImageAvailable = socialGallery.getPicId3();
		} else {
			IV_2.setImageDrawable(getResources().getDrawable(
					R.drawable.profile_gallery_main_img_bg));
			i3ImageAvailable = null;
		}

		if (!socialGallery.getPicUrl4().equals("")) {
			Picasso.with(getActivity()).load(socialGallery.getPicUrl4()).fit()
					.centerCrop().into(IV_3);
			i4ImageAvailable = socialGallery.getPicId4();
		} else {
			IV_3.setImageDrawable(getResources().getDrawable(
					R.drawable.profile_gallery_main_img_bg));
			i4ImageAvailable = null;
		}

		if (!socialGallery.getPicUrl5().equals("")) {
			Picasso.with(getActivity()).load(socialGallery.getPicUrl5()).fit()
					.centerCrop().into(IV_4);
			i5ImageAvailable = socialGallery.getPicId5();
		} else {
			IV_4.setImageDrawable(getResources().getDrawable(
					R.drawable.profile_gallery_main_img_bg));
			i5ImageAvailable = null;
		}
	}

	@Override
	public void onCameraButtonPressed() {
		// TODO Auto-generated method stub
		try {
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
		}catch (SecurityException e){
            MyAlertDialog dialog = new MyAlertDialog(getActivity(), "Camera Access Permission Denied! Please make sure you enable Camera Permission & File Write Permissions.");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            final Intent i = new Intent();
            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.setData(Uri.parse("package:" + getActivity().getPackageName()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            getActivity().startActivity(i);
		}
	}

	@Override
	public void onGalleryButtonPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PICK_IMAGE);
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			((RefreshDataListner) getActivity()).refreshData();
			if (!deleteImageFlag && !swapImageFlag) {
				Toast.makeText(getActivity(), "Picture has been uploaded!",
						Toast.LENGTH_SHORT).show();
			} else if (!swapImageFlag) {
				deleteImageFlag = false;
				Toast.makeText(getActivity(), "Picture has been deleted!",
						Toast.LENGTH_SHORT).show();
			} else {
				swapImageFlag = false;
				Toast.makeText(getActivity(), "Picture has been swaped!",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onDeleteCameraButtonPressed() {
		// TODO Auto-generated method stub
		Intent cameraIntent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	@Override
	public void onDeleteGalleryButtonPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PICK_IMAGE);
	}

	@Override
	public void onDeleteButtonPressed() {
		// TODO Auto-generated method stub
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("profile_id", Constants.PROFILE_ID_SOCIAL);
			jsonobj.put("pic_id", deleteImageId);
			new ConnectionTaskSupportFragment(
					ProfileGallerySocialFragment.this, jsonobj)
					.execute(Constants.API_PICTURE_DELETE);
			deleteImageFlag = true;
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void SwapPic(String picId, String TargetPicId) {
		// TODO Auto-generated method stub
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			swapImageFlag = true;
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("picid1", picId);
			jsonobj.put("picid2", TargetPicId);
			new ConnectionTaskSupportFragment(
					ProfileGallerySocialFragment.this, jsonobj)
					.execute(Constants.API_PICTURE_SWAP);
		} catch (JSONException ex) {
			ex.printStackTrace();
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
			Toast toast = Toast.makeText(getActivity(),
					"This device doesn't support the crop action!",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public interface RefreshDataListner {
		void refreshData();
	}

}
