package com.example.luhongcheng.MainFragment_One;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luhongcheng.MBox.MBoxItem;
import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.Box;

import java.util.ArrayList;
import java.util.List;

public class One_second_fragment extends Fragment {


    private List<Box> fruitList = new ArrayList<Box>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.a_two_viewpager, container, false);
        return v;
    }


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initFruits();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view222);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BoxAdapter adapter = new BoxAdapter(fruitList);
        recyclerView.setAdapter(adapter);

    }

    private void initFruits() {
        Box orange = new Box(R.string.A1, R.drawable.best_min);
        fruitList.add(orange);
        Box apple = new Box(R.string.A2, R.drawable.talk_min);
        fruitList.add(apple);
        Box banana = new Box(R.string.A3, R.drawable.love_min);
        fruitList.add(banana);
        Box watermelon = new Box(R.string.A4, R.drawable.learn_min);
        fruitList.add(watermelon);
        Box pear = new Box(R.string.A5, R.drawable.anli_min);
        fruitList.add(pear);
        Box grape = new Box(R.string.A6, R.drawable.food_min);
        fruitList.add(grape);
        Box pineapple = new Box(R.string.A7, R.drawable.xuqiu_min);
        fruitList.add(pineapple);
        Box A1 = new Box(R.string.A8, R.drawable.gongju_min);
        fruitList.add(A1);
        Box A3 = new Box(R.string.A9, R.drawable.kaoyan);
        fruitList.add(A3);
        Box A4 = new Box(R.string.A10, R.drawable.tuijian_min);
        fruitList.add(A4);
        Box A5 = new Box(R.string.A11, R.drawable.music_min);
        fruitList.add(A5);
        Box A6 = new Box(R.string.A12, R.drawable.read_min);
        fruitList.add(A6);
    }

    class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder> {
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
            final BoxAdapter.ViewHolder holder = new BoxAdapter.ViewHolder(view);
            holder.fruitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Box fruit = mFruitList.get(position);
                    switch(position){
                        case 0:
                            //今日最佳
                            Intent intent = new Intent(getContext(), MBoxItem.class);
                            intent.putExtra("flag","A1");
                            startActivity(intent);
                            break;
                        case 1:
                            //众话说
                            Intent intent1 = new Intent(getContext(), MBoxItem.class);
                            intent1.putExtra("flag","A4");
                            startActivity(intent1);
                            break;
                        case 2:
                            //表白墙
                            Intent intent2 = new Intent(getContext(),MBoxItem.class);
                            intent2.putExtra("flag","A3");
                            startActivity(intent2);
                            break;
                        case 3:
                            //学习交流
                            Intent intent3 = new Intent(getContext(), MBoxItem.class);
                            intent3.putExtra("flag","A6");
                            startActivity(intent3);
                            break;
                        case 4:
                            //安利
                            Intent intent4 = new Intent(getContext(), MBoxItem.class);
                            intent4.putExtra("flag","A7");
                            startActivity(intent4);
                            break;
                        case 5:
                            //一日三餐
                            Intent intent5 = new Intent(getContext(), MBoxItem.class);
                            intent5.putExtra("flag","A2");
                            startActivity(intent5);
                            break;
                        case 6:
                            //需求池
                            Intent intent6 = new Intent(getContext(), MBoxItem.class);
                            intent6.putExtra("flag","A8");
                            startActivity(intent6);
                            break;
                        case 7:
                            //需求池
                            Intent intent7 = new Intent(getContext(), MBoxItem.class);
                            intent7.putExtra("flag","A5");
                            startActivity(intent7);
                            break;
                        case 8:
                            //需求池
                            Intent intent8 = new Intent(getContext(), MBoxItem.class);
                            intent8.putExtra("flag","A9");
                            startActivity(intent8);
                            break;
                        case 9:
                            //需求池
                            Intent intent9 = new Intent(getContext(), MBoxItem.class);
                            intent9.putExtra("flag","A10");
                            startActivity(intent9);
                            break;
                        case 10:
                            //需求池
                            Intent intent10 = new Intent(getContext(), MBoxItem.class);
                            intent10.putExtra("flag","A11");
                            startActivity(intent10);
                            break;
                        case 11:
                            //需求池
                            Intent intent11 = new Intent(getContext(), MBoxItem.class);
                            intent11.putExtra("flag","A12");
                            startActivity(intent11);
                            break;
                        case 12:
                            //需求池
                            Intent intent12 = new Intent(getContext(), MBoxItem.class);
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
