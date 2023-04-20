package com.example.trainingapp.Activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainingapp.Classe.Statistiques;
import com.example.trainingapp.DataBase.MatchDatabaseHelper;
import com.example.trainingapp.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivitySaveStats extends AppCompatActivity {

    private Spinner mSpinnerMatch;
    private EditText mButsMarques;
    private EditText mButsEncaisses;
    private EditText mPossession;
    private EditText mTirs;
    private EditText mTirsCadres;
    private Button mSaveButton;


    private List<Integer> mMatchIds = new ArrayList<>();
    private List<String> mMatchNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_stats);

        MatchDatabaseHelper dbHelper = new MatchDatabaseHelper(this);


        mSpinnerMatch = findViewById(R.id.spinner_match);
        mButsMarques = findViewById(R.id.edittext_buts_marques);
        mButsEncaisses = findViewById(R.id.edittext_buts_encaisses);
        mPossession = findViewById(R.id.edittext_possession);
        mTirs = findViewById(R.id.edittext_tirs);
        mTirsCadres = findViewById(R.id.edittext_tirs_cadres);
        mSaveButton = findViewById(R.id.button_save);

// Charger les matchs depuis la base de données MySQL
        // Charger les matchs depuis la base de données MySQL
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/trainingapp", "root", "password");
                    String sql = "SELECT * FROM Matchs";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int matchId = resultSet.getInt("Id");
                        String matchName = resultSet.getString("Adresse");
                        mMatchIds.add(matchId);
                        mMatchNames.add(matchName);
                    }
                    resultSet.close();
                    statement.close();
                    connection.close();

                    // Mettre à jour l'adaptateur du spinner sur le thread principal
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivitySaveStats.this, android.R.layout.simple_spinner_dropdown_item, mMatchNames);
                            mSpinnerMatch.setAdapter(adapter);
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        // Enregistrer les statistiques lorsque le bouton "Enregistrer" est cliqué
        mSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int id = mMatchIds.get(mSpinnerMatch.getSelectedItemPosition());
                String adresseM = mMatchNames.get(mSpinnerMatch.getSelectedItemPosition());
                int butsMarques = Integer.parseInt(mButsMarques.getText().toString());
                int butsEncaisses = Integer.parseInt(mButsEncaisses.getText().toString());
                double possession = Double.parseDouble(mPossession.getText().toString());
                int tirs = Integer.parseInt(mTirs.getText().toString());
                int tirsCadres = Integer.parseInt(mTirsCadres.getText().toString());

                Statistiques stats = new Statistiques(adresseM, butsMarques, butsEncaisses, possession, tirs, tirsCadres);

              //  Enregistrer les statistiques dans la base de données SQLite
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                dbHelper.insertStatistiques(stats, db);
                db.close();

                // Enregistrer les statistiques dans la base de données MySQL
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/trainingapp", "root", "password");
                            String sql = "INSERT INTO Statistiques (MatchId, ButsMarques, ButsEncaisses, Possession, Tirs, TirsCadres) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)";
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.setInt(1,id);
                            statement.setInt(2, stats.getButsMarques());
                            statement.setInt(3, stats.getButsEncaisses());
                            statement.setDouble(4, stats.getPossession());
                            statement.setInt(5, stats.getTirs());
                            statement.setInt(6, stats.getTirsCadres());
                            statement.executeUpdate();
                            connection.close();


                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

        });
        }


}


