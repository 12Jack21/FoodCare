package service.Imp;

import dao.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import po.Account;
import po.Food;
import service.AccountService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.core.util.Loader.getClassLoader;

@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountDAO accountDAO;

    @Override
    public Account getAccById(int id) {
        return accountDAO.getAccountById(id);
    }

    @Override
    public Account getAccByUser(String user) {
        return accountDAO.getAccountByUser(user);
    }

    @Override
    public boolean canLogin(String user, String password) {
        return accountDAO.getPassword(user).equals(password);
    }

    @Override
    public boolean register(String user, String password) {
        return accountDAO.insertAccount(user, password);
    }

    @Override
    @Transactional
    // 更新头像路径
    public boolean updatePic(int id, String suffix) {

        String picture = "/img_user.action/" + id + "." + suffix;
        return accountDAO.updatePicture(id, picture);
    }

    @Override
    public boolean cancel(int id) {
        return accountDAO.delete(id);
    }

    @Override
    public byte[] getPic(String path) {
        return new byte[0];
    }

    @Override
    public boolean updateInfo(Account account) {
        return accountDAO.updateInfo(account);
    }

    @Override
    public List<Food> recommend(int account_id, int group) {
        return null;
    }

}
