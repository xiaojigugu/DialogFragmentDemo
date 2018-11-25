package com.example.junt.dialogfragmentdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DialogUtil extends Dialog {

    private View view;
    private Boolean canCanceled;
    private Context context;
    private int width, height;
    private int x, y, gravity;
    private float widthScale, heightScale;

    private DialogUtil(Builder builder) {
        super(builder.context);
        context = builder.context;
        canCanceled = builder.canCanceled;
    }

    private DialogUtil(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        context = builder.context;
        canCanceled = builder.canCanceled;
        view = builder.view;
        width = builder.width;
        height = builder.height;
        x = builder.x;
        y = builder.y;
        widthScale = builder.widthScale;
        heightScale = builder.heightScale;
        gravity = builder.gravity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(canCanceled);
        setCanceledOnTouchOutside(canCanceled);
        setContentView(view);
        setOwnerActivity((Activity) context);
    }

    public static final class Builder {
        private int x, y, gravity;
        private float widthScale, heightScale;
        private int width, height;
        private Context context;
        private int resStyle;
        private Boolean canCanceled = false;
        private View view;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder sizeScale(float widthScale, float heightScale) {
            this.widthScale = widthScale;
            this.heightScale = heightScale;
            return this;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder position(int x, int y, int gravity) {
            this.x = x;
            this.y = y;
            this.gravity = gravity;
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder View(int resLayoutId) {
            view = LayoutInflater.from(context).inflate(resLayoutId, null);
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canCanceled) {
            this.canCanceled = canCanceled;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            if (backgroundDrawable != null) {
                view.setBackground(backgroundDrawable);
            }

            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            if (backgroundColor != 0) {
                view.setBackgroundColor(backgroundColor);
            }
            return this;
        }

        public Builder setBackgroundResource(int backgroundResource) {
            if (backgroundResource != 0) {
                view.setBackgroundResource(backgroundResource);
            }
            return this;
        }


        public Builder setText(int viewId, String str, int textColor, int textSize, int gravity) {
            if (view.findViewById(viewId) instanceof TextView) {
                if (!str.isEmpty()) {
                    ((TextView) view.findViewById(viewId)).setText(str);
                }
                if (textColor != 0) {
                    ((TextView) view.findViewById(viewId)).setTextColor(textColor);
                }
                if (textSize != 0) {
                    ((TextView) view.findViewById(viewId)).setTextSize(textSize);
                }
                if (gravity != 0) {
                    ((TextView) view.findViewById(viewId)).setGravity(gravity);
                }
            }
            return this;
        }

        public Builder setButtonText(int viewId, String str, int textColor, int textSize) {
            if (view.findViewById(viewId) instanceof Button) {
                if (!str.isEmpty()) {
                    ((Button) view.findViewById(viewId)).setText(str);
                }
                if (textColor != 0) {
                    ((Button) view.findViewById(viewId)).setTextColor(textColor);
                }
                if (textSize != 0) {
                    ((Button) view.findViewById(viewId)).setTextSize(textSize);
                }
            }
            return this;
        }

        public Builder setButtonBackgroundDrawable(int viewId, Drawable backgroundDrawable) {
            if (backgroundDrawable != null && view.findViewById(viewId) instanceof Button) {
                view.findViewById(viewId).setBackground(backgroundDrawable);
            }

            return this;
        }

        public Builder setButtonBackgroundColor(int viewId, int backgroundColor) {
            if (backgroundColor != 0 && view.findViewById(viewId) instanceof Button) {
                view.findViewById(viewId).setBackgroundColor(backgroundColor);
            }
            return this;
        }

        public Builder setButtonBackgroundResource(int viewId, int backgroundResource) {
            if (backgroundResource != 0 && view.findViewById(viewId) instanceof Button) {
                view.findViewById(viewId).setBackgroundResource(backgroundResource);
            }
            return this;
        }

        public Builder setOnViewClick(int viewId, View.OnClickListener listener) {
            view.findViewById(viewId).setOnClickListener(listener);
            return this;
        }

        public View getWidget(int widgetId) {
            return view.findViewById(widgetId);
        }

        public DialogUtil build() {
            if (resStyle != -1) {
                DialogUtil dialogUtil = new DialogUtil(this, resStyle);
                dialogUtil.setOwnerActivity((Activity) context);
                return dialogUtil;
            } else {
                DialogUtil dialogUtil = new DialogUtil(this);
                dialogUtil.setOwnerActivity((Activity) context);
                return dialogUtil;
            }
        }
    }

    @Override
    public void show() {
        if (context instanceof Activity) {
            Activity ac = (Activity) context;
            if (!ac.isFinishing()) {
                super.show();
                Window window = getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                if (widthScale != 0 && heightScale != 0) {
                    params.width = (int) (context.getResources().getDisplayMetrics().widthPixels * widthScale);
                    params.height = (int) (context.getResources().getDisplayMetrics().widthPixels * widthScale * heightScale);
                    window.setAttributes(params);
                } else {
                    if (width != 0) {
                        params.width = dip2px(context, width);
                    }
                    if (height != 0) {
                        params.height = dip2px(context, height);
                    }
                    if (x != 0) {
                        params.x = x;
                    }
                    if (y != 0) {
                        params.y = y;
                    }
                    if (gravity != 0) {
                        params.gravity = gravity;
                    }
                    window.setAttributes(params);
                }
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
