package com.pocketmarket.mined.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.pocketmarket.mined.R;

/**
 * Created by mark on 12/1/17.
 */

public class CustomFontTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()){
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        String fontName = styledAttrs.getString(R.styleable.CustomFontTextView_customFont);
        styledAttrs.recycle();

        if (fontName != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            setTypeface(typeface);
        }
    }
}
