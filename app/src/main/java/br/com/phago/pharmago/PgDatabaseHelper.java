package br.com.phago.pharmago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PgDatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "PgDatabaseHelper";
    /////////////////////////////////////////////////////////////////////////////////////////////
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "pharmago.db";
    // Common column names (used in more than one table)
    private static final String KEY_ID = "_id";
    private static final String KEY_CREATED_AT = "created_at";
    // Table Names - pg_user
    private static final String TABLE_USER = "pg_user";
    /////////////////////////////////////////////////////////////////////////////////////////////
    // Table Names - pg_transaction
    private static final String TABLE_TRANSACTION = "pg_transaction";
    // Table Names - pg_sponsor
    private static final String TABLE_SPONSOR = "pg_sponsor";
    // Table Names - pg_campaign
    private static final String TABLE_CAMPAIGN = "pg_campaign";
    // Table Names - pg_question
    private static final String TABLE_QUESTION = "pg_question";
    // Table Names - pg_option
    private static final String TABLE_OPTION = "pg_option";
    // OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK
    // Table: pg_user - Field Names
    private static final String FIELD_USER_NAME = "name";
    private static final String FIELD_USER_EMAIL = "email";
    private static final String FIELD_USER_CPF = "cpf";
    private static final String FIELD_USER_PASSWORD = "password";
    private static final String FIELD_USER_STATUS = "userAccountStatus";
    private static final String FIELD_USER_COMPANY_CODE = "companyCode";
    private static final String FIELD_USER_COMPANY_NAME = "companyName";
    private static final String FIELD_USER_COMPANY_LATITUDE = "companyLatitude";
    private static final String FIELD_USER_COMPANY_LONGITUDE = "companyLongitude";
    // OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK
    // Table Create Statements  -  pg_user
    private static final String CREATE_TABLE_USER = "CREATE TABLE " +
            TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_USER_NAME + " TEXT," +
            FIELD_USER_EMAIL + " TEXT, " +
            FIELD_USER_CPF + " TEXT, " +
            FIELD_USER_PASSWORD + " TEXT, " +
            FIELD_USER_STATUS + " TEXT, " +
            FIELD_USER_COMPANY_CODE + " TEXT, " +
            FIELD_USER_COMPANY_NAME + " TEXT, " +
            FIELD_USER_COMPANY_LATITUDE + " TEXT, " +
            FIELD_USER_COMPANY_LONGITUDE + " TEXT, " +
            KEY_CREATED_AT + " TEXT" + ");";
    // TODO review
    // Table: pg_transaction - Field Names
    private static final String FIELD_TRANSACTION_ID = "idTransaction";
    private static final String FIELD_TRANSACTION_ID_CAMPAIGN = "idCampaign";
    private static final String FIELD_TRANSACTION_CAMPAIGN_TITLE = "title";
    private static final String FIELD_TRANSACTION_SPONSOR_CODE = "sponsorCode";
    private static final String FIELD_TRANSACTION_EVENT_DATE = "eventDate";
    private static final String FIELD_TRANSACTION_NATURE = "nature";
    private static final String FIELD_TRANSACTION_AMOUNT = "amount";
    // TODO review
    // Table Create Statements  -  pg_transaction
    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " +
            TABLE_TRANSACTION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_TRANSACTION_ID + " INTEGER, " +
            FIELD_TRANSACTION_ID_CAMPAIGN + " INTEGER, " +
            FIELD_TRANSACTION_CAMPAIGN_TITLE + " TEXT, " +
            FIELD_TRANSACTION_SPONSOR_CODE + " TEXT, " +
            FIELD_TRANSACTION_EVENT_DATE + " TEXT, " +
            FIELD_TRANSACTION_NATURE + " TEXT, " +
            FIELD_TRANSACTION_AMOUNT + " INTEGER, " +
            KEY_CREATED_AT + " TEXT" + ");";
    // OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK
    // Table: pg_sponsor - Field Names
    private static final String FIELD_SPONSOR_ID = "sponsorId";
    private static final String FIELD_SPONSOR_CODE = "sponsorCode";
    private static final String FIELD_SPONSOR_NAME = "sponsorName";
    // OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK
    // Table Create Statements  -  pg_sponsor
    private static final String CREATE_TABLE_SPONSOR = "CREATE TABLE " +
            TABLE_SPONSOR + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_SPONSOR_ID + " INTEGER, " +
            FIELD_SPONSOR_CODE + " TEXT, " +
            FIELD_SPONSOR_NAME + " TEXT );";
    // OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK
    // Table: pg_campaign - Field Names
    private static final String FIELD_CAMPAIGN_ID = "idCampaign";
    private static final String FIELD_CAMPAIGN_SPONSOR_ID = "idSponsor";
    private static final String FIELD_CAMPAIGN_TITLE = "title";
    private static final String FIELD_CAMPAIGN_START_DATE = "startDate";
    private static final String FIELD_CAMPAIGN_END_DATE = "endDate";
    private static final String FIELD_CAMPAIGN_NUMBER_OF_QUESTIONS = "numberOfQuestions";
    private static final String FIELD_CAMPAIGN_POINTS_RIGHT_ANSWER = "pointsForRightAnswer";
    private static final String FIELD_CAMPAIGN_POINTS_PARTICIPATION = "pointsForParticipation";
    private static final String FIELD_CAMPAIGN_STATUS = "status";
    // OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK
    // Table Create Statements  -   pg_campaign
    private static final String CREATE_TABLE_CAMPAIGN = "CREATE TABLE " +
            TABLE_CAMPAIGN + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_CAMPAIGN_ID + " INTEGER, " +
            FIELD_CAMPAIGN_SPONSOR_ID + " INTEGER, " +
            FIELD_CAMPAIGN_TITLE + " TEXT, " +
            FIELD_CAMPAIGN_START_DATE + " TEXT, " +
            FIELD_CAMPAIGN_END_DATE + " TEXT, " +
            FIELD_CAMPAIGN_NUMBER_OF_QUESTIONS + " INTEGER, " +
            FIELD_CAMPAIGN_POINTS_RIGHT_ANSWER + " INTEGER, " +
            FIELD_CAMPAIGN_POINTS_PARTICIPATION + " INTEGER, " +
            FIELD_CAMPAIGN_STATUS + " TEXT" + ");";
    // OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK
    // Table: pg_question - Field Names
    private static final String FIELD_QUESTION_ID = "idQuestion";
    private static final String FIELD_QUESTION_CAMPAIGN_ID = "idCampaign";
    private static final String FIELD_QUESTION_SPONSOR_ID = "idSponsor";
    private static final String FIELD_QUESTION_LABEL = "label";
    // OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK
    // Table Create Statements  -  pg_question
    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE " +
            TABLE_QUESTION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_QUESTION_ID + " INTEGER, " +
            FIELD_QUESTION_CAMPAIGN_ID + " INTEGER, " +
            FIELD_QUESTION_SPONSOR_ID + " INTEGER, " +
            FIELD_QUESTION_LABEL + " TEXT );";
    // TODO review
    // Option(Integer idSponsor, Integer idCampaign, Integer idQuestion, Integer sequential, String label, String rightAnswer, String userAnswer)
    // Table: pg_option - Field Names
    private static final String FIELD_OPTION_SPONSOR_ID = "idSponsor";
    private static final String FIELD_OPTION_CAMPAIGN_ID = "idCampaign";
    private static final String FIELD_OPTION_QUESTION_ID = "idQuestion";
    private static final String FIELD_OPTION_SEQUENTIAL = "sequential";
    private static final String FIELD_OPTION_LABEL = "label";
    private static final String FIELD_OPTION_RIGHT_ANSWER = "rightAnswer";
    private static final String FIELD_OPTION_USER_ANSWER = "userAnswer";
    // TODO review
    // Table Create Statements  -  pg_option
    private static final String CREATE_TABLE_OPTION = "CREATE TABLE " +
            TABLE_OPTION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_OPTION_SPONSOR_ID + " INTEGER, " +
            FIELD_OPTION_CAMPAIGN_ID + " INTEGER, " +
            FIELD_OPTION_QUESTION_ID + " INTEGER, " +
            FIELD_OPTION_SEQUENTIAL + " INTEGER, " +
            FIELD_OPTION_LABEL + " TEXT, " +
            FIELD_OPTION_RIGHT_ANSWER + " TEXT, " +
            FIELD_OPTION_USER_ANSWER + " TEXT, " +
            KEY_CREATED_AT + " TEXT" + ");";
    /////////////////////////////////////////////////////////////////////////////////////////////
    // http://guides.codepath.com/android/local-databases-with-sqliteopenhelper
    /////////////////////////////////////////////////////////////////////////////////////////////
    private static PgDatabaseHelper sInstance;
    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private PgDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    // http://guides.codepath.com/android/local-databases-with-sqliteopenhelper
    /////////////////////////////////////////////////////////////////////////////////////////////
    public static synchronized PgDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new PgDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


