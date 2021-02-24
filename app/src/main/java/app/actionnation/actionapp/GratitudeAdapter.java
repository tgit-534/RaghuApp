package app.actionnation.actionapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.data.DbHelper;

public class GratitudeAdapter extends RecyclerView.Adapter<GratitudeAdapter.GratitudeViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    ArrayList<String> strgrPattern = new ArrayList<>();


    public GratitudeAdapter(Context ctx, Cursor cursor, ArrayList<String> strGratitudeList) {
        mContext = ctx;
        mCursor = cursor;
        strgrPattern = strGratitudeList;
    }

    @NonNull
    @Override
    public GratitudeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.lf_distractionlist, parent, false);
        return new GratitudeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final GratitudeViewHolder holder, int position) {

        if (mCursor != null) {

            String name = mCursor.getString(mCursor.getColumnIndex("G_GratitudeName"));

            holder.tvGratitude.setText(name);
            holder.chkGratitude.setTag(name);
            holder.chkGratitude.setVisibility(View.INVISIBLE);
            mCursor.moveToNext();

            if (strgrPattern != null) {
                if (strgrPattern.contains(name)) {
                    holder.chkGratitude.setChecked(true);
                }
            }
        }

        holder.chkGratitude.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Calendar cal = Calendar.getInstance();
                int dayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);
                DbHelper db = new DbHelper(mContext);
                if (isChecked == true) {
                    db.insertGratitudeDayList(holder.chkGratitude.getTag().toString(), 1, dayOfTheYear);
                } else {
                    db.updateGratitudeDayList(holder.chkGratitude.getTag().toString(), 0, dayOfTheYear);
                    ;
                }
            }

        });


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

    public class GratitudeViewHolder extends RecyclerView.ViewHolder {

        public TextView tvGratitude;
        public CheckBox chkGratitude;

        public GratitudeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGratitude = itemView.findViewById(R.id.distraction_name);
            chkGratitude = itemView.findViewById(R.id.chkDistraction);
        }
    }

}


