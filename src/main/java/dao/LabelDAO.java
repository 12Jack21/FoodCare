package dao;

import org.springframework.stereotype.Repository;
import po.Label;

import java.util.List;

@Repository
public interface LabelDAO {

    List<Label> getAllLabel(); //所有食物标签

    Boolean insert(Label label);

    Boolean delete(int id);

}
