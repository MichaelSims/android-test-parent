package com.example;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import com.example.events.GeneralEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class HelloAndroidActivity extends Activity {

    private final static String TAG = HelloAndroidActivity.class.getSimpleName();

    private final static String EXTRA_INSTANCE_ID = "EXTRA_INSTANCE_ID";

    private final Bus bus = DIContainer.getInstance().getBus();
    private final IdGenerator idGenerator = DIContainer.getInstance().getIdGenerator();
    private int instanceId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            instanceId = savedInstanceState.getInt(EXTRA_INSTANCE_ID);
        } else {
            instanceId = idGenerator.getUniqueId();
        }
        Log.e(TAG, "Instance id is " + instanceId);
        setContentView(R.layout.activity_main);
        View launchDialogButton = findViewById(R.id.launch_dialog_button);
        launchDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(new MyDialog(), "TAG");
                ft.commit();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_INSTANCE_ID, instanceId);
    }

    @Subscribe
    @SuppressWarnings("UnusedDeclaration")
    public void consumeEvent(GeneralEvent event) {
        if (event.getInstanceId() != instanceId) {
            Log.e(TAG, "This ain't mine!");
        }
        Log.e(TAG, "I got my event!");
        Toast.makeText(this, "I got my event!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.R.menu.main, menu);
        return true;
    }

}

