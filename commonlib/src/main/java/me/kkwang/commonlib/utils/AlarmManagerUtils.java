/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.kkwang.commonlib.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by drakeet on 7/1/15.
 */
public class AlarmManagerUtils {

    /*
     * 设定时分秒做某件事
     * 可以配合开屏监听时注册
     *  if (intent != null && Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            AlarmManagerUtils.register(context);
        }
        ...
         <receiver android:name=".service.KeepAlarmLiveReceiver">
            <intent-filter>
                <action android:name="android.intent.action.USER_PRESENT"/>
            </intent-filter>
        </receiver>
     */
    public static void register(Context context, int hour, int minute, int second, Intent intent) {
        Calendar today = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, hour);
        today.set(Calendar.MINUTE, minute);
        today.set(Calendar.SECOND, second);

        if (now.after(today)) {
            return;
        }

        PendingIntent broadcast = PendingIntent.getBroadcast(context, 520, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        manager.set(AlarmManager.RTC_WAKEUP, today.getTimeInMillis(), broadcast);
    }
}
