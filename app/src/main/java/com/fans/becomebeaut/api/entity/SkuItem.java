package com.fans.becomebeaut.api.entity;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class SkuItem {
    /**
     * id : 10045
     * items : [{"id":10046,"items":[],"name":"8G","pid":0},{"id":10047,"items":[],"name":"16G","pid":0},{"id":10048,"items":[],"name":"32G","pid":0}]
     * name : 机身内存
     * pid : 0
     */

    private int id;
    private String name;
    private int pid;
    /**
     * id : 10046
     * items : []
     * name : 8G
     * pid : 0
     */


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }



}
