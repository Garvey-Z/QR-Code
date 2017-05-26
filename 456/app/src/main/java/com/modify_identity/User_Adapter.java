package com.modify_identity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.client.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/5/25.
 */

public class User_Adapter extends ArrayAdapter<User_infomation> {
    private int resourceid;
    public User_Adapter(Context context, int textViewResourceId, List<User_infomation> objects){
        super(context, textViewResourceId, objects);
        resourceid = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        User_infomation info = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceid, null);
        TextView account = (TextView)view.findViewById(R.id.user_account);
        TextView nickname = (TextView)view.findViewById(R.id.user_nickname);
        EditText identity = (EditText)view.findViewById(R.id.user_identity);
        account.setText(info.getAccount());
        nickname.setText(info.getNickname());
        identity.setText(info.getIdentity());
        return  view;
    }

}
