package by.airoports.app;

import android.app.Application;

public class AiroportsApplication extends Application {

	public static AiroportsApplication instance = null;

	public AiroportsApplication() {
		instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public static AiroportsApplication getInstance() {
		if (instance == null) {
			instance = new AiroportsApplication();
		}
		return instance;
	}

}
