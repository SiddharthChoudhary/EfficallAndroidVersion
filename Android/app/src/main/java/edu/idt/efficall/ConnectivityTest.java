package edu.idt.efficall;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

public class ConnectivityTest extends AsyncTask<Void, Void, Void> implements ISpeedTestListener
{
	private Activity activity;

	public ConnectivityTest(Activity activity)
	{
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(Void... voids)
	{
		SpeedTestSocket speedTestSocket = new SpeedTestSocket();

		speedTestSocket.addSpeedTestListener(this);

		//speedTestSocket.startDownload("http://eu.httpbin.org/stream-bytes/50000000", 1000);
		speedTestSocket.startDownload("http://ipv4.ikoula.testdebit.info/5M.iso", 1000);

		return null;
	}

	@Override
	public void onCompletion(SpeedTestReport report)
	{
		System.out.println("[COMPLETED] rate in octet/s : " + report.getTransferRateOctet());
		System.out.println("[COMPLETED] rate in bit/s   : " + report.getTransferRateBit());

		Intent intent = new Intent(activity, NetConnectivityResult.class);
		intent.putExtra(NetConnectivityResult.constants.SPEED.toString(), report.getTransferRateBit().toString());
		intent.putExtra(NetConnectivityResult.constants.SIZE.toString(),
				((Long) report.getTotalPacketSize()).toString());
		intent.putExtra(NetConnectivityResult.constants.TIME.toString(), ((Long) report.getReportTime()).toString());

		activity.startActivity(intent);
	}

	@Override
	public void onProgress(float percent, SpeedTestReport report)
	{
		System.out.println("[PROGRESS] progress : " + percent + "%");
		System.out.println("[PROGRESS] rate in octet/s : " + report.getTransferRateOctet());
		System.out.println("[PROGRESS] rate in bit/s   : " + report.getTransferRateBit());
	}

	@Override
	public void onError(SpeedTestError speedTestError, String errorMessage)
	{
		System.out.println("Error");
	}
}
