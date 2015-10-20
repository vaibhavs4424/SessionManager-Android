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
package com.example.sessionmanager;

import android.app.Application;
import android.content.Context;

/**
 * Session Application class
 *
 * @author VaibhavS
 */
public class SessionApplication extends Application {

    //Context of the session manager library application
    private static Context mApplicationContext = null;


    public void onCreate() {
        super.onCreate();
        setGlobalApplicationContext(getApplicationContext()); // Set the application context

    }

    public static Context getGlobalApplicationContext() {
        return mApplicationContext;
    }

    public void setGlobalApplicationContext(Context mApplciationContext) {
        this.mApplicationContext = mApplciationContext;
    }

}
