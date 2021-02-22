package app.actionnation.actionapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentClock extends Fragment implements View.OnClickListener {
    Button btn10, btn15, btn20, btn30, btn60, btn190;

    public FragmentClock() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clock, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn10 = view.findViewById(R.id.btn_fclock_10);
        btn15 = view.findViewById(R.id.btn_fclock_15);
        btn20 = view.findViewById(R.id.btn_fclock_20);
        btn30 = view.findViewById(R.id.btn_fclock_30);
        btn60 = view.findViewById(R.id.btn_fclock_1hr);
        btn190 = view.findViewById(R.id.btn_fclock_130hr);

        btn10.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn20.setOnClickListener(this);
        btn30.setOnClickListener(this);
        btn60.setOnClickListener(this);
        btn190.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int i = v.getId();

       CommonClass cls = new CommonClass();
        if (i == R.id.btn_fclock_10) {
            cls.setTime(getContext(), 0, 10);
        } else if (i == R.id.btn_fclock_15) {
            cls.setTime(getContext(), 0, 15);

        } else if (i == R.id.btn_fclock_20) {
            cls.setTime(getContext(), 0, 20);

        } else if (i == R.id.btn_fclock_30) {
            cls.setTime(getContext(), 0, 30);

        } else if (i == R.id.btn_fclock_1hr) {
            cls.setTime(getContext(), 1, 0);

        } else if (i == R.id.btn_fclock_130hr) {
            cls.setTime(getContext(), 1, 30);

        }
    }
}
