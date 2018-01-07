package kz.nta.kazweather.mvp.presenters

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    val disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}