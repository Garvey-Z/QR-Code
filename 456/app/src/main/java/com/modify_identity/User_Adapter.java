package com.modify_identity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.client.android.R;
import com.mytool.post;

import java.util.List;

/**
 * Created by apple on 2017/5/25.
 */

public class User_Adapter extends ArrayAdapter<User_infomation> {
    private int resourceid;
    private User_infomation info;
    List<User_infomation> list;
    private  String sole;
    private post p = new post();
    private int mTouchItemPosition = -1;
    public User_Adapter(Context context, int textViewResourceId, List<User_infomation> objects){
        super(context, textViewResourceId, objects);
        resourceid = textViewResourceId;
        list = objects;
    }
    @Override
    public View getView(final int position,View convertView, ViewGroup parent){
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceid, null);
            viewHolder.identity = (EditText)convertView.findViewById(R.id.user_identity);
            viewHolder.account = (TextView) convertView.findViewById(R.id.user_account);
            viewHolder.button = (Button) convertView.findViewById(R.id.user_btn_change);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //list.remove(position);
                    if (sole!=null){
                        list.get(position).setIdentity(sole);
                    }
                    notifyDataSetChanged();
                    User_infomation uif = list.get(position);
                    Log.v("12",uif.getAccount() + "\n" + uif.getIdentity());
                    p.post_infomation(uif.getAccount(),uif.getIdentity());
                }
            });
            viewHolder.identity.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (view == null){
                        return false;
                    }
//                    Log.v("view",view.getTag().toString());
                    mTouchItemPosition = (Integer) view.getTag();
//                    Log.v("nowPo",String.valueOf(mTouchItemPosition));
                    return false;
                }
            });
            viewHolder.identity.addTextChangedListener(new TextWatcher() {
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
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        info = getItem(position);

        viewHolder.account.setText(info.getAccount());
        viewHolder.identity.setText(info.getIdentity());
        viewHolder.identity.setTag(position);

        notifyDataSetChanged();
        if (mTouchItemPosition == position) {
            viewHolder.identity.requestFocus();
            viewHolder.identity.setSelection(viewHolder.identity.getText().length());
        } else {
            viewHolder.identity.clearFocus();
        }
        return  convertView;
    }

    private class ViewHolder {
        private EditText identity;
        private TextView account;
        private Button button;
    }
}
