## CustomSwitch
自定义滑动开关控件，利用接口回调方式实现开关实现状态的监听。
添加了自定义属性的功能，可以在xml布局文件中自定义开关的滑块及开关背景

* 继承View对象
* 重写绘制和测量方法
* 重写触摸监听事件，根绝用户的触摸情况来更新开关的状态
* 通过接口回调的方法来通知使用者开关的状态
