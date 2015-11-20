package com.udacity.gradle.builditbigger;



import android.app.FragmentManager;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.richa.jokedisplaylibrary.JokeActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends ActionBarActivity implements TaskFragment.TaskCallbacks{
    InterstitialAd mInterstitialAd;
    private ProgressBar spinner;
    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private TaskFragment mTaskFragment;
    private FragmentManager fm;
    public static final String PREFS_NAME = "BuildItBiggerPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        updateStartJokeTaskPref(false);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                updateStartJokeTaskPref(true);
            }
        });
        requestNewInterstitial();

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean taskFlag = settings.getBoolean("startJokeTaskPref", false);
        if (taskFlag){
            startJokeRetrievalTask();
            spinner.setVisibility(View.VISIBLE);
            updateStartJokeTaskPref(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            startJokeRetrievalTask();
            spinner.setVisibility(View.VISIBLE);
        }
    }




    @Override
    public void onJokeRetrieved(String result) {
        //Launch activity to display joke.
        spinner.setVisibility(View.GONE);
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, result);
        startActivity(intent);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void startJokeRetrievalTask(){

        if (mTaskFragment ==null) {
            Log.d("RB", "test1");
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }else{
            Log.d("RB", "test2");
            fm.beginTransaction().remove(mTaskFragment).commit();
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }

    }

    private void updateStartJokeTaskPref(Boolean flag){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("startJokeTaskPref", flag);
        editor.commit();
    }


}
