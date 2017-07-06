package seow.evolution.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import seow.evolution.com.R;
import seow.evolution.com.ui.ChapterActivity;
import seow.evolution.com.ui.ContentActivity;

public class HomeFragment extends Fragment {

    @Bind(R.id.chapter_list)
    ListView chapterList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(HomeFragment.this, v);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.content_item, android.R.id.text1, getResources().getStringArray(R.array.chapter_titles));
        chapterList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i >= 1) {
                    String sectionKey = "chapter_" + String.valueOf(i - 1);

                    int resourceId = getActivity().getResources().getIdentifier(sectionKey, "array", getActivity().getPackageName());

                    if (resourceId != 0) {
                        Intent intent = new Intent(getActivity(), ChapterActivity.class);
                        intent.putExtra("chapter_no", i - 1);

                        startActivity(intent);
                    } else {
                        String contentKey = "content_" + String.valueOf(i - 1);
                        resourceId = getActivity().getResources().getIdentifier(contentKey, "array", getActivity().getPackageName());

                        if (resourceId != 0) {
                            Intent intent = new Intent(getActivity(), ContentActivity.class);
                            intent.putExtra("chapter_no", i - 1);
                            intent.putExtra("content_id", resourceId);
                            intent.putExtra("section_no", 0);
                            intent.putExtra("title_no", 0);

                            startActivity(intent);
                        }
                    }
                } else if(i == 0) {
                    String contentKey = "content_foreword";
                    int resourceId = getActivity().getResources().getIdentifier(contentKey, "array", getActivity().getPackageName());

                    if (resourceId != 0) {
                        Intent intent = new Intent(getActivity(), ContentActivity.class);
                        intent.putExtra("chapter_no", 12);
                        intent.putExtra("section_no", 0);
                        intent.putExtra("title_no", 0);
                        intent.putExtra("content_id", resourceId);

                        startActivity(intent);
                    }
                }
            }
        });

        return v;
    }

    public static HomeFragment newInstance() {
        HomeFragment f = new HomeFragment();

        return f;
    }
}