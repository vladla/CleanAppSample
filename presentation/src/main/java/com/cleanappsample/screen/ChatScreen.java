package com.cleanappsample.screen;

import com.cleanappsample.flow.HasParent;
import com.cleanappsample.model.Chat;
import com.cleanappsample.model.Chats;
import com.cleanappsample.view.ChatView;

import javax.inject.Inject;

import flow.Flow;
import flow.path.Path;
import io.techery.presenta.mortarscreen.presenter.InjectablePresenter;

/**
 * Created by Lubenets Vladyslav on 10/23/16.
 */

public class ChatScreen extends Path implements HasParent {

    private final int conversationIndex; // available in presenter as outer class field

    public ChatScreen(int conversationIndex) {
        this.conversationIndex = conversationIndex;
    }

    @Override
    public ChatListScreen getParent() {
        return new ChatListScreen();
    }

    public class Presenter extends InjectablePresenter<ChatView> {
        @Inject
        Chats chats;
        private final Chat chat;


        public Presenter(PresenterInjector injector) {
            super(injector);
            this.chat = chats.getChat(conversationIndex);
        }

        public void onConversationSelected(int position) {
            Flow.get(getView().getContext()).set(new MessageScreen(chat.getId(), position));
        }
    }
}
