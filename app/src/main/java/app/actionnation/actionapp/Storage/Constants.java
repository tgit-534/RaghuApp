package app.actionnation.actionapp.Storage;

public class Constants {

    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";

    public static final String STORAGE_PATH_NLW = "nlw/";
    public static final String DATABASE_PATH_NLW = "nlw";

    public static final String STORAGE_PATH_DisplayFields = "DisplayFields/";
    public static final String DATABASE_PATH_DisplayFields = "DisplayFields";

    public static final String display_Datatabs_Values = "Values";
    public static final String display_Datatabs_Affirmation = "Affirmations";
    public static final String display_Datatabs_Description = "Description";
    public static final String display_Datatabs_MVP = "MVP";

    public static final String happy_Datatabs_Gratitude = "Gratitude";
    public static final String happy_Datatabs_Forgiveness = "Forgiveness";
    public static final String happy_Datatabs_Abundance = "Abundance";

    public static final String happy_Datatabs_purpose = "Dream";

    //Tabs
    public static final String attention_Datatabs_distraction = "Distraction";
    public static final String attention_Datatabs_traction = "Traction";

    public static final String eatHealthy_Datatabs_Avoid = "Avoid";
    public static final String eatHealty_Datatabs_Eat = "Eat";
    public static final String eatHealty_Datatabs_Exercise = "Exercise";

    public static final String ar_BookToRead = "Book To Be Read";
    public static final String ar_BookAlRead = "Book Already Read";
    public static final String ar_BookReading = "Book Reading Now";

    public static final String ar_BookReading_Number = "1";
    public static final String ar_BookToRead_Number = "2";
    public static final String ar_BookAlRead_Number = "3";

    public static final String fs_PersonalHabits_Habitdays = "habitDays";
    public static final String fs_PersonalHabits_Comments = "Your Habit recorded for today!";

    public static final int power_limit = 6;
    public static final String fs_PersonalHabits_PowerLimit_Comments = "No of days you can further skip habit: ";
    public static final long milliDay = 86400000;
    public static final String NoOfDaysToFullfillPromise = "No of days left: ";


    public static final String common_auth = "auth";
    public static final String common_google = "google";
    public static final String Page_Redirect = "PageRedirect";
    public static final String Page_Redirect_Habit = "Habit";
    public static final String Intent_ArrayCaptain = "arrayCaptain";
    public static final String Intent_ArrayGameScore = "arrayGameScore";
    public static final String Intent_DataInsertion = "dataInsertion";
    public static final String Intent_GameDocumentId = "GameDocumentId";
    public static final String Intent_GameCoinsPerDay = "CoinsPerDay";
    public static final String Intent_ExcellenceBar = "ExcellenceBar";
    public static final String common_emptySpace = " ";




    //Generic Score
    public static final int GS_meditationScore = 2;
    public static final int GS_trueLearningScore = 3;
    public static final int GS_exerciseScore = 4;
    public static final int GS_natureScore = 5;
    public static final int GS_yourStoryScore = 6;
    public static final int GS_ourBeliefScore = 7;

    //Common Score Value
    public static final int Game_CommonScore = 1;
    public static final int Game_CommonScore_Negative = -1;


    //Generic Score
    public static final int HP_GratitudeScore = 2;
    public static final int HP_ForgiveInsideScore = 3;
    public static final int HP_ForgiveOutsideScore = 4;
    public static final int HP_AngerControl = 5;
    public static final int HP_AngerExplode = 6;
    public static final int HP_HateControl = 7;
    public static final int HP_HateExplode = 8;
    public static final int HP_SadControl = 9;
    public static final int HP_SadExplode = 10;
    public static final int HP_EnvyControl = 11;
    public static final int HP_EnvyExplode = 12;
    public static final int HP_AbundanceScore = 13;


    //Statuses
    public static final int Status_One = 1;
    public static final int Status_Zero = 0;
    public static final int Status_Two = 2;

    //Tabs Activity
    public static final int TabAttention = 1;
    public static final int TabEatHealthy = 2;


    public static final int aaq_AvoidFood_Number = 13;
    public static final int aaq_EatHealthy_Number = 8;


    //Generic Score
    public static final int DbSql_Integrity_SelfWin = 2;
    public static final int DbSql_Integrity_PlaceWin = 3;
    public static final int DbSql_Integrity_WordAgreement = 4;
    public static final int DbSql_Integrity_RespectWork = 5;
    public static final int HP_DbSql_Integrity_RespectWorkItems = 9;

    //Game Integrity


    public static final int Game_Abundance = 100;
    public static final int Game_AvoidForHealth = 100;


    public static final int Game_SelfWin = 50;
    public static final int Game_PlaceWin = 50;
    public static final int Game_WordWin = 400;
    public static final int Game_WorkWIn = 200;

    //Game Attention
    public static final int Game_Distraction = 100;
    public static final int Game_Traction = 100;

    //Game Meditation
    public static final int Game_Meditation = 100;

    // Game True Learning
    public static final int Game_TrueLearning = 100;

    //Game Happiness
    public static final int Game_Forgiveness_Self = 50;
    public static final int Game_Forgiveness_Outside = 50;
    public static final int Game_Gratitude = 100;

