package com.open.sample.presenter;

import android.view.ViewGroup;

import com.open.leanback.widget.Presenter;
import com.open.sample.model.Movie;
import com.open.sample.view.CardView;

/**
 * Created by hailongqiu on 2016/12/16.
 */
public class CardPresenter extends Presenter {

    private static final int CARD_WIDTH = 313;
    private static final int CARD_HEIGHT = 176;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        CardView cardView = new CardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                super.setSelected(selected);
            }
        };
        ViewGroup.LayoutParams lp = cardView.getLayoutParams();
//        lp.width = CARD_WIDTH;
//        lp.height = CARD_HEIGHT;
//        cardView.setLayoutParams(lp);
//        cardView.setLayoutParams(new ViewGroup.LayoutParams(CARD_WIDTH, CARD_HEIGHT));
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;
        String title = movie.getTitle();

        CardView cardView = (CardView) viewHolder.view;
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        CardView cardView = (CardView) viewHolder.view;
    }
}
