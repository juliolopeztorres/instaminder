package oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.Model;

import java.util.Date;

public class Photo {
    private String id;
    private String name;
    private String caption;
    private Date date;
    private byte[] buffer;
    private String log;
    @oob.instagramapitest.Util.Database.Photo.State
    private String state;

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

    public String getState() {
        return state;
    }

    public Photo setState(String state) {
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
