package app.actionnation.actionapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;

import app.actionnation.actionapp.CommonClass;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Database_Content.UserTeam;
import app.actionnation.actionapp.R;
import app.actionnation.actionapp.Storage.Constants;

public class ShowGameTrackingAdapter extends FirestoreRecyclerAdapter<UserGame, ShowGameTrackingAdapter.ViewHolderShowGame> {


    FirestoreRecyclerOptions<UserGame> classOptions;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShowGameTrackingAdapter(@NonNull FirestoreRecyclerOptions<UserGame> options) {
        super(options);
        classOptions = options;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolderShowGame holder, int position, @NonNull UserGame model) {

        CommonClass cls = new CommonClass();
        int TotalScore = cls.userGameScore(model);
        float percentScore = ((float) model.getUserTotatScore() / Constants.Game_userTotalScore) * 100;
        int finalPercent = (int) percentScore;
        holder.mIdGameScore.setText(String.valueOf(finalPercent) + "%");
        int coinsTotal = 0;
        int noOfParticipants = 0;
       /* if (txtNoOfPlayers.getTag() != null) {
            noOfParticipants = Integer.parseInt(txtNoOfPlayers.getTag().toString());
            noOfParticipants = noOfParticipants + 1;
            txtNoOfPlayers.setTag(noOfParticipants);
            txtNoOfPlayers.setText(txtNoOfPlayers.getTag().toString());

        }
        else
        {
            noOfParticipants = noOfParticipants + 1;
            txtNoOfPlayers.setTag(noOfParticipants);
        }*/


        if (percentScore >= model.getUserExellenceBar()) {
            holder.mIdnoOfCoins.setText(String.valueOf(model.getUserCoinsPerDay()));
        } else {
            holder.mIdnoOfCoins.setText(String.valueOf(Constants.Status_Zero));
            /*if (btnGameStakes.getTag() != null) {
                coinsTotal = Integer.valueOf(btnGameStakes.getTag().toString());
                coinsTotal = coinsTotal + model.getUserCoinsPerDay();
                btnGameStakes.setTag(coinsTotal);
                txtTotalScore.setText(btnGameStakes.getTag().toString());
            }*/

        }

        //holder.btnHabitSubmit.setTag(getSnapshots().getSnapshot(position).getId() + "," + model.getHabitDayOfTheYear() + "," + model.getHabitDays() + "," + model.getPowerLimit());
        holder.mIdPlayerName.setText(model.getUserName());
    }

    @Override
    public int getItemCount() {
        return classOptions.getSnapshots().size();
    }

    @NonNull
    @Override
    public ObservableSnapshotArray<UserGame> getSnapshots() {
        return super.getSnapshots();
    }

    @NonNull
    @Override
    public ViewHolderShowGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_showgametracking, parent, false);
        return new ViewHolderShowGame(view);
    }


    public static class ViewHolderShowGame extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdPlayerName;
        public final TextView mIdGameScore;
        public final TextView mIdnoOfCoins;


        public UserTeam mItem;

        public ViewHolderShowGame(View view) {
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
