package com.example.tacademy.recyclerviewtest.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.recyclerviewtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStarPostsFragment extends Fragment {


    public TopStarPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_star_posts, container, false);
    }

}