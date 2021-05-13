package app.actionnation.actionapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AbundanceAdapter extends RecyclerView.Adapter<AbundanceAdapter.AbundanceViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    ArrayList<String> strgrPattern = new ArrayList<>();


    public AbundanceAdapter(Context ctx, Cursor cursor) {
        mContext = ctx;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public AbundanceAdapter.AbundanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.lf_distractionlist, parent, false);

        return new AbundanceAdapter.AbundanceViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final AbundanceAdapter.AbundanceViewHolder holder, int position) {

        if (mCursor != null) {

            String name = mCursor.getString(mCursor.getColumnIndex("Ab_Reframe"));

            holder.tv.setText(name);
            mCursor.moveToNext();
        }
        holder.chk.setVisibility(View.INVISIBLE);
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

    public class AbundanceViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;
        public CheckBox chk;

        public AbundanceViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.distraction_name);
            chk = itemView.findViewById(R.id.chkDistraction);
        }
    }

}
