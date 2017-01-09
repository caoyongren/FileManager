package com.jiepier.filemanager.ui.category.memory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jiepier.filemanager.bean.AppProcessInfo;
import com.jiepier.filemanager.manager.CategoryManager;

import java.util.List;
import java.util.Set;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by panruijie on 17/1/9.
 * Email : zquprj@gmail.com
 */

public class MemoryPresenter implements MemoryContact.Presenter {

    private Context mContext;
    private CategoryManager mCategoryManager;
    private MemoryContact.View mView;
    private CompositeSubscription mCompositeSubscription;

    public MemoryPresenter(Context context){
        mContext = context;
        mCategoryManager = CategoryManager.getInstance();
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getRunningAppInfo() {

        mView.showLoadingView();

        mCategoryManager.getRunningAppList()
                .subscribe(appProcessInfos -> {
                    mView.dimissLoadingView();
                    mView.setData(appProcessInfos);
                }, Throwable::printStackTrace);
    }

    @Override
    public void killRunningAppInfo(Set<String> set) {

        mView.showLoadingView();

        mCategoryManager.killRunningAppUsingObservable(set)
            .subscribe(memory -> {
                mView.dimissLoadingView();
                mView.showMemoryClean(memory);
            });
    }

    @Override
    public void attachView(@NonNull MemoryContact.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mCompositeSubscription.isUnsubscribed())
            this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
    }
}