package com.example.ramil.myuniversity;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "FCM Notification Message: " + remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendNewTokenToFirebase(token);
    }

    private void sendNewTokenToFirebase(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
