package com.example.luhongcheng.SQ;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.R;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PingLun extends AppCompatActivity {
    String ssID = null;//说说ID
    String plID = null;//评论ID
    ListView listView;
    EditText msg;
    ImageView send_msg;
    List<String> content = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinglun);


        listView = (ListView)findViewById(R.id.pinglun_listView);
        msg = (EditText)findViewById(R.id.msg);
        send_msg=(ImageView)findViewById(R.id.send_msg);

        msg.clearFocus();

        ssID = getIntent().getStringExtra("pinglun");
       // Log.i("传递的ssID",ssID);
        getPingLun();

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send_Msg();
            }

        });
    }


    private void Send_Msg() {
        String A = msg.getText().toString();
        content.add(A);

        com.example.luhongcheng.Bmob_bean.PingLun object = new com.example.luhongcheng.Bmob_bean.PingLun();
        object.setPl(content);
        object.update(plID, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                   // Log.i("发送评论","更新成功");
                    Toast.makeText(getApplicationContext(),"评论成功",Toast.LENGTH_SHORT).show();
                }else{
                   // Log.i("发送评论","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }


        });


    }


    private void getPingLun() {

        final BmobQuery<com.example.luhongcheng.Bmob_bean.PingLun> query = new BmobQuery<com.example.luhongcheng.Bmob_bean.PingLun>();
        query.addWhereEqualTo("ssID", ssID);
        query.findObjects(new FindListener<com.example.luhongcheng.Bmob_bean.PingLun>() {
            @Override
            public void done(List<com.example.luhongcheng.Bmob_bean.PingLun> object, BmobException e) {
                if(e==null){
                    for (com.example.luhongcheng.Bmob_bean.PingLun xixi : object) {
                        plID= xixi.getObjectId();
                    }
                    if (plID != null){
                        getPing(plID);
                    }
                   // Log.i("ssID",plID);
                }else{
                   // Log.i("Bmob是否有ssID","失败："+e.getMessage()+","+e.getErrorCode());

                    com.example.luhongcheng.Bmob_bean.PingLun xixi = new com.example.luhongcheng.Bmob_bean.PingLun();
                    xixi.setSsID(ssID);
                    xixi.setPl(content);
                    xixi.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e1) {
                            if(e1==null){
                              //  Log.i("创建ssID","更新成功");
                            }else{
                               // Log.i("创建ssID","更新失败："+e1.getMessage());
                            }
                        }
                    });



                }
            }
        });


    }

    private void getPing(String plID) {
        BmobQuery< com.example.luhongcheng.Bmob_bean.PingLun> query = new BmobQuery< com.example.luhongcheng.Bmob_bean.PingLun>();
        query.addWhereEqualTo("objectId",plID);
        query.findObjects(new FindListener< com.example.luhongcheng.Bmob_bean.PingLun>() {
            @Override
            public void done(List< com.example.luhongcheng.Bmob_bean.PingLun> object, BmobException e) {
                if(e==null){
                   // Log.i("查询评论内容","查询成功：共" + object.size() + "条数据。");
                    content = object.get(0).getPl();
                    listView.setAdapter(new PLAdaper(content));
                }else{
                    //Log.i("查询评论内容","失败："+e.getMessage());
                }
            }

        });

    }

    public class PLAdaper extends BaseAdapter {
        private List<String> list;
        public PLAdaper(List<String> list) {
            super();
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinglun_item, null);
                holder = new ViewHolder();
                holder.content =(TextView) convertView.findViewById(R.id.content);
                holder.nickname =(TextView)convertView.findViewById(R.id.nickname);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.content.setText(list.get(position));
            //Random random = new Random();
            //int i = random.nextInt(100);
            //holder.nickname.setText("匿名用户"+i);

            return convertView;
        }

        class ViewHolder {
            ImageView icon;
            TextView nickname,content;

        }

    }


    public void close(View view) {
        this.finish();
    }


}
