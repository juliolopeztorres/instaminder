package oob.instaminder.Util.Database;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Photo extends RealmObject {

    public static final String PENDING = "PENDING";
    public static final String ERROR = "ERROR";

    @StringDef({PENDING, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @PrimaryKey
    private String id;
    private String caption;
    private Date date;
    private byte[] buffer;

    @State
    private String state;
    private String log;

    public String getId() {
        return id;
    }

    public Photo setId(String id) {
        this.id = id;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public Photo setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Photo setDate(Date date) {
        this.date = date;
        return this;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public Photo setBuffer(byte[] buffer) {
        this.buffer = buffer;
        return this;
    }

    @State
    public String getState() {
        return state;
    }

    public Photo setState(@State String state) {
        this.state = state;
        return this;
    }

    public String getLog() {
        return log;
    }

    public Photo setLog(String log) {
        this.log = log;
        return this;
    }
}
