package enjoy.the.music;



import java.io.File;
import java.util.ArrayList;
import java.util.List;


import enjoy.the.music.tools.CustomDialog;
import enjoy.the.music.tools.ScanSDReicver;
import enjoy.the.music.tools.Setting;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.tarena.fashionmusic.MyApplication;
import com.tarena.fashionmusic.play.MusicPlayActivity;
import com.tarena.fashionmusic.util.BitmapTool;
import com.tarena.fashionmusic.util.Constant;
import com.tarena.fashionmusic.util.StrTime;

import enjoy.the.music.db.MusicGroupDao;
import enjoy.the.music.db.MusicItemDao;
import enjoy.the.music.entry.Music;
import enjoy.the.music.entry.MusicGroup;
import enjoy.the.music.entry.MusicItem;
import enjoy.the.music.entry.Musicdata;
import enjoy.the.music.main.adapter.MyPagerAdapter;
import enjoy.the.music.service.DownloadService;
import enjoy.the.music.service.DownloadService.MyBinder;
import enjoy.the.music.service.MusicPlayerService;

public class MainActivity extends Activity implements OnClickListener {


	// ------------�����ؼ�--------------------------------
	ViewPager viewPager;
	Context context;
	ImageButton btprevious, btplay, btnext, handler_icon;
	ImageButton  btfav,btnet;
	ImageButton btlocal;
	ImageView list_show_album, move2group;
	TextView tvsongname, tvdurction;
	ProgressBar progressBar;
	ProgressDialog progressDialog;
	// ------------------------------------------------
	List<View> views;
	LayoutInflater inflater;
	MyPagerAdapter pagerAdapter;
	File dir;
	int position;
	Bitmap nowbitmap = null;
	MusicReciver myReciver;
	refreshReciver reciver;
	ViewFlipper viewFlipper;
	MusicGroupDao groupDao;
	int mRingChoiceID;
	String musicname;
	public static final int MENU_UPDATE_GROUP = 1;
	public static final int MENU_DELETE_GROUP = 2;
	public static final int MENU_ADD_GROUP = 3;
	public static final int MENU_CLEAR_GROUP = 4;

	public static final int MENU_CHI_PLAY_ONE = 5;
	public static final int MENU_CHI_PLAYALL = 6;
	public static final int MENU_CHI_REMOVE = 7;

	public static final int MENU_DOWN_MUSIC = 8;
	public static final int MENU_LISTEN_MUSIC = 9;
	public static final int MENU_SETRING = 10;
  
	
	/**
	 * ��ȡ��Ļ�Ĵ�С0����� 1���߶�
	 * */
	public static int[] getScreen(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		return new int[] { (int) (outMetrics.density * outMetrics.widthPixels),
				(int) (outMetrics.density * outMetrics.heightPixels) };
	}
	
	/**
	 * ��ȡ�ļ��ĺ�׺�������ش�д
	 * */
	public static String getSuffix(String str) {
		int i = str.lastIndexOf('.');
		if (i != -1) {
			return str.substring(i + 1).toUpperCase();
		}
		return str;
	}
	
