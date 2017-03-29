package com.mktech.viewflipper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by stefan on 2017/3/29.
 */

public class MarqueeTextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {
	private Context		mContext;
	private Timer		mTime;
	private String[]	mResource;
	private int			index;
	private Handler		mHandler	= new Handler() {
										@Override
										public void handleMessage(Message msg) {
											super.handleMessage(msg);
											switch (msg.what) {
												case 1 :
													index = next();
													update();
													break;
											}
										}
									};
	public MarqueeTextSwitchView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	
	public MarqueeTextSwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}
	
	private void init() {
		if (mTime == null) {
			mTime = new Timer();
		}
		this.setFactory(this);
		this.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up));
		this.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_up));
	}
	
	public void setResource(String[] resource) {
		mResource = resource;
	}
	public void setTextStillTime(long time) {
		if (mTime == null) {
			mTime = new Timer();
		} else {
			mTime.scheduleAtFixedRate(new MyTask(), 1, time);// 每3秒更新
		}
	}
	private class MyTask extends TimerTask {
		@Override
		public void run() {
			mHandler.sendEmptyMessage(1);
		}
	}
	
	@Override
	public View makeView() {
		TextView tv = new TextView(mContext);
		tv.setTextSize(20);
		tv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
		return tv;
	}
	private int next() {
		int flag = index + 1;
		if (flag > mResource.length - 1) {
			flag = flag - mResource.length;
		}
		return flag;
	}
	private void update() {
		this.setText(mResource[index]);
	}
}
