package com.example.luhongcheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luhongcheng.BBox.AnLi;
import com.example.luhongcheng.BBox.EatFood;
import com.example.luhongcheng.BBox.Learning;
import com.example.luhongcheng.BBox.Love;
import com.example.luhongcheng.BBox.ManyWorks;
import com.example.luhongcheng.BBox.PeopleSay;
import com.example.luhongcheng.BBox.ToDayBest;
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
        Box orange = new Box("#今日最佳#", R.drawable.best);
        fruitList.add(orange);
        Box apple = new Box("#众话说#", R.drawable.talk);
        fruitList.add(apple);
        Box banana = new Box("#表白墙#", R.drawable.love);
        fruitList.add(banana);
        Box watermelon = new Box("#学习交流#", R.drawable.learn);
        fruitList.add(watermelon);
        Box pear = new Box("#安利#", R.drawable.anli);
        fruitList.add(pear);
        Box grape = new Box("#一日三餐#", R.drawable.food);
        fruitList.add(grape);
        Box pineapple = new Box("#需求池#", R.drawable.xuqiu);
        fruitList.add(pineapple);
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
                            Intent intent = new Intent(MoreBox.this, ToDayBest.class);
                            startActivity(intent);
                            break;
                        case 1:
                            //众话说
                            Intent intent1 = new Intent(MoreBox.this, PeopleSay.class);
                            startActivity(intent1);
                            break;
                        case 2:
                            //表白墙
                            Intent intent2 = new Intent(MoreBox.this, Love.class);
                            startActivity(intent2);
                            break;
                        case 3:
                            //学习交流
                            Intent intent3 = new Intent(MoreBox.this, Learning.class);
                            startActivity(intent3);
                            break;
                        case 4:
                            //安利
                            Intent intent4 = new Intent(MoreBox.this, AnLi.class);
                            startActivity(intent4);
                            break;
                        case 5:
                            //一日三餐
                            Intent intent5 = new Intent(MoreBox.this, EatFood.class);
                            startActivity(intent5);
                            break;
                        case 6:
                            //需求池
                            Intent intent6 = new Intent(MoreBox.this, ManyWorks.class);
                            startActivity(intent6);
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
         }


        @Override
        public int getItemCount() {
            return mFruitList.size();
        }
    }




}
