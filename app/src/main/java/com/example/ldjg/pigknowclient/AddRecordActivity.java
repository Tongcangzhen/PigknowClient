package com.example.ldjg.pigknowclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRecordActivity extends AppCompatActivity {
    String videourl;
    String iamgeurl;


    @BindView(R.id.nice_video_player)
    NiceVideoPlayer mNiceVideoPlayer;

    @BindView(R.id.edittext_beizhu)
    EditText editTextBeizhu;

    @BindView(R.id.edittext_pig_num)
    EditText editTextPigNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        videourl=intent.getStringExtra("url");
        init();
    }

    private void init() {
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setUp(videourl, null);

        TxVideoPlayerController controller = new TxVideoPlayerController(this);

        controller.setTitle("当前待上传视频");
//        controller.setImage(iamgeurl);
        mNiceVideoPlayer.setController(controller);
    }

    private void addNewRecord(){
        int num=Integer.valueOf(editTextPigNum.getText().toString());
        String beizhu=editTextBeizhu.getText().toString();


    }

    @OnClick(R.id.button_add_record)
    public void addRecord(){

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}
