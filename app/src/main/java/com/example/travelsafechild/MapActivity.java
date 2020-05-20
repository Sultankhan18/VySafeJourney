package com.example.travelsafechild;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //Initializing variable here
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;


    @Override
    //Initializing onMapready method
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        //Assigning variable here
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);


        //Initializing fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        //Checking permission
        if (ActivityCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //As you fitch permission granted
            //Call method
            getCurrentLocation();
        }else {
            //When permission denied
            //Request permission
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private void getCurrentLocation() {
        //Initializing task location
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                //When success
                if(location != null){

                    //Sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                  public void onMapReady(GoogleMap googleMap) {
                           //Initializing lat lang
                             LatLng latLng = new LatLng(location.getLatitude()
                                            ,location.getLongitude());
                            //Create marker options
                            MarkerOptions options = new MarkerOptions().position(latLng).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.kidmarker))
                                         .title("Traveling safely toward the destination!!");

                                    // Implying of map zoom
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                             googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                             //Adding up MARKER on the map
                            googleMap.addMarker(options);

                        }
                    });
                }
            }
        });

    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When permission granted
                //Call method
                getCurrentLocation();
            }
        }
    }

}

