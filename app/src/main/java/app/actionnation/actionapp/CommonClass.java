package app.actionnation.actionapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;

import static com.facebook.FacebookSdk.getApplicationContext;

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
            case R.id.itemFriendsInvite:
                InviteToActionNation obj = new InviteToActionNation();
                obj.CreateLink(ct);
                break;
            case R.id.knowYourCareer:
                Intent intent = new Intent(ct, knowyourcareer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ct.startActivity(intent);
                break;
            case R.id.ActionNationPhilosophy:
                Intent homepage1 = new Intent(ct, displaydata.class);
                Bundle mBundle1 = new Bundle();
                mBundle1.putString("auth", "google");
                homepage1.putExtras(mBundle1);
                ct.startActivity(homepage1);
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

    public String compareDates(long dtHome, boolean dateOrTime) {
        String drt = "";
        Date dateTime = new Date(dtHome);
        if (dateOrTime == true) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            drt = sdf.format(dateTime);
        } else if (dateOrTime == false) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
            drt = sdf2.format(dateTime);
        }
        return drt;
    }

    final static int RQS_1 = 1;

    public void setAlarm(Calendar targetCal, Context ctx) {

        Intent intent = new Intent(ctx, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                ctx, RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);
    }

    String TAG = "InsertImage";

    public void InsertImage(Context ctx, Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(ctx.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, "UniqueFileName" + ".jpg");
        Log.e(TAG, "Entered Insertion1");
        if (!file.exists()) {
            Log.d("path", file.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                Log.e(TAG, "Entered Insertion2");

            } catch (java.io.IOException e) {
                Log.e(TAG, "Entered Insertion3");

                e.printStackTrace();
            }
        }
    }


    public boolean CheckLeapYear(int year) {
        boolean bl = false;
        if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
            bl = true;
        else
            bl = false;

        return bl;
    }


    public void setTime(Context ctx, int hrs, int mins) {
        Intent intAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, hrs);
        c.add(Calendar.MINUTE, mins);

        SimpleDateFormat Hr = new SimpleDateFormat("H");
        SimpleDateFormat Min = new SimpleDateFormat("mm");

        String hr = Hr.format(c.getTime());
        String min = Min.format(c.getTime());
        intAlarm.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hr));

        intAlarm.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(min));
        // AlarmClock.
        ctx.startActivity(intAlarm);
    }


    public void AlertBoxYesNo(Context ctx) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        // Set a title for alert dialog
        builder.setTitle("Select your answer.");

        // Ask the final question
        builder.setMessage("Are you sure to hide?");

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when user clicked the Yes button
                // Set the TextView visibility GONE
            }
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when No button clicked
                Toast.makeText(getApplicationContext(),
                        "No Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }


    public void InsertAttentionScore(DbHelper db, String fbId, int dayOfTheYear, int yr, int disScore, int traScore, int totDistraction, int totTraction) {
        Cursor curAttention = db.getAttentionScore(fbId, dayOfTheYear, yr);
        int disScoreDb = 0;
        int traScoreDb = 0;
        int disTotalDb = 0;
        int traTotDb = 0;
        int countScore = curAttention.getCount();
        if (curAttention.getCount() > 0) {

            while (curAttention.moveToNext()) {

                disScoreDb = Integer.parseInt(curAttention.getString(2));
                traScoreDb = Integer.parseInt(curAttention.getString(3));
                disTotalDb = Integer.parseInt(curAttention.getString(4));
                traTotDb = Integer.parseInt(curAttention.getString(5));
            }
        }

        if (disTotalDb > totDistraction)
            totDistraction = disTotalDb;

        if (traTotDb > totTraction)
            totTraction = traTotDb;

        if (countScore > 0) {
            db.updateAttentionScore(disScoreDb + disScore, traScoreDb + traScore, totDistraction, totTraction, fbId, dayOfTheYear, yr, 1);
        } else {
            db.insertAttentionScore(disScoreDb + disScore, traScoreDb + traScore, totDistraction, totTraction, fbId, dayOfTheYear, yr, 1);

        }

    }


    public void InsertEatHealthyScore(DbHelper db, String fbId, int dayOfTheYear, int yr, int eatFoodScore, int avoidFoodScore, int eatFoodTotal, int avoidFoodTotal) {
        Cursor curEatHealthy = db.getEatHealthyScore(fbId, dayOfTheYear, yr);
        int eatfoodScoreDb = 0, avoidScoreDb = 0, eatFoodTotalDb = 0, avoidFoodTotalDb = 0;

        int countScore = curEatHealthy.getCount();
        if (curEatHealthy.getCount() > 0) {

            while (curEatHealthy.moveToNext()) {

                eatfoodScoreDb = Integer.parseInt(curEatHealthy.getString(Constants.Game_AS_EatFoodScore));
                avoidScoreDb = Integer.parseInt(curEatHealthy.getString(Constants.Game_AS_AvoidFoodScore));
                eatFoodTotalDb = Integer.parseInt(curEatHealthy.getString(Constants.Game_AS_TotEatFoodScore));
                avoidFoodTotalDb = Integer.parseInt(curEatHealthy.getString(Constants.Game_AS_TotAvoidFoodScore));
            }
        }

        if (eatFoodTotal == 0)
            eatFoodTotal = eatFoodTotalDb;

        if (avoidFoodTotal == 0)
            avoidFoodTotal = avoidFoodTotalDb;

        if (countScore > 0) {
            db.updateEatHealthyScore(eatfoodScoreDb + eatFoodScore, avoidScoreDb + avoidFoodScore, eatFoodTotal, avoidFoodTotal, fbId, dayOfTheYear, yr, 1);
        } else {
            db.insertEatHealthyScore(eatfoodScoreDb + eatFoodScore, avoidScoreDb + avoidFoodScore, eatFoodTotal, avoidFoodTotal, fbId, dayOfTheYear, yr, 1);
        }
    }


    public void SubmitGenericGame(int selectItem, DbHelper db, String usrId, int dayOfTheYear, int yr) {

        int countData = 0;
        int meditationScore = 0, trueLearningScore = 0, exerciseScore = 0, natureScore = 0, yourStoryScore = 0, ourBeliefScore = 0;

        Cursor csrGenericScore = db.getGenericScore(usrId, dayOfTheYear, yr);
        countData = csrGenericScore.getCount();
        if (countData > 0) {

            while (csrGenericScore.moveToNext()) {

                meditationScore = Integer.parseInt(csrGenericScore.getString(2));
                trueLearningScore = Integer.parseInt(csrGenericScore.getString(3));
                exerciseScore = Integer.parseInt(csrGenericScore.getString(4));
                natureScore = Integer.parseInt(csrGenericScore.getString(5));
                yourStoryScore = Integer.parseInt(csrGenericScore.getString(6));
                ourBeliefScore = Integer.parseInt(csrGenericScore.getString(7));

            }
        }
        if (selectItem == Constants.GS_meditationScore)
            meditationScore = Constants.Game_CommonScore;
        else if (selectItem == Constants.GS_trueLearningScore)
            trueLearningScore = Constants.Game_CommonScore;
        else if (selectItem == Constants.GS_exerciseScore)
            exerciseScore = Constants.Game_CommonScore;
        else if (selectItem == Constants.GS_natureScore)
            natureScore = Constants.Game_CommonScore;
        else if (selectItem == Constants.GS_yourStoryScore)
            yourStoryScore = Constants.Game_CommonScore;
        else if (selectItem == Constants.GS_ourBeliefScore)
            ourBeliefScore = Constants.Game_CommonScore;

        if (countData > 0) {
            db.updateGenericScore(meditationScore, trueLearningScore, exerciseScore, natureScore, yourStoryScore, ourBeliefScore, usrId, dayOfTheYear, yr, 1);
        } else {
            db.insertGenericScore(meditationScore, trueLearningScore, exerciseScore, natureScore, yourStoryScore, ourBeliefScore, usrId, dayOfTheYear, yr, 1);

        }
    }


    public int SubmitHappinessGame(int selectItem, DbHelper db, String usrId, int dayOfTheYear, int yr) {

        int countData = 0;
        int returnData = 0;
        int gratitudeScore = 0, forgiveInsideScore = 0, forgiveOusideScore = 0, angerControlScore = 0, angerExplodeScore = 0,
                hateControlScore = 0, hateExplodeScore = 0, SadControlScore = 0, sadExplodeScore = 0, envyControlScore = 0, envyExplodeScore = 0, abundanceScore = 0;

        Cursor csrGenericScore = db.getHappinessScore(usrId, dayOfTheYear, yr);
        countData = csrGenericScore.getCount();
        if (countData > 0) {

            while (csrGenericScore.moveToNext()) {

                gratitudeScore = Integer.parseInt(csrGenericScore.getString(2));
                forgiveInsideScore = Integer.parseInt(csrGenericScore.getString(3));
                forgiveOusideScore = Integer.parseInt(csrGenericScore.getString(4));
                angerControlScore = Integer.parseInt(csrGenericScore.getString(5));
                angerExplodeScore = Integer.parseInt(csrGenericScore.getString(6));
                hateControlScore = Integer.parseInt(csrGenericScore.getString(7));
                hateExplodeScore = Integer.parseInt(csrGenericScore.getString(8));
                SadControlScore = Integer.parseInt(csrGenericScore.getString(9));
                sadExplodeScore = Integer.parseInt(csrGenericScore.getString(10));
                envyControlScore = Integer.parseInt(csrGenericScore.getString(11));
                envyExplodeScore = Integer.parseInt(csrGenericScore.getString(12));
                abundanceScore = Integer.parseInt(csrGenericScore.getString(13));
            }
        }
        if (selectItem == Constants.HP_GratitudeScore) {
            gratitudeScore = Constants.Game_CommonScore;
        } else if (selectItem == Constants.HP_ForgiveInsideScore) {
            forgiveInsideScore = Constants.Game_CommonScore;
        } else if (selectItem == Constants.HP_ForgiveOutsideScore) {
            forgiveOusideScore = Constants.Game_CommonScore;
        } else if (selectItem == Constants.HP_AngerControl) {
            angerControlScore = angerControlScore + Constants.Game_CommonScore;
            returnData = angerControlScore;
        } else if (selectItem == Constants.HP_AngerExplode) {
            angerExplodeScore = angerExplodeScore + Constants.Game_CommonScore;
            returnData = angerExplodeScore;
        } else if (selectItem == Constants.HP_HateControl) {
            hateControlScore = hateControlScore + Constants.Game_CommonScore;
            returnData = hateControlScore;

        } else if (selectItem == Constants.HP_HateExplode) {
            hateExplodeScore = hateExplodeScore + Constants.Game_CommonScore;
            returnData = hateExplodeScore;

        } else if (selectItem == Constants.HP_SadControl) {
            SadControlScore = SadControlScore + Constants.Game_CommonScore;
            returnData = SadControlScore;

        } else if (selectItem == Constants.HP_SadExplode) {
            sadExplodeScore = sadExplodeScore + Constants.Game_CommonScore;
            returnData = sadExplodeScore;

        } else if (selectItem == Constants.HP_EnvyControl) {
            envyControlScore = envyControlScore + Constants.Game_CommonScore;
            returnData = envyControlScore;

        } else if (selectItem == Constants.HP_EnvyExplode) {
            envyExplodeScore = envyExplodeScore + Constants.Game_CommonScore;
            returnData = envyExplodeScore;

        } else if (selectItem == Constants.HP_AbundanceScore) {
            abundanceScore = Constants.Game_CommonScore;
            returnData = abundanceScore;

        }


        if (countData > 0) {
            db.updateHappinessScore(gratitudeScore, forgiveInsideScore, forgiveOusideScore,
                    angerControlScore, angerExplodeScore,
                    hateControlScore, hateExplodeScore,
                    SadControlScore, sadExplodeScore,
                    envyControlScore, envyExplodeScore,
                    abundanceScore,
                    usrId, dayOfTheYear, yr, Constants.Status_One);
        } else {
            db.insertHappinessScore(gratitudeScore, forgiveInsideScore, forgiveOusideScore,
                    angerControlScore, angerExplodeScore,
                    hateControlScore, hateExplodeScore,
                    SadControlScore, sadExplodeScore,
                    envyControlScore, envyExplodeScore,
                    abundanceScore,
                    usrId, dayOfTheYear, yr, Constants.Status_One);
        }
        return returnData;

    }


    protected String fetchUserId(FirebaseAuth mAuth) {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        return usrId;
    }


    public void SubmitHabitScore(int habitScore, int habitTotal, String strHabitName, DbHelper db, String usrId, int dayOfTheYear, int yr) {

        int countData = 0;
        int habitScoreDb = 0, habitTotalDb = 0;

        Cursor csrHabitName = db.getHabitDayTrack(usrId, dayOfTheYear, yr);

        ArrayList<String> strHabitPattern = new ArrayList<>();
        int countHabit = csrHabitName.getCount();
        if (countHabit > 0) {
            while (csrHabitName.moveToNext()) {

                strHabitPattern.add(csrHabitName.getString(2));
            }
        }

        if (!strHabitPattern.contains(strHabitName)) {

            Cursor csrHabitScore = db.getHabitScore(usrId, dayOfTheYear, yr);
            countData = csrHabitScore.getCount();
            if (countData > 0) {
                while (csrHabitScore.moveToNext()) {

                    habitScoreDb = Integer.parseInt(csrHabitScore.getString(2));
                    habitTotalDb = Integer.parseInt(csrHabitScore.getString(3));
                }
                habitScoreDb = habitScoreDb + habitScore;
                habitTotalDb = habitTotal;


                db.updateHabitScore(habitScoreDb, habitTotalDb, usrId, dayOfTheYear, yr, Constants.Status_One);
            } else {
                db.insertHabitScore(habitScoreDb, habitTotalDb, usrId, dayOfTheYear, yr, Constants.Status_One);

            }
        }
    }

    protected int fetchDate(int i) {
        Calendar c = Calendar.getInstance();

        int returnvalue = 0;
        if (i == 0)
            returnvalue = c.get(Calendar.DAY_OF_YEAR);
        else if (i == 1) {
            returnvalue = c.get(Calendar.YEAR);
        }
        return returnvalue;

    }

    public UserGame loadUserGame(String fbId, int dayOfTheYear, int yr) {
        UserGame userGame = new UserGame();
        userGame.setFb_Id(fbId);
        userGame.setDayOfTheYear(dayOfTheYear);
        userGame.setYear(yr);
        userGame.setUserTotatScore(Constants.Game_userTotalScore);
        userGame.setStatus(Constants.Status_One);
        return userGame;
    }


}



