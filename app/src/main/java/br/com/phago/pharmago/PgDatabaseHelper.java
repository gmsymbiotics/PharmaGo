package br.com.phago.pharmago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gustavo on 05/03/2017.
 * *
 * // TIPS to debug SQLite databases ...
 * // adb root         //// to get logger as root in adb
 * // adb -e shell
 * // su /// prompt = '$' if you are not rooted root, if you are rooted your prompt will be '#'
 * //                  run 'su' command to change to #
 * // cd /data/data/br.com.phago.pharmago/databases/
 * // ls -l
 * // rm <filename>   ////  to DELETE a FILE
 * // sqlite3 phago.db
 * // .tables
 * // .schema <tablename>
 * // SELECT * FROM <tablename>;
 * // DROP TABLE <tablename>;
 * // .help
 */


public class PgDatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "PgDatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "phago.db";

    // Common column names (used in more than one table)
    private static final String KEY_ID = "_id";
    private static final String KEY_CREATED_AT = "created_at";

    // Table Names - pg_user
    private static final String TABLE_USER = "pg_user";
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
            FIELD_SPONSOR_ID + " INTEGER, "+
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
            FIELD_OPTION_RIGHT_ANSWER + " INTEGER, " +
            FIELD_OPTION_USER_ANSWER + " INTEGER, " +
            KEY_CREATED_AT + " TEXT" + ");";



    // TODO - CRUD (Create, Read, Update and Delete) Operations

    // Constructor
    public PgDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

        ContentValues values = new ContentValues();
        values.put(FIELD_SPONSOR_ID, sponsor.getSponsorId());
        values.put(FIELD_SPONSOR_CODE, sponsor.getSponsorCode());
        values.put(FIELD_SPONSOR_NAME, sponsor.getSponsorName());

        // insert row
        long sponsor_id = db.insert(TABLE_SPONSOR, null, values);

        return sponsor_id;
    }

    public long addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_USER_EMAIL, user.getEmail());
        values.put(FIELD_USER_NAME, user.getName());
        values.put(FIELD_USER_STATUS, user.getUserAccountStatus());
        values.put(FIELD_USER_CPF, user.getCpf());
        values.put(FIELD_USER_COMPANY_CODE, user.getCompanyCode());
        values.put(FIELD_USER_COMPANY_NAME, user.getCompanyName());
        values.put(FIELD_USER_COMPANY_LATITUDE, user.getCompanyLatitude());
        values.put(FIELD_USER_COMPANY_LONGITUDE, user.getCompanyLongitude());

        // insert row
        //String TempTableSponsor = "temp_"+TABLE_SPONSOR;
        long user_id = db.insert(TABLE_USER, null, values);

        return user_id;

    }

    public long addCampaign(Campaign campaign) {

        SQLiteDatabase db = this.getWritableDatabase();

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

        return option_id;
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
        long tr_id = db.insert(TABLE_SPONSOR, null, values);

        return tr_id;
    }

    /*
* getting all Campaign
*
    public List<Campaign> getAllCampaigns() {
        List<Campaign> campaigns = new ArrayList<Campaign>();
        String selectQuery = "SELECT  * FROM " + TABLE_CAMPAIGN;

        //Log.e(LOG, selectQuery);
        // CREATE TABLE pg_campaign(_id INTEGER PRIMARY KEY AUTOINCREMENT, idCampaign TEXT, sponsorCode TEXT,
        // sponsorName TEXT, startDate TEXT, endDate TEXT,
        // numberOfQuestions INTEGER, pointsForRightAnswer INTEGER, pointsForParticipation INTEGER,
        // status TEXT);


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Campaign cp = new Campaign();

                // _id PRIMARY KEY
                cp.set_id(c.getInt((c.getColumnIndex(KEY_ID))));
                //setIdCampaign(String sponsorCode, String startDate, int numberOfQuestions,
                //int pointsForRightAnswer, int pointsForParticipation)

                // idCampaign
                cp.setIdCampaign((c.getString(c.getColumnIndex("sponsorCode"))),
                                                (c.getString(c.getColumnIndex("startDate"))),
                                                (c.getInt(c.getColumnIndex("numberOfQuestions"))),
                                                (c.getInt(c.getColumnIndex("pointsForRightAnswer"))),
                                                (c.getInt(c.getColumnIndex("pointsForParticipation"))));
                // sponsorCode
                cp.setSponsorCode(c.getString(c.getColumnIndex("sponsorCode")));
                // sponsorName
                cp.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));
                // startDate
                cp.setStartDate(c.getString(c.getColumnIndex("startDate")));
                // endDate
                cp.setEndDate(c.getString(c.getColumnIndex("endDate")));
                // numberOfQuestions
                cp.setNumberOfQuestions(c.getInt(c.getColumnIndex("numberOfQuestions")));
                // pointsForRightAnswer
                cp.setPointsForRightAnswer(c.getInt(c.getColumnIndex("pointsForRightAnswer")));
                // pointsForParticipation
                cp.setPointsForParticipation(c.getInt(c.getColumnIndex("pointsForParticipation")));
                // status
                cp.setStatus(c.getString(c.getColumnIndex("status")));


                // adding to todo list
                campaigns.add(cp);
            } while (c.moveToNext());
        }

        return campaigns;
    }
    */

    /**
     * get datetime
     */

    public void dropTable(String TableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
    }

    public void createTableUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_USER);
    }

    public void createTableSponsor(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_SPONSOR);
    }

    public void createTableCampaign(){
        SQLiteDatabase db = this.getWritableDatabase();
        dropTable(TABLE_CAMPAIGN);
        db.execSQL(CREATE_TABLE_CAMPAIGN);
    }

    public void createTableQuestion(){
        SQLiteDatabase db = this.getWritableDatabase();
        dropTable(TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_QUESTION);
    }

    public void createTableOption(){
        SQLiteDatabase db = this.getWritableDatabase();
        dropTable(TABLE_OPTION);
        db.execSQL(CREATE_TABLE_OPTION);
    }

    public void createTableTransaction(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }


    public void clearTableUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_USER);
    }

    public void clearTableSponsor(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_SPONSOR);
    }

    public void clearTableCampaign(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_CAMPAIGN);
    }

    public void clearTableQuestion(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_QUESTION);
    }

    public void clearTableOption(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_OPTION);
    }

    public void clearTableTransaction(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_TRANSACTION);
    }


    public Sponsor getSponsorByCode(String sponsorCode){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT "+FIELD_SPONSOR_NAME+" FROM " + TABLE_SPONSOR +" WHERE sponsorCode = '"+ sponsorCode + "';";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Sponsor sp = new Sponsor();
        sp.setSponsorId(c.getInt(c.getColumnIndex("sponsorId")));
        sp.setSponsorCode(c.getString(c.getColumnIndex("sponsorCode")));
        sp.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));
        return sp;
    }

    public List<Sponsor> getAllSponsors() {
        List<Sponsor> sponsors = new ArrayList<Sponsor>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPONSOR;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Sponsor sp = new Sponsor();
                sp.setSponsorId(c.getInt((c.getColumnIndex("sponsorId"))));
                sp.setSponsorCode(c.getString(c.getColumnIndex("sponsorCode")));
                sp.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));

                // adding to todo list
                sponsors.add(sp);
            } while (c.moveToNext());
        }

        return sponsors;
    }

    public List<CampaignListClass> getAllCampaigns() {
        List<CampaignListClass> campList = new ArrayList<CampaignListClass>();
        String selectQuery = "SELECT cp." + FIELD_CAMPAIGN_TITLE + " campaignName, " +
                " sp." + FIELD_SPONSOR_NAME + " sponsorName, " +
                " cp." + FIELD_CAMPAIGN_START_DATE + " startDate, " +
                " cp." + FIELD_CAMPAIGN_STATUS + " campaignStatus " +
                " FROM " + TABLE_CAMPAIGN + " cp, " + TABLE_SPONSOR + " sp "+
                " WHERE " + "(cp." + FIELD_CAMPAIGN_SPONSOR_ID + " = sp." + FIELD_SPONSOR_ID +");";

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows returned by cursor and adding to the list of CampaignListClass
        // CampaignListClass(String campaignName, String sponsorName, String startDate, String campaignStatus)
        if (c.moveToFirst()) {
            do {
                CampaignListClass mCampaignListItem = new CampaignListClass();
                mCampaignListItem.setCampaignName(c.getString((c.getColumnIndex("campaignName"))));
                mCampaignListItem.setSponsorName(c.getString(c.getColumnIndex("sponsorName")));
                mCampaignListItem.setStartDate(c.getString(c.getColumnIndex("startDate")));
                mCampaignListItem.setCampaignStatus(c.getString(c.getColumnIndex("campaignStatus")));

                // adding to todo list
                campList.add(mCampaignListItem);
            } while (c.moveToNext());
        }

        return campList;
    }


    public List<CampaignDetailListClass> getAllDetailedCampaigns() {
        List<CampaignDetailListClass> campDetailedList = new ArrayList<CampaignDetailListClass>();
        String selectQuery = "SELECT cp." + FIELD_CAMPAIGN_TITLE +
                " campaignTitle, sp." + FIELD_SPONSOR_NAME +
                " sponsorName, cp." + FIELD_CAMPAIGN_START_DATE +
                " startDate, cp." + FIELD_CAMPAIGN_STATUS +
                " campaignStatus, qt."+ FIELD_QUESTION_LABEL +
                " questionLabel, op." + FIELD_OPTION_SEQUENTIAL +
                " optionSeqNumber, op." + FIELD_OPTION_LABEL +
                " optionLabel, op." + FIELD_OPTION_RIGHT_ANSWER +
                " optionIsRight, op." + FIELD_OPTION_USER_ANSWER +
                " optionUserAnswer, cp." + FIELD_CAMPAIGN_POINTS_RIGHT_ANSWER +
                " pointsRightAnswer, cp." + FIELD_CAMPAIGN_POINTS_PARTICIPATION +" pointsParticipation " +
                " FROM " + TABLE_CAMPAIGN + " cp ," + TABLE_SPONSOR + " sp, " + TABLE_QUESTION +" qt, " + TABLE_OPTION + " op " +
                " WHERE ((cp." + FIELD_CAMPAIGN_SPONSOR_ID + " = sp." + FIELD_SPONSOR_ID +") " +
                " AND (qt." + FIELD_QUESTION_CAMPAIGN_ID + " = cp." + FIELD_CAMPAIGN_ID +") " +
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

        return campDetailedList;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    /////////     HANDLING DATABASE VERSION
    ///////////////////////////////////////////////////////////////////////////////////////////
    private void updatePgDatabase(SQLiteDatabase db, int oldVersion, int newVersion){

        if (oldVersion<1) {
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
        if (oldVersion<2){
            // this code will run if the user already has the version
            // so insert here the code to add the extra column
            // example: db.execSQL("ALTER TABLE CAMPAIGN ADD COLUMN EXPIRED INT;");
        }

        // downgrade:
        if (oldVersion==3){
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
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}