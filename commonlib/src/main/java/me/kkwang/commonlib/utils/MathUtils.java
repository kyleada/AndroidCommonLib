/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.kkwang.commonlib.utils;

public class MathUtils {

    public static int clamp(int value, int min, int max) {
        return value < min ? min : value > max ? max : value;
    }

    public static float clamp(float value, float min, float max) {
        return value < min ? min : value > max ? max : value;
    }
}
