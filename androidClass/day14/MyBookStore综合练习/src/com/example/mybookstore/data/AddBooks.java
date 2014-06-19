package com.example.mybookstore.data;

import java.io.File;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.example.mybookstore.data.DataContract.BookCatagoryContract;
import com.example.mybookstore.data.DataContract.BookContract;
/**
 * 添加数据库数据
 * @author Administrator
 *
 */
public class AddBooks {
	private String BOOKS_DIR = "booksStore";

	public void initialData(SQLiteDatabase db) {
		
		ContentValues g1 = new ContentValues();
		g1.put(BookCatagoryContract.CATAGORY_NAME, "名著");

		ContentValues g2 = new ContentValues();
		g2.put(BookCatagoryContract.CATAGORY_NAME, "科技");

		ContentValues g3 = new ContentValues();
		g3.put(BookCatagoryContract.CATAGORY_NAME, "生活");
		

		db.insert(BookCatagoryContract.TABLE_NAME, "", g1);
		db.insert(BookCatagoryContract.TABLE_NAME, "", g2);
		db.insert(BookCatagoryContract.TABLE_NAME, "", g3);

		ContentValues b1 = new ContentValues();
		b1.put(BookContract.TITLE, "老人与海");
		b1.put(BookContract.AUTHOR, "海明威");
		b1.put(BookContract.CATAGORY_ID, 1);
		b1.put(BookContract.PRICE, 27.5);
		b1.put(BookContract.ART, getBookAbsolutePath("img_book_01.jpg"));
		b1.put(BookContract.DATE, "1988-08-15");
		b1.put(BookContract.PAGES,500);
		b1.put(BookContract.DESCRIPTION,"《老人与海》是海明威于1951年在古巴写的一篇中篇小说，于1952年出版。" +
				"是海明威最著名的作品之一。它围绕一位老年古巴渔夫，与一条巨大的马林鱼在离岸很远的湾流中搏斗而展" +
				"开故事的讲述。它奠定了海明威在世界文学中的突出地位，这篇小说相继获得了1953年美国普利策奖和1954" +
				"年诺贝尔文学奖。");

		ContentValues b2 = new ContentValues();
		b2.put(BookContract.TITLE, "悲惨世界");
		b2.put(BookContract.AUTHOR, "雨果");
		b2.put(BookContract.CATAGORY_ID, 1);
		b2.put(BookContract.PRICE, 30.0);
		b2.put(BookContract.ART, getBookAbsolutePath("img_book_03.jpg"));
		b2.put(BookContract.DATE, "1956-01-15");
		b2.put(BookContract.PAGES,700);
		b2.put(BookContract.DESCRIPTION,"《悲惨世界》是由法国大作家维克多·雨果于1862年所发表的一部长篇小说，涵盖了" +
				"拿破仑战争和之后的十几年的时间，是十九世纪最著名的小说之一。故事的主线围绕主人公土伦苦刑犯冉·阿让" +
				"（Jean Valjean）的个人经历，融进了法国的历史、革命、战争、道德哲学、法律、正义、宗教信仰。多次被改" +
				"编演绎成影视作品。");

		ContentValues b3 = new ContentValues();
		b3.put(BookContract.TITLE, "时间简史");
		b3.put(BookContract.AUTHOR, "霍金");
		b3.put(BookContract.CATAGORY_ID, 1);
		b3.put(BookContract.PRICE, 54.0);
		b3.put(BookContract.ART, getBookAbsolutePath("img_book_02.jpg"));
		b3.put(BookContract.DATE, "1999-08-13");
		b3.put(BookContract.PAGES,2000);
		b3.put(BookContract.DESCRIPTION,"《时间简史》是由英国伟大的物理学家史蒂芬·霍金撰写的一本有关宇宙学的著作，" +
				"是一部将高深的理论物理通俗化的科普范本。");

		ContentValues b4 = new ContentValues();
		b4.put(BookContract.TITLE, "大数据的力量");
		b4.put(BookContract.AUTHOR, "郭昕");
		b4.put(BookContract.CATAGORY_ID, 2);
		b4.put(BookContract.PRICE, 43.0);
		b4.put(BookContract.ART, getBookAbsolutePath("img_book_04.jpg"));
		b4.put(BookContract.DATE, "2008-05-07");
		b4.put(BookContract.PAGES,357);
		b4.put(BookContract.DESCRIPTION,"联系当前大数据的发展现状，对照微软、IBM、GE等世界500强企业对大数据的应用，" +
				"从应用大数据的行业、大数据人才争夺战、大数据应用关键技术、中国大数据的发展前景等角度，立体解读了大" +
				"数据对我们工作、生活和学习方式的改进，以及大数据在企业发展转型过程中的应用。\r\n《大数据的力量》专注" +
				"于用案例来分析企业在大数据应用环节对数据的理解、收集、应用以及如何实现数据价值最大化，使读者能够亲身体" +
				"验领军者如何把握机遇，同时又深入细节，捕捉领军者应用大数据的具体方式、方法。");

		ContentValues b5 = new ContentValues();
		b5.put(BookContract.TITLE, "国富论");
		b5.put(BookContract.AUTHOR, "亚当.斯密");
		b5.put(BookContract.CATAGORY_ID, 1);
		b5.put(BookContract.PRICE, 43.0);
		b5.put(BookContract.ART, getBookAbsolutePath("img_book_05.jpg"));
		b5.put(BookContract.DATE, "1780-05-18");
		b5.put(BookContract.PAGES,625);
		b5.put(BookContract.DESCRIPTION,"《国民财富的性质和原因的研究（上卷）》总结了近代初期各国资本主义发展的经验，" +
				"并在批判吸收了当时有关重要经济理论的基础上，就整个国民经济运动过程作了较系统、较明白的描述。" +
				"《国民财富的性质和原因的研究（上卷）》出版以后，不但对于英国资本主义的发展，直接产生了重大的促进作用，" +
				"而且对世界资本主义的发展来说，恐怕也没有过任何其他一部资产阶级的经济学著作，曾产生那么广泛的影响。" +
				"无怪当时有些资产阶级学者把它奉为至宝。可是，历史很快就把它的局限性和缺点错误显示出来了。");

		ContentValues b6 = new ContentValues();
		b6.put(BookContract.TITLE, "恋爱中的女人");
		b6.put(BookContract.AUTHOR, "劳伦斯");
		b6.put(BookContract.CATAGORY_ID, 3);
		b6.put(BookContract.PRICE, 27.0);
		b6.put(BookContract.ART, getBookAbsolutePath("img_book_06.jpg"));
		b6.put(BookContract.DATE, "2010-09-18");
		b6.put(BookContract.PAGES,125);
		b6.put(BookContract.DESCRIPTION,"《恋爱中的女人》，是D．H .劳伦斯最伟大、最有代表性、最脍炙人口的两部长篇" +
				"小说之一（另一部是《虹》），他本人也认为它是他的“最佳作品”；它以英国小说中没有先例的热情与深度探索了" +
				"有关恋爱的心理问题，代表了劳伦斯作品的最高成就，因此它同《虹》成为了现代小说的先驱。");

		ContentValues b7 = new ContentValues();
		b7.put(BookContract.TITLE, "跟着君之做饼干");
		b7.put(BookContract.AUTHOR, "君之");
		b7.put(BookContract.CATAGORY_ID, 3);
		b7.put(BookContract.PRICE, 18.0);
		b7.put(BookContract.ART, getBookAbsolutePath("img_book_07.jpg"));
		b7.put(BookContract.DATE, "2012-09-18");
		b7.put(BookContract.PAGES,173);
		b7.put(BookContract.DESCRIPTION,"本书共七章，介绍了六十多款上手容易的饼干品种：冷冻整形饼干、擀制饼干、" +
				"块状饼干、挤制饼干、滴落饼干、手工整形及模具整形饼干。即使0技巧、0经验，也能做出0失败完美饼干。" +
				"超详细步骤图，最实用tips，带你感受最简单，却最完美、最成功的幸福饼干滋味。");

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
