package com.mad.sepprac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {


    private static final String INTENT_EXTRA_TAG = "INTENT_EXTRA_TAG";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DBRef = database.getReference("message");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBRef.setValue("Hello, World!");

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(INTENT_EXTRA_TAG, "Key: " + key + ", Value: " + value);
            }
        }

        // Button subscribeButton = findViewById(R.id.subscribeButton);
    }
}
