package edu.idt.efficall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class NetConnectivityResult extends Activity
{
	enum constants
	{
		SPEED, SIZE, TIME;
	}

	private TextView	speedField;
	private TextView	sizeField;
	private TextView	timeField;
	private TextView	networkType;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		speedField = (TextView) findViewById(R.id.speed);
		sizeField = (TextView) findViewById(R.id.size);
		timeField = (TextView) findViewById(R.id.time);
		networkType = (TextView) findViewById(R.id.network_type);

		Intent intent = getIntent();

		String speedText = intent.getStringExtra(constants.SPEED.toString());
		String sizeText = intent.getStringExtra(constants.SIZE.toString());
		String timeText = intent.getStringExtra(constants.TIME.toString());

		double speed = Double.parseDouble(speedText);
		long time = Long.parseLong(timeText);

		speed = speed / 8;
		speed = speed / (1024 * 1024);
		speed = Math.round(speed * 100.0) / 100.0;

		time = time / 1000000000;

		speedField.setText(speed + " Mb/s");
		sizeField.setText(sizeText);
		timeField.setText(time + " secs");

		if (isWifi())
		{
			networkType.setText("WIFI");
		}
		else
		{
			networkType.setText("Mobile Network");
		}

	}

	private boolean isWifi()
	{
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();

		for (NetworkInfo ni : netInfo)
		{
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}

		return haveConnectedWifi || haveConnectedMobile;
	}
}
