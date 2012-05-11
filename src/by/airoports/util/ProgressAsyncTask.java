package by.airoports.util;

import java.lang.ref.WeakReference;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public abstract class ProgressAsyncTask<Params, Progress, Result, WeakTarget extends Context> extends
		WeakAsyncTask<Params, Progress, Result, WeakTarget> implements DialogInterface.OnCancelListener {
	protected ProgressDialogInfo info;
	protected WeakReference<ProgressDialog> progress;

	public ProgressAsyncTask(WeakTarget target, ProgressDialogInfo info) {
		super(target);
		this.info = info;
	}

	@Override
	public void setTarget(WeakTarget target) {
		super.setTarget(target);
		if (getStatus() == Status.RUNNING) {
			showDialog(target);
		} else {
			dismissDialog();
		}
	}

	@Override
	protected void onPreExecute(WeakTarget target) {
		showDialog(target);
	}

	@Override
	protected void onPostExecute(WeakTarget target, Result result) {
		dismissDialog();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		if (getStatus() == Status.RUNNING) {
			cancel(true);
		}
	}

	private void showDialog(WeakTarget target) {
		if (info != null) {
			final ProgressDialog dialog = info.showDialog(target, this);
			progress = new WeakReference<ProgressDialog>(dialog);
		}
	}

	public void dismissDialog() {
		if (progress != null) {
			final ProgressDialog dialog = progress.get();
			if (dialog != null) {
				dialog.dismiss();
			}
		}
	}

	public static class ProgressDialogInfo {
		public final String title;
		public final String message;
		public final boolean indeterminate;
		public final boolean cancelable;

		public ProgressDialogInfo(String title, String message, boolean indeterminate, boolean cancelable) {
			this.title = title;
			this.message = message;
			this.indeterminate = indeterminate;
			this.cancelable = cancelable;
		}

		public ProgressDialog showDialog(Context target, OnCancelListener cancelListener) {
			return ProgressDialog.show(target, title, message, indeterminate, cancelable, cancelListener);
		}

	}
}
