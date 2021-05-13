package app.actionnation.actionapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BeliefAdapter extends RecyclerView.Adapter<BeliefAdapter.TvChkViewHolder> {
    private Context mContext;
    private Cursor mCursor;


    public BeliefAdapter(Context ctx, Cursor cursor) {
        mContext = ctx;
        mCursor = cursor;

    }

    @NonNull
    @Override
    public BeliefAdapter.TvChkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.lf_belieflist, parent, false);

        return new BeliefAdapter.TvChkViewHolder(view);
    }

    private static final String TAG = "BeliefAdapter:Log";

    @Override
    public void onBindViewHolder(@NonNull final BeliefAdapter.TvChkViewHolder holder, int position) {


        if (mCursor != null && mCursor.getCount() > 0) {



            if (position < mCursor.getCount()) {
                String name = mCursor.getString(mCursor.getColumnIndex("BS_Desc"));

                Log.d(TAG, "Uid :" + name);

                holder.tv.setText(name);
                mCursor.moveToNext();
                Log.d(TAG, "Uid 2:" + name);

            }

            if (position == mCursor.getCount() - 1) {
                mCursor.moveToFirst();
            }


        }
       /* if (mCursor != null) {

            String name = mCursor.getString(mCursor.getColumnIndex("BS_Desc"));
            holder.tv.setText(name);
            mCursor.moveToNext();

        }*/


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class TvChkViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;

        public TvChkViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.belief_name);
        }
    }

}

