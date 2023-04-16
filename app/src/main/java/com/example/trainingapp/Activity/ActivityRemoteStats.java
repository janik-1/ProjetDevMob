package com.example.trainingapp.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainingapp.DataBase.MatchDatabaseHelper;
import com.example.trainingapp.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ActivityRemoteStats extends AppCompatActivity {

    private Spinner mSpinner;
    private TextView mTextView;

    private List<Integer> mMatchIds;
    private List<String> mMatchNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_stats);

        mSpinner = findViewById(R.id.spinner);
        mTextView = findViewById(R.id.text_view_stats);

        mMatchIds = new ArrayList<>();
        mMatchNames = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/trainingapp", "root", "admin95");

                    String sql = "SELECT * FROM Matchs";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int matchId = resultSet.getInt("Id");
                        String matchName = resultSet.getString("Adresse");

                        mMatchIds.add(matchId);
                        mMatchNames.add(matchName);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityRemoteStats.this, android.R.layout.simple_spinner_dropdown_item, mMatchNames);
                            mSpinner.setAdapter(adapter);

                            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    int matchId = mMatchIds.get(position);
                                    displayMatchStats(matchId);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void displayMatchStats(int matchId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/trainingapp", "root", "admin95");

                    String sql = "SELECT * FROM Statistiques WHERE MatchId=?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, matchId);
                    ResultSet resultSet = statement.executeQuery();

                    final StringBuilder statsBuilder = new StringBuilder();
                    while (resultSet.next()) {
                        int goalsScored = resultSet.getInt("ButsMarques");
                        int goalsConceded = resultSet.getInt("ButsEncaisses");
                        double possession = resultSet.getDouble("Possession");
                        int shots = resultSet.getInt("Tirs");
                        int shotsOnTarget = resultSet.getInt("TirsCadres");

                        statsBuilder.append(String.format("Goals scored: %d\n", goalsScored));
                        statsBuilder.append(String.format("Goals conceded: %d\n", goalsConceded));
                        statsBuilder.append(String.format("Possession: %.1f%%\n", possession));
                        statsBuilder.append(String.format("Shots: %d\n", shots));
                        statsBuilder.append(String.format("Shots on target: %d\n\n", shotsOnTarget));
                    }

                    // Mettre à jour l'interface utilisateur sur le thread principal
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Afficher les statistiques dans le TextView
                            mTextView.setText(statsBuilder.toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
