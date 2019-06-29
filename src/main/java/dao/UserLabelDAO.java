package dao;

import org.springframework.stereotype.Repository;
import po.UserLabel;

import java.util.List;

@Repository
public interface UserLabelDAO {

    List<UserLabel> getByAccountId(int account_id);

    Boolean insert(UserLabel userLabel);

    Boolean delete(int id);

    Boolean update(UserLabel userLabel); //只 update 权重weight

}
