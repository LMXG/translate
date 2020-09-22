package mhy.dict.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mhy.dict.adapter.translateAdapter;
import mhy.dict.domain.Lishi;
import mhy.dict.domain.WordItem;
import mhy.dict.service.DBManager;
import mhy.dict.service.LishiService;
import mhy.dict.service.WordService;
import mhy.dict.tools.LanguageAnalysisTools;

public class SelectActivity extends Activity implements OnClickListener, TextWatcher {
    private AutoCompleteTextView selectWord;
    private ImageButton btn;
    static ImageView imgbt;
    static DBManager dbManager;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    static Cursor cur;
    MyApplication myApp;

    LishiService sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectword);
        myApp = MyApplication.getInstance(); //
        sc = new LishiService(SelectActivity.this);
        initComponent();
    }

    private void initComponent() {
        selectWord = (AutoCompleteTextView) findViewById(R.id.input_act);
        btn = (ImageButton) findViewById(R.id.select_btn);
        tv1 = (TextView) findViewById(R.id.select_word);
        tv2 = (TextView) findViewById(R.id.select_word_mean);
        tv3 = (TextView) findViewById(R.id.show);
        imgbt = (ImageView) findViewById(R.id.add_note);
        selectWord.addTextChangedListener(this);
        selectWord.setThreshold(1);
        btn.setOnClickListener(this);
        imgbt.setOnClickListener(this);
    }


    @Override
    public void afterTextChanged(Editable s) {
        dbManager = new DBManager(this);
        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        tv3.setVisibility(View.INVISIBLE);
        imgbt.setVisibility(View.INVISIBLE);
        if (s.length() > 0) {
            int language = LanguageAnalysisTools.getLanguage(s.toString());
            if (language == 0) {
                String sql = "select chinese as _id from t_words where chinese like ?";
                cur = dbManager.fuzzySelect(sql, new String[]{s.toString() + "%"});
                if (cur.getCount() > 0) {
                    translateAdapter translateAdapter = new translateAdapter(this,
                            cur, true);
                    selectWord.setAdapter(translateAdapter);
                } else {
                    translateAdapter translateAdapter = new translateAdapter(this,
                            null, true);
                    selectWord.setAdapter(translateAdapter);
                }
            } else if (language == 1) {
                String sql = "select english as _id from t_words where english like ?";
                cur = dbManager.fuzzySelect(sql, new String[]{s.toString() + "%"});
                if (cur.getCount() > 0) {
                    translateAdapter translateAdapter = new translateAdapter(this,
                            cur, true);
                    selectWord.setAdapter(translateAdapter);
                } else {
                    translateAdapter translateAdapter = new translateAdapter(this,
                            null, true);
                    selectWord.setAdapter(translateAdapter);
                }
            }
        } else {
            translateAdapter translateAdapter = new translateAdapter(this,
                    null, true);
            selectWord.setAdapter(translateAdapter);
        }
        dbManager.closeDatabase();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_note:
                note();
                break;
            case R.id.select_btn:
                search();
                break;
            default:
                break;
        }

    }
    public void search() {
        cur.close();
        dbManager = new DBManager(this);
        if (!selectWord.getText().toString().equals("")) {
            String selword = selectWord.getText().toString();

            String sql = null;
            String result = "δ�ҵ��õ���.";
            if (selword != null) {
                if(!sc.exist(selword,String.valueOf(myApp.getUser().get_id())))
                {
                    Lishi scobj=new Lishi();
                    scobj.setUserid(String.valueOf(myApp.getUser().get_id()));
                    scobj.setWord_name(selword);
                    sc.save(scobj);
                    sc = new LishiService(SelectActivity.this);

                    LishiActivity.list=sc.getword(String.valueOf(myApp.getUser().get_id()));
                    sc.close();
                }

                int language = LanguageAnalysisTools.getLanguage(selword);
                if (language == 0) {
                    sql = "select english from t_words where chinese like ?";
                    Cursor cursor = dbManager.fuzzySelect(sql, new String[]{"%" + selword + "%"});
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        result = cursor.getString(cursor.getColumnIndex("english"));
                        tv1.setVisibility(View.VISIBLE);
                        tv2.setVisibility(View.VISIBLE);
                        tv3.setVisibility(View.VISIBLE);
                        imgbt.setVisibility(View.VISIBLE);
                        tv1.setText(selectWord.getText());
                        tv2.setText(result);
                        WordItem w = new WordItem(tv1.getText().toString(), tv2.getText().toString(), String.valueOf(myApp.getUser().get_id()));
                        WordService ws = new WordService(SelectActivity.this);

                        if (!ws.isExist(w)) {
                            imgbt.setImageResource(R.drawable.add_note);
                        } else {
                            imgbt.setImageResource(R.drawable.del_note);
                        }
                        ws.close();
                    } else {
                        translateAdapter translateAdapter = new translateAdapter(this,
                                null, true);
                        selectWord.setAdapter(translateAdapter);
                        Toast.makeText(SelectActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                } else if (language == 1) {
                    sql = "select chinese from t_words where english = ?";
                    Cursor cursor = dbManager.select(sql, new String[]{selword});
                    //
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        result = cursor.getString(cursor.getColumnIndex("chinese"));
                        tv1.setVisibility(View.VISIBLE);
                        tv2.setVisibility(View.VISIBLE);
                        tv3.setVisibility(View.VISIBLE);
                        imgbt.setVisibility(View.VISIBLE);
                        tv2.setText(result);
                        tv1.setText(selectWord.getText());
                    } else {
                        translateAdapter translateAdapter = new translateAdapter(this,
                                null, true);
                        selectWord.setAdapter(translateAdapter);
                        Toast.makeText(SelectActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
            }
        } else {
            translateAdapter translateAdapter = new translateAdapter(this,
                    null, true);
            selectWord.setAdapter(translateAdapter);
            Toast.makeText(SelectActivity.this, "�����뵥�ʻ����", Toast.LENGTH_SHORT).show();
        }
        dbManager.closeDatabase();
    }

    public void note() {
        WordItem w = new WordItem(tv1.getText().toString(), tv2.getText().toString(), String.valueOf(myApp.getUser().get_id()));
        WordService ws = new WordService(SelectActivity.this);
        if (!ws.isExist(w)) {
            ws.save(w);
            ws.close();
            Toast.makeText(SelectActivity.this, "���������ʱ��ɹ�!", Toast.LENGTH_SHORT).show();
            ws = new WordService(SelectActivity.this);
            NotebookActivity.list = ws.getword(String.valueOf(myApp.getUser().get_id()));
            ws.close();
            imgbt.setImageResource(R.drawable.del_note);
        } else {
            ws = new WordService(SelectActivity.this);
            ws.delete(w);
            ws.close();
            ws = new WordService(SelectActivity.this);
            Toast.makeText(SelectActivity.this, "�����ʱ�ɾ���ɹ�!", Toast.LENGTH_SHORT).show();
            NotebookActivity.list = ws.getword(String.valueOf(myApp.getUser().get_id()));
            ws.close();
            imgbt.setImageResource(R.drawable.add_note);
        }
    }
}
