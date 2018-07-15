## SimpleLineView

[介绍](https://xingdongyu.github.io/2018/07/15/simple-line-view/#more)

这里简单借鉴了OkHttp的责任链模式，整体架构图如下：
![image](https://github.com/XingdongYu/SimpleLineView/blob/master/art/diagram.png)

效果图
---
![image](https://github.com/XingdongYu/SimpleLineView/blob/master/art/sample.gif)
![image](https://github.com/XingdongYu/SimpleLineView/blob/master/art/sample1.gif)

使用
---
[![](https://jitpack.io/v/XingdongYu/SimpleLineView.svg)](https://jitpack.io/#XingdongYu/SimpleLineView)

#### Gradle
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.XingdongYu:SimpleLineView:v1.0.3'
}
```
#### Maven
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
    <groupId>com.github.XingdongYu</groupId>
    <artifactId>SimpleLineView</artifactId>
    <version>v1.0.0</version>
</dependency>
```

添加路径
---
```
// 圆形
PixelPath circlePath = new PixelPath(10, 10, new int[]{1, 100});
CirclePainter ciclePainter = new RealCirclePainter(circlePath, 1000, -120, 360, false);
// 矩形
PixelPath squarePath = new PixelPath(2, 2, new int[]{1, 2, 4, 3});
Painter squarePainter = new SegmentPainter(squarePath, 1000, true);

mView.addPainter(ciclePainter).addPainter(squarePainter);
// 启动
mView.start();
// 停止
mView.stop();
// 继续
mView.stick();
```

路径点
---
以4 * 4的表格为例

|  1 |  2 |  3 |  4 |
| :-:| :-:| :-:| :-:|
|  5 |  6 |  7 |  8 |
|  9 | 10 | 11 | 12 |
| 13 | 14 | 15 | 16 |

如果path为{1, 13, 16, 4}, 则绘制的图形为依次连接1，13，16，4的矩形(是否封闭可设置对应参数)。

如果图形的形状比较复杂，可以用PS打开图片，依次获取像素点的x和y值(这里x和y值的单位可以是像素、厘米等，但是计算时要与图像大小的单位一致)。假设图像宽为w, 高为h, 则当前点的值为 w * ( y - 1) + x。
