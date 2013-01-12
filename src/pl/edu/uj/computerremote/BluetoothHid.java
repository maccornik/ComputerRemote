package pl.edu.uj.computerremote;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.computerremote.ipc.IBluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.os.RemoteException;
import android.util.Log;

public class BluetoothHid implements BluetoothProfile {
	private static final String TAG = "BluetoothHid";
	private static final boolean DBG = false;
	private ServiceListener serviceListener;
	private IBluetooth service;
	private BluetoothAdapter adapter;

	public List<BluetoothDevice> getConnectedDevices() {
		if (service != null && isEnabled()) {
			try {
				service.getConnectedHidDevices();
			} catch (RemoteException e) {
				Log.e(TAG, "Stack: " + Log.getStackTraceString(new Throwable()));
				return new ArrayList<BluetoothDevice>();
			}
		}
		if (service == null) Log.w(TAG, "Proxy no attached to service");
		return new ArrayList<BluetoothDevice>();
	}

	public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) {
		if (service != null && isEnabled()) {
			try {
				return service.getHidDevicesMatchingConnectionStates(states);
			} catch (RemoteException e) {
				Log.e(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
				return new ArrayList<BluetoothDevice>();
			}
		}
		if (service == null) Log.w(TAG, "Proxy not attached to service");
		return new ArrayList<BluetoothDevice>();
	}

	public int getConnectionState(BluetoothDevice device) {
		if (service != null && isEnabled() && isValidDevice(device)) {
			try {
				return service.getHidDeviceConnectionState(device);
			} catch (RemoteException e) {
				Log.e(TAG, e.toString());
			}
		}
		else {
			Log.w(TAG, "Proxy not attached to service");
			if(DBG) Log.d(TAG, "Stack:" + Log.getStackTraceString(new Throwable()));
		}
		return STATE_DISCONNECTED;
	}

}
