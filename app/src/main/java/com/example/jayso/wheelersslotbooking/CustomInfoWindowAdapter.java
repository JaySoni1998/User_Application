package com.example.jayso.wheelersslotbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.bottome_sheet, null);
    }

    private void rendowWindowText(Marker marker, View view){

        String mainLocation = marker.getTitle();
        TextView main_location =  view.findViewById(R.id.main_location);

        if(!mainLocation.equals("")){
            main_location.setText(mainLocation);
        }

        String address = marker.getSnippet();
        TextView place_address =  view.findViewById(R.id.place_address);

        /*if(!address.equals("")){
            place_address.setText(address);
        }*/
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}