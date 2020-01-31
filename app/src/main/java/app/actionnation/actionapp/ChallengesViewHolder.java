package app.actionnation.actionapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import app.actionnation.actionapp.Database_Content.Challenges;


public class ChallengesViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView cv_ChallengeNo;
    public final TextView cv_ChallengeName;
    public final TextView cv_ChallengeDesc;

    public Challenges mItem;

    public ChallengesViewHolder(View view) {
        super(view);
        mView = view;

        cv_ChallengeNo = view.findViewById(R.id.tv_ocv_EduName);
        cv_ChallengeName = view.findViewById(R.id.tv_ocv_ChallengeName);
        cv_ChallengeDesc = view.findViewById(R.id.tv_ocv_ChallengeDesc);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + cv_ChallengeName.getText() + "'";
    }
}
