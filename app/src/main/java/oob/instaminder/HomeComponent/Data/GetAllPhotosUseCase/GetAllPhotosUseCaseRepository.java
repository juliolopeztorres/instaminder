package oob.instaminder.HomeComponent.Data.GetAllPhotosUseCase;

import java.util.List;

import io.realm.Realm;
import oob.instaminder.HomeComponent.Data.GetAllPhotosUseCase.Mapper.PhotoCollectionMapper;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCaseRepositoryInterface;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public class GetAllPhotosUseCaseRepository implements GetAllPhotosUseCaseRepositoryInterface {
    private Realm realm;

    public GetAllPhotosUseCaseRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public List<Photo> getAllOrderByDate() {
        return PhotoCollectionMapper.map(this.realm.where(oob.instaminder.Util.Database.Photo.class).sort("date").findAll());
    }
}
