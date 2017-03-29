package com.mktech.viewflipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by stefan on 2017/3/29.
 */

public class HorizontalTextViewActivity extends Activity {
	private MarqueeTextSwitchView	mMarqueeTextSwitchView;
	private String[]				mResource	= new String[]{"你是大路口见多了按时", "看哈收到了看看啦老大", "啥大家奥路径大", "阿萨德卡洛斯看"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.horizontal_textswitch);
		mMarqueeTextSwitchView = (MarqueeTextSwitchView) findViewById(R.id.marquee);
		mMarqueeTextSwitchView.setResource(mResource);
		mMarqueeTextSwitchView.setTextStillTime(2000);
		
	}
}
