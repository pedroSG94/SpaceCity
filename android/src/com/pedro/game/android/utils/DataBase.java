package com.pedro.game.android.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pedro.game.utils.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by alumno on 15/02/16.
 */
public class DataBase extends SQLiteOpenHelper {

    private Context context;
    private DataBase db;
    private SQLiteDatabase sqliteDB;

    public DataBase(Context context) {
        super(context, "dbSpaceCity", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE scores(number INTEGER PRIMARY KEY, name TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dbSpaceCity");
        onCreate(db);
    }

    public void open(){
        db = new DataBase(context);
        sqliteDB = db.getWritableDatabase();
    }

    public void close(){
        sqliteDB.close();
    }

    public void insertScore(int score, String name) throws Exception{
        ContentValues values = new ContentValues();
        values.put("number", score);
        values.put("name", name);
        sqliteDB.insert("scores", null, values);
    }

    public ArrayList<Score> getScores() throws Exception{

        ArrayList<Score> listScore = new ArrayList<>();
        String[] columns = new String[]{"number", "name"};

        Cursor cursor = sqliteDB.query("scores", columns, null, null, null, null, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Score score = new Score();
            score.setScore(cursor.getInt(cursor.getColumnIndex("number")));
            score.setName(cursor.getString(cursor.getColumnIndex("name")));
            listScore.add(score);
        }
        cursor.close();

        /*sort the list (max to min) and get top 10 scores*/
        Collections.sort(listScore, new Comparator<Score>() {
            @Override public int compare(Score score1, Score score2) {
                return score2.getScore() - score1.getScore(); // Descending
            }

        });

        ArrayList<Score> topTen = new ArrayList<>();
        int cont = 0;
        for (Score i : listScore){
            topTen.add(i);
            cont++;
            if(cont >= 10)
                break;
        }

        return topTen;
    }
}
