package po;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Diet {

    private int id;
    //组别（早餐，午餐，晚餐）
    private int group;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Account account;

    public Diet() {
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
}
