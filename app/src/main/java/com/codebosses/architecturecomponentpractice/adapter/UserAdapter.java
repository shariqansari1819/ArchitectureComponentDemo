package com.codebosses.architecturecomponentpractice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codebosses.architecturecomponentpractice.R;
import com.codebosses.architecturecomponentpractice.database.User;
import com.codebosses.architecturecomponentpractice.databinding.RowUsersBinding;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemLongPressed onItemLongPressed;
    private List<User> userList = new ArrayList<>();

    public UserAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void setOnItemLongPressed(OnItemLongPressed onItemLongPressed) {
        this.onItemLongPressed = onItemLongPressed;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowUsersBinding rowUsersBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_users, parent, false);
        return new UserHolder(rowUsersBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.rowUsersBinding.setUser(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        RowUsersBinding rowUsersBinding;

        public UserHolder(@NonNull RowUsersBinding rowUsersBinding) {
            super(rowUsersBinding.getRoot());
            this.rowUsersBinding = rowUsersBinding;

            rowUsersBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongPressed != null) {
                        onItemLongPressed.onLogPressed(v, userList.get(getAdapterPosition()), getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemLongPressed {
        public void onLogPressed(View view, User user, int position);
    }

}
