package com.example.tacademy.recyclerviewtest.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.recyclerviewtest.R;
import com.example.tacademy.recyclerviewtest.model.Post;
import com.example.tacademy.recyclerviewtest.model.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class TotalPostsFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    public TotalPostsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_posts,container,false);

        // 화면 세팅 구성
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        // 레이아웃 세팅
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 쿼리가 수행 되어야 한다. ( limitTofirest( 10개 데이터 가져옴 ) 앞에서 부터 last 뒤부터
        Query query =
                FirebaseDatabase.getInstance().getReference().child("posts").limitToLast(10);
        // 아답터 생성
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(
                // 데이터 4개를 맞춰야함
                Post.class,
                R.layout.cell_post_layout,
                PostViewHolder.class,
                // 쿼리 결과 가 들어간다.
                query
        ){
            // 레이아웃을 담기는 그릇, 데이터가 담기를 그릇, 필요한 인덱스
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                // 1. position 정보를 가지고 -> 데이터 획득( 참조 획득 )
                DatabaseReference databaseReference = getRef(position);
                // 2. viewHolder로 -> 이벤트 등록
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 상세보기로 이동
                    }
                });
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (model.getStars().containsKey(uid)) {
                    // 별처리 ( 색을 채운다 ) ( 좋아요 같은거! )
                } else {
                    // 별처리 ( 색을 비운다 )
                }
                // 3. viewHolder로 -> 데이터 세팅(bindToPost)
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        return view;
    }
}
