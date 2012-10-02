package by.airoports.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import by.airoports.R;

public class Calendar extends View {

	private Paint textPaint;
	private String text;
	private int ascent;
	private boolean isShownText;

	public Calendar(Context context) {
		super(context);
		initLabelView();
	}

	public Calendar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLabelView();
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.Calendar, 0, 0);

		try {
			isShownText = a.getBoolean(R.styleable.Calendar_showText, false);
		} finally {
			a.recycle();
		}
	}

	private final void initLabelView() {
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		// Must manually scale the desired text size to match screen density
		textPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
		textPaint.setColor(0xFF000000);
		setPadding(3, 3, 3, 3);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawText(text, getPaddingLeft(), getPaddingTop() - ascent,
				textPaint);		
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = (int) textPaint.measureText(text) + getPaddingLeft()
					+ getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		ascent = (int) textPaint.ascent();
		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number)
			result = (int) (-ascent + textPaint.descent()) + getPaddingTop()
					+ getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	public boolean isShowText() {
		return isShownText;
	}

	public void setText(String item) {
		this.text = item;
		requestLayout();
		invalidate();
	}

}
