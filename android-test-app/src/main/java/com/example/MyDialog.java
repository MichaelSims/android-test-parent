package com.example;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.events.GeneralEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MyDialog extends DialogFragment {

    private static final String TAG = MyDialog.class.getSimpleName();
    private final Bus bus = DIContainer.getInstance().getBus();
    private final SingletonEventProducer eventProducer = DIContainer.getInstance().getEventProducer();

    private TextView dialogText;

    @Override
    public void onAttach(final Activity activity) {
        Log.e(TAG, "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        Log.e(TAG, "onCreateDialog");
        return super.onCreateDialog(savedInstanceState);
    }

    @SuppressWarnings("UnusedDeclaration")
    @Subscribe
    public void consumeTheEvent(GeneralEvent event) {
        Toast.makeText(getActivity(), "I got the event!", Toast.LENGTH_SHORT).show();
        dialogText.setText("It was done");
        dialogText.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        View layout = inflater.inflate(R.layout.dialog_layout, container, false);
        dialogText = (TextView) layout.findViewById(R.id.dialog_text);
        layout.findViewById(R.id.do_background_work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogText.setText("Work is dispatched");
                eventProducer.doSomeBackgroundWorkThenBroadcast();
            }
        });

        return layout;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        Log.e(TAG, "onCancel");
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        Log.e(TAG, "onDismiss");
        super.onDismiss(dialog);
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        Log.e(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach");
        super.onDetach();
    }
}
