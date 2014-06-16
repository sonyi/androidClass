package enjoy.the.music;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tarena.fashionmusic.util.Constant;

import enjoy.the.music.main.adapter.MusicListAdapter;
import enjoy.the.music.tools.Setting;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

/** ר���� **/
/*
 * This work comes from DreamerؼTeam. The main programmer is LinShaoHan.
 * QQ:752280466 Welcome to join with us.
 */
public class FindSongActivity extends Activity {
	private int[] _ids;
	private String[] _titles;
	private String[] _artists;
	private String[] _path; // �����ļ���·��
	private ListView listview;
	private int pos;
	public Cursor content;
	private String albumName;
	private MusicListAdapter adapter;
	
	/* �����Ĳ˵��� */
	private static final int PLAY_ITEM = Menu.FIRST;
	private static final int DELETE_ITEM = Menu.FIRST + 1;
	private static final int SONG_SHARE = Menu.FIRST + 2;
	private static final int SET_AS = Menu.FIRST + 3;
	private boolean isselect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		albumName = intent.getExtras().getString("albums");
		listview = new ListView(this);
		LinearLayout list = new LinearLayout(this);
		// ����Ƥ������
		Setting setting = new Setting(this, false);
		list.setBackgroundResource(setting.getCurrentSkinResId());
		setListData();
		listview.setOnItemClickListener(new ListItemClickListener());
		listview.setOnCreateContextMenuListener(new ContextMenuListener());
	


		/* �����setBackgroundResource���õ�����ʾָ��ר�������и�����ʾ�б�ı��� */
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		list.addView(listview, params);
		setContentView(list);
		
		/* ������������������Ӧֵ�Ĵ��� �����Ѹ��ʵ�ֵ���Ҫ����*/
		String select = getIntent().getStringExtra("select");
		isselect = getIntent().getBooleanExtra("isselect", false);
		if (isselect) {
			ContentResolver cr = FindSongActivity.this.getContentResolver();
			content = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					null, " title like ?", new String[] { "%" + select + "%" },
					null);

			if (content != null) {
				_ids = new int[content.getCount()];
				_artists = new String[content.getCount()];
				_titles = new String[content.getCount()];
				int i = 0;
				while (content.moveToNext()) {
					_ids[i] = content.getInt(content
							.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
					_artists[i] = content
							.getString(content
									.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
					_titles[i] = content
							.getString(content
									.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
					i++;

				}

			}
         
			adapter = new MusicListAdapter(this, content);
			listview.setAdapter(adapter);
			
		}

	}

	/* ����ѡ�е����� */

	private void playMusic(int position) {
		Intent intent = new Intent(Constant.ACTION_FIND);
		intent.putExtra("name", _titles[position]);
		this.sendBroadcast(intent);

	}

	/* ���б���ɾ��ѡ�е����� */
	private void deleteMusic(int position) {
		this.getContentResolver().delete(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				MediaStore.Audio.Media._ID + "=" + _ids[position], null);
	}

	/* ��sdcard��ɾ��ѡ�е����� */
	private void deleteMusicFile(int position) {
		File file = new File(_titles[position]);
		
		try {
			deleteFile(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ɾ��ͼƬ�ļ��ķ���
	public static void deleteFile(File f) throws Exception {
		if (f.isFile()) {
			if (f.canWrite()) {
				f.delete();
			} else {
				throw new Exception("�ļ���" + f.getName() + "ֻ�����޷�ɾ�������ֶ�ɾ��");
			}
		} else {
			File[] fs = f.listFiles();
			if (fs.length != 0) {
				for (int i = 0; i < fs.length; i++) {
					deleteFile(fs[i]);
				}
			}
		}
	}


	/* ����ѡ�е����� */
	private void ShareMusicFile(int position) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		// intent.setType("text/plain");
		intent.setType("audio/*");
		File file = new File(_titles[position]);
		Uri u = Uri.fromFile(file);
		intent.putExtra(Intent.EXTRA_STREAM, u);
		intent.putExtra(Intent.EXTRA_SUBJECT, "����");
		intent.putExtra(Intent.EXTRA_TEXT,
				"��������    (����Dreamer����С�飬��ӭʹ������Ӱ��������)");
		startActivity(Intent.createChooser(intent, getTitle()));

	}

	// ָ���������ò���
	private void setEffects() {

		String[] items = { "����Ϊ��������", "����Ϊ֪ͨ����", "����Ϊ��������" };

		AlertDialog dialog = new AlertDialog.Builder(FindSongActivity.this)
				.setIcon(R.drawable.menu_icon_set).setTitle("�����趨����")
				.setItems(items, onSetEffectsSelect).create();
		dialog.show();
	}

	OnClickListener onSetEffectsSelect = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {

			switch (which) {
			/* ����---���� */
			case 0:
				setMyRingtone(pos);

				break;
			/* ����---��ʾ�� */
			case 1:

				setMyNotification(pos);
				break;

			/* ����---������ */
			case 2:
				setMyAlarm(pos);
				break;
			}

		}
	};

	// ����--�����ľ��巽��
	public void setMyRingtone(int position) {

		File sdfile = new File(_titles[position]);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
		values.put(MediaStore.Audio.Media.IS_ALARM, false);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile
				.getAbsolutePath());
		Uri newUri = this.getContentResolver().insert(uri, values);
		RingtoneManager.setActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_RINGTONE, newUri);
		Toast.makeText(getApplicationContext(), "�������������ɹ���", Toast.LENGTH_SHORT)
				.show();
		System.out.println("setMyRingtone()-----����");
	}

