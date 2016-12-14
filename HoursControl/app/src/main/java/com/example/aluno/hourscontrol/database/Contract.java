package com.example.aluno.hourscontrol.database;

import android.provider.BaseColumns;

/**
 * Created by aline on 12/1/2016.
 */

public class Contract
{
         /* TableUser */
    public static abstract class  TableUser implements BaseColumns
    {

        public static final String TABLE_NAME = "TableUSER";
        public static final String COLUMN_ID = "UserID";
        public static final String COLUMN_FULLNAME = "UserFULLNAME";
        public static final String COLUMN_USERNAME = "UserUSERNAME";
        public static final String COLUMN_PASSWORD = "UserPASSWORD";
        public static final String COLUMN_DAILY_HOURS = "UserDAILYHOUR";
        public static final String COLUMN_EMAIL = "UserEMAIL";
        /* End TableUser */
    }
        /* TableWorkDay */
    public static abstract class  TableWorkDay implements BaseColumns
    {
        public static final String TABLE_NAME = "TableWorkDay";
        public static final String COLUMN_ID = "WorkDayID";
        public static final String COLUMN_DATE_ENTRY = "DateEntry";
        public static final String COLUMN_DATE_BREAK = "DateBreak";
        public static final String COLUMN_DATE_BACK_BREAK = "DateBackBreak";
        public static final String COLUMN_DATE_OUT = "DateOut";
        public static final String COLUMN_BALANCE_OF_THE_DAY = "balanceOfTheDay";
        public static final String COLUMN_STATE_HOURS = "stateHours";
        public static final String COLUMN_USER_ID = "idUser";
        /* End TableWorkDay */
    }
}
