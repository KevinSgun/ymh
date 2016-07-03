package com.fans.becomebeaut.common.datasource;

/**
 * Created by lu on 2016/4/12.
 */
public class PagedProxy {

    public static final int DEFAULT_PAGE_SIZE = 20;
    private int mDataCount = 0;
    private int mPageCount = 0;
    private boolean mReachEnd = false;
    //
    private int mCurrentPage;
    private int mPageSize = DEFAULT_PAGE_SIZE;
    private boolean mPageCountSet = false;

    public PagedProxy() {

    }

    public PagedProxy(int pageSize) {
//        this.pag
        this.mPageSize = pageSize;
    }


    public int reset() {
        this.setCurrentPage(1);
        this.setPageSize(mPageSize);
        this.mPageCountSet = false;
        this.mReachEnd = false;
        this.mDataCount = 0;
        this.mPageCount = 0;
        return mCurrentPage;
    }

    public final int getDataCount() {
        return this.mDataCount;
    }

    public final void setDataCount(int dataCount) {
        this.mDataCount = dataCount;
        this.mPageCount = (dataCount + mPageSize - 1) / mPageSize;
        this.mPageCountSet = true;
    }

    public int getPageCount() {
        return this.mPageCount;
    }

    public void setPageCount(int pageCount) {
        this.mPageCount = pageCount;
        this.mPageCountSet = true;
    }

    public boolean hasNextPage() {
        boolean hasNext = this.mReachEnd ? false : !this.mPageCountSet || mCurrentPage < this.mPageCount;
        return hasNext;
    }

    public void setReachEnd(boolean reachEnd) {
        this.mReachEnd = reachEnd;
    }

    public void setPageSize(int pageSize) {
        this.mPageSize = pageSize;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage < 0) {
            throw new IllegalArgumentException();
        } else {
            this.mCurrentPage = currentPage;
        }
    }

    public boolean isFirstPage() {
        return mCurrentPage == 1;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public int toNextPage() {
        return ++mCurrentPage;
    }


    public boolean isPageCountSet() {
        return mPageCountSet;
    }
}
