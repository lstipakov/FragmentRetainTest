package com.boogiesoftware.fragmentretaintest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	private final static String TAG = "FragmentRetainTest";

	RetainedFragment mFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(TAG,
				"MainActivity onCreate, saved instance: "
						+ Boolean.toString(savedInstanceState != null));

		FragmentManager fm = getSupportFragmentManager();

		// Check to see if we have retained the worker fragment.
		mFrag = (RetainedFragment) fm.findFragmentByTag("frag");

		// If not retained (or first time running), we need to create it.
		if (mFrag == null) {
			mFrag = new RetainedFragment();
			fm.beginTransaction().add(mFrag, "frag").commit();
			Log.d(TAG, "MainActivity: fragment does not exist, creating it");
		} else {
			Log.d(TAG, "MainActivity: fragment already exist");
		}

		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mFrag.startWork();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();

		Log.d(TAG, "MainActivity onPause");
	}

	@Override
	public void onResume() {
		super.onResume();

		Log.d(TAG, "MainActivity onResume");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		Log.d(TAG, "MainActivity onDestroy, isFinishing: " + isFinishing());
	}
}
