package oob.instagramapitest.HomeComponent.Data.GetAllPhotosUseCase;

import java.util.List;

import io.realm.Realm;
import oob.instagramapitest.HomeComponent.Data.GetAllPhotosUseCase.Mapper.PhotoCollectionMapper;
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCaseRepositoryInterface;
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public class GetAllPhotosUseCaseRepository implements GetAllPhotosUseCaseRepositoryInterface {
    private Realm realm;

    public GetAllPhotosUseCaseRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public List<Photo> getAllOrderByDate() {
        return PhotoCollectionMapper.map(this.realm.where(oob.instagramapitest.Util.Database.Photo.class).sort("date").findAll());
    }
}
