package com.example;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class HelloAndroidActivity extends Activity {

    private final static String TAG = HelloAndroidActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.R.menu.main, menu);
        return true;
    }

}

