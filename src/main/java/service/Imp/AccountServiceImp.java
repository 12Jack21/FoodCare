package service.Imp;

import dao.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import po.Account;
import service.AccountService;

import java.io.File;
import java.io.FileNotFoundException;

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
    public boolean cancel(int id) {
        return accountDAO.delete(id);
    }

    @Override
    public byte[] getPic(String path) {
        return new byte[0];
    }

    @Override
    //TODO 更新头像
    public boolean updatePic(int id, String picture) {


        String classpath = AccountServiceImp.class.getResource("/").getPath();

        try {
            //得到 Resources 的目录下的文件，不能得到目录
            File file = ResourceUtils.getFile("classpath:image_user" + "/jdbc.properties");

            String c = ResourceUtils.getFile("classpath:image_user").getPath();
            System.out.println(c);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String img_path = AccountServiceImp.class.getResource("/").getPath().replace("classes", "img");
//        System.out.println("Img_path: " + img_path);


        return accountDAO.updatePicture(id, null);
    }

    @Override
    public boolean updateInfo(Account account) {
        return accountDAO.updateInfo(account);
    }

}
