package rs.ac.uns.ftn.chiefcook.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ExtendedIngredient implements Parcelable {

    private Integer id;
    private String aisle;
    private String image;
    private String name;
    private Double amount;
    private String unit;
    private String unitShort;
    private String unitLong;
    private String originalString;
    private List<String> metaInformation = new ArrayList<String>();
    /**
     * Indicates if ingredient is selected in a list view with checkboxes
     */
    private Boolean selected = true;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The aisle
     */
    public String getAisle() {
        return aisle;
    }

    /**
     *
     * @param aisle
     * The aisle
     */
    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     *
     * @param unit
     * The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     *
     * @return
     * The unitShort
     */
    public String getUnitShort() {
        return unitShort;
    }

    /**
     *
     * @param unitShort
     * The unitShort
     */
    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    /**
     *
     * @return
     * The unitLong
     */
    public String getUnitLong() {
        return unitLong;
    }

    /**
     *
     * @param unitLong
     * The unitLong
     */
    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }

    /**
     *
     * @return
     * The originalString
     */
    public String getOriginalString() {
        return originalString;
    }

    /**
     *
     * @param originalString
     * The originalString
     */
    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    /**
     *
     * @return
     * The metaInformation
     */
    public List<String> getMetaInformation() {
        return metaInformation;
    }

    /**
     *
     * @param metaInformation
     * The metaInformation
     */
    public void setMetaInformation(List<String> metaInformation) {
        this.metaInformation = metaInformation;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.aisle);
        dest.writeString(this.image);
        dest.writeString(this.name);
        dest.writeValue(this.amount);
        dest.writeString(this.unit);
        dest.writeString(this.unitShort);
        dest.writeString(this.unitLong);
        dest.writeString(this.originalString);
        dest.writeStringList(this.metaInformation);
        dest.writeValue(this.selected);
    }

    public ExtendedIngredient() {
    }

    protected ExtendedIngredient(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.aisle = in.readString();
        this.image = in.readString();
        this.name = in.readString();
        this.amount = (Double) in.readValue(Double.class.getClassLoader());
        this.unit = in.readString();
        this.unitShort = in.readString();
        this.unitLong = in.readString();
        this.originalString = in.readString();
        this.metaInformation = in.createStringArrayList();
        this.selected = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExtendedIngredient> CREATOR = new Parcelable.Creator<ExtendedIngredient>() {
        @Override
        public ExtendedIngredient createFromParcel(Parcel source) {
            return new ExtendedIngredient(source);
        }

        @Override
        public ExtendedIngredient[] newArray(int size) {
            return new ExtendedIngredient[size];
        }
    };
}