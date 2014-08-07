package models;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Columns;

public class ArtikliDAO {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { dbHelper.COLUMN_ID, dbHelper.COLUMN_BARKOD, 
			dbHelper.COLUMN_NAZIV, dbHelper.COLUMN_CIJENA, dbHelper.COLUMN_KOLICINA };
	
	
	public ArtikliDAO(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		database.close();
	}
		
	public Artikal izKursoraUArtikal (Cursor cursor) {
		Artikal artikal = new Artikal();
		artikal.setId(cursor.getLong(0));
		artikal.setBarkod(cursor.getInt(1));
		artikal.setNaziv(cursor.getString(2));
		artikal.setCijena(cursor.getFloat(3));
		artikal.setKolicina(cursor.getInt(4));
		return artikal;
	}
	
	public void promijeniKolicinu(long id, int kol) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_KOLICINA, kol);
		database.update(dbHelper.TABLE_ARTIKLI, values, dbHelper.COLUMN_ID + "=" + id, null);
	}
	
	public Artikal kreirajArtikal(int barkod, String naziv, float cijena, int kolicina) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_BARKOD, barkod);
		values.put(dbHelper.COLUMN_NAZIV, naziv);
		values.put(dbHelper.COLUMN_CIJENA, cijena);
		values.put(dbHelper.COLUMN_KOLICINA, kolicina);
		long insertId = database.insert(dbHelper.TABLE_ARTIKLI, null, values);
		
		Cursor cursor = database.query(dbHelper.TABLE_ARTIKLI, allColumns, dbHelper.COLUMN_ID + " = "+ insertId, null, null, null, null);
		cursor.moveToFirst();
		Artikal artikal = izKursoraUArtikal(cursor);
		cursor.close();
		return artikal;
	}
	
	public void izmijeniArtikal(Artikal artikal)	{
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_BARKOD, artikal.getBarkod());
		values.put(dbHelper.COLUMN_NAZIV, artikal.getNaziv());
		values.put(dbHelper.COLUMN_CIJENA, artikal.getCijena());
		values.put(dbHelper.COLUMN_KOLICINA, artikal.getKolicina());
		database.update(dbHelper.TABLE_ARTIKLI, values, dbHelper.COLUMN_ID + " = " + artikal.getId(), null);
	}
	
	
	public void izbrisiArtikal(long id) {
		database.delete(dbHelper.TABLE_ARTIKLI, dbHelper.COLUMN_ID + " = " + id , null);
	}
	
	public List<Artikal> uzmiListuSvihArtikala() {
		List<Artikal>artikli = new ArrayList<Artikal>();
		Cursor cursor = database.query(dbHelper.TABLE_ARTIKLI, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			artikli.add(izKursoraUArtikal(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		return artikli;
	}
	
	public Artikal uzmiArtikalSaId(long id)	{
		Cursor cursor = database.query(dbHelper.TABLE_ARTIKLI, allColumns, dbHelper.COLUMN_ID + " = " + id, null, null, null, null);
		Artikal artikal = izKursoraUArtikal(cursor);
		cursor.close();
		return artikal;
	}
	
}
	
	
	
	
	
