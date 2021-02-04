package com.example.fairmoneyapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fairmoneyapp.R;
import com.example.fairmoneyapp.model.UserModel;
import com.example.fairmoneyapp.ui.UserProfile;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(UserModel userModel);
    }

    private Context context;
    List<UserModel> userModelList;
    private OnItemClickListener listener;


    public UserAdapter(Context context, List<UserModel> userModelList, OnItemClickListener listener) {
        this.context = context;
        this.userModelList = userModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userlist_row, parent, false);
        return new UserAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        UserModel model = userModelList.get(position);
        holder.tvId.setText(model.getId());
        holder.tvName.setText(model.getFirstName());
        Glide.with(holder.picture.getContext())
                .load(model.getPictureUrl())
                .asBitmap()
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvId;
        public ImageView picture;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvId = view.findViewById(R.id.tvId);
            tvName = view.findViewById(R.id.tvFname);
            picture = view.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getLayoutPosition();

                    Intent intent = new Intent(v.getContext(), UserProfile.class);
                    intent.putExtra("id", userModelList.get(itemPosition).getId());
                    intent.putExtra("fName", userModelList.get(itemPosition).getFirstName());
                    intent.putExtra("lName", userModelList.get(itemPosition).getLastName());
                    intent.putExtra("email", userModelList.get(itemPosition).getEmail());
                    intent.putExtra("title", userModelList.get(itemPosition).getTitle());
                    intent.putExtra("picture", userModelList.get(itemPosition).getPictureUrl());

                    v.getContext().startActivity(intent);
                }
            });
        }
    }


}
