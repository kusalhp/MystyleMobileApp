package com.sliit.www.mystylemobile;

import android.app.Application;

import com.parse.Parse;

/**
 * The Class ChattApp is the Main Application class of this app. The onCreate
 * method of this class initializes the Parse.
 */
public class ChattApp extends Application
{

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

        Parse.initialize(this, "lXHMgHkRrgMXIoOOK2e5jv21TUSqr7XxcPFwAjKd", "8pRpUxr8E74pU6zpohiodieRGhq6R6OMdqe23l9q");

	}
}
