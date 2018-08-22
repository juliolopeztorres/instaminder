package oob.instaminder.HomeComponent.Data.GetAllPhotosUseCase;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import oob.instaminder.HomeComponent.Data.GetAllPhotosUseCase.Mapper.PhotoCollectionMapper;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCaseRepositoryInterface;

public class GetAllPhotosUseCaseRepository implements GetAllPhotosUseCaseRepositoryInterface {
    private Realm realm;
    // Keep strong reference to the list so that realm/GC does not get rid of it
    private RealmResults<oob.instaminder.Util.Database.Photo> photosDB;

    public GetAllPhotosUseCaseRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void fetchAllOrderByDate(final Callback callback) {
        this.photosDB = this.realm.where(oob.instaminder.Util.Database.Photo.class).sort("date").findAll();
        this.photosDB.addChangeListener(new RealmChangeListener<RealmResults<oob.instaminder.Util.Database.Photo>>() {
            @Override
            public void onChange(@NonNull RealmResults<oob.instaminder.Util.Database.Photo> photosDB) {
                callback.onPhotoListRetrieved(PhotoCollectionMapper.map(photosDB));
            }
        });

        callback.onPhotoListRetrieved(PhotoCollectionMapper.map(this.photosDB));
    }
}
