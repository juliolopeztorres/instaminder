package oob.instagramapitest.AddPhotoScheduleComponent.Data.SavePhotoUseCase.Mapper;

import java.util.UUID;

import oob.instagramapitest.Util.Database.Photo;

public class PhotoMapper {
    public static Photo map(oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model.Photo photo) {
        Photo photoDB = new Photo();

        photoDB.setId(UUID.randomUUID().toString())
                .setName(photo.getName())
                .setCaption(photo.getCaption())
                .setDate(photo.getDate())
                .setBuffer(photo.getBuffer())
                .setState(Photo.PENDING);

        return photoDB;
    }
}
