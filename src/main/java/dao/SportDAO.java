package dao;

import org.springframework.stereotype.Repository;
import po.Sport;

import java.util.List;

@Repository
public interface SportDAO {

    List<Sport> getAllSports();


}
