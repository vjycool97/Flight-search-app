package com.ssatyacc.ixigo.ixigoflights.async;

import android.os.AsyncTask;
import android.util.Log;

import com.ssatyacc.ixigo.ixigoflights.model.Response;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by vijay on 4/4/17.
 */

public class GetFlightDetails extends AsyncTask<Object, Object, Response> {
    private static final String TAG = "GetFlightDetails";

    private static final String DATA_URL = "https://firebasestorage.googleapis.com/v0/b/gcm-test-6ab64.appspot.com/o/ixigoandroidchallenge%2Fsample_flight_api_response.json?alt=media&token=d8005801-7878-4f57-a769-ac24133326d6";

    private WeakReference<Listener> mListener;

    public GetFlightDetails(Listener listener) {
        mListener = new WeakReference<>(listener);
    }

    public interface Listener {
        void onSuccess(Response response);

        void onFailure();

        void onStarted();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");
        Listener listener = mListener.get();
        if (listener != null) {
            listener.onStarted();
        }
    }

    @Override
    protected Response doInBackground(Object... params) {
        int count;
        StringBuilder responseBuilder = new StringBuilder();
        try {
            URL url = new URL(DATA_URL);
            URLConnection conection = url.openConnection();
            conection.connect();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            while ((count = input.read()) != -1) {
                responseBuilder.append((char) count);
            }
            input.close();
            return new Response(new JSONObject(responseBuilder.toString()));

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        Listener listener = mListener.get();
        if (listener == null) {
            return;
        }

        if (response == null) {
            listener.onFailure();
            return;
        }

        listener.onSuccess(response);
    }
}
