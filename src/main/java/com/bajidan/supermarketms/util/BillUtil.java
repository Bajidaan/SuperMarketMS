package com.bajidan.supermarketms.util;

import java.util.Date;

public class BillUtil {
    public static String getUUID() {
        Date date = new Date();
        long time = date.getTime();
        return "BILL-" + time;
    }

}
