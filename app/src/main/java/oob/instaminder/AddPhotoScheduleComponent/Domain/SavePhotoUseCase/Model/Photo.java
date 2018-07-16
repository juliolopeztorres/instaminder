package oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model;

import java.util.Date;

public class Photo {
    private static final int PHOTO_BUFFER_MAX_LENGTH = 16777200;

    private String name;
    private String caption;
    private Date date;
    private byte[] buffer;

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

    public static boolean validate(Photo photo) {
        return !photo.getName().isEmpty() &&
                !photo.getCaption().isEmpty() &&
                photo.getDate() != null &&
                photo.getBuffer() != null && photo.getBuffer().length > 0 && photo.getBuffer().length <= PHOTO_BUFFER_MAX_LENGTH;
    }
}
