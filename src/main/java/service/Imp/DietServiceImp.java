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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    //添加 dietDetail时判断是否有相同食物的，如果有，则覆盖
    public boolean addDietDetail(int diet_id, int food_id, int quantity) {

        List<DietDetail> dietDetails = dietDetailDAO.getByDietId(diet_id);

        for (DietDetail dietDetail : dietDetails){
            //有相同的食物
            if (dietDetail.getFood().getId() == food_id){
                //删除原有的
                dietDetailDAO.delete(diet_id, food_id);
                break;
            }
        }

        return dietDetailDAO.insert(new DietDetail(quantity,new Diet(diet_id),new Food(food_id)));
    }

    @Override
    public boolean removeDietDetail(int diet_id, int food_id) {
        return dietDetailDAO.delete(diet_id, food_id);
    }

    @Override
    public boolean updateDietDetail(int diet_id, int food_id, int quantity) {
        return dietDetailDAO.update(diet_id, food_id, quantity);
    }

    @Override
    public boolean removeDiet(int id) {
        return dietDAO.delete(id);
    }

    @Override
    public List<Diet> getDietByAccToday(int account_id) {
        java.sql.Date date = new java.sql.Date(new Date().getTime());

        List<Diet> diets = dietDAO.getByAccDate(account_id,date);

        for (Diet diet : diets){
            diet.setDetailList(dietDetailDAO.getByDietId(diet.getId()));
        }

        return diets;
    }

    @Override
    public Diet getDietByAccDateGroup(int account_id, int group) {
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        return dietDAO.getByAccGroup(account_id, group,date);
    }

    @Override
    public List<Diet> getDietByAccDate(int account_id, Date date) {
        java.sql.Date date1 = new java.sql.Date(date.getTime());

        return dietDAO.getByAccDate(account_id, date);
    }

    @Override
    public List<Diet> getDietByAccDateString(int account_id, String date) {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            java.sql.Date date2 = new java.sql.Date(date1.getTime());

            List<Diet> diets = dietDAO.getByAccDate(account_id,date2);
            for (Diet diet : diets){
                diet.setDetailList(dietDetailDAO.getByDietId(diet.getId()));
            }
            return diets;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<DietDetail> getDetailsByDiet(int diet_id) {
        return dietDetailDAO.getByDietId(diet_id);
    }


}
