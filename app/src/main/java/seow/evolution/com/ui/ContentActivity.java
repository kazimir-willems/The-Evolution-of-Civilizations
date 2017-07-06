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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import seow.evolution.com.R;
import seow.evolution.com.fragment.ContentFragment;
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
    @Bind(R.id.activity_content)
    RelativeLayout contentLayout;
    @Bind(R.id.admob_view)
    AdView admobView;

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

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("A39667C0609A678E4C2174E9E7F5432A")
                .build();
        admobView.loadAd(adRequest);

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

                currentPage = position;
                String curSlide = String.format(getResources().getString(R.string.slide_no_format), currentPage + 1, totalPage);
                tvSlideNo.setText(curSlide);
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

        SharedPrefManager.getInstance(this).saveLastChapter(chapterNo);
        SharedPrefManager.getInstance(this).saveReading(true);

        String curSlide = String.format(getResources().getString(R.string.slide_no_format), 1, totalPage);
        tvSlideNo.setText(curSlide);
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
        SharedPrefManager.getInstance(this).saveBookmarkChapter(chapterNo);
        SharedPrefManager.getInstance(this).saveBookmarkSlide(currentPage);
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
}
