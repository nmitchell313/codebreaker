package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.adapter.GameSummaryAdapter;
import edu.cnm.deepdive.codebreaker.databinding.FragmentScoresBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.ScoresViewModel;

public class ScoresFragment extends Fragment {

    private ScoresViewModel viewModel;
    private FragmentScoresBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScoresBinding.inflate(inflater, container, false);
        // TODO add onClickListener to column headers. Listeners should invoke the set method
        // in ScoresViewModel to force a refresh of the query.
        // TODO add onItemSelectedListener to spinner. Invokes set method ScoresViewModel to
        // force refresh of the query.
        // TODO populate spinners, using min and max code length & pool size integer resources
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ScoresViewModel.class);
        viewModel
            .getScoreboard()
            .observe(getViewLifecycleOwner(), (games) -> {
                GameSummaryAdapter adapter = new GameSummaryAdapter(getContext(), games);
                binding.games.setAdapter(adapter);
            });
        viewModel
            .getCodeLength()
            .observe(getViewLifecycleOwner(), (codeLength) -> {/* TODO set value of spinner. */});
        viewModel
            .getPoolSize()
            .observe(getViewLifecycleOwner(), (poolSize) -> {/* TODO set value of spinner. */});
        viewModel
            .getSortedByTime()
            .observe(getViewLifecycleOwner(), (sortedByTime) -> {/* TODO update styling and listeners on column headers. */});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}