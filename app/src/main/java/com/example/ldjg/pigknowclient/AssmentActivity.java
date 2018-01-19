package com.example.ldjg.pigknowclient;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ldjg.pigknowclient.Adapter.TableAdapter;
import com.example.ldjg.pigknowclient.DB.AssBean;
import com.example.ldjg.pigknowclient.DB.Record;
import com.example.ldjg.pigknowclient.DB.User;
import com.example.ldjg.pigknowclient.Util.CustomDatePicker;
import com.example.ldjg.pigknowclient.Util.ShowDialog;
import com.example.ldjg.pigknowclient.Util.UIHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AssmentActivity extends AppCompatActivity {
    private Dialog waitDialog;
    private boolean flagEnd = false;
    private boolean flagStart = false;
    private String startTime;
    private String endTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;

    List<AssBean> assBeanList;


    @BindView(R.id.table_title)
    ViewGroup tableTitle;

    @BindView(R.id.listview_pig_asssment)
    ListView listView;

    @BindView(R.id.textview_date_start)
    TextView textViewDateStart;

    @BindView(R.id.textview_date_end)
    TextView textViewDateEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assment);
        ButterKnife.bind(this);
        initDatePicker();
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

    private void showTable() {
        waitDialog = UIHelper.createLoadingDialog(this, "加载中....");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = null;
        Date dateEnd = null;
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Record> query = new BmobQuery<Record>();
        query.addWhereEqualTo("audit", 1);
        query.addWhereEqualTo("user", user);
        if (flagStart) {
            String StringStartTime = startTime + ":00";
            try {
                dateStart = sdf.parse(StringStartTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (flagEnd) {
                String StringEndTime = endTime + ":00";
                try {
                    dateEnd = sdf.parse(StringEndTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (dateStart.before(dateEnd)) {
                    query.addWhereGreaterThan("createdAt", new BmobDate(dateStart));
                    query.addWhereLessThan("createdAt", new BmobDate(dateEnd));
                } else {
                    UIHelper.closeDialog(waitDialog);
                    ShowDialog.showDateWrongDialog(AssmentActivity.this);
                    return;
                }
            } else {
                query.addWhereGreaterThan("createdAt", new BmobDate(dateStart));
            }
        }

        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> list, BmobException e) {
                UIHelper.closeDialog(waitDialog);
                int i = 1;
                int sum = 0;
                assBeanList = new ArrayList<AssBean>();
                for (Record record : list) {
                    AssBean assBean = new AssBean();
                    assBean.setId("" + i);
                    assBean.setDate(record.getUpLoadDate());
                    assBean.setNum(record.getNum());
                    assBeanList.add(assBean);
                    i++;
                    sum += record.getNum();
                }
                AssBean assBeanEnd = new AssBean();
                assBeanEnd.setDate("合计：");
                assBeanEnd.setNum(sum);
                assBeanList.add(assBeanEnd);
                tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));
                TableAdapter adapter = new TableAdapter(AssmentActivity.this, assBeanList);
                listView.setAdapter(adapter);
            }
        });
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        textViewDateEnd.setText(now.split(" ")[0]);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                textViewDateEnd.setText(time.split(" ")[0]);
                endTime = time;
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                textViewDateStart.setText(time.split(" ")[0]);
                startTime = time;
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(false); // 不显示时和分
        customDatePicker2.setIsLoop(false); // 不允许循环滚动
    }

    @OnClick(R.id.button_show_table)
    public void showTheTable() {
        showTable();
    }

    @OnClick(R.id.textview_date_end)
    public void selectEndDate() {
        customDatePicker1.show(textViewDateEnd.getText().toString());
        flagEnd = true;
    }

    @OnClick(R.id.textview_date_start)
    public void selectStartDate() {
        customDatePicker2.show(textViewDateEnd.getText().toString());
        flagStart = true;
    }

}