	private class refreshReciver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(intent.getAction())) {
				// ��ȡý�����Ϣ
				ArrayList<Music> musics = Musicdata.getMultiDatas(context);
				// ����listview
				MyApplication.getInstance().setMusics(musics);
				localLayout.Refresh(LocalLayout.CHANGE_LIST, musics);
				progressDialog.cancel();
				context.sendBroadcast(new Intent(Constant.ACTION_LISTCHANGED));
			} else if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(intent
					.getAction())) {
				// ��ʾ�Ի���
				progressDialog = new ProgressDialog(context);
				progressDialog.setMessage("�б�����ˢ����,��ȴ�......");
				progressDialog.show();
			}
		}

	}

	int totalms = 1;
	int curms = 1;
    public Music music;
	
	private class MusicReciver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// ����ʱ�����
			if (intent.getAction().equals(Constant.ACTION_UPDATE)) {
				music = (Music) intent.getSerializableExtra("music");
				totalms = intent.getIntExtra("totalms", 288888);// ��ʱ��
				int status = intent.getIntExtra("status", -1);
				try {
					String musicname = music.getMusicName();
					tvdurction.setText("00:00/"
							+ StrTime.getTime(music.getTime()));
					tvsongname.setText(musicname);
					if (intent.getBooleanExtra("isnet", false)) {
						nowbitmap = MyApplication.bitmap_s;
					} else {
						String albumkey = music.getAlbumkey();
						if (albumkey != null && !albumkey.equals("")) {
							nowbitmap = BitmapTool.getbitBmBykey(context,
									music.getAlbumkey());
						} else {
							nowbitmap = MyApplication.bitmap_s;
						}
					}
					if (nowbitmap != null && !nowbitmap.isRecycled()) {
						list_show_album.setImageBitmap(nowbitmap);
					} else {
						list_show_album
								.setImageResource(R.drawable.default_bg_s);
					}
					if (status == 3) {
						btplay.setImageResource(R.drawable.desktop_pausebt);
						isplaying = true;
					} else {
						btplay.setImageResource(R.drawable.desktop_playbt);
						isplaying = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (intent.getAction().equals(Constant.ACTION_DISS_DIALOG)) {
				if (progressDialog != null) {
					progressDialog.cancel();
				}
			}

		}

	}

	boolean isrunable = true;

	class myrunabe extends Thread {
		@Override
		public void run() {
			while (isrunable) {
				if (MyApplication.mediaPlayer != null
						&& MyApplication.mediaPlayer.isPlaying()) {
					curms = MyApplication.mediaPlayer.getCurrentPosition();
					handler.sendEmptyMessage(20);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 10:
				favoriteLayout.Refresh(FavoriteLayout.REFRESH_GROUP);
				viewFlipper.showPrevious();
				localLayout.Refresh(LocalLayout.SINGLE_CHOSE);
				break;
			case 20:
				int progress = curms * 100 / totalms;
				// ���õ�ǰ����
				progressBar.setProgress(progress);
				tvdurction.setText(StrTime.gettim(curms) + "/"
						+ StrTime.gettim(totalms));
				break;
			}
		}

	};

	private MyBinder binder;
	// �󶨺ͽ��serviceʱ�Ļص�����conn
	private ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			binder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (MyBinder) service;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		MyApplication.getInstance().addActivity(this);
		context = this;
		inflater = LayoutInflater.from(this);
		// ��ȡ�ϴ��˳�ʱ�Ĳ���λ��
		initView();
		startService(new Intent(context, MusicPlayerService.class));
		// ����service
		Intent intent = new Intent(this, DownloadService.class);
		startService(intent);
		this.getApplicationContext()
				.bindService(intent, conn, BIND_AUTO_CREATE);
		// ��������Ŀ¼
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File root = Environment.getExternalStorageDirectory();
			dir = new File(root, "TMusic");
			if (!dir.exists()) {
				dir.mkdir();
			}
		}
		myReciver = new MusicReciver();
		reciver = new refreshReciver();
		groupDao = new MusicGroupDao(context);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (localLayout.localistview.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
				localLayout.Refresh(LocalLayout.SINGLE_CHOSE);
				localLayout.localistview
						.setChoiceMode(ListView.CHOICE_MODE_NONE);
				viewFlipper.showPrevious();
				return false;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
		if (keyCode == KeyEvent.KEYCODE_MENU){
			MenuChoice();
		}
		return super.onKeyDown(keyCode, event);
	}

	NetLayout netLayout;
	LocalLayout localLayout;
	FavoriteLayout favoriteLayout;

	private void initView() {
		/* �м䲿��view page��ͼ���� */
		viewPager = (ViewPager) findViewById(R.id.center_body_view_pager);
		views = new ArrayList<View>();
	
		localLayout = new LocalLayout(context, handler);
		favoriteLayout = new FavoriteLayout(context);
		netLayout = new NetLayout(context);
		
		views.add(localLayout);
		views.add(favoriteLayout);
		views.add(netLayout);
		
		/* ����������������ť */

     	btlocal = (ImageButton) findViewById(R.id.imagemusic);
		btfav = (ImageButton) findViewById(R.id.imagelove);
		btnet = (ImageButton) findViewById(R.id.imagenet);
		/* �ײ���ʵר��ͼƬ */
		list_show_album = (ImageView) findViewById(R.id.list_show_album);
		/* ���������ǿ��ư�ť */
		btprevious = (ImageButton) findViewById(R.id.btnPrevious_player);
		btplay = (ImageButton) findViewById(R.id.btnPlay_player);
		btnext = (ImageButton) findViewById(R.id.btnNext_player);
		
		/* ���������ֲ���������ʵ��ǰ���������� �������� ʱ�� */
		tvsongname = (TextView) findViewById(R.id.list_song_name);
		tvdurction = (TextView) findViewById(R.id.list_song_durction);
		/* ������ */
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		viewFlipper = (ViewFlipper) findViewById(R.id.myfliper);
		move2group = (ImageView) findViewById(R.id.move_to_group);
		/* ���ð�ť���¼����� */
		btlocal.setOnClickListener(new MyMusicListener());
		btfav.setOnClickListener(new LoveMusicListener());
		btnet.setOnClickListener(new NetMusicListener());
		btprevious.setOnClickListener(this);
		btplay.setOnClickListener(this);
		btnext.setOnClickListener(this);
		list_show_album.setOnClickListener(this);
		move2group.setOnClickListener(this);

		registerForContextMenu(favoriteLayout.exgroupview);
		registerForContextMenu(netLayout.netlistview);

	}
   //�ҵ�������ʾ�б�
	 class MyMusicListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			btlocal.setSelected(true);
			btfav.setSelected(false);
			btnet.setSelected(false);
			viewPager.setCurrentItem(0);
		}
	 
	 }
	 //��ϲ���ĸ����б�
	 class LoveMusicListener implements View.OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btlocal.setSelected(false);
				btfav.setSelected(true);
				btnet.setSelected(false);
				viewPager.setCurrentItem(1);
			}
		 
		 }
	 //��������б�
	 class NetMusicListener implements View.OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btlocal.setSelected(false);
				btfav.setSelected(false);
				btnet.setSelected(true);
				viewPager.setCurrentItem(2);
			}
		 
		 }
	@Override
	protected void onStart() {
		super.onStart();
		// ������ʱ����� ҳ�浱ǰ������Ϣ
		sendBroadcast(new Intent(Constant.ACTION_UPDATE_ALL));
		position = MyApplication.musicPreference.getsaveposition(context);
		ShowNowPlayMusic();
	}

	public void ShowNowPlayMusic() {
		Music music = MyApplication.musics.get(position);
		if (music != null) {
			nowbitmap = BitmapTool.getbitBmBykey(context, music.getAlbumkey());
			if (nowbitmap != null && nowbitmap.isRecycled() == false) {
				list_show_album.setImageBitmap(nowbitmap);
			} else {
				list_show_album.setImageResource(R.drawable.default_bg_s);
			}
			tvsongname.setText(music.getMusicName());
			tvdurction.setText("00:00/" + StrTime.getTime(music.getTime()));
		}
	}

	Thread myThread;

	@Override
	protected void onResume() {
		super.onResume();
		initpagedata();
		// ����Ƥ������
		Setting setting = new Setting(this, false);
		viewPager.setBackgroundResource(setting.getCurrentSkinResId());
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_UPDATE);
		filter.addAction(Constant.ACTION_DISS_DIALOG);
		filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		registerReceiver(myReciver, filter);

		IntentFilter filter2 = new IntentFilter();
		filter2.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
		filter2.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		filter2.addDataScheme("file");
		registerReceiver(reciver, filter2);
		isrunable = true;
		// �������½��������߳�
		myThread = new myrunabe();
		myThread.start();
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		switch (v.getId()) {
		case R.id.elvGroup:
			ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
			long packedPosition = info.packedPosition;
			menu.setHeaderIcon(R.drawable.menu_info).setHeaderTitle("�˵�����");
			int type = ExpandableListView.getPackedPositionType(packedPosition);
			if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
				menu.add(0, MENU_CHI_PLAY_ONE, 1, "���Ÿ�����");
				menu.add(0, MENU_CHI_REMOVE, 1, "�Ƴ�����");
				menu.add(0, MENU_SETRING, 1, "��������");
			} else {
				menu.add(0, MENU_ADD_GROUP, 2, "��ӷ���");
				menu.add(0, MENU_DELETE_GROUP, 3, "ɾ������");
				menu.add(0, MENU_UPDATE_GROUP, 4, "���·�����");
				menu.add(0, MENU_CLEAR_GROUP, 3, "��շ���");
			}
			menu.add(0, MENU_CHI_PLAYALL, 1, "���ŷ���");
			break;
		case R.id.lvSounds:
			menu.setHeaderIcon(R.drawable.menu_info).setHeaderTitle(R.string.choice_do);
			menu.add(1, MENU_DOWN_MUSIC, 0, "��������");
			menu.add(1, MENU_LISTEN_MUSIC, 1, "��������");
			break;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	protected void onStop() {
		isrunable = false;
		super.onStop();
	}

	private void DownMusic(Music music) {
		// �����µ���������
		music.setSavePath(dir.getAbsolutePath()
				+ music.getMusicPath().substring(
						music.getMusicPath().lastIndexOf("/")));
		// ͨ��binder������service������������������
		binder.addTask(music);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getGroupId() == 1) {// Զ������
			AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
					.getMenuInfo();
			final Music music = (Music) netLayout.netlistview.getAdapter()
					.getItem(menuInfo.position);
			switch (item.getItemId()) {
			case MENU_DOWN_MUSIC:
				netLayout.Refresh(NetLayout.DOWN_MUSIC, menuInfo.position);
				if (!music.isLoaded()) {
					DownMusic(music);
				} else {
					AlertDialog.Builder builder = new Builder(context);
					builder.setTitle("ע��")
							.setMessage("�����Ѿ�����ȷ��Ҫ�ظ�������?")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											DownMusic(music);
										}
									}).setNegativeButton("ȡ��", null).create()
							.show();
				}
				break;
			case MENU_LISTEN_MUSIC:
				progressDialog = new ProgressDialog(MainActivity.this);
				progressDialog.setMessage("�������ڻ�����,��ȴ�......");
				progressDialog.show();
				Intent intent = new Intent(Constant.ACTION_NET_PLAY);
				intent.putExtra("net_music", music);
				sendBroadcast(intent);
				netLayout.Refresh(NetLayout.LISTEN_MUSIC, menuInfo.position);
				break;
			}
		} else if (item.getGroupId() == 0) {
			// ���menuinfo����
			ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item
					.getMenuInfo();
			// ���һ��packedPosition
			long packedPosition = info.packedPosition;
			// ����packedPosition����ȡExpandablelistview�б�ѡ�����groupPosition
			// ��id
			int groupPosition = ExpandableListView
					.getPackedPositionGroup(packedPosition);
			// childid
			int childPosition = ExpandableListView
					.getPackedPositionChild(packedPosition);
			// ��ȡ�����
			MusicGroup group = (MusicGroup) favoriteLayout.exgroupview
					.getExpandableListAdapter().getGroup(groupPosition);

			MusicItemDao musicDao = new MusicItemDao(this);

			switch (item.getItemId()) {
			case MENU_ADD_GROUP:// ��ӷ���
				favoriteLayout.Refresh(FavoriteLayout.ADD_GROUP);
				break;
			case MENU_CHI_PLAY_ONE:// ���ŵ�ǰѡ�����
				if (childPosition != -1) {
					MusicItem musicItem = (MusicItem) favoriteLayout.exgroupview
					.getExpandableListAdapter().getChild(groupPosition,childPosition);
			        Intent intent = new Intent(Constant.ACTION_FIND);
			        musicname = Musicdata.getMusicbyid(context,
					String.valueOf(musicItem.getMusicId())).getMusicName();
			        intent.putExtra("name", musicname);
			        sendBroadcast(intent);
				}
				break;
			case MENU_CHI_PLAYALL:// ���ŵ�ǰ����
				MyApplication.getInstance().setMusics(
						Musicdata.getmusicsByitem(
								musicDao.getMusicsByGroup(group.getId()),
								context));
				sendBroadcast(new Intent(Constant.ACTION_LISTCHANGED));
				break;
			case MENU_CHI_REMOVE:// �ӷ������Ƴ�
				if (childPosition != -1) {
					// ��ȡmusicitem����
					MusicItem musicItems = (MusicItem) favoriteLayout.exgroupview
							.getExpandableListAdapter().getChild(groupPosition,
									childPosition);
					// ��ȡ��musicitem��id��ɾ��
					musicDao.deleteItemById(musicItems.getId());
					// ���½���
					favoriteLayout.Refresh(FavoriteLayout.REFRESH_GROUP);
				}
				break;
			case MENU_CLEAR_GROUP:// ��շ���
				musicDao.deleteItemByGroupid(group.getId());
				favoriteLayout.Refresh(FavoriteLayout.REFRESH_GROUP);
				break;
			case MENU_DELETE_GROUP:// ɾ������
				groupDao.deleteGroup(group.getId());
				favoriteLayout.Refresh(FavoriteLayout.REFRESH_GROUP);
				break;
			case MENU_UPDATE_GROUP:// �޸ķ�������
				updatagroup(group.getId());
				break;
			case  MENU_SETRING://��������
				MusicItem musicItem1 = (MusicItem) favoriteLayout.exgroupview
				.getExpandableListAdapter().getChild(groupPosition,
						childPosition);
			      musicname = Musicdata.getMusicbyid(this.context, String.valueOf(musicItem1.getMusicId())).getMusicName();
			      setMoreRing();
				break;
			}
		}
		return super.onContextItemSelected(item);

	}


	private void setMoreRing(){
		String[] menustring = new String[] { "��Ϊ��������", "��Ϊ��ʾ��", "��Ϊ������" };
		ListView menulist = new ListView(context);
		menulist.setCacheColorHint(Color.TRANSPARENT);
		menulist.setDividerHeight(1);
		menulist.setAdapter(new ArrayAdapter<String>(context,R.layout.dialog_menu_item, R.id.text1, menustring));
		menulist.setLayoutParams(new LayoutParams(MainActivity.getScreen(context)[0] / 2,LayoutParams.WRAP_CONTENT));

		final CustomDialog xfdialog = new CustomDialog.Builder(context).setTitle("��Ҫ���ļ�����Ϊ:").setView(menulist).create();
		xfdialog.show();
		menulist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				xfdialog.cancel();
				xfdialog.dismiss();
				if (position==0) { MainActivity.this.setMyRingtone();}//��Ϊ��������
				else if (position==1) {MainActivity.this.setMyNotification();}
				else if (position==2) {MainActivity.this.setMyAlarm();}
			
			}
		});
	}

	/*���������*/
	private  void openDialog(){
		LayoutInflater in = MainActivity.this.getLayoutInflater();
		final View alarm = in.inflate(R.layout.alarm, null);
		
		new AlertDialog.Builder(this)
				.setTitle("Introduction Daitls")
				.setIcon(R.drawable.views)
				.setView(alarm)
				.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							
							public void onClick(DialogInterface dialog,
									int which) {														
									Toast.makeText(MainActivity.this,
											"������ϣ���л���Ա������ʹ�ã�", Toast.LENGTH_SHORT)
											.show();
							} 
					
						}).setNegativeButton("ȡ��", null).show();

	                            }
	
	
	//����--�����ľ��巽��
    public void setMyRingtone()  
    {  

      File sdfile = new File(musicname);
      ContentValues values = new ContentValues();  
      values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());  
      values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
      values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");   
      values.put(MediaStore.Audio.Media.IS_RINGTONE, true);  
      values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);  
      values.put(MediaStore.Audio.Media.IS_ALARM, false);  
      values.put(MediaStore.Audio.Media.IS_MUSIC, false);  
      Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile.getAbsolutePath());
      Uri newUri = this.getContentResolver().insert(uri, values);
      RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, newUri); 
      Toast.makeText( getApplicationContext (),"�������������ɹ���", Toast.LENGTH_SHORT ).show(); 
      System.out.println("setMyRingtone()-----����");
    }  

  //����--��ʾ���ľ���ʵ�ַ���
    public void setMyNotification()  
    { 
   
      File sdfile = new File(musicname);   
      ContentValues values = new ContentValues();  
      values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());  
      values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
      values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");   
      values.put(MediaStore.Audio.Media.IS_RINGTONE, false);  
      values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);  
      values.put(MediaStore.Audio.Media.IS_ALARM, false);  
      values.put(MediaStore.Audio.Media.IS_MUSIC, false);  
      
      Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile.getAbsolutePath());
      Uri newUri = this.getContentResolver().insert(uri, values);
      
      RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION, newUri); 
      Toast.makeText( getApplicationContext (),"����֪ͨ�����ɹ���", Toast.LENGTH_SHORT ).show();
      System.out.println("setMyNOTIFICATION-----��ʾ��");
    } 
  //����--�������ľ���ʵ�ַ���
  public void setMyAlarm()  
    {  
      File sdfile = new File(musicname);   
      ContentValues values = new ContentValues();  
      values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());  
      values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
      values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");   
      values.put(MediaStore.Audio.Media.IS_RINGTONE, false);  
      values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);  
      values.put(MediaStore.Audio.Media.IS_ALARM, true);  
      values.put(MediaStore.Audio.Media.IS_MUSIC, false);  
      
      Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile.getAbsolutePath());
      Uri newUri = this.getContentResolver().insert(uri, values);
      RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM, newUri); 
      Toast.makeText( getApplicationContext (),"�������������ɹ���", Toast.LENGTH_SHORT ).show();
      System.out.println("setMyNOTIFICATION------������");
    } 
  

	/**
	 * ɨ��SD��
	 */
	private void ScanSDCard() {
		IntentFilter intentfilter = new IntentFilter(
				Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentfilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentfilter.addDataScheme("file");
		ScanSDReicver receiver = new ScanSDReicver();
		registerReceiver(receiver, intentfilter);
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
		Uri.parse("file://"+ Environment.getExternalStorageDirectory().getAbsolutePath())));
		
	}
	/* ����ѡ�е����� */
	private void ShareMusicFile() {
	
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("audio/*");
		File file = new File(music.getMusicName());
		Uri u = Uri.fromFile(file);
		intent.putExtra(Intent.EXTRA_STREAM, u);
		System.out.println(music.getMusicName());
		intent.putExtra(Intent.EXTRA_SUBJECT, "����");
		intent.putExtra(Intent.EXTRA_TEXT,"��ǰ�����������"+music.getMusicName()+"(�û�ͨ��ħ�ֺз�����һ�׸���)");
		startActivity(Intent.createChooser(intent, getTitle()));
        return;
	}
	
	private void MenuChoice() {
		String[] menustring = new String[] { "ˢ�������б�","����ǰ����", "�������", "�˳�Ӧ��" };
		ListView menulist = new ListView(context);
		menulist.setCacheColorHint(Color.TRANSPARENT);
		menulist.setDividerHeight(1);
		menulist.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.dialog_menu_item, R.id.text1, menustring));
		menulist.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));

		final CustomDialog xfdialog = new CustomDialog.Builder(context).setTitle("�˵�ѡ��").setView(menulist).create();
		xfdialog.show();
		menulist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				xfdialog.cancel();
				xfdialog.dismiss();
				if (position==0) {
					ScanSDCard();
				}
				else if (position==1) {
					ShareMusicFile();
				}else if (position==2) {
					openDialog();
				}else if (position==3) {
					MusicPlayerService.manager.cancelAll();//����㲥
					SharedPreferences sp = getSharedPreferences("service", 0);
					sp.edit().putBoolean("isStart", false).commit();
					sendBroadcast(new Intent(Constant.ACTION_STOP));
					MyApplication.getInstance().exit();
				}

			}
		});
	}


	public void updatagroup(final int id) {
		View sleepdialog = getLayoutInflater().inflate(
				R.layout.change_groupname_dialog, null);
		final EditText editText = (EditText) sleepdialog
				.findViewById(R.id.sleeptime_edit);
		new AlertDialog.Builder(context).setView(sleepdialog)
				.setTitle("������Ҫ�޸ķ�������").setIcon(R.drawable.edite_group)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (editText.getText().toString() == null) {
							Toast.makeText(context, "���Ʋ�����Ϊ��", 2000).show();
						} else {
							groupDao.updateGroup(editText.getText().toString(),
									id);
							favoriteLayout
									.Refresh(FavoriteLayout.REFRESH_GROUP);
						}
					}
				}).setNegativeButton("ȡ��", null).show();
	}

	/**
	 * ��view page����Ӳ���
	 */
	public void initpagedata() {
		if (viewPager.getChildCount() <= 0) {
			pagerAdapter = new MyPagerAdapter(views);
		}
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {

				btfav.setSelected(false);
				btnet.setSelected(false);
				btlocal.setSelected(false);
				switch (arg0) {
			
				case 0:
					btlocal.setSelected(true);
					break;
				case 1:
					btfav.setSelected(true);
					favoriteLayout.Refresh(FavoriteLayout.REFRESH_GROUP);
					break;
				case 2:
					btnet.setSelected(true);
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		viewPager.setCurrentItem(0);
	}

	boolean isplaying = false;
	Intent broadcastIntent;
	int itemId = 1;

	@Override
	public void onClick(View v) {
		broadcastIntent = new Intent();
		switch (v.getId()) {
		case R.id.main_top_bt1:
			if (itemId > 0) {
				itemId--;
			} else {
				itemId = 2;
			}
			viewPager.setCurrentItem(itemId);
			break;
		case R.id.main_top_btn:
			viewPager.setCurrentItem(1);
			break;
		case R.id.main_top_bt2:
			if (itemId < 2) {
				itemId++;
			} else {
				itemId = 0;
			}
			viewPager.setCurrentItem(itemId);
			break;
		case R.id.list_show_album:
			startActivity(new Intent(context, MusicPlayActivity.class));
			overridePendingTransition(R.anim.act_in, R.anim.act_out);
			break;
		case R.id.btnPlay_player:
			if (!isplaying) {
				btplay.setImageResource(R.drawable.desktop_pausebt);
				if (position > 0) {// �������0˵�����Ǽ������ϴ��˳�ʱ��ĸ���
					broadcastIntent.setAction(Constant.ACTION_JUMR);
					broadcastIntent.putExtra("position", position);
				} else {
					broadcastIntent.setAction(Constant.ACTION_PLAY);
				}
				context.sendBroadcast(broadcastIntent);
				isplaying = true;
			} else {
				broadcastIntent.setAction(Constant.ACTION_PAUSE);
				sendBroadcast(broadcastIntent);
				isplaying = false;
				btplay.setImageResource(R.drawable.desktop_playbt);
			}
			break;
		case R.id.btnNext_player:
			btplay.setImageResource(R.drawable.desktop_pausebt);
			isplaying = true;
			broadcastIntent.setAction(Constant.ACTION_NEXT);
			context.sendBroadcast(broadcastIntent);
			break;
		case R.id.btnPrevious_player:
			btplay.setImageResource(R.drawable.desktop_pausebt);
			isplaying = true;
			broadcastIntent.setAction(Constant.ACTION_PREVIOUS);
			context.sendBroadcast(broadcastIntent);
			break;
		case R.id.move_to_group:
			
			localLayout.Refresh(LocalLayout.STAR_ADDGROUP);
			break;
		}

	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(myReciver);
		unregisterReceiver(reciver);
		super.onDestroy();
	}
}
