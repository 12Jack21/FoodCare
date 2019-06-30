package dao;

import org.springframework.stereotype.Repository;
import po.UserLabel;

import java.util.List;

@Repository
public interface UserLabelDAO {

    List<UserLabel> getLabelByAccountId(int account_id);

    Boolean insert(int label_id,int account_id,Double weight);

    Boolean delete(int label_id,int account_id);

    Boolean update(UserLabel userLabel); //只 update 权重weight

}
