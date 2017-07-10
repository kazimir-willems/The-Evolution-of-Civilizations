package seow.evolution.com.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import seow.evolution.com.R;
import seow.evolution.com.util.SharedPrefManager;

public class SettingsFragment extends Fragment {

    @Bind(R.id.switch_admob)
    Switch switchAdmob;
    @Bind(R.id.switch_interestial)
    Switch switchInterestial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(SettingsFragment.this, v);

        switchAdmob.setChecked(SharedPrefManager.getInstance(getActivity()).getGoogleAds());
        switchInterestial.setChecked(SharedPrefManager.getInstance(getActivity()).getGoogleInterestial());

        switchAdmob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPrefManager.getInstance(getActivity()).saveGoogleAds(b);
            }
        });

        switchInterestial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPrefManager.getInstance(getActivity()).saveGoogleInterestial(b);
            }
        });

        return v;
    }

    public static SettingsFragment newInstance() {
        SettingsFragment f = new SettingsFragment();

        return f;
    }

    @OnClick(R.id.tv_more_from_dev)
    void onClickMoreFromDev() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=SEOW%20WEIJIE")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=SEOW%20WEIJIE")));
        }
    }
}