package com.aineri.group.vote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String LOG = "DatabaseHelper";
	
    // Database Version
    private static final int DATABASE_VERSION = 1;	
    
    // Database Name
    private static final String DATABASE_NAME = "VOTES";    
    
    // Table Names
    private static final String TABLE_VOTE = "VOTE"; 
    private static final String TABLE_RESULT = "RESULT";
    
    // Column names Table Vote
    private static final String KEY_ID = "id";
    private static final String KEY_TOPIC = "topic";  
    private static final String KEY_CHOICE1 = "choice1";  
    private static final String KEY_CHOICE2 = "choice2"; 
    private static final String KEY_CHOICE3 = "choice3"; 
    private static final String KEY_CHOICE4 = "choice4"; 
    private static final String KEY_TIMER = "timer"; 
    private static final String KEY_ON = "created"; 
    private static final String KEY_STATUS = "status"; 
    
    // Column names Table RESULT
    private static final String KEY_RES_ID = "id";
    private static final String KEY_TOPIC_ID = "topicid";  
    private static final String KEY_OPTION = "option";  
   
    
    // Vote table create statement
    private static final String CREATE_TABLE_VOTE = "CREATE TABLE "
            + TABLE_VOTE + "(" 
    		+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
            + KEY_TOPIC+ " TEXT," 
    		+ KEY_CHOICE1 + " TEXT,"
            + KEY_CHOICE2 + " TEXT,"
    		+ KEY_CHOICE3 + " TEXT,"
            + KEY_CHOICE4 + " TEXT,"
    		+ KEY_TIMER + " INTEGER,"
            + KEY_ON + " datetime," 
    		+ KEY_STATUS+" INTEGER"
            +")";  
    
    // Result table create statement
    private static final String CREATE_TABLE_RESULT = "CREATE TABLE "
            + TABLE_RESULT + "(" 
    		+ KEY_RES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
            + KEY_TOPIC_ID+ " INTEGER," 
    		+ KEY_OPTION + " TEXT"           
            +")";  
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_VOTE);	
		db.execSQL(CREATE_TABLE_RESULT);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULT);	
		onCreate(db);
	}
	
	/*
	 * Creating a item
	 */
	public long createTopic(Votes vote) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_TOPIC, vote.getTopic());
	    values.put(KEY_CHOICE1, vote.getChoice1());	
	    values.put(KEY_CHOICE2, vote.getChoice2());
	    values.put(KEY_CHOICE3, vote.getChoice3());
	    values.put(KEY_CHOICE4, vote.getChoice4());
	    values.put(KEY_TIMER, vote.getTimer());
	    values.put(KEY_ON, getDateTime());
	    values.put(KEY_STATUS, vote.getStatus());
	    // insert row
	    long topicid = db.insert(TABLE_VOTE, null, values);	 
	    return topicid;
	}
	
	public long createVote(int topic,String option){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TOPIC_ID, topic);
		values.put(KEY_OPTION, option);
		long voteid = db.insert(TABLE_RESULT, null, values);
		return voteid;
	}
	
	public long getVoteCount(){
		SQLiteDatabase db = this.getWritableDatabase();			
		String selectQuery = "SELECT COUNT(*) FROM " + TABLE_VOTE;
		return DatabaseUtils.longForQuery(db, selectQuery, null);
	}

	
	public List<Votes> getAllVotes() {
	    List<Votes> votes = new ArrayList<Votes>();
	    String selectQuery = "SELECT  * FROM " + TABLE_VOTE+" ORDER BY "+KEY_ID;    
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	        	Votes vote = new Votes();
	            vote.setId(c.getInt(c.getColumnIndex(KEY_ID)));	            
	            vote.setTopic(c.getString(c.getColumnIndex(KEY_TOPIC)));
	            vote.setChoice1(c.getString(c.getColumnIndex(KEY_CHOICE1)));
	            vote.setChoice2(c.getString(c.getColumnIndex(KEY_CHOICE2)));
	            vote.setChoice3(c.getString(c.getColumnIndex(KEY_CHOICE3)));
	            vote.setChoice4(c.getString(c.getColumnIndex(KEY_CHOICE4)));
	            vote.setCreatedon(c.getString(c.getColumnIndex(KEY_ON)));	            
	            vote.setTimer(c.getInt(c.getColumnIndex(KEY_TIMER)));	 
	            vote.setStatus(c.getInt(c.getColumnIndex(KEY_STATUS)));
	            votes.add(vote);
	        } while (c.moveToNext());
	    }
	 
	    return votes;
	}
	
	public List<Result> getResults(int topicid) {
	    List<Result> results = new ArrayList<Result>();
	    String selectQuery = "SELECT R.option,COUNT(R.option) votecount FROM VOTE V,RESULT R WHERE V.id=R.topicid AND R."+KEY_TOPIC_ID+"='"+topicid+"' GROUP BY R.option";
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	        	Result result = new Result();
	        	result.setOption(c.getString(c.getColumnIndex(KEY_OPTION)));
	        	result.setVoteCount(c.getInt(c.getColumnIndex("votecount")));
	        	results.add(result);
	        } while (c.moveToNext());
	    }
	 
	    return results;
	} 	
	
	public String getTopicName(int topicid){
		String topicName = "";
		String selectQuery = "SELECT "+KEY_TOPIC+" FROM VOTE WHERE "+KEY_ID+"='"+topicid+"'";
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);		
	    if (c.moveToFirst()) {
	    	topicName = c.getString(c.getColumnIndex(KEY_TOPIC));
	    }
	    return topicName;
	}


	public List<Votes> getAVote(String id) {
	    List<Votes> votes = new ArrayList<Votes>();
	    String selectQuery = "SELECT  * FROM " + TABLE_VOTE+" WHERE "+KEY_ID+"='"+id+"'";	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	            Votes vote = new Votes();
	            vote.setId(c.getInt(c.getColumnIndex(KEY_ID)));	            
	            vote.setTopic(c.getString(c.getColumnIndex(KEY_TOPIC)));
	            vote.setChoice1(c.getString(c.getColumnIndex(KEY_CHOICE1)));
	            vote.setChoice2(c.getString(c.getColumnIndex(KEY_CHOICE2)));
	            vote.setChoice3(c.getString(c.getColumnIndex(KEY_CHOICE3)));
	            vote.setChoice4(c.getString(c.getColumnIndex(KEY_CHOICE4)));
	            vote.setCreatedon(c.getString(c.getColumnIndex(KEY_ON)));	            
	            vote.setTimer(c.getInt(c.getColumnIndex(KEY_TIMER)));	 
	            vote.setStatus(c.getInt(c.getColumnIndex(KEY_STATUS)));
	            votes.add(vote);
	        } while (c.moveToNext());
	    }
	 
	    return votes;
	} 


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm", Locale.getDefault());
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
