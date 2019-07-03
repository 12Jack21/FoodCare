package service.Imp;

import dao.MenuDAO;
import dao.MenuItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.Menu;
import po.MenuItem;
import service.MenuService;

import java.util.List;

@Service
public class MenuServiceImp implements MenuService {
    @Autowired
    private MenuDAO menuDAO;
    @Autowired
    private MenuItemDAO menuItemDAO;

    @Override
    public List<Menu> getMenuByAccId(int account_id) {
        return menuDAO.getByAcc(account_id);
    }

    @Override
    public List<MenuItem> getItemById(int menu_id) {
        return menuItemDAO.getByMenuId(menu_id);
    }

    @Override
    public boolean addMenu(Menu menu) {
        return menuDAO.insert(menu);
    }

    @Override
    public boolean deleteMenu(int menu_id) {
        return menuDAO.delete(menu_id);
    }

    @Override
    public boolean addItem(MenuItem menuItem) {
        return menuItemDAO.insert(menuItem);
    }

    @Override
    public boolean deleteItem(int menuItem_id) {
        return menuItemDAO.delete(menuItem_id);
    }
}
