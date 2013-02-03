package com.boogiesoftware.fragmentretaintest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class RetainedFragment extends Fragment {
	private final static String TAG = "FragmentRetainTest";

	private boolean mWorking = false;

	public static class DialogFrag extends DialogFragment {
		@Override
		public void onDismiss(DialogInterface dialog) {
			super.onDismiss(dialog);

			FragmentManager fm = getFragmentManager();
			if (fm != null) {
				RetainedFragment frag = (RetainedFragment) fm
						.findFragmentByTag("frag");
				frag.onWorkDone();
			}
		}

		@Override
		public Dialog onCreateDialog(Bundle saveInstanceState) {
			ProgressDialog dlg = new ProgressDialog(getActivity());
			dlg.setMessage("Working...");
			dlg.setIndeterminate(true);

			Log.d(TAG, "RetainedFragment DialogFrag onCreateDialog");

			return dlg;
		}
	}

	@Override
	public void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);

		Log.d(TAG, "RetainedFragment onCreate, savedState: "
				+ pSavedInstanceState);

		setRetainInstance(true);

		if (pSavedInstanceState != null) {
			if (pSavedInstanceState.getBoolean("working")) {
				Log.d(TAG,
						"RetainedFragment onCreate: was working, remove dialog");

				Fragment fr = getFragmentManager().findFragmentByTag("dialog");
				getFragmentManager().beginTransaction().remove(fr).commit();
			}
		}
	}

	protected void onWorkDone() {
		Log.d(TAG, "RetainedFragment work done");
		mWorking = false;
	}

	@Override
	public void onSaveInstanceState(Bundle pBundle) {
		pBundle.putBoolean("working", mWorking);
	}

	@Override
	public void onStop() {
		super.onStop();

		Log.d(TAG, "RetainedFragment onStop");
	}

	public void startWork() {
		DialogFragment frag = new DialogFrag();
		frag.show(getFragmentManager(), "dialog");
		mWorking = true;

		Log.d(TAG, "RetainedFragment start work");
	}
}
