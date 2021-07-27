package ga.cubeqw.barhi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Barcode {
    @SerializedName("value")
    @Expose
    String value;
    @SerializedName("date")
    @Expose
    String date;
    public Barcode(String value, String date) {
        this.value = value;
        this.date = date;
    }
    public String getValue(){
        return value;
    }
    public String getDate(){
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
