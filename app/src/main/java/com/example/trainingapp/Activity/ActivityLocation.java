package com.example.trainingapp.Activity;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.trainingapp.R;
import com.example.trainingapp.DataBase.MatchDatabaseHelper;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityLocation extends FragmentActivity {

    private MapView mapView;
    private Connection connection;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Configuration.getInstance().load(getApplicationContext(), androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_location);

        mapView = (MapView) findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
      //  mapView.setBuiltInZoomControls(true);
      mapView.setMultiTouchControls(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/trainingapp", "root", "admin95");
                    String sql = "SELECT * FROM Matchs";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();
                    final List<Marker> markers = new ArrayList<Marker>(); // Create a list to hold the markers
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("Latitude");
                        double longitude = resultSet.getDouble("Longitude");
                        String address = getAddress(latitude, longitude);

                        // Add a marker for the match
                        GeoPoint matchLocation = new GeoPoint(latitude, longitude);
                        Marker marker = new Marker(mapView);
                        marker.setPosition(matchLocation);
                        marker.setTitle(address);
                        markers.add(marker); // Add the marker to the list
                    }
                    resultSet.close();
                    statement.close();
                    connection.close();

                    // Post a Runnable to the UI thread to add the markers to the map and set the zoom level
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MapController mapController = (MapController) mapView.getController();
                            if (markers.size() > 0) {
                                // Create a bounding box that contains all the markers
                                List<GeoPoint> geoPoints = new ArrayList<>();
                                for (Marker marker : markers) {
                                    geoPoints.add(marker.getPosition());
                                }
                                BoundingBox boundingBox = BoundingBox.fromGeoPoints(geoPoints);


                                // Set the zoom level so that the bounding box fits on the screen
                                mapView.zoomToBoundingBox(boundingBox, true);

                                // Center the map on the bounding box
                                mapController.setCenter(boundingBox.getCenterWithDateLine());
                            }
                            for (Marker marker : markers) {
                                mapView.getOverlays().add(marker);
                            }
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * Returns the street address for the given latitude and longitude using the Geocoder API.
     */
    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            Log.e("ActivityLocation", "Error getting address for location " + latitude + ", " + longitude, e);
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database when the activity is destroyed
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(), androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}

