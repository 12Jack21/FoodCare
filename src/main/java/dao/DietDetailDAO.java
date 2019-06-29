package dao;

import org.springframework.stereotype.Repository;
import po.DietDetail;

import java.util.List;

@Repository
public interface DietDetailDAO {

    List<DietDetail> getByDietId(int diet_id);


    Boolean insert(DietDetail dietDetail);

    Boolean delete(int id);

    Boolean update(int id,int quantity);

}
