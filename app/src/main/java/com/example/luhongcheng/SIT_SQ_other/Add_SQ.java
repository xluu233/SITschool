package com.example.luhongcheng.SIT_SQ_other;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.SQ;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.utils.CompressImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Add_SQ extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate{
    EditText content;
    TextView send;
    Toolbar toolbar;

    BGASortableNinePhotoLayout Photos;
    List<String> Path = new ArrayList<>(); //选择图片的path地址集合
    int select_times = 0; //选择图片的次数

    LinearLayout  status_layout;
    LinearLayout  pic_layout;
    ProgressBar  progressBar;
    TextView  status_text;

    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";

    int send_time = 0;
    String last_b = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.add_sq);
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));//设置状态栏背景色
        }

        initView();
        onClick();
        init();

    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        content = findViewById(R.id.add_content);
        send = findViewById(R.id.ad_send);
        toolbar = findViewById(R.id.ad_toolbar);
        Photos = findViewById(R.id.add_photos);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        status_layout = findViewById(R.id.ad_status);
        progressBar = findViewById(R.id.ad_progress);
        status_text = findViewById(R.id.ad_status_text);
        pic_layout = findViewById(R.id.ad_layout);

    }

    private void onClick() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (send_time>=1){
                    Snackbar.make(v,"请15s后重试",Toast.LENGTH_SHORT).show();
                }else {
                    send_time++;
                    String b = content.getText().toString();
                    if (Path.size() == 0 ){
                        if (b.length() !=0){
                            status_layout.setVisibility(View.VISIBLE);
                            pic_layout.setVisibility(View.INVISIBLE);
                            update_message_no_pic();
                        }
                    }else {
                        status_layout.setVisibility(View.VISIBLE);
                        pic_layout.setVisibility(View.INVISIBLE);
                        update_image();
                    }
                }

                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        send_time = 0;
                    }
                }, 15000);


                // Log.d("xixi", String.valueOf(Path));
            }
        });
    }

    private void update_image() {
        final String[] list = new String[Path.size()];

        //图片压缩
        for (int i=0;i<Path.size();i++ ){
            list[i] = CompressImageUtil.saveBitmap2file(BitmapFactory.decodeFile(Path.get(i)),getApplicationContext());
        }

        BmobFile.uploadBatch(list, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files,List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if(urls.size()==Path.size()){//如果数量相等，则代表文件全部上传完成
                    Log.d("反馈url", String.valueOf(urls));
                    update_message(urls);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                Log.d("反馈错误码:",statuscode +",错误描述："+errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
  /*              Log.d("反馈", String.valueOf(curIndex));
                Log.d("反馈", String.valueOf(curPercent));
                Log.d("反馈", String.valueOf(total));*/
                Log.d("反馈-总上传进度：", totalPercent+"%");
                status_text.setText("上传中："+totalPercent+"%");
            }
        });

    }

    private void update_message(List<String> urls) {
        SQ qa = new SQ();
        qa.setContent(content.getText().toString());

        SharedPreferences sp=getSharedPreferences("personID",0);
        String personID =  sp.getString("ID","");


        if (BmobUser.isLogin()){
            if (urls.size() != 0 ){
                qa.setImage(urls);
            }
            //添加一对一关联，设置作者为Userinfo表中id的用户
            UserInfo xixi = new UserInfo();
            xixi.setObjectId(personID);
            qa.setAuthor(xixi);
            qa.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        //成功
                        //Log.d("反馈","成功");
                        status_layout.setVisibility(View.INVISIBLE);
                        pic_layout.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"发布成功，积分+10",Toast.LENGTH_SHORT).show();
                    }else{
                        //toast("创建数据失败：" + e.getMessage());
                        status_layout.setVisibility(View.INVISIBLE);
                        pic_layout.setVisibility(View.VISIBLE);
                    }
                }
            });


        }else {
            Toast.makeText(getApplicationContext(),"不是登录状态",Toast.LENGTH_SHORT).show();
        }


    }

    private void update_message_no_pic() {
        SQ qa = new SQ();
        qa.setContent(content.getText().toString());

        SharedPreferences sp=getSharedPreferences("personID",0);
        String personID =  sp.getString("ID","");


        if (BmobUser.isLogin()){
            //添加一对一关联，设置作者为Userinfo表中id的用户
            UserInfo xixi = new UserInfo();
            xixi.setObjectId(personID);
            qa.setAuthor(xixi);
            qa.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        //成功
                        //Log.d("反馈","成功");
                        status_layout.setVisibility(View.INVISIBLE);
                        pic_layout.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"发布成功，积分+10",Toast.LENGTH_SHORT).show();
                    }else{
                        //toast("创建数据失败：" + e.getMessage());
                        status_layout.setVisibility(View.INVISIBLE);
                        pic_layout.setVisibility(View.VISIBLE);
                    }
                }
            });


        }else {
            Toast.makeText(getApplicationContext(),"不是登录状态",Toast.LENGTH_SHORT).show();
        }


    }

    private void init() {
        Photos.setMaxItemCount(9);
        Photos.setEditable(true);
        Photos.setPlusEnable(true);
        Photos.setSortable(true);

        // 设置拖拽排序控件的代理
        Photos.setDelegate(this);
    }

    public static Moment getMoment(Intent intent) {
        return intent.getParcelableExtra(EXTRA_MOMENT);
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        select_times++;
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .maxChooseCount(Photos.getMaxItemCount() - Photos.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);

        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }


    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }


    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Photos.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(Photos.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        Toast.makeText(this, "排序发生变化", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
/*            if (select_times == 1) {
                Photos.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                Path = BGAPhotoPickerActivity.getSelectedPhotos(data);
            } else {
                Photos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                Path.addAll(BGAPhotoPickerActivity.getSelectedPhotos(data));
            }*/

            Photos.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            Path = BGAPhotoPickerActivity.getSelectedPhotos(data);
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            Photos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
            Path = BGAPhotoPickerActivity.getSelectedPhotos(data);
        }


    }



}