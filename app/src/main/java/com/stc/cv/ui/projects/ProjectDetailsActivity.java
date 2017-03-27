package com.stc.cv.ui.projects;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import com.stc.cv.R;
import com.stc.cv.model.Project;

public class ProjectDetailsActivity extends AppCompatActivity {

    public static final String ICON_TRANSITION = "ICON_TRANSITION";
    public static final String PLATFORM_TRANSITION = "PLATFORM_TRANSITION";
    private FirebaseAnalytics analytics;

    private Project project;
	private Toolbar toolbar;
	private ImageView projImage, logo, platform;
	private TextView title, company, stack,duties,  description;
	private View coordinatorLayout;
	private NestedScrollView nestedScroll;
	private FloatingActionButton fabPlay, fabGithub;
	private CollapsingToolbarLayout collapsingToolbar;
	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
		fabGithub=(FloatingActionButton) findViewById(R.id.fabGithub);
		fabPlay=(FloatingActionButton) findViewById(R.id.fabPlay);
		duties=(TextView) findViewById(R.id.duties);
		description=(TextView) findViewById(R.id.description);
		stack=(TextView) findViewById(R.id.stack);
		title=(TextView) findViewById(R.id.title);
		company=(TextView) findViewById(R.id.company);
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		collapsingToolbar=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
		coordinatorLayout=findViewById(R.id.coordinator_layout);
		nestedScroll=(NestedScrollView)findViewById(R.id.nested_scroll);
		projImage=(ImageView) findViewById(R.id.proj_image);
		platform=(ImageView) findViewById(R.id.platform);
		logo=(ImageView) findViewById(R.id.logo);


        analytics = FirebaseAnalytics.getInstance(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        project = getIntent().getParcelableExtra("project");

        Picasso.with(this)
                .load(project.webPic)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(projImage);

        Picasso.with(this)
                .load(project.coverPic)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(logo);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "extra_image");


        if (project.platform.equals(Project.PLATFORM_ANDROID)) {
            platform.setImageResource(R.drawable.ic_android);
        } else {
            platform.setImageResource(R.drawable.ic_apple);
        }
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        title.setText(project.name);
        company.setText(project.vendor);

        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                Rect scrollBounds = new Rect();
                nestedScroll.getHitRect(scrollBounds);
                if (title.getLocalVisibleRect(scrollBounds)) {

                    collapsingToolbar.setTitle("");
                } else {
                    collapsingToolbar.setTitle(project.name);

                }

            }
        });


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            projImage.setTransitionName(ICON_TRANSITION);
            platform.setTransitionName(PLATFORM_TRANSITION);
        }*/

        description.setText(Html.fromHtml(project.description));
        duties.setText(Html.fromHtml(project.duties));
        stack.setText(Html.fromHtml(project.stack));

        if (TextUtils.isEmpty(project.storeUrl)) {
	        fabPlay.setVisibility(View.GONE);
        } else {
	        fabPlay.setVisibility(View.VISIBLE);
	        fabPlay.setOnClickListener(view -> {

		        Bundle bundle = new Bundle();
		        bundle.putString("project", project.name);
		        analytics.logEvent("project_details_clicked", bundle);

		        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(project.storeUrl));
		        try {
			        startActivity(intent);

		        } catch (ActivityNotFoundException exception) {

			        Snackbar snackbar = Snackbar
					        .make(coordinatorLayout, R.string.cant_help_it, Snackbar.LENGTH_LONG);

			        snackbar.show();

		        }
	        });
        }

        if (TextUtils.isEmpty(project.gitHub)) {
	        fabGithub.setVisibility(View.GONE);
        } else {
	        fabGithub.setVisibility(View.VISIBLE);
            fabGithub.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("project", project.name);
                analytics.logEvent("project_source_code_clicked", bundle);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(project.gitHub));
                try {
                    startActivity(intent);

                } catch (ActivityNotFoundException exception) {

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, R.string.cant_help_it, Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_project_details, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.project));
        stringBuilder.append(' ');
        stringBuilder.append(project.name);

        if (!TextUtils.isEmpty(project.storeUrl)) {
            stringBuilder.append(' ');
            stringBuilder.append(project.storeUrl);
        }

        if (!TextUtils.isEmpty(project.gitHub)) {
            stringBuilder.append(' ');
            stringBuilder.append(getString(R.string.code));
            stringBuilder.append(' ');
            stringBuilder.append(project.gitHub);
        }


        shareIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());

        shareActionProvider.setShareIntent(shareIntent);

        Bundle bundle = new Bundle();
        bundle.putString("project", project.name);
        analytics.logEvent("project_share_clicked", bundle);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
