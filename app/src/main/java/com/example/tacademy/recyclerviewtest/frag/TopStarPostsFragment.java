package com.example.tacademy.recyclerviewtest.frag;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
*   17.02.07
 */
public class TopStarPostsFragment extends ParentFragment {

    public TopStarPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query query =
                databaseReference.child("user-posts").child(getUid()).orderByChild("star_count");
        return query;
    }

}
