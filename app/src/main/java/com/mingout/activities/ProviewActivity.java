package com.mingout.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.mingout.chatModule.ConnectServer;
import com.mingout.chatModule.ConnectServer.ConnectedToServer;
import com.mingout.chatModule.ConnectServer.ServerNewMessageListner;
import com.mingout.dialog.ConfirmationDialog;
import com.mingout.dialog.ConfirmationDialog.ConfirmationDialogListner;
import com.mingout.dialog.MyAlertDialog;
import com.mingout.dialog.PreviewMoreDialog;
import com.mingout.fragments.ChatFragment;
import com.mingout.fragments.ChatSearchFragment.RemoveChatSearchFragmentTriger;
import com.mingout.fragments.MenuContactUsActivity;
import com.mingout.fragments.MenuHelpActivity;
import com.mingout.fragments.PreviewProfileListFragment;
import com.mingout.fragments.PromotionsFragment;
import com.mingout.models.ChatModel;
import com.mingout.models.QRscanLoginModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;
import com.squareup.picasso.Picasso;

public class ProviewActivity extends Activity implements RemoveChatSearchFragmentTriger, ResultJSON,
		ConfirmationDialogListner, ServerNewMessageListner, ConnectedToServer {

	TextView TV_more, TV_newMessageCounter, TV_title, TV_NavName, TV_gym, TV_loyalty, TV_match, TV_album;
    ImageView IV_menu, IV_NavLogo, IV_titleBarPromos, IV_titleBarChat;
	int fragFlag, newMessageCounter = 0;
	boolean qrLogoutFlag = false, appLogin = false, chatMessageCounterFlag = true, facebookCheckinFlag = true;
	ChatFragment chatFrag;
	SharedPreferences mPrefs;
    AccessToken accessToken;
    PromotionsFragment promotionsFragment;
    PreviewProfileListFragment previewProfileListFragment;
    TextView TV_navPromotions, TV_navChat, TV_navChatHistory, TV_navReviews, TV_navBillBoard, TV_navFacebookStatus, TV_navContactUs, TV_navHelp;
    RelativeLayout RL_settings, RL_logout;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proview_layout);

        TV_title = (TextView) findViewById(R.id.TV_title);
        TV_NavName = (TextView) findViewById(R.id.TV_NavName);
		TV_more = (TextView) findViewById(R.id.TV_more);
		TV_newMessageCounter = (TextView) findViewById(R.id.TV_newMessageCounter);
        TV_gym = (TextView) findViewById(R.id.TV_gym);
        TV_loyalty = (TextView) findViewById(R.id.TV_loyalty);
        TV_match = (TextView) findViewById(R.id.TV_match);
        TV_album = (TextView) findViewById(R.id.TV_album);

        TV_navPromotions = (TextView) findViewById(R.id.TV_navPromotions);
        TV_navChat = (TextView) findViewById(R.id.TV_navChat);
        TV_navChatHistory = (TextView) findViewById(R.id.TV_navChat);
        TV_navReviews = (TextView) findViewById(R.id.TV_navReviews);
        TV_navBillBoard = (TextView) findViewById(R.id.TV_navBillBoard);
        TV_navFacebookStatus = (TextView) findViewById(R.id.TV_navFacebookStatus);
        TV_navContactUs = (TextView) findViewById(R.id.TV_navContactUs);
        TV_navHelp = (TextView) findViewById(R.id.TV_navHelp);

        RL_settings = (RelativeLayout) findViewById(R.id.RL_settings);
        RL_logout = (RelativeLayout) findViewById(R.id.RL_logout);

        navigationView = (NavigationView) findViewById(R.id.NV_container);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        IV_NavLogo = (ImageView) findViewById(R.id.IV_NavLogo);
        IV_menu = (ImageView) findViewById(R.id.IV_menu);
        IV_titleBarPromos = (ImageView) findViewById(R.id.IV_promos);
        IV_titleBarChat = (ImageView) findViewById(R.id.IV_titleBarChat);
        Constants.PreviewAddProfileComment = (ImageView) findViewById(R.id.IV_addProviewComment);
        Constants.PreviewSearchChat = (ImageView) findViewById(R.id.IV_SearchChat);

		Constants.LIST_CHAT_USERS = null;
		Constants.List_chat_users = null;
		Constants.LIST_CHAT_USERS = new ArrayList<ChatModel>();

        promotionsFragment = new PromotionsFragment();
        previewProfileListFragment = new PreviewProfileListFragment();
        chatFrag = new ChatFragment();

        try {
			fragFlag = getIntent().getIntExtra("fragment flag", 0);
		} catch (Exception ee) {
			// TODO: handle exception
		}

        Toast.makeText(ProviewActivity.this, fragFlag  + "", Toast.LENGTH_SHORT).show();

        switch (Constants.CURRENT_FRAGMENT_FLAG){
            case Constants.SOCIAL_FRAGMENT_FLAG:
                TV_NavName.setText(Constants.USER_NAME + " / " + Constants.SOCIAL_AGE);
                Picasso.with(ProviewActivity.this).load(Constants.PROFILE_SOCIAL_image).fit().centerCrop().into(IV_NavLogo);
                break;

            case Constants.BUSINESS_FRAGMENT_FLAG:
                TV_NavName.setText(Constants.USER_NAME + " / " + Constants.BUSINESS_AGE);
                Picasso.with(ProviewActivity.this).load(Constants.PROFILE_BUSINESS_image).fit().centerCrop().into(IV_NavLogo);
                break;
        }


        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Gson gson = new Gson();
        String json = mPrefs.getString("facebook_access_token", "");
        accessToken = gson.fromJson(json, AccessToken.class);

		FragmentManager fmm = getFragmentManager();
		FragmentTransaction ftt = fmm.beginTransaction();
		ftt.add(Constants.JOIN_CHAT_FRAG, Constants.JOIN_CHAT_FRAG.getTag());
		ftt.commit();

		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		getData(Constants.USER_LAT, Constants.USER_LONG);

        switch (fragFlag){
            case 1:
                onChatClick();
                break;

            case 0:
                ft.replace(R.id.frag_container_proviewData, promotionsFragment);
                chatMessageCounterFlag = true;
                ft.commit();
                break;

            default:
                ft.replace(R.id.frag_container_proviewData, promotionsFragment);
                chatMessageCounterFlag = true;
                ft.commit();
                break;
        }

        TV_navPromotions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onPromotionClick();
                mDrawerLayout.closeDrawers();
            }
        });

        TV_navChat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onChatClick();
                mDrawerLayout.closeDrawers();
            }
        });

        TV_navReviews.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onPreviewClick();
                mDrawerLayout.closeDrawers();
            }
        });

        TV_navChatHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onChatClick();
                mDrawerLayout.closeDrawers();
            }
        });

        TV_navBillBoard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                Intent i = new Intent();
                i.setClass(ProviewActivity.this, BillboardActivity.class);
                startActivity(i);
            }
        });

        TV_navContactUs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                Intent ix = new Intent();
                ix.setClass(ProviewActivity.this, MenuContactUsActivity.class);
                startActivity(ix);
            }
        });

        TV_navHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                Intent ixx = new Intent();
                ixx.setClass(ProviewActivity.this, MenuHelpActivity.class);
                startActivity(ixx);
            }
        });

        TV_navFacebookStatus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                Intent iii = new Intent();
                iii.setClass(ProviewActivity.this, PreviewFacebookStatusActivity.class);
                startActivity(iii);
            }
        });

        IV_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){mDrawerLayout.closeDrawers();
                }else{mDrawerLayout.openDrawer(Gravity.LEFT);}
            }
        });

        IV_titleBarPromos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onPromotionClick();
            }
        });

        IV_titleBarChat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onChatClick();
            }
        });

        RL_settings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent();
                ii.setClass(ProviewActivity.this, MenuSettingsActivity.class);
                startActivity(ii);
                mDrawerLayout.closeDrawers();
            }
        });

        RL_logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmationDialog dialog = new ConfirmationDialog(ProviewActivity.this, "Are you sure you want to logout?");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                mDrawerLayout.closeDrawers();
            }
        });

        TV_gym.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent();
                ii.setClass(ProviewActivity.this, GymWorkPlanDevicesActivity.class);
                startActivity(ii);
                mDrawerLayout.closeDrawers();
            }
        });

        TV_gym.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent();
                ii.setClass(ProviewActivity.this, GymWorkPlanDevicesActivity.class);
                startActivity(ii);
                mDrawerLayout.closeDrawers();
            }
        });

        TV_loyalty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent();
                ii.setClass(ProviewActivity.this, LoyaltyProgramActivity.class);
                startActivity(ii);
                mDrawerLayout.closeDrawers();
            }
        });

        TV_match.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent();
                ii.setClass(ProviewActivity.this, MatchesActivity.class);
                startActivity(ii);
                mDrawerLayout.closeDrawers();
            }
        });

        TV_album.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

	}

    private void onChatClick(){
        if (Constants.QR_LOGIN) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frag_container_proviewData, chatFrag);
            ft.commit();

            chatMessageCounterFlag = false;
            newMessageCounter = 0;
            Constants.PreviewAddProfileComment.setVisibility(View.GONE);
            Constants.PreviewSearchChat.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(ProviewActivity.this, "Please wait!", Toast.LENGTH_SHORT).show();
        }
    }

	private void checkinFacebook(){
        Bundle params = new Bundle();
        //params.putString("message", Constants.USER_NAME + " checked-in via MingOut");
        params.putString("place", Constants.FACEBOOK_CHECK_IN);
        new GraphRequest(accessToken, "/me/feed", params, HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                    }
                }
        ).executeAsync();
	}

    private void onPromotionClick(){
        if (Constants.QR_LOGIN) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frag_container_proviewData, promotionsFragment);
            ft.commit();

            chatMessageCounterFlag = true;
            Constants.PreviewAddProfileComment.setVisibility(View.GONE);
            Constants.PreviewSearchChat.setVisibility(View.GONE);
        } else {
            Toast.makeText(ProviewActivity.this, "Please wait!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onPreviewClick(){
        if (Constants.QR_LOGIN) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frag_container_proviewData, previewProfileListFragment);
            ft.commit();

            chatMessageCounterFlag = true;
            Constants.PreviewAddProfileComment.setVisibility(View.VISIBLE);
            Constants.PreviewSearchChat.setVisibility(View.GONE);
        } else {
            Toast.makeText(ProviewActivity.this, "Please wait!", Toast.LENGTH_SHORT).show();
        }
    }

	@Override
	public void RemoveChatSearchFragment() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}

	private void getData(String Lat, String Long) {
		// TODO Auto-generated method stub

		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			// adding some keys
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			jsonobj.put("profile_id", Constants.QR_LOGIN_PROFILE_ID);
			jsonobj.put("lat", Lat);
			jsonobj.put("lon", Long);
			jsonobj.put("qr_code", Constants.QR_CODE);

			new ConnectionTask(this, jsonobj, false).execute(Constants.API_JOIN_CHAT);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void UpdateResult(Object obj) {
		// TODO Auto-generated method stub
		try {
			String string = (String) obj;
			JSONObject jData = new JSONObject(string);
			Log.e("Response", string);
			if (!qrLogoutFlag && !appLogin) {
				if (jData.getString("status_code").equals("1")) {
                    JSONObject response = jData.getJSONObject("response");
                    if(response.has("facebook") && facebookCheckinFlag){
                        Constants.FACEBOOK_CHECK_IN = response.getString("facebook");
                        Constants.COMPANY_NAME = response.getString("company_name");
                        TV_title.setText(Constants.COMPANY_NAME);
                        checkinFacebook();
                        facebookCheckinFlag = false;
                    }
				} else {
				}
			} else if (!appLogin) {
				Log.e("Response :", string);
				qrLogoutFlag = false;
				Constants.QR_LOGIN = false;
				Constants.QR_LOGIN_TIMER = true;
				Constants.QR_LOGIN_PROFILE_ID = null;
				Constants.QR_CODE = null;
				Constants.chatUserListAdapter = null;
				Constants.List_chat_users = null;
				Constants.PROMOTIONS_UPPER_BANNER = null;
				Constants.PROMOTIONS_LOWER_BANNER = null;
                Constants.cachePromotionsText = "";
                Constants.List_profile_preview = null;

				Intent i = new Intent();
				i.setClass(ProviewActivity.this, SplashActivity.class);
				startActivity(i);
				finish();

				if (Constants.XMPP_CONNECTION.isConnected()) {
					Constants.XMPP_CONNECTION.disconnect();
				}

			} else {
				JSONObject jResponse = (JSONObject) jData.getJSONObject("response");
				Constants.SESSION_TOKEN = jResponse.getString("session_token");

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		ConfirmationDialog dialog = new ConfirmationDialog(this, "Are you sure you want to logout?");
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
	}

	@Override
	public void confirmationDialogYesButtonPressed() {
		// TODO Auto-generated method stub
		logoutQR();
	}

	private void logoutQR() {
		// TODO Auto-generated method stub
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		try {
			qrLogoutFlag = true;
			jsonobj.put("appkey", "spiderman1450@gmail.com");
			jsonobj.put("session_token", Constants.SESSION_TOKEN);
			jsonobj.put("user_id", Constants.USER_ID);
			new ConnectionTask(this, jsonobj).execute(Constants.API_LOGOUT);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void newMessageListner(Message message) {
		// TODO Auto-generated method stub

	}

    @Override
    public void connectToServer() {
        // TODO Auto-generated method stub
        PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
        try {
            Constants.XMPP_CONNECTION.addPacketListener(new PacketListener() {
                public void processPacket(Packet packet) {
                    final Message message = (Message) packet;

                    ProviewActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (chatFrag != null && message.getBody() != null){
                                chatFrag.newMessageListner(message);
                            }

                            if (chatMessageCounterFlag &&  message.getBody() != null) {
                                newMessageCounter++;
                                TV_newMessageCounter.setVisibility(View.VISIBLE);
                                TV_newMessageCounter.setText(String.valueOf(newMessageCounter));
                            }
                        }
                    });

                    if(message.getBody() != null){
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        String[] email1 = message.getFrom().split("\\/");

                        ChatModel obj = new ChatModel();
                        obj.setChatUserName(email1[0]);
                        obj.setMessageFrom(message.getBody());
                        obj.setMessageTo(null);
                        obj.setMessageToTime(null);

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                        String[] time = sdf.format(c.getTime()).split("\\:");
                        String AMDetail;
                        if (time[2].contains("AM")) {
                            AMDetail = "AM";
                        } else {
                            AMDetail = "PM";
                        }

                        obj.setMessageFromTime(time[0] + ":" + time[1] + " " + AMDetail);
                        Constants.LIST_CHAT_USERS.add(obj);
                    }
                }
            }, filter);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("Method", "onStart");
		if (Utilities.isConnected(ProviewActivity.this)) {
			if (Constants.XMPP_CONNECTION == null) {
				ConnectServer connectToXmppServer = new ConnectServer(this);
				connectToXmppServer.EstablishConnectionServer();
			} else if (!Constants.XMPP_CONNECTION.isConnected()) {
				ConnectServer connectToXmppServer = new ConnectServer(this);
				connectToXmppServer.EstablishConnectionServer();
			}
		} else {
			MyAlertDialog dialog = new MyAlertDialog(ProviewActivity.this, "Please connect to the Internet");
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Constants.JOIN_CHAT_FLAG = false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        updateScreen();

		if (!Constants.JOIN_CHAT_FRAG.isJoinChatRunning())
			Constants.JOIN_CHAT_FRAG.StartJoinChat();
	}

    public void updateScreen(){
        switch (Constants.PROVIEW_UPDATE_SCREEN){
            case 0:
                onChatClick();
                Constants.PROVIEW_UPDATE_SCREEN = 100;
                break;

            case 1:
                onPromotionClick();
                Constants.PROVIEW_UPDATE_SCREEN = 100;
                break;

            case 2:
                onPreviewClick();
                Constants.PROVIEW_UPDATE_SCREEN = 100;
                break;
        }
    }
}