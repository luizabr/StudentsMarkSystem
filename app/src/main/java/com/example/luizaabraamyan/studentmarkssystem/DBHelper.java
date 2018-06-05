package com.example.luizaabraamyan.studentmarkssystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    protected SQLiteDatabase db = null;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UniversityDB.db";

    private static final String TEACHERS_TABLE = "teachers";
    private static final String SUBJECTS_TABLE = "subjects";
    private static final String GROUPS_TABLE = "groups";
    private static final String STUDENTS_TABLE = "students";
    private static final String LABS_TABLE = "labs";
    private static final String MARKS_TABLE = "marks";
    private static final String TEACHERS_SUBJECTS_GROUPS_TABLE = "teachers_subjects_groups";
    private static final String STUDENTS_LABS_TABLE = "students_labs";
    private static final String STUDENTS_MARKS_TABLE = "students_marks";


    private static final String TEACHER_ID = "teacherId";
    private static final String TEACHER_UNIVERSITY_ID = "universityId";

    private static final String SUBJECT_ID = "subjectId";
    private static final String SUBJECT_NAME = "subjectName";

    private static final String GROUP_ID = "groupId";
    private static final String GROUP_NUMBER = "groupNumber";

    private static final String STUDENT_ID = "studentId";
    private static final String STUDENT_NAME = "studentName";
    private static final String STUDENT_FAC_NUM = "studentFacNum";
    private static final String STUDENT_TEMP_MARK = "studentTempMark";
    private static final String IS_STUDENT_ENDORSED = "isStudentEndorsed";
    private static final String STUDENT_NOTE = "studentNote";

    private static final String LAB_ID = "labId";
    private static final String LAB_PRETTY_NAME = "prettyName";

    private static final String MARK_ID = "markId";
    private static final String MARK_NUMBER = "markNumber";

    private static final String IS_PRESENT = "isPresent";

    private static final String CREATE_TEACHERS_TABLE = "CREATE TABLE "
            + TEACHERS_TABLE + "(" + TEACHER_ID + " INTEGER PRIMARY KEY, " + TEACHER_UNIVERSITY_ID
            + " TEXT" + ");";

    private static final String CREATE_SUBJECTS_TABLE = "CREATE TABLE "
            + SUBJECTS_TABLE + "(" + SUBJECT_ID + " INTEGER PRIMARY KEY, " + SUBJECT_NAME
            + " TEXT" + ");";

    private static final String CREATE_GROUPS_TABLE = "CREATE TABLE "
            + GROUPS_TABLE + "(" + GROUP_ID + " INTEGER PRIMARY KEY, " + GROUP_NUMBER
            + " INTEGER" + ");";

    private static final String CREATE_STUDENTS_TABLE = "CREATE TABLE "
            + STUDENTS_TABLE + "(" + STUDENT_ID + " INTEGER PRIMARY KEY, "
            + STUDENT_NAME + " TEXT, " + STUDENT_FAC_NUM + " TEXT, "
            + GROUP_ID + " INTEGER, "
            + IS_STUDENT_ENDORSED + " INTEGER, " + STUDENT_NOTE + " TEXT, " + STUDENT_TEMP_MARK + " INTEGER,"
            + " FOREIGN KEY(" + GROUP_ID + ") REFERENCES " + GROUPS_TABLE + "(" + GROUP_ID + "));";

    //Added subject id. Relation subject - lab -> 1 - M. Removed isPresent.
    private static final String CREATE_LABS_TABLE = "CREATE TABLE "
            + LABS_TABLE + "(" + LAB_ID + " INTEGER PRIMARY KEY, " + LAB_PRETTY_NAME + " TEXT, "
            + SUBJECT_ID + " INTEGER, "
            + " FOREIGN KEY(" + SUBJECT_ID + ") REFERENCES " + SUBJECTS_TABLE + "(" + SUBJECT_ID + "));";

    private static final String CREATE_MARKS_TABLE = "CREATE TABLE "
            + MARKS_TABLE + "(" + MARK_ID + " INTEGER PRIMARY KEY, " + MARK_NUMBER + " INTEGER" + ");";

    private static final String CREATE_TEACHERS_SUBJECTS_GROUPS_TABLE = "CREATE TABLE "
            + TEACHERS_SUBJECTS_GROUPS_TABLE
            + "(" + TEACHER_ID + " INTEGER, " + SUBJECT_ID + " INTEGER, " + GROUP_ID + " INTEGER,"
            + " PRIMARY KEY(" + TEACHER_ID + ", " + SUBJECT_ID + ", " + GROUP_ID + "),"
            + " FOREIGN KEY(" + TEACHER_ID + ") REFERENCES " + TEACHERS_TABLE + "(" + TEACHER_ID + "),"
            + " FOREIGN KEY(" + SUBJECT_ID + ") REFERENCES " + SUBJECTS_TABLE + "(" + SUBJECT_ID + "),"
            + " FOREIGN KEY(" + GROUP_ID + ") REFERENCES " + GROUPS_TABLE + "(" + GROUP_ID + "));";

    private static final String CREATE_STUDENTS_LABS_TABLE = "CREATE TABLE "
            + STUDENTS_LABS_TABLE
            + "(" + STUDENT_ID + " INTEGER, " + LAB_ID + " INTEGER, " + IS_PRESENT + " INTEGER, "
            + " PRIMARY KEY(" + STUDENT_ID + ", " + LAB_ID + "),"
            + " FOREIGN KEY(" + STUDENT_ID + ") REFERENCES " + STUDENTS_TABLE + "(" + STUDENT_ID + "),"
            + " FOREIGN KEY(" + LAB_ID + ") REFERENCES " + LABS_TABLE + "(" + LAB_ID + "));";

    private static final String CREATE_STUDENTS_MARKS_TABLE = "CREATE TABLE "
            + STUDENTS_MARKS_TABLE
            + "(" + STUDENT_ID + " INTEGER, " + MARK_ID + " INTEGER, "
