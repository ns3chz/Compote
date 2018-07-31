package cn.hu.zc.compote.manage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

import cn.hu.zc.compote.R;
import cn.hu.zc.compote.content.Content;

public class SqlManager {
    public static final String HOME = "home";
    public static final String HOME_TABLE = "tb_" + HOME;
    public static final String KEY_HEAD_SEARCH = "key_head_search";

    /**
     * 首页搜索提示查询
     */
    public static Cursor queryHeadSearchText(Context context, String text) {
//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir() + HOME + ".db", null);
//        Cursor cursor = null;
//        String querySql = "select * from " + HOME_TABLE + " where " + KEY_HEAD_SEARCH + " like '%" + text + "%'";
//        try {
//            cursor = db.rawQuery(querySql, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            String createSql = "create table " + HOME_TABLE + " (_id integer primary key autoincrement," + KEY_HEAD_SEARCH + " varchar(100))";
//            db.execSQL(createSql);
//
//            String insertSql = "insert into " + HOME_TABLE + " values (null,?)";
//            for (int i = 0; i < Content.QueryTextArrays.length; i++) {
//                db.execSQL(insertSql, new String[]{Content.QueryTextArrays[i]});
//            }
//
//            cursor = db.rawQuery(querySql, null);
//        }
        return null;
    }

    /**
     * 首页head搜索提示adapter
     */
    public static SimpleCursorAdapter getHeadSimpleCursorAdapter(Context context, Cursor cursor) {
        return new SimpleCursorAdapter(context,
                R.layout.query_item_layout, cursor, new String[]{KEY_HEAD_SEARCH}, new int[]{R.id.tv_text});
    }
}
