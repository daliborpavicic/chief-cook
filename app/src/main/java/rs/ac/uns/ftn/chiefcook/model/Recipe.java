package rs.ac.uns.ftn.chiefcook.model;

import java.util.ArrayList;
import java.util.List;


public class Recipe {

    private Integer id;
    private String title;
    private Integer readyInMinutes;
    private String image;
    private Boolean vegetarian;
    private Boolean vegan;
    private Boolean glutenFree;
    private Boolean dairyFree;
    private Boolean veryHealthy;
    private Boolean cheap;
    private Boolean veryPopular;
    private Boolean sustainable;
    private Integer weightWatcherSmartPoints;
    private String gaps;
    private Boolean lowFodmap;
    private Boolean ketogenic;
    private Boolean whole30;
    private Integer servings;
    private String sourceUrl;
    private String spoonacularSourceUrl;
    private Integer aggregateLikes;
    private String creditText;
    private String sourceName;
    private List<ExtendedIngredient> extendedIngredients = new ArrayList<ExtendedIngredient>();

    /**
     * @return The vegetarian
     */
    public Boolean getVegetarian() {
        return vegetarian;
    }

    /**
     * @param vegetarian The vegetarian
     */
    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    /**
     * @return The vegan
     */
    public Boolean getVegan() {
        return vegan;
    }

    /**
     * @param vegan The vegan
     */
    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    /**
     * @return The glutenFree
     */
    public Boolean getGlutenFree() {
        return glutenFree;
    }

    /**
     * @param glutenFree The glutenFree
     */
    public void setGlutenFree(Boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    /**
     * @return The dairyFree
     */
    public Boolean getDairyFree() {
        return dairyFree;
    }

    /**
     * @param dairyFree The dairyFree
     */
    public void setDairyFree(Boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    /**
     * @return The veryHealthy
     */
    public Boolean getVeryHealthy() {
        return veryHealthy;
    }

    /**
     * @param veryHealthy The veryHealthy
     */
    public void setVeryHealthy(Boolean veryHealthy) {
        this.veryHealthy = veryHealthy;
    }

    /**
     * @return The cheap
     */
    public Boolean getCheap() {
        return cheap;
    }

    /**
     * @param cheap The cheap
     */
    public void setCheap(Boolean cheap) {
        this.cheap = cheap;
    }

    /**
     * @return The veryPopular
     */
    public Boolean getVeryPopular() {
        return veryPopular;
    }

    /**
     * @param veryPopular The veryPopular
     */
    public void setVeryPopular(Boolean veryPopular) {
        this.veryPopular = veryPopular;
    }

    /**
     * @return The sustainable
     */
    public Boolean getSustainable() {
        return sustainable;
    }

    /**
     * @param sustainable The sustainable
     */
    public void setSustainable(Boolean sustainable) {
        this.sustainable = sustainable;
    }

    /**
     * @return The weightWatcherSmartPoints
     */
    public Integer getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    /**
     * @param weightWatcherSmartPoints The weightWatcherSmartPoints
     */
    public void setWeightWatcherSmartPoints(Integer weightWatcherSmartPoints) {
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
    }

    /**
     * @return The gaps
     */
    public String getGaps() {
        return gaps;
    }

    /**
     * @param gaps The gaps
     */
    public void setGaps(String gaps) {
        this.gaps = gaps;
    }

    /**
     * @return The lowFodmap
     */
    public Boolean getLowFodmap() {
        return lowFodmap;
    }

    /**
     * @param lowFodmap The lowFodmap
     */
    public void setLowFodmap(Boolean lowFodmap) {
        this.lowFodmap = lowFodmap;
    }

    /**
     * @return The ketogenic
     */
    public Boolean getKetogenic() {
        return ketogenic;
    }

    /**
     * @param ketogenic The ketogenic
     */
    public void setKetogenic(Boolean ketogenic) {
        this.ketogenic = ketogenic;
    }

    /**
     * @return The whole30
     */
    public Boolean getWhole30() {
        return whole30;
    }

    /**
     * @param whole30 The whole30
     */
    public void setWhole30(Boolean whole30) {
        this.whole30 = whole30;
    }

    /**
     * @return The servings
     */
    public Integer getServings() {
        return servings;
    }

    /**
     * @param servings The servings
     */
    public void setServings(Integer servings) {
        this.servings = servings;
    }

    /**
     * @return The sourceUrl
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * @param sourceUrl The sourceUrl
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    /**
     * @return The spoonacularSourceUrl
     */
    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    /**
     * @param spoonacularSourceUrl The spoonacularSourceUrl
     */
    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }

    /**
     * @return The aggregateLikes
     */
    public Integer getAggregateLikes() {
        return aggregateLikes;
    }

    /**
     * @param aggregateLikes The aggregateLikes
     */
    public void setAggregateLikes(Integer aggregateLikes) {
        this.aggregateLikes = aggregateLikes;
    }

    /**
     * @return The creditText
     */
    public String getCreditText() {
        return creditText;
    }

    /**
     * @param creditText The creditText
     */
    public void setCreditText(String creditText) {
        this.creditText = creditText;
    }

    /**
     * @return The sourceName
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * @param sourceName The sourceName
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * @return The extendedIngredients
     */
    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    /**
     * @param extendedIngredients The extendedIngredients
     */
    public void setExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The readyInMinutes
     */
    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    /**
     * @param readyInMinutes The readyInMinutes
     */
    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

}
