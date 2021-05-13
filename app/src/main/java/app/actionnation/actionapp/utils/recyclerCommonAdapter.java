package app.actionnation.actionapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.actionnation.actionapp.FragmentSendGameInvitations;
import app.actionnation.actionapp.R;

public class recyclerCommonAdapter extends RecyclerView.Adapter<FragmentSendGameInvitations.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mTeamGameList;


    public recyclerCommonAdapter(Context ctx, ArrayList<String> strTeamList) {
        mContext = ctx;
        mTeamGameList = strTeamList;

    }


    @NonNull
    @Override
    public FragmentSendGameInvitations.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.lf_belieflist, parent, false);
        return new FragmentSendGameInvitations.ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull FragmentSendGameInvitations.ViewHolder holder, int position) {

        holder.emailId.setText(mTeamGameList.get(position));

    }

    @Override
    public int getItemCount() {
       return mTeamGameList.size();
    }

}



