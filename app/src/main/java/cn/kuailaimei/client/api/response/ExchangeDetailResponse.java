package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.api.entity.StockItem;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class ExchangeDetailResponse {
    private GoodsDetail goods;
    private List<SkuItem> sku;
    private List<StockItem> stock;

    public GoodsDetail getGoods() {
        return goods;
    }

    public void setGoods(GoodsDetail goods) {
        this.goods = goods;
    }

    public List<SkuItem> getSku() {
        return sku;
    }

    public void setSku(List<SkuItem> sku) {
        this.sku = sku;
    }

    public List<StockItem> getStock() {
        return stock;
    }

    public void setStock(List<StockItem> stock) {
        this.stock = stock;
    }
}
