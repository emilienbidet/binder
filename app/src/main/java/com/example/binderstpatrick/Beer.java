
package com.example.binderstpatrick;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Beer implements Serializable, Parcelable
{
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;
    @ColumnInfo(name = "tagline")
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @ColumnInfo(name = "tagline")
    @SerializedName("first_brewed")
    @Expose
    private String firstBrewed;
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;
    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @ColumnInfo(name = "abv")
    @SerializedName("abv")
    @Expose
    private Double abv;
    @ColumnInfo(name = "ibu")
    @SerializedName("ibu")
    @Expose
    private Integer ibu;
    @ColumnInfo(name = "ebc")
    @SerializedName("ebc")
    @Expose
    private Integer ebc;
    @Embedded(prefix = "FoodPairings")
    @SerializedName("food_pairing")
    @Expose
    private ArrayList<String> foodPairing = null;
    @ColumnInfo(name = "brewers_tips")
    @SerializedName("brewers_tips")
    @Expose
    private String brewersTips;
    public final static Parcelable.Creator<Beer> CREATOR = new Creator<Beer>() {
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        public Beer[] newArray(int size) {
            return (new Beer[size]);
        }
    }
    ;
    private final static long serialVersionUID = 576068583839120137L;

    protected Beer(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.tagline = in.readString();
        this.firstBrewed = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.abv = in.readDouble();
        this.ibu = in.readInt();
        this.ebc = in.readInt();
        this.foodPairing = in.readArrayList(java.lang.String.class.getClassLoader());
        this.brewersTips = in.readString();
    }

    public Beer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getFirstBrewed() {
        return firstBrewed;
    }

    public void setFirstBrewed(String firstBrewed) {
        this.firstBrewed = firstBrewed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public Integer getIbu() {
        return ibu;
    }

    public void setIbu(Integer ibu) {
        this.ibu = ibu;
    }

    public Integer getEbc() {
        return ebc;
    }

    public void setEbc(Integer ebc) {
        this.ebc = ebc;
    }

    public ArrayList<String> getFoodPairing() {
        return foodPairing;
    }

    public void setFoodPairing(ArrayList<String> foodPairing) {
        this.foodPairing = foodPairing;
    }

    public String getBrewersTips() {
        return brewersTips;
    }

    public void setBrewersTips(String brewersTips) {
        this.brewersTips = brewersTips;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(abv).append(foodPairing).append(imageUrl).append(ebc).append(name).append(tagline).append(firstBrewed).append(description).append(brewersTips).append(id).append(ibu).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Beer) == false) {
            return false;
        }
        Beer rhs = ((Beer) other);
        return new EqualsBuilder().append(abv, rhs.abv).append(foodPairing, rhs.foodPairing).append(imageUrl, rhs.imageUrl).append(ebc, rhs.ebc).append(name, rhs.name).append(tagline, rhs.tagline).append(firstBrewed, rhs.firstBrewed).append(description, rhs.description).append(brewersTips, rhs.brewersTips).append(id, rhs.id).append(ibu, rhs.ibu).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(tagline);
        dest.writeString(firstBrewed);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeDouble(abv);
        dest.writeInt(ibu);
        dest.writeInt(ebc);
        dest.writeList(foodPairing);
        dest.writeString(brewersTips);
    }

    public int describeContents() {
        return  0;
    }

}
