package oob.instagramapitest.HomeComponent.Data.GetAllPhotosUseCase.Mapper;

import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public class PhotoMapper {
    public static Photo map(oob.instagramapitest.Util.Database.Photo photoDB) {
        Photo photo = new Photo();

        photo.setName(photoDB.getName())
                .setCaption(photoDB.getCaption())
                .setDate(photoDB.getDate())
                .setBuffer(photoDB.getBuffer())
                .setState(photoDB.getState());

        return photo;
    }
}
