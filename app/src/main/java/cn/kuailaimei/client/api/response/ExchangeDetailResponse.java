package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.api.entity.StockItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class ExchangeDetailResponse {
    private GoodsDetail goods;
    private ArrayList<SkuItem> sku;
    private ArrayList<StockItem> stock;

    public GoodsDetail getGoods() {
        return goods;
    }

    public void setGoods(GoodsDetail goods) {
        this.goods = goods;
    }

    public ArrayList<SkuItem> getSku() {
        return sku;
    }

    public void setSku(ArrayList<SkuItem> sku) {
        this.sku = sku;
    }

    public ArrayList<StockItem> getStock() {
        return stock;
    }

    public void setStock(ArrayList<StockItem> stock) {
        this.stock = stock;
    }
}
