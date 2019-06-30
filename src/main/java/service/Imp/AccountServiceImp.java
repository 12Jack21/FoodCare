package service.Imp;

import dao.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.Account;
import service.AccountService;

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
    public boolean cancel(int id) {
        return accountDAO.delete(id);
    }

    @Override
    public boolean updatePic(int id, Byte[] picture) {
        return accountDAO.updatePicture(id, picture);
    }

    @Override
    public boolean updateInfo(Account account) {
        return accountDAO.updateInfo(account);
    }
}
