package com.cleanappsample.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cleanappsample.model.Message;
import com.cleanappsample.screen.ChatScreen;

import io.techery.presenta.mortar.PresenterService;

/**
 * Created by Lubenets Vladyslav on 10/23/16.
 */

public class ChatView extends ListView {
    ChatScreen.Presenter presenter;

    public ChatView(Context context) {
        super(context);
        init();
    }

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChatView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        presenter = PresenterService.getPresenter(getContext());
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }


    public ArrayAdapter<Message> getItems() {
        @SuppressWarnings("unchecked") ArrayAdapter<Message> adapter =
                (ArrayAdapter<Message>) getAdapter();

        if (adapter == null) {
            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
            setAdapter(adapter);
            adapter.setNotifyOnChange(true);
            setOnItemClickListener(new OnItemClickListener() {
                @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    presenter.onConversationSelected(position);
                }
            });
        }

        return adapter;
    }

}
