package seow.evolution.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import seow.evolution.com.R;
import seow.evolution.com.ui.ChapterActivity;
import seow.evolution.com.util.SharedPrefManager;

public class ContentFragment extends Fragment {

    private int contentResourceID;
    private int slideNo;

    private String strContent;

    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.text_scroll)
    ScrollView textScroll;
    @Bind(R.id.iv_image)
    ImageView ivImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);

        ButterKnife.bind(ContentFragment.this, v);

        strContent = getResources().getStringArray(contentResourceID)[slideNo];

        if(strContent.startsWith("Text")) {
            strContent = strContent.substring(4);
            tvContent.setText(strContent);
            ivImage.setVisibility(View.GONE);
            textScroll.setVisibility(View.VISIBLE);
        } else if(strContent.startsWith("Image")) {
            textScroll.setVisibility(View.GONE);
            ivImage.setVisibility(View.VISIBLE);
            String key = strContent.substring(5);

            int resourceId = getActivity().getResources().getIdentifier(key, "drawable", getActivity().getPackageName());
            ivImage.setBackground(getResources().getDrawable(resourceId));
        }

        setFontSize(SharedPrefManager.getInstance(getActivity()).getFontSize() * 2 + 14);
        return v;
    }

    public static ContentFragment newInstance(int resourceID, int slideNo) {
        ContentFragment f = new ContentFragment();
        f.contentResourceID = resourceID;
        f.slideNo = slideNo;
        return f;
    }

    public void setFontSize(int size) {
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
}