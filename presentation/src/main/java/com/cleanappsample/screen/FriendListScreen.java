/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cleanappsample.screen;

import android.os.Bundle;

import com.cleanappsample.R;
import com.cleanappsample.flow.HasParent;
import com.cleanappsample.model.User;
import com.cleanappsample.view.FriendListView;

import java.util.ArrayList;
import java.util.List;

import flow.path.Path;
import io.techery.presenta.addition.flow.path.Layout;
import io.techery.presenta.mortarscreen.presenter.InjectablePresenter;
import io.techery.presenta.mortarscreen.presenter.WithPresenter;

@Layout(R.layout.friend_list_view) @WithPresenter(FriendListScreen.Presenter.class)
public class FriendListScreen extends Path implements HasParent {

  @Override
  public Object getParent() {
    return null;
  }

  public static class Presenter extends InjectablePresenter<FriendListView> {
    List<User> friends;
    public Presenter(PresenterInjector injector) {
      super(injector);
      friends = new ArrayList<>();
      for (int i = 0; i < 100; i++) {
        friends.add(new User(i, "User " + i));
      }
    }

    @Override
    public void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);
      if (!hasView()) return;
      getView().showFriends(friends);
    }

    public void onFriendSelected(int position) {
      //TODO: complete
//      Flow.get(getView()).set(new FriendScreen(position));
    }
  }

}