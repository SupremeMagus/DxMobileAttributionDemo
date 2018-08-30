package com.deltax.deltaxtracker;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class DeltaXTracker {
    private String trackingEndPoint = "https://webhook.site/d1ae6042-7425-4e04-8648-e0179791877b";
    private HttpPostRequest postRequest;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public DeltaXTracker(String xb, Context dxContext){
        //Xb = xb;
        pref = dxContext.getSharedPreferences("DeltaXPref", MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("xb",xb);
        editor.commit();
    }

    public void setCurrentClickId(String clickId){
        editor.putString("CurrentClickId",clickId);
        String currentClickPath = pref.getString("ClickPath","");
        String ClickPath = currentClickPath + (currentClickPath.length()>0?",":"")+clickId;
        editor.putString("ClickPath", ClickPath);
        editor.commit();
    }

    public void trackInstall(){
        Boolean firstRun = pref.getBoolean("First_Run", false);
        if(!firstRun){

        }
    }

    public void trackGoal(String goalId, TrackingParams params){
        String CurrentClickId = pref.getString("CurrentClickId", "");
        String ClickPath = pref.getString("ClickPath","");
        String xb = pref.getString("xb","");
        String qs = String.format("xb=%s&xgid=%s&clickid=%s&clickpath=%s",xb, goalId, CurrentClickId, ClickPath);
        if(params.xtid != null)
            qs += "&xtid="+params.xtid;
        if(params.xcv != null)
            qs += "&xcv="+params.xcv;
        if(params.xcc != null)
            qs += "&xcc="+params.xcc;
        if(params.xqty != null)
            qs += "&xqty="+params.xqty;
        if(params.xstage1 != null)
            qs += "&xstage1="+params.xstage1;
        if(params.xstage2 != null)
            qs += "&xstage2="+params.xstage2;
        String finalUrl = String.format("%s?%s",trackingEndPoint,qs);
        postRequest = new HttpPostRequest();
        try {
            postRequest.execute(finalUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

