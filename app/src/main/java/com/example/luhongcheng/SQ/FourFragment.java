package com.example.luhongcheng.SQ;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luhongcheng.Box;
import com.example.luhongcheng.MBox.MBoxItem;
import com.example.luhongcheng.R;

import java.util.ArrayList;
import java.util.List;

public class FourFragment extends Fragment {

    public static FourFragment newInstance() {
        return new FourFragment();
    }
    private List<Box> fruitList = new ArrayList<Box>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.sq_fragment_three, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initFruits2();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view2);
        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager2);
        BoxAdapter2 adapter2 = new BoxAdapter2(fruitList);
        recyclerView.setAdapter(adapter2);

    }



    private void initFruits2() {
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

    }

    class BoxAdapter2 extends RecyclerView.Adapter<BoxAdapter2.ViewHolder>{
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

        public BoxAdapter2(List<Box> fruitList) {
            mFruitList = fruitList;
        }

        @Override
        public BoxAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_item3, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.fruitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Box fruit = mFruitList.get(position);
                    switch(position){
                        case 0:
                            //今日最佳
                            Intent intent = new Intent(getActivity(), MBoxItem.class);
                            intent.putExtra("flag","A1");
                            startActivity(intent);
                            break;
                        case 1:
                            //众话说
                            Intent intent1 = new Intent(getActivity(), MBoxItem.class);
                            intent1.putExtra("flag","A4");
                            startActivity(intent1);
                            break;
                        case 2:
                            //表白墙
                            Intent intent2 = new Intent(getActivity(), MBoxItem.class);
                            intent2.putExtra("flag","A3");
                            startActivity(intent2);
                            break;
                        case 3:
                            //学习交流
                            Intent intent3 = new Intent(getActivity(), MBoxItem.class);
                            intent3.putExtra("flag","A6");
                            startActivity(intent3);
                            break;
                        case 4:
                            //安利
                            Intent intent4 = new Intent(getActivity(), MBoxItem.class);
                            intent4.putExtra("flag","A7");
                            startActivity(intent4);
                            break;
                        case 5:
                            //一日三餐
                            Intent intent5 = new Intent(getActivity(), MBoxItem.class);
                            intent5.putExtra("flag","A2");
                            startActivity(intent5);
                            break;
                        case 6:
                            //需求池
                            Intent intent6 = new Intent(getActivity(), MBoxItem.class);
                            intent6.putExtra("flag","A8");
                            startActivity(intent6);
                            break;
                        case 7:
                            //工具推荐
                            Intent intent7 = new Intent(getActivity(), MBoxItem.class);
                            intent7.putExtra("flag","A5");
                            startActivity(intent7);
                            break;
                        case 8:
                            //考研党
                            Intent intent8 = new Intent(getActivity(), MBoxItem.class);
                            intent8.putExtra("flag","A9");
                            startActivity(intent8);
                            break;
                        case 9:
                            //周边推荐
                            Intent intent9 = new Intent(getActivity(), MBoxItem.class);
                            intent9.putExtra("flag","A10");
                            startActivity(intent9);
                            break;
                        case 10:
                            //每日一听
                            Intent intent10 = new Intent(getActivity(), MBoxItem.class);
                            intent10.putExtra("flag","A11");
                            startActivity(intent10);
                            break;
                        case 11:
                            //晨读打卡
                            Intent intent11 = new Intent(getActivity(), MBoxItem.class);
                            intent11.putExtra("flag","A12");
                            startActivity(intent11);
                            break;
                        default:
                            break;
                    }

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
