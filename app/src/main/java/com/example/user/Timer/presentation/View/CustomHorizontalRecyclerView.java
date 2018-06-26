package com.example.user.Timer.presentation.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.user.Timer.R;
import com.example.user.Timer.presentation.Adapters.UserAdapter;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import java.util.ArrayList;
import java.util.List;

public class CustomHorizontalRecyclerView extends FrameLayout {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private SmoothScrollLinearLayoutManager layoutManager;
    private int screenWidth = 0;
    List<ModelInPresentationLayer> userList = new ArrayList<>();
    //HorizontalListener listener;
    int cellWidth, cellHeight;
    boolean positionChanged = false;
    int indent;

    public CustomHorizontalRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomHorizontalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomHorizontalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.horizontal_view, this);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new SmoothScrollLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        getScreenWidth();
        adapter = new UserAdapter(getContext());
        adapter.setCellWidthAndHeight(cellWidth, cellHeight);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            long lastTimeUpdate = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                long l = System.currentTimeMillis();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && l - lastTimeUpdate > 500) {
                    scrollToNewPosition(recyclerView);
                    lastTimeUpdate = l;
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) + 1 >= totalItemCount) {
                      //  listener.onFetchData();
                    }
                }
            }
        });
    }

    private void getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        cellWidth = width / 5;
        indent = (width - cellWidth) / 2;
        cellHeight = size.y / 4;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        layoutManager.setWidthView(w);
        //  changePadding();
//        if (w != oldw && selectedString != null && elementTags != null) {
//            scrollToStartPosition(false);
//        }
    }

    private void changePadding() {
        if (adapter != null && userList.get(0) != null) {
            recyclerView.setPadding(indent, 0, indent, 0);
        }
    }

    private void scrollToNewPosition(RecyclerView recyclerView) {
        int position = recyclerView.computeHorizontalScrollOffset();
        if (positionChanged) {
            changeItemPosition(position);
        } else {
            int newPosition = 0;
            if (position % cellWidth > cellWidth / 2) {
                newPosition = (position / cellWidth + 1) * cellWidth;
            } else {
                newPosition = (position / cellWidth) * cellWidth;
            }
            int itemPosition = newPosition / cellWidth;
            recyclerView.smoothScrollToPosition(itemPosition);
            positionChanged = true;
            if (position % cellWidth < 0.01 * cellWidth) {
                changeItemPosition(position);
            } else if (position % cellWidth > 0.99 * cellWidth) {
                changeItemPosition(position);
            }
        }
    }

    private void changeItemPosition(int position) {
        int itemPosition = Math.round(position * 1.0f / cellWidth * 1.0f);
        layoutManager.smoothScrollToPosition(recyclerView, null, itemPosition);
        positionChanged = false;
    }


    public void setData(List<ModelInPresentationLayer> userList) {
        this.userList.addAll(userList);
        adapter.setData(userList);
        changePadding();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawLine(screenWidth / 2, 10, screenWidth / 2, cellHeight - CustomViewForGraphic.INDENT_FOR_TEXT, paint);
        paint.setARGB(255, 0, 0, 0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{10f, 10f}, 0));
        paint.setStrokeWidth(2);
        Path pathDashLine = new Path();
        pathDashLine.reset();
        pathDashLine.moveTo(16, cellHeight / 4);
        pathDashLine.lineTo(screenWidth - 16, cellHeight / 4);
        canvas.drawPath(pathDashLine, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        canvas.restore();
    }
}
