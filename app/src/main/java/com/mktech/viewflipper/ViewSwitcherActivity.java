package com.mktech.viewflipper;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 2017/3/28.
 */

public class ViewSwitcherActivity extends Activity {
	private ViewSwitcher	mViewSwitcher;
	private final int		MAX_APP_COUNT	= 20;
	
	public static class AppInfo {
		public String	name;
		public Drawable	icon;
		
	}
	
	private List<AppInfo>		mAppInfos		= new ArrayList<>();
	
	private int					mScreenNo		= -1;				// 当前显示屏幕
	private int					mScreenCount;						// 保存总屏幕数量
	
	private LayoutInflater		mLayoutInflater;
	private PackageManager		mPackageManager;
	
	private GestureDetector		mGestureDetector;
	private MyGestureListener	mMyGestureListener;
	private final int			MIN_DISTANCE	= 200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_viewswitcher);
		initView();
		initData();
	}
	
	private void initData() {
		mPackageManager = getPackageManager();
		List<ApplicationInfo> applicationInfos = mPackageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (ApplicationInfo app : applicationInfos) {
			AppInfo appinfo = new AppInfo();
			appinfo.name = app.loadLabel(mPackageManager).toString();
			appinfo.icon = app.loadIcon(mPackageManager);
			mAppInfos.add(appinfo);
		}
		Toast.makeText(this, "" + mAppInfos.size(), Toast.LENGTH_SHORT).show();
		mScreenCount = mAppInfos.size() % MAX_APP_COUNT == 0 ? mAppInfos.size() / MAX_APP_COUNT : mAppInfos.size() / MAX_APP_COUNT + 1;
		mViewSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
			@Override
			public View makeView() {
				return mLayoutInflater.inflate(R.layout.slide_viewswitcher, null);
			}
		});
		next();
		
	}
	
	private void initView() {
		mViewSwitcher = (ViewSwitcher) findViewById(R.id.viewswitch);
		mLayoutInflater = LayoutInflater.from(this);
		mMyGestureListener = new MyGestureListener();
		mGestureDetector = new GestureDetector(this, mMyGestureListener);
	}
	
	private void next() {
		if (mScreenNo < mScreenCount - 1) {
			mScreenNo++;
			mViewSwitcher.setInAnimation(this, R.anim.slide_in);
			mViewSwitcher.setOutAnimation(this, R.anim.slide_out);
			((GridView) mViewSwitcher.getNextView()).setAdapter(mBaseAdapter);
			mViewSwitcher.showNext();
		}
	}
	private void pre() {
		if (mScreenNo > 0) {
			mScreenNo--;
			mViewSwitcher.setInAnimation(this, R.anim.slide_in_left);
			mViewSwitcher.setOutAnimation(this, R.anim.slide_out_right);
			((GridView) mViewSwitcher.getNextView()).setAdapter(mBaseAdapter);
			mViewSwitcher.showPrevious();
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}
	
	private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e1.getX() - e2.getX() > MIN_DISTANCE) {
				next();
			} else if (e2.getX() - e1.getX() > MIN_DISTANCE) {
				pre();
			}
			
			return true;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}
	}
	
	private BaseAdapter mBaseAdapter = new BaseAdapter() {
		@Override
		public int getCount() {
			if (mScreenNo == mScreenCount - 1 && mAppInfos.size() % MAX_APP_COUNT != 0) {
				return mAppInfos.size() % MAX_APP_COUNT;
			}
			return MAX_APP_COUNT;
		}
		
		@Override
		public AppInfo getItem(int position) {
			return mAppInfos.get(mScreenNo * MAX_APP_COUNT + position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (convertView == null) {
				view = mLayoutInflater.inflate(R.layout.slide_switch_content, null);
			}
			ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
			imageView.setImageDrawable(getItem(position).icon);
			TextView textView = (TextView) view.findViewById(R.id.textview);
			textView.setText(getItem(position).name);
			return view;
		}
	};
}
