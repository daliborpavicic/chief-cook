package rs.ac.uns.ftn.chiefcook.model;

import java.util.ArrayList;
import java.util.List;

public class RecipeInstructionsItem {

    private String name;
    private List<Step> steps = new ArrayList<>();

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The steps
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * @param steps The steps
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}

