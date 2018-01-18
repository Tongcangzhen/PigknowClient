package com.example.ldjg.pigknowclient;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldjg.pigknowclient.DB.Record;
import com.example.ldjg.pigknowclient.Util.ShowDialog;
import com.example.ldjg.pigknowclient.Util.UIHelper;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class RecordDetailActivity extends AppCompatActivity {

    @BindView(R.id.nice_video_player)
    NiceVideoPlayer mNiceVideoPlayer;

    @BindView(R.id.textview_date)
    TextView textViewDate;

    @BindView(R.id.textview_num)
    TextView textViewNum;

    @BindView(R.id.textview_audit)
    TextView textViewAudit;

    @BindView(R.id.textview_adminremarks)
    TextView textViewAdminRemarks;

    @BindView(R.id.edittext_reset_num)
    EditText editTextResetnum;

    @BindView(R.id.lineralayout_admin_marks)
    LinearLayout LayoutAdminMarks;

    @BindView(R.id.textinput_layout_num)
    TextInputLayout textInputLayout;

    @BindView(R.id.button_record_detail)
    Button buttonRecordDetail;

    Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        ButterKnife.bind(this);
        record=(Record)getIntent().getSerializableExtra("pig_data");
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
        int status = record.getAudit();
        if (status == 0) {
            LayoutAdminMarks.setVisibility(View.INVISIBLE);
            textInputLayout.setVisibility(View.INVISIBLE);
            buttonRecordDetail.setVisibility(View.INVISIBLE);
        } else if (status == 1) {
            textInputLayout.setVisibility(View.INVISIBLE);
            buttonRecordDetail.setVisibility(View.INVISIBLE);
            textViewAdminRemarks.setText(record.getAdminRemarks());
        } else {
            textViewAdminRemarks.setText(record.getAdminRemarks());
        }
        textViewDate.setText(record.getUpLoadDate());
        textViewNum.setText(record.getNum() + "");
        if (status == 0) {
            textViewAudit.setText("未审核");
        } else if (status == 1) {
            textViewAudit.setText("审核通过");
        } else if (status == 2) {
            textViewAudit.setText("审核未通过");
        } else {
            textViewAudit.setText("获取审核状态失败");
        }
        initVideo();
    }

    private void initVideo(){
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setUp(record.getVideoFile().getUrl(), null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("当前视频");
        mNiceVideoPlayer.setController(controller);
    }

    private void modifyTheRecord() {
        int num;
        if (editTextResetnum.getText().toString().length()==0) {
            ShowDialog.showResetNumEmptyDialog(this);
            return;
        } else if (Integer.valueOf(editTextResetnum.getText().toString()) == record.getNum()) {
            ShowDialog.showRestNumErrorDialog(this);
            return;
        } else {
            num = Integer.valueOf(editTextResetnum.getText().toString());
        }
        record.setAudit(0);
        record.setNum(num);
        record.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(RecordDetailActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                    UIHelper.returnHome(RecordDetailActivity.this);
                } else {
                    Toast.makeText(RecordDetailActivity.this, "请求失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick(R.id.button_record_detail)
    public void modifyRecord() {
        modifyTheRecord();
    }

}
