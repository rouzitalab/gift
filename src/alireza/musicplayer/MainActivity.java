package alireza.musicplayer;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	MediaPlayer some;
	int length;
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		some = MediaPlayer.create(this, R.raw.elle_est_d_ailleurs);
		Log.e("Song is playing", "in  Mediya Player ");
		Log.e("Current", "Position -> " + length);
		some.setLooping(false);
		try {
			some.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		some.start();
		prefs.edit().putBoolean("mediaplaying", true);
		prefs.edit().commit();
		some.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer some) {
				some.stop();
				prefs.edit().putBoolean("mediaplaying", false);
				prefs.edit().commit();
			}
		});
		Log.e("CHK", "onCreate Called...");
	}

	@Override
	protected void onStart() {
		boolean isPlaying = prefs.getBoolean("mediaplaying", false);
		if (isPlaying) {
			int position = some.getCurrentPosition();
			prefs.edit().putInt("mediaPosition", position);
			prefs.edit().commit();
		}
		Log.e("CHK", "On start...");
		super.onStart();
	}
	@Override
	protected void onPause() {
		super.onPause();
		boolean isPlaying = prefs.getBoolean("mediaplaying", false);
		if (isPlaying) {
			int position = some.getCurrentPosition();
			prefs.edit().putInt("mediaPosition", position);
			prefs.edit().commit();
		}
		Log.e("CHK", "On Pause...");
	}

	@Override
	protected void onStop() {
		super.onStop();
		boolean isPlaying = prefs.getBoolean("mediaplaying", false);
		if (isPlaying) {
			int position = some.getCurrentPosition();
			prefs.edit().putInt("mediaPosition", position);
			prefs.edit().commit();

		}
		Log.e("CHK", "On Stop...");
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			some.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean isPlaying = prefs.getBoolean("mediaplaying", false);
		if (isPlaying) {
			int position = prefs.getInt("mediaPosition", 0);
			some.seekTo(position);
		}
		Log.e("CHK", "On Resume...");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		try {
			some.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean isPlaying = prefs.getBoolean("mediaplaying", false);
		if (isPlaying) {
			int position = prefs.getInt("mediaPosition", 0);
			some.seekTo(position);
		}
		Log.e("CHK", "New Intent...");
		super.onNewIntent(intent);
	}

	@Override
	protected void onDestroy() {
		Log.e("CHK", "On Destroy...");
		boolean isPlaying = prefs.getBoolean("mediaplaying", false);
		if (isPlaying) {
			int position = some.getCurrentPosition();
			prefs.edit().putInt("mediaPosition", position);
			prefs.edit().commit();

		}
		some.stop();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
