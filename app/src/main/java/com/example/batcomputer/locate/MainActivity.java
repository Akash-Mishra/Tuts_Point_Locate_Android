package com.example.batcomputer.locate;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import java.util.Locale;

public class MainActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

    LocationClient mLocationClient;
    private TextView addressLabel;
    private TextView locationLabel;
    private Button getLocationBtn;
    private Button disconnectBtn;
    private Button connectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationLabel = (TextView) findViewById(R.id.locationLabel);
        addressLabel = (TextView) findViewById(R.id.addressLabel);
        getLocationBtn = (Button) findViewById(R.id.getLocation);

        getLocationBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                mLocationClient.disconnect();
                locationLabel.setText("Got Disconnected....");
            }
        });

        connectBtn = (Button) findViewById(R.id.connect);
        connectBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
            public void onClick(View view) {
                mLocationClient.connect();
                locationLabel.setText("Got Connected....");
            }
        });

        //create the LocationRequest Object
        mLocationClient = new LocationClient(this, this, this);
    }

    @Override
    protected void onStart(){
        super.onStart();

        //connect the client
        mLocationClient.connect();
        locationLabel.setText("Got Connected....");
    }

    @Override
    protected void onStop() {
        // Disconnect the client
        mLocationClient.disconnect();
        super.onStop();
        locationLabel.setText("Got disconnected....");
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        //Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisconnected(){
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please Reconnect.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // display the error code on failure
        Toast.makeText(this, "Connection Failure : " + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }

    public void displayCurrentLocation() {
        // Get the current location's latitude and longitude
        Location currentLocation = mLocationClient.getLastLocation();
        String msg = "Current Location: " + Double.toString(currentLocation.getLatitude()) + " , " + Double.toString(currentLocation.getLongitude());

        //Display the current location in ui
        locationLabel.setText(msg);

        // To display the Current address in the UI
        (new GetAddressTask(this)).execute(currentLocation);
    }

//    following is a Subclass of the Asynctask which has been used to get address corresponding to
//            the given latitude and longitude

    private class GetAddressTask extends AsyncTask<Location, Void, String>{
        Context mContext;

        public GetAddressTask( Context context){
            super();
            mContext = context;
        }

//        when the task finishes, onPostExecute() displays the address

        @Override
        protected void onPostExecute(String address) {
            // Display the current address in the ui
            addressLabel.setText(address);
        }

        @Override
        protected String doInBackground(Location... params) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

            //Get the current location from the input parameter list
            Location loc = params[0];

            //create a list to contain the result address
            <Address> addresses = null;
        }

    }
}
