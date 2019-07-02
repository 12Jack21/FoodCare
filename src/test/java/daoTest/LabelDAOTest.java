package daoTest;

import baseTest.BaseTest;
import dao.LabelDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import po.Label;
import static org.junit.Assert.*;
import java.util.List;

public class LabelDAOTest extends BaseTest {
    @Autowired
    private LabelDAO labelDAO;

    @Test
    public void getAll(){
        List<Label> labels = labelDAO.getAllLabel();
        assertEquals(1,labels.toArray().length);

    }

    @Test
    public void insert(){
        Label label = new Label();
        label.setName("高糖");
        assertTrue(labelDAO.insert(label));
    }
    @Test
    public void delete(){
        assertTrue(labelDAO.delete(1));
    }

}
