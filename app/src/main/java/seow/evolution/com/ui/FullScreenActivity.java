package seow.evolution.com.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import seow.evolution.com.R;
import seow.evolution.com.customview.TouchImageView;
import seow.evolution.com.db.FavoriteDB;
import seow.evolution.com.fragment.ContentFragment;
import seow.evolution.com.model.FavoriteItem;
import seow.evolution.com.util.SharedPrefManager;

public class FullScreenActivity extends AppCompatActivity {

    @Bind(R.id.iv_full_image)
    TouchImageView ivImageView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        ButterKnife.bind(this);

        int resourceID = getIntent().getIntExtra("resourceID", 0);
        ivImageView.setImageResource(resourceID);

        ButterKnife.bind(this);

    }
}
