package app.actionnation.actionapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserTeam;
import app.actionnation.actionapp.R;
import app.actionnation.actionapp.Storage.UserShowCaseGame;

public class GameTrackingAdapter extends RecyclerView.Adapter<GameTrackingAdapter.ViewHolderGameTracking> {
    private ArrayList<UserShowCaseGame> dataModalArrayList;
    private Context context;
    private int mNoOfWinners, mNoOfLosers, mNoOfCoins;


    // constructor class for our Adapter
    public GameTrackingAdapter(ArrayList<UserShowCaseGame> dataModalArrayList, Context context, int noOfWinners, int noOfLosers, int noOfCoins) {
        this.dataModalArrayList = dataModalArrayList;
        this.context = context;
        mNoOfWinners = noOfWinners;
        mNoOfLosers = noOfLosers;
        mNoOfCoins = noOfCoins;
    }

    @NonNull
    @Override
    public ViewHolderGameTracking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item

        return new ViewHolderGameTracking(LayoutInflater.from(context).inflate(R.layout.lf_showgametracking, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGameTracking holder, int position) {
        UserShowCaseGame model = dataModalArrayList.get(position);

        holder.mIdGameScore.setText(String.valueOf(model.getUserScore()) + "%");

        float noOfCoins = model.getUserCoins();
        if (model.getUserCoins() > 0) {

            UserShowCaseGame modelNew =  dataModalArrayList.get(getItemCount()-1);

            holder.mIdnoOfCoins.setText(String.valueOf(model.getUserCoins()));
            float coinsEarned = (modelNew.getCountCoinLosers() * noOfCoins) / modelNew.getCountCoinWinners();
            holder.mIdnoOfCoins.setText(String.valueOf(model.getUserCoins() + coinsEarned));

        }

        holder.mIdPlayerName.setText(model.getUserName());
    }
/*
    @Override
    public void onBindViewHolder(@NonNull ViewHolderGameTracking.ViewHolder holder, int position) {
        // setting data to our views in Recycler view items.
        DataModal modal = dataModalArrayList.get(position);

    }*/

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return dataModalArrayList.size();
    }

    public class ViewHolderGameTracking extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdPlayerName;
        public final TextView mIdGameScore;
        public final TextView mIdnoOfCoins;


        public UserTeam mItem;

        public ViewHolderGameTracking(View view) {
            super(view);
            mView = view;
            mIdPlayerName = view.findViewById(R.id.tv_fm_playerName);
            mIdGameScore = view.findViewById(R.id.tv_fm_gameScore);
            mIdnoOfCoins = view.findViewById(R.id.tv_fm_coinsEarned);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdPlayerName.getText() + "'";
        }
    }
}
