package enjoy.the.music;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;





import com.tarena.fashionmusic.MusicLayIntenface;
import com.tarena.fashionmusic.MyApplication;
import com.tarena.fashionmusic.util.Constant;
import com.tarena.fashionmusic.util.StrTime;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import enjoy.the.music.R;
import enjoy.the.music.db.MusicGroupDao;
import enjoy.the.music.db.MusicItemDao;
import enjoy.the.music.entry.Music;
import enjoy.the.music.entry.MusicGroup;
import enjoy.the.music.entry.MusicItem;
import enjoy.the.music.main.adapter.LocalMusicListAdapter;
import enjoy.the.music.tools.CustomDialog;


public class LocalLayout extends LinearLayout implements MusicLayIntenface {

	
	View rootview;
	ListView localistview;
	LayoutInflater inflater;
	Context context;
	MusicItemDao musicItemDao;
	LocalMusicListAdapter adapter;
	Handler handler;
    public int num;
	public static final int MULTCHOSE = 1;
	public static final int STAR_ADDGROUP = 2;
	public static final int SINGLE_CHOSE = 3;
	public static final int CHANGE_LIST = 4;

	//���ظ����ı���ʾ
	public LocalLayout(Context context, Handler handler) {
		super(context);
		//ͨ��LayoutInflaterһ������inflater������ʾһ��������
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		rootview = inflater.inflate(R.layout.musiclist, this, true);
		this.handler = handler;
		initView();//���¼���ʼ��
		initData();//���¼���ʼ����
		initListener();
		//Others();
	
	}

	@Override
	public void initView() {
		musicItemDao = new MusicItemDao(context);
		localistview = (ListView) rootview.findViewById(R.id.lvSounds);
	}

	ArrayList<Music> locallist;
	ArrayList<Integer> musicid = new ArrayList<Integer>();;
	MusicItem musicItem;

	@Override
	public void initData() {

		locallist = MyApplication.musics;
		adapter = new LocalMusicListAdapter(context, locallist, localistview);
		localistview.setAdapter(adapter);
		
	
	}
	
	


	/**
	 * �û������б������¼�
	 */
	private void SongItemDialog() {
		String[] menustring = new String[] { "���Ŵ�����", "��ӵ�����", "ɾ���˸���" , "�鿴�ø�������"};
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
				Music music = locallist.get(num);
			
			    
				if (position==0) {goplay(num);}//����
				else if (position==1) 
				{AddToGroup(context, music.getId(), false,null);}
				else if (position==2) {// ��ý�����ɾ�������ֵļ�¼
					LocalLayout.this.context.getContentResolver()
					.delete(ContentUris.withAppendedId(
							MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
								music.getId()),
							null, null);
			// ��sdcard��ɾ���ļ�
			new File(music.getSavePath()).delete();
			// ����listView
			LocalLayout.this.adapter.remove(num);
			// �����ַ����б����Ƴ�������Ϣ
			musicItemDao.deleteItemByMusicid(music
					.getId());}
				
				else if (position==3){
					
					View view1 = inflater.inflate(R.layout.song_detail, null);
					((ViewGroup) rootview).removeView(view1);
					((TextView) view1.findViewById(R.id.tv_song_title)).setText(music.getMusicName());
					((TextView) view1.findViewById(R.id.tv_song_artist)).setText(music.getSinger());
					((TextView) view1.findViewById(R.id.tv_song_album)).setText(music.getAlbumName());
					((TextView) view1.findViewById(R.id.tv_song_filepath)).setText(music.getSavePath());
					((TextView) view1.findViewById(R.id.tv_song_duration)).setText(StrTime.getTime(music.getTime()));
					((TextView) view1.findViewById(R.id.tv_song_format)).setText(MainActivity.getSuffix(music.getSavePath()));
					//((TextView) view1.findViewById(R.id.tv_song_size)).setText(music.getSerialversionuid() + "MB");
					new CustomDialog.Builder(context).setTitle("������ϸ��Ϣ:").setNeutralButton(R.string.Yes, null).setView(view1).create().show();	
				}
			}
		});
	}
	/*��Listview�ļ���������е���Ӧ����*/
	@Override
	public void initListener() {
		localistview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				num=arg2;
				SongItemDialog();
				return false;
			}
		});

		localistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				goplay(arg2);
			}
		});

	}



	/**
	 * ��ӵ�ǰ���ֵ�����
	 * 
	 * @param context
	 * @param positions
	 */
	private void AddToGroup(final Context context, final int musicId,
			final boolean ismult, final ArrayList<Integer> ids) {
		// ��ѯ���е�musicgroup
		ArrayList<MusicGroup> groups = new MusicGroupDao(context).getGroups();
		// �����е�musicgrouptitle������һ��������
		final String[] titles = new String[groups.size()];
		for (int i = 0; i < titles.length; i++) {
			titles[i] = groups.get(i).getId() + "��" + groups.get(i).getTitle();
		}
		// ������ѡ�Ի���
		AlertDialog.Builder builder = new Builder(context);
		final AlertDialog dialog = builder
				.setTitle(R.string.choice_group)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(titles, -1,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// ��ȡ��id
								int groupId = Integer.parseInt(titles[which]
										.substring(0,
												titles[which].indexOf("��")));
								if (ismult) {
									for (Integer id : ids) {
										addToGroup(id, groupId);
									}
								} else {
									addToGroup(musicId, groupId);
								}
								// �رնԻ���
								dialog.cancel();
							}
						}).setNegativeButton(R.string.cancel, null).show();
	}

	private void addToGroup(final int musicId, int groupId) {
		// ����musicItem����
		musicItem = new MusicItem();
		musicItem.setGroupId(groupId);
		musicItem.setMusicId(musicId);
		// ������ݵ�musicitem��
		musicItemDao.addMusicItem(musicItem);
	}

	public void goplay(int position) {
		Intent intent = new Intent(Constant.ACTION_JUMR);
		intent.putExtra("position", position);
		context.sendBroadcast(intent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void Refresh(Object... obj) {
		int FLAG = Integer.parseInt(String.valueOf(obj[0]));
		if (FLAG == MULTCHOSE) {
			localistview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			adapter.setmultchosemode(true);
		} else if (FLAG == STAR_ADDGROUP) {
			Iterator i = adapter.getchoseMap().values().iterator();
			while (i.hasNext()) {
				Music o = (Music) i.next();
				musicid.add(o.getId());
			}
			AddToGroup(context, 0, true, musicid);
			handler.sendEmptyMessage(10);
		} else if (SINGLE_CHOSE == FLAG) {
			adapter.setmultchosemode(false);
		} else if (CHANGE_LIST == FLAG) {
			adapter.addItems((ArrayList<Music>) obj[1]);
		}
	}

}
