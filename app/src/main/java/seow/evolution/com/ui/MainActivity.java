package seow.evolution.com.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import seow.evolution.com.R;
import seow.evolution.com.fragment.FavoriteFragment;
import seow.evolution.com.fragment.HomeFragment;
import seow.evolution.com.fragment.SettingsFragment;
import seow.evolution.com.util.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.iv_favorite)
    ImageView ivFavorite;
    @Bind(R.id.iv_home)
    ImageView ivHome;
    @Bind(R.id.iv_setting)
    ImageView ivSetting;

    @Bind(R.id.tv_favorite)
    TextView tvFavorite;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.tv_setting)
    TextView tvSetting;

    private int selectedTab = 1;        //0:Favotie, 1:Home, 2:Setting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).getReading()) {
            Intent intent = new Intent(this, ContentActivity.class);

            intent.putExtra("title", SharedPrefManager.getInstance(this).getTitle());
            intent.putExtra("slide_no", SharedPrefManager.getInstance(this).getLastSlide());
            intent.putExtra("content_id", SharedPrefManager.getInstance(this).getContentID());
            intent.putExtra("from_reading", false);

            startActivity(intent);
        }

        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_layout, HomeFragment.newInstance())
                .commit();

    }

    @OnClick(R.id.favorite_layout)
    void onClickFavorite() {
        if(selectedTab == 0) {
            return;
        } else {
            selectedTab = 0;
            ivFavorite.setBackground(getResources().getDrawable(R.drawable.ic_favorite_pressed));
            tvFavorite.setTextColor(getResources().getColor(R.color.colorPrimary));
            ivHome.setBackground(getResources().getDrawable(R.drawable.ic_home));
            tvHome.setTextColor(getResources().getColor(R.color.colorWhite));
            ivSetting.setBackground(getResources().getDrawable(R.drawable.ic_setting));
            tvSetting.setTextColor(getResources().getColor(R.color.colorWhite));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_layout, FavoriteFragment.newInstance())
                    .commit();
        }
    }

    @OnClick(R.id.home_layout)
    void onClickHome() {
        if(selectedTab == 1) {
            return;
        } else {
            selectedTab = 1;
            ivFavorite.setBackground(getResources().getDrawable(R.drawable.ic_favorite));
            tvFavorite.setTextColor(getResources().getColor(R.color.colorWhite));
            ivHome.setBackground(getResources().getDrawable(R.drawable.ic_home_pressed));
            tvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
            ivSetting.setBackground(getResources().getDrawable(R.drawable.ic_setting));
            tvSetting.setTextColor(getResources().getColor(R.color.colorWhite));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_layout, HomeFragment.newInstance())
                    .commit();
        }
    }

    @OnClick(R.id.settings_layout)
    void onClickSetting() {
        if(selectedTab == 2) {
            return;
        } else {
            selectedTab = 2;
            ivFavorite.setBackground(getResources().getDrawable(R.drawable.ic_favorite));
            tvFavorite.setTextColor(getResources().getColor(R.color.colorWhite));
            ivHome.setBackground(getResources().getDrawable(R.drawable.ic_home));
            tvHome.setTextColor(getResources().getColor(R.color.colorWhite));
            ivSetting.setBackground(getResources().getDrawable(R.drawable.ic_setting_pressed));
            tvSetting.setTextColor(getResources().getColor(R.color.colorPrimary));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_layout, SettingsFragment.newInstance())
                    .commit();
        }
    }
}
