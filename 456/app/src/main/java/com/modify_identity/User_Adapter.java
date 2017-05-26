package com.modify_identity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private User_infomation info;
    private EditText identity;
    List<User_infomation> list;
    private View view;
    private TextView account;
    private Button button;
    private  String sole;
    public User_Adapter(Context context, int textViewResourceId, List<User_infomation> objects){
        super(context, textViewResourceId, objects);
        resourceid = textViewResourceId;
        list = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceid, null);
        }else
        {
            view = convertView;
        }
        info = getItem(position);

       account = (TextView) view.findViewById(R.id.user_account);
        button = (Button) view.findViewById(R.id.user_btn_change);
        identity = (EditText)view.findViewById(R.id.user_identity);
        account.setText(info.getAccount());
        identity.setText(info.getIdentity());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //list.remove(position);
                list.get(position).setIdentity(sole);
                notifyDataSetChanged();
                Log.v("12",getItem(position).getIdentity() + "\n" + sole);
            }
        });
        identity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sole = editable.toString();


            }
        });
        //identity.setText(sole);
        notifyDataSetChanged();
        return  view;
    }

}
