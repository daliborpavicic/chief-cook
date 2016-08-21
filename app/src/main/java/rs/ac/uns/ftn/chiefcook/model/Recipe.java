package rs.ac.uns.ftn.chiefcook.model;

/**
 * Created by daliborp on 20.8.16..
 */
public class Recipe {

    Integer id;
    String title;
    Integer readyInMinutes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    @Override
    public String toString() {
        return title;
    }
}
