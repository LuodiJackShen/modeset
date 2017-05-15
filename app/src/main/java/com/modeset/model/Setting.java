package com.modeset.model;

/**
 * Created by JackShen on 2016/5/9.
 */
public class Setting {
    /***
     * true -> summer  && false -> winter
     */
    private static boolean StateOfSeasonSwitch = true;

    /***
     * true -> vibrate && false -> silence
     */
    private static boolean StateOfModelSwitch = false;

    public static boolean getStateOfModelSwitch() {
        return StateOfModelSwitch;
    }

    public static void setStateOfModelSwitch(final boolean stateOfModelSwitch) {
        StateOfModelSwitch = stateOfModelSwitch;
    }

    public static boolean getStateOfSeasonSwitch() {
        return StateOfSeasonSwitch;
    }

    public static void setStateOfSeasonSwitch(final boolean stateOfSeasonSwitch) {
        StateOfSeasonSwitch = stateOfSeasonSwitch;
    }
}
