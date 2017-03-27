package com.stc.cv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
	private DrawerLayout drawerLayout;
	private NavigationView navView;
	private Contacts contacts;
	private FirebaseAnalytics analytics;
	private int selectedSection;
	private Subscription subscription;
	private Toolbar toolbar;
	private CollapsingToolbarLayout collapsingToolbarLayout;

	//private ProgressBar progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		analytics = FirebaseAnalytics.getInstance(this);

		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) findViewById(R.id.toolbar2);
		setSupportActionBar(toolbar);
		collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.download);
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
		//progress=(ProgressBar)findViewById(R.id.progress);
		navView=(NavigationView) findViewById(R.id.nav_view) ;
		drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);


		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawerLayout.setDrawerListener(toggle);
		toggle.syncState();

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
		//getSupportActionBar().setTitle(selectedSection);

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
		toolbar.setTitle(R.string.projects);
		collapsingToolbarLayout.setTitle(getString(R.string.projects));
		selectedSection = R.id.action_projects;
	}

	@Override
	public void onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
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
				drawerLayout.closeDrawer(GravityCompat.START);
				break;

			case R.id.action_common:
				if (selectedSection != R.id.action_common) {
					showCommon();
					analytics.logEvent("drawer_common_selected", null);
				}
				drawerLayout.closeDrawer(GravityCompat.START);
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

	private void showCommon() {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_main, CommonFragment.instance(), CommonFragment.TAG)
				.commit();
		toolbar.setTitle(R.string.action_common);
		collapsingToolbarLayout.setTitle(getString(R.string.action_common));
		selectedSection = R.id.action_common;
	}

	@Override
	protected void onStart() {
		super.onStart();

		subscription = RxFirebaseDatabase
				.observeValueEvent(FirebaseDatabase.getInstance().getReference(), ContactsResponse.class)
				.map(contactsResponse -> {

                    /*Map<String, String> strings = contactsResponse.resources.get(
                            LocaleService.getInstance().getLocale(MainActivity.this));

                    Contacts contacts = contactsResponse.contacts ;
                    contacts.cv = strings.get(contacts.cvKey);
                    contacts.name = strings.get(contacts.nameKey);
                    contacts.profession = strings.get(contacts.professionKey);
*/
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
