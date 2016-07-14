package cn.kuailaimei.client.mall.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.api.entity.StockItem;
import cn.kuailaimei.client.common.utils.ToastMaster;
import cn.kuailaimei.client.common.utils.Utils;
import cn.kuailaimei.client.common.utils.ViewUtils;
import cn.kuailaimei.client.common.widget.ChooseSpecLayout;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleButton;


public class ChooseSpecActivity extends Activity {

    private ImageView close;
    private TextView price;
    private TextView avaliableExchange;
    private ChooseSpecLayout choseSpecLayout;
    private RippleButton confirm;
    private LinearLayout rootLayout;
    private RemoteImageView icon;
    private SkuItem choosedSku;
    private StockItem stockItem;
    private TextView name;
    private TextView stock;
    private StockItem choosedStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ActiviyDialogStyleBottom);
        setContentView(R.layout.activity_choose_spec);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.name = (TextView) findViewById(R.id.name);
        this.confirm = (RippleButton) findViewById(R.id.confirm);
        this.choseSpecLayout = (ChooseSpecLayout) findViewById(R.id.choseSpecLayout);
        this.avaliableExchange = (TextView) findViewById(R.id.avaliableExchange);
        this.price = (TextView) findViewById(R.id.price);
        this.close = (ImageView) findViewById(R.id.close);
        this.rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        this.icon = (RemoteImageView) findViewById(R.id.icon);
        this.stock = (TextView) findViewById(R.id.stock);
        confirm.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                if (choosedSku != null) {
                    Intent data = new Intent();
                    data.putExtra(Constants.ActivityExtra.CHOOSE_SKU, choosedSku);
                    data.putExtra(Constants.ActivityExtra.CHOOSE_STOCK, choosedStock);
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    ToastMaster.popToast(ChooseSpecActivity.this, "请选择商品规格");
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        GoodsDetail detail = getIntent().getParcelableExtra(Constants.ActivityExtra.GOODS_DETAIL);
        final ArrayList<SkuItem> skus = getIntent().getParcelableArrayListExtra(Constants.ActivityExtra.SKU_LIST);
        final ArrayList<StockItem> stockItems = getIntent().getParcelableArrayListExtra(Constants.ActivityExtra.STOCK_LIST);
        name.setText(detail.getName());
        price.setText(Utils.formartPrice( detail.getPrice()) + "+" + detail.getScore() + "美券");
        avaliableExchange.setText("可兑换" + detail.getInventory() + "件");
//        GoodsItem
        icon.setImageUri(R.mipmap.ic_shop_default, detail.getPhotos().size() > 0 ? detail.getPhotos().get(0) : "");

        choseSpecLayout.setSkuItems(skus);
        choseSpecLayout.setOnSkuCheckChangedListener(new ChooseSpecLayout.OnSkuCheckChangedListener() {
            @Override
            public void onSkuChanged(SkuItem item) {
                try {
                    choosedSku = item;
                    int index = skus.indexOf(item);
                    choosedStock = stockItems.get(index);
                    stock.setText("库存："+choosedStock.getInventory());
                    if(choosedStock.getInventory()>0){
                        confirm.setEnabled(true);
                    }else{
                        confirm.setEnabled(false);
                    }
                } catch (Exception e) {
                    ToastMaster.popToast(ChooseSpecActivity.this, "数据错误");
                }
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!ViewUtils.isTouchedView(rootLayout, event)) {
                finish();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public static void launchForResult(Activity context, GoodsDetail goodsDetail, ArrayList<SkuItem> skuItems, ArrayList<StockItem> stockItems, int requestCode) {
        Intent intent = new Intent(context, ChooseSpecActivity.class);
        intent.putExtra(Constants.ActivityExtra.GOODS_DETAIL, goodsDetail);
        intent.putParcelableArrayListExtra(Constants.ActivityExtra.SKU_LIST, skuItems);
        intent.putParcelableArrayListExtra(Constants.ActivityExtra.STOCK_LIST, stockItems);
        context.startActivityForResult(intent, requestCode);
    }
}
