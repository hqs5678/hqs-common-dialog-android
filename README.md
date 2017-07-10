# hqs-dialog
简单封装对话框

#### 运行效果
![运行效果图]()


 
### 添加到项目

#### gradle

```

dependencies {
    compile 'com.hqs.common.view.dialog:qdialog:1.0.14'
}

```

### 使用说明

> 显示对话框并添加按钮监听
```
dialog = QDialog.create(MainActivity.this)
            .setSingleButtonMode()
            .setSingleButtonText("我知道了")
            .show("我是来打酱油打酱油的!!!", new QDialog.OnDialogClickListener() {
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
            // 只一个按钮时也会触发onCancel()
            makeToast("onCancel");
        }
    });
```

#### 设置返回按钮事件

> 在调用者的activity中覆盖onBackPressed(), 例如:
```
@Override
public void onBackPressed() {
    if (dialog != null && !dialog.onBackPressed()){
        // do your own things
        // ...
        super.onBackPressed();
    }
}
```





