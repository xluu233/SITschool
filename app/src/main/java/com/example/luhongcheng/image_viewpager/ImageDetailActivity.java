package com.example.luhongcheng.image_viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.luhongcheng.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageDetailActivity extends Activity implements OnClickListener, OnLongClickListener {
	ZoomDragImageViewPager vp;
	Map<Integer, Bitmap> bigBitmapsCache = new HashMap<>();
	public static int url_path = 0;
	public static int local_file_path = 1;
	int pathType ;

	TextView title,content,save;

	//context,url集合，默认position，数据类型（网络或本地）
	public static Intent getMyStartIntent(Context c, List<String> screenshot_samples, int defaultPos, int pathType) {
		Intent intent = new Intent(c, ImageDetailActivity.class);
		intent.putExtra("pos", defaultPos);
		intent.putExtra("pathType", pathType);
		intent.putStringArrayListExtra("screenshot_samples", (ArrayList<String>) screenshot_samples);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail_activity);
		int pos = getIntent().getIntExtra("pos", 0);
		pathType = getIntent().getIntExtra("pathType", url_path);
		title = findViewById(R.id.title);
		content = findViewById(R.id.content);
		save = findViewById(R.id.save_image);


		ArrayList<String> screenshot_samples = getIntent().getStringArrayListExtra("screenshot_samples");

		vp = (ZoomDragImageViewPager) findViewById(R.id.vp);
		vp.setAdapter(new MyPagerAdapter(screenshot_samples));

		vp.setOffscreenPageLimit(5);
		vp.setCurrentItem(pos);
		final int size = screenshot_samples.size();
		//title.setText(pos+1+"/"+size);
		vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {
				title.setText(i+1+"/"+size);
			}

			@Override
			public void onPageSelected(int i) {

			}

			@Override
			public void onPageScrollStateChanged(int i) {

			}
		});


	}



	public class ViewHolder {
		int pos;
		public ZoomDragImageIV content_iv;
		ProgressBar pb;
		public void setPos(int pos) {
			this.pos = pos;
		}

	}

	public void initView(ViewHolder vh, View v) {
		vh.content_iv = (ZoomDragImageIV) v.findViewById(R.id.content_iv);
		vh.content_iv.dragAndZoomTouchListener = new DragAndZoomTouchListener(vh.content_iv);
		vh.pb = (ProgressBar) v.findViewById(R.id.pb);
		v.setTag(vh);
	}


	public class MyPagerAdapter extends PagerAdapter {

		ArrayList<String> screenshot_samples;
		List<View> pagerViews = new ArrayList<>();

		public MyPagerAdapter(ArrayList<String> screenshot_samples) {
			this.screenshot_samples = screenshot_samples;
			for (String pic : screenshot_samples) {
				View v = View.inflate(ImageDetailActivity.this, R.layout.image_detail_lay, null);
				ViewHolder vh = new ViewHolder();
				initView(vh, v);
				pagerViews.add(v);
			}
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(final View arg0, final int position) {
			View v = pagerViews.get(position);
			final ViewHolder vh = (ViewHolder) v.getTag();
			vh.setPos(position);
			setPreviewBitmap(vh,pathType);
			setBitmap(vh,pathType);
			((ViewPager) arg0).addView(v);
			return v;
		}

		private void setBitmap(final ViewHolder vh, final int pathType) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Bitmap tempBitmap = null;
					if (bigBitmapsCache.get(vh.pos) == null) {
						if(pathType == url_path){

						    try {
                                URL url=new URL(screenshot_samples.get(vh.pos));
                                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                                con.setRequestMethod("GET");
                                con.setConnectTimeout(10*1000);
                                InputStream is=con.getInputStream();
                                //把流转换为bitmap
                                tempBitmap= BitmapFactory.decodeStream(is);

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }


						}else if(pathType == local_file_path){
							tempBitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(screenshot_samples.get(vh.pos)));
						}
						final Bitmap bitmap = tempBitmap;
						vh.content_iv.post(new Runnable() {
							@Override
							public void run() {
								if (bitmap == null) {
								} else {
									vh.content_iv.setImageBitmap(bitmap);
									bigBitmapsCache.put(vh.pos, bitmap);
								}
								vh.pb.setVisibility(View.GONE);
							}
						});
					} else {
						vh.content_iv.post(new Runnable() {
							@Override
							public void run() {
								vh.content_iv.setImageBitmap(bigBitmapsCache.get(vh.pos));
								vh.pb.setVisibility(View.GONE);
							}
						});
					}
				}
			}).start();
		}

		private void setPreviewBitmap(ViewHolder vh,int pathType) {
			if(pathType == url_path){
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
				if (bitmap != null) {
					vh.content_iv.setImageBitmap(bitmap);
				}
			}
		}

		@Override
		public int getCount() {
			return screenshot_samples == null ? 0 : screenshot_samples.size();
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(pagerViews.get(arg1));
		}

		public View getItem(int position) {
			return pagerViews.get(position);
		}
	}



    @Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.content_iv:
				finish();
				break;
			case R.id.save_image:

				break;

		}
	}

	@Override
	protected void onDestroy() {
		for (int i : bigBitmapsCache.keySet()) {
			if (bigBitmapsCache.get(i) != null) {
				bigBitmapsCache.get(i).recycle();
			}
		}
		super.onDestroy();
	}



	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()){
			case R.id.content_iv:

				break;

		}
		return false;
	}

}
