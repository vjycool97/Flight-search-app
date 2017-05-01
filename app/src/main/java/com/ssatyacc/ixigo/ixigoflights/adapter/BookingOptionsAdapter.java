package com.ssatyacc.ixigo.ixigoflights.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.ssatyacc.ixigo.ixigoflights.databinding.ItemBookingOptionBinding;
import com.ssatyacc.ixigo.ixigoflights.model.Fare;
import com.ssatyacc.ixigo.ixigoflights.model.Flight;
import com.ssatyacc.ixigo.ixigoflights.model.Response;
import com.ssatyacc.ixigo.ixigoflights.utils.Constants;

/**
 * Created by vijay on 4/4/17.
 */

public class BookingOptionsAdapter extends RecyclerView.Adapter<BookingOptionsAdapter.BookingOptionViewHolder> {
    private static final String TAG = "BookingOptionsAdapter";
    private Listener listener;
    private Flight flight;
    private Response response;

    public interface Listener {
        void onBookNowClicked(Fare fare);
    }

    public BookingOptionsAdapter(Flight flight, Response response, Listener listener) {
        this.listener = listener;
        this.flight = flight;
        this.response = response;
    }

    @Override
    public BookingOptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookingOptionBinding binding = ItemBookingOptionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookingOptionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BookingOptionViewHolder holder, int position) {
        holder.bind(response, flight.getFares().get(position), listener);
    }

    @Override
    public int getItemCount() {
        return flight.getFares().size();
    }

    class BookingOptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemBookingOptionBinding B;
        private Listener mListener;
        private Fare mFare;

        public BookingOptionViewHolder(ItemBookingOptionBinding binding) {
            super(binding.getRoot());
            B = binding;
        }

        void bind(Response response, Fare fare, Listener listener) {
            mFare = fare;
            mListener = listener;

            B.book.setOnClickListener(this);
            B.providerName.setText(response.getAppendix().getProvidersMapping().get(String.valueOf(fare.getProviderId())));
            B.fare.setText("₹" + mFare.getFare());
            Picasso.with(B.getRoot().getContext()).load(Constants.PROVIDER_URL).into(B.providerImage);
        }

        @Override
        public void onClick(View v) {
            mListener.onBookNowClicked(mFare);
        }
    }
}
