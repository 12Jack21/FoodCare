package service;

import po.Menu;
import po.MenuItem;

import java.util.List;

public interface MenuService {

    List<Menu> getMenuByAccId(int account_id);

    List<MenuItem> getItemById(int menu_id);

    boolean addMenu(Menu menu);

    boolean deleteMenu(int menu_id);

    boolean addItem(MenuItem menuItem);

    boolean deleteItem(int menuItem_id);


}
