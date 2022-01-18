package com.example.a3lesson2.ui.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a3lesson2.R;
import com.example.a3lesson2.data.models.Post;
import com.example.a3lesson2.databinding.FragmentPostsBinding;
import com.example.a3lesson2.utils.App;
import com.example.a3lesson2.utils.ItemOnClick;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;
    private PostsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostsAdapter();
        adapter.setItemOnClick(new ItemOnClick() {
            @Override
            public void onClick(int position) {
                if (adapter.getPost(position).getUserId() == 6)
                    openFragment(adapter.getPost(position));
                else
                    Toast.makeText(requireActivity(), "вы не можете редактировать записи других пользователей", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                Post post6 = adapter.getPost(position);
                if (post6.getUserId() == 6) {
                    App.api.deletePost(post6.getId()).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            adapter.deleteItem(position);
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                } else
                    Toast.makeText(requireActivity(), "вы не можете удалять записи других пользователей", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);

        App.api.getPosts(6).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPost(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

        initListener();
    }

    private void initListener() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                controller.navigate(R.id.formFragment);
            }
        });
    }

    private void openFragment(Post post) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ttt", post);
        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        controller.navigate(R.id.formFragment, bundle);
    }
}