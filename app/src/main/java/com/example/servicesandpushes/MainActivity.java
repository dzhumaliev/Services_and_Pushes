package com.example.servicesandpushes;


import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.servicesandpushes.data.Events;
import com.example.servicesandpushes.utils.NotificationHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText editText;
    private Button buttonStartService;
    private Button buttonStopService;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_push);
        editText = findViewById(R.id.etDescription);
        buttonStartService = findViewById(R.id.btnStartService);
        textView = findViewById(R.id.tvTitle);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_push:
                NotificationHelper.createNotification(this, getTextFromEt());
                break;
            case R.id.btnStartService:
                startForeground();
                break;
            case R.id.btnStopService:
                stopForeground();
                break;
        }

    }

    private void stopForeground() {
        Intent intent = new Intent(this, TrackingService.class);
        stopService(intent);
    }

    private void startForeground() {
        Intent intent = new Intent(this, TrackingService.class);
        startService(intent);
    }


    private String getTextFromEt() {

        if (editText.getText() == null) return "No typed description";
        return editText.getText().toString();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMassages(Events event){
        Toast.makeText(this, event.getTitle(), Toast.LENGTH_LONG).show();
        textView.setText(event.getTitle());
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
