package com.mingout.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.MessageEventManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.mingout.adapters.ChatAdapter;
import com.mingout.chatModule.ConnectServer;
import com.mingout.chatModule.ConnectServer.ConnectedToServer;
import com.mingout.chatModule.ConnectServer.ReconnectServerListner;
import com.mingout.dialog.ChatMoreDialog;
import com.mingout.dialog.MyAlertDialog;
import com.mingout.models.ChatModel;
import com.mingout.models.ChatRoomUserListModel;
import com.mingout.util.ConnectionTask;
import com.mingout.util.Constants;
import com.mingout.util.ResultJSON;
import com.mingout.util.Utilities;

public class ChatUserActivity extends Activity implements ReconnectServerListner, ResultJSON, ConnectedToServer {

    ListView lv;
    ChatAdapter adapter;
    EditText ET_message;
    Button B_send;
    TextView TV_more, TV_name;
    TextView TV_userStatus;
    String name, sendMessageTo, secondPersonEmail, cacheUserStatus;
    boolean ringFlag = false;
    int ringFlagCounter = 0;
    Thread thread;

    private Handler mHandler;
    private PacketListener packetListener;

    // ListViewAutoScrollHelper mScrollHelper;
    String secondPUserName;
    ChatRoomUserListModel secondUserData;
    List<ChatModel> localChatList;
    List<ChatRoomUserListModel> onlineUserList;
    boolean typingFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_user_layout);

        lv = (ListView) findViewById(R.id.listView1);
        ET_message = (EditText) findViewById(R.id.ET_message);
        B_send = (Button) findViewById(R.id.B_send);
        TV_more = (TextView) findViewById(R.id.TV_more);
        TV_name = (TextView) findViewById(R.id.TV_name);
        TV_userStatus = (TextView) findViewById(R.id.TV_userStatus);

        try {
            sendMessageTo = getIntent().getStringExtra("to");
            TV_name.setText(getIntent().getStringExtra("name"));
            secondPersonEmail = getIntent().getStringExtra("email");

            String ee = secondPersonEmail;
            String[] email1 = ee.split("\\@");

            secondPUserName = email1[0];
            String[] emaill1 = email1[1].toString().split("\\.");
            secondPUserName += "_" + emaill1[0];
            secondPUserName += "_" + emaill1[1];
            secondPUserName += "@chatserver.mingout.com";

            secondUserData = (ChatRoomUserListModel) getIntent().getSerializableExtra("data");

        } catch (Exception ee) {
            // TODO: handle exception
        }
        mHandler = new Handler();
        Log.e("Created Chat user : ", secondPUserName);

        if (Constants.LIST_CHAT_USERS != null) {
            localChatList = new ArrayList<ChatModel>();
            for (int i = 0; i < Constants.LIST_CHAT_USERS.size(); i++) {
                if (Constants.LIST_CHAT_USERS.get(i).getChatUserName().equals(secondPUserName)) {
                    localChatList.add(Constants.LIST_CHAT_USERS.get(i));
                }
            }
            adapter = new ChatAdapter(ChatUserActivity.this, R.layout.item_chat_layout, localChatList, secondPUserName);
            lv.setAdapter(adapter);
        } else {
            Constants.LIST_CHAT_USERS = new ArrayList<ChatModel>();
            localChatList = new ArrayList<ChatModel>();
            adapter = new ChatAdapter(ChatUserActivity.this, R.layout.item_chat_layout, localChatList, secondPUserName);
            lv.setAdapter(adapter);
        }

        ET_message.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (Utilities.isConnected(ChatUserActivity.this)) {
                    if (Constants.XMPP_CONNECTION.isConnected()) {
                        Message ty = new Message(secondPUserName, Message.Type.chat);

                        MessageEventManager event = new MessageEventManager(Constants.XMPP_CONNECTION);
                        event.sendComposingNotification(secondPUserName, ty.getPacketID());
                    } else {
                        ConnectServer connectToXmppServer = new ConnectServer(ChatUserActivity.this, true);
                        connectToXmppServer.EstablishConnectionServer();
                    }

                } else {
                    MyAlertDialog dialog = new MyAlertDialog(ChatUserActivity.this, "Please connect to the Internet");
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        B_send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (Utilities.isConnected(ChatUserActivity.this)) {
                    if (Constants.XMPP_CONNECTION != null) {
                        if (Constants.XMPP_CONNECTION.isConnected()) {

                            if (!TextUtils.isEmpty(ET_message.getText())) {
                                String text = ET_message.getText().toString();
                                try {
                                    Message msg = new Message(secondPUserName, Message.Type.chat);
                                    msg.setBody(text);
                                    Constants.XMPP_CONNECTION.sendPacket(msg);

                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                ChatModel obj = new ChatModel();
                                obj.setChatUserName(secondPUserName);
                                obj.setMessageTo(text);
                                obj.setMessageFrom(null);
                                obj.setMessageFromTime(null);

                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                                String[] time = sdf.format(c.getTime()).split("\\:");
                                String AMDetail;
                                if (time[2].contains("AM")) {
                                    AMDetail = "AM";
                                } else {
                                    AMDetail = "PM";
                                }

                                obj.setMessageToTime(time[0] + ":" + time[1] + " " + AMDetail);

                                localChatList.add(obj);
                                // mHandler.post(new Runnable() {
                                // public void run() {
                                // // setListAdapter();
                                // adapter.notifyDataSetChanged();
                                // lv.setSelection(lv.getCount() - 1);
                                // }
                                // });
                                lv.invalidateViews();
                                adapter.notifyDataSetChanged();
                                lv.setSelection(lv.getCount() - 1);
                                Constants.LIST_CHAT_USERS.add(obj);
                                ET_message.setText("");

                            }
                        } else {
                            ConnectServer connectToXmppServer = new ConnectServer(ChatUserActivity.this, true);
                            connectToXmppServer.EstablishConnectionServer();
                        }
                    } else {
                        ConnectServer connectToXmppServer = new ConnectServer(ChatUserActivity.this, true);
                        connectToXmppServer.EstablishConnectionServer();
                    }
                } else {
                    MyAlertDialog dialog = new MyAlertDialog(ChatUserActivity.this, "Please connect to the Internet");
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        TV_more.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ChatMoreDialog dialog = new ChatMoreDialog(ChatUserActivity.this, secondUserData);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        try {
            PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
            packetListener = new PacketListener() {

                @Override
                public void processPacket(Packet packet) {
                    // TODO Auto-generated method stub
                    Message message = (Message) packet;
                    Log.e("New Message", message.getFrom());
                    // if (String.valueOf(secondPUserName + "/Smack").equals(
                    // message.getFrom())) {

                    String[] email1 = message.getFrom().split("\\/");
                    Log.e("Works", email1[0]);

                    if (String.valueOf(secondPUserName).equals(email1[0]) && !message.getBody().equals("")) {

                        Log.e("Response", secondPUserName + " : " + message.getBody());

                        ChatModel obj = new ChatModel();
                        obj.setChatUserName(secondPUserName);
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

                        localChatList.add(obj);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                lv.setSelection(lv.getCount() - 1);
                            }
                        });

                        // lv.invalidateViews();
                        Constants.LIST_CHAT_USERS.add(obj);

                    }
                }
            };
            Constants.XMPP_CONNECTION.addPacketListener(packetListener, filter);

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Exception", e.toString());
        }

        Constants.XMPP_CONNECTION.getChatManager().addChatListener(new ChatManagerListener() {

            @Override
            public void chatCreated(Chat arg0, boolean arg1) {
                // TODO Auto-generated method stub
                arg0.addMessageListener(new MessageListener() {

                    @Override
                    public void processMessage(Chat arg0, Message arg1) {
                        Log.e("Workkkkssss", "Typing..............");
                        if (!TV_userStatus.getText().toString().equals("Typing...")) {
                            TV_userStatus.setText("Typing...");
                        } else {
                            if (typingFlag) {
                                typingFlag = false;
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        TV_userStatus.setText(cacheUserStatus);
                                        typingFlag = true;
                                    }
                                }, 2000);
                            }
                        }

                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.e("Methord", "onStart");
        getUserOnlineStatus();

        if (Utilities.isConnected(ChatUserActivity.this)) {
            if (Constants.XMPP_CONNECTION == null) {
                ConnectServer connectToXmppServer = new ConnectServer(this);
                connectToXmppServer.EstablishConnectionServer();
            } else if (!Constants.XMPP_CONNECTION.isConnected()) {
                ConnectServer connectToXmppServer = new ConnectServer(this);
                connectToXmppServer.EstablishConnectionServer();
            }
        } else {
            MyAlertDialog dialog = new MyAlertDialog(ChatUserActivity.this, "Please connect to the Internet");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.e("Methord", "onStop");
        ringFlag = true;
    }

    @Override
    public void reconnectServerSucessfully() {
        // TODO Auto-generated method stub
        // connectWithChat();
    }

    public void newMessageListner(Message message) {
        // TODO Auto-generated method stub

    }

    public void refreshList() {
        new Handler().post(new Runnable() {
            public void run() {
                // setListAdapter();
                adapter.notifyDataSetChanged();
                lv.setSelection(lv.getCount() - 1);

            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("Method", "onDestroy");
        Constants.XMPP_CONNECTION.removePacketListener(packetListener);
        try {
            thread.stop();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void getUserOnlineStatus() {
        // TODO Auto-generated method stub

        JSONObject jsonobj;
        jsonobj = new JSONObject();
        try {
            // adding some keys
            jsonobj.put("appkey", "spiderman1450@gmail.com");
            jsonobj.put("session_token", Constants.SESSION_TOKEN);
            jsonobj.put("user_id", Constants.USER_ID);
            jsonobj.put("profile_id", Constants.QR_LOGIN_PROFILE_ID);
            jsonobj.put("qr_code", Constants.QR_CODE);
            jsonobj.put("lat", Constants.USER_LAT);
            jsonobj.put("lon", Constants.USER_LONG);
            jsonobj.put("limit", "100");
            new ConnectionTask(this, jsonobj, false).execute(Constants.API_ROOM_USERS);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void UpdateResult(Object obj) {
        // TODO Auto-generated method stub
        onlineUserList = new ArrayList<ChatRoomUserListModel>();
        try {
            String string = (String) obj;
            JSONObject jData = new JSONObject(string);
            if (jData.getString("status_code").equals("1")) {
                JSONObject jResponse = jData.getJSONObject("response");
                JSONArray userArray = jResponse.getJSONArray("users");

                for (int i = 0; i < userArray.length(); i++) {
                    JSONObject jsonObj = (JSONObject) userArray.get(i);

                    ChatRoomUserListModel modelObj = new ChatRoomUserListModel();

                    modelObj.setName(jsonObj.getString("full_name"));
                    modelObj.setAge(jsonObj.getString("age"));
                    modelObj.setEmail(jsonObj.getString("email"));
                    modelObj.setGender(jsonObj.getString("gender"));
                    modelObj.setMyPhrase(jsonObj.getString("my_phrase"));
                    modelObj.setProfileId(jsonObj.getString("profileid"));
                    modelObj.setUserId(jsonObj.getString("uid"));
                    modelObj.setType(jsonObj.getString("type"));
                    modelObj.setOriginal_picture(jsonObj.getString("original_picture"));
                    modelObj.setThumb_picture(jsonObj.getString("thumb_picture"));
                    modelObj.setLookingFor(jsonObj.getString("looking_for"));
                    modelObj.setUserName(Utilities.getXMPPuid(jsonObj.getString("email")));
                    modelObj.setUnreadMessageCounter("");

                    onlineUserList.add(modelObj);
                }
                for (int i = 0; i < onlineUserList.size(); i++) {
                    String userName = onlineUserList.get(i).getUserName() + "@chatserver.mingout.com/Smack";
                    if (userName.equals(secondPUserName + "/Smack")) {
                        TV_userStatus.setText("Online");
                        cacheUserStatus = "Online";
                        break;
                    } else {
                        TV_userStatus.setText("Offline");
                        cacheUserStatus = "Offline";
                    }
                }

            }
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(30000);
                        getUserOnlineStatus();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void connectToServer() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (!Constants.JOIN_CHAT_FRAG.isJoinChatRunning())
            Constants.JOIN_CHAT_FRAG.StartJoinChat();
    }

}
