package com.example.trainingapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.example.trainingapp.Classe.Match;
import com.example.trainingapp.Classe.Statistiques;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MatchDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "match.db";

    private boolean isThreadExecuted = false;

    public MatchDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public  void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS Statistiques");
        db.execSQL("DROP TABLE IF EXISTS Matchs");

      /*  db.execSQL("CREATE TABLE IF NOT EXISTS Matchs (" +
                "Id INTEGER PRIMARY KEY, " +
                "Date TEXT NOT NULL, " +
                "Adresse TEXT NOT NULL, " +
                "Latitude REAL NOT NULL, " +
                "Longitude REAL NOT NULL, " +
                "Image TEXT" +
                ");"); // créer une table equipe et mettre des equipes dedans + dans l'appli mettre formulaire création equipe/stats/matchs
*/
        db.execSQL("CREATE TABLE IF NOT EXISTS Statistiques (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "AdresseM TEXT NOT NULL, " +
                "ButsMarques INTEGER NOT NULL, " +
                "ButsEncaisses INTEGER NOT NULL, " +
                "Possession REAL NOT NULL, " +
                "Tirs INTEGER NOT NULL, " +
                "TirsCadres INTEGER NOT NULL" +
                ");"); //voir si ajouter nom équipes.

    }




    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Statistiques");
        db.execSQL("DROP TABLE IF EXISTS Matchs");
        onCreate(db);
    }

 /*   public void insertMatch(Match match, SQLiteDatabase db) {
            ContentValues values = new ContentValues();
              values.put("Id", match.getId());
            values.put("Date", match.getDate());
            values.put("Adresse", match.getAdresse());
            values.put("Latitude", match.getLatitude());
            values.put("Longitude", match.getLongitude());
            values.put("Image", match.getImage());

            //long id =
            db.insert("Matchs", null, values);
            //match.setId((int) id);
            //System.out.println(id + "match");
            //return id;


    }*/




    public void insertStatistiques(Statistiques stats, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("AdresseM", stats.getAdresseM());
        values.put("ButsMarques", stats.getButsMarques());
        values.put("ButsEncaisses", stats.getButsEncaisses());
        values.put("Possession", stats.getPossession());
        values.put("Tirs", stats.getTirs());
        values.put("TirsCadres", stats.getTirsCadres());

     db.insert("Statistiques", null, values);

        // Si la table contient plus de 5 lignes, supprime la plus ancienne ligne
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Statistiques", null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 5) {
                db.execSQL("DELETE FROM Statistiques WHERE Id = (SELECT MIN(Id) FROM Statistiques)");
            }
            cursor.close();
        }
    }







}



