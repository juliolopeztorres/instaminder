package oob.instagramapitest.AddPhotoScheduleComponent.Data.SavePhotoUseCase;

import io.realm.Realm;
import oob.instagramapitest.AddPhotoScheduleComponent.Data.SavePhotoUseCase.Mapper.PhotoMapper;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model.Photo;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseRepositoryInterface;

public class SavePhotoUseCaseRepository implements SavePhotoUseCaseRepositoryInterface {
    private Realm realm;

    public SavePhotoUseCaseRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void save(Photo photo) {
        this.realm.beginTransaction();
        this.realm.insertOrUpdate(PhotoMapper.map(photo));
        this.realm.commitTransaction();
    }
}
