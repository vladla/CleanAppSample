package com.cleanappsample.screen;

import android.os.Bundle;

import com.cleanappsample.MainActivity;
import com.cleanappsample.R;
import com.cleanappsample.flow.HasParent;
import com.cleanappsample.model.User;
import com.cleanappsample.model.UserModel;
import com.cleanappsample.view.FriendView;

import javax.inject.Inject;

import dagger.Provides;
import flow.path.Path;
import io.techery.presenta.addition.flow.path.Layout;
import io.techery.presenta.di.ScreenScope;
import io.techery.presenta.mortarscreen.component.WithComponent;
import mortar.ViewPresenter;

/**
 * Created by Lubenets Vladyslav on 10/30/16.
 */

@Layout(R.layout.friend_view) @WithComponent(FriendScreen.Component.class)
public class FriendScreen extends Path implements HasParent {
    private final UserModel user;

    public FriendScreen(UserModel user) {
        this.user = user;
    }

    @Override
    public FriendListScreen getParent() {
        return new FriendListScreen();
    }

    @ScreenScope(FriendScreen.class)
    public static class Presenter extends ViewPresenter<FriendView> {
        private final UserModel friend;

        @Inject
        public Presenter(UserModel user) {
            this.friend = user;
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (!hasView()) return;
            getView().setFriends("Followers "+ friend.getFollowers());
        }
    }

    @ScreenScope(FriendScreen.class)
    @dagger.Component(dependencies = MainActivity.Component.class, modules = Module.class)
    public static interface Component {
        void inject(FriendView friendView);
    }

    @dagger.Module
    public class Module {
        @Provides UserModel provideFriend() {
            return user;
        }
    }

}
