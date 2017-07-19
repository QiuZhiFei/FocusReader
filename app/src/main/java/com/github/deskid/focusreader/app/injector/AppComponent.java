package com.github.deskid.focusreader.app.injector;

import com.github.deskid.focusreader.activity.SplashActivity;
import com.github.deskid.focusreader.activity.WebViewActivity;
import com.github.deskid.focusreader.api.Api;
import com.github.deskid.focusreader.screens.duanzi.DuanziFragment;
import com.github.deskid.focusreader.screens.tugua.TuGuaFragment;
import com.github.deskid.focusreader.screens.yitu.ZenImageFragment;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    Api api();

    void inject(SplashActivity activity);

    void inject(WebViewActivity activity);

    void inject(TuGuaFragment fragment);

    void inject(ZenImageFragment fragment);

    void inject(DuanziFragment fragment);
}
