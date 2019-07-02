package service.Imp;

import dao.DietDAO;
import dao.DietDetailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.Account;
import po.Diet;
import po.DietDetail;
import po.Food;
import service.DietService;

import java.util.Date;
import java.util.List;

@Service
public class DietServiceImp implements DietService {
    @Autowired
    private DietDAO dietDAO;
    @Autowired
    private DietDetailDAO dietDetailDAO;

    @Override
    public boolean addDiet(int group, int account_id) {
        return dietDAO.insert(new Diet(group,new Date(),new Account(account_id)));
    }

    @Override
    public boolean addDietDetail(int diet_id, int food_id, int quantity) {
        return dietDetailDAO.insert(new DietDetail(quantity,new Diet(diet_id),new Food(food_id)));
    }

    @Override
    public boolean removeDietDetail(int diet_id, int food_id) {
        return dietDetailDAO.delete(diet_id, food_id);
    }

    @Override
    public boolean removeDiet(int id) {
        return dietDAO.delete(id);
    }

    @Override
    public List<Diet> getDietByAccDate(int account_id) {
        return dietDAO.getByAccDate(account_id,new Date());
    }

    @Override
    public Diet getDietByAccDateGroup(int account_id, int group) {
        return dietDAO.getByAccGroup(account_id, group,new Date());
    }

    @Override
    public List<DietDetail> getDetailsByDiet(int diet_id) {
        return dietDetailDAO.getByDietId(diet_id);
    }


}
