package com.example.gettinguserlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
int permissionint=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FusedLocationProviderClient fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        //Fused ->Api Of Google such that it send data

        if(permissionGranted()){
            if(locationManager()){

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null){
                            String lat=String.valueOf(location.getLatitude());
                            String lon=String.valueOf(location.getLongitude());
                            Toast.makeText(MainActivity.this, "lat :"+lat+"long"+lon, Toast.LENGTH_SHORT).show();
                            Log.i("location","lat :"+lat+"long"+lon);
                        }
                    }
                });
            }

        }else{
         requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},permissionint);
        }

    }
    public boolean permissionGranted(){ //Permission of location
        //We use (this Keyword) with Activity
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED;

    }
    public boolean locationManager(){ //Permission of gps,wifi network
        LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        Log.i("locationManger",String.valueOf(locationManager));
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}