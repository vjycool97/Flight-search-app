package com.ssatyacc.ixigo.ixigoflights.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.ssatyacc.ixigo.ixigoflights.databinding.ItemFlightBinding;
import com.ssatyacc.ixigo.ixigoflights.model.Flight;
import com.ssatyacc.ixigo.ixigoflights.model.Response;
import com.ssatyacc.ixigo.ixigoflights.utils.AirlineUtils;
import com.ssatyacc.ixigo.ixigoflights.utils.Constants;

/**
 * Created by vijay on 4/4/17.
 */

public class FlightsListAdapter extends RecyclerView.Adapter<FlightsListAdapter.FlightViewHolder> {
    private static final String TAG = "FlightsListAdapter";

    public interface Listener {
        void onFlightSelected(Flight flight, Response response);
    }

    private Response response;
    private Listener listener;

    public FlightsListAdapter(Response response, Listener listener) {
        this.response = response;
        this.listener = listener;
    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFlightBinding binding = ItemFlightBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FlightViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FlightViewHolder holder, int position) {
        holder.bind(response.getFlights().get(position), listener, response);
    }

    @Override
    public int getItemCount() {
        return response.getFlights().size();
    }

    class FlightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemFlightBinding B;
        Flight mFlight;
        Listener mFlightSelectListener;
        Response mResponse;


        FlightViewHolder(ItemFlightBinding binding) {
            super(binding.getRoot());
            B = binding;
        }

        void bind(Flight flight, Listener listener, Response response) {
            this.mFlight = flight;
            mFlightSelectListener = listener;
            mResponse = response;

            if (AirlineUtils.airlines.get(flight.getAirlineCode()).photoURL != 0) {
                // TODO:  Set Service Provider images from local!
            } else {
                Picasso.with(B.getRoot().getContext()).load(Constants.PLANE_URL).into(B.airlinesLogo);
            }
            B.flightNumber.setText(flight.getAirlineCode());

            B.departureTime.setText(DateUtils.formatDateRange(B.getRoot().getContext(), flight.getDepartureTime(), flight.getArrivalTime(), 1));

            int minDiff = (int) ((flight.getArrivalTime() - flight.getDepartureTime()) / (1000 * 60));
            B.duration.setText("travel - " + minDiff / 60 + "h " + minDiff % 60 + "m");
            B.price.setText("₹" + String.valueOf(flight.getMinFare().getFare()));

            B.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFlightSelectListener.onFlightSelected(mFlight, mResponse);
        }
    }

    public void setResponse(Response response) {
        this.response = response;
        notifyDataSetChanged();
    }
}
