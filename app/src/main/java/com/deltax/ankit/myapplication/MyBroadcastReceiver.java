package com.deltax.ankit.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.deltax.deltaxtracker.DeltaXTracker;
import com.deltax.deltaxtracker.TrackingParams;

public class MyBroadcastReceiver extends BroadcastReceiver {
    DeltaXTracker dxt;
    InstallReferrerClient mReferrerClient;
    @Override
    public void onReceive(Context context, final Intent intent) {
        dxt = new DeltaXTracker("Test1234",context);
        String mAction = intent.getAction();
        if (mAction == "com.example.ankit.intent.manual") {
            mReferrerClient = InstallReferrerClient.newBuilder(context).build();
            mReferrerClient.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    String clickid = "";
                    switch (responseCode) {
                        case InstallReferrerClient.InstallReferrerResponse.OK:
                            try {
                                ReferrerDetails response = mReferrerClient.getInstallReferrer();
                                clickid = response.getInstallReferrer();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            dxt.setCurrentClickId(clickid);
                            TrackingParams params = new TrackingParams();
                            params.xtid = "i1234";
                            dxt.trackGoal("Install",params);
                            mReferrerClient.endConnection();
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                            // API not available on the current Play Store app
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                            // Connection could not be established
                            break;
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                }
            });
        }
    }
}
