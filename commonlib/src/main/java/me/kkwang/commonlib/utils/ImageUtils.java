package me.kkwang.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kw on 2015/11/16.
 */
public class ImageUtils {

    public static String saveBitmap(Bitmap bm, String path, String filename) {

        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        path = path + "/" + filename;
        File f2 = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(f2);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return path;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static void loadRoundImage(final Context context, int placeholderDrawable, int errorDrawable, String url, final ImageView iv, final int radius) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(radius);
                        iv.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void loadImageUrl(final Context context, int drawable, String url, final ImageView iv) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

    }

    //用Glide下载图片，改自Meizhi,实现认为有问题，何须二进制流解析成Bitmap再变成文件
    //还没改 需要时再改 2016/03/13
    public static Observable<Uri> saveImageAndGetPathObservable(final Context context, final String url, final String title) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {

//                    File bitmap2 = Glide.with(context)
//                            //.using(new NetworkDisablingLoader<DownloadModel>()) // optional depending on if you want network or not
//                            .load(url)
//                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

                    bitmap = Glide.with(context).load(url).asBitmap().
                            into(100, 100). // Width and height
                            get();
                    ;
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                if (bitmap == null) {
                    subscriber.onError(new Exception("无法下载到图片"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<Bitmap, Observable<Uri>>() {
            @Override
            public Observable<Uri> call(Bitmap bitmap) {
                File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = title.replace('/', '-') + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri uri = Uri.fromFile(file);
                // 通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                context.sendBroadcast(scannerIntent);
                return Observable.just(uri);
            }
        }).subscribeOn(Schedulers.io());

    }
}
