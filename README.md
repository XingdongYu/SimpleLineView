## SimpleLineView

可以定制一些简单的路径，按想要的绘制顺序添加，SimpleLineView会依次展现路径动画。

这里简单地借鉴了OkHttp的责任链模式，整体架构如下图：
![image](https://github.com/XingdongYu/SimpleLineView/blob/master/pic/diagram.png)

效果图
---
![image](https://github.com/XingdongYu/SimpleLineView/blob/master/pic/sample.gif)

添加路径
---
```
PixelShape cubeShape = new PixelShape(2, 2, new int[]{1, 2, 4, 3});
Painter cubePainter = new SegmentPainter(cubeShape, 1000, true);
mView.addPainter(cubePainter);
//启动
mView.start();
//停止
mView.stop();
//继续
mView.stick();
```

关于路径点
---
以4 * 4的表格为例

|  1 |  2 |  3 |  4 |
| :-:| :-:| :-:| :-:|
|  5 |  6 |  7 |  8 |
|  9 | 10 | 11 | 12 |
| 13 | 14 | 15 | 16 |

如果path为{1, 13, 16, 4}, 则绘制的图形为依次连接1，13，16，4的矩形(是否封闭可设置对应参数)
