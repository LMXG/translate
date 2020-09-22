package mhy.dict.activity;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import java.util.List;

import mhy.dict.adapter.WordItemAdapter;
import mhy.dict.domain.WordItem;
import mhy.dict.service.WordService;

public class MainActivity extends TabActivity {

	MyApplication myApp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost th = getTabHost();
		myApp = MyApplication.getInstance();
		LayoutInflater.from(this).inflate(R.layout.dict, th.getTabContentView(),true);
		th.addTab(th.newTabSpec("translate")
				.setIndicator("Ӣ����",getResources().getDrawable(R.drawable.dictab))
				.setContent(new Intent(MainActivity.this,SelectActivity.class)));
		th.addTab(th.newTabSpec("translate")
				.setIndicator("Ӣ����",getResources().getDrawable(R.drawable.dictab))
				.setContent(new Intent(MainActivity.this,SelectActivity.class)));
		th.addTab(th.newTabSpec("notebook")
				.setIndicator("���ʱ�",getResources().getDrawable(R.drawable.notetab))
				.setContent(new Intent(MainActivity.this,NotebookActivity.class)));
		th.addTab(th.newTabSpec("lishi")
				.setIndicator("������ʷ",getResources().getDrawable(R.drawable.notetab))
				.setContent(new Intent(MainActivity.this,LishiActivity.class)));
		th.setOnTabChangedListener(changeTab);
	}
	private OnTabChangeListener changeTab = new OnTabChangeListener() {
		@Override
		public void onTabChanged(String tabId) {
			if(tabId.equals("notebook")){
				WordService wordService = new WordService(MainActivity.this);
				List<WordItem> list = wordService.getword(String.valueOf(myApp.getUser().get_id()));
				wordService.close();
				WordItemAdapter adapter = new WordItemAdapter(list, MainActivity.this);
				NotebookActivity.listView.setAdapter(adapter);
			}
		}
	};
}