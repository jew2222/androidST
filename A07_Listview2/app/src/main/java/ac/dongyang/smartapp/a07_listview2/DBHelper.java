package ac.dongyang.smartapp.a07_listview2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

interface DBContract { //콘트랙트 인터페이스 ,객체생성할 필요없어서 클래스로정의해도됩니다
    static final String TABLE_NAME="PWD_T";
    static final String COL_ID="ID";
    static final String COL_NAME="NAME";
    static final String COL_LID="LOGINID";
    static final String COL_PWD="PWD";
    static final String COL_URL="URL"; //컬럼들 이름


    //테이블 생성 구문
    static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "(" +
            COL_ID + " INTEGER NOT NULL PRIMARY KEY, " +
            COL_NAME + " TEXT NOT NULL, " +
            COL_LID + " TEXT NOT NULL, " +
            COL_PWD + " TEXT NOT NULL, " +
            COL_URL + " TEXT)";
    //주의할 건 낫 눌인 컬럼들이 없으면 저장이 안된 다는점
    //테이블 삭제 쿼리
    static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    static final String SQL_LOAD = "SELECT * FROM " + TABLE_NAME;
    //특정 컬럼이 ?인 모든것 가져오는
    static final String SQL_SELECT = "SELECT * FROM "  + TABLE_NAME + " WHERE " + COL_NAME + "=?";


    //특정 컬럼이 ?인 id 컬럼만 가져오는 쿼리
    static final String SQL_SELECT_ID = "SELECT ID FROM "  + TABLE_NAME + " WHERE " + COL_NAME + "=?";
}

class DBHelper extends SQLiteOpenHelper { //상속
    static final String DB_FILE = "pwd_t.db";
    static final int DB_VERSION = 1;

    DBHelper(Context context) {
        super(context, DB_FILE, null, DB_VERSION); // 세번째 인자 : cursor factory
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //버전 없그레이드 시 호출
        db.execSQL(DBContract.SQL_DROP_TABLE);
        onCreate(db);
    }
}
