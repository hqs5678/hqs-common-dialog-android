package com.hqs.common.hqs_dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hqs.common.helper.dialog.QDialog;
import com.hqs.common.utils.ActivityUtil;
import com.hqs.common.utils.DensityUtils;
import com.hqs.common.utils.StatusBarUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private QDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityUtil.setActivityFullScreen(this);
        ActivityUtil.hideActionBar(this);
        setContentView(R.layout.activity_main);
        StatusBarUtil.transparencyBar(this);

        final ArrayList<String> titles = new ArrayList<String>();

        titles.add("dialog0 single button");
        titles.add("dialog1 onclick listener");
        titles.add("dialog2 cancel ok");


        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new BaseAdapter() {


            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public Object getItem(int position) {
                return titles.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                RelativeLayout relativeLayout;
                TextView tv;
                if (convertView == null && convertView instanceof RelativeLayout == false) {
                    relativeLayout = new RelativeLayout(MainActivity.this);
                    tv = new TextView(MainActivity.this);


                    int margin = DensityUtils.dp2px(MainActivity.this, 20);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    layoutParams.topMargin = margin;
                    layoutParams.bottomMargin = margin;
                    tv.setLayoutParams(layoutParams);

                    relativeLayout.addView(tv);
                } else {
                    relativeLayout = (RelativeLayout) convertView;
                    tv = (TextView) relativeLayout.getChildAt(0);
                }

                tv.setText(titles.get(position));

                return relativeLayout;
            }
        });





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        dialog = QDialog.create(MainActivity.this)
                                .setSingleButtonMode()
                                .setSingleButtonText("我知道了")
                                .show("我是来打酱油打酱油的是来打酱油的是来打酱油的我是来打酱的是来打酱油的是来打酱油的我是来打酱油的!!!", new QDialog.OnDialogClickListener() {
                            @Override
                            public void onClickRightButton() {
                                makeToast("ok");
                            }

                            @Override
                            public void onClickLeftButton() {
                                makeToast("cancel");
                            }

                            @Override
                            public void onCancel() {

                            }
                        });

                        break;


                    case 1:
                        dialog = QDialog.create(MainActivity.this)
                                .setRightButtonTextColor(Color.GREEN)
                                .setMessageTextColor(Color.BLACK)
                                .setRightButtonText("OK")
                                .setLeftButtonTextColor(Color.RED)
                                .setLeftButtonText("Cancel")
                                .setBackgroundColor(Color.parseColor("#AB282828"))
                                .setCancelable(true)
                                .show("我是来打酱油打酱油的是来打酱油的是来打酱油的我是来打酱的是来打酱油的是来打酱油的我是来打酱油的!!!", new QDialog.OnDialogClickListener() {
                            @Override
                            public void onClickRightButton() {
                                makeToast("ok");
                            }

                            @Override
                            public void onClickLeftButton() {
                                makeToast("cancel");
                            }

                            @Override
                            public void onCancel() {

                            }
                        });

                        break;


                    case 2:
                        dialog = QDialog.create(MainActivity.this)
                                .setRightButtonTextColor(Color.BLUE)
                                .setRightButtonText("OK")
                                .setLeftButtonTextColor(Color.RED)
                                .setLeftButtonText("Cancel")
                                .setDividerHeight(4)
                                .setDividerColor(Color.YELLOW)
                                .setContentBackgroundColor(getResources().getColor(R.color.colorAccent))
                                .setCancelable(true)
                                .show("我是来打酱油打酱油的是来打酱油的是来打酱油的我是来打酱的是来打酱油的是来打酱油的我是来打酱油的!!!", new QDialog.OnDialogClickListener() {
                            @Override
                            public void onClickRightButton() {
                                makeToast("ok");
                            }

                            @Override
                            public void onClickLeftButton() {
                                makeToast("cancel");
                                
                            }

                            @Override
                            public void onCancel() {
                                
                            }
                        });

                        break;

                    default:
                }
            }
        });
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (dialog != null && !dialog.onBackPressed()){
            super.onBackPressed();
        }
    }
}
