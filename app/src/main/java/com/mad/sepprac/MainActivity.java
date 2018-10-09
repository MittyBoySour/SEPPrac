package com.mad.sepprac;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button notificationButton;

    private static final String INTENT_EXTRA_TAG = "INTENT_EXTRA_TAG";
    private static final String INSTANCE_ID_TAG = "INSTANCE_ID_TAG";
    private static final String CHANNEL_ID = "CHANNEL_ID";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DBRef = database.getReference("message");

    private FirebaseInstanceId firebaseInstanceId;

    NotificationCompat.Builder notification;
    private static final int uniqueId = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBRef.setValue("Hello, World!");

        firebaseInstanceId = FirebaseInstanceId.getInstance();

        firebaseInstanceId.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String id = task.getResult().getId();
                String token = task.getResult().getToken();
                Log.d(INSTANCE_ID_TAG, "onComplete firebase instance id: " + id);
                Log.d(INSTANCE_ID_TAG, "onComplete firebase instance token: " + token);

            }
        });

        Log.d(INSTANCE_ID_TAG, "onCreate firebase instance id token: " + firebaseInstanceId.getInstanceId());
        Log.d(INSTANCE_ID_TAG, "onCreate firebase id token: " + firebaseInstanceId.getId());


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(INTENT_EXTRA_TAG, "Key: " + key + ", Value: " + value);
            }
        }

        // Button subscribeButton = findViewById(R.id.subscribeButton);

        // scheduleNotification(this, 3000, 0);


        notification = new NotificationCompat.Builder(this, CHANNEL_ID);
        notification.setAutoCancel(true);


        notificationButton = findViewById(R.id.notification_button);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send notification here
                notification.setSmallIcon(R.drawable.ic_launcher_foreground);
                notification.setTicker("ticker");
                notification.setWhen(System.currentTimeMillis() - 20000);
                notification.setContentTitle("title");
                notification.setContentText("text");

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(v.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);

                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(uniqueId, notification.build());

                scheduleNotification(v.getContext(), 3000, 0);

            }
        });



    }

    public void scheduleNotification1(Context context, long delay, int notificationId) {

    }


    public void scheduleNotification(Context context, long delay, int notificationId) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle("SEP new message")
            .setContentText("the text of the message")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);

    Intent intent = new Intent(context, MainActivity.class);
    PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    builder.setContentIntent(activity);

    Notification notification = builder.build();

    Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
    notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
    notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.SECOND, 5);


    long futureInMillis = SystemClock.elapsedRealtime() + delay;

    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

}
