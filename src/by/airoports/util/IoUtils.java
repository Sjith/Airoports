package by.airoports.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class UtilsIo.
 * 
 * @author aleksey.dmitriev
 */
public abstract class IoUtils {
	public static boolean isInternetConnectionAvailable(final Context c) {
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isGpsOrWifiAvailable(final Context c) {
		LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isGpsAvailable(final Context c) {
		LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);

		return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static boolean networkStateInfo(final Context c) {
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] ni = cm.getAllNetworkInfo();

		for (int i = 0; i < ni.length; i++) {

		}

		return true;
	}
}
