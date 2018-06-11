/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.open.sample.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.leanback.widget.RowPresenter;
import com.open.sample.R;

/**
 */
public class InvisibleRowPresenter extends RowPresenter {

    public InvisibleRowPresenter() {
//        setHeaderPresenter(null); // 屏蔽掉头.
    }

    /**
     * 不得不说，android 的 Leanback 确实很灵活.
     * 这里可以换成其它布局的，不一定是 LinerLayout.
     */
    @Override
    protected ViewHolder createRowViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_patch_wall_view, null, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        ViewGroup vg = (ViewGroup) holder.view;
        for (int i = 0; i < vg.getChildCount(); i++) {
            View childView = vg.getChildAt(i);
            childView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    } else {
                        v.animate().scaleY(1.0f).scaleX(1.0f).start();
                    }
                }
            });
        }
    }


    @Override
    protected void onUnbindRowViewHolder(ViewHolder holder) {
        super.onUnbindRowViewHolder(holder);
    }

}
