package oob.instagramapitest.HomeComponent.Framework.DependencyInjection;

import dagger.Component;
import oob.instagramapitest.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;
import oob.instagramapitest.HomeComponent.Framework.HomeActivity;

@HomeComponentScopeInterface
@Component(modules = HomeComponentModule.class, dependencies = BaseApplicationComponentInterface.class)
public interface HomeComponentInterface {
    void inject(HomeActivity homeActivity);
}
