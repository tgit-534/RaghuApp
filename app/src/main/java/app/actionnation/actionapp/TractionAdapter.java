package app.actionnation.actionapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
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

public class TractionAdapter extends RecyclerView.Adapter<TractionAdapter.TractionViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private String mfbId;
    private ArrayList<String> strTractionData;


    public TractionAdapter(Context ctx, Cursor cursor, String fbId, ArrayList<String> strTractionList) {
        mContext = ctx;
        mCursor = cursor;
        mfbId = fbId;
        strTractionData = strTractionList;

    }

    @NonNull
    @Override
    public TractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.lf_distractionlist, parent, false);

        return new TractionViewHolder(view);
    }

    private static final String TAG = "TractionAdapter:Log";

    @Override
    public void onBindViewHolder(@NonNull final TractionViewHolder holder, int position) {
        if (mCursor != null) {

            String name = mCursor.getString(mCursor.getColumnIndex("Tr_Name"));
            Log.d(TAG, "Uid :" + name);


            holder.tv.setText(name);

            if (strTractionData != null) {
                if (strTractionData.contains(name)) {
                    holder.chk.setChecked(true);
                }
            }
            holder.chk.setTag(name + "#" + String.valueOf(mCursor.getCount()));
            mCursor.moveToNext();

        }

        holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Calendar cal = Calendar.getInstance();
                int dayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);
                int yr = cal.get(Calendar.YEAR);
                CommonClass cls = new CommonClass();
                String[] arrSplit = holder.chk.getTag().toString().split("#");

                DbHelper db = new DbHelper(mContext);
                if (isChecked == true) {
                    db.insertTractionDayList(arrSplit[0], 1, mfbId, dayOfTheYear);
                    cls.InsertAttentionScore(db, mfbId, dayOfTheYear, yr, 0, 1, 0, Integer.parseInt(arrSplit[1]));
                } else {
                    db.updateTractionDayList(holder.chk.getTag().toString(), 0, mfbId, dayOfTheYear);
                    cls.InsertAttentionScore(db, mfbId, dayOfTheYear, yr, 0, -1, 0, Integer.parseInt(arrSplit[1]));
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

    public class TractionViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;
        public CheckBox chk;

        public TractionViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.distraction_name);
            chk = itemView.findViewById(R.id.chkDistraction);
        }
    }

}