//            + " PRIMARY KEY(" + STUDENT_ID + ", " + MARK_ID + "),"
            + " FOREIGN KEY(" + STUDENT_ID + ") REFERENCES " + STUDENTS_TABLE + "(" + STUDENT_ID + "),"
            + " FOREIGN KEY(" + MARK_ID + ") REFERENCES " + MARKS_TABLE + "(" + MARK_ID + "));";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void close() {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TEACHERS_TABLE);
        db.execSQL(CREATE_SUBJECTS_TABLE);
        db.execSQL(CREATE_GROUPS_TABLE);
        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_LABS_TABLE);
        db.execSQL(CREATE_MARKS_TABLE);
        db.execSQL(CREATE_TEACHERS_SUBJECTS_GROUPS_TABLE);
        db.execSQL(CREATE_STUDENTS_LABS_TABLE);
        db.execSQL(CREATE_STUDENTS_MARKS_TABLE);
        initTeachers(db);
        initSubjects(db);
        initGroups(db);
        initStudents(db);
        initLabs(db);
        initMarks(db);
        initTeachersSubjectsGroups(db);
        initStudentsLabs(db);
        initStudentsMarks(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TEACHERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GROUPS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LABS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MARKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TEACHERS_SUBJECTS_GROUPS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_LABS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_MARKS_TABLE);
        onCreate(db);
    }

    //INSERTING DATA
    private void initTeachers(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TEACHERS_TABLE
                + " VALUES (1, '11111'), (2, '22222'), (3, '33333');");
    }

    private void initSubjects(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + SUBJECTS_TABLE
                + " VALUES (1, 'Математика / Курс 1'), (2, 'Английски език / Курс 2'), "
                + "(3, 'Информационни системи / Курс 3'), "
                + "(4, 'Мрежова сигурност / Курс 3'), "
                + "(5, 'ООП / Курс 3');");
    }

    private void initGroups(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + GROUPS_TABLE
                + " VALUES (1, 47), (2, 48), (3, 49), (4, 50), (5, 51);");
    }

    private void initStudents(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + STUDENTS_TABLE
                + " VALUES (1, 'Траян Мучев', '501214001', 1, NULL, NULL, 0), "
                + "(2, 'Християн Стоянов', '501214002', 1, NULL, NULL, 0), "
                + "(3, 'Борислав Борисов', '501214003', 2, NULL, NULL, 0), "
                + "(4, 'Камен Тодоров', '501214004', 2, NULL, NULL, 0), "
                + "(5, 'Антонина Петрова', '501214005', 3, NULL, NULL, 0), "
                + "(6, 'Красимира Димова', '501214006', 3, NULL, NULL, 0), "
                + "(7, 'Мария Иванова', '501214007', 4, NULL, NULL, 0), "
                + "(8, 'Луиза Абраамян', '501214008', 4, NULL, NULL, 0), "
                + "(9, 'Мая Петрова', '501214009', 5, NULL, NULL, 0), "
                + "(10, 'Елена Георгиева', '501214010', 5, NULL, NULL, 0);");
    }

    private void initLabs(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + LABS_TABLE
                + " VALUES (1, 'Упражнение 1', 1), "
                + "(2, 'Упражнение 2', 1), "
                + "(3, 'Упражнение 3', 1), "
                + "(4, 'Упражнение 4', 1), "
                + "(5, 'Упражнение 5', 1), "
                + "(6, 'Упражнение 6', 1), "
                + "(7, 'Упражнение 7', 1), "

                + "(8, 'Упражнение 1', 2), "
                + "(9, 'Упражнение 2', 2), "
                + "(10, 'Упражнение 3', 2), "
                + "(11, 'Упражнение 4', 2), "
                + "(12, 'Упражнение 5', 2), "
                + "(13, 'Упражнение 6', 2), "
                + "(14, 'Упражнение 7', 2), "

                + "(15, 'Упражнение 1', 3), "
                + "(16, 'Упражнение 2', 3), "
                + "(17, 'Упражнение 3', 3), "
                + "(18, 'Упражнение 4', 3), "
                + "(19, 'Упражнение 5', 3), "
                + "(20, 'Упражнение 6', 3), "
                + "(21, 'Упражнение 7', 3), "

                + "(22, 'Упражнение 1', 4), "
                + "(23, 'Упражнение 2', 4), "
                + "(24, 'Упражнение 3', 4), "
                + "(25, 'Упражнение 4', 4), "
                + "(26, 'Упражнение 5', 4), "
                + "(27, 'Упражнение 6', 4), "
                + "(28, 'Упражнение 7', 4), "

                + "(29, 'Упражнение 1', 5), "
                + "(30, 'Упражнение 2', 5), "
                + "(31, 'Упражнение 3', 5), "
                + "(32, 'Упражнение 4', 5), "
                + "(33, 'Упражнение 5', 5), "
                + "(34, 'Упражнение 6', 5), "
                + "(35, 'Упражнение 7', 5);");
    }

    private void initMarks(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + MARKS_TABLE
                + " VALUES (1, 2), (2, 3), (3, 4), "
                + "(4, 5), (5, 6);");
    }

    private void initTeachersSubjectsGroups(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TEACHERS_SUBJECTS_GROUPS_TABLE
                + " VALUES (1, 1, 1), (1, 2, 2), (1, 3, 1), "
                + "(2, 3, 3), (2, 4, 5), (3, 4, 4), (3, 5, 5);");
    }

    private void initStudentsLabs(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + STUDENTS_LABS_TABLE
                + " VALUES (1, 1, 0), (1, 2, 0), (1, 3, 0), (1, 4, 0), (1, 5, 0), (1, 6, 0), (1, 7, 0), "
                + "(2, 1, 0), (2, 2, 0), (2, 3, 0), (2, 4, 0), (2, 5, 0), (2, 6, 0), (2, 7, 0),"
                + "(1, 15, 0), (1, 16, 0), (1, 17, 0), (1, 18, 0), (1, 19, 0), (1, 20, 0), (1, 21, 0), "
                + "(2, 15, 0), (2, 16, 0), (2, 17, 0), (2, 18, 0), (2, 19, 0), (2, 20, 0), (2, 21, 0),"
                + " (3, 8, 0), (3, 9, 0), (3, 10, 0), (3, 11, 0), (3, 12, 0), (3, 13, 0), (3, 14, 0),"
                + " (4, 8, 0), (4, 9, 0), (4, 10, 0), (4, 11, 0), (4, 12, 0), (4, 13, 0), (4, 14, 0),"
                + " (5, 15, 0), (5, 16, 0), (5, 17, 0), (5, 18, 0), (5, 19, 0), (5, 20, 0), (5, 21, 0),"
                + " (6, 15, 0), (6, 16, 0), (6, 17, 0), (6, 18, 0), (6, 19, 0), (6, 20, 0), (6, 21, 0),"
                + " (7, 22, 0), (7, 23, 0), (7, 24, 0), (7, 25, 0), (7, 26, 0), (7, 27, 0), (7, 28, 0),"
                + " (8, 22, 0), (8, 23, 0), (8, 24, 0), (8, 25, 0), (8, 26, 0), (8, 27, 0), (8, 28, 0),"
                + " (9, 22, 0), (9, 23, 0), (9, 24, 0), (9, 25, 0), (9, 26, 0), (9, 27, 0), (9, 28, 0),"
                + " (10, 22, 0), (10, 23, 0), (10, 24, 0), (10, 25, 0), (10, 26, 0), (10, 27, 0), (10, 28, 0),"
                + " (9, 29, 0), (9, 30, 0), (9, 31, 0), (9, 32, 0), (9, 33, 0), (9, 34, 0), (9, 35, 0),"
                + " (10, 29, 0), (10, 30, 0), (10, 31, 0), (10, 32, 0), (10, 33, 0), (10, 34, 0), (10, 35, 0);");
    }

    private void initStudentsMarks(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + STUDENTS_MARKS_TABLE
                + " VALUES (NULL, NULL);");
    }

}
