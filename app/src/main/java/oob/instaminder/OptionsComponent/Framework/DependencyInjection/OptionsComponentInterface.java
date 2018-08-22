package oob.instaminder.OptionsComponent.Framework.DependencyInjection;

import dagger.Component;
import oob.instaminder.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;
import oob.instaminder.OptionsComponent.Framework.OptionsActivity;

@OptionsComponentScopeInterface
@Component(modules = OptionsComponentModule.class, dependencies = BaseApplicationComponentInterface.class)
public interface OptionsComponentInterface {
    void inject(OptionsActivity optionsActivity);
}
