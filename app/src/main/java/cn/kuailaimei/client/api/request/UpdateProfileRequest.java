package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class UpdateProfileRequest implements RequestData {

    /**
     * name : 我是好家伙
     */

    private String name;
    private String sex;//0 1 2 性别  0 保密 1男 2女
    private String birthday;
    private String portrait;//用户图像地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
