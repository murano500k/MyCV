package com.stc.cv;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.stc.cv.model.Contacts;
import com.stc.cv.model.ContactsResponse;
import com.stc.cv.ui.common.CommonFragment;
import com.stc.cv.ui.projects.ProjectsFragment;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private static final String TAG = "MainActivity";
	private DrawerLayout mDrawerLayout;
	private NavigationView navView;
	private Contacts contacts;
	private FirebaseAnalytics analytics;
	private int selectedSection;
	private Subscription subscription;
	private Toolbar toolbar;
	private CollapsingToolbarLayout collapsingToolbarLayout;
    ActionBarDrawerToggle mDrawerToggle;
    AppBarLayout appBarLayout;
	//private ProgressBar progress;
	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
	private boolean mUserLearnedDrawer;
	FloatingActionButton fab;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		analytics = FirebaseAnalytics.getInstance(this);

		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) findViewById(R.id.toolbar2);
		setSupportActionBar(toolbar);
		collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
		fab = (FloatingActionButton) findViewById(R.id.download);

		//progress=(ProgressBar)findViewById(R.id.progress);
		navView=(NavigationView) findViewById(R.id.nav_view) ;
		mDrawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        appBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        mDrawerToggle = new ActionBarDrawerToggle(
				this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

		navView.setNavigationItemSelectedListener(this);
		if (savedInstanceState == null) {
			navView.setCheckedItem(R.id.action_common);
			showCommon();
		} else {
			int section = savedInstanceState.getInt("section", R.id.action_projects);
			if (section == R.id.action_projects) {
				showProjects();
			} else {
				showCommon();
			}
		}
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
        if (!mUserLearnedDrawer) {
            mDrawerLayout.openDrawer(navView);




            mUserLearnedDrawer = true;
            sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
        }

    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("section", selectedSection);
	}
    private void showProjects() {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_main, ProjectsFragment.instance(), ProjectsFragment.TAG)
				.commit();

		appBarLayout.setExpanded(true, true);
		collapsingToolbarLayout.setTitle(getString(R.string.projects));
		fab.setImageResource(R.drawable.ic_github);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				analytics.logEvent("github_profile_clicked", bundle);

				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.gitHub));
				try {
					startActivity(intent);

				} catch (ActivityNotFoundException exception) {

					Snackbar snackbar = Snackbar
							.make(v, R.string.cant_help_it, Snackbar.LENGTH_LONG);

					snackbar.show();
				}
			}});
        selectedSection = R.id.action_projects;

	}

    private void showCommon() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, CommonFragment.instance(), CommonFragment.TAG)
                .commit();

        appBarLayout.setExpanded(true, true);
        collapsingToolbarLayout.setTitle(getString(R.string.action_common));
        fab.setImageResource(R.drawable.ic_pdf_file_format_symbol);

        fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (contacts != null) {
              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.cv));
              startActivity(intent);
              analytics.logEvent("download_cv_pressed", null);
            }
          }
        });

        selectedSection = R.id.action_common;
    }
	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			mDrawerLayout.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}


	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();

		switch (id) {
			case R.id.action_projects:
				if (selectedSection != R.id.action_projects) {
					showProjects();
					analytics.logEvent("drawer_projects_selected", null);
				}
				mDrawerLayout.closeDrawer(GravityCompat.START);
				break;

			case R.id.action_common:
				if (selectedSection != R.id.action_common) {
					showCommon();
					analytics.logEvent("drawer_common_selected", null);
				}
				mDrawerLayout.closeDrawer(GravityCompat.START);
				break;

			case R.id.action_email:
				if (contacts != null) {
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
							"mailto", contacts.email, null));
					startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_chooser)));
					analytics.logEvent("contact_email", null);
				}

				break;

			case R.id.action_phone:
				if (contacts != null) {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contacts.phone, null));
					startActivity(intent);
					analytics.logEvent("contact_phone", null);
				}
				break;

			case R.id.action_github:
				if (contacts != null) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.gitHub));
					startActivity(intent);
					analytics.logEvent("contact_github", null);
				}
				break;
		}


		return true;
	}


	@Override
	protected void onStart() {
		super.onStart();

		subscription = RxFirebaseDatabase
				.observeValueEvent(FirebaseDatabase.getInstance().getReference(), ContactsResponse.class)
				.map(contactsResponse -> {

					return contactsResponse.contacts ;
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(cont -> {
					contacts = cont;

					TextView name = (TextView) navView.findViewById(R.id.name);
					name.setText(contacts.name);

					TextView prof = (TextView) navView.findViewById(R.id.profession);
					prof.setText(contacts.profession);

					ImageView userPic = (ImageView) navView.findViewById(R.id.user_pic);
					Picasso.with(MainActivity.this)
							.load(contacts.userPic)
							.placeholder(R.drawable.ic_android)
							.error(R.drawable.ic_android)
							.into(userPic);

					ImageView certPic = (ImageView) navView.findViewById(R.id.cert_img);
					Picasso.with(MainActivity.this)
							.load(contacts.certImgUrl)
							.placeholder(R.drawable.ic_android)
							.error(R.drawable.ic_android)
							.into(certPic);
					certPic.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.certUrl));

							startActivity(intent);
						}
					});

				}, t -> {
					Log.e(TAG, "onStart: ",t);
					FirebaseCrash.report(t);
				});

	}

	@Override
	protected void onStop() {
		super.onStop();
		if (subscription != null && !subscription.isUnsubscribed()) {
			subscription.unsubscribe();
		}
	}


}
