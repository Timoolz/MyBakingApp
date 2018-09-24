package com.olamide.mybakingapp.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.olamide.mybakingapp.enums.ConnectionStatus;

import timber.log.Timber;


public final class ConnectionUtils {


    private ConnectionUtils() {

    }

    public static ConnectionStatus getConnectionStatus(Context context){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){

            Timber.e( "connection type  %s", activeNetwork.getType());

            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if(isWiFi){
                return  new  ConnectionStatus(ConnectionStatus.WIFI);
            }
            else {
                return new  ConnectionStatus(ConnectionStatus.MOBILE);
            }
        }else {
            return new  ConnectionStatus(ConnectionStatus.NONE);
        }


    }

}
