package com.example.hippy.chatapp2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hippy.chatapp2.R;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    public UserListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int index) {
        return null;
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_user, null);

            viewHolder = new ViewHolder();
            viewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.user_name_textview);
            viewHolder.tagsTextView = (TextView) convertView.findViewById(R.id.tags_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private void applyUserName(ViewHolder viewHolder, String userName) {
        viewHolder.userNameTextView.setText(userName);
    }

    private void applyTags(ViewHolder viewHolder, List<String> tags) {
        if (tags != null) {
            viewHolder.tagsTextView.setText(tags.toString());
        }
    }

    public static class ViewHolder {

        TextView userNameTextView;
        TextView tagsTextView;
    }
}
