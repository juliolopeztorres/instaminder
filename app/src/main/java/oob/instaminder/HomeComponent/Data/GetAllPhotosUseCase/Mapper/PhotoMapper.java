package oob.instaminder.HomeComponent.Data.GetAllPhotosUseCase.Mapper;

import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public class PhotoMapper {
    public static Photo map(oob.instaminder.Util.Database.Photo photoDB) {
        Photo photo = new Photo();

        photo.setId(photoDB.getId())
                .setCaption(photoDB.getCaption())
                .setDate(photoDB.getDate())
                .setBuffer(photoDB.getBuffer())
                .setState(photoDB.getState())
                .setLog(photoDB.getLog());

        return photo;
    }
}
