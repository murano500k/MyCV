package com.stc.cv.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.stc.cv.R;
import com.stc.cv.model.Cert;
import com.stc.cv.model.CertsResponce;
import com.stc.cv.model.Education;
import com.stc.cv.model.EducationResponce;
import com.stc.cv.model.SkillsGroup;
import com.stc.cv.model.SkillsResponce;
import com.stc.cv.model.Training;
import com.stc.cv.model.TrainingsResponce;
import com.stc.cv.model.Work;
import com.stc.cv.model.WorkResponce;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CommonFragment extends Fragment {

    public static final String TAG = "CommonFragment";
    private Subscription subscriptionSkills;
	private Subscription subscriptionWork;
	private SkillsAdapter skillsAdapter;
	private WorkAdapter workAdapter;
	private CertsAdapter certsAdapter;

	private RecyclerView recyclerViewSkills, recyclerViewWork, recyclerViewCerts, recyclerViewTrainings;
	TextView textUniversity;
	TextView textDegree;
	TextView textPeriod;
	TextView textFaculty;
	private Subscription subscriptionCerts;
	private TrainingsAdapter trainingsAdapter;
	private Subscription subscriptionTrainings;


	public static CommonFragment instance() {
        return new CommonFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skillsAdapter = new SkillsAdapter();
	    workAdapter = new WorkAdapter();
		certsAdapter = new CertsAdapter(FirebaseAnalytics.getInstance(getContext()));
		trainingsAdapter = new TrainingsAdapter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_common, container, false);
	    recyclerViewSkills=(RecyclerView) view.findViewById(R.id.rv_skills);
	    recyclerViewWork=(RecyclerView) view.findViewById(R.id.rv_work);
		recyclerViewCerts=(RecyclerView)view.findViewById(R.id.rv_certs);
		recyclerViewTrainings=(RecyclerView)view.findViewById(R.id.rv_training);
	    textDegree = (TextView)view.findViewById(R.id.text_degree);
	    textPeriod = (TextView)view.findViewById(R.id.text_period);
	    textUniversity = (TextView)view.findViewById(R.id.text_university);
	    textFaculty = (TextView)view.findViewById(R.id.text_faculty);



	    recyclerViewSkills.setLayoutManager(new LinearLayoutManager(getContext()));
	    recyclerViewWork.setLayoutManager(new LinearLayoutManager(getContext()));
	    recyclerViewCerts.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerViewTrainings.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerViewCerts.setAdapter(certsAdapter);
		recyclerViewWork.setAdapter(workAdapter);
	    recyclerViewSkills.setAdapter(skillsAdapter);
	    recyclerViewTrainings.setAdapter(trainingsAdapter);

	    recyclerViewSkills.setNestedScrollingEnabled(false);
	    recyclerViewCerts.setNestedScrollingEnabled(false);
	    recyclerViewWork.setNestedScrollingEnabled(false);
	    recyclerViewTrainings.setNestedScrollingEnabled(false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        subscriptionTrainings = RxFirebaseDatabase
                .observeValueEvent(FirebaseDatabase.getInstance().getReference(), TrainingsResponce.class)
                .flatMap(new Func1<TrainingsResponce, Observable<Training>>() {
	                @Override
	                public Observable<Training> call(TrainingsResponce trainingsResponce) {
		                return Observable.from(trainingsResponce.trainings);
	                }
                })
		        .subscribeOn(Schedulers.io())
		        .observeOn(AndroidSchedulers.mainThread())
		        .subscribe(new Observer<Training>() {

			        @Override
			        public void onCompleted() {
				        trainingsAdapter.notifyDataSetChanged();

			        }

			        @Override
			        public void onError(Throwable e) {
				        trainingsAdapter.notifyDataSetChanged();
				        Log.e(TAG, "onError: ", e);
				        FirebaseCrash.report(e);
			        }

			        @Override
			        public void onNext(Training training) {
				        trainingsAdapter.addItem(training);
			        }
		        });

 subscriptionCerts = RxFirebaseDatabase
                .observeValueEvent(FirebaseDatabase.getInstance().getReference(), CertsResponce.class)
                .flatMap(new Func1<CertsResponce, Observable<Cert>>() {
	                @Override
	                public Observable<Cert> call(CertsResponce certsResponce) {
		                return Observable.from(certsResponce.certifications);
	                }
                })
		        .subscribeOn(Schedulers.io())
		        .observeOn(AndroidSchedulers.mainThread())
		        .subscribe(new Observer<Cert>() {

			        @Override
			        public void onCompleted() {
				        certsAdapter.notifyDataSetChanged();

			        }

			        @Override
			        public void onError(Throwable e) {
				        certsAdapter.notifyDataSetChanged();
				        Log.e(TAG, "onError: ", e);
				        FirebaseCrash.report(e);
			        }

			        @Override
			        public void onNext(Cert cert) {
				        certsAdapter.addItem(cert);
			        }
		        });


        subscriptionWork = RxFirebaseDatabase
                .observeValueEvent(FirebaseDatabase.getInstance().getReference(), WorkResponce.class)
                .flatMap(new Func1<WorkResponce, Observable<Work>>() {
	                @Override
	                public Observable<Work> call(WorkResponce workResponce) {
		                return Observable.from(workResponce.works);
	                }
                })
		        .subscribeOn(Schedulers.io())
		        .observeOn(AndroidSchedulers.mainThread())
		        .subscribe(new Observer<Work>() {

			        @Override
			        public void onCompleted() {
				        workAdapter.notifyDataSetChanged();

			        }

			        @Override
			        public void onError(Throwable e) {
				        workAdapter.notifyDataSetChanged();
				        Log.e(TAG, "onError: ", e);
				        FirebaseCrash.report(e);
			        }

			        @Override
			        public void onNext(Work work) {
				        workAdapter.addItem(work);
			        }
		        });


	    subscriptionSkills = RxFirebaseDatabase
			    .observeValueEvent(FirebaseDatabase.getInstance().getReference(), SkillsResponce.class)
			    .flatMap(new Func1<SkillsResponce, Observable<SkillsGroup>>() {
				    @Override
				    public Observable<SkillsGroup> call(SkillsResponce skillsResponce) {
					    return Observable.from(skillsResponce.skillGroups);
				    }
			    })
			    .subscribeOn(Schedulers.io())
			    .observeOn(AndroidSchedulers.mainThread())
			    .subscribe(new Observer<SkillsGroup>() {

				    @Override
				    public void onCompleted() {
				    }

				    @Override
				    public void onError(Throwable e) {

					    skillsAdapter.notifyDataSetChanged();
					    Log.e(TAG, "onError: ", e);
					    FirebaseCrash.report(e);
				    }

				    @Override
				    public void onNext(SkillsGroup skillsGroup) {
					    skillsAdapter.addItem(skillsGroup);
				    }
			    });

	    RxFirebaseDatabase
			    .observeValueEvent(FirebaseDatabase.getInstance().getReference(), EducationResponce.class)
			    .flatMap(new Func1<EducationResponce, Observable<Education>>() {
				    @Override
				    public Observable<Education> call(EducationResponce educationResponce) {
					    return Observable.just(educationResponce.education);
				    }
			    })
			    .subscribeOn(Schedulers.io())
			    .observeOn(AndroidSchedulers.mainThread())
			    .subscribe(new Observer<Education>() {

				    @Override
				    public void onCompleted() {
				    }

				    @Override
				    public void onError(Throwable e) {

					    Log.e(TAG, "onError: ", e);
					    FirebaseCrash.report(e);
				    }

				    @Override
				    public void onNext(Education education) {
					    textFaculty.setText(education.faculty);
					    textUniversity.setText(education.university);
					    textPeriod.setText(education.period);
					    textDegree.setText(education.degree);

				    }
			    });

    }

    @Override
    public void onStop() {
        super.onStop();
	    if (subscriptionSkills != null && !subscriptionSkills.isUnsubscribed()) {
		    subscriptionSkills.unsubscribe();
	    }
	    if (subscriptionWork != null && !subscriptionWork.isUnsubscribed()) {
		    subscriptionWork.unsubscribe();
	    }

    }
}
