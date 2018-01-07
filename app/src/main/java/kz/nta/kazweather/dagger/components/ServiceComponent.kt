package kz.nta.kazweather.dagger.components

import dagger.Component
import kz.nta.kazweather.dagger.modules.ServiceModule
import kz.nta.kazweather.dagger.scopes.PerActivity
import kz.nta.kazweather.mvp.presenters.MainActivityPresenter


@PerActivity
@Component(modules = [ServiceModule::class], dependencies = [AppComponent::class] )
interface ServiceComponent{
    fun injectMainActivityPresenter(presenter: MainActivityPresenter)
}