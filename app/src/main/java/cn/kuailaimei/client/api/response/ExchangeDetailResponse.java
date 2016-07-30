package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.Sku;
import cn.kuailaimei.client.api.entity.Stock;

import java.util.ArrayList;

/**
 * Created by lu on 2016/7/2.
 */
public class ExchangeDetailResponse {
    private GoodsDetail goods;
    private ArrayList<Sku> sku;
    private ArrayList<Stock> stock;

    public GoodsDetail getGoods() {
        return goods;
    }

    public void setGoods(GoodsDetail goods) {
        this.goods = goods;
    }

    public ArrayList<Sku> getSku() {
        return sku;
    }

    public void setSku(ArrayList<Sku> sku) {
        this.sku = sku;
    }

    public ArrayList<Stock> getStock() {
        return stock;
    }

    public void setStock(ArrayList<Stock> stock) {
        this.stock = stock;
    }
}
