package com.example.ldjg.pigknowclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.ldjg.pigknowclient.Adapter.VideoAdapter;
import com.example.ldjg.pigknowclient.DB.Media;
import com.example.ldjg.pigknowclient.Util.UIHelper;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoSelectActivity extends AppCompatActivity {

    @BindView(R.id.pig_recyclerview_video)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_select);
        ButterKnife.bind(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                UIHelper.returnHome(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        List<Media> mediaList = DataSupport.where("visiable = ?", "1").order("Date desc").find(Media.class);
        VideoAdapter videoAdapter = new VideoAdapter(mediaList);
        recyclerView.setAdapter(videoAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(VideoSelectActivity.this,
                DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(VideoSelectActivity.this,
                DividerItemDecoration.VERTICAL));

    }
}
