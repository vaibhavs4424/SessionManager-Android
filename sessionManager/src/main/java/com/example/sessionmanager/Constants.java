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

import android.os.CountDownTimer;

import com.example.sessionmanager.SessionManager.CountDownFinish;

/**
 * Class that stores all the constants for Session manager library
 * 
 * @author Vaibhav Shukla
 * 
 */
public class Constants {

	public static Long				CmSessionTimeInMillis		= 65000l;

	public static Long				CmTimerDisplayInMillis		= 60000l;

	public static Long				mSessionTimeInMillis		= 65000l;

	public static Long				mTimerDisplayInMillis		= 60000l;

	public static CountDownFinish	countDownFinishListener		= null;

	public static CountDownTimer	screenTimeOutTimer			= null;

	public static FromBackGround	fromBackgroundListener		= null;

	public static Long				mSessionTimeLeftInMillis	= null;

    /**
     * Interface that notifies whenever the app loads from the background to foreground
     *
     * @author Vaibhav Shukla
     */
	public interface FromBackGround {

		public void fromBackgroundAction();

	}
}
