package inducesmile.com.CareerPundit.temp;

import android.provider.BaseColumns;

/**
 * Created by test on 12-03-2016.
 */
public class TableData {
    public TableData()  {

    }

    public static abstract class Tableinfo implements BaseColumns
    {
        public static final String EMAIL="email";
        public static final String PWD="user_pass";
        public static final String DOB="dob";
        public static final String NAME="name";
        public static final String DB_NAME="user_info";
        public static final String TABLE_NAME="reg_info";


    }
}
