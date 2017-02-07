package com.example.tacademy.recyclerviewtest.frag;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 *  17.02.07
 *  부모 프레그먼트의 oncreateview 앞부분만 짤라버린다.
 *  생성자만 주고 날려 버린다.
 *  모든 작성자
 */
public class TotalPostsFragment extends ParentFragment {

    public TotalPostsFragment() {

    }

    // 자식이 필요에 의해서 추가 한 것이다.
    @Override
    public Query getQuery(DatabaseReference databaseReference){
        Query query =
                databaseReference.child("posts").limitToLast(10);
        return query;
    }

}
