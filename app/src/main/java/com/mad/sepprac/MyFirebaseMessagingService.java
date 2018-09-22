package com.mad.sepprac;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String REF_TOKEN_TAG = "REF_TOKEN_TAG";
    private static final String MESSAGE_TAG = "MESSAGE_TAG";
    private static final String NOTIFICATION_TAG = "NOTIFICATION_TAG";
    private static final int NOT_REQ_CODE = 0;
    private FirebaseInstanceId firebaseInstanceId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // check for data payload
        if (remoteMessage.getData().size() > 0) {
            Log.d(MESSAGE_TAG, "Message data payload: " + remoteMessage.getData());

            // schedule job / handle
        }

        // check for notification
        if (remoteMessage.getNotification() != null) {
            Log.d(NOTIFICATION_TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


        }

        // schedule notification on reception of message
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(REF_TOKEN_TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOT_REQ_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // String channelId = getString(R.string.default_notification_channel_id);
        // sound

        // build notification
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //
    }
}
