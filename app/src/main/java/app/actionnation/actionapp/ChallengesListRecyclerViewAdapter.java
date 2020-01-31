package app.actionnation.actionapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.actionnation.actionapp.Database_Content.Challenges;

public class ChallengesListRecyclerViewAdapter extends RecyclerView.Adapter<ChallengesListRecyclerViewAdapter.MyViewHolder> {

    private List<Challenges> challengesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tx_ChallengeName, tx_ChallengeId;
        public Button btn_ChallengeExpand;

        public MyViewHolder(View view) {
            super(view);
            tx_ChallengeName = (TextView) view.findViewById(R.id.rv_tx_ChallengeName);
            tx_ChallengeId = (TextView) view.findViewById(R.id.rv_tx_ChallengeNo);

            //btn_ChallengeExpand = (Button) view.findViewById(R.id.rv_btnChallengeView);
        }
    }


    public ChallengesListRecyclerViewAdapter(List<Challenges> challengesList) {
        this.challengesList = challengesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_challenges__expand, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Challenges chlg = challengesList.get(position);
       //holder.tx_ChallengeId.setText(String.valueOf(chlg.getId()));
       holder.tx_ChallengeName.setText(chlg.getChallenge_Desc());
       //holder.btn_ChallengeExpand.setTag(chlg.getId());

       holder.btn_ChallengeExpand.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(view.getContext(),holder.btn_ChallengeExpand.getText(),Toast.LENGTH_LONG).show();
               Intent homepage = new Intent( view.getContext(), OneChallenge_Expand.class);
               Bundle mBundle = new Bundle();
               mBundle.putString("get_ChallengeId", holder.btn_ChallengeExpand.getTag().toString());
               homepage.putExtras(mBundle);
               view.getContext().startActivity(homepage);
           }
       });
       //  holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return challengesList.size();
    }
}
