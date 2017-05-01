package com.ssatyacc.ixigo.ixigoflights.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ssatyacc.ixigo.ixigoflights.R;
import com.ssatyacc.ixigo.ixigoflights.adapter.BookingOptionsAdapter;
import com.ssatyacc.ixigo.ixigoflights.databinding.ActivityFlightInfoBinding;
import com.ssatyacc.ixigo.ixigoflights.model.Fare;
import com.ssatyacc.ixigo.ixigoflights.model.Flight;
import com.ssatyacc.ixigo.ixigoflights.model.Response;
import com.ssatyacc.ixigo.ixigoflights.utils.AirlineUtils;
import com.ssatyacc.ixigo.ixigoflights.utils.Constants;

public class FlightInfoActivity extends AppCompatActivity implements BookingOptionsAdapter.Listener {
    private static final String TAG = "FlightInfoActivity";
    private static final String FLIGHT = "FLIGHT";
    private static final String RESPONSE = "RESPONSE";

    ActivityFlightInfoBinding B;

    private Flight mFlight;
    private Response mResponse;

    /**
     * Returns an intent using which activity can be started. Using this approach the private
     * fields like FLIGHT and RESPONSE need not be exposed to other acivities
     * @param context
     * @param flight
     * @param response
     * @return
     */
    public static Intent getCallingIntent(Context context, Flight flight, Response response) {
        Intent intent = new Intent(context, FlightInfoActivity.class);
        intent.putExtra(FLIGHT, flight);
        intent.putExtra(RESPONSE, response);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_flight_info);

        mFlight = (Flight) getIntent().getSerializableExtra(FLIGHT);
        mResponse = (Response) getIntent().getSerializableExtra(RESPONSE);
        setToolbar();
        bindData();
    }

    private void setToolbar() {
        setTitle(mFlight.getOriginCode() + " - " + mFlight.getDestinationCode());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Bind the data from flight and response in the layout
     * Alternatively flight, response can be databinded to layout, but it would result in more unmanageable code
     */
    private void bindData() {
        if (AirlineUtils.airlines.get(mFlight.getAirlineCode()).photoURL == 0) {
            Picasso.with(FlightInfoActivity.this).load(Constants.PLANE_URL).into(B.airlinesLogo);
        } else {
            //
        }

        String sourceName = mResponse.getAppendix().getAirportCodeMapping().get(mFlight.getOriginCode());
        String destinationName = mResponse.getAppendix().getAirportCodeMapping().get(mFlight.getDestinationCode());
        B.sourceDestinationText.setText(sourceName + " - " + destinationName);

        String airlines = mResponse.getAppendix().getAirlineCodeMapping().get(mFlight.getAirlineCode());
        B.flightInfo.setText(airlines + " " + mFlight.getAirlineCode() + " - " + mFlight.getSeat());

        B.source.setText(mFlight.getOriginCode() + " " + Constants.HOURS_FORMAT.format(mFlight.getDepartureTime()));
        B.destination.setText(mFlight.getDestinationCode() + " " + Constants.HOURS_FORMAT.format(mFlight.getArrivalTime()));

        B.sourceName.setText(mResponse.getAppendix().getAirportCodeMapping().get(mFlight.getOriginCode()));
        B.destinationName.setText(mResponse.getAppendix().getAirportCodeMapping().get(mFlight.getDestinationCode()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        B.bookingOptionsList.setLayoutManager(layoutManager);
        B.bookingOptionsList.setAdapter(new BookingOptionsAdapter(mFlight, mResponse, this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(B.bookingOptionsList.getContext(), layoutManager.getOrientation());
        B.bookingOptionsList.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onBookNowClicked(Fare fare) {
        Toast.makeText(this, "Booking Initiated!", Toast.LENGTH_LONG).show();
        finish();
    }
}
