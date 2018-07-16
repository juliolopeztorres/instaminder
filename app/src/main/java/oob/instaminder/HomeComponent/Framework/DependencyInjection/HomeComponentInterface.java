package oob.instaminder.HomeComponent.Framework.DependencyInjection;

import dagger.Component;
import oob.instaminder.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;
import oob.instaminder.HomeComponent.Framework.HomeActivity;

@HomeComponentScopeInterface
@Component(modules = HomeComponentModule.class, dependencies = BaseApplicationComponentInterface.class)
public interface HomeComponentInterface {
    void inject(HomeActivity homeActivity);
}
