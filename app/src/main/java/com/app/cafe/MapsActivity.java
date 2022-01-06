package com.app.cafe;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.app.cafe.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button btnBack,btnTim, btnChiDuong;
    private TextView txtTime, txtDistance;
    private EditText edtDiemBatDau, edtDiemKetThuc;
    Location gps_loc;
    Location network_loc;
    Location final_loc;
    private double longitude;
    private double latitude;


    private List<Marker> originMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtTime = findViewById(R.id.txtThoiGian);
        txtDistance = findViewById(R.id.txtKhoangCach);
        btnBack = findViewById(R.id.btnBack);
        btnTim = findViewById(R.id.btnTim);
        btnChiDuong = findViewById(R.id.btnChiDuong);
        edtDiemBatDau = findViewById(R.id.edtDiemDi);
        edtDiemKetThuc = findViewById(R.id.edtDiemDen);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ve = new Intent(getApplicationContext(), trangchu.class);
                startActivity(ve);
            }
        });
        btnChiDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendRequest();
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera


//        LatLng duyTan = new LatLng(latitude, longitude);
        LatLng nha = new LatLng(16.059928, 108.209772);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nha, 20));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Đại học Duy Tân")
                .position(nha)));

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {

            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gps_loc != null) {
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        } else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        } else {
            latitude = 0.0;
            longitude = 0.0;
        }

        mMap.setMyLocationEnabled(true);

//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(duyTan)
//                .zoom(17)
//                .bearing(90)
//                .tilt(30)
//                .build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        mMap.addMarker(new MarkerOptions().title("Địa chỉ hiện tại"));

//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(duyTan, 15));
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(duyTan))


    }


}