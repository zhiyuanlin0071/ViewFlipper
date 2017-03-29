package com.mktech.viewflipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by stefan on 2017/3/29.
 */

public class HorizontalTextViewActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.horizontal_textswitch);
		
	}
}
