package po;

import java.io.Serializable;

public class DietDetail implements Serializable {

    private int quantity;

    private Diet diet;
    private Food food;

    public DietDetail() {
    }

    public DietDetail(int quantity, Diet diet, Food food) {
        this.quantity = quantity;
        this.diet = diet;
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Diet getDiet() {
        return diet;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
