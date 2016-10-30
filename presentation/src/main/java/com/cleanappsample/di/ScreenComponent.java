package com.cleanappsample.di;


import com.cleanappsample.screen.FriendListScreen;

public interface ScreenComponent {
  void inject(FriendListScreen.Presenter presenter);
}
