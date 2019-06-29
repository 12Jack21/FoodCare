package dao;

import org.springframework.stereotype.Repository;
import po.Account;

@Repository
public interface AccountDAO {

    Account getAccountByUser(String user);

    Account getAccountById(int id);

    String getPassword(String user);

    Integer getId(String user);

    Boolean insertAccount(String user,String password);

    Boolean delete(int id);

    Boolean updatePassword(int id,String password);

    Boolean updatePicture(int id,Byte[] picture);
    Boolean updateInfo(Account account);

}
