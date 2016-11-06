package com.cleanappsample.di;

import com.cleanappsample.actions.UsersAction;

import io.techery.janet.ActionPipe;
import rx.schedulers.Schedulers;

/**
 * Created by Lubenets Vladyslav on 11/6/16.
 */

public class UsersManager extends BaseManager {

    private ActionPipe<UsersAction> usersActionActionPipe;

    public ActionPipe<UsersAction> provideUsersPipe() {
        if (usersActionActionPipe == null) {
            usersActionActionPipe = provideJanet().createPipe(UsersAction.class, Schedulers.io());
        }
        return usersActionActionPipe;
    }

    public void users() {
        provideUsersPipe().send(new UsersAction());
    }
}
