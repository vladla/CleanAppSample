package com.cleanappsample.di;


import com.cleanappsample.screen.ChatListScreen;
import com.cleanappsample.screen.ChatScreen;
import com.cleanappsample.screen.FriendListScreen;
import com.cleanappsample.screen.FriendScreen;
import com.cleanappsample.screen.MessageScreen;

public interface ScreenComponent {
  void inject(ChatScreen.Presenter presenter);

  void inject(ChatListScreen.Presenter presenter);

  void inject(FriendListScreen.Presenter presenter);

  void inject(FriendScreen.Presenter presenter);

  void inject(MessageScreen.Presenter presenter);
}
