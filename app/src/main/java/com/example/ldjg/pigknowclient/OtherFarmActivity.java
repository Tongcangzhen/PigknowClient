package com.example.ldjg.pigknowclient;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ldjg.pigknowclient.Adapter.PigAdapter;
import com.example.ldjg.pigknowclient.DB.Farms;
import com.example.ldjg.pigknowclient.DB.Record;
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
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class OtherFarmActivity extends AppCompatActivity {
    private String selectFarmName;
    private Dialog waitDialog;
    private boolean flagEnd = false;
    private boolean flagStart = false;
    private String startTime;
    private String endTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    private AlertDialog.Builder builder;
    private boolean flag = false;
    Farms farmsByFind;

    @BindView(R.id.textview_date_start_find)
    TextView textViewDateStart;

    @BindView(R.id.textview_date_end_find)
    TextView textViewDateEnd;

    @BindView(R.id.textview_farms_find)
    TextView textViewFarmsFind;

    @BindView(R.id.button_find_other_farm)
    Button buttonFindOtherFarm;

    @BindView(R.id.pig_recyclerview_other)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_farm);
        ButterKnife.bind(this);
        initDatePicker();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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


    @OnClick(R.id.textview_date_end_find)
    public void selectEndDate() {
        customDatePicker1.show(textViewDateEnd.getText().toString());
        flagEnd = true;
    }
    @OnClick(R.id.textview_date_start_find)
    public void selectStartDate() {
        customDatePicker2.show(textViewDateEnd.getText().toString());
        flagStart = true;
    }

    @OnClick(R.id.textview_farms_find)
    public void findFarms() {
        showSimpleListDialog1();
    }
    @OnClick(R.id.button_find_other_farm)
    public void buttonClick() {
        if (flag) {
            getData();
        } else {
            ShowDialog.showDefaultDialog(this,"未选择农场");
        }
    }

    private void showSimpleListDialog1() {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_list_dialog);

        BmobQuery<Farms> query=new BmobQuery<Farms>();
        query.addWhereExists("admin1");
        query.addWhereExists("admin");
        query.findObjects(new FindListener<Farms>() {
            @Override
            public void done(final List<Farms> list, BmobException e) {
                if (e == null) {
                    ArrayList<String> farmNames=new ArrayList<String>();
                    for (Farms farm : list) {
                        String farmName =farm.getFarmsName();
                        String farmAddress=farm.getAdress();
                        farmNames.add(farmName);
                    }
                    final String[] arrString = (String[])farmNames.toArray(new String[0]);
                    builder.setItems(arrString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            farmsByFind=list.get(i);
                            textViewFarmsFind.setText(farmsByFind.getFarmsName());
                            flag = true;
                        }
                    });
                    builder.setCancelable(true);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败 ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });

    }

    private void getData() {
        waitDialog = UIHelper.createLoadingDialog(this, "加载中....");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = null;
        Date dateEnd = null;
        BmobQuery<Record> query = new BmobQuery<Record>();
        query.addWhereEqualTo("audit", 1);
        query.addWhereEqualTo("farms", farmsByFind);
        query.order("-createdAt");
        query.setLimit(50);
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
                    ShowDialog.showDateWrongDialog(this);
                    return;
                }
            } else {
                query.addWhereGreaterThan("createdAt", new BmobDate(dateStart));
            }
        }
        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> list, BmobException e) {
                if (e == null) {
                    UIHelper.closeDialog(waitDialog);
                    PigAdapter pigAdapter=new PigAdapter(list);
                    recyclerView.setAdapter(pigAdapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(OtherFarmActivity.this,
                            DividerItemDecoration.VERTICAL));
                } else {
                    UIHelper.closeDialog(waitDialog);
                    Toast.makeText(OtherFarmActivity.this, "查询失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

//        innerQuery.addWhereEqualTo("admin", admin);
//        String aDate = Gettime.getMonthDate();
//        Date date = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            date = sdf.parse(aDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        BmobQuery<Record> query=new BmobQuery<Record>();
//        BmobQuery<Farms> innerQuery=new BmobQuery<Farms>();
//        query.addWhereNotEqualTo("audit",1);
//        query.addWhereMatchesQuery("farms","Farms",innerQuery);
//        query.addWhereGreaterThanOrEqualTo("createdAt", new BmobDate(date));
//        query.order("-createdAt");
//        query.findObjects(new FindListener<Record>() {
//            @Override
//            public void done(List<Record> list, BmobException e) {
//                if (e == null) {
//                    UIHelper.closeDialog(waitDialog);
//                    PigAdapter pigAdapter=new PigAdapter(list);
//                    recyclerView.setAdapter(pigAdapter);
//                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
//                            DividerItemDecoration.VERTICAL));
//                } else {
//                    UIHelper.closeDialog(waitDialog);
//                    Toast.makeText(getContext(), "查询失败", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            }
//        });
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



}
