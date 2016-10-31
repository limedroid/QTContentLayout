package cn.droidlover.qtcontentlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * Created by wanglei on 16/10/22.
 */

public class QTContentLayout extends FrameLayout {
    View loadingView;
    View errorView;
    View emptyView;
    View contentView;

    int loadingLayoutId;
    int errorLayoutId;
    int emptyLayoutId;
    int contentLayoutId;

    public static final int STATE_LOADING = 0x1;
    public static final int STATE_ERROR = 0x2;
    public static final int STATE_EMPTY = 0x3;
    public static final int STATE_CONTENT = 0x4;
    int displayState = STATE_LOADING;

    public QTContentLayout(Context context) {
        super(context, null);
    }

    public QTContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public QTContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attr) {
        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.QTContentLayout);
        loadingLayoutId = typedArray.getResourceId(R.styleable.QTContentLayout_cl_loadingLayoutId, -1);
        errorLayoutId = typedArray.getResourceId(R.styleable.QTContentLayout_cl_errorLayoutId, -1);
        emptyLayoutId = typedArray.getResourceId(R.styleable.QTContentLayout_cl_emptyLayoutId, -1);
        contentLayoutId = typedArray.getResourceId(R.styleable.QTContentLayout_cl_contentLayoutId, -1);
        typedArray.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount > 4) {
            throw new IllegalStateException("QTContentLayout can only host 4 elements");
        } else {
            loadingView = findViewById(loadingLayoutId);
            errorView = findViewById(errorLayoutId);
            emptyView = findViewById(emptyLayoutId);
            contentView = findViewById(contentLayoutId);

            if (contentView == null) {
                if (childCount == 1) {
                    contentView = getChildAt(0);
                }
            }
            if (contentView == null) {
                throw new IllegalStateException("ContentView can not be null");
            }

            bindView(loadingView, STATE_LOADING);
            bindView(errorView, STATE_ERROR);
            bindView(emptyView, STATE_EMPTY);
            bindView(contentView, STATE_CONTENT);

            if (loadingView != null) {
                setDisplayState(STATE_LOADING);
            } else {
                setDisplayState(STATE_CONTENT);
            }
        }
    }

    public void setDisplayState(int displayState) {
        this.displayState = displayState;

        if (loadingView != null) {
            if (displayState == STATE_LOADING) {
                loadingView.setVisibility(VISIBLE);
                loadingView.bringToFront();
            } else {
                loadingView.setVisibility(GONE);
            }
        }

        if (errorView != null) {
            if (displayState == STATE_ERROR) {
                errorView.setVisibility(VISIBLE);
                errorView.bringToFront();
            } else {
                errorView.setVisibility(GONE);
            }
        }

        if (emptyView != null) {
            if (displayState == STATE_EMPTY) {
                emptyView.setVisibility(VISIBLE);
                emptyView.bringToFront();
            } else {
                emptyView.setVisibility(GONE);
            }
        }

        if (contentView != null) {
            if (displayState == STATE_CONTENT) {
                contentView.setVisibility(VISIBLE);
                contentView.bringToFront();
            } else {
                contentView.setVisibility(GONE);
            }
        }
    }

    public void bindView(View child, int state) {
        if (child != null) {
            if (state == STATE_LOADING) {
                if (loadingView != null) {
                    removeView(loadingView);
                    loadingView = null;
                }
                loadingView = child;
            }
            if (state == STATE_EMPTY) {
                if (emptyView != null) {
                    removeView(emptyView);
                    emptyView = null;
                }
                emptyView = child;
            }
            if (state == STATE_ERROR) {
                if (errorView != null) {
                    removeView(errorView);
                    errorView = null;
                }
                errorView = child;
            }
            if (state == STATE_CONTENT) {
                if (contentView != null) {
                    removeView(contentView);
                    contentView = null;
                }
                contentView = child;
            }

            ViewParent parent = child.getParent();
            if (parent == null || parent != this) {
                super.addView(child);
            }
        }
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState savedState = new SavedState(parcelable);
        savedState.state = this.displayState;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.displayState = savedState.state;
        setDisplayState(this.displayState);
    }


    static class SavedState extends BaseSavedState {
        int state;

        SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            try {
                state = source.readInt();
            } catch (IllegalArgumentException e) {
                state = STATE_LOADING;
            }
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

    }

    public void showContent() {
        setDisplayState(STATE_CONTENT);
    }

    public void showEmpty() {
        setDisplayState(STATE_EMPTY);
    }

    public void showError() {
        setDisplayState(STATE_ERROR);
    }

    public void showLoading() {
        setDisplayState(STATE_LOADING);
    }

    //-------

    public QTContentLayout loadingView(View loadingView) {
        bindView(loadingView, STATE_LOADING);
        return this;
    }

    public QTContentLayout errorView(View errorView) {
        bindView(errorView, STATE_ERROR);
        return this;
    }

    public QTContentLayout emptyView(View emptyView) {
        bindView(emptyView, STATE_EMPTY);
        return this;
    }

    public int getDisplayState() {
        return displayState;
    }

    public View getEmptyView() {
        return emptyView;
    }

    public View getLoadingView() {
        return loadingView;
    }

    public View getErrorView() {
        return errorView;
    }
}

