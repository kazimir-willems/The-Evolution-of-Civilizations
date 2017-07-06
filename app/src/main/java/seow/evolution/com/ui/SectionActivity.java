package seow.evolution.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import seow.evolution.com.R;

public class SectionActivity extends AppCompatActivity {

    @Bind(R.id.title_list)
    ListView titleList;

    private int chapterNo;
    private int sectionNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        ButterKnife.bind(this);

        chapterNo = getIntent().getIntExtra("chapter_no", 0);
        sectionNo = getIntent().getIntExtra("section_no", 0);
        String titleKey = "section_" + chapterNo + "_" + sectionNo;

        int resourceId = this.getResources().getIdentifier(titleKey, "array", this.getPackageName());
        if(resourceId != 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SectionActivity.this,
                    R.layout.content_item, android.R.id.text1, getResources().getStringArray(resourceId));

            titleList.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }

        titleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String systemKey = "content_" + chapterNo + "_" + sectionNo + "_" + String.valueOf(i + 1);
                int resourceId = SectionActivity.this.getResources().getIdentifier(systemKey, "array", SectionActivity.this.getPackageName());

                if(resourceId != 0) {
                    Intent intent = new Intent(SectionActivity.this, ContentActivity.class);
                    intent.putExtra("chapter_no", chapterNo);
                    intent.putExtra("section_no", sectionNo);
                    intent.putExtra("title_no", i + 1);
                    intent.putExtra("content_id", resourceId);

                    startActivity(intent);
                }
            }
        });


    }
}
