package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by richa on 11/19/15.
 */
public class TaskFragmentTest extends ActivityInstrumentationTestCase2<TaskFragmentTest.TestActivity>{

    TestActivity testActivity;
    TaskFragment taskFragment;
    FragmentManager fm;
    static final String TAG_TASK_FRAGMENT = "task_fragment";
    String joke=null;

    public TaskFragmentTest(Class<TestActivity> activityClass) {
        super(activityClass);
    }

    public class TestActivity extends Activity implements TaskFragment.TaskCallbacks{

        @Override
        public void onJokeRetrieved(String result) {
            joke = result;
        }
    }



    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testActivity = (TestActivity)getActivity();
        fm = testActivity.getFragmentManager();

    }

    public void testTaskFragment() throws Exception {
        taskFragment = new TaskFragment();
        fm.beginTransaction().add(taskFragment,TAG_TASK_FRAGMENT).commit();
        Thread.sleep(30000);
        assertFalse(joke.isEmpty());
        assertTrue(!joke.isEmpty());




    }



}
