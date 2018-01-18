package com.example.ldjg.pigknowclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ldjg.pigknowclient.DB.Farms;
import com.example.ldjg.pigknowclient.DB.Record;
import com.example.ldjg.pigknowclient.DB.User;
import com.example.ldjg.pigknowclient.Util.Gettime;
import com.example.ldjg.pigknowclient.Util.UIHelper;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.example.ldjg.pigknowclient.LoginActivity.Application_ID;

public class AddRecordActivity extends AppCompatActivity {
    String videourl;
    String iamgeurl;
    SharedPreferences sharedPreferences;

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
        Bmob.initialize(this,Application_ID);
        Intent intent=getIntent();
        videourl=intent.getStringExtra("url");
        iamgeurl=intent.getStringExtra("path");
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        init();
    }

    private void init() {
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setUp(videourl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("当前待上传视频");
        Glide.with(this)
                .load(iamgeurl)
//                .placeholder(R.drawable.img_default)
//                .crossFade()
                .into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
    }

    private void addNewRecord(BmobFile bmobFile){
        int num;
        String time= Gettime.getthisdate();
        if (editTextPigNum.getText().toString().length()==0) {
            num = 1;
        } else {
            num=Integer.valueOf(editTextPigNum.getText().toString());
        }
        String beizhu;
        if (editTextBeizhu.getText().toString().length()==0) {
            beizhu = "未备注";
        } else {
            beizhu=editTextBeizhu.getText().toString();
        }
        User user=new User();
        user.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
        Farms farms=new Farms();
        farms.setObjectId(sharedPreferences.getString("farmsid",""));
        Record record=new Record();
        record.setFarmsName(sharedPreferences.getString("farmsname",""));
        record.setNum(num);
        record.setUser(user);
        record.setFarms(farms);
        record.setUpLoadDate(time);
        record.setFarmsRemarks(beizhu);
        record.setAudit(0);
        record.setVideoFile(bmobFile);
        record.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(AddRecordActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                    pushAction();
                } else {
                    Toast.makeText(AddRecordActivity.this,"出现未知错误,请联系管理员",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void upLoadVideo(){
        final ProgressDialog progress = new ProgressDialog(AddRecordActivity.this);
        progress.setMessage("正在上传视频中...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        final BmobFile bmobFile=new BmobFile(new File(videourl));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    progress.dismiss();
                    addNewRecord(bmobFile);
                } else {
                    Toast.makeText(AddRecordActivity.this,"视频上传失败",Toast.LENGTH_LONG).show();
                    progress.dismiss();
                    e.printStackTrace();
                }
            }
        });
    }

    public void pushAction() {
        String installationId = sharedPreferences.getString("installationId", "");
        String msg = ("来自"+sharedPreferences.getString("farmsname", ""));
        BmobPushManager bmobPush = new BmobPushManager();
        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
        query.addWhereEqualTo("installationId", installationId);
        bmobPush.setQuery(query);
        bmobPush.pushMessage(msg, new PushListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    UIHelper.returnHome(AddRecordActivity.this);
                } else {
                    Toast.makeText(AddRecordActivity.this,"推送消息失败",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    UIHelper.returnHome(AddRecordActivity.this);
                }
            }
        });
    }


    @OnClick(R.id.button_add_record)
    public void addRecord(){
        upLoadVideo();
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
