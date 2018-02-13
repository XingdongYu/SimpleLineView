package com.robog.library;

import java.util.List;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public class PixelUtil {

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
                if (!close) break;
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
