package oob.instaminder.AddPhotoScheduleComponent.Framework.DependencyInjection;

import dagger.Component;
import oob.instaminder.AddPhotoScheduleComponent.Framework.AddPhotoScheduleActivity;
import oob.instaminder.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;

@AddPhotoScheduleComponentScopeInterface
@Component(modules = AddPhotoScheduleComponentModule.class, dependencies = BaseApplicationComponentInterface.class)
public interface AddPhotoScheduleComponentInterface {
    void inject(AddPhotoScheduleActivity addPhotoScheduleActivity);
}
