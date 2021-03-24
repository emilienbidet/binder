package com.emilienbidet.binderstpatrick;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.binderstpatrick.R;
import com.google.gson.Gson;

import static com.emilienbidet.binderstpatrick.SharedPreferencesConfig.*;

public class OptionsFragment extends Fragment {

    private EditText etName;
    private CrystalRangeSeekbar rsbAbv;
    private CrystalRangeSeekbar rsbIbu;
    private CrystalRangeSeekbar rsbEbc;
    private TextView tvAbvMin;
    private TextView tvAbvMax;
    private TextView tvIbuMin;
    private TextView tvIbuMax;
    private TextView tvEbcMin;
    private TextView tvEbcMax;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.editTextTextPersonName);
        rsbAbv = view.findViewById(R.id.rsb_options_abv);
        rsbIbu = view.findViewById(R.id.rsb_options_ibu);
        rsbEbc = view.findViewById(R.id.rsb_options_ebc);
        tvAbvMin = view.findViewById(R.id.tv_options_abv_min);
        tvAbvMax = view.findViewById(R.id.tv_options_abv_max);
        tvIbuMin = view.findViewById(R.id.tv_options_ibu_min);
        tvIbuMax = view.findViewById(R.id.tv_options_ibu_max);
        tvEbcMin = view.findViewById(R.id.tv_options_ebc_min);
        tvEbcMax = view.findViewById(R.id.tv_options_ebc_max);

        rsbAbv.setOnRangeSeekbarChangeListener(getOnRangeSeekbarChangeListener(tvAbvMin, tvAbvMax));
        rsbIbu.setOnRangeSeekbarChangeListener(getOnRangeSeekbarChangeListener(tvIbuMin, tvIbuMax));
        rsbEbc.setOnRangeSeekbarChangeListener(getOnRangeSeekbarChangeListener(tvEbcMin, tvEbcMax));

        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_OPTIONS_NAME, 0);
        String json = sharedPreferences.getString(SHARED_PREFERENCES_KEY_FILTERS, null);
        Filters filters = json != null ? new Gson().fromJson(json, Filters.class) : new Filters();

        etName.setText(filters.getName());

        rsbAbv.setMinStartValue(filters.getAbvMin());
        rsbAbv.setMaxStartValue(filters.getAbvMax());
        rsbAbv.setMinValue(Filters.DEFAULT_ABV_MIN);
        rsbAbv.setMaxValue(Filters.DEFAULT_ABV_MAX);
        rsbAbv.apply();

        rsbIbu.setMinStartValue(filters.getIbuMin());
        rsbIbu.setMaxStartValue(filters.getIbuMax());
        rsbIbu.setMinValue(Filters.DEFAULT_IBU_MIN);
        rsbIbu.setMaxValue(Filters.DEFAULT_IBU_MAX);
        rsbIbu.apply();

        rsbEbc.setMinStartValue(filters.getEbcMin());
        rsbEbc.setMaxStartValue(filters.getEbcMax());
        rsbEbc.setMinValue(Filters.DEFAULT_EBC_MIN);
        rsbEbc.setMaxValue(Filters.DEFAULT_EBC_MAX);
        rsbEbc.apply();
    }

    private void saveData() {
        SharedPreferences pref = getActivity().getSharedPreferences(SHARED_PREFERENCES_OPTIONS_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        Filters filters = new Filters();

        filters.setName(etName.getText().toString());
        filters.setAbvMin(rsbAbv.getSelectedMinValue().intValue());
        filters.setAbvMax(rsbAbv.getSelectedMaxValue().intValue());
        filters.setIbuMin(rsbIbu.getSelectedMinValue().intValue());
        filters.setIbuMax(rsbIbu.getSelectedMaxValue().intValue());
        filters.setEbcMin(rsbEbc.getSelectedMinValue().intValue());
        filters.setEbcMax(rsbEbc.getSelectedMaxValue().intValue());

        editor.putString(SHARED_PREFERENCES_KEY_FILTERS, new Gson().toJson(filters));
        editor.apply();
    }

    private OnRangeSeekbarChangeListener getOnRangeSeekbarChangeListener(TextView tvMin, TextView tvMax) {
        return new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue.intValue()));
                tvMax.setText(String.valueOf(maxValue.intValue()));
            }
        };
    }
}
