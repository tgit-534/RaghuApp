package app.actionnation.actionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class CommonClass {


    public FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    //uri to store file
    private Uri filePath;


    public Bitmap ProcessingBitmap(String captionString, Context ct) {
        Bitmap bm1 = null;
        Bitmap newBitmap = null;

        bm1 = BitmapFactory.decodeResource(ct.getResources(), R.drawable.untitled)
                .copy(Bitmap.Config.ARGB_8888, true);

        Bitmap.Config config = bm1.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bm1, 0, 0, null);

        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.BLUE);
        paintText.setTextSize(70);
        paintText.setStyle(Paint.Style.FILL);
        //paintText.setShadowLayer(10f, 10f, 10f, Color.BLACK);

        Rect textRect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        paintText.getTextBounds(captionString, 0, captionString.length(), textRect);

        int xPos = (canvas.getWidth() / 2) - 200;
        int yPos = (int) ((canvas.getHeight() / 2) - ((paintText.descent() + paintText.ascent()) / 2)) - 30;
        canvas.drawText(captionString, xPos, yPos, paintText);
        //canvas.drawText(captionString, 0, textRect.height(), paintText);

    /*    } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        //mImageView.setImageBitmap(newBitmap);

        return newBitmap;
    }


    public Drawable convertImagePathToDrawable(String imagePath) {
        Drawable d = Drawable.createFromPath(imagePath);
        return d;
    }


    public void logoutFromFacebook(Context ct) {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(ct, GoFbLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ct.startActivity(intent);

    }

    public void signOut(Context ct, GoogleSignInClient mGoogleSignInClient) {
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(ct, GoFbLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ct.startActivity(intent);
    }


    public GoogleSignInClient GoogleStart(Context ct) {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ct.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(ct, gso);
        return mGoogleSignInClient;
    }

    public void ShareToOtherPlatforms(String setType, String ShareBody, String ShareSubject, String strTitle, Context ct) {

        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "Your body is here";
        String shareSub = "Your subject";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        ct.startActivity(Intent.createChooser(myIntent, "Share using"));
    }

    public MenuItem menuGenerationGeneral(Activity ct, MenuItem item, GoogleSignInClient mGoogleSignInClient) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menulogout:
                Intent in = ct.getIntent();
                String StrData = in.getStringExtra("auth");


                // String StrData= in.getStringExtra("auth");
                if (StrData.equals("fb")) {

                    logoutFromFacebook(ct);
                } else if (StrData.equals("google") || StrData.equals("firebase")) {

                    signOut(ct, mGoogleSignInClient);

                }
                break;

        }
        return item;
    }

    public void callToast(Context ct, String strMessage) {
        Toast.makeText(ct, strMessage, Toast.LENGTH_LONG).show();
    }

    public void redirectToLoginPage(Context fromContext) {
        Intent intent = new Intent(fromContext, GoFbLogin.class);
        intent.putExtra("KEY_ACTIVITY_NAME", "fp");
        fromContext.startActivity(intent);
    }

}
