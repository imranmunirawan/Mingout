package com.mingout.chatModule;

import android.app.Activity;
import android.util.Log;

import com.mingout.util.Constants;
import com.mingout.util.Utilities;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

public class ConnectServer {

    String HOST = "chatserver.mingout.com";
    int PORT = 5222;
    String USERNAME, PASSWORD, myChatUserName;
    String service = "chatserver.mingout.com";
    // XMPPConnection connection;
    Activity context;
    boolean returnConnectionEstablishFlag = false;

    public ConnectServer(Activity context) {
        this.context = context;

        USERNAME = Utilities.getXMPPuid(Constants.USER_EMAIL);
        PASSWORD = Utilities.getXMPPuid(Constants.USER_EMAIL);
    }

    public ConnectServer(Activity context, boolean b) {
        // TODO Auto-generated constructor stub

        this.context = context;

        String e = Constants.USER_EMAIL;
        String[] email = e.split("\\@");

        myChatUserName = email[0];
        String[] emaill = email[1].toString().split("\\.");
        myChatUserName += "_" + emaill[0];
        myChatUserName += "_" + emaill[1];

        USERNAME = myChatUserName;
        PASSWORD = myChatUserName;

        returnConnectionEstablishFlag = true;
    }

    public void EstablishConnectionServer() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    // ConnectionConfiguration connConfig = new
                    // ConnectionConfiguration(
                    // HOST, PORT, service);
                    // connConfig.setReconnectionAllowed(true);
                    // Constants.XMPP_CONNECTION = new
                    // XMPPConnection(connConfig);

                    Constants.XMPP_CONNECTION.connect();
                    Log.i("XMPPChatDemoActivity", "Connected to " + Constants.XMPP_CONNECTION.getHost());

                } catch (Exception ex) {
                    Log.e("XMPPChatDemoActivity", "Failed to connect to " + ex.toString());
                    Log.e("XMPPChatDemoActivity", ex.toString());

					/*MyAlertDialog dialog = new MyAlertDialog(context, "Failed to connect to Chat Server! Please try later");
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();*/
                }
                try {
                    Constants.XMPP_CONNECTION.login(USERNAME, PASSWORD);
                    if (Constants.XMPP_CONNECTION.isAuthenticated()) {
                        Log.i("Login :", "Sucessfully");
                    }
                    Log.i("XMPPChatDemoActivity", "Logged in as " + Constants.XMPP_CONNECTION.getUser());
                    Log.i("XMPPChatDemoActivity", "-- User Authenticated " + USERNAME);
                    // Set the status to available
                    Presence presence = new Presence(Presence.Type.subscribe);
                    presence.setMode(Presence.Mode.available);
                    presence.setStatus("Online");
                    Constants.XMPP_CONNECTION.sendPacket(presence);

                    if (returnConnectionEstablishFlag) {
                        ((ReconnectServerListner) context)
                                .reconnectServerSucessfully();
                    }
                    ((ConnectedToServer) context).connectToServer();

                } catch (XMPPException ex) {
                    Log.e("XMPPChatDemoActivity", "Failed to log in as " + USERNAME);
                    Log.e("XMPPChatDemoActivity", ex.toString());

					/*MyAlertDialog dialog = new MyAlertDialog(context, "Unable to login Chat server! Please try later");
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();*/
                }

            }
        });
        t.start();

    }

    public interface ReconnectServerListner {
        void reconnectServerSucessfully();
    }

    public interface ServerNewMessageListner {
        void newMessageListner(Message message);
    }

    public interface ConnectedToServer {
        void connectToServer();
    }
}
