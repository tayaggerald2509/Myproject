package ph.com.swak.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ph.com.swak.R;

/**
 * Created by SWAK-THREE on 4/6/2015.
 */
public class DialogBuilder extends Dialog implements DialogInterface {

    private final String defTextColor = "#FFFFFFFF";
    private final String defDividerColor = "#11000000";
    private final String defMsgColor = "#FFFFFFFF";
    private final String defDialogColor = "#FFE74C3C";
    private Effectstype type = null;
    private LinearLayout mLinearLayoutView;
    private RelativeLayout mRlinearLayoutView;
    private LinearLayout mLinearLayoutMsgView;
    private LinearLayout mLinearLayoutTopView;
    private FrameLayout mFrameLayoutCustomView;
    private View mDialogView;
    private View mDivider;
    private TextView mTitle;
    private TextView mMessage;
    private ImageView mIcon;
    private Button mButton1;
    private Button mButton2;
    private int mDuration = -1;
    private Context context;

    private volatile static DialogBuilder instance;

    public DialogBuilder(Context context) {
        super(context);
        init(context);
    }

    public DialogBuilder(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(
                (android.view.WindowManager.LayoutParams) params);
    }

    public static DialogBuilder getInstance(Context context) {
        if (instance == null) {
            synchronized (DialogBuilder.class) {
                if (instance == null) {
                    instance = new DialogBuilder(context, R.style.dialog_untran);
                    instance.setCancelable(false);
                    instance.setCanceledOnTouchOutside(false);
                }
            }
        }
        return instance;
    }

    private void init(Context context) {

        mDialogView = View.inflate(context, R.layout.dialog_layout, null);
        mLinearLayoutView = (LinearLayout) mDialogView
                .findViewById(R.id.parentPanel);
        mRlinearLayoutView = (RelativeLayout) mDialogView
                .findViewById(R.id.main);
        mLinearLayoutTopView = (LinearLayout) mDialogView
                .findViewById(R.id.topPanel);
        mLinearLayoutMsgView = (LinearLayout) mDialogView
                .findViewById(R.id.contentPanel);
        mFrameLayoutCustomView = (FrameLayout) mDialogView
                .findViewById(R.id.customPanel);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        mButton1 = (Button) mDialogView.findViewById(R.id.button1);
        mButton2 = (Button) mDialogView.findViewById(R.id.button2);
        setContentView(mDialogView);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                mLinearLayoutView.setVisibility(View.VISIBLE);
                if (type == null) {
                    type = Effectstype.BounceUp;
                }
                start(type);

            }
        });
        mRlinearLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void toDefault() {
        mTitle.setTextColor(Color.parseColor(defTextColor));
        mDivider.setBackgroundColor(Color.parseColor(defDividerColor));
        mMessage.setTextColor(Color.parseColor(defMsgColor));
        mLinearLayoutView.setBackgroundColor(Color.parseColor(defDialogColor));
    }

    public DialogBuilder withDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }

    public DialogBuilder withTitle(CharSequence title) {
        toggleView(mLinearLayoutTopView, title);
        mTitle.setText(title);
        mTitle.setTypeface(Util.setTypeface(this.getContext(), Util.FONT));
        return this;
    }

    public DialogBuilder withTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public DialogBuilder withMessage(int textResId) {
        toggleView(mLinearLayoutMsgView, textResId);
        mMessage.setText(textResId);
        return this;
    }

    public DialogBuilder withMessage(CharSequence msg) {
        toggleView(mLinearLayoutMsgView, msg);
        mMessage.setText(msg);
        mMessage.setTypeface(Util.setTypeface(this.getContext(), Util.FONT), Typeface.BOLD);
        return this;
    }

    public DialogBuilder withMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public DialogBuilder withIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    public DialogBuilder withIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    public DialogBuilder withDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public DialogBuilder withEffect(Effectstype type) {
        this.type = type;
        return this;
    }

    public DialogBuilder withButtonDrawable(int resid) {
        mButton1.setBackgroundResource(resid);
        mButton2.setBackgroundResource(resid);
        return this;
    }

    public DialogBuilder withButton1Text(CharSequence text) {
        mButton1.setVisibility(View.VISIBLE);
        mButton1.setText(text);

        return this;
    }

    public DialogBuilder withButton2Text(CharSequence text) {
        mButton2.setVisibility(View.VISIBLE);
        mButton2.setText(text);
        return this;
    }

    public DialogBuilder setButton1Click(View.OnClickListener click) {
        mButton1.setOnClickListener(click);
        return this;
    }

    public DialogBuilder setButton2Click(View.OnClickListener click) {
        mButton2.setOnClickListener(click);
        return this;
    }

    public DialogBuilder setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        if (mFrameLayoutCustomView.getChildCount() > 0) {
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(customView);
        return this;
    }

    public DialogBuilder setCustomView(View view, Context context) {
        if (mFrameLayoutCustomView.getChildCount() > 0) {
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(view);
        return this;
    }

    private void toggleView(View view, Object obj) {
        if (obj == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {

        if (mTitle.getText().equals(""))
            mDialogView.findViewById(R.id.topPanel).setVisibility(View.GONE);
        super.show();
    }

    private void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mRlinearLayoutView);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mButton1.setVisibility(View.GONE);
        mButton2.setVisibility(View.GONE);
    }
}