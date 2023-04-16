package com.example.trainingapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainingapp.Classe.Statistiques;
import com.example.trainingapp.DataBase.MatchDatabaseHelper;
import com.example.trainingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityLocalStats extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private List<Statistiques> mStatistiquesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_stats);

        // Ouverture de la base de données
        MatchDatabaseHelper dbHelper = new MatchDatabaseHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        // Récupération des 5 dernières statistiques
        String query = "SELECT * FROM Statistiques;";
        Cursor cursor = mDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Récupération des données de la ligne courante
                @SuppressLint("Range")String adresseM = cursor.getString(cursor.getColumnIndex("AdresseM"));                @SuppressLint("Range")int butsMarques = cursor.getInt(cursor.getColumnIndex("ButsMarques"));
                @SuppressLint("Range") int butsEncaisses = cursor.getInt(cursor.getColumnIndex("ButsEncaisses"));
                @SuppressLint("Range") double possession = cursor.getDouble(cursor.getColumnIndex("Possession"));
                @SuppressLint("Range")int tirs = cursor.getInt(cursor.getColumnIndex("Tirs"));
                @SuppressLint("Range")int tirsCadrés = cursor.getInt(cursor.getColumnIndex("TirsCadres"));

                // Création de l'objet Statistiques correspondant
                Statistiques stats = new Statistiques(adresseM, butsMarques, butsEncaisses, possession, tirs, tirsCadrés);
                mStatistiquesList.add(stats);
            } while (cursor.moveToNext());
        }
        cursor.close();
        mDatabase.close();



        // Affichage des statistiques dans des TextView
        if (!mStatistiquesList.isEmpty()) {

            Statistiques stats = mStatistiquesList.get(0);
            TextView match1TextView = findViewById(R.id.match1_text_view);
            match1TextView.setText("Match 1 : " + stats.getAdresseM() + "- Buts marqués : " + stats.getButsMarques() + ", Buts encaissés : " + stats.getButsEncaisses() + ", Possession : " + stats.getPossession() + "%, Tirs : " + stats.getTirs() + ", Tirs cadrés : " + stats.getTirsCadres());

            if (mStatistiquesList.size() >= 2) {

                stats = mStatistiquesList.get(1);
                TextView match2TextView = findViewById(R.id.match2_text_view);
                match2TextView.setText("Match 2 - Buts marqués : " + stats.getButsMarques() + ", Buts encaissés : " + stats.getButsEncaisses() + ", Possession : " + stats.getPossession() + "%, Tirs : " + stats.getTirs() + ", Tirs cadrés : " + stats.getTirsCadres());
            }

            if (mStatistiquesList.size() >= 3) {
                stats = mStatistiquesList.get(2);
                TextView match3TextView = findViewById(R.id.match3_text_view);
                match3TextView.setText("Match 3 - Buts marqués : " + stats.getButsMarques() + ", Buts encaissés : " + stats.getButsEncaisses() + ", Possession : " + stats.getPossession() + "%, Tirs : " + stats.getTirs() + ", Tirs cadrés : " + stats.getTirsCadres());
            }

            if (mStatistiquesList.size() >= 4) {
                stats = mStatistiquesList.get(3);
                TextView match4TextView = findViewById(R.id.match4_text_view);
                match4TextView.setText("Match 4 - Buts marqués : " + stats.getButsMarques() + ", Buts encaissés : " + stats.getButsEncaisses() + ", Possession : " + stats.getPossession() + "%, Tirs : " + stats.getTirs() + ", Tirs cadrés : " + stats.getTirsCadres());
            }

            if (mStatistiquesList.size() >= 5) {
                stats = mStatistiquesList.get(4);
                TextView match5TextView = findViewById(R.id.match5_text_view);
                match5TextView.setText("Match 5 - Buts marqués : " + stats.getButsMarques() + ", Buts encaissés : " + stats.getButsEncaisses() + ", Possession : " + stats.getPossession() + "%, Tirs : " + stats.getTirs() + ", Tirs cadrés : " + stats.getTirsCadres());
            }

        }

    }

    public void onViewAllButtonClick(View view) {
        Intent intent = new Intent(this, ActivityRemoteStats.class);
        startActivity(intent);
    }

}
