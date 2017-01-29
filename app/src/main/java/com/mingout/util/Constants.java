package com.mingout.util;

import android.app.Application;
import android.widget.ImageView;

import com.mingout.adapters.ChatUsersListAdapter;
import com.mingout.fragments.JoinChatFragment;
import com.mingout.fragments.MatchesFragment;
import com.mingout.models.ChatModel;
import com.mingout.models.ChatRoomUserListModel;
import com.mingout.models.PreviewBillboardModel;
import com.mingout.models.PreviewProfileModel;
import com.mingout.models.PreviewRattingCommentModel;
import com.mingout.models.ProfileModel;
import com.mingout.models.WorkOutDevicesModel;
import com.mingout.models.workoutPlanModel;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

import java.util.ArrayList;
import java.util.List;

public class Constants extends Application{
	public final static int SOCIAL_FRAGMENT_FLAG = 1;
	public final static int BUSINESS_FRAGMENT_FLAG = 0;
	// ****************************** Data Flags
	// *
	public final static int FLAG_MY_PHRASE = 0;
	public final static int FLAG_SEX = 1;
	public final static int FLAG_STATUS = 2;
	public final static int FLAG_AGE = 3;
	public final static int FLAG_HEIGHT = 4;
	public final static int FLAG_LOOKING_FOR = 5;
	public final static int FLAG_ABOUT_ME = 6;
	public final static int FLAG_WANT_TO_MEET = 7;
	public final static int FLAG_JOB_TITLE = 8;
	public final static int FLAG_COMPANY = 9;
	public final static int FLAG_BIOGRAPHY = 10;
	// *
	// ****************************** Close Data Flags
	public final static int FLAG_LOCK_FRAGMENT = 11;
	public static int CURRENT_FRAGMENT_FLAG = 1;
	public static String AGE = null;
	public static int FRAGMENT_FLAG = 1;
	/*********************** Profile ID *******************/
	public static String USER_ID = null;
	public static String USER_EMAIL = null;
	public static String USER_NAME = null;
	public static String PROFILE_ID_SOCIAL = null;
	public static String PROFILE_SOCIAL_image = null;
	public static String PROFILE_ID_BUSINESS = null;
	public static String PROFILE_BUSINESS_image = null;
	public static String SOCIAL_AGE = null;
	public static String SOCIAL_DOB = null;
	public static String BUSINESS_AGE = null;

	// ********************** Chat Section ************/
	public static String BUSINESS_DOB = null;
	public static boolean QR_LOGIN = false;
	public static boolean JOIN_CHAT_FLAG = true;
	public static JoinChatFragment JOIN_CHAT_FRAG = new JoinChatFragment();
	public static boolean QR_LOGIN_TIMER = true;
	public static String QR_LOGIN_PROFILE_ID = null;
	public static String USER_LAT = null;
	public static String USER_LONG = null;
	public static String PROMOTIONS_UPPER_BANNER = null;
	public static String PROMOTIONS_LOWER_BANNER = null;
	public static String PROMOTIONS_BANNER_URL = "http://mingout.cloudapp.net/assets/banners";
	public static ProfileModel PROFILE_DATA = null;
	public static String HOST = "chatserver.mingout.com";
	public static int PORT = 5222;
	public static String service = "chatserver.mingout.com";
	public static List<ChatModel> LIST_CHAT_USERS = null;
	/****************** Menu Contact **********************/
	public static String CONTACT_RECEIVER_EMAIL = "test@gmail.com";
	public static String PROFILE_IMAGE_NOT_AVAILABLE = "http://mingout.cloudapp.net/assets/profile-images/";
	public static List<PreviewProfileModel> List_profile_preview = null;
	public static List<PreviewBillboardModel> List_billboard_preview = null;
	public static List<ChatRoomUserListModel> List_chat_users = null;
	public static ChatUsersListAdapter chatUserListAdapter = null;
	public static ArrayList<ChatModel> List_chat = null;
	public static List<ChatModel> List_chatCache = null;
	// public static List<workoutPlanModel> List_workoutPlan_preview = null; **
	// Not Used **
	public static List<WorkOutDevicesModel> List_workOutPlan_devices = null;
	public static List<workoutPlanModel> List_workOutPlan = null;
	public static List<PreviewRattingCommentModel> List_ratting_comment = null;
	public static String facebookProfileImage;
	/************************ API's Url ***********************/
	//public static String base_url = "http://beta.mingout.com/client/";      //************************ Development base URL ***********************
	//public static String base_url = "http://mingout.cloudapp.net/client/";		 //************************ Production base URL  ***********************
	public static String base_url = "http://mingout.cloudapp.net/mingout-api-android/api/json/";		 //************************ Production base URL  ***********************
	public static String API_PROFILE = base_url + "profile";
	public static String API_USER_PROFILE = base_url + "userprofile";
	//public static String base_url = "http://20floor.com/mingoutbeta/api/json/";		 //************************ Production base URL  ***********************
	//public static String base_url = "http://production.mingout.com/mingout-api-android/api/json/";

