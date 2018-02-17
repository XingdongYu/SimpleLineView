package com.robog.library;

import com.robog.library.painter.Painter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public final class Utils {

    /**
     * 设置点的实际坐标
     * @param painter       当前painter
     * @param pixelPoints   当前painter下所有点
     * @param width         View宽
     * @param height        View高
     */
    public static void setPoint(Painter painter, List<PixelPoint> pixelPoints, int width, int height) {

        final PixelShape shape = painter.getShape();
        final int[] path = shape.getPath();
        final int horizontal = shape.getHorizontal();
        final int vertical = shape.getVertical();

        for (int target : path) {

            if (target > horizontal * vertical) {
                throw new IllegalArgumentException("Current coordinate [" + target + "] is invalid!");
            }

            int quotient = target / horizontal;
            int remainder = target % horizontal;

            float x;
            float y;
            float coefficientX;
            float coefficientY;
            if (remainder != 0) {
                // 针对余数不为0时
                coefficientX = remainder - 0.5f;
                coefficientY = quotient + 0.5f;
            } else {
                // 余数为0时
                coefficientX = horizontal - 0.5f;
                coefficientY = quotient - 0.5f;
            }
            x = coefficientX *  width / horizontal;
            y = coefficientY *  height / vertical;

            PixelPoint pixelPoint = new PixelPoint(x, y);
            pixelPoints.add(pixelPoint);
        }
    }

    /**
     * 当{@link SimpleLineView}状态为STATUS_START时，
     * 会调用{@link PixelPoint#reset()}方法来重置所有PixelPoint状态
     * @param painterPool 缓存PixelPoint
     */
    public static void resetPointStatus(Map<Painter, List<PixelPoint>> painterPool) {
        for (Map.Entry<Painter, List<PixelPoint>> next : painterPool.entrySet()) {
            List<PixelPoint> pixelPoints = next.getValue();
            resetPoint(pixelPoints);
        }
    }

    public static void resetPoint(List<PixelPoint> pixelPoints) {
        for (PixelPoint point : pixelPoints) {
            point.reset();
        }
    }

    /**
     * 当前点与下一点连线占总长度的百分比
     *
     * @param distance  总长度
     * @param current   当前点
     * @param next      下一点
     * @return
     */
    public static float calFraction(float distance, PixelPoint current, PixelPoint next) {
        float dis = getDis(current, next);
        return dis / distance;
    }

    public static float calDistance(List<PixelPoint> pointList, boolean close) {

        float distance = 0;
        for (int i = 0; i < pointList.size(); i ++) {
            PixelPoint current = pointList.get(i);
            PixelPoint next;
            if (i < pointList.size() - 1) {
                next = pointList.get(i + 1);
            } else {
                if (!close) {
                    break;
                }
                next = pointList.get(0);
            }
            distance += getDis(current, next);
        }
        return distance;
    }

    public static float getDis(PixelPoint current, PixelPoint next) {
        float currentX = current.getStartX();
        float currentY = current.getStartY();

        float nextX = next.getStartX();
        float nextY = next.getStartY();

        return getDis(currentX, currentY, nextX, nextY);
    }

    public static float getDis(float x1, float y1, float x2, float y2) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
