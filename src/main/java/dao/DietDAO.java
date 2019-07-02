package dao;

import org.springframework.stereotype.Repository;
import po.Diet;

import java.util.Date;
import java.util.List;

@Repository
public interface DietDAO {

    List<Diet> getByAccount(int account_id);

    List<Diet> getByAccDate(int account_id, Date date);

    //还根据diet的组别（早餐）
    Diet getByAccGroup(int account_id,int group,Date date);

    Diet getById(int id);

    Boolean insert(Diet diet);

    Boolean delete(int id);

    //Boolean update(Diet);

}
