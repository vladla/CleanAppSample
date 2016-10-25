package com.cleanappsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cleanappsample.di.ScreenComponent;
import com.cleanappsample.network.components.ApplicationComponent;
import com.cleanappsample.screen.FriendListScreen;
import com.cleanappsample.view.BaseActivity;
import com.google.gson.Gson;

import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.path.Path;
import flow.path.PathContainerView;
import io.techery.presenta.addition.flow.util.BackSupport;
import io.techery.presenta.addition.flow.util.GsonParceler;
import io.techery.presenta.di.ScreenScope;
import io.techery.presenta.mortar.DaggerService;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import com.cleanappsample.actions.UsersAction;
import com.cleanappsample.view.BaseActivity;

import javax.inject.Inject;

import io.techery.janet.ActionPipe;
import io.techery.janet.Janet;
import io.techery.janet.helper.ActionStateSubscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, Flow.Dispatcher {

//    private enum Screens {
//        CAMERA(R.id.nav_camera, new ImportScreen()),
//        GALLERY(R.id.nav_gallery, new GalleryScreen()),
//        SLIDESHOW(R.id.nav_slideshow, new SlideShowScreen()),
//        TOOLS(R.id.nav_manage, new ToolsScreen()),
//        SHARE(R.id.nav_share, new ShareScreen()),
//        SEND(R.id.nav_send, new SendScreen());
//
//        private final int id;
//        private final Path screen;
//
//        Screens(int id, Path screen) {
//            this.id = id;
//            this.screen = screen;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public Path getScreen() {
//            return screen;
//        }
//
//        public static Screens getScreenById(int itemId) {
//            for (Screens screen : Screens.values()) {
//                if (screen.getId() == itemId) {
//                    return screen;
//                }
//            }
//            return null;
//        }
//    }

    @ScreenScope(MainActivity.class)
    @dagger.Component(dependencies = ApplicationComponent.class)
    public interface Component extends ApplicationComponent, ScreenComponent {
        void inject(MainActivity activity);
    }

    private FlowDelegate flowSupport;
    private MortarScope activityScope;
    private PathContainerView container;
    private BackSupport.HandlesBack containerAsBackTarget;

    @Inject
    Janet janet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object appComponent = DaggerService.getDaggerComponent(getApplicationContext());
        Component component = DaggerService.createComponent(Component.class, appComponent);
        component.inject(this);

        String scopeName = getLocalClassName() + "-task-" + getTaskId();
        MortarScope parentScope = MortarScope.getScope(getApplication());
        activityScope = parentScope.findChild(scopeName);
        if (activityScope == null) {
            activityScope = parentScope.buildChild()
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build(scopeName);
        }
        BundleServiceRunner.getBundleServiceRunner(activityScope).onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        container = (PathContainerView) findViewById(R.id.container);
        containerAsBackTarget = (BackSupport.HandlesBack) container;


        History history = History.single(new FriendListScreen());
        FlowDelegate.NonConfigurationInstance nonConfig = (FlowDelegate.NonConfigurationInstance) getLastCustomNonConfigurationInstance();
        flowSupport = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, new GsonParceler(new Gson()), history, this);
    }

    @Override public Object getSystemService(String name) {
        if (flowSupport != null) {
            Object flowService = flowSupport.getSystemService(name);
            if (flowService != null) return flowService;
        }

        return activityScope != null && activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowSupport.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        flowSupport.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowSupport.onSaveInstanceState(outState);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return flowSupport.onRetainNonConfigurationInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowSupport.onResume();
        ActionPipe<UsersAction> actionPipe = janet.createPipe(UsersAction.class, Schedulers.io());
        actionPipe.observe().subscribe(new ActionStateSubscriber<UsersAction>()
                .onStart(action -> System.out.println("Request is being sent " + action))
                .onProgress((action, progress) -> System.out.println("Request in progress: " + progress))
                .onSuccess(action -> Log.d("onSuccess", action.getResponse().toString()))
                .onFail((action, throwable) -> Log.d("onError", throwable.getLocalizedMessage()))
        );
        actionPipe.send(new UsersAction());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (containerAsBackTarget.onBackPressed()) return;
            if (flowSupport.onBackPressed()) return;
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        Path path = traversal.destination.top();
        setTitle(path.getClass().getSimpleName());
        boolean canGoBack = traversal.destination.size() > 1;
//        String title = path.getClass().getSimpleName();
//        ActionBarOwner.MenuAction menu = canGoBack ? null : new ActionBarOwner.MenuAction("Friends", new Runnable() {
//            @Override
//            public void run() {
//                Flow.get(MortarDemoActivity.this).set(new FriendListScreen());
//            }
//        });
//        actionBarOwner.setConfig(new ActionBarOwner.Config(false, canGoBack, title, menu));
        container.dispatch(traversal, new Flow.TraversalCallback() {
            @Override
            public void onTraversalCompleted() {
                invalidateOptionsMenu();
                callback.onTraversalCompleted();
            }
        });

    }
}
