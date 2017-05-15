package com.modeset.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by JackShen on 2016/5/21.
 */
public class GregorianCalendarSets {
    private static List<GregorianCalendar> morning = new ArrayList<>();
    private static List<GregorianCalendar> summerAfternoon = new ArrayList<>();
    private static List<GregorianCalendar> winterAfternoon = new ArrayList<>();

    static {
        /***
         * to initialize the morning set
         */
        morning.add(getGregorianCalendar(7, 50, 0));
        morning.add(getGregorianCalendar(8, 50, 0));
        morning.add(getGregorianCalendar(9, 0, 0));
        morning.add(getGregorianCalendar(9, 50, 0));

        morning.add(getGregorianCalendar(10, 10, 0));
        morning.add(getGregorianCalendar(11, 0, 0));
        morning.add(getGregorianCalendar(11, 10, 0));
        morning.add(getGregorianCalendar(12, 0, 0));

        /***
         * to initialize the summerAfternoon set
         */
        summerAfternoon.add(getGregorianCalendar(14, 30, 0));
        summerAfternoon.add(getGregorianCalendar(15, 20, 0));
        summerAfternoon.add(getGregorianCalendar(15, 30, 0));
        summerAfternoon.add(getGregorianCalendar(16, 20, 0));

        //for test
//        summerAfternoon.add(getGregorianCalendar(17, 22, 0));

        summerAfternoon.add(getGregorianCalendar(16, 40, 0));
        summerAfternoon.add(getGregorianCalendar(17, 30, 0));
        summerAfternoon.add(getGregorianCalendar(17, 40, 0));
        summerAfternoon.add(getGregorianCalendar(18, 30, 0));

        summerAfternoon.add(getGregorianCalendar(19, 30, 0));
        summerAfternoon.add(getGregorianCalendar(20, 20, 0));
        summerAfternoon.add(getGregorianCalendar(20, 30, 0));
        summerAfternoon.add(getGregorianCalendar(21, 20, 0));

        /***
         * to initialize the winterAfternoon set
         */
        winterAfternoon.add(getGregorianCalendar(14, 0, 0));
        winterAfternoon.add(getGregorianCalendar(14, 50, 0));
        winterAfternoon.add(getGregorianCalendar(15, 0, 0));
        winterAfternoon.add(getGregorianCalendar(15, 50, 0));

        winterAfternoon.add(getGregorianCalendar(16, 10, 0));
        winterAfternoon.add(getGregorianCalendar(17, 0, 0));
        winterAfternoon.add(getGregorianCalendar(17, 10, 0));
        winterAfternoon.add(getGregorianCalendar(18, 0, 0));

        winterAfternoon.add(getGregorianCalendar(19, 0, 0));
        winterAfternoon.add(getGregorianCalendar(19, 50, 0));
        winterAfternoon.add(getGregorianCalendar(20, 0, 0));
        winterAfternoon.add(getGregorianCalendar(20, 50, 0));
    }

    public static GregorianCalendar getGregorianCalendar(
            int hourOfDay, int minute, int second) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.HOUR_OF_DAY, hourOfDay);
        gc.set(Calendar.MINUTE, minute);
        gc.set(Calendar.SECOND, second);
        return gc;
    }

    public static GregorianCalendar getMorning(int index) {
        return morning.get(index);
    }

    public static GregorianCalendar getSummerAfternoon(int index) {
        return summerAfternoon.get(index);
    }

    public static GregorianCalendar getWinterAfternoon(int index) {
        return winterAfternoon.get(index);
    }
}
