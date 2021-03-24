package com.emilienbidet.binderstpatrick;

public class Filters {

    public static String DEFAULT_NAME = "";
    public static int DEFAULT_ABV_MIN = 0;
    public static int DEFAULT_ABV_MAX = 70;
    public static int DEFAULT_IBU_MIN = 0;
    public static int DEFAULT_IBU_MAX = 150;
    public static int DEFAULT_EBC_MIN = 0;
    public static int DEFAULT_EBC_MAX = 140;

    public static String PAGE = "&page=";
    public static String BEER_NAME = "&beer_name=";
    public static String ABV_GT = "&abv_gt=";
    public static String ABV_LT = "&abv_lt=";
    public static String IBU_GT = "&ibu_gt=";
    public static String IBU_LT = "&ibu_lt=";
    public static String EBC_GT = "&ebc_gt=";
    public static String EBC_LT = "&ebc_lt=";

    private String name;
    private int abvMin;
    private int abvMax;
    private int ibuMin;
    private int ibuMax;
    private int ebcMin;
    private int ebcMax;

    public Filters(){
        this.name = DEFAULT_NAME;
        this.abvMin = DEFAULT_ABV_MIN;
        this.abvMax = DEFAULT_ABV_MAX;
        this.ibuMin = DEFAULT_IBU_MIN;
        this.ibuMax = DEFAULT_IBU_MAX;
        this.ebcMin = DEFAULT_EBC_MIN;
        this.ebcMax = DEFAULT_EBC_MAX;
    }

    public String getName() {
        return name;
    }

    public int getAbvMin() {
        return abvMin;
    }

    public int getAbvMax() {
        return abvMax;
    }

    public int getIbuMin() {
        return ibuMin;
    }

    public int getIbuMax() {
        return ibuMax;
    }

    public int getEbcMin() {
        return ebcMin;
    }

    public int getEbcMax() {
        return ebcMax;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbvMin(int abvMin) {
        this.abvMin = abvMin;
    }

    public void setAbvMax(int abvMax) {
        this.abvMax = abvMax;
    }

    public void setIbuMin(int ibuMin) {
        this.ibuMin = ibuMin;
    }

    public void setIbuMax(int ibuMax) {
        this.ibuMax = ibuMax;
    }

    public void setEbcMin(int ebcMin) {
        this.ebcMin = ebcMin;
    }

    public void setEbcMax(int ebcMax) {
        this.ebcMax = ebcMax;
    }

    @Override
    public String toString() {
        return "Filters{" +
                "name='" + name + '\'' +
                ", abvMin=" + abvMin +
                ", abvMax=" + abvMax +
                ", ibuMin=" + ibuMin +
                ", ibuMax=" + ibuMax +
                ", ebcMin=" + ebcMin +
                ", ebcMax=" + ebcMax +
                '}';
    }
}
