package com.mkhaleghy.cinema;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mkhaleghy.cinema.adapter.Binder;
import com.mkhaleghy.cinema.adapter.RecyclerAdapter;
import com.mkhaleghy.cinema.app.GlideApp;
import com.mkhaleghy.cinema.daylist.Movie;
import com.mkhaleghy.cinema.detail.DetailActivity;

/**
 * Created by mk on 3/2/2018.
 */

public class MovieView extends ConstraintLayout implements Binder<Movie>, PopupMenu.OnMenuItemClickListener {
    public static final String TAG = "MovieView";
    RecyclerAdapter.OnAdapterInteractionListener mListener;
    private ImageView iv_icon;
    private CardView cv;
    private ImageView iv_overFlow;
    private ImageView iv_ticket;
    private TextView tv_title;
    private TextView tv_subtitle;
    private TextView tv_genre;
    private TextView tv_time;
    private AppCompatRatingBar rb_rate;
    private Movie movie;

    public MovieView(Context context) {
        super(context);
        init(null);
    }

    public MovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MovieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attributeSet) {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iv_icon = findViewById(R.id.iv_icon);
        cv = findViewById(R.id.cv);
        iv_overFlow = findViewById(R.id.iv_overflow);
        iv_ticket = findViewById(R.id.iv_ticket);
        tv_title = findViewById(R.id.tv_title);
        tv_subtitle = findViewById(R.id.tv_subtitle);
        tv_genre = findViewById(R.id.tv_genre);
        tv_time = findViewById(R.id.tv_time);
        rb_rate = findViewById(R.id.rb_rate);

        PopupMenu popupMenu = new PopupMenu(getContext(), iv_overFlow);
        popupMenu.inflate(R.menu.item_list_menu);
        popupMenu.setOnMenuItemClickListener(this);

        iv_overFlow.setOnClickListener(v -> {
            popupMenu.show();
        });

    }

    @Override
    public void bind(Movie item, RecyclerAdapter.OnAdapterInteractionListener mListener) {
        this.movie = item;

        GlideApp.with(((Activity) getContext())).load(R.drawable.walle).into(iv_icon);

        tv_title.setText(item.title());
        tv_subtitle.setText(item.subtitle());
        tv_genre.setText(item.genre());
        tv_time.setText(item.time());
        rb_rate.setRating(item.rate());

        setOnClickListener(v -> {
            int[] ticketLoc = new int[2];
            iv_ticket.getLocationInWindow(ticketLoc);
            mListener.detailSelected();
            DetailActivity.start(((Activity) getContext()), movie.detail(), ticketLoc[1], iv_icon, iv_ticket, cv, tv_title, tv_subtitle, rb_rate);
        });


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (movie != null) {
            if (item.getItemId() == R.id.book) {
                //TODO book the movie
                Toast.makeText(getContext(), "book the movie", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
}
