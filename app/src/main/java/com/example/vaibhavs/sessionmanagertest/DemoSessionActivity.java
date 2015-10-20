/*
 * Copyright (C) 2015 Vaibhav Shukla
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.vaibhavs.sessionmanagertest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sessionmanager.SessionManager;

/**
 * A sample test activity to demonstrate the session manager functionality with UI.
 *
 * @author Vaibhav Shukla
 */

public class DemoSessionActivity extends ActionBarActivity implements SessionManager.CountDownFinish {

    //Variable to hold the instance of session manager class
    protected static SessionManager mSession;

    //Edit-text to display timer
    private EditText mTimerDisplayTime, mTotalSessionTime;

    //Button to start the timer
    private Button mStartCountDown;

    //Default values of the total timer, i.e session time
    Long sessionTimeOut = 10000l;

    //Default value of the time for which timer is displayed
    Long sessionTimeOutCountDown = 5000l;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_session);
        mTimerDisplayTime = (EditText) findViewById(R.id.display_timer);
        mTotalSessionTime = (EditText) findViewById(R.id.complete_timer);
        mStartCountDown = (Button) findViewById(R.id.start_timer);

//On click listener for the timer button
        mStartCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mTimerDisplayTime.getText() == null && mTimerDisplayTime.getText().equals("")) || (mTotalSessionTime.getText() == null && mTotalSessionTime.getText().equals("")))
                    Toast.makeText(DemoSessionActivity.this, "Fields cannot be left blank before starting the timer.", Toast.LENGTH_LONG).show();
                else {
                    sessionTimeOut = Long.parseLong(mTotalSessionTime.getText().toString());
                    sessionTimeOutCountDown = Long.parseLong(mTimerDisplayTime.getText().toString());
                    mSession.stopCountDownTimer();
                    mSession = new SessionManager(DemoSessionActivity.this, sessionTimeOut, sessionTimeOutCountDown);

                }


            }
        });

        //Creating a session timer with the values specified
        mSession = new SessionManager(this, sessionTimeOut, sessionTimeOutCountDown);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_session, menu);
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

    /**
     * Method to reset the timer whenever user interacts with the UI.
     * This method should be called in the base activity which all the activities extend,
     * so that the timer is reset whenever user interacts anywhere in the app except the Login page.
     *
     * @author Vaibhav Shukla
     */
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        mSession.resetSessionTimer();
    }


    /**
     * Over-ridden method of the interface which is called as soon as the timer is is complete.
     * Write the code which needs to be called on countdown finish.
     *
     * @author Vaibhav Shukla
     */
    @Override
    public void onCountDownFinish() {
        Toast.makeText(this, "Countdown finished", Toast.LENGTH_LONG).show();
        sessionTimeOut = 10000l;
        sessionTimeOutCountDown = 5000l;
        mSession = new SessionManager(this, sessionTimeOut, sessionTimeOutCountDown);

    }
}
