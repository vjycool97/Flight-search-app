package com.ssatyacc.ixigo.ixigoflights.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;

import com.ssatyacc.ixigo.ixigoflights.R;
import com.ssatyacc.ixigo.ixigoflights.adapter.FlightsListAdapter;
import com.ssatyacc.ixigo.ixigoflights.async.GetFlightDetails;
import com.ssatyacc.ixigo.ixigoflights.databinding.ActivityFlightOptionsBinding;
import com.ssatyacc.ixigo.ixigoflights.model.Flight;
import com.ssatyacc.ixigo.ixigoflights.model.Response;
import com.ssatyacc.ixigo.ixigoflights.utils.comparators.FlightArrivalTimeComparator;
import com.ssatyacc.ixigo.ixigoflights.utils.comparators.FlightDepartureTimeComparator;
import com.ssatyacc.ixigo.ixigoflights.utils.comparators.FlightDurationComparator;
import com.ssatyacc.ixigo.ixigoflights.utils.comparators.FlightPriceComparator;

import java.util.Collections;

public class FlightOptionsActivity extends AppCompatActivity implements GetFlightDetails.Listener, FlightsListAdapter.Listener {
    private static final String TAG = "FlightOptionsActivity";

    private ActivityFlightOptionsBinding B;
    private ProgressDialog progressDialog;
    private Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_flight_options);

        setToolbar();
        setTabs();

        new GetFlightDetails(this).execute();
    }

    /**
     * Tabs for soritng data based on price, arrival and departure times.
     * Hardcoded 0,1,2 can be eliminated but weren't because enums are inefficient!
     */
    private void setTabs() {
        B.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (response == null) {
                    Log.d(TAG, "onTabSelected: NULL response!");
                    return;
                }
                switch (tab.getPosition()) {
                    case 0:
                        Collections.sort(response.getFlights(), new FlightPriceComparator());
                        break;
                    case 1:
                        Collections.sort(response.getFlights(), new FlightDepartureTimeComparator());
                        break;
                    case 2:
                        Collections.sort(response.getFlights(), new FlightArrivalTimeComparator());
                        break;
                    case 3:
                        Collections.sort(response.getFlights(), new FlightDurationComparator());
                        break;
                }
                B.flights.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do Nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do Nothing
            }
        });

        B.tabs.addTab(B.tabs.newTab().setText("Cheap"));
        B.tabs.addTab(B.tabs.newTab().setText("Departure"));
        B.tabs.addTab(B.tabs.newTab().setText("Arrival"));
        B.tabs.addTab(B.tabs.newTab().setText("Duration"));
    }

    private void setToolbar() {
        setSupportActionBar(B.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("DEL - BOM");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Returns an intent using which activity can be started.
     *
     * @param context
     * @return
     */
    public static Intent getCallingIntent(Context context) {
        return new Intent(context, FlightOptionsActivity.class);
    }

    /**
     * On Success - set response param, configure recycler view if not already configured, reload data in adapter
     *
     * @param response
     */
    @Override
    public void onSuccess(Response response) {
        progressDialog.dismiss();

        FlightsListAdapter adapter = (FlightsListAdapter) B.flights.getAdapter();
        this.response = response;
        if (adapter == null) {
            B.flights.setAdapter(new FlightsListAdapter(response, FlightOptionsActivity.this));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            B.flights.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(B.flights.getContext(),
                    layoutManager.getOrientation());
            B.flights.addItemDecoration(dividerItemDecoration);
        } else {
            ((FlightsListAdapter) B.flights.getAdapter()).setResponse(response);
        }

        B.tabs.getTabAt(1).select();
    }

    /**
     * todo On Failure show internet error message and option to retry!
     * todo Better re-try automatically after waiting for 10 sec
     */
    @Override
    public void onFailure() {
        Log.d(TAG, "onFailure: ");
        progressDialog.dismiss();
    }

    /**
     * todo Show a in-layout loader instead of progress dialog?
     */
    @Override
    public void onStarted() {
        Log.d(TAG, "onStarted: ");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * Handle the event where flight is selected - Display all available prices for the plane
     *
     * @param flight
     * @param response
     */
    @Override
    public void onFlightSelected(Flight flight, Response response) {
        startActivity(FlightInfoActivity.getCallingIntent(this, flight, response));
    }
}
