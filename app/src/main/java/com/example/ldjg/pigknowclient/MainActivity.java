package com.example.ldjg.pigknowclient;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ldjg.pigknowclient.DB.Farms;
import com.example.ldjg.pigknowclient.DB.Record;
import com.example.ldjg.pigknowclient.DB.User;
import com.example.ldjg.pigknowclient.Util.ShowDialog;
import com.example.ldjg.pigknowclient.dummy.DummyContent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{
    private final int GET_PERMISSION_REQUEST = 100; //权限申请自定义码
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private long firstTime = 0;


    @BindView(R.id.viewpage)
    ViewPager viewpage;

    @BindView(R.id.tl)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MyPageAdapter myPageAdapter=new MyPageAdapter(getSupportFragmentManager());
        viewpage.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewpage);
        final User user =  BmobUser.getCurrentUser(User.class);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Intent intent=new Intent(MainActivity.this,TakephotoActivity.class);
//                startActivity(intent);
                if (sharedPreferences.getString("farmsid","") == null || user.getMobilePhoneNumberVerified()!=null || user.getMobilePhoneNumber() == null) {
                    if (sharedPreferences.getString("farmsid","") == ""||sharedPreferences.getString("installationId","")=="") {
//                        getPermissions();
                      setFarms();

                    } else if (sharedPreferences.getString("farmsid","") != "" && user.getMobilePhoneNumber() == null ) {
//                    ShowDialog.showFillPhoneDialog(MainActivity.this);
                        getPermissions();
//                    } else if (user.getFarms() != null && user.getMobilePhoneNumber() != null && !user.getMobilePhoneNumberVerified()) {
//                        ShowDialog.showCheckPhoneDialog(MainActivity.this);
                }else {
                        Toast.makeText(MainActivity.this,"出现未知错误,请联系管理员",Toast.LENGTH_LONG).show();
//                        getPermissions();
                    }
                } else {
                    getPermissions();
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_quit) {
            User.logOut();
            sharedPreferences.edit().clear();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            return true;
        } else {
            if (id == R.id.action_add) {
                Intent intent=new Intent(MainActivity.this, SelectFarmsActivity.class);
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFarms() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Farms> query = new BmobQuery<Farms>();
        query.addWhereEqualTo("User", user);
        query.include("admin");
        query.findObjects(new FindListener<Farms>() {
            @Override
            public void done(List<Farms> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        ShowDialog.showFillFarmsDialog(MainActivity.this);
                    } else {
                        Farms farms = list.get(0);
                        editor = sharedPreferences.edit();
                        editor.putString("farmsid",farms.getObjectId());
                        editor.putString("farmsname",farms.getFarmsName());
                        editor.putString("installationId", farms.getAdmin().getInstalId());
                        editor.apply();
                        Toast.makeText(MainActivity.this, "查询农场成功"+farms.getAdmin().getInstalId(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "查询农场失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                startActivityForResult(new Intent(MainActivity.this, TakephotoActivity.class), 100);
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
            }
        } else {
            startActivityForResult(new Intent(MainActivity.this, TakephotoActivity.class), 100);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            Log.i("CJT", "picture");
            String path = data.getStringExtra("path");
//            photo.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        if (resultCode == 102) {
            Log.i("CJT", "video");
            String path = data.getStringExtra("path");
        }
        if (resultCode == 103) {
            Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[2];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    startActivityForResult(new Intent(MainActivity.this, TakephotoActivity.class), 100);
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onListFragmentInteraction(Record item) {
//        Toast.makeText(this,item.id+item.details,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, RecordDetailActivity.class);
        intent.putExtra("pig_data", item);
        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ( secondTime - firstTime < 2000) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
