package oob.instagramapitest.Util.Database;

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
    private String name;
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

    public String getName() {
        return name;
    }

    public Photo setName(String name) {
        this.name = name;
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

// GET PHOTO STUFF
//List<Photo> photos = this.realm.where(Photo.class).findAll();

// Remove all
        /*this.realm.beginTransaction();
        this.realm.delete(Photo.class);
        this.realm.commitTransaction();*/

// Find
// this.realm.where(Photo.class).greaterThanOrEqualTo("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2018-07-02 13:00:00")).findAll()

 /*private void addMockPhotos() {
        final Photo photo = new Photo();
        final Photo photo2 = new Photo();

        byte[] buffer;

        try {
            InputStream is = getAssets().open("example_image.jpg");
            buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        photo.setId(UUID.randomUUID().toString());
        photo.setBuffer(buffer);
        photo.setCaption("Awesome photo");

        photo2.setId(UUID.randomUUID().toString());
        photo2.setBuffer(buffer);
        photo2.setCaption("HEY YO");

        try {
            photo.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2018-07-03 16:22:00"));
            photo2.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2018-07-03 14:30:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Photo> photos = new ArrayList<Photo>() {{
            add(photo);
            add(photo2);
        }};

        this.realm.beginTransaction();
        this.realm.insertOrUpdate(photos);
        this.realm.commitTransaction();
    }*/