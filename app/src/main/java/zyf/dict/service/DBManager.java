package mhy.dict.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import mhy.dict.activity.R;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

//������һ�����е����ݿ�
public class DBManager {
	private final int BUFFER_SIZE = 400000;    
	public static final String DB_NAME = "translate.db"; //��������ݿ��ļ���  
	public static final String PACKAGE_NAME = "mhy.dict.activity";    
	public static final String DB_PATH = "/data"+ Environment.getDataDirectory().getAbsolutePath() + "/"+ PACKAGE_NAME;  //���ֻ��������ݿ��λ��   
	private Context context; 
	private SQLiteDatabase database;
    public DBManager(Context context) {        
    	this.context = context;
    	open();
    }     
    public void open() {        
    	try
		{
			// ��translatey.db�ļ��ľ���·��
  			String databaseFilename = DB_PATH + "/" + DB_NAME;
			File dir = new File(DB_PATH);
			// ���/sdtranslateonaryĿ¼�д��ڣ��������Ŀ¼
			if (!dir.exists())
				dir.mkdir();
			// �����translatectionaryĿ¼�translatetionary.db�ļ������res\rawĿ¼�и�������translate�Ŀ¼��/sdcard/dictionary��
			if (!(new File(databaseFilename)).exists())
			{
				// ��÷translatery.db�ļ���InputStream����
				InputStream is = this.context.getResources().openRawResource(
						R.raw.translate);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				// ��ʼtranslatenary.db�ļ�
				while ((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			}
			// ��/sdcartranslateryĿ¼�translatery.db�ļ�
			database = SQLiteDatabase.openOrCreateDatabase(
					databaseFilename, null);
		}
		catch (Exception e)
		{
		}
    }     
    //��ѯ
    public Cursor select(String sql,String[] condition){
		return database.rawQuery(sql, condition);
    }
    //ģ����ѯ
    public Cursor fuzzySelect(String sql,String[] condition){
    	return database.rawQuery(sql, condition);
    }
    public void closeDatabase() {        
    	database.close();    
    }
}
