package assignment2.instruments;

/*
    This class represents a record of an instrument. Each record consists
    of a DataKey and data.
 */

import assignment2.instruments.DataKey;

public class InstrumentRecord {

    private DataKey key;
    private String about;
    private String sound;
    private String image;

    public InstrumentRecord() { this(null, null, null, null);}

    public InstrumentRecord(DataKey k, String a, String s, String i) {
        key = k;
        about = a;
        sound = s;
        image = i;
    }

    //public getters
    public DataKey getDataKey() { return key; }
    public String getAbout() { return about; }
    public String getSound() { return sound; }
    public String getImage() { return image; }
}
