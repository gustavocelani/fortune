package com.gcelani.fortune.utils;

/**
 * Utils
 */
public class Utils {

    /**
     * parseMoneyStringToDouble
     * @param moneyString moneyString Ex.: R$ 183,52
     * @return Double value Ex.: 183,52
     */
    public static double parseMoneyStringToDouble(String moneyString) {
        double parsed = 0;
        String cleanString = moneyString.replaceAll("[^\\d]", "");

        try {
            parsed = (Double.parseDouble(cleanString) / 100);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        return parsed;
    }
}
