package com.deltax.ankit.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.deltax.deltaxtracker.DeltaXTracker;
import com.deltax.deltaxtracker.TrackingParams;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public DeltaXHelper dxh = new DeltaXHelper();
    public DeltaXTracker dxt; //new DeltaXTracker("Test1234",dxContext);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            TrackingParams tp = new TrackingParams();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("Track Home");
                    tp.xtid = "12121";
                    tp.xqty = "12";
                    dxt.trackGoal("A12341", tp);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("Track Dashboard");
                    tp = new TrackingParams();
                    tp.xtid = "d1232";
                    tp.xcv = "12";
                    dxt.trackGoal("A12341", tp);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("Track notifications");
                    tp = new TrackingParams();
                    tp.xtid = "n980";
                    tp.xqty = "1";
                    tp.xcv = "14";
                    tp.xcc = "USD";
                    dxt.trackGoal("A12341", tp);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context dxContext = getApplicationContext();
        dxt = new DeltaXTracker("Test1234",dxContext);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData!= null){
            String clickId = appLinkData.getQueryParameter("clickid");
            dxt.setCurrentClickId(clickId);
        }
    }

}
