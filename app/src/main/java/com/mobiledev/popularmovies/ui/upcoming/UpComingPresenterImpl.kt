package com.mobiledev.popularmovies.ui.upcoming

import com.mobiledev.popularmovies.data.DataManager
import com.mobiledev.popularmovies.data.model.UpComingResponseModel
import com.mobiledev.popularmovies.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import javax.inject.Inject

/**
 * Created by manu on 2/28/2018.
 */


class UpComingPresenterImpl<V : UpComingView> @Inject
constructor(controller: DataManager, compositeDisposable: CompositeDisposable) : BasePresenter<V>(controller, compositeDisposable), UpComingPresenter<V> {

    override fun fetchAllUpComingMovies(page: Int) {
        mvpView?.showLoading()
        compositeDisposable.add(dataManager
                .getUpComingMovies(page)
                .timeInterval()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<Timed<UpComingResponseModel>>() {
                    override fun onComplete() {}
                    override fun onError(error: Throwable) {
                        mvpView?.hideLoading()
                       // mvpView?.onError("Please try again")
                    }

                    override fun onNext(productModels: Timed<UpComingResponseModel>) {
                        mvpView?.hideLoading()
                        mvpView?.onGettingTopRatedMovieList(productModels.value())
                    }
                }))
    }
}