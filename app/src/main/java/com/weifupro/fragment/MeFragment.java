package com.weifupro.fragment;

import android.view.View;
import android.widget.Toast;

import com.weifupro.R;
import com.weifupro.utils.SetItemView;

/**
 * Created by HuangJ on 2017/7/16.17:44
 */

public class MeFragment extends BaseFragment {

    private SetItemView mSetItemView;

    @Override
    public int getContentId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init(View view) {
        super.init(view);
        mSetItemView = (SetItemView) view.findViewById(R.id.fragment_me_new);
        mSetItemView.setmOnSetItemOnClick(new SetItemView.onSetItemOnClick() {
            @Override
            public void onClick() {
                Toast.makeText(getActivity(), "点击版本更新", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
