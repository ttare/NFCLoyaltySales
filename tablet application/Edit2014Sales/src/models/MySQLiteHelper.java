package models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "artikli.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_ARTIKLI = "artikli";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_BARKOD = "barkod";
	public static final String COLUMN_NAZIV = "naziv";
	public static final String COLUMN_CIJENA = "cijena";
	public static final String COLUMN_KOLICINA = "kolicina";
	
	public static final String DATABASE_CREATE = "create table " + TABLE_ARTIKLI + "( " 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_BARKOD + " integer not null, " 
			+ COLUMN_NAZIV + " varchar(50) not null, "
			+ COLUMN_CIJENA + " float not null, "
			+ COLUMN_KOLICINA + " integer not null);";
				
	
	
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXIST " + TABLE_ARTIKLI);
		onCreate(db);	
	}

}
