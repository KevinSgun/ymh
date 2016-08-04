package cn.kuailaimei.client.api.entity;

/**
 * Created by ludaiqian on 16/8/2.
 */
public class Evaluation {
    /**
     * content : 22222
     * date : 2016-06-19 17:53:28
     * id : 2
     * uid : 11903
     * uname : 大大
     * portrait : http://e.hiphotos.baidu.com/image/pic/item/14ce36d3d539b600be63e95eed50352ac75cb7ae.jpg
     */

    private String content;
    private String date;
    private int id;
    private int uid;
    private String uname;
    private String portrait;
    private int commentType;// 评论类型 评论类型 1非常满意 2满意 3不满意

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getSatisfaction() {
        switch (commentType) {
            case 1:
                return "非常满意";
            case 2:
                return "满意";
            case 3:
                return "不满意";

        }
        return "满意";
    }
}