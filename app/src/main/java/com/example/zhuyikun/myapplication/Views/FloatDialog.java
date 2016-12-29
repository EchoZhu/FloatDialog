package com.example.zhuyikun.myapplication.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zhuyikun.myapplication.MainActivity;
import com.example.zhuyikun.myapplication.R;

import java.lang.ref.WeakReference;

/**
 * Created by Echozyk on 16/12/29.
 */

public class FloatDialog extends DialogFragment implements View.OnClickListener {
    private WeakReference<MainActivity> mRef;

    public void setOutContext(MainActivity activity) {
        mRef = new WeakReference<MainActivity>(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView;
        mView = inflater.inflate(R.layout.float_dialog, container);
        LinearLayout googleView = (LinearLayout) mView.findViewById(R.id.a_view);
        LinearLayout codapay = (LinearLayout) mView.findViewById(R.id.b_view);
        googleView.setOnClickListener(this);
        codapay.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a_view: {
                if (mRef != null) {
                    MainActivity act = mRef.get();
                    if (act != null) {
                        Toast.makeText(act, "a_view", Toast.LENGTH_LONG).show();
                    }
                }
                dismiss();
                break;
            }
            case R.id.b_view: {
                if (mRef != null) {
                    MainActivity act = mRef.get();
                    if (act != null) {
                        Toast.makeText(act, "b_view", Toast.LENGTH_LONG).show();
                    }
                }
                dismiss();
                break;
            }
        }

    }
}