	//	BASE_URL @"http://mingout.cloudapp.net/mingout-api-android/api/json/"
	public static String API_SIGNIN = base_url + "signin";
	public static String API_REGISTRATION = base_url + "register";
	public static String API_LOGOUT = base_url + "logout";
	public static String API_PROFILE_UPDATE = base_url + "profileupdate";
	public static String API_PICTURE_UPLOAD = base_url + "picupload";
	public static String API_PICTURE_DELETE =  base_url + "deletepic";
	public static String API_PICTURE_SWAP = base_url + "swap_pic";
	public static String API_UPDATE_STATUS = base_url + "update_status";
	public static String API_JOIN_CHAT = base_url + "joinchat";
	public static String API_ROOM_USERS = base_url + "roomusers";
	public static String API_GET_PM_CHAT = base_url + "getpmchat";
	public static String API_SEND_PM_MESSAGE = base_url + "send_pm";
	public static String API_CONTACT_US = base_url + "sendemail";
	public static String API_PROMOTIONS = base_url + "businesstext";
	public static String API_SHOW_REVIEW_LIST = base_url + "showreview";
	public static String API_ADD_REVIEW_FLAG = base_url + "canpost";
	public static String API_SHOW_REVIEW_COMMENTS = base_url + "showcomments";
	public static String API_SAVE_REVIEW_COMMENTS = base_url + "savecomments";
	public static String API_SHOW_REVIEW_LABELS = base_url + "showlabels";
	public static String API_ADD_BILLBOARD = base_url + "addbillboard";
	public static String API_GET_BILLBOARD = base_url + "getbillboard";
	public static String API_HELP = base_url + "helptext";
	public static String API_SAVE_REVIEW = base_url + "savereview";
	public static String API_ADD_GYM_WORKOUT_PLAN = base_url + "addgymworkplan";
	public static String API_ADD_GYM_WORKOUT_DEVICE = base_url + "addgymdevice";
	public static String API_DEL_GYM_WORKOUT_PLAN = base_url + "delworkplan";
	public static String API_DEL_GYM_WORKOUT_DEVICE = base_url + "delgymdevices";
	public static String API_GET_GYM_WORKOUT_PLAN = base_url + "getgymworkplan";
	public static String API_GET_GYM_DEVICES = base_url + "getgymdevices";
	public static String API_UPDATE_GYM_WORKOUT_PLAN = base_url + "updategymworkplan";
	public static String API_UPDATE_GYM_WORKOUT_DEVICE = base_url + "updategymdevice";
	//public static String API_GET_QR_CODE = base_url + "get_qrcode";
	//public static String API_QR_VERIFY = base_url + "qr_verification";
	public static String API_QR_LOCATION_VERIFY = base_url + "location_verification";
	public static String URL_GET_BILLBOARD_IMAGE = "http://mingout.cloudapp.net/mingout-beta-api/assets/billboard/";
	/****************** Session Token ******/
	public static String SESSION_TOKEN = null;
	//public static String API_QR_LOGOUT = base_url + "qrlogout";
	public static String APP_KEY = "spiderman1450@gmail.com";
	public static String QR_CODE = null;
	public static String FACEBOOK_CHECK_IN = null;
	public static String COMPANY_NAME = "";
	public static String cachePromotionsText = "";
	public static String PROFILE_IMAGE_URL = null;
	public  static ImageView PreviewAddProfileComment = null;
	public  static ImageView PreviewSearchChat = null;
	public static ArrayList<MatchesFragment> fragments = null;
    public static List<ChatRoomUserListModel> List_matches = null;
    public static int PROVIEW_UPDATE_SCREEN = 0;
	// *********** Cache for matches settings **********
	public static String MATCHES_SETTINGS_GENDER = null;
	public static String MATCHES_SETTINGS_FROM_AGE = null;
	public static String MATCHES_SETTINGS_TO_AGE = null;
	public static String B_Name = null;
	public static String B_MY_PHRASE = null;
	public static String B_GENDER = null;
	public static String B_AGE_STR = null;
	public static String B_JOB_TITLE = null;
	public static String B_COMPANY = null;
	public static String B_BIOGRAPHY = null;
	public static String S_Name = null;
	public static String S_MY_PHRASE = null;
	public static String S_GENDER = null;
	public static String S_AGE_STR = null;
	public static String S_STATUS = null;
	public static String S_HEIGHT = null;
	public static String S_LOOKING = null;
	public static String S_ABOUT_ME = null;
	public static String S_WANT_TO_MEET = null;
	public static String UserImage = null;
	public static int UserAge = 0;
	static ConnectionConfiguration connConfig = new ConnectionConfiguration(HOST, PORT, service);
	public static XMPPConnection XMPP_CONNECTION = new XMPPConnection(connConfig);
	//public static int DASHBOARD_FRAG = 1;
}
