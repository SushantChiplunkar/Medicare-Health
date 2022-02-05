package com.test.medicarehealth.model;

import android.os.Parcelable;

public class Launch implements Parcelable
{

    private String id;
    private String provider;
    public final static Creator<Launch> CREATOR = new Creator<Launch>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Launch createFromParcel(android.os.Parcel in) {
            return new Launch(in);
        }

        public Launch[] newArray(int size) {
            return (new Launch[size]);
        }

    }
            ;

    protected Launch(android.os.Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.provider = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Launch() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(provider);
    }

    public int describeContents() {
        return 0;
    }

}
