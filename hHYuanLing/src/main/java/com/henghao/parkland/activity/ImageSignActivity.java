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
import com.henghao.parkland.utils.FileUtils;


import net.zombie_sama.imagepainter.PaintableImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    private List<Bitmap> bitmapCacheList = new ArrayList<>();
    private int cacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_sign);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        fileSrc = (File) intent.getExtras().get("file");
        cacheSize = intent.getIntExtra("cacheSize", 10);
        assert fileSrc != null;
        Bitmap bitmap = BitmapFactory.decodeFile(fileSrc.getAbsolutePath());
        bitmapCacheList.add(bitmap.copy(bitmap.getConfig(), false));
        piv.setImageBitmap(bitmap);
        piv.setOnDrawDoneListener(new PaintableImageView.OnDrawDoneListener() {
            @Override
            public void onDrawDone(Bitmap bitmap) {
                saveCache(bitmap);
            }
        });
        seekBar.setProgress((int) (piv.getPaintStrokeWidth() - 1));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                piv.setPaintStrokeWidth(seekBar.getProgress() + 1);
            }
        });
    }

    private void saveCache(Bitmap bitmap) {
        if (bitmapCacheList.size() > 11) {
            bitmapCacheList.remove(11);
            saveCache(bitmap);
        } else {
            bitmapCacheList.add(0, bitmap.copy(bitmap.getConfig(), false));
        }
    }

    @OnClick({R.id.btn_commit, R.id.btn_back, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                File file = toFile(piv.getResult());
                if (file != null) {
                    Intent intent = new Intent();
                    intent.putExtra("file", file);
                    intent.putExtra("src", fileSrc);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(-2);
                }
                finish();
                break;
            case R.id.btn_back:
                if (bitmapCacheList.size() >= 2) {
                    bitmapCacheList.remove(0);
                    piv.setImageBitmap(bitmapCacheList.get(0),false);
                }
                break;
            case R.id.btn_reset:
                piv.reset();
                break;
        }
    }

    private File toFile(Bitmap bitmap) {
        String filesName = fileSrc.toString().substring(0, fileSrc.getAbsolutePath().lastIndexOf("."));
        String suffix = fileSrc.toString().substring(fileSrc.getAbsolutePath().lastIndexOf("."));
        String hashName = "";
        for (char c : filesName.toCharArray()) {
            hashName += Integer.toHexString(c);
        }
        File file = new File(FileUtils.getSDPath() + "/ParKLand/cache/SignedImage/" + hashName + suffix);
        Log.d("ImageSignActivity", file.getAbsolutePath());
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                File tmp = new File(file.getParent() + ".nomedia/");
                if (!tmp.exists()) tmp.mkdirs();
            }
            file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            Log.e("ImageSignActivity", "bitmap转换失败", e);
            return null;
        }
        return file;
    }
}
