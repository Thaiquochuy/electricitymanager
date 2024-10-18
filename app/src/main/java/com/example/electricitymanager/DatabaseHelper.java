package com.example.electricitymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "electricBillManager.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_ELEC_USER_TYPE = "electric_user_type";

    // Common column
    public static final String COLUMN_ID = "id";

    // Customer table columns
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_YYYYMM = "yyyymm";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_USED_NUM_ELECTRIC = "used_num_electric";
    public static final String COLUMN_ELEC_USER_TYPE_ID = "elec_user_type_id";

    // Electric User Type table columns
    public static final String COLUMN_ELEC_USER_TYPE_NAME = "elec_user_type_name";
    public static final String COLUMN_UNIT_PRICE = "unit_price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create electric_user_type table
        String CREATE_ELECTRIC_USER_TYPE_TABLE = "CREATE TABLE " + TABLE_ELEC_USER_TYPE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ELEC_USER_TYPE_NAME + " TEXT NOT NULL, "
                + COLUMN_UNIT_PRICE + " INTEGER NOT NULL);";

        // Create customer table
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_YYYYMM + " TEXT NOT NULL, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_USED_NUM_ELECTRIC + " INTEGER, "
                + COLUMN_ELEC_USER_TYPE_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_ELEC_USER_TYPE_ID + ") REFERENCES "
                + TABLE_ELEC_USER_TYPE + "(" + COLUMN_ID + "));";

        // Execute the SQL statements
        db.execSQL(CREATE_ELECTRIC_USER_TYPE_TABLE);
        db.execSQL(CREATE_CUSTOMER_TABLE);

        // Insert default values into electric_user_type table
        insertDefaultElectricUserTypes(db);
    }

    // Inserting default data into electric_user_type
    private void insertDefaultElectricUserTypes(SQLiteDatabase db) {
        String insertPrivate = "INSERT INTO " + TABLE_ELEC_USER_TYPE + " ("
                + COLUMN_ELEC_USER_TYPE_NAME + ", " + COLUMN_UNIT_PRICE + ") "
                + "VALUES ('Private', 1000);";
        String insertBusiness = "INSERT INTO " + TABLE_ELEC_USER_TYPE + " ("
                + COLUMN_ELEC_USER_TYPE_NAME + ", " + COLUMN_UNIT_PRICE + ") "
                + "VALUES ('Business', 2000);";

        db.execSQL(insertPrivate);
        db.execSQL(insertBusiness);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELEC_USER_TYPE);
        // Recreate the tables
        onCreate(db);
    }

    // Add new customer
        public long addCustomer(String name, String yyyymm, String address, int usedNumElectric, int elecUserTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_YYYYMM, yyyymm);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_USED_NUM_ELECTRIC, usedNumElectric);
        values.put(COLUMN_ELEC_USER_TYPE_ID, elecUserTypeId);

        // Insert a new row and return the ID of the newly inserted row
        return db.insert(TABLE_CUSTOMER, null, values);
    }

    // Update customer
    public void updateCustomer(int id, String name, String Dob, String address, int usedNumElectric, int elecUserTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_YYYYMM, Dob);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_USED_NUM_ELECTRIC, usedNumElectric);
        values.put(COLUMN_ELEC_USER_TYPE_ID, elecUserTypeId);

        // Update the row and return the number of affected rows
        db.update(TABLE_CUSTOMER, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Delete customer
    public int deleteCustomer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the row by ID and return the number of affected rows
        return db.delete(TABLE_CUSTOMER, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Fetch all customers (optional)
    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CUSTOMER;
        return db.rawQuery(query, null);
    }

    public ElectricUserType getElectricUserTypeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ELEC_USER_TYPE, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ELEC_USER_TYPE_NAME));
            int unitPrice = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_UNIT_PRICE));
            cursor.close();
            return new ElectricUserType(id, name, unitPrice);
        }

        if (cursor != null) {
            cursor.close();
        }
        return null; // Return null if no user type found
    }
}
