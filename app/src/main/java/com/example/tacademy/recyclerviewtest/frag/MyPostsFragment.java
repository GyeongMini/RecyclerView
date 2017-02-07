package com.example.tacademy.recyclerviewtest.frag;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 *  17.02.07
 *  내가 작성한 글만 보인다
 */
public class MyPostsFragment extends ParentFragment {

    public MyPostsFragment() {
        // Required empty public constructor
    }
    // 내가 작성한 글만
    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query query =
                databaseReference.child("user-posts").child(getUid()); // 아이디를 구함
        return query;
    }

}