    //Game Eat and Exercise
    public static final int Game_EatHealthy = 100;
    public static final int Game_Exercise = 100;
    //Game Habits
    public static final int Game_Habits = 500;
    //Game Experience Nature
    public static final int Game_ExperienceNature = 100;

    // Game Reveal Story
    public static final int Game_RevealStory = 100;

    // Game OUrBelief
    public static final int Game_OurBeliefSystem = 200;

    public static final int Game_userTotalScore = 2600;


    // Game OUrBelief
    public static final int Game_AS_DistractionScore = 2;
    public static final int Game_AS_TractionScore = 3;
    public static final int Game_AS_TotDistraction = 4;
    public static final int Game_AS_TotTraction = 5;
    public static final int Game_AS_DayOfTheYear = 6;
    public static final int Game_AS_Year = 7;
    public static final int Game_AS_Status = 8;

    // Game Eat Healthy
    public static final int Game_AS_EatFoodScore = 2;
    public static final int Game_AS_AvoidFoodScore = 3;
    public static final int Game_AS_TotEatFoodScore = 4;
    public static final int Game_AS_TotAvoidFoodScore = 5;

    // Game Eat Healthy
    public static final int Game_AS_WordScore = 4;
    public static final int Game_AS_TotWordScore = 9;
    public static final int Game_AS_RespectWorkScore = 5;


    // Game Habit
    public static final int Game_AS_HabitScore = 2;
    public static final int Game_AS_TotHabit = 3;


    //Game Array
    public static final int Game_CP__UserSelfWinScore = 0;
    public static final int Game_CP__UserPlaceWinScore = 1;
    public static final int Game_CP__UserWordWinScore = 2;
    public static final int Game_CP__UserWorkWinScore = 3;
    public static final int Game_CP__UserDistractionScore = 4;
    public static final int Game_CP__UserTractionScore = 5;
    public static final int Game_CP__UserMeditationScore = 6;
    public static final int Game_CP__UserTrueLearningScore = 7;
    public static final int Game_CP__UserGratitudeScore = 8;
    public static final int Game_CP__UserForgivenessSelfScore = 9;
    public static final int Game_CP__UserForgivenessOutsideScore = 10;
    public static final int Game_CP__UserAbundanceScore = 11;
    public static final int Game_CP__UserEatHealthyScore = 12;
    public static final int Game_CP__UserAvoidForHealthScore = 13;
    public static final int Game_CP__UserExerciseScore = 14;
    public static final int Game_CP__UserHabitsScore = 15;
    public static final int Game_CP__UserExperienceNatureScore = 16;
    public static final int Game_CP__UserRevealStoryScore = 17;
    public static final int Game_CP__UserOurBeliefScore = 18;
    public static final int Game_CP__UserTotatScore = 19;


    public static final String UserProfile_UserNoOfRatings = "userNoOfRatings";
    public static final String UserProfile_UserRating = "userRating";
    public static final int UserProfile_Array_Rating = 0;
    public static final int UserProfile_Array_NoOfRatings = 1;


    public static final int Display_fields_Integrity = 0;
    public static final int Display_fields_Attention = 1;
    public static final int Display_fields_Meditation = 2;
    public static final int Display_fields_TrueLearning = 3;
    public static final int Display_fields_Happy = 4;
    public static final int Display_fields_EatHealthy = 5;
    public static final int Display_fields_Habit = 6;
    public static final int Display_fields_ExPNature = 7;
    public static final int Display_fields_RevealStory = 8;
    public static final int Display_fields_OurBelief = 9;


    public static final String ClassName_WordWin = "app.actionnation.actionapp.ActivityIntegrity";
    public static final String ClassName_RespectYourWorkWin = "app.actionnation.actionapp.RespectWork";
    public static final String ClassName_HabitInside = "app.actionnation.actionapp.ActivityTimerWindow";

    public static final String ClassName_Attention = "app.actionnation.actionapp.ActivityAttention";
    public static final String ClassName_Happiness = "app.actionnation.actionapp.ActivityHappiness";
    public static final String ClassName_Belief = "app.actionnation.actionapp.ActivityOurBelief";

    public static final String ClassName_GameTracking = "app.actionnation.actionapp.ActivityGameTracking";
    public static final String ClassName_GameCreation = "app.actionnation.actionapp.ActivityGameCreation";



    public static final String StakeGame_70 = "70%";
    public static final String StakeGame_80 = "80%";
    public static final String StakeGame_90 = "90%";

    public static final int StakeGame_70_Int = 70;
    public static final int StakeGame_80_Int = 80;
    public static final int StakeGame_90_Int = 90;


    public static final int gameObject_playerDoucmentId = 0;
    public static final int gameObject_playerStartDate = 1;
    public static final int gameObject_playerEndDate = 2;
    public static final int gameObject_gameName = 3;


    //TabsAdapterGameCreation
    public static final String tabsAdapter_GameCreation_FirstTab = "Master Game";
    public static final String tabsAdapter_GameCreation_SecondTab = "Player Game";

    public static final int profileObject_fullName = 0;
    public static final int profileObject_Dream = 1;
    public static final int profileObject_Challange = 2;
    public static final int profileObject_userHandle = 3;



}
