package dao;

import org.springframework.stereotype.Repository;
import po.MenuItem;

import java.util.List;

@Repository
public interface MenuItemDAO {

    List<MenuItem> getByMenuId(int menu_id);

    boolean insert(MenuItem menuItem);

    boolean delete(int id);

}