	// ����--��ʾ���ľ���ʵ�ַ���
	public void setMyNotification(int position) {

		File sdfile = new File(_titles[position]);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
		values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
		values.put(MediaStore.Audio.Media.IS_ALARM, false);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile
				.getAbsolutePath());
		Uri newUri = this.getContentResolver().insert(uri, values);
		RingtoneManager.setActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_NOTIFICATION, newUri);
		Toast.makeText(getApplicationContext(), "����֪ͨ�����ɹ���", Toast.LENGTH_SHORT)
				.show();
		System.out.println("setMyNOTIFICATION-----��ʾ��");
	}

	// ����--�������ľ���ʵ�ַ���
	public void setMyAlarm(int position) {

		File sdfile = new File(_titles[position]);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
		values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
		values.put(MediaStore.Audio.Media.IS_ALARM, true);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile
				.getAbsolutePath());
		Uri newUri = this.getContentResolver().insert(uri, values);
		RingtoneManager.setActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_ALARM, newUri);
		Toast.makeText(getApplicationContext(), "�������������ɹ���", Toast.LENGTH_SHORT)
				.show();
		System.out.println("setMyNOTIFICATION------������");
	}

	class ListItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {

			playMusic(position);
		}

	}

	/* ���������Ĳ˵������� */
	class ContextMenuListener implements OnCreateContextMenuListener {

		public void onCreateContextMenu(ContextMenu menu, View view,
				ContextMenuInfo info) {
			menu.setHeaderTitle("��ز���");
			menu.setHeaderIcon(R.drawable.menu_icon_set);
			menu.add(0, PLAY_ITEM, 0, "����");
			menu.add(0, DELETE_ITEM, 0, "ɾ��");
			menu.add(0, SONG_SHARE, 0, "����");
			menu.add(0, SET_AS, 0, "�������ò���");
			final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) info;
			pos = menuInfo.position;
		}
	}

	/* �����Ĳ˵���ĳһ����ʱ�ص��÷��� */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case PLAY_ITEM: // ��ʼ����
			playMusic(pos);
			break;

		case DELETE_ITEM: // ɾ��һ�׸���
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("��ȷ��Ҫ�����ֿ���ɾ�����׸�����")
					.setPositiveButton("��",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									deleteMusic(pos); // ���б���ɾ������
									deleteMusicFile(pos); // ��sdcard��ɾ������
									setListData(); // ���»���б���ҩ��ʾ������
									adapter.notifyDataSetChanged(); // �����б�UI
								}
							}).setNegativeButton("��", null);
			AlertDialog ad = builder.create();
			ad.show();
			break;

		case SONG_SHARE:// ����ѡ�еĸ���
			ShareMusicFile(pos);
			break;

		case SET_AS:// ��ר���б��б�ѡ�еĸ�������Ϊ...
			setEffects();
			break;

		}
		return true;
	}

	private void setListData() {
		Cursor c = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Media.TITLE,
						MediaStore.Audio.Media.DURATION,
						MediaStore.Audio.Media.ARTIST,
						MediaStore.Audio.Media._ID,
						MediaStore.Audio.Media.DISPLAY_NAME,
						MediaStore.Audio.Media.DATA,
						MediaStore.Audio.Media.ALBUM_ID, },
				MediaStore.Audio.Media.ALBUM + "='" + albumName + "'",

				null, null);
		c.moveToFirst();
		_ids = new int[c.getCount()];
		_titles = new String[c.getCount()];
		_path = new String[c.getCount()];
		_artists = new String[c.getCount()];
		for (int i = 0; i < c.getCount(); i++) {
			_ids[i] = c.getInt(3);
			_titles[i] = c.getString(0);
			_path[i] = c.getString(5);
			_artists[i] = c.getString(c
					.getColumnIndex(MediaStore.Audio.Media.ARTIST));
			c.moveToNext();
		}
		adapter = new MusicListAdapter(this, c);
		listview.setAdapter(adapter);
	}

}
