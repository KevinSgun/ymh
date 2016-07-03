package com.fans.becomebeaut.api.request;

/**
 * Created by ymh on 2016/7/3 0003.
 * 需要分页加载的请求
 */
public class PageRequest extends Request{

    private PageRequestData data;

    public PageRequest(PageRequestData data) {
        super(data);
        this.data = data;
    }

    public void setCurrentPage(int page){
        data.setIndex(page);
    }
}
