package cn.kuailaimei.client.api.entity;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class Pagination {
    /**
     * index : 1
     * pages : 1
     * rows : 1
     * size : 10
     */

    private int index;
    private int pages;
    private int rows;
    private int size;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
