package seow.evolution.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import seow.evolution.com.R;

public class ChapterActivity extends AppCompatActivity {

    @Bind(R.id.section_list)
    ListView sectionList;

    private int chapterNo;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        ButterKnife.bind(this);

        chapterNo = getIntent().getIntExtra("chapter_no", 0);
        String sectionKey = "chapter_" + chapterNo;

        int resourceId = this.getResources().getIdentifier(sectionKey, "array", this.getPackageName());
        if(resourceId != 0) {
            adapter = new ArrayAdapter<String>(ChapterActivity.this,
                    R.layout.content_item, android.R.id.text1, getResources().getStringArray(resourceId));

            sectionList.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }

        sectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String systemKey = "section_" + chapterNo + "_" + String.valueOf(i + 1);
                int resourceId = ChapterActivity.this.getResources().getIdentifier(systemKey, "array", ChapterActivity.this.getPackageName());
                if(resourceId != 0) {
                    Intent intent = new Intent(ChapterActivity.this, SectionActivity.class);
                    intent.putExtra("chapter_no", chapterNo);
                    intent.putExtra("section_no", i + 1);

                    startActivity(intent);
                } else {
                    String contentKey = "content_" + chapterNo + "_" + String.valueOf(i + 1);
                    resourceId = ChapterActivity.this.getResources().getIdentifier(contentKey, "array", ChapterActivity.this.getPackageName());

                    if(resourceId != 0) {

                        Intent intent = new Intent(ChapterActivity.this, ContentActivity.class);
                        intent.putExtra("chapter_no", chapterNo);
                        intent.putExtra("section_no", i + 1);
                        intent.putExtra("title_no", 0);
                        intent.putExtra("content_id", resourceId);
                        intent.putExtra("title", adapter.getItem(i));
                        intent.putExtra("from_reading", true);

                        startActivity(intent);
                    }
                }
            }
        });


    }
}
