package com.henghao.parkland.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.henghao.parkland.R;


import net.zombie_sama.imagepainter.PaintableImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ImageSignActivity extends AppCompatActivity {

    @InjectView(R.id.seekBar)
    SeekBar seekBar;
    @InjectView(R.id.piv)
    PaintableImageView piv;

    private File fileSrc;
    private List<Bitmap> bitmapCacheList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_sign);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        fileSrc = (File) intent.getExtras().get("file");
        assert fileSrc != null;
        piv.setImageBitmap(BitmapFactory.decodeFile(fileSrc.getAbsolutePath()));
        piv.setOnDrawDoneListener(new PaintableImageView.OnDrawDoneListener() {
            @Override
            public void onDrawDone(Bitmap bitmap) {
                saveCache(bitmap);
            }
        });
    }

    private void saveCache(Bitmap bitmap) {
        if (bitmapCacheList.size() > 10) {
            bitmapCacheList.remove(10);
            saveCache(bitmap);
        } else {
            bitmapCacheList.add(bitmap);
        }
    }

    @OnClick({R.id.btn_commit, R.id.btn_back, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                File file = toFile(piv.getResult());
                Intent intent = new Intent();
                intent.putExtra("file", file);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_back:
                if (bitmapCacheList.size() >= 2) {
                    bitmapCacheList.remove(0);
                    piv.setImageBitmap(bitmapCacheList.get(0));
                }
                break;
            case R.id.btn_reset:
                piv.reset();
                break;
        }
    }

    private File toFile(Bitmap bitmap) {
        String path = fileSrc.getAbsolutePath().substring(0, fileSrc.getAbsolutePath().lastIndexOf(".")) + "_edit.jpg";
        File file = new File(path);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            Log.e("ImageSignActivity", "bitmap转换失败", e);
        }
        return file;
    }
}
