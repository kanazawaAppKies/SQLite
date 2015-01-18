package com.example.sqlite_part2;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView varListView;
	public static final String NAMELIST = "INSERT INTO namelist(name,latitude,longitude,genre,open,close,rest) VALUES ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout varLinearLayout = new LinearLayout(this);
        varLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        setContentView(varLinearLayout);

        ListView varListView = new ListView(this);

        // データ名と保存場所を登録
        String dbName = "data/data/" + getPackageName() + "/myDatabase1.db";
        // データベースオブジェクトを生成
        SQLiteDatabase dbObject = SQLiteDatabase.openOrCreateDatabase(dbName, null);

        // 古いテーブルを破棄するSQL文を登録
        String dropTable = "DROP TABLE IF EXISTS namelist";
        // 3つのカラムを持つテーブルを作成するSQL文を登録
        String createTable = "CREATE TABLE namelist" + "(id INTEGER PRIMARY KEY, name STRING, latitude NUMERIC, longitude NUMERIC, genre INTEGER, open INTEGER, close INTEGER, rest INTEGER)";

        // データを追加するSQL文を登録
        String[] insertData ={
        		NAMELIST+"('海鮮どん屋',36.570923,136.648972,0,1100,2100,10001000)",
        		NAMELIST+"('廻る近江町市場寿し',36.571633,136.656835,0,930,2000,10000000)",
        		NAMELIST+"('近江町海鮮丼家ひら井本店',36.570985,136.656721,0,1100,2130,10001000)",
        		NAMELIST+"('まめや金澤萬久',36.571488,136.655877,0,0,0,10000000)",
        		NAMELIST+"('いきいき亭',36.571488,136.655877,0,700,1500,10000100)",
        		NAMELIST+"('かいてん寿し大倉',36.57121,136.655513,0,1030,1900,10000000)",
        		NAMELIST+"('串揚げひかりや近江町店',36.571682,136.656791,0,1100,0,10100000)",
        		NAMELIST+"('大友屋',36.571285,136.657308,0,1100,2200,11000000)",
        		NAMELIST+"('もりもり寿し近江町店',36.571488,136.655877,0,1000,2100,10000000)",
        		NAMELIST+"('百万石うどん 近江町市場本店',36.571346,136.65676,0,800,1700,11000000)",
        		NAMELIST+"('近江町食堂',36.571099,136.655827,0,1030,2200,10000000)",
        		NAMELIST+"('近江町市場寿し 上近江町店',36.570967,136.656465,0,930,2000,10000000)",
        		NAMELIST+"('くずの花',36.570794,136.656588,0,900,1730,10000000)",
        		NAMELIST+"('刺身屋',36.571166,136.656002,0,1100,2100,10001000)",
        		NAMELIST+"('山さん寿司本店',36.571729,136.656927,0,730,1900,10000000)",
        		NAMELIST+"('鮨処 源平',36.571027,136.656966,0,1100,2100,11000000)"
        };

        // 検索を行うSQL文の登録（０の部分を任意のポイント数に書き換える）
        String query = "SELECT * FROM namelist WHERE genre >= 0";

        // 古いテーブルを破棄
        dbObject.execSQL(dropTable);
        // テーブルを作成
        dbObject.execSQL(createTable);

        // データを追加
        for(int i=0; i<insertData.length; i++){

        		dbObject.execSQL(insertData[i]);


        }
        // ①データへアクセスするためのカーソルオブジェクトを生成してクエリーを実行
        Cursor cursor = dbObject.rawQuery(query, null);

        // ②ArrayAdapterのインスタンスを生成
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        while(cursor.moveToNext()){
        	
        	// ③各カラムのidを取得
        	int idId = cursor.getColumnIndex("id");
        	int idName = cursor.getColumnIndex("name");
        	int idLatitude = cursor.getColumnIndex("latitude");
        	int idLongitude = cursor.getColumnIndex("longitude");
        	int idOpen = cursor.getColumnIndex("open");
        	int idClose = cursor.getColumnIndex("close");

        	int id = cursor.getInt(idId);
        	String name = cursor.getString(idName);
        	double latitude = cursor.getDouble(idLatitude);
        	double longitude = cursor.getDouble(idLongitude);
        	int open = cursor.getInt(idOpen);
        	int close = cursor.getInt(idClose);


        	String row = id + "   " + name+"\n"
        				+latitude+"\t"+longitude+"\n"
        				+"open "+open/100+":"+open%100+ "   "+"close "+close/100+":"+close%100+"\n";

        	ad.add(row);

        }

        varListView.setAdapter(ad);

        varLinearLayout.addView(varListView);

        // データベースオブジェクトをクローズ
        dbObject.close();

    }

}
