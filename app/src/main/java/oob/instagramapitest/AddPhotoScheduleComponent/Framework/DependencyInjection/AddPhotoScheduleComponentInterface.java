package oob.instagramapitest.AddPhotoScheduleComponent.Framework.DependencyInjection;

import dagger.Component;
import oob.instagramapitest.AddPhotoScheduleComponent.Framework.AddPhotoScheduleActivity;
import oob.instagramapitest.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;

@AddPhotoScheduleComponentScopeInterface
@Component(modules = AddPhotoScheduleComponentModule.class, dependencies = BaseApplicationComponentInterface.class)
public interface AddPhotoScheduleComponentInterface {
    void inject(AddPhotoScheduleActivity addPhotoScheduleActivity);
}
