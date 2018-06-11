package com.open.sample.presenter;

import com.open.leanback.widget.ItemAlignmentFacet;
import com.open.leanback.widget.ListRow;
import com.open.leanback.widget.ListRowPresenter;
import com.open.leanback.widget.Presenter;
import com.open.leanback.widget.PresenterSelector;

/**
 * 多个选择器.
 */
public class NewPresenterSelector extends PresenterSelector {
    private ListRowPresenter mListRowPresenter = new ListRowPresenter();
    private NewListRowPresenter mShadowDisabledRowPresenter = new NewListRowPresenter();
    private InvisibleRowPresenter mTestRowPresenter = new InvisibleRowPresenter();

    public NewPresenterSelector() {
        mListRowPresenter.setNumRows(1);
        mListRowPresenter.setHeaderPresenter(new HeaderPresenter());
        mShadowDisabledRowPresenter.setNumRows(1);
        mShadowDisabledRowPresenter.setHeaderPresenter(new HeaderPresenter());
        mTestRowPresenter.setHeaderPresenter(new HeaderPresenter()); // 可以换不同的头样式，自己写.
    }

    @Override
    public Presenter getPresenter(Object item) {
        ListRow listRow = (ListRow) item;
        if ((item instanceof ButtonListRow)) return mTestRowPresenter;
        return mShadowDisabledRowPresenter;
    }

    @Override
    public Presenter[] getPresenters() {
        return new Presenter[]{
                mShadowDisabledRowPresenter,
                mListRowPresenter,
                mTestRowPresenter
        };
    }
}
