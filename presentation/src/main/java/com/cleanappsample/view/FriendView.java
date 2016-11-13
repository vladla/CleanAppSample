package com.cleanappsample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cleanappsample.R;
import com.cleanappsample.screen.FriendScreen;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.techery.presenta.mortar.DaggerService;

/**
 * Created by Lubenets Vladyslav on 10/30/16.
 */

public class FriendView extends FrameLayout {

    @Inject
    FriendScreen.Presenter presenter;

    @Bind(R.id.friend_info)
    TextView friendInfo;

    public FriendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DaggerService.<FriendScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
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

    public void setFriends(String name) {
        friendInfo.setText(name);
    }
}
