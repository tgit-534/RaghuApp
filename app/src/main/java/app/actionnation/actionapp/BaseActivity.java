package app.actionnation.actionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;



public class BaseActivity extends AppCompatActivity {



    @VisibleForTesting

    public ProgressDialog mProgressDialog;
    private static final String TAG = "BaseActivity_GA";
    public void showProgressDialog() {
        Log.d(TAG, "ShowP1:");
        if (mProgressDialog == null) {
            Log.d(TAG, "ShowP2:");
            mProgressDialog = new ProgressDialog(this);
            Log.d(TAG, "ShowP3:");

            //mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setMessage("Welcome to Action Nation!");
            Log.d(TAG, "ShowP4:");


            mProgressDialog.setIndeterminate(true);
            Log.d(TAG, "ShowP5:");

        }


        Log.d(TAG, "ShowP6:");
        mProgressDialog.show();
        Log.d(TAG, "ShowP7:");

    }

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();

        }

    }

    public void hideKeyboard(View view) {

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }



    @Override

    public void onStop() {

        super.onStop();

        hideProgressDialog();

    }



}
