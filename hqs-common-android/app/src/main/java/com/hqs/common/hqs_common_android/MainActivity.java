package com.hqs.common.hqs_common_android;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hqs.common.utils.DensityUtils;
import com.hqs.common.view.QDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private QDialog dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> titles = new ArrayList<String>();

        titles.add("dialog0");
        titles.add("dialog1 onclick listener");
        titles.add("dialog2 cancel ok");
        titles.add("dialog3 auto cancel");


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

                        dialogView = new QDialog(MainActivity.this);
                        dialogView.setSingleButtonMode()
                                .setRightButtonText("OK")
                                .setRightButtonTextColor(Color.BLUE)
                                .setCancelable(true);

                        dialogView.show("hello this is message!!!!", new QDialog.OnDialogClickListener() {
                            @Override
                            public void onClickRightButton() {
                                makeToast("ok");
                                dialogView.release();
                            }

                            @Override
                            public void onClickLeftButton() {
                                makeToast("cancel");
                                dialogView.release();
                            }

                            @Override
                            public void onCancel() {
                                dialogView.release();
                            }
                        });

                        break;


                    case 1:
                        dialogView = new QDialog(MainActivity.this);

                        dialogView.setRightButtonTextColor(Color.BLUE)
                                .setRightButtonText("OK")
                                .setLeftButtonTextColor(Color.RED)
                                .setLeftButtonText("Cancel")
                                .setCancelable(true);
                        dialogView.show("hello this is message!!!!", new QDialog.OnDialogClickListener() {
                            @Override
                            public void onClickRightButton() {
                                makeToast("ok");
                                dialogView.release();
                            }

                            @Override
                            public void onClickLeftButton() {
                                makeToast("cancel");
                                dialogView.release();
                            }

                            @Override
                            public void onCancel() {
                                dialogView.release();
                            }
                        });

                        break;


                    case 2:
                        dialogView = new QDialog(MainActivity.this);

                        dialogView.setRightButtonTextColor(Color.BLUE)
                                .setRightButtonText("OK")
                                .setLeftButtonTextColor(Color.RED)
                                .setLeftButtonText("Cancel")
                                .setCancelable(true)
                                .setDividerHeight(4)
                                .setDividerColor(Color.YELLOW)
                                .setBackgroundRes(R.mipmap.ic_launcher)
                                .setCancelable(true);

                        dialogView.show("hello this is message!!!!", new QDialog.OnDialogClickListener() {
                            @Override
                            public void onClickRightButton() {
                                makeToast("ok");
                                dialogView.release();
                            }

                            @Override
                            public void onClickLeftButton() {
                                makeToast("cancel");
                                dialogView.release();
                            }

                            @Override
                            public void onCancel() {
                                dialogView.release();
                            }
                        });

                        break;

                    case 3:
                        dialogView = new QDialog(MainActivity.this);

                        dialogView.setRightButtonTextColor(Color.BLUE)
                                .setRightButtonText("OK")
                                .setLeftButtonTextColor(Color.RED)
                                .setLeftButtonText("Cancel")
                                .setCancelable(true)
                                .setDividerHeight(4)
                                .setCancelable(true);

                        dialogView.show("hello this is message!!!!", new QDialog.OnDialogClickListener() {
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

//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialogView.dismiss();
//                            }
//                        }, 1000);

                        break;


                    default:
                }
            }
        });
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
