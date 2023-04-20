package com.example.versioapp1;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.versioapp1.Database.Running;
import com.example.versioapp1.Database.RunningViewModel;
import com.example.versioapp1.Database.User;
import com.example.versioapp1.Database.UserWithRunning;
import com.example.versioapp1.Database.Walking;
import com.example.versioapp1.Database.WalkingViewModel;
import com.example.versioapp1.RecycleView.ActivityAdapter;
import com.example.versioapp1.RecycleView.RunningAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WalkingViewModel walkingViewModel;
    private RunningViewModel runningViewModel;
    private Button btnWalking, btnRunning;
    private int defaultColour;
    private SearchView searchView1;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        //searchView
        searchView1 = view.findViewById(R.id.searchView1);
        searchView1.clearFocus();

        //default text colour
        defaultColour = ContextCompat.getColor(getContext(), R.color.black);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);//walking
        RecyclerView recyclerViewRun = view.findViewById(R.id.recycler_view2);//running
        //running
        RunningAdapter runningAdapter = new RunningAdapter();
        recyclerViewRun.setAdapter(runningAdapter);
        recyclerViewRun.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRun.setHasFixedSize(true);
        //walking
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ActivityAdapter adapter = new ActivityAdapter();
        recyclerView.setAdapter(adapter);
        //walking search bar
        walkingViewModel = new ViewModelProvider(getActivity()).get(WalkingViewModel.class);
        walkingViewModel.getAllWalking().observe(getViewLifecycleOwner(), new Observer<List<Walking>>() {
            @Override
            public void onChanged(List<Walking> walkings) {
                adapter.setWalkingActivities(walkings);
                //search bar
                searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        List<Walking> filteredList = new ArrayList<>();
                        for (Walking item : walkings) {
                            if (item.getCurrentDate().contains(newText)) {
                                filteredList.add(item);
                            }
                        }
                        if (filteredList.isEmpty()) {
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter.setFilteredList(filteredList);
                        }

                        return true;
                    }
                });

            }
        });

        //button walking
        btnWalking = view.findViewById(R.id.btnWalking);
        btnWalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewRun.setVisibility(View.GONE);
                btnWalking.setTextColor(Color.BLUE);
                btnRunning.setTextColor(defaultColour);
                walkingViewModel = new ViewModelProvider(getActivity()).get(WalkingViewModel.class);
                walkingViewModel.getAllWalking().observe(getViewLifecycleOwner(), new Observer<List<Walking>>() {
                    @Override
                    public void onChanged(List<Walking> walkings) {
                        adapter.setWalkingActivities(walkings);
                        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {

                                List<Walking> filteredList = new ArrayList<>();
                                for (Walking item : walkings) {
                                    if (item.getCurrentDate().contains(newText)) {
                                        filteredList.add(item);
                                    }
                                }
                                if (filteredList.isEmpty()) {
                                    Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    adapter.setFilteredList(filteredList);
                                }

                                return true;
                            }
                        });

                    }
                });
            }
        });
        //button running
        btnRunning = view.findViewById(R.id.btnRunning);
        btnRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                recyclerViewRun.setVisibility(View.VISIBLE);
                btnRunning.setTextColor(Color.BLUE);
                btnWalking.setTextColor(defaultColour);
                //second adapter
                runningViewModel = new ViewModelProvider(getActivity()).get(RunningViewModel.class);
                runningViewModel.getAllRunning().observe(getViewLifecycleOwner(), new Observer<List<Running>>() {
                    @Override
                    public void onChanged(List<Running> runningList) {
                        runningAdapter.setRunningActivities(runningList);
                        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {

                                List<Running> filteredList = new ArrayList<>();
                                for (Running item : runningList) {
                                    if (item.getCurrentDate().contains(newText)) {
                                        filteredList.add(item);
                                    }
                                }
                                if (filteredList.isEmpty()) {
                                    Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    runningAdapter.setFilteredList(filteredList);
                                }

                                return true;
                            }
                        });
                    }
                });

            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }


}