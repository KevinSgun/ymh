package cn.kuailaimei.client.common.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.utils.ToastMaster;
import cn.kuailaimei.client.common.widget.HackyViewPager;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoPagerActivity extends BaseActivity {

    private HackyViewPager mViewPager;

    private static final String PHOTO_URLS = "photoUrls";
    private static final String THUMBNAIL_URLS = "thumbnailUrls";
    private static final String POSIOTION = "position";

    private ArrayList<String> thumbnailUrls;
    protected int position;

    private SamplePagerAdapter adapter;

    private ArrayList<String> photoUrls;

    private TextView photoCountTv;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_photos_pager);
    }


    @Override
    protected void initView() {
        mViewPager = (HackyViewPager) findViewById(R.id.hackpager);
        photoCountTv = (TextView) findViewById(R.id.photo_counts);
        handleIntent(getIntent());
    }

    @Override
    protected void initData() {

    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @SuppressWarnings("deprecation")
    private void handleIntent(Intent intent) {
        photoUrls = intent.getStringArrayListExtra(PHOTO_URLS);
        thumbnailUrls = intent.getStringArrayListExtra(THUMBNAIL_URLS);
        int postion = intent.getIntExtra(POSIOTION, 0);
        adapter = new SamplePagerAdapter(thumbnailUrls, photoUrls);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(postion);

        final int allCouts = photoUrls.size();
        photoCountTv.setText(postion + 1 + "/" + allCouts);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                PhotoPagerActivity.this.position = position;
                photoCountTv.setText(position + 1 + "/" + allCouts);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    class SamplePagerAdapter extends PagerAdapter {
        ArrayList<String> photoUrls;
        ArrayList<String> thumbnailUrls;
        private PhotoView photoView;

        public SamplePagerAdapter(ArrayList<String> smallPath, ArrayList<String> photosPath) {
            super();
            this.photoUrls = photosPath;
            this.thumbnailUrls = smallPath;
        }

        @Override
        public int getCount() {
            return photoUrls.size();
        }

        public View getCurrentView() {
            return photoView;
        }

        @SuppressLint("NewApi")
        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());

            if (thumbnailUrls != null && thumbnailUrls.size() == photoUrls.size()) {
                photoView.setDefaultImageResource(R.mipmap.ic_shop_default);
                Glide.with(PhotoPagerActivity.this).load(thumbnailUrls.get(position)).asBitmap().listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        photoView.setDefaultImageBitmap(resource);
                        return true;
                    }
                });
            } else {
                photoView.setDefaultImageResource(R.mipmap.ic_shop_default);
            }
//			Glide.
//			photoView.setAnimationEnable(false);
            photoView.setImageUri(photoUrls.get(position));
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                }
            });

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            this.photoView = (PhotoView) object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    /**
     * @param context
     * @param photoUrls
     * @param thumbnailUrls
     */

    public static void launch(Context context, ArrayList<String> photoUrls, ArrayList<String> thumbnailUrls, int position) {
        try {
            if (photoUrls == null || photoUrls.size() <= 0) {
                ToastMaster.popToast(context, context.getResources().getString(R.string.image_noloading));
            } else {
                Intent intent = new Intent(context, PhotoPagerActivity.class);
                intent.putStringArrayListExtra(PHOTO_URLS, photoUrls);
                intent.putStringArrayListExtra(THUMBNAIL_URLS, thumbnailUrls);
                intent.putExtra(POSIOTION, position);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            //Logger.e("eeeee" + e.getMessage());
        }

    }

    public static void launch(Context mContext, String url, String thumb) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url);
        ArrayList<String> thumbs;
        if (!url.equals(thumb)) {
            thumbs = new ArrayList<>();
            urls.add(thumb);
        } else {
            thumbs = urls;
        }
        launch(mContext, urls, thumbs, 0);
    }

    @Override
    protected void onDestroy() {
        if (mViewPager != null) {
            mViewPager.removeAllViews();
            mViewPager.setAdapter(null);
        }
        if (photoUrls != null)
            photoUrls.clear();
        if (thumbnailUrls != null)
            thumbnailUrls.clear();
        super.onDestroy();
    }

}
