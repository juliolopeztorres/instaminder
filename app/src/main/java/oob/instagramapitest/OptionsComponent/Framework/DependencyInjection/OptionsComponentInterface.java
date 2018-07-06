package oob.instagramapitest.OptionsComponent.Framework.DependencyInjection;

import dagger.Component;
import oob.instagramapitest.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;
import oob.instagramapitest.OptionsComponent.Framework.OptionsActivity;

@OptionsComponentScopeInterface
@Component(modules = OptionsComponentModule.class, dependencies = BaseApplicationComponentInterface.class)
public interface OptionsComponentInterface {
    void inject(OptionsActivity optionsActivity);
}
