package mhy.dict.activity;

import java.util.ArrayList;
import java.util.List;
import mhy.dict.adapter.WordItemAdapter;
import mhy.dict.domain.WordItem;
import mhy.dict.service.WordService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class NotebookActivity extends Activity {
	static ListView listView;
	private TextView textview;
	private WordService wordService;
	static List<WordItem> list;
	AlertDialog.Builder builder;

	MyApplication myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notebook);
		myApp = MyApplication.getInstance();
		initComponent();
	}

	private void initComponent(){
		textview = (TextView) findViewById(R.id.text_tv);
		listView = (ListView)findViewById(R.id.list);

		wordService = new WordService(this);
		list = wordService.getword(String.valueOf(myApp.getUser().get_id()));
		wordService.close();
		WordItemAdapter adapter = new WordItemAdapter(list, this);
		listView.setAdapter(adapter);
		builder = new AlertDialog.Builder(NotebookActivity.this);
		listView.setOnItemLongClickListener(itemlongclick);
		listView.setOnItemClickListener(itemclick);
	}
	private OnItemLongClickListener itemlongclick = new OnItemLongClickListener(){
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
			final int temp = arg2;
			builder.setMessage("�Ƿ�ȷ��ɾ��").setPositiveButton("��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					wordService = new WordService(NotebookActivity.this);
					wordService.delete(list.get(temp));
					wordService.close();
					wordService = new WordService(NotebookActivity.this);
					List<WordItem> list = wordService.getword(String.valueOf(myApp.getUser().get_id()));
					wordService.close();
					WordItemAdapter adapter = new WordItemAdapter(list, NotebookActivity.this);
					listView.setAdapter(adapter);
					SelectActivity.imgbt.setImageResource(R.drawable.add_note);
				}
			}).setNegativeButton("��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			AlertDialog ag = builder.create();
			ag.show();
			return true;
		}
	};
	private OnItemClickListener itemclick = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Intent intent = new Intent(NotebookActivity.this,ShowItemwordActivity.class);
			Bundle mBundle = new Bundle();
			ArrayList<WordItem> mlist = new ArrayList<WordItem>();
			for(WordItem item:list){
				mlist.add(item);
			}
			mBundle.putParcelableArrayList("worditems", mlist);
			intent.putExtra("list", mBundle);
			intent.putExtra("key", arg2+"");
			intent.setComponent(new ComponentName(NotebookActivity.this,ShowItemwordActivity.class));
			startActivity(intent);
		}
	};

}
