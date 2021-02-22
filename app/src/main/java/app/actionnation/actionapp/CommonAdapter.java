package app.actionnation.actionapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommonAdapter {

    public static class TvChkViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;
        public CheckBox chk;

        public TvChkViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.distraction_name);
            chk = itemView.findViewById(R.id.chkDistraction);
        }
    }

}
