# hqs-dialog
简单封装对话框, 原理: 在当前activity的root view 中添加自定义view, 添加进入动画和谈出动画.

#### 运行效果
![运行效果图](https://github.com/hqs5678/hqs-common-dialog-android/blob/master/2017-07-10%2015_10_51.gif)



### 添加到项目

#### gradle

```

dependencies {
    compile 'com.hqs.common.view.dialog:qdialog:1.0.18'
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


##### 自定义对话框样式

1. setContentBackgroundColor  设置对话框颜色
1. setBackgroundColor 设置对话框背景颜色
1. setDividerHeight  设置分割线高度
1. setDividerColor 设置分割线颜色
1. setButtonRippleColor  设置按钮波纹颜色
1. setLeftButtonText  设置左边按钮的标题
1. setLeftButtonTextColor  设置左边按钮的字体颜色
1. setRightButtonText 设置右边按钮的标题
1. setRightButtonTextColor 设置右边按钮的字体颜色
1. setSingleButtonMode 设置一个按钮的模式
1. setSingleButtonText 设置一个按钮时按钮的标题
1. setSingleButtonTextColor 设置一个按钮时按钮的颜色

###### 详情请查看Demo
