package mhy.dict.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mhy.dict.activity.R;

//AutoCompleteTextView��ʹ�õ�Adapter
public class translateAdapter extends CursorAdapter
{
	private LayoutInflater layoutInflater;
	@Override
	//��cursorת����CharSequence,����һ��������ַ�����
	public CharSequence convertToString(Cursor cursor)
	{
		//����ָ������
		//��Android ��ѯ������ͨ��Cursor ����ʵ�ֵġ�������ʹ�� SQLiteDatabase.query()����ʱ���ͻ�õ�Cursor���� Cursor��ָ��ľ���ÿһ������
		return cursor == null ? "" : cursor.getString(cursor.getColumnIndex("_id"));
	}
	private void setView(View view, Cursor cursor)
	{
		TextView tvWordItem = (TextView) view;
		tvWordItem.setText(cursor.getString(cursor.getColumnIndex("_id")));
	}
    //����һ�����е�view��ʹ����ʾ��ǰcursor��ָ������ݡ�����ǰcursor��Ϊview��Text���� 
	@Override
	public void bindView(View view, Context context, Cursor cursor)     // view���Ѵ��ڵ���ͼ, ����֮ǰnewView������������ͼ��
	{
		setView(view, cursor);
	}
    
    //�½�һ����ͼ������cursorָ�������
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)    //Context��Ӧ�ó���ȫ����Ϣ�ӿ�
	{
		View view = layoutInflater.inflate(R.layout.word_list_item, null);
		setView(view, cursor);
		return view;
	}
	public translateAdapter(Context context, Cursor c, boolean autoRequery)
	{
		super(context, c, autoRequery);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);    //ȡ��xml�ﶨ���view
	}
}