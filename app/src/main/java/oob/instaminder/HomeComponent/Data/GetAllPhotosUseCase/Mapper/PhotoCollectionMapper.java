package oob.instaminder.HomeComponent.Data.GetAllPhotosUseCase.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public class PhotoCollectionMapper {
    public static List<Photo> map(RealmResults<oob.instaminder.Util.Database.Photo> photosDB) {
        List<Photo> photos = new ArrayList<>();

        for (oob.instaminder.Util.Database.Photo photoDB : photosDB) {
            photos.add(PhotoMapper.map(photoDB));
        }

        return photos;
    }
}
