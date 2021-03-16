package app.actionnation.actionapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import app.actionnation.actionapp.Database_Content.UserGame;

public class DbHelper extends SQLiteOpenHelper {


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Personal_Habits " +
                        "(id integer primary key, fb_Id text,habit text,habitDays integer)"
        );

        db.execSQL(CREATE_TABLE_Excercise);
        db.execSQL(CREATE_TABLE_ROUTINE);
        db.execSQL(CREATE_TABLE_RESPECT_WORK);
        db.execSQL(CREATE_TABLE_ATTENTION);
        db.execSQL(CREATE_TABLE_TRUE_LEARNING);
        db.execSQL(CREATE_TABLE_GRATITUDE_TABLE);
        db.execSQL(CREATE_TABLE_GRATITUDEDAY_TABLE);
        db.execSQL(CREATE_TABLE_BELIEF_TABLE);
        db.execSQL(CREATE_TABLE_TRACTION_TABLE);
        db.execSQL(CREATE_TABLE_TRACTION_DAY_TABLE);
        db.execSQL(CREATE_TABLE_EAT_HEALTHY_TABLE);
        db.execSQL(CREATE_TABLE_INTEGRITYSCORE);
        db.execSQL(CREATE_TABLE_PERSONAL_AttentionScore);
        db.execSQL(CREATE_TABLE_PERSONAL_GenericScore);
        db.execSQL(CREATE_TABLE_PERSONAL_HappinessScore);
        db.execSQL(CREATE_TABLE_PERSONAL_HabitScore);
        db.execSQL(CREATE_TABLE_PERSONAL_HabitDayTrack);
        db.execSQL(CREATE_TABLE_PERSONAL_AbundanceList);
        db.execSQL(CREATE_TABLE_PERSONAL_EatHealthyScore);
        db.execSQL(CREATE_TABLE_PERSONAL_GAMESCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Personal_Habits");
        db.execSQL("DROP TABLE IF EXISTS Personal_Excercise");
        db.execSQL("DROP TABLE IF EXISTS Personal_Routine");
        db.execSQL("DROP TABLE IF EXISTS Personal_Respect_Work");
        db.execSQL("DROP TABLE IF EXISTS Personal_Attention");
        db.execSQL("DROP TABLE IF EXISTS Personal_True_Learning");
        db.execSQL("DROP TABLE IF EXISTS Personal_Gratitude_List");
        db.execSQL("DROP TABLE IF EXISTS Personal_GratitudeDay_List");
        db.execSQL("DROP TABLE IF EXISTS Personal_Belief_List");
        db.execSQL("DROP TABLE IF EXISTS Personal_Traction_List");
        db.execSQL("DROP TABLE IF EXISTS Personal_TractionDay_List");
        db.execSQL("DROP TABLE IF EXISTS Personal_EatHealthy_List");
        db.execSQL("DROP TABLE IF EXISTS Personal_Integrity_Score");
        db.execSQL("DROP TABLE IF EXISTS Personal_Attention_Score");
        db.execSQL("DROP TABLE IF EXISTS Personal_Generic_Score");
        db.execSQL("DROP TABLE IF EXISTS Personal_Happiness_Score");
        db.execSQL("DROP TABLE IF EXISTS Personal_HabitDay_Track");
        db.execSQL("DROP TABLE IF EXISTS Personal_HabitScore");
        db.execSQL("DROP TABLE IF EXISTS Personal_Abundance_List");
        db.execSQL("DROP TABLE IF EXISTS Personal_EatHealthy_Score");
        db.execSQL("DROP TABLE IF EXISTS Personal_Game_Score");



        onCreate(db);
    }

    private static final int VERSION = 26;

    private HashMap hp;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //Personal Habits Table
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "Personal_Habits";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_Fb_Id = "fb_Id";
    public static final String CONTACTS_Habit = "habit";
    public static final String CONTACTS_Habit_Days = "habitDays";
    public static final String CONTACTS_Habit_Description = "habitdescription";

    //Personal Excercise Table
    public static final String Excercise_TABLE_NAME = "Personal_Excercise";
    public static final String Excercise_COLUMN_ID = "id";
    public static final String Excercise_Fb_Id = "fb_Id";
    public static final String Excercise_Pattern = "pattern";
    public static final String Excercise_pattern_name = "patternName";

    //Personal Routine Table
    public static final String Exe_TABLE_NAME = "Personal_Routine";
    public static final String Exe_COLUMN_ID = "id";
    public static final String Exe_Fb_Id = "fb_Id";
    public static final String Exe_Pattern = "pattern";
    public static final String Exe_routine_name = "patternName";

    //Personal Respect Work
    public static final String Exe_TABLE_NAME_Work = "Personal_Respect_Work";
    public static final String Exe_Work_COLUMN_ID = "id";
    public static final String Exe_Work_Fb_Id = "fb_Id";
    public static final String Exe_Work_Finised = "rw_finished";
    public static final String Exe_Work_incomplete = "rw_incomplete";
    public static final String Exe_Work_abandoned = "rw_abandoned";
    public static final String Exe_Work_today_date = "rw_dayOfTheYear";


    //Personal Attention
    public static final String Exe_TABLE_NAME_ATTENTION = "Personal_Attention";
    public static final String Exe_Attention_COLUMN_ID = "id";
    public static final String Exe_Attention_Fb_Id = "fb_Id";
    public static final String Exe_Distraction_Name = "a_distractionName";
    public static final String Exe_Distraction_Status = "a_distractionStatus";
    public static final String Exe_Attention_DayOfYear = "a_dayOfTheYear";
    public static final String Exe_Attention_Year = "year";


    //Personal True Learning
    public static final String Exe_TABLE_NAME_TRUELEARNING = "Personal_True_Learning";
    public static final String Exe_Learning_COLUMN_ID = "id";
    public static final String Exe_Learning_Name = "l_learningName";
    public static final String Exe_Learning_Status = "l_learningStatus";
    public static final String Exe_Learning_DayOfYear = "l_dayOfTheYear";
    public static final String Exe_Learning_Fb_Id = "fb_Id";
    public static final String Exe_Learning_Year = "year";


    //Personal Gratitude List
    public static final String Exe_TABLE_NAME_GRATITIDE = "Personal_Gratitude_List";
    public static final String Exe_Gratitude_COLUMN_ID = "id";
    public static final String Exe_Gratitude_Name = "G_GratitudeName";
    public static final String Exe_Gratitude_Status = "G_GratitudeStatus";


    //Personal GratitudeDay List
    public static final String Exe_TABLE_NAME_GRATITUDE_DAY = "Personal_GratitudeDay_List";
    public static final String Exe_GratitudeDay_COLUMN_ID = "id";
    public static final String Exe_GratitudeDay_Name = "GD_GratitudeName";
    public static final String Exe_GratitudeDay_Status = "GD_GratitudeStatus";
    public static final String Exe_GratitudeDay_DayOfYear = "GD_dayOfTheYear";

    //Personal Belief List
    public static final String Exe_TABLE_NAME_BELIEF = "Personal_Belief_List";
    public static final String Exe_Belief_COLUMN_ID = "id";
    public static final String Exe_Belief_Fb_Id = "fb_Id";
    public static final String Exe_Belief_Name = "BS_Name";
    public static final String Exe_Belief_Status = "BS_Status";
    public static final String Exe_Belief_Desc = "BS_Desc";

    //Personal Traction List
    public static final String Exe_TABLE_NAME_Traction = "Personal_Traction_List";
    public static final String Exe_Traction_COLUMN_ID = "id";
    public static final String Exe_Traction_Fb_Id = "fb_Id";
    public static final String Exe_Traction_Name = "Tr_Name";
    public static final String Exe_Traction_Status = "Tr_Status";

    //Personal Traction List Day
    public static final String Exe_TABLE_NAME_TractionDay = "Personal_TractionDay_List";
    public static final String Exe_TractionDay_COLUMN_ID = "id";
    public static final String Exe_TractionDay_Fb_Id = "fb_Id";
    public static final String Exe_TractionDay_Name = "Trd_Name";
    public static final String Exe_TractionDay_Status = "Trd_Status";
    public static final String Exe_TractionDay_DayOfYear = "Trd_dayOfTheYear";


    //Personal Traction List Day
    public static final String Exe_TABLE_NAME_EatHealthy = "Personal_EatHealthy_List";
    public static final String Exe_EatHealthy_COLUMN_ID = "id";
    public static final String Exe_EatHealthy_Fb_Id = "fb_Id";
    public static final String Exe_EatHealthy_Name = "Eh_Name";
    public static final String Exe_EatHealthy_Status = "status";
    public static final String Exe_EatHealthy_DayOfYear = "dayOfTheYear";
    public static final String Exe_EatHealthy_Year = "year";


    // Personal Integrity Score
    public static final String Exe_TABLE_IntegrityScore = "Personal_Integrity_Score";
    public static final String Exe_IG_COLUMN_ID = "id";
    public static final String Exe_IG_Fb_Id = "fb_Id";
    public static final String Exe_IG_SelfWin = "IG_SelfWin";
    public static final String Exe_IG_PlaceWin = "IG_PlaceWin";
    public static final String Exe_IG_WorkAgreement = "IG_WorkAgreement";
    public static final String Exe_IG_WorkAgreement_Items = "IG_WorkAgreement_Items";
    public static final String Exe_IG_RespectWork = "IG_RespectWork";
    public static final String Exe_IG_DayOfTheYear = "dayOfTheYear";
    public static final String Exe_IG_Year = "year";
    public static final String Exe_IG_Status = "status";



    // Personal Attention Score
    public static final String Exe_TABLE_AttentionScore = "Personal_Attention_Score";
    public static final String Exe_AS_COLUMN_ID = "id";
    public static final String Exe_AS_Fb_Id = "fb_Id";
    public static final String Exe_AS_DistractionScore = "AS_DistractionScore";
    public static final String Exe_AS_TractionScore = "AS_TractionScore";
    public static final String Exe_AS_TotDistraction = "AS_TotDistraction";
    public static final String Exe_AS_TotTraction = "AS_TotTraction";
    public static final String Exe_AS_DayOfTheYear = "dayOfTheYear";
    public static final String Exe_AS_Year = "year";
    public static final String Exe_AS_Status = "status";


    //Personal Gereric Score
    public static final String Exe_TABLE_GenericScore = "Personal_Generic_Score";
    public static final String Exe_GS_COLUMN_ID = "id";
    public static final String Exe_GS_Fb_Id = "fb_Id";
    public static final String Exe_GS_MeditationScore = "GS_MeditationScore";
    public static final String Exe_GS_TrueLearningScore = "GS_TrueLearningScore";
    public static final String Exe_GS_ExerciseScore = "GS_ExerciseScore";
    public static final String Exe_GS_NatureScore = "GS_NatureScore";
    public static final String Exe_GS_YourStoryScore = "GS_YourStoryScore";
    public static final String Exe_GS_OurBeliefScore = "GS_OurBeliefScore";
    public static final String Exe_GS_DayOfTheYear = "dayOfTheYear";
    public static final String Exe_GS_Year = "year";
    public static final String Exe_GS_Status = "status";

    // Personal Happiness Score
    public static final String Exe_TABLE_HappinessScore = "Personal_Happiness_Score";
    public static final String Exe_HP_COLUMN_ID = "id";
    public static final String Exe_HP_Fb_Id = "fb_Id";
    public static final String Exe_HP_GratitudeScore = "HP_GratitudeScore";
    public static final String Exe_HP_ForgiveInsideScore = "HP_ForgiveInsideScore";
    public static final String Exe_HP_ForgiveOutsideScore = "HP_ForgiveOutsideScore";
    public static final String Exe_HP_AngerControl = "HP_AngerControl";
    public static final String Exe_HP_AngerExplode = "HP_AngerExplode";
    public static final String Exe_HP_HateControl = "HP_HateControl";
    public static final String Exe_HP_HateExplode = "HP_HateExplode";
    public static final String Exe_HP_SadControl = "HP_SadControl";
    public static final String Exe_HP_SadExplode = "HP_SadExplode";
    public static final String Exe_HP_EnvyControl = "HP_EnvyControl";
    public static final String Exe_HP_EnvyExplode = "HP_EnvyExplode";
    public static final String Exe_HP_AbundanceScore = "HP_AbundanceScore";
    public static final String Exe_HP_DayOfTheYear = "dayOfTheYear";
    public static final String Exe_HP_Year = "year";
    public static final String Exe_HP_Status = "status";


    // Personal Eat Healthy Score
    public static final String Exe_TABLE_EatHealthy_Score = "Personal_EatHealthy_Score";
    public static final String Exe_EHS_COLUMN_ID = "id";
    public static final String Exe_EHS_Fb_Id = "fb_Id";
    public static final String Exe_EHS_EatFoodScore = "EHS_EatFoodScore";
    public static final String Exe_EHS_AvoidFoodScore = "EHS_AvoidFoodScore";
    public static final String Exe_EHS_EatFoodTotal = "EHS_EatFoodTotal";
    public static final String Exe_EHS_AvoidFoodTotal = "EHS_AvoidFoodTotal";
    public static final String Exe_EHS_DayOfTheYear = "dayOfTheYear";
    public static final String Exe_EHS_Year = "year";
    public static final String Exe_EHS_Status = "status";


    // Personal Habit Day Track
    public static final String Exe_TABLE_HabitDayTrack = "Personal_HabitDay_Track";
    public static final String Exe_HDT_COLUMN_ID = "id";
    public static final String Exe_HDT_Fb_Id = "fb_Id";
    public static final String Exe_HDT_HabitName = "HDT_HabitName";
    public static final String Exe_HDT_DayOfTheYear = "dayOfTheYear";
    public static final String Exe_HDT_Year = "year";
    public static final String Exe_HDT_Status = "status";


    // Personal Habit  Score
    public static final String Exe_TABLE_HabitScore = "Personal_HabitScore";
    public static final String Exe_HS_COLUMN_ID = "id";
    public static final String Exe_HS_Fb_Id = "fb_Id";
    public static final String Exe_HS_HabitScore = "HS_HabitScore";
    public static final String Exe_HS_HabitTotal = "HS_HabitTotal";
    public static final String Exe_HS_DayOfTheYear = "dayOfTheYear";
    public static final String Exe_HS_Year = "year";
    public static final String Exe_HS_Status = "status";


    //Personal Abundance List
    public static final String Exe_TABLE_NAME_Abundance = "Personal_Abundance_List";
    public static final String Exe_Abundance_COLUMN_ID = "id";
    public static final String Exe_Abundance_Fb_Id = "fb_Id";
    public static final String Exe_Abundance_Reframe = "Ab_Reframe";
    public static final String Exe_Abundance_Status = "Ab_Status";


    //Personal Abundance  Score Insert and all
    private static final String CREATE_TABLE_PERSONAL_AbundanceList = "CREATE TABLE "
            + Exe_TABLE_NAME_Abundance + "("
            + Exe_Abundance_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_Abundance_Fb_Id + " TEXT,"
            + Exe_Abundance_Reframe + " TEXT,"
            + Exe_Abundance_Status + " INTEGER"
            + ")";


    public boolean insertAbundance(String reframSentence,
                                   String fbId, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Abundance_Fb_Id, fbId);
        contentValues.put(Exe_Abundance_Reframe, reframSentence);
        contentValues.put(Exe_Abundance_Status, status);
        db.insert(Exe_TABLE_NAME_Abundance, null, contentValues);
        return true;
    }


    public Cursor getAbundance(String fbId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_Abundance,
                null,
                "fb_Id = ?",
                new String[]{fbId},
                null,
                null,
                null);

        return res1;
    }


    //Personal Happiness  Score Insert and all
    private static final String CREATE_TABLE_PERSONAL_HabitScore = "CREATE TABLE "
            + Exe_TABLE_HabitScore + "("
            + Exe_HS_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_HS_Fb_Id + " TEXT,"
            + Exe_HS_HabitScore + " INTEGER,"
            + Exe_HS_HabitTotal + " INTEGER,"
            + Exe_HS_DayOfTheYear + " INTEGER,"
            + Exe_HS_Year + " INTEGER,"
            + Exe_HS_Status + " INTEGER"
            + ")";


    public boolean insertHabitScore(int habitScore, int habitTotal,
                                    String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_HS_Fb_Id, fbId);
        contentValues.put(Exe_HS_HabitScore, habitScore);
        contentValues.put(Exe_HS_HabitTotal, habitTotal);
        contentValues.put(Exe_HS_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_HS_Year, year);
        contentValues.put(Exe_HS_Status, status);

        db.insert(Exe_TABLE_HabitScore, null, contentValues);
        return true;
    }


    public Cursor getHabitScore(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_HabitScore,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateHabitScore(int habitScore, int habitTotal,
                                    String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_HS_Fb_Id, fbId);
        contentValues.put(Exe_HS_HabitScore, habitScore);
        contentValues.put(Exe_HS_HabitTotal, habitTotal);
        contentValues.put(Exe_HS_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_HS_Year, year);
        contentValues.put(Exe_HS_Status, status);

        db.update(Exe_TABLE_HabitScore, contentValues, "dayOfTheYear = ? AND fb_Id = ? and year = ?", new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)});
        return true;
    }


    //Personal Happiness  Score Insert and all
    private static final String CREATE_TABLE_PERSONAL_HabitDayTrack = "CREATE TABLE "
            + Exe_TABLE_HabitDayTrack + "("
            + Exe_HDT_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_HDT_Fb_Id + " TEXT,"
            + Exe_HDT_HabitName + " TEXT,"
            + Exe_HDT_DayOfTheYear + " INTEGER,"
            + Exe_HDT_Year + " INTEGER,"
            + Exe_HDT_Status + " INTEGER"
            + ")";


    public boolean insertHabitDayTrack(String habitName,
                                       String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_HDT_Fb_Id, fbId);
        contentValues.put(Exe_HDT_HabitName, habitName);
        contentValues.put(Exe_HDT_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_HDT_Year, year);
        contentValues.put(Exe_HDT_Status, status);

        db.insert(Exe_TABLE_HabitDayTrack, null, contentValues);
        return true;
    }


    public Cursor getHabitDayTrack(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_HabitDayTrack,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    //Personal Happiness  Score Insert and all
    private static final String CREATE_TABLE_PERSONAL_EatHealthyScore = "CREATE TABLE "
            + Exe_TABLE_EatHealthy_Score + "("
            + Exe_EHS_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_EHS_Fb_Id + " TEXT,"
            + Exe_EHS_EatFoodScore + " INTEGER,"
            + Exe_EHS_AvoidFoodScore + " INTEGER,"
            + Exe_EHS_EatFoodTotal + " INTEGER,"
            + Exe_EHS_AvoidFoodTotal + " INTEGER,"
            + Exe_EHS_DayOfTheYear + " INTEGER,"
            + Exe_EHS_Year + " INTEGER,"
            + Exe_EHS_Status + " INTEGER"
            + ")";


    public boolean insertEatHealthyScore(int eatFoodScore, int avoidFoodScore, int eatFoodTotal, int avoidFoodTotal,
                                         String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_EHS_Fb_Id, fbId);
        contentValues.put(Exe_EHS_EatFoodScore, eatFoodScore);
        contentValues.put(Exe_EHS_AvoidFoodScore, avoidFoodScore);
        contentValues.put(Exe_EHS_EatFoodTotal, eatFoodTotal);
        contentValues.put(Exe_EHS_AvoidFoodTotal, avoidFoodTotal);

        contentValues.put(Exe_EHS_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_EHS_Year, year);
        contentValues.put(Exe_EHS_Status, status);

        db.insert(Exe_TABLE_EatHealthy_Score, null, contentValues);
        return true;
    }


    public Cursor getEatHealthyScore(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_EatHealthy_Score,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateEatHealthyScore(int eatFoodScore, int avoidFoodScore, int eatFoodTotal, int avoidFoodTotal,
                                         String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_EHS_Fb_Id, fbId);
        contentValues.put(Exe_EHS_EatFoodScore, eatFoodScore);
        contentValues.put(Exe_EHS_AvoidFoodScore, avoidFoodScore);
        contentValues.put(Exe_EHS_EatFoodTotal, eatFoodTotal);
        contentValues.put(Exe_EHS_AvoidFoodTotal, avoidFoodTotal);
        contentValues.put(Exe_EHS_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_EHS_Year, year);
        contentValues.put(Exe_EHS_Status, status);

        db.update(Exe_TABLE_EatHealthy_Score, contentValues, "dayOfTheYear = ? AND fb_Id = ? and year = ?", new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)});
        return true;
    }


    //Personal Happiness  Score Insert and all
    private static final String CREATE_TABLE_PERSONAL_HappinessScore = "CREATE TABLE "
            + Exe_TABLE_HappinessScore + "("
            + Exe_HP_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_HP_Fb_Id + " TEXT,"
            + Exe_HP_GratitudeScore + " INTEGER,"
            + Exe_HP_ForgiveInsideScore + " INTEGER,"
            + Exe_HP_ForgiveOutsideScore + " INTEGER,"
            + Exe_HP_AngerControl + " INTEGER,"
            + Exe_HP_AngerExplode + " INTEGER,"
            + Exe_HP_HateControl + " INTEGER,"
            + Exe_HP_HateExplode + " INTEGER,"
            + Exe_HP_SadControl + " INTEGER,"
            + Exe_HP_SadExplode + " INTEGER,"
            + Exe_HP_EnvyControl + " INTEGER,"
            + Exe_HP_EnvyExplode + " INTEGER,"
            + Exe_HP_AbundanceScore + " INTEGER,"
            + Exe_HP_DayOfTheYear + " INTEGER,"
            + Exe_HP_Year + " INTEGER,"
            + Exe_HP_Status + " INTEGER"
            + ")";


    public boolean insertHappinessScore(int gratitudeScore, int forgiveInsideScore, int forgiveOutsideScore,
                                        int angerControlScore, int angerExplodeScore,
                                        int hateControlScore, int hateExplodeScore,
                                        int sadControlScore, int sadExplodeScore,
                                        int envyControlScore, int envyExplodeScore,
                                        int abundanceScore,
                                        String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_HP_Fb_Id, fbId);
        contentValues.put(Exe_HP_GratitudeScore, gratitudeScore);
        contentValues.put(Exe_HP_ForgiveInsideScore, forgiveInsideScore);
        contentValues.put(Exe_HP_ForgiveOutsideScore, forgiveOutsideScore);
        contentValues.put(Exe_HP_AngerControl, angerControlScore);
        contentValues.put(Exe_HP_AngerExplode, angerExplodeScore);
        contentValues.put(Exe_HP_HateControl, hateControlScore);
        contentValues.put(Exe_HP_HateExplode, hateExplodeScore);
        contentValues.put(Exe_HP_SadControl, sadControlScore);
        contentValues.put(Exe_HP_SadExplode, sadExplodeScore);
        contentValues.put(Exe_HP_EnvyControl, envyControlScore);
        contentValues.put(Exe_HP_EnvyExplode, envyExplodeScore);
        contentValues.put(Exe_HP_AbundanceScore, abundanceScore);
        contentValues.put(Exe_HP_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_HP_Year, year);
        contentValues.put(Exe_HP_Status, status);

        db.insert(Exe_TABLE_HappinessScore, null, contentValues);
        return true;
    }


    public Cursor getHappinessScore(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_HappinessScore,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateHappinessScore(int gratitudeScore, int forgiveInsideScore, int forgiveOutsideScore,
                                        int angerControlScore, int angerExplodeScore,
                                        int hateControlScore, int hateExplodeScore,
                                        int sadControlScore, int sadExplodeScore,
                                        int envyControlScore, int envyExplodeScore,
                                        int abundanceScore,
                                        String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_HP_Fb_Id, fbId);
        contentValues.put(Exe_HP_GratitudeScore, gratitudeScore);
        contentValues.put(Exe_HP_ForgiveInsideScore, forgiveInsideScore);
        contentValues.put(Exe_HP_ForgiveOutsideScore, forgiveOutsideScore);
        contentValues.put(Exe_HP_AngerControl, angerControlScore);
        contentValues.put(Exe_HP_AngerExplode, angerExplodeScore);
        contentValues.put(Exe_HP_HateControl, hateControlScore);
        contentValues.put(Exe_HP_HateExplode, hateExplodeScore);
        contentValues.put(Exe_HP_SadControl, sadControlScore);
        contentValues.put(Exe_HP_SadExplode, sadExplodeScore);
        contentValues.put(Exe_HP_EnvyControl, envyControlScore);
        contentValues.put(Exe_HP_EnvyExplode, envyExplodeScore);
        contentValues.put(Exe_HP_AbundanceScore, abundanceScore);
        contentValues.put(Exe_HP_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_HP_Year, year);
        contentValues.put(Exe_HP_Status, status);

        db.update(Exe_TABLE_HappinessScore, contentValues, "dayOfTheYear = ? AND fb_Id = ? and year = ?", new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)});
        return true;
    }


    //Personal Geniric Score Score Insert and all
    private static final String CREATE_TABLE_PERSONAL_GenericScore = "CREATE TABLE "
            + Exe_TABLE_GenericScore + "("
            + Exe_GS_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_GS_Fb_Id + " TEXT,"
            + Exe_GS_MeditationScore + " INTEGER,"
            + Exe_GS_TrueLearningScore + " INTEGER,"
            + Exe_GS_ExerciseScore + " INTEGER,"
            + Exe_GS_NatureScore + " INTEGER,"
            + Exe_GS_YourStoryScore + " INTEGER,"
            + Exe_GS_OurBeliefScore + " INTEGER,"
            + Exe_GS_DayOfTheYear + " INTEGER,"
            + Exe_GS_Year + " INTEGER,"
            + Exe_GS_Status + " INTEGER"
            + ")";


    public boolean insertGenericScore(int meditationScore, int trueLearningScore,
                                      int exerciseScore, int natureScore, int yourstoryScore, int ourbeliefScore,
                                      String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_GS_Fb_Id, fbId);
        contentValues.put(Exe_GS_MeditationScore, meditationScore);
        contentValues.put(Exe_GS_TrueLearningScore, trueLearningScore);
        contentValues.put(Exe_GS_ExerciseScore, exerciseScore);
        contentValues.put(Exe_GS_NatureScore, natureScore);
        contentValues.put(Exe_GS_YourStoryScore, yourstoryScore);
        contentValues.put(Exe_GS_OurBeliefScore, ourbeliefScore);
        contentValues.put(Exe_GS_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_GS_Year, year);
        contentValues.put(Exe_GS_Status, status);

        db.insert(Exe_TABLE_GenericScore, null, contentValues);
        return true;
    }


    public Cursor getGenericScore(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_GenericScore,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateGenericScore(int meditationScore, int trueLearningScore,
                                      int exerciseScore, int natureScore, int yourstoryScore, int ourbeliefScore,
                                      String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_GS_Fb_Id, fbId);
        contentValues.put(Exe_GS_MeditationScore, meditationScore);
        contentValues.put(Exe_GS_TrueLearningScore, trueLearningScore);
        contentValues.put(Exe_GS_ExerciseScore, exerciseScore);
        contentValues.put(Exe_GS_NatureScore, natureScore);
        contentValues.put(Exe_GS_YourStoryScore, yourstoryScore);
        contentValues.put(Exe_GS_OurBeliefScore, ourbeliefScore);
        contentValues.put(Exe_GS_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_GS_Year, year);
        contentValues.put(Exe_GS_Status, status);


        db.update(Exe_TABLE_GenericScore, contentValues, "dayOfTheYear = ? AND fb_Id = ? and year = ?", new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)});
        return true;
    }


    //Personal Attention Score Insert and all
    private static final String CREATE_TABLE_PERSONAL_AttentionScore = "CREATE TABLE "
            + Exe_TABLE_AttentionScore + "("
            + Exe_AS_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_AS_Fb_Id + " TEXT,"
            + Exe_AS_DistractionScore + " INTEGER,"
            + Exe_AS_TractionScore + " INTEGER,"
            + Exe_AS_TotDistraction + " INTEGER,"
            + Exe_AS_TotTraction + " INTEGER,"
            + Exe_AS_DayOfTheYear + " INTEGER,"
            + Exe_AS_Year + " INTEGER,"
            + Exe_AS_Status + " INTEGER"
            + ")";


    public boolean insertAttentionScore(int distractionScore, int tractionScore, int totDistraction, int totTraction,
                                        String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_AS_Fb_Id, fbId);
        contentValues.put(Exe_AS_DistractionScore, distractionScore);
        contentValues.put(Exe_AS_TractionScore, tractionScore);
        contentValues.put(Exe_AS_TotDistraction, totDistraction);
        contentValues.put(Exe_AS_TotTraction, totTraction);
        contentValues.put(Exe_AS_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_AS_Year, year);
        contentValues.put(Exe_AS_Status, status);

        db.insert(Exe_TABLE_AttentionScore, null, contentValues);
        return true;
    }


    public Cursor getAttentionScore(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_AttentionScore,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateAttentionScore(int distractionScore, int tractionScore, int totDistraction, int totTraction,
                                        String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_AS_Fb_Id, fbId);
        contentValues.put(Exe_AS_DistractionScore, distractionScore);
        contentValues.put(Exe_AS_TractionScore, tractionScore);
        contentValues.put(Exe_AS_TotDistraction, totDistraction);
        contentValues.put(Exe_AS_TotTraction, totTraction);
        contentValues.put(Exe_AS_DayOfTheYear, dayOfTheYear);
        contentValues.put(Exe_AS_Year, year);
        contentValues.put(Exe_AS_Status, status);


        db.update(Exe_TABLE_AttentionScore, contentValues, "dayOfTheYear = ? AND fb_Id = ? and year = ?", new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)});
        return true;
    }



    //Personal Game Score
    public static final String Exe_TABLE_GameScore = "Personal_Game_Score";
    public static final String Exe_Game_COLUMN_ID = "id";
    public static final String Exe_Game_Fb_Id = "fb_Id";
    public static final String Exe_Game_SelfWinScore = "G_SelfWinScore";
    public static final String Exe_Game_PlaceWinScore = "G_PlaceWinScore";
    public static final String Exe_Game_WordWinScore = "G_WordWinScore";
    public static final String Exe_Game_WorkWinScore = "G_WorkWinScore";
    public static final String Exe_Game_DistractionScore = "G_DistractionScore";
    public static final String Exe_Game_TractionScore = "G_TractionScore";
    public static final String Exe_Game_Meditation = "G_MeditationScore";
    public static final String Exe_Game_TrueLearning = "G_TrueLearningScore";
    public static final String Exe_Game_GratitudeScore = "G_GratitudeScore";
    public static final String Exe_Game_ForgivenessSelfScore = "G_ForgivenessSelfScore";
    public static final String Exe_Game_ForgivenessOutsideScore = "G_ForgivenessOutsideScore";
    public static final String Exe_Game_AbundanceScore = "G_AbundanceScore";
    public static final String Exe_Game_EatHealty = "G_EatHealthyScore";
    public static final String Exe_Game_AvoidUnHealty = "G_AvoidUnHealtyScore";
    public static final String Exe_Game_Exercise = "G_ExerciseScore";
    public static final String Exe_Game_Habits = "G_HabitsScore";
    public static final String Exe_Game_ExpNature = "G_ExperienceNatureScore";
    public static final String Exe_Game_RevealStory = "G_RevealStoryScore";
    public static final String Exe_Game_OurBelief = "G_OurBeliefScore";
    public static final String Exe_Game_TotalScore = "G_TotalScore";
    public static final String Exe_Game_DayOfTheYear = "dayOfTheYear";
    public static final String Exe_Game_Year = "year";
    public static final String Exe_Game_Status = "status";



    //Traction List Insert and all
    private static final String CREATE_TABLE_PERSONAL_GAMESCORE = "CREATE TABLE "
            + Exe_TABLE_GameScore + "("
            + Exe_Game_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_Game_Fb_Id + " TEXT,"
            + Exe_Game_SelfWinScore + " INTEGER,"
            + Exe_Game_PlaceWinScore + " INTEGER,"
            + Exe_Game_WordWinScore + " INTEGER,"
            + Exe_Game_WorkWinScore + " INTEGER,"
            + Exe_Game_DistractionScore + " INTEGER,"
            + Exe_Game_TractionScore + " INTEGER,"
            + Exe_Game_Meditation + " INTEGER,"
            + Exe_Game_TrueLearning + " INTEGER,"
            + Exe_Game_GratitudeScore + " INTEGER,"
            + Exe_Game_ForgivenessSelfScore + " INTEGER,"
            + Exe_Game_ForgivenessOutsideScore + " INTEGER,"
            + Exe_Game_AbundanceScore + " INTEGER,"
            + Exe_Game_EatHealty + " INTEGER,"
            + Exe_Game_AvoidUnHealty + " INTEGER,"
            + Exe_Game_Exercise + " INTEGER,"
            + Exe_Game_Habits + " INTEGER,"
            + Exe_Game_ExpNature + " INTEGER,"
            + Exe_Game_RevealStory + " INTEGER,"
            + Exe_Game_OurBelief + " INTEGER,"
            + Exe_Game_TotalScore + " INTEGER,"
            + Exe_Game_DayOfTheYear + " INTEGER,"
            + Exe_Game_Year + " INTEGER,"
            + Exe_Game_Status + " INTEGER"
            + ")";


    public boolean insertGameScore(UserGame userGame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Game_Fb_Id, userGame.getFb_Id());
        contentValues.put(Exe_Game_SelfWinScore, userGame.getUserSelfWinScore());
        contentValues.put(Exe_Game_PlaceWinScore, userGame.getUserPlaceWinScore());
        contentValues.put(Exe_Game_WordWinScore, userGame.getUserWordWinScore());
        contentValues.put(Exe_Game_WorkWinScore, userGame.getUserWorkWinScore());
        contentValues.put(Exe_Game_DistractionScore, userGame.getUserDistractionScore());
        contentValues.put(Exe_Game_TractionScore, userGame.getUserTractionScore());
        contentValues.put(Exe_Game_Meditation, userGame.getUserMeditationScore());
        contentValues.put(Exe_Game_TrueLearning, userGame.getUserTrueLearningScore());
        contentValues.put(Exe_Game_GratitudeScore, userGame.getUserGratitudeScore());
        contentValues.put(Exe_Game_ForgivenessSelfScore, userGame.getUserForgivenessSelfScore());
        contentValues.put(Exe_Game_ForgivenessOutsideScore, userGame.getUserForgivenessOutsideScore());
        contentValues.put(Exe_Game_AbundanceScore, userGame.getUserAbundanceScore());
        contentValues.put(Exe_Game_EatHealty, userGame.getUserEatHealthyScore());
        contentValues.put(Exe_Game_AvoidUnHealty, userGame.getUserAvoidForHealthScore());
        contentValues.put(Exe_Game_Exercise, userGame.getUserExerciseScore());

        contentValues.put(Exe_Game_Habits, userGame.getUserHabitsScore());
        contentValues.put(Exe_Game_ExpNature, userGame.getUserExperienceNatureScore());
        contentValues.put(Exe_Game_RevealStory, userGame.getUserRevealStoryScore());
        contentValues.put(Exe_Game_OurBelief, userGame.getUserOurBeliefScore());
        contentValues.put(Exe_Game_TotalScore, userGame.getUserTotatScore());



        contentValues.put(Exe_Game_DayOfTheYear, userGame.getDayOfTheYear());
        contentValues.put(Exe_Game_Year, userGame.getYear());
        contentValues.put(Exe_Game_Status, userGame.getStatus());

        db.insert(Exe_TABLE_GameScore, null, contentValues);
        return true;
    }


    public Cursor getGameScore(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_GameScore,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateGameScore(UserGame userGame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Exe_Game_Fb_Id, userGame.getFb_Id());
        contentValues.put(Exe_Game_SelfWinScore, userGame.getUserSelfWinScore());
        contentValues.put(Exe_Game_PlaceWinScore, userGame.getUserPlaceWinScore());
        contentValues.put(Exe_Game_WordWinScore, userGame.getUserWordWinScore());
        contentValues.put(Exe_Game_WorkWinScore, userGame.getUserWorkWinScore());
        contentValues.put(Exe_Game_DistractionScore, userGame.getUserDistractionScore());
        contentValues.put(Exe_Game_TractionScore, userGame.getUserTractionScore());
        contentValues.put(Exe_Game_Meditation, userGame.getUserMeditationScore());
        contentValues.put(Exe_Game_TrueLearning, userGame.getUserTrueLearningScore());
        contentValues.put(Exe_Game_GratitudeScore, userGame.getUserGratitudeScore());
        contentValues.put(Exe_Game_ForgivenessSelfScore, userGame.getUserForgivenessSelfScore());
        contentValues.put(Exe_Game_ForgivenessOutsideScore, userGame.getUserForgivenessOutsideScore());
        contentValues.put(Exe_Game_AbundanceScore, userGame.getUserAbundanceScore());
        contentValues.put(Exe_Game_EatHealty, userGame.getUserEatHealthyScore());
        contentValues.put(Exe_Game_AvoidUnHealty, userGame.getUserAvoidForHealthScore());
        contentValues.put(Exe_Game_Exercise, userGame.getUserExerciseScore());

        contentValues.put(Exe_Game_Habits, userGame.getUserHabitsScore());
        contentValues.put(Exe_Game_ExpNature, userGame.getUserExperienceNatureScore());
        contentValues.put(Exe_Game_RevealStory, userGame.getUserRevealStoryScore());
        contentValues.put(Exe_Game_OurBelief, userGame.getUserOurBeliefScore());
        contentValues.put(Exe_Game_TotalScore, userGame.getUserTotatScore());



        contentValues.put(Exe_Game_DayOfTheYear, userGame.getDayOfTheYear());
        contentValues.put(Exe_Game_Year, userGame.getYear());
        contentValues.put(Exe_Game_Status, userGame.getStatus());

        db.update(Exe_TABLE_GameScore, contentValues, "dayOfTheYear = ? AND fb_Id = ? and year = ?", new String[]{String.valueOf(userGame.getDayOfTheYear()), userGame.getFb_Id(), String.valueOf(userGame.getYear())});
        return true;
    }


    //Traction List Insert and all
    private static final String CREATE_TABLE_INTEGRITYSCORE = "CREATE TABLE "
            + Exe_TABLE_IntegrityScore + "("
            + Exe_IG_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_IG_Fb_Id + " TEXT,"
            + Exe_IG_SelfWin + " INTEGER,"
            + Exe_IG_PlaceWin + " INTEGER,"
            + Exe_IG_WorkAgreement + " INTEGER,"
            + Exe_IG_RespectWork + " DECIMAL(3,2),"
            + Exe_IG_DayOfTheYear + " INTEGER,"
            + Exe_IG_Year + " INTEGER,"
            + Exe_IG_Status + " INTEGER,"
            + Exe_IG_WorkAgreement_Items + " INTEGER"
            + ")";


    public boolean insertIntegrityScore(int selfWinNumber, int placewinNumber, int workAgreementNumber, int workAgreementItems, double respectworkNumber, String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_IG_Fb_Id, fbId);
        contentValues.put(Exe_IG_SelfWin, selfWinNumber);
        contentValues.put(Exe_IG_PlaceWin, placewinNumber);
        contentValues.put(Exe_IG_WorkAgreement, workAgreementNumber);
        contentValues.put(Exe_IG_WorkAgreement_Items, workAgreementItems);
        contentValues.put(Exe_IG_RespectWork, respectworkNumber);
        contentValues.put(Exe_EatHealthy_DayOfYear, dayOfTheYear);
        contentValues.put(Exe_EatHealthy_Year, year);
        contentValues.put(Exe_IG_Status, status);

        db.insert(Exe_TABLE_IntegrityScore, null, contentValues);
        return true;
    }

    public Cursor getIntegrityScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_IntegrityScore,
                null,
                null,
                null,
                null,
                null,
                null);

        return res1;
    }

    public Cursor getIntegrityScore(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_IntegrityScore,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateIntegrityScore(int selfWinNumber, int placewinNumber, int workAgreementNumber, int workAgreementItems, double respectworkNumber, String fbId, int dayOfTheYear, int year, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Exe_IG_Fb_Id, fbId);
        contentValues.put(Exe_IG_SelfWin, selfWinNumber);
        contentValues.put(Exe_IG_PlaceWin, placewinNumber);
        contentValues.put(Exe_IG_WorkAgreement, workAgreementNumber);
        contentValues.put(Exe_IG_WorkAgreement_Items, workAgreementItems);

        contentValues.put(Exe_IG_RespectWork, respectworkNumber);
        contentValues.put(Exe_EatHealthy_DayOfYear, dayOfTheYear);
        contentValues.put(Exe_EatHealthy_Year, year);
        contentValues.put(Exe_IG_Status, status);

        db.update(Exe_TABLE_IntegrityScore, contentValues, "dayOfTheYear = ? and fb_Id = ? and year = ?", new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)});
        return true;
    }


    //Traction List Insert and all
    private static final String CREATE_TABLE_EAT_HEALTHY_TABLE = "CREATE TABLE "
            + Exe_TABLE_NAME_EatHealthy + "("
            + Exe_EatHealthy_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_EatHealthy_Fb_Id + " TEXT,"
            + Exe_EatHealthy_Name + " TEXT,"
            + Exe_EatHealthy_Status + " INTEGER,"
            + Exe_EatHealthy_DayOfYear + " INTEGER,"
            + Exe_EatHealthy_Year + " INTEGER"
            + ")";


    public boolean insertEatHealthyList(String eatHealthyName, int eatHealthyStatus, String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_EatHealthy_Name, eatHealthyName);
        contentValues.put(Exe_EatHealthy_Fb_Id, fbId);
        contentValues.put(Exe_EatHealthy_Status, eatHealthyStatus);
        contentValues.put(Exe_EatHealthy_DayOfYear, dayOfTheYear);
        contentValues.put(Exe_EatHealthy_Year, year);

        db.insert(Exe_TABLE_NAME_EatHealthy, null, contentValues);
        return true;
    }

    public Cursor getEatHealthyList() {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_EatHealthy,
                null,
                null,
                null,
                null,
                null,
                null);

        return res1;
    }

    public Cursor getEatHealthyList(String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_EatHealthy,
                null,
                "dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId, String.valueOf(year)},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateEatHealthyList(String eatHealthyName, int status, String fbId, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_EatHealthy_Name, eatHealthyName);
        contentValues.put(Exe_EatHealthy_Fb_Id, fbId);
        contentValues.put(Exe_EatHealthy_Status, status);
        contentValues.put(Exe_EatHealthy_DayOfYear, dayOfTheYear);
        contentValues.put(Exe_EatHealthy_Year, year);
        db.update(Exe_TABLE_NAME_EatHealthy, contentValues, "Eh_Name = ? AND dayOfTheYear = ? AND fb_Id = ? and year = ?", new String[]{eatHealthyName, String.valueOf(dayOfTheYear), fbId, String.valueOf(year)});
        return true;
    }


    //Traction List Insert and all
    private static final String CREATE_TABLE_TRACTION_DAY_TABLE = "CREATE TABLE "
            + Exe_TABLE_NAME_TractionDay + "("
            + Exe_TractionDay_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_TractionDay_Fb_Id + " TEXT,"
            + Exe_TractionDay_Name + " TEXT,"
            + Exe_TractionDay_Status + " INTEGER,"
            + Exe_TractionDay_DayOfYear + " INTEGER"
            + ")";


    public boolean insertTractionDayList(String tractionName, int tractionStatus, String fbId, int dayOfTheYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_TractionDay_Name, tractionName);
        contentValues.put(Exe_TractionDay_Fb_Id, fbId);
        contentValues.put(Exe_TractionDay_Status, tractionStatus);
        contentValues.put(Exe_TractionDay_DayOfYear, dayOfTheYear);

        db.insert(Exe_TABLE_NAME_TractionDay, null, contentValues);
        return true;
    }

    public Cursor getTractionDayList() {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_TractionDay,
                null,
                null,
                null,
                null,
                null,
                null);

        return res1;
    }

    public Cursor getTractionDayList(String fbId, int dayOfTheYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_TractionDay,
                null,
                "Trd_dayOfTheYear = ? and fb_Id = ?",
                new String[]{String.valueOf(dayOfTheYear), fbId},
                null,
                null,
                null);

        return res1;
    }


    public boolean updateTractionDayList(String tractionName, int tractionStatus, String fbId, int dayOfTheYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_TractionDay_Name, tractionName);
        contentValues.put(Exe_TractionDay_Fb_Id, fbId);
        contentValues.put(Exe_TractionDay_Status, tractionStatus);
        contentValues.put(Exe_TractionDay_DayOfYear, dayOfTheYear);
        db.update(Exe_TABLE_NAME_TractionDay, contentValues, "Trd_Name = ? AND Trd_dayOfTheYear = ? AND fb_Id = ?", new String[]{tractionName, String.valueOf(dayOfTheYear), fbId});
        return true;
    }


    //Traction List Insert and all
    private static final String CREATE_TABLE_TRACTION_TABLE = "CREATE TABLE "
            + Exe_TABLE_NAME_Traction + "("
            + Exe_Traction_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_Traction_Fb_Id + " TEXT,"
            + Exe_Traction_Name + " TEXT,"
            + Exe_Traction_Status + " INTEGER"
            + ")";


    public boolean insertTractionList(String tractionName, int tractionStatus, String fbId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Traction_Name, tractionName);
        contentValues.put(Exe_Traction_Fb_Id, fbId);
        contentValues.put(Exe_Traction_Status, tractionStatus);

        db.insert(Exe_TABLE_NAME_Traction, null, contentValues);
        return true;
    }

    public Cursor getTractionList(String fbId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_Traction,
                null,
                "fb_Id = ?",
                new String[]{fbId},
                null,
                null,
                null);


        return res1;
    }


    public boolean updateTractionList(String tractionName, int tractionStatus, String fbId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Traction_Name, tractionName);
        contentValues.put(Exe_Traction_Fb_Id, fbId);
        contentValues.put(Exe_Traction_Status, tractionStatus);
        db.update(Exe_TABLE_NAME_Traction, contentValues, "Tr_Name = ?", new String[]{tractionName});
        return true;
    }


    //Belief List Insert and all
    private static final String CREATE_TABLE_BELIEF_TABLE = "CREATE TABLE "
            + Exe_TABLE_NAME_BELIEF + "("
            + Exe_Belief_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_Belief_Fb_Id + " TEXT,"
            + Exe_Belief_Name + " TEXT,"
            + Exe_Belief_Desc + " TEXT,"
            + Exe_Belief_Status + " INTEGER"
            + ")";


    public boolean insertBeliefList(String beliefName, String beliefDesc, int beliefStatus, String fbId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Belief_Name, beliefName);
        contentValues.put(Exe_Belief_Fb_Id, fbId);
        contentValues.put(Exe_Belief_Desc, beliefDesc);
        contentValues.put(Exe_Belief_Status, beliefStatus);

        db.insert(Exe_TABLE_NAME_BELIEF, null, contentValues);
        return true;
    }

    public Cursor getBeliefList(String fbId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_BELIEF,
                null,
                "fb_Id = ?",
                new String[]{fbId},
                null,
                null,
                null);


        return res1;
    }


    public boolean updateBeliefList(String beliefName, String beliefDesc, int beliefStatus, String fbId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Belief_Name, beliefName);
        contentValues.put(Exe_Belief_Fb_Id, fbId);
        contentValues.put(Exe_Belief_Desc, beliefDesc);
        contentValues.put(Exe_Belief_Status, beliefStatus);
        db.update(Exe_TABLE_NAME_GRATITUDE_DAY, contentValues, "BS_Name = ? AND fb_Id = ?", new String[]{beliefName, fbId});
        return true;
    }

    //Gratititude Day Create Update and all
    private static final String CREATE_TABLE_GRATITUDEDAY_TABLE = "CREATE TABLE "
            + Exe_TABLE_NAME_GRATITUDE_DAY + "("
            + Exe_GratitudeDay_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_GratitudeDay_Name + " TEXT,"
            + Exe_GratitudeDay_DayOfYear + " INTEGER,"
            + Exe_GratitudeDay_Status + " INTEGER"
            + ")";

    public boolean insertGratitudeDayList(String gratitudeName, int trueLearningStatus, int dayOfTheYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_GratitudeDay_Name, gratitudeName);

        contentValues.put(Exe_GratitudeDay_Status, trueLearningStatus);
        contentValues.put(Exe_GratitudeDay_DayOfYear, dayOfTheYear);

        db.insert(Exe_TABLE_NAME_GRATITUDE_DAY, null, contentValues);
        return true;
    }

    public Cursor getGratitudeDayList(String dayOfTheYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_GRATITUDE_DAY,
                null,
                "GD_dayOfTheYear = ?",
                new String[]{dayOfTheYear},
                null,
                null,
                null);


        return res1;
    }


    public boolean updateGratitudeDayList(String gratitudeName, int trueLearningStatus, int dayOfTheYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_GratitudeDay_Name, gratitudeName);
        contentValues.put(Exe_GratitudeDay_Status, trueLearningStatus);
        contentValues.put(Exe_GratitudeDay_DayOfYear, dayOfTheYear);
        db.update(Exe_TABLE_NAME_GRATITUDE_DAY, contentValues, "GD_dayOfTheYear = ? AND GD_GratitudeName = ?", new String[]{Integer.toString(dayOfTheYear), gratitudeName});
        return true;
    }


    //Gratitude Table Create, Insert and Update


    private static final String CREATE_TABLE_GRATITUDE_TABLE = "CREATE TABLE "
            + Exe_TABLE_NAME_GRATITIDE + "("
            + Exe_Gratitude_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_Gratitude_Name + " TEXT,"
            + Exe_Gratitude_Status + " INTEGER"
            + ")";


    public boolean insertGratitudeList(String gratitudeName, int trueLearningStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Gratitude_Name, gratitudeName);
        contentValues.put(Exe_Gratitude_Status, trueLearningStatus);
        db.insert(Exe_TABLE_NAME_GRATITIDE, null, contentValues);
        return true;
    }

    public Cursor getGratitudeList() {
        SQLiteDatabase db = this.getReadableDatabase();
        //   Cursor res = db.rawQuery("select * from Personal_Gratitude_List", null);

        Cursor res1 = db.query(Exe_TABLE_NAME_GRATITIDE,
                null,
                null,
                null,
                null,
                null,
                null);


        return res1;
    }


    // Table Create Statements
    // Todo table create statement


    private static final String CREATE_TABLE_ATTENTION = "CREATE TABLE "
            + Exe_TABLE_NAME_ATTENTION + "("
            + Exe_Attention_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_Attention_Fb_Id + " TEXT,"
            + Exe_Distraction_Name + " TEXT,"
            + Exe_Attention_DayOfYear + " INTEGER,"
            + Exe_Distraction_Status + " INTEGER,"
            + Exe_Attention_Year + " INTEGER"
            + ")";


    private static final String CREATE_TABLE_TRUE_LEARNING = "CREATE TABLE "
            + Exe_TABLE_NAME_TRUELEARNING + "("
            + Exe_Learning_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_Learning_Name + " TEXT,"
            + Exe_Learning_Status + " TEXT,"
            + Exe_Learning_DayOfYear + " INTEGER,"
            + Exe_Learning_Fb_Id + " TEXT,"
            + Exe_Learning_Year + " INTEGER"
            + ")";

    public boolean insertTrueLearning(String trueLearningName, int trueLearningStatus, int dayOfTheYear, String fbId, int yr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Learning_Name, trueLearningName);
        contentValues.put(Exe_Learning_DayOfYear, dayOfTheYear);
        contentValues.put(Exe_Learning_Status, trueLearningStatus);
        contentValues.put(Exe_Learning_Fb_Id, fbId);
        contentValues.put(Exe_Learning_Year, yr);

        db.insert(Exe_TABLE_NAME_TRUELEARNING, null, contentValues);
        return true;
    }


    public Cursor getDataTrueLearning(String dayOfTheYear, String yr, String fbId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(Exe_TABLE_NAME_TRUELEARNING,
                null,
                "l_dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{dayOfTheYear, fbId, yr},
                null,
                null,
                null);

        return res;
    }

    public boolean updateDataTrueLearning(String trueLearningName, int trueLearningStatus, int dayOfTheYear, String fbId, int yr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Learning_Name, trueLearningName);
        contentValues.put(Exe_Learning_DayOfYear, dayOfTheYear);
        contentValues.put(Exe_Learning_Status, trueLearningStatus);
        db.update(Exe_TABLE_NAME_TRUELEARNING, contentValues, "l_dayOfTheYear = ? AND l_learningName = ? and fb_Id = ? and year = ?", new String[]{Integer.toString(dayOfTheYear), trueLearningName, fbId, Integer.toString(yr)});
        return true;
    }


    public boolean insertAttention(String fb_id, String distractionName, int distractionStatus, int dayOfTheYear, int yr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Attention_Fb_Id, fb_id);
        contentValues.put(Exe_Distraction_Name, distractionName);
        contentValues.put(Exe_Attention_DayOfYear, dayOfTheYear);
        contentValues.put(Exe_Distraction_Status, distractionStatus);
        contentValues.put(Exe_Attention_Year, yr);

        db.insert(Exe_TABLE_NAME_ATTENTION, null, contentValues);
        return true;
    }

    public Cursor getDataAttention(String dayOfTheYear, String fb_Id, String yr) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(Exe_TABLE_NAME_ATTENTION,
                null,
                "a_dayOfTheYear = ? and fb_Id = ? and year = ?",
                new String[]{dayOfTheYear, fb_Id, yr},
                null,
                null,
                null);

        return res;
    }

    public boolean updateDataAttention(String fb_id, String distractionName, int distractionStatus, int dayOfTheYear, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Attention_Fb_Id, fb_id);
        contentValues.put(Exe_Distraction_Name, distractionName);
        contentValues.put(Exe_Attention_DayOfYear, dayOfTheYear);
        contentValues.put(Exe_Distraction_Status, distractionStatus);
        db.update(Exe_TABLE_NAME_ATTENTION, contentValues, "a_dayOfTheYear = ? and fb_Id = ? and year = ? and a_distractionName = ?", new String[]{Integer.toString(dayOfTheYear), fb_id, Integer.toString(year), distractionName});
        return true;
    }


    //Excercise Create Update, and Delete
    // TOdo Excercise
    private static final String CREATE_TABLE_Excercise = "CREATE TABLE "
            + Excercise_TABLE_NAME + "(" + Excercise_COLUMN_ID + " INTEGER PRIMARY KEY," + Excercise_Fb_Id
            + " TEXT," + Excercise_Pattern + " TEXT," + Excercise_pattern_name
            + " TEXT" + ")";

    public boolean insertExcercise(String fb_id, String pattern, String patternName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Excercise_Fb_Id, fb_id);
        contentValues.put(Excercise_Pattern, pattern);
        contentValues.put(Excercise_pattern_name, patternName);
        db.insert(Excercise_TABLE_NAME, null, contentValues);
        return true;
    }


    //Create Routine
    //Todo Create Routine

    private static final String CREATE_TABLE_ROUTINE = "CREATE TABLE "
            + Exe_TABLE_NAME + "(" + Exe_COLUMN_ID + " INTEGER PRIMARY KEY," + Exe_Fb_Id
            + " TEXT," + Exe_Pattern + " TEXT," + Exe_routine_name
            + " TEXT" + ")";

    public boolean insertExcerciseRoutine(String fb_id, String pattern, String patternName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Fb_Id, fb_id);
        contentValues.put(Exe_Pattern, pattern);
        contentValues.put(Exe_routine_name, patternName);
        db.insert(Exe_TABLE_NAME, null, contentValues);
        return true;
    }


    public Integer deleteExcercise() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Excercise_TABLE_NAME,
                null,
                null);
    }

    public Cursor getDataExcercise() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Personal_Excercise", null);
        return res;
    }


    public Cursor getDataRoutine(String routineName) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor res = db.rawQuery("select * from Personal_Routine where patternName = '" + routineName+"'", null);
        Cursor res = db.query(Exe_TABLE_NAME,
                null,
                "patternName = ?",
                new String[]{routineName},
                null,
                null,
                null);

        return res;
    }

    public Integer deleteRoutine(String routineName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String table = Exe_TABLE_NAME;
        String whereClause = "patternName = ?";
        String[] whereArgs = new String[]{routineName};
        return db.delete(table, whereClause, whereArgs);
    }

    public Cursor getDataRoutine() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Personal_Routine", null);
        return res;
    }


    public boolean insertHabit(String fb_id, String habit, int habitCount, String habitDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fb_Id", fb_id);
        contentValues.put("habit", habit);
        contentValues.put("habitDays", habitCount);
        contentValues.put("habitDescription", habitDescription);
        db.insert("Personal_Habits", null, contentValues);
        return true;
    }


    //RespectWork
    //Todo respect Work

    private static final String CREATE_TABLE_RESPECT_WORK = "CREATE TABLE "
            + Exe_TABLE_NAME_Work + "("
            + Exe_Work_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Exe_Work_Fb_Id + " TEXT,"
            + Exe_Work_Finised + " INTEGER,"
            + Exe_Work_incomplete + " INTEGER,"
            + Exe_Work_abandoned + " INTEGER,"
            + Exe_Work_today_date + " INTEGER"
            + ")";

    public boolean insertRespectWork(String fb_id, int workFinishedCount, int workIncompleteCount, int workAbandonedCount, int dayOfTheYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Work_Fb_Id, fb_id);
        contentValues.put(Exe_Work_Finised, workFinishedCount);
        contentValues.put(Exe_Work_incomplete, workIncompleteCount);
        contentValues.put(Exe_Work_abandoned, workAbandonedCount);
        contentValues.put(Exe_Work_today_date, dayOfTheYear);
        db.insert(Exe_TABLE_NAME_Work, null, contentValues);
        return true;
    }


    public Cursor getDataRespectWork(String dayOfTheYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(Exe_TABLE_NAME_Work,
                null,
                "rw_dayOfTheYear = ?",
                new String[]{dayOfTheYear},
                null,
                null,
                null);

        return res;
    }


    public boolean updateRespectWork(String fb_id, int workFinishedCount, int workIncompleteCount, int workAbandonedCount, int dayOfTheYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Exe_Work_Fb_Id, fb_id);
        contentValues.put(Exe_Work_Finised, workFinishedCount);
        contentValues.put(Exe_Work_incomplete, workIncompleteCount);
        contentValues.put(Exe_Work_abandoned, workAbandonedCount);
        db.update(Exe_TABLE_NAME_Work, contentValues, "rw_dayOfTheYear = ? ", new String[]{Integer.toString(dayOfTheYear)});
        return true;
    }


}