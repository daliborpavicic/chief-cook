package rs.ac.uns.ftn.chiefcook.model;

import java.util.ArrayList;
import java.util.List;


public class Step {

    private Long number;
    private String step;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Equipment> equipment = new ArrayList<>();

    /**
     * @return The number
     */
    public Long getNumber() {
        return number;
    }

    /**
     * @param number The number
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * @return The step
     */
    public String getStep() {
        return step;
    }

    /**
     * @param step The step
     */
    public void setStep(String step) {
        this.step = step;
    }

    /**
     * @return The ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * @param ingredients The ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * @return The equipment
     */
    public List<Equipment> getEquipment() {
        return equipment;
    }

    /**
     * @param equipment The equipment
     */
    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

}