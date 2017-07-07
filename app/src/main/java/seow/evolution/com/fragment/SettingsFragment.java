package seow.evolution.com.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import seow.evolution.com.R;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(SettingsFragment.this, v);

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