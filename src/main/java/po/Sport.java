package po;

import java.io.Serializable;

public class Sport implements Serializable {

    private int id;
    private String name;
    //每小时运动消耗的热量
    private int consume;

    //图片地址 URL
    private String url;

    public Sport() {
    }

    public Sport(int id) {
        this.id = id;
    }

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

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
