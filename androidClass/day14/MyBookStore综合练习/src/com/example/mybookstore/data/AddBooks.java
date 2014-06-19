package com.example.mybookstore.data;

import java.io.File;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.example.mybookstore.data.DataContract.BookCatagoryContract;
import com.example.mybookstore.data.DataContract.BookContract;
/**
 * ������ݿ�����
 * @author Administrator
 *
 */
public class AddBooks {
	private String BOOKS_DIR = "booksStore";

	public void initialData(SQLiteDatabase db) {
		
		ContentValues g1 = new ContentValues();
		g1.put(BookCatagoryContract.CATAGORY_NAME, "����");

		ContentValues g2 = new ContentValues();
		g2.put(BookCatagoryContract.CATAGORY_NAME, "�Ƽ�");

		ContentValues g3 = new ContentValues();
		g3.put(BookCatagoryContract.CATAGORY_NAME, "����");
		

		db.insert(BookCatagoryContract.TABLE_NAME, "", g1);
		db.insert(BookCatagoryContract.TABLE_NAME, "", g2);
		db.insert(BookCatagoryContract.TABLE_NAME, "", g3);

		ContentValues b1 = new ContentValues();
		b1.put(BookContract.TITLE, "�����뺣");
		b1.put(BookContract.AUTHOR, "������");
		b1.put(BookContract.CATAGORY_ID, 1);
		b1.put(BookContract.PRICE, 27.5);
		b1.put(BookContract.ART, getBookAbsolutePath("img_book_01.jpg"));
		b1.put(BookContract.DATE, "1988-08-15");
		b1.put(BookContract.PAGES,500);
		b1.put(BookContract.DESCRIPTION,"�������뺣���Ǻ�������1951���ڹŰ�д��һƪ��ƪС˵����1952����档" +
				"�Ǻ���������������Ʒ֮һ����Χ��һλ����Ű������һ���޴�����������밶��Զ�������в�����չ" +
				"�����µĽ��������춨�˺�������������ѧ�е�ͻ����λ����ƪС˵��̻����1953�����������߽���1954" +
				"��ŵ������ѧ����");

		ContentValues b2 = new ContentValues();
		b2.put(BookContract.TITLE, "��������");
		b2.put(BookContract.AUTHOR, "���");
		b2.put(BookContract.CATAGORY_ID, 1);
		b2.put(BookContract.PRICE, 30.0);
		b2.put(BookContract.ART, getBookAbsolutePath("img_book_03.jpg"));
		b2.put(BookContract.DATE, "1956-01-15");
		b2.put(BookContract.PAGES,700);
		b2.put(BookContract.DESCRIPTION,"���������硷���ɷ���������ά�˶ࡤ�����1862���������һ����ƪС˵��������" +
				"������ս����֮���ʮ�����ʱ�䣬��ʮ��������������С˵֮һ�����µ�����Χ�����˹����׿��̷�Ƚ������" +
				"��Jean Valjean���ĸ��˾������ڽ��˷�������ʷ��������ս����������ѧ�����ɡ����塢�ڽ���������α���" +
				"�������Ӱ����Ʒ��");

		ContentValues b3 = new ContentValues();
		b3.put(BookContract.TITLE, "ʱ���ʷ");
		b3.put(BookContract.AUTHOR, "����");
		b3.put(BookContract.CATAGORY_ID, 1);
		b3.put(BookContract.PRICE, 54.0);
		b3.put(BookContract.ART, getBookAbsolutePath("img_book_02.jpg"));
		b3.put(BookContract.DATE, "1999-08-13");
		b3.put(BookContract.PAGES,2000);
		b3.put(BookContract.DESCRIPTION,"��ʱ���ʷ������Ӣ��ΰ�������ѧ��ʷ�ٷҡ�����׫д��һ���й�����ѧ��������" +
				"��һ�����������������ͨ�׻��Ŀ��շ�����");

		ContentValues b4 = new ContentValues();
		b4.put(BookContract.TITLE, "�����ݵ�����");
		b4.put(BookContract.AUTHOR, "���");
		b4.put(BookContract.CATAGORY_ID, 2);
		b4.put(BookContract.PRICE, 43.0);
		b4.put(BookContract.ART, getBookAbsolutePath("img_book_04.jpg"));
		b4.put(BookContract.DATE, "2008-05-07");
		b4.put(BookContract.PAGES,357);
		b4.put(BookContract.DESCRIPTION,"��ϵ��ǰ�����ݵķ�չ��״������΢��IBM��GE������500ǿ��ҵ�Դ����ݵ�Ӧ�ã�" +
				"��Ӧ�ô����ݵ���ҵ���������˲�����ս��������Ӧ�ùؼ��������й������ݵķ�չǰ���ȽǶȣ��������˴�" +
				"���ݶ����ǹ����������ѧϰ��ʽ�ĸĽ����Լ�����������ҵ��չת�͹����е�Ӧ�á�\r\n�������ݵ�������רע" +
				"���ð�����������ҵ�ڴ�����Ӧ�û��ڶ����ݵ���⡢�ռ���Ӧ���Լ����ʵ�����ݼ�ֵ��󻯣�ʹ�����ܹ�������" +
				"���������ΰ��ջ�����ͬʱ������ϸ�ڣ���׽�����Ӧ�ô����ݵľ��巽ʽ��������");

		ContentValues b5 = new ContentValues();
		b5.put(BookContract.TITLE, "������");
		b5.put(BookContract.AUTHOR, "�ǵ�.˹��");
		b5.put(BookContract.CATAGORY_ID, 1);
		b5.put(BookContract.PRICE, 43.0);
		b5.put(BookContract.ART, getBookAbsolutePath("img_book_05.jpg"));
		b5.put(BookContract.DATE, "1780-05-18");
		b5.put(BookContract.PAGES,625);
		b5.put(BookContract.DESCRIPTION,"������Ƹ������ʺ�ԭ����о����Ͼ����ܽ��˽������ڸ����ʱ����巢չ�ľ��飬" +
				"�������������˵�ʱ�й���Ҫ�������۵Ļ����ϣ����������񾭼��˶��������˽�ϵͳ�������׵�������" +
				"������Ƹ������ʺ�ԭ����о����Ͼ��������Ժ󣬲�������Ӣ���ʱ�����ķ�չ��ֱ�Ӳ������ش�Ĵٽ����ã�" +
				"���Ҷ������ʱ�����ķ�չ��˵������Ҳû�й��κ�����һ���ʲ��׼��ľ���ѧ��������������ô�㷺��Ӱ�졣" +
				"�޹ֵ�ʱ��Щ�ʲ��׼�ѧ�߰�����Ϊ���������ǣ���ʷ�ܿ�Ͱ����ľ����Ժ�ȱ�������ʾ�����ˡ�");

		ContentValues b6 = new ContentValues();
		b6.put(BookContract.TITLE, "�����е�Ů��");
		b6.put(BookContract.AUTHOR, "����˹");
		b6.put(BookContract.CATAGORY_ID, 3);
		b6.put(BookContract.PRICE, 27.0);
		b6.put(BookContract.ART, getBookAbsolutePath("img_book_06.jpg"));
		b6.put(BookContract.DATE, "2010-09-18");
		b6.put(BookContract.PAGES,125);
		b6.put(BookContract.DESCRIPTION,"�������е�Ů�ˡ�����D��H .����˹��ΰ�����д����ԡ��������˿ڵ�������ƪ" +
				"С˵֮һ����һ���ǡ��硷����������Ҳ��Ϊ�������ġ������Ʒ��������Ӣ��С˵��û�����������������̽����" +
				"�й��������������⣬����������˹��Ʒ����߳ɾͣ������ͬ���硷��Ϊ���ִ�С˵��������");

		ContentValues b7 = new ContentValues();
		b7.put(BookContract.TITLE, "���ž�֮������");
		b7.put(BookContract.AUTHOR, "��֮");
		b7.put(BookContract.CATAGORY_ID, 3);
		b7.put(BookContract.PRICE, 18.0);
		b7.put(BookContract.ART, getBookAbsolutePath("img_book_07.jpg"));
		b7.put(BookContract.DATE, "2012-09-18");
		b7.put(BookContract.PAGES,173);
		b7.put(BookContract.DESCRIPTION,"���鹲���£���������ʮ����������׵ı���Ʒ�֣��䶳���α��ɡ�ߦ�Ʊ��ɡ�" +
				"��״���ɡ����Ʊ��ɡ�������ɡ��ֹ����μ�ģ�����α��ɡ���ʹ0���ɡ�0���飬Ҳ������0ʧ���������ɡ�" +
				"����ϸ����ͼ����ʵ��tips�����������򵥣�ȴ����������ɹ����Ҹ�������ζ��");

		db.insert(BookContract.TABLE_NAME, "", b1);
		db.insert(BookContract.TABLE_NAME, "", b2);
		db.insert(BookContract.TABLE_NAME, "", b3);
		db.insert(BookContract.TABLE_NAME, "", b4);
		db.insert(BookContract.TABLE_NAME, "", b5);
		db.insert(BookContract.TABLE_NAME, "", b6);
		db.insert(BookContract.TABLE_NAME, "", b7);
	}

	private String getBookAbsolutePath(String fileName) {
		File dir = new File(Environment.getExternalStorageDirectory(),
				BOOKS_DIR);
		File file = new File(dir, fileName);
		return file.getAbsolutePath();
	}
}