//    public PgDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SPONSOR);
        db.execSQL(CREATE_TABLE_CAMPAIGN);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_OPTION);
        db.execSQL(CREATE_TABLE_TRANSACTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // oldVersion = 0 to create from zero
        updatePgDatabase(db, 0, DATABASE_VERSION);
    }

    public long addSponsor(Sponsor sponsor) {

        SQLiteDatabase db = this.getWritableDatabase();
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();//
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////

        ContentValues values = new ContentValues();
        values.put(FIELD_SPONSOR_ID, sponsor.getSponsorId());
        values.put(FIELD_SPONSOR_CODE, sponsor.getSponsorCode());
        values.put(FIELD_SPONSOR_NAME, sponsor.getSponsorName());

        // insert row
        long sponsor_id = db.insert(TABLE_SPONSOR, null, values);

        closeDB(db);
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //DatabaseManager.getInstance().closeDatabase(); ///////////////////
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////

        return sponsor_id;
    }

    public long addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_USER_EMAIL, user.getEmail());
        values.put(FIELD_USER_NAME, user.getName());
        values.put(FIELD_USER_PASSWORD, user.getPassword());
        values.put(FIELD_USER_STATUS, user.getUserAccountStatus());
        values.put(FIELD_USER_CPF, user.getCpf());
        values.put(FIELD_USER_COMPANY_CODE, user.getCompanyCode());
        values.put(FIELD_USER_COMPANY_NAME, user.getCompanyName());
        values.put(FIELD_USER_COMPANY_LATITUDE, user.getCompanyLatitude());
        values.put(FIELD_USER_COMPANY_LONGITUDE, user.getCompanyLongitude());

        // insert row
        //String TempTableSponsor = "temp_"+TABLE_SPONSOR;
        long user_id = db.insert(TABLE_USER, null, values);
        closeDB(db);
        return user_id;

    }

    public long addCampaign(Campaign campaign) {

        SQLiteDatabase db = super.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_CAMPAIGN_ID, campaign.getIdCampaign());
        values.put(FIELD_CAMPAIGN_SPONSOR_ID, campaign.getIdSponsor());
        values.put(FIELD_CAMPAIGN_TITLE, campaign.getTitle());
        values.put(FIELD_CAMPAIGN_START_DATE, campaign.getStartDate());
        values.put(FIELD_CAMPAIGN_END_DATE, campaign.getEndDate());
        values.put(FIELD_CAMPAIGN_NUMBER_OF_QUESTIONS, campaign.getNumberOfQuestions());
        values.put(FIELD_CAMPAIGN_POINTS_RIGHT_ANSWER, campaign.getPointsForRightAnswer());
        values.put(FIELD_CAMPAIGN_POINTS_PARTICIPATION, campaign.getPointsForParticipation());
        values.put(FIELD_CAMPAIGN_STATUS, campaign.getStatus());

        long campaign_id = db.insert(TABLE_CAMPAIGN, null, values);


        closeDB(db);


        return campaign_id;

    }

    public long addQuestion(Question question) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_QUESTION_ID, question.getIdQuestion());
        values.put(FIELD_QUESTION_CAMPAIGN_ID, question.getIdCampaign());
        values.put(FIELD_QUESTION_SPONSOR_ID, question.getIdSponsor());
        values.put(FIELD_QUESTION_LABEL, question.getLabel());


        long question_id = db.insert(TABLE_QUESTION, null, values);
        closeDB(db);
        return question_id;
    }

    public long addOption(Option option) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(FIELD_OPTION_CAMPAIGN_ID, option.getIdCampaign());
        values.put(FIELD_OPTION_QUESTION_ID, option.getIdQuestion());
        values.put(FIELD_OPTION_SEQUENTIAL, option.getSequential());
        values.put(FIELD_OPTION_LABEL, option.getLabel());
        values.put(FIELD_OPTION_SPONSOR_ID, option.getIdSponsor());
        values.put(FIELD_OPTION_RIGHT_ANSWER, option.getRightAnswer());
        values.put(FIELD_OPTION_USER_ANSWER, option.getUserAnswer());

        long option_id = db.insert(TABLE_OPTION, null, values);
        closeDB(db);
        return option_id;
    }

    public void updateOption(Option selectedOption) {

        String SQL_Update_Option = "UPDATE " + TABLE_OPTION +
                " SET " +
                FIELD_OPTION_USER_ANSWER + " = " + selectedOption.getUserAnswer() +
                " WHERE (" +
                FIELD_OPTION_CAMPAIGN_ID +" = " + selectedOption.getIdCampaign().toString() + " AND " +
                FIELD_OPTION_QUESTION_ID  +" = " + selectedOption.getIdQuestion().toString() + " AND " +
                FIELD_OPTION_SEQUENTIAL  +" = " + selectedOption.getSequential().toString()+" ) ;";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_Update_Option);
        closeDB(db);
    }


    public long addTransaction(Transaction tr) {

        /*
        Constructor:
        Transaction(String sponsorCode, String eventDate, String title, String nature, int idCampaign, int idTransaction, int amount)
         */
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FIELD_TRANSACTION_SPONSOR_CODE, tr.getSponsorCode());
        values.put(FIELD_TRANSACTION_EVENT_DATE, tr.getEventDate());
        values.put(FIELD_TRANSACTION_CAMPAIGN_TITLE, tr.getTitle());
        values.put(FIELD_TRANSACTION_NATURE, tr.getSponsorCode());
        values.put(FIELD_TRANSACTION_ID_CAMPAIGN, tr.getIdCampaign());
        values.put(FIELD_TRANSACTION_ID, tr.getIdTransaction());
        values.put(FIELD_TRANSACTION_AMOUNT, tr.getAmount());

        // insert row
        long tr_id = db.insert(TABLE_TRANSACTION, null, values);
        closeDB(db);
        return tr_id;
    }

    // CREATE, CLEAR AND DROP TABLES
    public void dropTable(String TableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();//
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        db.execSQL("DROP TABLE IF EXISTS " + TableName);

        closeDB(db);
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //DatabaseManager.getInstance().closeDatabase(); ///////////////////
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
    }

    public void createTable(String SQL_Create_String) {
        SQLiteDatabase db = this.getWritableDatabase();
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();//
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        db.execSQL(SQL_Create_String);
        closeDB(db);
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //DatabaseManager.getInstance().closeDatabase(); ///////////////////
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
    }

    public void createTableUser() {
        dropTable(TABLE_USER);
        createTable(CREATE_TABLE_USER);
    }

    public void createTableSponsor() {
        dropTable(TABLE_SPONSOR);
        createTable(CREATE_TABLE_SPONSOR);
    }

    public void createTableCampaign() {
        dropTable(TABLE_CAMPAIGN);
        createTable(CREATE_TABLE_CAMPAIGN);
    }

    public void createTableQuestion() {
        dropTable(TABLE_QUESTION);
        createTable(CREATE_TABLE_QUESTION);
    }

    public void createTableOption() {
        dropTable(TABLE_OPTION);
        createTable(CREATE_TABLE_OPTION);
    }

    public void createTableTransaction() {
        dropTable(TABLE_TRANSACTION);
        createTable(CREATE_TABLE_TRANSACTION);
    }

    public void clearTableUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER);
    }

    public void clearTableSponsor() {
        SQLiteDatabase db = this.getWritableDatabase();
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();//
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        db.execSQL("DELETE FROM " + TABLE_SPONSOR);
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //DatabaseManager.getInstance().closeDatabase(); ///////////////////
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        closeDB(db);
    }

    public void clearTableCampaign() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CAMPAIGN);
        closeDB(db);
    }

    public void clearTableQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_QUESTION);
    }

    public void clearTableOption() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_OPTION);
    }

    public void clearTableTransaction() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TRANSACTION);
    }

    ///// READ AND QUERY DATA

    public User getLastUser() {

        User user = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " +
                FIELD_USER_NAME + ", " +
                FIELD_USER_CPF + ", " +
                FIELD_USER_EMAIL + ", " +
                FIELD_USER_PASSWORD + ", " +
                FIELD_USER_STATUS + ", " +
                FIELD_USER_COMPANY_CODE + ", " +
                FIELD_USER_COMPANY_NAME + ", " +
                FIELD_USER_COMPANY_LATITUDE + ", " +
                FIELD_USER_COMPANY_LONGITUDE + " " +
                " FROM " + TABLE_USER + ";";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
            if (!c.getString(c.getColumnIndex(FIELD_USER_EMAIL)).isEmpty()) {

                user.setName(c.getString(c.getColumnIndex(FIELD_USER_NAME)));
                user.setCpf(c.getString(c.getColumnIndex(FIELD_USER_CPF)));
                user.setEmail(c.getString(c.getColumnIndex(FIELD_USER_EMAIL)));
                user.setPassword(c.getString(c.getColumnIndex(FIELD_USER_PASSWORD)));
                user.setUserAccountStatus(c.getString(c.getColumnIndex(FIELD_USER_STATUS)));
                user.setCompanyCode(c.getString(c.getColumnIndex(FIELD_USER_COMPANY_CODE)));
                user.setCompanyName(c.getString(c.getColumnIndex(FIELD_USER_COMPANY_NAME)));
                user.setCompanyLatitude(c.getString(c.getColumnIndex(FIELD_USER_COMPANY_LATITUDE)));
                user.setCompanyLongitude(c.getString(c.getColumnIndex(FIELD_USER_COMPANY_LONGITUDE)));
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        closeDB(db);
        return user;
    }

    public Sponsor getSponsorByCode(String sponsorCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + FIELD_SPONSOR_NAME + " FROM " + TABLE_SPONSOR + " WHERE sponsorCode = '" + sponsorCode + "';";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Sponsor sp = new Sponsor();
        sp.setSponsorId(c.getInt(c.getColumnIndex("sponsorId")));
        sp.setSponsorCode(c.getString(c.getColumnIndex("sponsorCode")));
        sp.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return sp;
    }


    public List<Sponsor> getAllSponsors() {
        List<Sponsor> sponsors = new ArrayList<Sponsor>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPONSOR + ";";

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();//
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Sponsor sp = new Sponsor();
                sp.setSponsorId(c.getInt((c.getColumnIndex("sponsorId"))));
                sp.setSponsorCode(c.getString(c.getColumnIndex("sponsorCode")));
                sp.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));

                Log.e("sponsorId", Integer.toString(c.getInt((c.getColumnIndex("sponsorId")))));
                Log.e("sponsorCode", (c.getString(1)));
                Log.e("sponsorId", c.getString((c.getColumnIndex("sponsorId"))));

                // adding to todo list
                sponsors.add(sp);
            } while (c.moveToNext());
        }

        ///////////////////////////////////////////////////////////////////
        ///////  using DatabaseManager/////////////////////////////////////
        //DatabaseManager.getInstance().closeDatabase(); ///////////////////
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        if (c != null && !c.isClosed()) {
            c.close();
        }
        closeDB(db);

        return sponsors;
    }

    public List<CampaignListClass> getAllCampaigns() {
        List<CampaignListClass> campList = new ArrayList<CampaignListClass>();

        try {
            String selectQuery = "SELECT cp." + FIELD_CAMPAIGN_TITLE + " campaignName, " +
                    " cp." + FIELD_CAMPAIGN_ID + " campaignId, " +
                    " sp." + FIELD_SPONSOR_NAME + " sponsorName, " +
                    " cp." + FIELD_CAMPAIGN_START_DATE + " startDate, " +
                    " cp." + FIELD_CAMPAIGN_STATUS + " campaignStatus " +
                    " FROM " + TABLE_CAMPAIGN + " cp, " + TABLE_SPONSOR + " sp " +
                    " WHERE " + "(cp." + FIELD_CAMPAIGN_SPONSOR_ID + " = sp." + FIELD_SPONSOR_ID + ");";

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.rawQuery(selectQuery, null);
            c.moveToFirst();
            Log.i("teste", Integer.toString(c.getCount()));
            CampaignListClass cpl_item = new CampaignListClass();

            // looping through all rows returned by cursor and adding to the list of CampaignListClass
            // CampaignListClass(String campaignName, String sponsorName, String startDate, String campaignStatus)
            if (c.moveToFirst()) {
                do {// adding item to list
                    cpl_item = new CampaignListClass(c.getString(c.getColumnIndex("campaignName")),
                            c.getInt(c.getColumnIndex("campaignId")),
                            c.getString(c.getColumnIndex("sponsorName")),
                            c.getString(c.getColumnIndex("startDate")),
                            c.getString(c.getColumnIndex("campaignStatus")));

                    campList.add(cpl_item);
                } while (c.moveToNext());
                if (c != null && !c.isClosed()) {
                    c.close();
                }
                closeDB(db);
            }
        } catch (Exception e) {
            Log.i("Erro", e.toString());
        }
        return campList;
    }


    public List<CampaignDetailListClass> getAllDetailedCampaigns() {
        List<CampaignDetailListClass> campDetailedList = new ArrayList<CampaignDetailListClass>();
        String selectQuery = "SELECT cp." + FIELD_CAMPAIGN_TITLE +
                " campaignTitle, " + "cp." + FIELD_CAMPAIGN_ID +
                " campaignId, sp." + FIELD_SPONSOR_NAME +
                " sponsorName, cp." + FIELD_CAMPAIGN_START_DATE +
                " startDate, cp." + FIELD_CAMPAIGN_STATUS +
                " campaignStatus, qt." + FIELD_QUESTION_LABEL +
                " questionLabel, op." + FIELD_OPTION_SEQUENTIAL +
                " optionSeqNumber, op." + FIELD_OPTION_LABEL +
                " optionLabel, op." + FIELD_OPTION_RIGHT_ANSWER +
                " optionIsRight, op." + FIELD_OPTION_USER_ANSWER +
                " optionUserAnswer, cp." + FIELD_CAMPAIGN_POINTS_RIGHT_ANSWER +
                " pointsRightAnswer, cp." + FIELD_CAMPAIGN_POINTS_PARTICIPATION + " pointsParticipation " +
                " FROM " + TABLE_CAMPAIGN + " cp ," + TABLE_SPONSOR + " sp, " + TABLE_QUESTION + " qt, " + TABLE_OPTION + " op " +
                " WHERE ((cp." + FIELD_CAMPAIGN_SPONSOR_ID + " = sp." + FIELD_SPONSOR_ID + ") " +
                " AND (qt." + FIELD_QUESTION_CAMPAIGN_ID + " = cp." + FIELD_CAMPAIGN_ID + ") " +
                " AND ((op." + FIELD_OPTION_CAMPAIGN_ID + " = qt." + FIELD_QUESTION_CAMPAIGN_ID + ") and (op." + FIELD_OPTION_QUESTION_ID + " = qt." + FIELD_QUESTION_ID + ")));";

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows returned by cursor and adding to the list of CampaignDetailListClass

        //     private String campaignTitle, sponsorName, startDate, campaignStatus, questionLabel, optionLabel,optionIsRight,optionUserAnswer;
        //     private int optionSeqNumber, pointsRightAnswer, pointsParticipation;

        if (c.moveToFirst()) {
            do {
                CampaignDetailListClass mCpDetailListItem = new CampaignDetailListClass();

                mCpDetailListItem.setCampaignTitle(c.getString((c.getColumnIndex("campaignTitle"))));
                mCpDetailListItem.setCampaignId(c.getInt(c.getColumnIndex("campaignId")));       // int
                mCpDetailListItem.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));
                mCpDetailListItem.setStartDate(c.getString(c.getColumnIndex("startDate")));
                mCpDetailListItem.setCampaignStatus(c.getString(c.getColumnIndex("campaignStatus")));
                mCpDetailListItem.setQuestionLabel(c.getString(c.getColumnIndex("questionLabel")));
                mCpDetailListItem.setOptionSeqNumber(c.getInt(c.getColumnIndex("optionSeqNumber")));         // int
                mCpDetailListItem.setOptionLabel(c.getString(c.getColumnIndex("optionLabel")));
                mCpDetailListItem.setOptionIsRight(c.getString(c.getColumnIndex("optionIsRight")));
                mCpDetailListItem.setOptionUserAnswer(c.getString(c.getColumnIndex("optionUserAnswer")));
                mCpDetailListItem.setPointsRightAnswer(c.getInt(c.getColumnIndex("pointsRightAnswer")));       // int
                mCpDetailListItem.setPointsParticipation(c.getInt(c.getColumnIndex("pointsParticipation")));       // int

                // adding to todo list
                campDetailedList.add(mCpDetailListItem);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return campDetailedList;
    }

    // example:
