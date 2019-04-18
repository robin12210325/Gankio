package com.gankio.gankio.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.gankio.R;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

public class PicView extends Activity {
    com.github.chrisbanes.photoview.PhotoView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pic_view);
        view = (com.github.chrisbanes.photoview.PhotoView) findViewById(R.id.imgPhoto);
        String url = getIntent().getExtras().getString("imgUrl","");
        Glide.with(PicView.this).load(url).into(view);
        PhotoViewAttacher attacher = new PhotoViewAttacher(view);
        attacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
