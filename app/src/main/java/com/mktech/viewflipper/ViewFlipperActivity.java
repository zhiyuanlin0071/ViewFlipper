package com.mktech.viewflipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class ViewFlipperActivity extends AppCompatActivity {
	private ViewFlipper			mViewFlipper;
	private int[]				image			= new int[]{R.drawable.board_browser, R.drawable.board_filebrowser, R.drawable.board_gallery,
			R.drawable.board_youtube};
	private final int			MIN_DISTANCE	= 200;
	private GestureDetector		mGestureDetector;
	private MyGestureListener	mMyGestureListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mViewFlipper = (ViewFlipper) findViewById(R.id.viewFilpper);
		mMyGestureListener = new MyGestureListener();
		mGestureDetector = new GestureDetector(this, mMyGestureListener);
		for (int i = 0; i < image.length; i++) {
			mViewFlipper.addView(getImageView(image[i]));
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}
	
	private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e1.getX()-e2.getX()>MIN_DISTANCE)
            {
				mViewFlipper.setInAnimation(ViewFlipperActivity.this, R.anim.slide_in);
				mViewFlipper.setOutAnimation(ViewFlipperActivity.this, R.anim.slide_out);
                mViewFlipper.showNext();
            }else if (e2.getX()-e1.getX()>MIN_DISTANCE){
				mViewFlipper.setInAnimation(ViewFlipperActivity.this, R.anim.slide_in);
				mViewFlipper.setOutAnimation(ViewFlipperActivity.this, R.anim.slide_out);
                mViewFlipper.showPrevious();
            }

            return true;
		}
	}
	
	public ImageView getImageView(int image) {
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(image);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		return imageView;
	}
}