//            {"idCampaign":4, "questions": [
//                                     {"idQuestion":1,"userAnswers": [ "Y", "N", "N", "Y" ] },
//                                     {"idQuestion":2,"userAnswers": [ "N", "Y" ] }
//                                  ] }

    public String getJsonUserAnswerByCampaignId(int selectedCampaignId) {

        List<UserAnswer> userAnswers = new ArrayList<UserAnswer>();
        String result = "{\"message\":\"error\"}";
        userAnswers = getUserAnswerByCampaignId(selectedCampaignId);
        if (userAnswers.size() > 0) {
            int mLastQuestionNumber;
            int mIdQuestion = -1;
            UserAnswer mUserAnswer;
            UserAnswer mLastUserAnswer;
            mLastUserAnswer =  userAnswers.get(userAnswers.size()-1);
            mLastQuestionNumber =mLastUserAnswer.getIdQuestion();
            result =  "{\"idCampaign\":" + Integer.toString(selectedCampaignId) + ", \"questions\": [ ";

            for (int i = 0; i < userAnswers.size(); i++) {
                // get one answer item
                mUserAnswer = userAnswers.get(i);

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if (mIdQuestion != mUserAnswer.getIdQuestion()) {    // is a new question
                    mIdQuestion = mUserAnswer.getIdQuestion();
                    result = result + "{\"idQuestion\":" + Integer.toString(mUserAnswer.getIdQuestion()) + ", \"userAnswers\":[ ";
                    //{"idCampaign":1, "questions": [ {"idQuestion":1 "userAnswers":[ "Y", "N", "N", "N"
                    while (mUserAnswer.getIdQuestion() == mIdQuestion) {
                        if (mUserAnswer.getIdOption() > 1) {
                            // put a comma befor Y/N answer
                            result = result + ", ";
                        }
                        // put a "Y" or "N" for the user answer for this option
                        result = result + "\"" + mUserAnswer.getUserAnswer() + "\"";
                        // skip to next user answer
                        Log.d("i = ", Integer.toString(i));
                        if (i<userAnswers.size()-1){
                            i++;
                        } else {
                            mIdQuestion=-1;
                        }
                        mUserAnswer = userAnswers.get(i);
                        Log.d("i = ", Integer.toString(i));
                    }   // next option
                    if (i<userAnswers.size()-1) {
                        i--;
                    }

                    //  reach this point when finished one idQuestion... so we have to close the ] and }
                    result = result + "] } ";
                    // but if this was not the last question we have to put a comma before next question
                    if (mIdQuestion>-1){
                        result = result + ", ";
                    }

                }   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            }    // next i...
            // time to close the Json string:
            result = result + "] } ";

        }   // end  if (userAnswers.size()>0)

        return result;
    }




    public List<UserAnswer> getUserAnswerByCampaignId(int selectedCampaignId) {
        List<UserAnswer> userAnswers = new ArrayList<UserAnswer>();
        String selectQuery = "SELECT cp." + FIELD_CAMPAIGN_ID + " idCampaign, " +
                "qt." + FIELD_QUESTION_ID + " idQuestion, " +
                "op." + FIELD_OPTION_SEQUENTIAL + " idOption, " +
                "op." + FIELD_OPTION_USER_ANSWER + " opUserAnswer " +
                " FROM " + TABLE_CAMPAIGN + " cp ," + TABLE_QUESTION + " qt, " + TABLE_OPTION + " op " +
                " WHERE ((qt." + FIELD_QUESTION_CAMPAIGN_ID + " = cp." + FIELD_CAMPAIGN_ID + ") " +
                " AND (cp." + FIELD_CAMPAIGN_ID + " = " + selectedCampaignId + ") " +
                " AND ((op." + FIELD_OPTION_CAMPAIGN_ID + " = qt." + FIELD_QUESTION_CAMPAIGN_ID + ") and (op." + FIELD_OPTION_QUESTION_ID + " = qt." + FIELD_QUESTION_ID + "))) " +
                " ORDER BY idCampaign ASC, idQuestion ASC, idOption ASC;";

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows returned by cursor and adding to the list of CampaignDetailListClass

        //     private String campaignTitle, sponsorName, startDate, campaignStatus, questionLabel, optionLabel,optionIsRight,optionUserAnswer;
        //     private int optionSeqNumber, pointsRightAnswer, pointsParticipation;

        if (c.moveToFirst()) {
            do {
                UserAnswer mUserAnswerItem = new UserAnswer();

                mUserAnswerItem.setIdCampaign(c.getInt((c.getColumnIndex("idCampaign"))));
                mUserAnswerItem.setIdQuestion(c.getInt(c.getColumnIndex("idQuestion")));
                mUserAnswerItem.setIdOption(c.getInt(c.getColumnIndex("idOption")));
                mUserAnswerItem.setUserAnswer(c.getString(c.getColumnIndex("opUserAnswer")));

                // adding to todo list
                userAnswers.add(mUserAnswerItem);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        closeDB(db);
        return userAnswers;
    }


    public List<CampaignDetailListClass> getDetailedCampaignByCampaignId(String selectedCampaignId) {
        List<CampaignDetailListClass> campDetailedList = new ArrayList<CampaignDetailListClass>();
        String selectQuery = "SELECT cp." + FIELD_CAMPAIGN_TITLE + " campaignTitle, " +
                "cp." + FIELD_CAMPAIGN_ID + " campaignId, " +
                "sp." + FIELD_SPONSOR_NAME + " sponsorName, " +
                "cp." + FIELD_CAMPAIGN_START_DATE + " startDate, " +
                "cp." + FIELD_CAMPAIGN_STATUS + " campaignStatus, " +
                "qt." + FIELD_QUESTION_LABEL + " questionLabel, " +
                "op." + FIELD_OPTION_SEQUENTIAL + " optionSeqNumber, " +
                "op." + FIELD_OPTION_LABEL + " optionLabel, " +
                "op." + FIELD_OPTION_RIGHT_ANSWER + " optionIsRight, " +
                "op." + FIELD_OPTION_USER_ANSWER + " optionUserAnswer, " +
                "cp." + FIELD_CAMPAIGN_POINTS_RIGHT_ANSWER + " pointsRightAnswer, " +
                "cp." + FIELD_CAMPAIGN_POINTS_PARTICIPATION + " pointsParticipation " +
                " FROM " + TABLE_CAMPAIGN + " cp ," + TABLE_SPONSOR + " sp, " + TABLE_QUESTION + " qt, " + TABLE_OPTION + " op " +
                " WHERE ((cp." + FIELD_CAMPAIGN_SPONSOR_ID + " = sp." + FIELD_SPONSOR_ID + ") " +
                " AND (qt." + FIELD_QUESTION_CAMPAIGN_ID + " = cp." + FIELD_CAMPAIGN_ID + ") " +
                " AND (cp." + FIELD_CAMPAIGN_ID + " = " + selectedCampaignId + ") " +
                " AND ((op." + FIELD_OPTION_CAMPAIGN_ID + " = qt." + FIELD_QUESTION_CAMPAIGN_ID + ") and (op." + FIELD_OPTION_QUESTION_ID + " = qt." + FIELD_QUESTION_ID + ")));";

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows returned by cursor and adding to the list of CampaignDetailListClass

        //     private String campaignTitle, sponsorName, startDate, campaignStatus, questionLabel, optionLabel,optionIsRight,optionUserAnswer;
        //     private int optionSeqNumber, pointsRightAnswer, pointsParticipation;

        if (c.moveToFirst()) {
            do {
                CampaignDetailListClass mCpDetailCampaignItem = new CampaignDetailListClass();

                mCpDetailCampaignItem.setCampaignTitle(c.getString((c.getColumnIndex("campaignTitle"))));
                mCpDetailCampaignItem.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));
                mCpDetailCampaignItem.setStartDate(c.getString(c.getColumnIndex("startDate")));
                mCpDetailCampaignItem.setCampaignStatus(c.getString(c.getColumnIndex("campaignStatus")));
                mCpDetailCampaignItem.setQuestionLabel(c.getString(c.getColumnIndex("questionLabel")));
                mCpDetailCampaignItem.setOptionSeqNumber(c.getInt(c.getColumnIndex("optionSeqNumber")));         // int
                mCpDetailCampaignItem.setOptionLabel(c.getString(c.getColumnIndex("optionLabel")));
                mCpDetailCampaignItem.setOptionIsRight(c.getString(c.getColumnIndex("optionIsRight")));
                mCpDetailCampaignItem.setOptionUserAnswer(c.getString(c.getColumnIndex("optionUserAnswer")));
                mCpDetailCampaignItem.setPointsRightAnswer(c.getInt(c.getColumnIndex("pointsRightAnswer")));       // int
                mCpDetailCampaignItem.setPointsParticipation(c.getInt(c.getColumnIndex("pointsParticipation")));       // int

                // adding to todo list
                campDetailedList.add(mCpDetailCampaignItem);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        closeDB(db);
        return campDetailedList;
    }

    public List<CampaignDetailListClass> getDetailedCampaignByCampaignName(String selectedCampaignName) {
        List<CampaignDetailListClass> campDetailedList = new ArrayList<CampaignDetailListClass>();
        String selectQuery = "SELECT cp." + FIELD_CAMPAIGN_TITLE + " campaignTitle, " +
                "cp." + FIELD_CAMPAIGN_ID + " campaignId, " +
                "sp." + FIELD_SPONSOR_NAME + " sponsorName, " +
                "cp." + FIELD_CAMPAIGN_START_DATE + " startDate, " +
                "cp." + FIELD_CAMPAIGN_STATUS + " campaignStatus, " +
                "qt." + FIELD_QUESTION_LABEL + " questionLabel, " +
                "op." + FIELD_OPTION_SEQUENTIAL + " optionSeqNumber, " +
                "op." + FIELD_OPTION_LABEL + " optionLabel, " +
                "op." + FIELD_OPTION_RIGHT_ANSWER + " optionIsRight, " +
                "op." + FIELD_OPTION_USER_ANSWER + " optionUserAnswer, " +
                "cp." + FIELD_CAMPAIGN_POINTS_RIGHT_ANSWER + " pointsRightAnswer, " +
                "cp." + FIELD_CAMPAIGN_POINTS_PARTICIPATION + " pointsParticipation " +
                " FROM " + TABLE_CAMPAIGN + " cp ," + TABLE_SPONSOR + " sp, " + TABLE_QUESTION + " qt, " + TABLE_OPTION + " op " +
                " WHERE ((cp." + FIELD_CAMPAIGN_SPONSOR_ID + " = sp." + FIELD_SPONSOR_ID + ") " +
                " AND (qt." + FIELD_QUESTION_CAMPAIGN_ID + " = cp." + FIELD_CAMPAIGN_ID + ") " +
                " AND (cp." + FIELD_CAMPAIGN_TITLE + " = " + selectedCampaignName + ") " +
                " AND ((op." + FIELD_OPTION_CAMPAIGN_ID + " = qt." + FIELD_QUESTION_CAMPAIGN_ID + ") and (op." + FIELD_OPTION_QUESTION_ID + " = qt." + FIELD_QUESTION_ID + ")));";

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows returned by cursor and adding to the list of CampaignDetailListClass

        //     private String campaignTitle, sponsorName, startDate, campaignStatus, questionLabel, optionLabel,optionIsRight,optionUserAnswer;
        //     private int optionSeqNumber, pointsRightAnswer, pointsParticipation;

        if (c.moveToFirst()) {
            do {
                CampaignDetailListClass mCpDetailCampaignItem = new CampaignDetailListClass();

                mCpDetailCampaignItem.setCampaignTitle(c.getString((c.getColumnIndex("campaignTitle"))));
                mCpDetailCampaignItem.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));
                mCpDetailCampaignItem.setStartDate(c.getString(c.getColumnIndex("startDate")));
                mCpDetailCampaignItem.setCampaignStatus(c.getString(c.getColumnIndex("campaignStatus")));
                mCpDetailCampaignItem.setQuestionLabel(c.getString(c.getColumnIndex("questionLabel")));
                mCpDetailCampaignItem.setOptionSeqNumber(c.getInt(c.getColumnIndex("optionSeqNumber")));         // int
                mCpDetailCampaignItem.setOptionLabel(c.getString(c.getColumnIndex("optionLabel")));
                mCpDetailCampaignItem.setOptionIsRight(c.getString(c.getColumnIndex("optionIsRight")));
                mCpDetailCampaignItem.setOptionUserAnswer(c.getString(c.getColumnIndex("optionUserAnswer")));
                mCpDetailCampaignItem.setPointsRightAnswer(c.getInt(c.getColumnIndex("pointsRightAnswer")));       // int
                mCpDetailCampaignItem.setPointsParticipation(c.getInt(c.getColumnIndex("pointsParticipation")));       // int

                // adding to todo list
                campDetailedList.add(mCpDetailCampaignItem);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        closeDB(db);
        return campDetailedList;
    }

    public List<Question> getQuestionsByCampaignId(int selectedCampaignId) {
        List<Question> questionList = new ArrayList<Question>();
        String selectQuery = "SELECT qt." + FIELD_QUESTION_ID + " questionId, " +
                "qt." + FIELD_QUESTION_LABEL + " questionLabel " +
                " FROM " + TABLE_CAMPAIGN + " cp, " + TABLE_QUESTION + " qt " +
                " WHERE ((cp." + FIELD_CAMPAIGN_ID + " = " + selectedCampaignId + ") " +
                " AND ((cp." + FIELD_CAMPAIGN_ID + " = qt." + FIELD_OPTION_CAMPAIGN_ID + "))) " +
                " ORDER BY qt." + FIELD_OPTION_QUESTION_ID + ";";

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows returned by cursor and adding to the list of CampaignDetailListClass

        //     private String campaignTitle, sponsorName, startDate, campaignStatus, questionLabel, optionLabel,optionIsRight,optionUserAnswer;
        //     private int optionSeqNumber, pointsRightAnswer, pointsParticipation;

        if (c.moveToFirst()) {
            do {
                Question mQuestionItem = new Question();

                mQuestionItem.setIdQuestion(c.getInt((c.getColumnIndex("questionId"))));      // int
                mQuestionItem.setLabel(c.getString(c.getColumnIndex("questionLabel")));

                // adding to questionList
                questionList.add(mQuestionItem);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        closeDB(db);
        return questionList;
    }

    public List<Option> getOptionsByQuestionAndCampaignId(int selectedCampaignId, int selectedQuestionId) {

        /**
         *     Option Class:
         ===========================
         private Integer idSponsor;   //
         private Integer idCampaign;
         private Integer idQuestion;  //
         private Integer sequential;
         ---------------------------
         private String label;
         private String rightAnswer;
         private String userAnswer;
         ===========================
         */
        List<Option> optionList = new ArrayList<Option>();

        String TAG = "getOptionsByQuestionAndCampaignId";

//        CREATE TABLE pg_option(_id INTEGER PRIMARY KEY AUTOINCREMENT,
// idSponsor INTEGER, idCampaign INTEGER, idQuestion INTEGER, sequential INTEGER,
// label TEXT, rightAnswer TEXT, userAnswer TEXT, created_at TEXT);

//        FIELD_OPTION_SPONSOR_ID = "idSponsor";  /
//        FIELD_OPTION_CAMPAIGN_ID = "idCampaign"; /
//        FIELD_OPTION_QUESTION_ID = "idQuestion"; /
//        FIELD_OPTION_SEQUENTIAL = "sequential"; /
//        FIELD_OPTION_LABEL = "label"; /
//        FIELD_OPTION_RIGHT_ANSWER = "rightAnswer"; /
//        FIELD_OPTION_USER_ANSWER = "userAnswer"; /

        String selectQuery = "SELECT op." + FIELD_OPTION_SEQUENTIAL + " optionSeqNumber, " +
                " op." + FIELD_OPTION_LABEL + " optionLabel, " +
                " op." + FIELD_OPTION_RIGHT_ANSWER + " optionRightAnswer, " +
                " op." + FIELD_OPTION_USER_ANSWER + " optionUserAnswer, " +
                " op." + FIELD_OPTION_SPONSOR_ID + " optionSponsorId, " +
                " op." + FIELD_OPTION_CAMPAIGN_ID + " optionCampaignId, " +
                " op." + FIELD_OPTION_QUESTION_ID + " optionQuestionId " +
                " FROM " + TABLE_OPTION + " op " +
                " WHERE ((op." + FIELD_OPTION_CAMPAIGN_ID + " = " + selectedCampaignId + ") " +
                " AND (op." + FIELD_OPTION_QUESTION_ID + " = " + selectedQuestionId + ")) " +
                " ORDER BY op." + FIELD_OPTION_SEQUENTIAL + ";";
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows returned by cursor and adding to the list of CampaignDetailListClass
        if (c.moveToFirst()) {
            do {
                Option mOptionItem = new Option();

                mOptionItem.setIdQuestion(c.getInt((c.getColumnIndex("optionQuestionId"))));      // int
                mOptionItem.setIdSponsor(c.getInt((c.getColumnIndex("optionSponsorId"))));      // int
                mOptionItem.setIdCampaign(c.getInt((c.getColumnIndex("optionCampaignId"))));      // int
                mOptionItem.setSequential(c.getInt((c.getColumnIndex("optionSeqNumber"))));      // int
                mOptionItem.setLabel(c.getString(c.getColumnIndex("optionLabel")));       // String
                mOptionItem.setRightAnswer(c.getString(c.getColumnIndex("optionRightAnswer")));       // String
                mOptionItem.setUserAnswer(c.getString(c.getColumnIndex("optionUserAnswer")));       // String

                // adding to questionList
                optionList.add(mOptionItem);
            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        closeDB(db);
        return optionList;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    /////////     HANDLING DATABASE VERSION
    ///////////////////////////////////////////////////////////////////////////////////////////
    private void updatePgDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 1) {
            // handle each upgrade here
            // suppose you added a new column in version 1

            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPONSOR);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMPAIGN);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);

            // create new tables
            onCreate(db);
        }
        if (oldVersion < 2) {
            // this code will run if the user already has the version
            // so insert here the code to add the extra column
            // example: db.execSQL("ALTER TABLE CAMPAIGN ADD COLUMN EXPIRED INT;");
        }

        // downgrade:
        if (oldVersion == 3) {
            // run code to existing version 3 database...
        }
        if (oldVersion < 6) {
            // run code...
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    /////////     UTILITIES
    ///////////////////////////////////////////////////////////////////////////////////////////
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // closing database
    public void closeDB(SQLiteDatabase db) {
        //SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    URL createURL(String email, String password, String action, String token) {
        String baseURL = "http://www.benben.net.br/apiPharmaGo?";
        // LoginActivity.super.getString(R.string.web_sevice_base_url);
        try {
            // create URL for specified email and password
            String baseUrl = "http://www.benben.net.br/apiPharmaGo?";
            String urlString = baseUrl + "email=" + email + "&password=" + password + "&action=" + action + "&token=" + token;
            // URLEncoder.encode(city, "UTF-8") use caso haja espaços ou outros caracteres especiais em uma string da consulta...

            return new URL(urlString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
