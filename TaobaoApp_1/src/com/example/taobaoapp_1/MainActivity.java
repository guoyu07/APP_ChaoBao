package com.example.taobaoapp_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/**
	 * 变量定义
	 */
	private ViewPager mViewPaper;
	private List<ImageView> images;
	private List<View> dots;
	private int currentItem;
	private int oldPosition = 0;
	private String[] titles = new String[] { "", "", "", "", "" };
	private TextView title;
	private EditText search;
	private ViewPagerAdapter adapter;
	private ScheduledExecutorService scheduledExecutorService;

	private int[] imageIds = new int[] { R.drawable.lunbo1, R.drawable.lunbo2,
			R.drawable.lunbo3, R.drawable.lunbo4, R.drawable.lunbo5 };

	private int[] pics = new int[] { R.drawable.shop1, R.drawable.shop2,
			R.drawable.shop3, R.drawable.shop4, R.drawable.shop5,
			R.drawable.shop6, R.drawable.shop7, R.drawable.shop8 };

	private int[] clothes = new int[] { R.drawable.cloth1, R.drawable.cloth2,
			R.drawable.cloth3, R.drawable.cloth4, R.drawable.cloth5,
			R.drawable.cloth6, R.drawable.cloth7, R.drawable.cloth8 };

	/***
	 * 
	 * oncreate 函数
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * 搜索框处理事件
		 * 
		 * @param v
		 */
		search = (EditText) findViewById(R.id.search);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://www.taobao.com");
				intent.setData(content_url);
				startActivity(intent);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		/**
		 * 轮转图片
		 * 
		 */

		mViewPaper = (ViewPager) findViewById(R.id.vp);
		images = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent();
					intent.setClass(MainActivity.this,
							MainActivity_Tuiguang.class);
					startActivity(intent);
				}
			});

			images.add(imageView);
		}
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot_0));
		dots.add(findViewById(R.id.dot_1));
		dots.add(findViewById(R.id.dot_2));
		dots.add(findViewById(R.id.dot_3));
		dots.add(findViewById(R.id.dot_4));

		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);

		adapter = new ViewPagerAdapter();
		mViewPaper.setAdapter(adapter);

		mViewPaper
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						title.setText(titles[position]);
						dots.get(position).setBackgroundResource(
								R.drawable.dot_focused);
						dots.get(oldPosition).setBackgroundResource(
								R.drawable.dot_normal);

						oldPosition = position;
						currentItem = position;
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

		/**
		 * gridview 处理
		 * 
		 */
		GridView gridview = (GridView) findViewById(R.id.gridview);
		GridView gridview1 = (GridView) findViewById(R.id.gridview1);
		// 生成动态数组，并且转入数据

		setListViewHeightBasedOnChildren(gridview1);

		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 8; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", pics[i]); // 添加图像资源的ID
			// map.put("ItemText", "Shop" + String.valueOf(i)); // 按序号做ItemText
			map.put("ItemText", "");// 按序号做ItemText
			lstImageItem.add(map);
		}

		ArrayList<HashMap<String, Object>> lstImageItem1 = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 8; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", clothes[i]);// 添加图像资源的ID
			map.put("ItemText", "");// 按序号做ItemText
			lstImageItem1.add(map);
		}

		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
				lstImageItem,// 数据来源
				R.layout.item,// night_item的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// 添加并且显示
		gridview.setAdapter(saImageItems);
		// 添加消息处理
		gridview.setOnItemClickListener(new ItemClickListener());

		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems_1 = new SimpleAdapter(this, // 没什么解释
				lstImageItem1,// 数据来源
				R.layout.item1,// night_item的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage1, R.id.ItemText1 });
		// 添加并且显示
		gridview1.setAdapter(saImageItems_1);
		// 添加消息处理
		gridview1.setOnItemClickListener(new ItemClickListener());

	}

	/***
	 * 扫码处理函数
	 * 
	 * @param v
	 */
	public void saoyisao(View v) {

		Intent intent = new Intent("android.media.action.STILL_IMAGE_CAMERA"); // 调用照相机
		startActivity(intent);
		// 2
		Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null);
		this.sendBroadcast(i);
		// 3
		long dateTaken = System.currentTimeMillis();
		String name = Math.random() + ".jpg";
		String fileName = name;
		ContentValues values = new ContentValues();
		values.put(Images.Media.TITLE, fileName);
		values.put("_data", fileName);
		values.put(Images.Media.PICASA_ID, fileName);
		values.put(Images.Media.DISPLAY_NAME, fileName);
		values.put(Images.Media.DESCRIPTION, fileName);
		values.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, fileName);
		Uri photoUri = getContentResolver().insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

		Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		inttPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		startActivityForResult(inttPhoto, 10);

	}

	/***
	 * 宝贝详情
	 * 
	 * @param v
	 */
	public void baobeiDetail(View v) {

		Intent intent = new Intent();
		intent.setClass(MainActivity.this, MainActivity_Tuiguang.class);
		startActivity(intent);
	}

	/***
	 * 消息查看函数
	 * 
	 * @param v
	 */
	public void xiaoxi(View v) {

		Toast.makeText(getApplicationContext(), "未收到消息", Toast.LENGTH_LONG)
				.show();
	}

	/***
	 * 店铺详情
	 * 
	 * @param v
	 */

	/***
	 * gridview自适应
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(GridView listView) {
		// 获取listview的adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		// 固定列宽，有多少列
		int col = 2;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 设置高度
		params.height = totalHeight;
		// 设置margin
		// ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// 设置参数
		listView.setLayoutParams(params);
	}

	/***
	 * 商店详情
	 * 
	 * @param v
	 */
	public void shopDetail(View v) {

		Intent intent = new Intent();
		intent.setClass(MainActivity.this, MainActivity_Tuiguang.class);
		startActivity(intent);

	}

	/**
	 * 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	 * 
	 * @author hechao
	 * 
	 */
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// 在本例中arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			// 显示所选Item的ItemText

			// ImageView iv = (ImageView) findViewById(R.id.ItemImage);
			// iv.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this,
			// MainActivity_Detail.class);
			// startActivity(intent);
			// }
			// });
			//
			// Toast.makeText(getApplicationContext(),
			// (String) item.get("ItemText"), Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * gridview 适配器
	 * 
	 * @author hechao
	 * 
	 */
	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			view.removeView(images.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			view.addView(images.get(position));
			return images.get(position);
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 5,
				5, TimeUnit.SECONDS);
	}

	private class ViewPageTask implements Runnable {

		@Override
		public void run() {
			currentItem = (currentItem + 1) % imageIds.length;
			mHandler.sendEmptyMessage(0);
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mViewPaper.setCurrentItem(currentItem);
		};
	};

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
