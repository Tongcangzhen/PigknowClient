package com.example.ldjg.pigknowclient;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldjg.pigknowclient.DB.Admin;
import com.example.ldjg.pigknowclient.DB.Farms;
import com.example.ldjg.pigknowclient.DB.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class SelectFarmsActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private Farms acitvityfarms;

    @BindView(R.id.edittext_invitationcode)
    EditText invitationcode;

    @BindView(R.id.textview_selectfarm)
    TextView theSelectFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_farms);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_selectfarm)
    public void selectFarm(){
        showSimpleListDialog();
    }

    @OnClick(R.id.button_selectfarm_do)
    public void selectFarmDo(){
        selectFarmAdd();
    }


    private void showSimpleListDialog() {
        String invicode=invitationcode.getText().toString();
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("可选择农场");

//        Admin admin = adminSharedPreference.getAdminObj();
        BmobQuery<Farms> query = new BmobQuery<Farms>();
        BmobQuery<Admin> innerQuery = new BmobQuery<Admin>();
        innerQuery.addWhereEqualTo("invitationCode",invicode);
        query.addWhereDoesNotExists("User");
        query.addWhereMatchesQuery("admin", "Admin",innerQuery);
        query.findObjects(new FindListener<Farms>() {
            @Override
            public void done(List<Farms> list, BmobException e) {
                if (e == null) {
                    ArrayList<String> farmNames = new ArrayList<String>();
                    final ArrayList<Farms> farmsArrayList = new ArrayList<Farms>();
                    for (Farms farm : list) {
                        String farmName = farm.getFarmsName();
                        farmNames.add(farmName);
                        farmsArrayList.add(farm);
                    }
                    final String[] arrString = (String[]) farmNames.toArray(new String[0]);
                    builder.setItems(arrString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Farms farms=farmsArrayList.get(i);
                            Toast.makeText(getApplicationContext(), farms.getFarmsName() + "的地址为：" + farms.getAdress(), Toast.LENGTH_SHORT).show();
                            acitvityfarms=farms;
                            theSelectFarm.setText(farms.getFarmsName());
                        }
                    });
                    builder.setCancelable(true);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败 ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });
    }

    private void selectFarmAdd(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("farmsid",acitvityfarms.getObjectId());
        editor.putString("farmsname",acitvityfarms.getFarmsName());
        editor.apply();
        User curruser= BmobUser.getCurrentUser(User.class);
        User user=new User();
        user.setObjectId(curruser.getObjectId());
        Farms nefarms=new Farms();
        nefarms.setUser(user);
        nefarms.update( acitvityfarms.getObjectId() ,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "添加成功 ", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "添加失败 ", Toast.LENGTH_SHORT).show();
                    editor.clear();
                    e.printStackTrace();
                }
            }
        });
    }
}
