package seow.evolution.com.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.vision.text.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import seow.evolution.com.R;
import seow.evolution.com.db.FavoriteDB;
import seow.evolution.com.fragment.ContentFragment;
import seow.evolution.com.model.FavoriteItem;
import seow.evolution.com.util.SharedPrefManager;

public class ContentActivity extends AppCompatActivity {

    private int chapterNo;
    private String chapterTitle;

    String[] contents;

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.tv_show_spinner)
    TextView tvShowSpinner;
    @Bind(R.id.font_seek)
    SeekBar fontSeek;
    @Bind(R.id.tv_slide_no)
    TextView tvSlideNo;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.admob_view)
    AdView admobView;

    private InterstitialAd adView;  // The ad
    private Handler mHandler;       // Handler to display the ad on the UI thread
    private Runnable displayAd;     // Code to execute to perform this operation

    private ContentFragment[] fragments;
    private int slideCnt;

    private int totalPage;
    private int currentPage = 0;

    private int contentResourceID = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        ButterKnife.bind(this);

        if(!SharedPrefManager.getInstance(this).getGoogleAds()) {
            admobView.setVisibility(View.GONE);
        } else {
            admobView.setVisibility(View.VISIBLE);
        }

        adView = new InterstitialAd(ContentActivity.this);
        adView.setAdUnitId("ca-app-pub-1588855366653236/4173011907");
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAd();
            }
        });
        mHandler = new Handler(Looper.getMainLooper());
        displayAd = new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (adView.isLoaded()) {
                            adView.show();
                        }
                    }
                });
            }
        };
        loadAd();

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("A39667C0609A678E4C2174E9E7F5432A")
                .build();
        admobView.loadAd(adRequest);

        chapterTitle = getIntent().getStringExtra("title");
        tvTitle.setText(chapterTitle);
        contentResourceID = getIntent().getIntExtra("content_id", 0);
        contents = getResources().getStringArray(contentResourceID);

        slideCnt = contents.length;
        totalPage = slideCnt;

        fragments = new ContentFragment[slideCnt];
        for(int i = 0; i < slideCnt; i++) {
            fragments[i] = new ContentFragment().newInstance(contentResourceID, i);
        }

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(slideCnt);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                SharedPrefManager.getInstance(ContentActivity.this).saveLastSlide(position);
                SharedPrefManager.getInstance(ContentActivity.this).saveContentID(contentResourceID);
                SharedPrefManager.getInstance(ContentActivity.this).saveTitle(chapterTitle);

                currentPage = position;
                String curSlide = String.format(getResources().getString(R.string.slide_no_format), currentPage + 1, totalPage);
                tvSlideNo.setText(curSlide);

                if(currentPage % 5 == 4) {
                    if(SharedPrefManager.getInstance(ContentActivity.this).getGoogleInterestial())
                        mHandler.post(displayAd);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvShowSpinner.setText(Html.fromHtml("A<sup><small>A</small></sup>"));

        fontSeek.setProgress(SharedPrefManager.getInstance(this).getFontSize());

        fontSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
//                length_edit.setText(Integer.toString(progress + 20));
                setFontSize(14 + progress * 2);
                SharedPrefManager.getInstance(ContentActivity.this).saveFontSize(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SharedPrefManager.getInstance(this).saveReading(true);

        String curSlide = String.format(getResources().getString(R.string.slide_no_format), 1, totalPage);
        tvSlideNo.setText(curSlide);

        boolean fromReading = getIntent().getBooleanExtra("from_reading", true);
        if(!fromReading) {
            currentPage = getIntent().getIntExtra("slide_no", 0);
            viewPager.setCurrentItem(currentPage);

            curSlide = String.format(getResources().getString(R.string.slide_no_format), currentPage + 1, totalPage);
            tvSlideNo.setText(curSlide);
        }


    }

    @OnClick(R.id.tv_show_spinner)
    void onClickTvSpinner() {
        if(fontSeek.getVisibility() == View.GONE)
            fontSeek.setVisibility(View.VISIBLE);
        else
            fontSeek.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_bookmark)
    void onClickTvBookmark() {
        FavoriteItem item = new FavoriteItem();
        item.setSlideNo(currentPage);
        item.setContentID(contentResourceID);
        item.setTitle(chapterTitle);

        FavoriteDB db = new FavoriteDB(this);
        long ret = db.addFavorite(item);
        if(ret == -1) {
            Toast.makeText(this, R.string.already_added, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.successfully_added, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack() {
        finish();
    }

    private void setFontSize(int size) {
        for(int i = 0; i < fragments.length; i++) {
            fragments[i].setFontSize(size);
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return fragments[pos];
        }

        @Override
        public int getCount() {
            return slideCnt;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(admobView != null) {
            admobView.resume();
        }
    }

    @Override
    public void onPause() {
        if(admobView != null) {
            admobView.pause();
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(admobView != null) {
            admobView.destroy();
        }

        super.onDestroy();
    }

    void loadAd() {
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Load the adView object witht he request
        adView.loadAd(adRequest);
    }
}
