package com.cleanappsample.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cleanappsample.model.Chat;
import com.cleanappsample.screen.ChatListScreen;

import java.util.List;

import io.techery.presenta.mortar.PresenterService;

/**
 * Created by Lubenets Vladyslav on 10/22/16.
 */

public class ChatListView extends ListView {
    private ChatListScreen.Presenter presenter;

    public ChatListView(Context context) {
        super(context);
        init();
    }

    public ChatListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChatListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChatListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        presenter = PresenterService.getPresenter(getContext());
    }

    public void showConversations(List<Chat> chats) {
        Adapter adapter = new Adapter(getContext(), chats);

        setAdapter(adapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onConversationSelected(position);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    private static class Adapter extends ArrayAdapter<Chat> {
        public Adapter(Context context, List<Chat> chats) {
            super(context, android.R.layout.simple_list_item_1, chats);
        }
    }
}
