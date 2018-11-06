package com.example.luhongcheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luhongcheng.MBox.MBoxItem;

import java.util.ArrayList;
import java.util.List;

public class MoreBox extends AppCompatActivity {
    private List<Box> fruitList = new ArrayList<Box>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morebox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BoxAdapter adapter = new BoxAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits() {
        Box orange = new Box("#今日最佳#", R.drawable.best_min);
        fruitList.add(orange);
        Box apple = new Box("#众话说#", R.drawable.talk_min);
        fruitList.add(apple);
        Box banana = new Box("#表白墙#", R.drawable.love_min);
        fruitList.add(banana);
        Box watermelon = new Box("#学习交流#", R.drawable.learn_min);
        fruitList.add(watermelon);
        Box pear = new Box("#安利#", R.drawable.anli_min);
        fruitList.add(pear);
        Box grape = new Box("#一日三餐#", R.drawable.food_min);
        fruitList.add(grape);
        Box pineapple = new Box("#需求池#", R.drawable.xuqiu_min);
        fruitList.add(pineapple);
        Box A1 = new Box("#工具推荐#", R.drawable.gongju_min);
        fruitList.add(A1);
        Box A3 = new Box("#考研党#", R.drawable.kaoyan);
        fruitList.add(A3);
        Box A4 = new Box("#周边推荐#", R.drawable.tuijian_min);
        fruitList.add(A4);
        Box A5 = new Box("#每日一听#", R.drawable.music_min);
        fruitList.add(A5);
        Box A6 = new Box("#晨读打卡#", R.drawable.read_min);
        fruitList.add(A6);
        Box A7 = new Box("#谈天说地#", R.drawable.talk_lala_min);
        fruitList.add(A7);
    }

     class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder>{
        private List<Box> mFruitList;
        class ViewHolder extends RecyclerView.ViewHolder {
            View fruitView;
            ImageView fruitImage;
            TextView fruitName;
            public ViewHolder(View view) {
                super(view);
                fruitView = view;
                fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
                fruitName = (TextView) view.findViewById(R.id.fruit_name);
            }
        }


        public BoxAdapter(List<Box> fruitList) {
            mFruitList = fruitList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_item2, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.fruitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Box fruit = mFruitList.get(position);
                    switch(position){
                        case 0:
                            //今日最佳
                            Intent intent = new Intent(MoreBox.this, MBoxItem.class);
                            intent.putExtra("flag","A1");
                            startActivity(intent);
                            break;
                        case 1:
                            //众话说
                            Intent intent1 = new Intent(MoreBox.this, MBoxItem.class);
                            intent1.putExtra("flag","A4");
                            startActivity(intent1);
                            break;
                        case 2:
                            //表白墙
                            Intent intent2 = new Intent(MoreBox.this,MBoxItem.class);
                            intent2.putExtra("flag","A3");
                            startActivity(intent2);
                            break;
                        case 3:
                            //学习交流
                            Intent intent3 = new Intent(MoreBox.this, MBoxItem.class);
                            intent3.putExtra("flag","A6");
                            startActivity(intent3);
                            break;
                        case 4:
                            //安利
                            Intent intent4 = new Intent(MoreBox.this, MBoxItem.class);
                            intent4.putExtra("flag","A7");
                            startActivity(intent4);
                            break;
                        case 5:
                            //一日三餐
                            Intent intent5 = new Intent(MoreBox.this, MBoxItem.class);
                            intent5.putExtra("flag","A2");
                            startActivity(intent5);
                            break;
                        case 6:
                            //需求池
                            Intent intent6 = new Intent(MoreBox.this, MBoxItem.class);
                            intent6.putExtra("flag","A8");
                            startActivity(intent6);
                            break;
                        case 7:
                            //需求池
                            Intent intent7 = new Intent(MoreBox.this, MBoxItem.class);
                            intent7.putExtra("flag","A5");
                            startActivity(intent7);
                            break;
                        case 8:
                            //需求池
                            Intent intent8 = new Intent(MoreBox.this, MBoxItem.class);
                            intent8.putExtra("flag","A9");
                            startActivity(intent8);
                            break;
                        case 9:
                            //需求池
                            Intent intent9 = new Intent(MoreBox.this, MBoxItem.class);
                            intent9.putExtra("flag","A10");
                            startActivity(intent9);
                            break;
                        case 10:
                            //需求池
                            Intent intent10 = new Intent(MoreBox.this, MBoxItem.class);
                            intent10.putExtra("flag","A11");
                            startActivity(intent10);
                            break;
                        case 11:
                            //需求池
                            Intent intent11 = new Intent(MoreBox.this, MBoxItem.class);
                            intent11.putExtra("flag","A12");
                            startActivity(intent11);
                            break;
                        case 12:
                            //需求池
                            Intent intent12 = new Intent(MoreBox.this, MBoxItem.class);
                            intent12.putExtra("flag","A13");
                            startActivity(intent12);
                            break;

                        default:
                            break;
                    }

                }
            });

            return holder;
        }

         @Override
         public void onBindViewHolder(ViewHolder holder, int position) {
             Box fruit = mFruitList.get(position);
             holder.fruitImage.setImageResource(fruit.getImageId());
             holder.fruitName.setText(fruit.getName());

             ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
             layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
         }


        @Override
        public int getItemCount() {
            return mFruitList.size();
        }
    }




}
