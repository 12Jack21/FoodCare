package dao;

import org.springframework.stereotype.Repository;
import po.Menu;

import java.util.List;

@Repository
public interface MenuDAO {

    List<Menu> getByAcc(int account_id);

    boolean insert(Menu menu);

    boolean delete(int id);

}
