package dao;

import org.springframework.stereotype.Repository;
import po.Diet;

import java.util.List;

@Repository
public interface DietDAO {

    List<Diet> getByAccount(int user_id);

    //还根据diet的组别（早餐）
    List<Diet> getByAccGroup(int user_id,int group);

    Diet getById(int id);

    Boolean insert(Diet diet);

    Boolean delete(int id);

    //Boolean update(Diet);

}
