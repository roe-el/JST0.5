package com.example.aaronfriesenhahn.jst05;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Circle;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
//TODO:Switch lat and long in all the code!

public class JSTMap0 extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
private GoogleMap mMap;

private final String LOG_TAG ="TestApp";
protected double clientLat;
protected double clientLng;
private GoogleApiClient mGoogleApiClient;
private LocationRequest mLocationRequest;
private String lat1;
private String lng1;
protected Location mLastLocation;

private String API_KEY = "HKM7SOBdxgPaW9hVNgquUU0IWYP7sv";
private String coordinatesID = "56d5d69d7625424120f93ca2";
private String coords;



public void getCoords() {
    (new UbidotsClient()).handleUbidots(coordinatesID, API_KEY, new UbidotsClient.UbiListener() {
        @Override
        public void onDataReady(List<UbidotsClient.Value> result) {
            Log.d("Coords", "======== On data Ready ===========");

            List<Entry> entries = new ArrayList();


            Entry be = new Entry(result.get(0).value, 0);
            entries.add(be);
            Log.d("Data", be.toString());

            final String con = result.get(0).context;


            Handler handler = new Handler(JSTMap0.this.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    coords = con;
                    //"{"Lat": "  27.495609", "Long": " -97.870003"}"
                    StringTokenizer tokens = new StringTokenizer(coords, "\"");
                    String first = tokens.nextToken();
                    String second = tokens.nextToken();
                    String third = tokens.nextToken();

                    String lat2 = tokens.nextToken();//-97
                    lat1 = lat2;//.replace("\"", "");
                    String fifth = tokens.nextToken();
                    String sixth = tokens.nextToken();
                    String seventh = tokens.nextToken();

                    String lng2 = tokens.nextToken();//27
                    lng1 = lng2;//.replace("\"", "");

                    //"{"Lat": "  27.495609", "Long": " -97.870003"}"

                    double lat = Double.parseDouble(lat1);
                    double lng = Double.parseDouble(lng1);
                }
            });

        }
    });


}

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_jstmap0);
    buildGoogleApiClient();



    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
}
protected synchronized void buildGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
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




    // Marker for College Hall and move the camera
   // LatLng busLocation = new LatLng(lng, lat);
    //mMap.addMarker(new MarkerOptions().position(busLocation).title("College Hall"));
    //center of camera and zoom

    
    // Markers for the stops on the blue route of the Blue and Gold Express routes (routes for left side of campus)
    //Jernigan Library
    LatLng Jernigan_Library = new LatLng(27.525628, -97.882072);
    //adds marker for position
    //mMap.addMarker(new MarkerOptions().position(Jernigan_Library).title("Jernigan library"));

    //Javelina Station
    LatLng Javelina_Station = new LatLng(27.530239, -97.883928);
    //mMap.addMarker(new MarkerOptions().position(Javelina_Station).title("Javelina Station"));

    //Business Building
    LatLng Business_Building = new LatLng(27.527186, -97.882553);
    //mMap.addMarker(new MarkerOptions().position(Business_Building).title("Business Building"));

    //Mesquite Village
    LatLng Mesquite_Village = new LatLng(27.526586, -97.884106);
    //mMap.addMarker(new MarkerOptions().position(Mesquite_Village).title("Mesquite Village"));

    // Turner Bishop Hall
    LatLng Turner_Bishop = new LatLng(27.523646, -97.884196);
    //mMap.addMarker(new MarkerOptions().position(Turner_Bishop).title("Turner Bishop Hall"));

    //circle "marker"s
    Circle Turner_Bishop_circle = googleMap.addCircle(new CircleOptions()
                    .center(Turner_Bishop).radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Mesquite_Village_circle = googleMap.addCircle(new CircleOptions()
                    .center(Mesquite_Village).radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Business_Building_circle = googleMap.addCircle(new CircleOptions()
                    .center(Business_Building).radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Javelina_Station_circle = googleMap.addCircle(new CircleOptions()
                    .center(Javelina_Station).radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Jernigan_Library_circle = googleMap.addCircle(new CircleOptions()
                    .center(Jernigan_Library).radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );

    // Markers for the stops on the gold route of the Blue and Gold Express routes (routes for right side of campus)
    //W Ave C Parking Lot
    LatLng W_Ave_C_Parking_Lot = new LatLng(27.527511, -97.878625);

    //Steinke Gym Parking Lot
    LatLng Steinke_Gym_Parking_Lot = new LatLng(27.526617, -97.878936);

    //Kleberg Hall
    LatLng Kleberg_Hall = new LatLng(27.526379, -97.881028);

    //Health and Rec Center
    LatLng Health_Rec_Center_Parking_Lot = new LatLng(27.525152, -97.879977);

    //Manning Hall Parking Lot
    LatLng Manning_Hall_Parking_Lot = new LatLng(27.523675, -97.878681);

    //Student Union Building
    LatLng Student_Union_Building = new LatLng(27.523337, -97.882248);

    //Gold Bus route start
    LatLng Gold_Route_Start = new LatLng(27.530368, -97.881767);

    //yellow circle markers
    Circle W_Ave_C_circle = googleMap.addCircle(new CircleOptions().center(W_Ave_C_Parking_Lot)
                    .radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Steinke_Gym_circle = googleMap.addCircle(new CircleOptions().center(Steinke_Gym_Parking_Lot)
                    .radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Health_and_Rec_circle = googleMap.addCircle(new CircleOptions().center(Health_Rec_Center_Parking_Lot)
                    .radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Manning_Hall_cirlce = googleMap.addCircle(new CircleOptions().center(Manning_Hall_Parking_Lot)
                    .radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Student_Union_circle = googleMap.addCircle(new CircleOptions().center(Student_Union_Building)
                    .radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );
    Circle Gold_start_circle = googleMap.addCircle(new CircleOptions().center(Gold_Route_Start)
                    .radius(10).strokeColor(Color.BLACK).fillColor(Color.RED)
    );


    //bus routes
    //blue bus route (left side of campus)
    Polyline blueroute = mMap.addPolyline(new PolylineOptions()
            .add(Javelina_Station).add(new LatLng(27.530249, -97.882496))
            .add(new LatLng(27.526500, -97.882464)).add(new LatLng(27.526462, -97.885661))
            .add(new LatLng(27.523646, -97.885634)).add(Turner_Bishop).add(new LatLng(27.525121, -97.884175))
                    //start of loop around College Hall
            .add(new LatLng(27.525121, -97.883102)).add(new LatLng(27.524759, -97.882877))
            .add(new LatLng(27.524578, -97.882405)).add(new LatLng(27.524854, -97.881911))
            .add(new LatLng(27.525187, -97.881782)).add(Jernigan_Library)
            .add(new LatLng(27.525958, -97.882436)).add(new LatLng(27.526491, -97.882436))
            .width(5)
            .color(Color.BLUE));

    //gold bus route (right side of campus)
    Polyline goldroute = mMap.addPolyline(new PolylineOptions()
            .add(new LatLng(27.530365, -97.881690)).add(new LatLng(27.530253, -97.882495))
            .add(new LatLng(27.527342, -97.882441)).add(new LatLng(27.527528, -97.877908))
            .add(new LatLng(27.526543, -97.877881)).add(new LatLng(27.526500, -97.881202))
            .add(new LatLng(27.525168, -97.881181)).add(new LatLng(27.525206, -97.877844))
            .add(new LatLng(27.525206, -97.877844)).add(new LatLng(27.523465, -97.877820))
            .add(new LatLng(27.523518, -97.878992)).add(new LatLng(27.522571, -97.878997))
            .add(new LatLng(27.522557, -97.880842)).add(new LatLng(27.522614, -97.881159))
            .add(new LatLng(27.522590, -97.882237))
                    //start of loop around College Hall
            .add(new LatLng(27.524598, -97.882312)).add(new LatLng(27.524807, -97.881953))
            .add(new LatLng(27.525154, -97.881797)).add(new LatLng(27.525549, -97.881963))
            .add(new LatLng(27.525723, -97.882428)).add(new LatLng(27.530253, -97.882495))
            .width(5)
            .color(Color.YELLOW));


}
@Override
protected void onStart() {
    super.onStart();
    // Connect the client.
    mGoogleApiClient.connect();
}

@Override
protected void onStop() {
    super.onStop();
    // Disconnecting the client invalidates it.
    if (mGoogleApiClient.isConnected()) {
        mGoogleApiClient.disconnect();
    }
}

@Override
public void onConnected(Bundle connectionHint) {

    mLocationRequest = LocationRequest.create();
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    mLocationRequest.setInterval(10); // Update location every second

    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

}

@Override
public void onConnectionSuspended(int i) {
    Log.i(LOG_TAG, "GoogleApiClient connection has been suspend");
    mGoogleApiClient.connect();
}

@Override
public void onConnectionFailed(ConnectionResult connectionResult) {
    // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
    // onConnectionFailed.
    Log.i(LOG_TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
}

@Override
public void onLocationChanged(Location location) {
    Log.i(LOG_TAG, location.toString());
    //txtOutput.setText(location.toString());

    clientLat=mLastLocation.getLatitude();
    clientLng=mLastLocation.getLongitude();
}


}
