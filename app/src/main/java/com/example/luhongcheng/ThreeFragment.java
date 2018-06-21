package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.example.luhongcheng.WeiXin.gongzonghao;
import com.example.luhongcheng.about.about1;
import com.example.luhongcheng.userCard.userCardinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/4/8.
 */

@SuppressLint("ValidFragment")
public class ThreeFragment extends Fragment {
    private String context;
    public ThreeFragment(String context){
        this.context = context;
    }

    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.c_fragment,container,false);
        return view;
    }


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*gridview.java*/
        gridView = (GridView) getView().findViewById(R.id.gridview);
        //初始化数据
        initData();

        String[] from={"ItemImage","ItemText"};

        int[] to={R.id.ItemImage,R.id.ItemText};

        adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item2, from, to);

        gridView.setAdapter(adapter);


        /* 给item设置点击事件*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent=new Intent(getActivity(),gongzonghao.class);
                        intent.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529511015&ver=1&signature=17xgy84huSXaJbwcLxEivfsd49TyGhqcwf4ABU82GM7CzLhEkp8S5qbGRmYa161dySjVYK*0oybMDfGbzcjMTA==");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(getActivity(),gongzonghao.class);
                        intent1.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529491422&ver=1&signature=WN8mIQnPbpnui6zobJHdirpHPhM5EJavnyBnh30gyyzjFH3J39vixyXm8KRe*uJt-bZSvfzzHUq3*VLI9gioag==");
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(getActivity(),gongzonghao.class);
                        intent2.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529491443&ver=1&signature=ROmUZTk3WtkGtiskfv-Pkqwj1lo1anNFcReVXXN7Aw0i*-7wLGPZTeItTYI35Zi3Q0D4CJ880UjidHSV7HZ4ZQ==");
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3=new Intent(getActivity(),gongzonghao.class);
                        intent3.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529491459&ver=1&signature=kZf-0QUbNIybhegXlr0lOHfjIW-ZGEc8gTh84-EG4GrH0dfDD7IOmyKXFg7Lr-2uaJwZad1WpYrgIaPHQUIuzQ==");
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4=new Intent(getActivity(),gongzonghao.class);
                        intent4.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529491484&ver=1&signature=FrkDNAddqUXozb0nd5WfT7PBooNV*0XhTkd3HnQElwA21UVNvDFOgmNqGSHpj5t8Acez2J4UL9efRYGA9MISbg==");
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5=new Intent(getActivity(),gongzonghao.class);
                        intent5.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529491500&ver=1&signature=rtT3yN8TAt7v6A9F4-0mcju-FdLXPJSJhaA7QgoT5YUBPpwoifV7bwYQ3J5yes1za-fw-hnlKug*mOaY1VJZ3w==");
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6=new Intent(getActivity(),gongzonghao.class);
                        intent6.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529491519&ver=1&signature=9tg9d3XEBy5nV38iYyZzITe84TCzpdYArOQgjoroP-UX3kdYp5Im4ayBZzhQobtfXnpBqS*ANsKwHjSy7oby*w==");
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7=new Intent(getActivity(),gongzonghao.class);
                        intent7.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529491542&ver=1&signature=u2PnYzFLOcen02NbvDbArwfBZYtb-d*vI-pCYORhQ2PEa5uf0HZWQfXTc2MuHs9pgzUgzMl-jmaDoBy47se9cQ==");
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(getActivity(),gongzonghao.class);
                        intent8.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529494429&ver=1&signature=GS8*EkVHMqmufQ7Od6iBuxwo3ZaBeb0CW6Kylep7*klCvsPRln2mRwp*xDkgfP0KP9QTCc4g9xTW9IR6zn4*ZA==");
                        startActivity(intent8);
                        break;
                    case 9:
                        Intent intent9 = new Intent(getActivity(),gongzonghao.class);
                        intent9.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529494429&ver=1&signature=BvVByJWr0s7Ep-G2BcVgZyJykXcpYOSvH6-s3IVG2VdBcGXY9zi*GhBxsAVbitvR4sUZscsHWKjPbn2SpEUZ*g==");
                        startActivity(intent9);
                        break;
                    case 10:
                        Intent intent10 = new Intent(getActivity(),gongzonghao.class);
                        intent10.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529494429&ver=1&signature=5WFbRs3XOrcVg1mCC22f4z7aAWrulMCvrwXnp5qjHZ*GEiTdeJ9DHkBFWGN0cvWnNo*ecKyOeWk-ysqf91mMtQ==");
                        startActivity(intent10);
                        break;
                    case 11:
                        Intent intent11 = new Intent(getActivity(),gongzonghao.class);
                        intent11.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529494429&ver=1&signature=LMAKwfhXY7ScrJLDn8di8PnvEHqt9ZlAdUC285M0LmygFAh15NrzuBrhhBhwfIwUNoR91YEa4GKh4Bq2SxuHKQ==");
                        startActivity(intent11);
                        break;
                    case 12:
                        Intent intent12 = new Intent(getActivity(),gongzonghao.class);
                        intent12.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529494429&ver=1&signature=u2aLUwF1muTUwc3AgwreTQfKKEp5TCT0z4gnQ0tu1tZbJ8xtj4hXJ9rPdu*aOQ1ThGGmGCgDJBLBOTSEmhUFdw==");
                        startActivity(intent12);
                        break;
                    case 13:
                        Intent intent13 = new Intent(getActivity(),gongzonghao.class);
                        intent13.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529494429&ver=1&signature=f71Zr5QQH3RLxzOBVEAXI36wE82KYFa94WWoK*m9i6H8yft3yKJKSLM84bqDJaVlihEFDrxAUoJVHQ2ESHD70A==");
                        startActivity(intent13);
                        break;
                    case 14:
                        Intent intent14 = new Intent(getActivity(),gongzonghao.class);
                        intent14.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529494429&ver=1&signature=6WGI0wFN7GUtN*DoVRkIVksUFNz22aAEUHZiGtgKeoTxkFS9jgS6DRs70GZe3JMl728t1qR-vU-6K7PUtPJBhw==");
                        startActivity(intent14);
                        break;
                    case 15:
                        Intent intent15 = new Intent(getActivity(),gongzonghao.class);
                        intent15.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529495385&ver=1&signature=eZKVia9PwA0NXknLVhM1U-liaA*nMEjSjCE6OUiBfOO-p1DjD*c4o7qo2YIi6mCrASN0P6pyftkrnxf4-CV2*g==");
                        startActivity(intent15);
                        break;
                    case 16:
                        Intent intent16 = new Intent(getActivity(),gongzonghao.class);
                        intent16.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529495385&ver=1&signature=QdQ1eS0Cm8Z0twkShCGpk3Wj4WqExao1Vs4i4ODiJoKcthd24daL*zlyTmjw2LJp8lY4xhkQkP9vw5J76GQSVQ==");
                        startActivity(intent16);
                        break;
                    case 17:
                        Intent intent17 = new Intent(getActivity(),gongzonghao.class);
                        intent17.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529495385&ver=1&signature=8WNsR*f2Tc6EDKok43RvEHRucSPQgKY7LyGYbqI6wD8y4IZNVWz26Wfu6WWk40kiut0MF6nvIj0HE4C5LUBr*g==");
                        startActivity(intent17);
                        break;
                    case 18:
                        Intent intent18 = new Intent(getActivity(),gongzonghao.class);
                        intent18.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529495560&ver=1&signature=AfP5g9yLOK6Bek-pUm*3nk6KF02ViOrkRs8inFocTqfrkAqu7M2IwRZFqr7NU6eiQKe8bPVCtUKZTI-OHKuDdA==");
                        startActivity(intent18);
                        break;
                    case 19:
                        Intent intent19 = new Intent(getActivity(),gongzonghao.class);
                        intent19.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529495560&ver=1&signature=NQM7Pf6TDCo4daGe2OHz6TG2AS0ayp73zg22ZDGf4qKlkSwlM-fHr*mosb*b0VxeOp6K*R*qfBovFCNs1Gxsvg==");
                        startActivity(intent19);
                        break;
                    case 20:
                        Intent intent20 = new Intent(getActivity(),gongzonghao.class);
                        intent20.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529495560&ver=1&signature=aFpjT61taU1jx9OckfXnvyrmCzgWWaqZ1RrSOOV8lQw-vivKPKlrnQwSpYswZoz7Caibhr30Wub8BKKTNpLekA==");
                        startActivity(intent20);
                        break;
                    case 21:
                        Intent intent21 = new Intent(getActivity(),gongzonghao.class);
                        intent21.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529495865&ver=1&signature=ATrERgHAdXplhjgnr5we45bMAm7wyiQcCcUAHBY-yc9RBtYVd-Ycvc0IvzAL9r*IE5Dn10nzgwnZ7cGU5dhVyg==");
                        startActivity(intent21);
                        break;
                    case 22:
                        Intent intent22 = new Intent(getActivity(),gongzonghao.class);
                        intent22.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529495865&ver=1&signature=t08Z2de1rv0ISMSl7Qb6qpCDyNABcQAxyNEibmvNMaJ-G3O1HVmJ5uNZunu2FDcHPio9848k*guuzn7O8votkw==");
                        startActivity(intent22);
                        break;
                    case 23:
                        Intent intent23 = new Intent(getActivity(),gongzonghao.class);
                        intent23.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496234&ver=1&signature=9tg9d3XEBy5nV38iYyZzITe84TCzpdYArOQgjoroP-UX3kdYp5Im4ayBZzhQobtfnbinr6W9ZLymz1aTRWxpNg==");
                        startActivity(intent23);
                        break;
                    case 24:
                        Intent intent24 = new Intent(getActivity(),gongzonghao.class);
                        intent24.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496234&ver=1&signature=xAaVrvMn1w621G7xmi8kKr45mt4cYjDkvWp1bMfVfQja0ZreXvqagx6mmfciNP2mqjp5fUSO6eqNYIkicbHUtw==");
                        startActivity(intent24);
                        break;
                    case 25:
                        Intent intent25 = new Intent(getActivity(),gongzonghao.class);
                        intent25.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496234&ver=1&signature=TjeNPsGtujKawY6L0WjSj9hMSg05fcwWhwhWYdWwXsWRprB-RICkcq9kOwpf3dDCKKf2iG4SlOXZnXS6rxSWOw==");
                        startActivity(intent25);
                        break;
                    case 26:
                        Intent intent26 = new Intent(getActivity(),gongzonghao.class);
                        intent26.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496410&ver=1&signature=1t9lyf8M5gQy8Kw9g0pSZDKIAxV0jTa2FeoLlcMhJro6RwAdiQpv5spzHpB0MaNDi5WyZvh3s3r3DSfRlOc80A==");
                        startActivity(intent26);
                        break;
                    case 27:
                        Intent intent27 = new Intent(getActivity(),gongzonghao.class);
                        intent27.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496410&ver=1&signature=O466Lup2n7WwNq7JKaebFBC5UPJKrHiIQEYRf8s9LaV4wotK2IXrDDdctmW4mMkLf4a2qzSesEfMvYx6C6q6eQ==");
                        startActivity(intent27);
                        break;
                    case 28:
                        Intent intent28 = new Intent(getActivity(),gongzonghao.class);
                        intent28.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496410&ver=1&signature=uxAlL3xkUnuQGBWAI20VOAkgjXUDY7Dag7VrKumzOH6zW6pY7Bb1JudJpecJ6uWNwql84N8VbI0gChuFVCx8iA==");
                        startActivity(intent28);
                        break;
                    case 29:
                        Intent intent29 = new Intent(getActivity(),gongzonghao.class);
                        intent29.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496584&ver=1&signature=fAKoGBDB95Db-endI7L*-uixymbjZ3iacI6Xd6Uj84RoCZNNA60Y7n0qqCjF5vvB-3IHc8pvnw08*Kczbfh4uQ==");
                        startActivity(intent29);
                        break;
                    case 30:
                        Intent intent30 = new Intent(getActivity(),gongzonghao.class);
                        intent30.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496584&ver=1&signature=3HQbWjdjwrrqih4YLizj6GtvAk8bNH-JTQ0wg9R3m5d4jxcyUQYRqmSpqfnok04ra2h2xa2HFSHI0cVJjfwDmA==");
                        startActivity(intent30);
                        break;
                    case 31:
                        Intent intent31 = new Intent(getActivity(),gongzonghao.class);
                        intent31.putExtra("URL","https://mp.weixin.qq.com/profile?src=3&timestamp=1529496584&ver=1&signature=XPSaIYX7j5GvW*0klj3V4FsgwjVWqVFtu-TuSsa1B-stu1rvVEb37CCLAWo1aIcMjgEjYmA*AlDinks39Hw0RA==");
                        startActivity(intent31);
                        break;

                    default:
                        break;
                }
            }
        });
        /*点击事件设置完毕*/

    }

    void initData() {
        //图标
        int icno[] = { R.drawable.itdada,R.drawable.it1,R.drawable.it2, R.drawable.it3,R.drawable.it4,
                R.drawable.it5,R.drawable.it6,R.drawable.it7,R.drawable.it8,R.drawable.it9,
                R.drawable.it10,R.drawable.it11,R.drawable.it12,R.drawable.it13,R.drawable.it14,
                R.drawable.it15,R.drawable.it16,R.drawable.it17,R.drawable.it18,R.drawable.it19,
                R.drawable.it21,R.drawable.it22,R.drawable.it23,R.drawable.it24, R.drawable.it25,
                R.drawable.it26,R.drawable.it27,R.drawable.it28,R.drawable.it29, R.drawable.it30,
                R.drawable.it31};
        //图标下的文字
        String name[]={"IT大大","SIT社团发展中心","上应青年","应技体育","上应学子",
                "溢彩摄影社","SIT志愿者服务中心","SIT体协","SIT青春经管","sit外院",
                "SIT人文","SIT材料","SIT社区工作站","化工-SIT","SIT合唱团",
        "SIT白玉兰志愿者服务队","SIT吉他社","SIT经管心路心理站","SIT悦牵公益服务队","SIT粵語社",
        "SIT易班经管分站","SIT知行学社","SIT校园足球","SIT志愿者服务中心","SIT10社区工作站"
        ,"SIT校舞蹈队","sit radio","SIT外事办","SIT海外交流","SIT自然科普社",
                "SIT七号社区工作站","SIT星光化工"};

        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ItemImage", icno[i]);
            map.put("ItemText",name[i]);
            dataList.add(map);
        }
    }

}
