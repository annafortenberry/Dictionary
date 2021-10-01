package assignment2.instruments;

/*
    This class represents a record of an instrument. Each record consists
    of a DataKey and data.
 */

public class InstrumentRecord {

    private DataKey key;
    private String about;
    private String sound;
    private String image;

    public InstrumentRecord() { this(null, null, null, null);}

    public InstrumentRecord(DataKey k, String a, String s, String i) {
        this.key = k;
        this.about = a;
        this.sound = s;
        this.image = i;
    }

    //public getters
    public DataKey getDataKey() { return this.key; }
    public String getAbout() { return this.about; }
    public String getSound() { return this.sound; }
    public String getImage() { return this.image; }
}
