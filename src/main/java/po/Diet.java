package po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Diet implements Serializable {

    private int id;
    //组别（早餐，午餐，晚餐）
    private int group;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Account account;

    private List<DietDetail> detailList;

    public Diet() {
    }

    public Diet(int id) {
        this.id = id;
    }

    public Diet(int group, Date date, Account account) {
        this.group = group;
        this.date = date;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<DietDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DietDetail> detailList) {
        this.detailList = detailList;
    }
}
