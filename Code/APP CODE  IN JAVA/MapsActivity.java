package com.learn.kiit.shramseva;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import static com.learn.kiit.shramseva.property.Locationp;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String address;
    Location change;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 6000, locationListener);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        FancyToast.makeText(getApplicationContext(),"Long Press To Select Your Loaction",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                    if (listAddresses != null && listAddresses.size() > 0) {
                        String address1 = "";

                        if (listAddresses.get(0).getThoroughfare() != null) {
                            address1 += listAddresses.get(0).getThoroughfare() + " ";
                        }

                        if (listAddresses.get(0).getLocality() != null) {
                            address1 += listAddresses.get(0).getLocality() + " ";
                        }

                        if (listAddresses.get(0).getPostalCode() != null) {
                            address1 += listAddresses.get(0).getPostalCode() + " ";
                        }

                        if (listAddresses.get(0).getAdminArea() != null) {
                            address1 += listAddresses.get(0).getAdminArea();
                        }
                     //   Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();
                        Log.i("Address", address1);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 6000, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mMap.clear();
            LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation).title("Here Is Your Location!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));



            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {

                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting the position for the marker
                    markerOptions.position(latLng);

                    // Setting the title for the marker.
                    // This will be displayed on taping the marker
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                    // Clears the previously touched position
                    mMap.clear();

                    // Animating to the touched position
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    // Placing a marker on the touched position
                    mMap.addMarker(markerOptions);
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                    try {
                        List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                        if (listAddresses != null && listAddresses.size() > 0) {
                            address = "";

                            if (listAddresses.get(0).getThoroughfare() != null) {
                                address += listAddresses.get(0).getThoroughfare() + " ";
                            }

                            if (listAddresses.get(0).getLocality() != null) {
                                address += listAddresses.get(0).getLocality() + " ";
                            }

                            if (listAddresses.get(0).getPostalCode() != null) {
                                address += listAddresses.get(0).getPostalCode() + " ";
                            }

                            if (listAddresses.get(0).getAdminArea() != null) {
                                address += listAddresses.get(0).getAdminArea();
                            }

                            //Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("result",address);
                            setResult(RESULT_OK,intent);
                            finish();


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            });

        }

    }

}


