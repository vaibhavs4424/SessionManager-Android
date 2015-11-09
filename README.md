# SessionManager-Android
Session Manager: This is a simple library project which you can use in your project to handle session in your android applications. This project will handle the session functionality and display a graphical timer at the end so that the user can extend the session as per his/her need or the default action will occur[logout]. The timer displayed in this library is designed in such a way that it will be displayed appropriately according to the device screen [phone/tablet].

____
 
ScreenShot
----------
 
![Screenshot]  (https://github.com/vaibhavs4424/SessionManager-Android/blob/master/SessionManager.gif)



___
Usage
-----

Include the library and add it as a dependency in the main project

Step1: 	Add the following line in your base activity.

		//Variable to hold the instance of session manager class
		protected static SessionManager mSession;
	
		//Default values of the total timer, i.e session time
		Long sessionTimeOut = 10000l;

		//Default value of the time for which timer is displayed
		Long sessionTimeOutCountDown = 5000l;
	
		//Creates the session for your project 
		mSession = new SessionManager(BaseActivity.this, sessionTimeOut, sessionTimeOutCountDown);

Step 2: Add the following lines in base activity's over-ridden method  

		@Override
		public void onUserInteraction() {
			super.onUserInteraction();
			mSession.resetSessionTimer();
		}

Step 3: Your base activity should listen to CountDownFinish	by implementing SessionManager.CountDownFinish	

		This adds overridden method 

		@Override
		public void onCountDownFinish() {
			//Perform what you want to countdown finish here
		}

Step 4: Finally add this to your manifest file 

		<activity
            android:name="com.example.sessionmanager.SessionActivity"
            android:theme="@style/Theme.Transparent"
            tools:replace="android:theme" />

		
___
LICENSE
-------
 
Copyright 2015 Vaibhav Shukla

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
