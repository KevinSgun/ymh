package cn.kuailaimei.client.api.entity;

/**
 * Created by ymh on 2016/5/30 0030.
 */
public class HomePageBanner {

    /**
     * id : 1
     * img : http://localhost/mall/manage/resources/upload/banner/banner_1.jpg
     * status : 0
     * url : http://www.baidu.com
     */

    private int id;
    private int status;
    private String url;
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
