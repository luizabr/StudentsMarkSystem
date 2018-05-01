package com.example.luizaabraamyan.studentmarkssystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    protected SQLiteDatabase db = null;

    private static  final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UniversityDB.db";

    private static final String TEACHERS_TABLE = "teachers";
    private static final String SUBJECTS_TABLE = "subjects";
    private static final String GROUPS_TABLE = "groups";
    private static final String STUDENTS_TABLE = "students";
    private static final String TEACHERS_SUBJECTS_GROUPS_TABLE = "teachers_subjects_groups";


    private static final String TEACHER_ID = "teacherId";
    private static final String TEACHER_UNIVERSITY_ID = "universityId";

    private static final String SUBJECT_ID = "subjectId";
    private static final String SUBJECT_NAME = "subjectName";

    private static final String GROUP_ID = "groupId";
    private static final String GROUP_NUMBER = "groupNumber";

    private static final String STUDENT_ID = "studentId";
    private static final String STUDENT_NAME = "studentName";
    private static final String STUDENT_FAC_NUM = "studentFacNum";
    private static final String STUDENT_MARK = "studentMark";

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
            + STUDENTS_TABLE + "(" + STUDENT_ID + " INTEGER PRIMARY KEY, " + STUDENT_NAME
            + " TEXT, " + STUDENT_FAC_NUM + " TEXT, " + STUDENT_MARK
            + " INTEGER, " + GROUP_ID + " INTEGER,"
            + " FOREIGN KEY(" + GROUP_ID + ") REFERENCES " + GROUPS_TABLE + "(" + GROUP_ID + "));";

    private static final String CREATE_TEACHERS_SUBJECTS_GROUPS_TABLE = "CREATE TABLE "
            + TEACHERS_SUBJECTS_GROUPS_TABLE
            + "(" + TEACHER_ID + " INTEGER, " + SUBJECT_ID + " INTEGER, " + GROUP_ID + " INTEGER,"
            //not sure if they should be primary
            + " PRIMARY KEY(" + TEACHER_ID + ", " + SUBJECT_ID + ", " + GROUP_ID + "),"
            + " FOREIGN KEY(" + TEACHER_ID + ") REFERENCES " + TEACHERS_TABLE + "(" + TEACHER_ID + "),"
            + " FOREIGN KEY(" + SUBJECT_ID + ") REFERENCES " + SUBJECTS_TABLE + "(" + SUBJECT_ID + "),"
            + " FOREIGN KEY(" + GROUP_ID + ") REFERENCES " + GROUPS_TABLE + "(" + GROUP_ID + "));";

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
        db.execSQL(CREATE_TEACHERS_SUBJECTS_GROUPS_TABLE);
        initTeachers(db);
        initSubjects(db);
        initGroups(db);
        initStudents(db);
        initTeachersSubjectsGroups(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TEACHERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GROUPS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TEACHERS_SUBJECTS_GROUPS_TABLE);
        onCreate(db);
    }

    //INSERTING DATA
    private void initTeachers(SQLiteDatabase db){
        db.execSQL("INSERT INTO " + TEACHERS_TABLE
                + " VALUES (1, '11111'), (2, '22222'), (3, '33333');");
    }

    private void initSubjects(SQLiteDatabase db){
        db.execSQL("INSERT INTO " + SUBJECTS_TABLE
                + " VALUES (1, 'Maths / Course 1'), (2, 'English / Course 2'), "
                + "(3, 'Information Systems / Course 3'), "
                + "(4, 'Network secutiry / Course 3'), "
                + "(5, 'Object oriented programming / Course 3');");
    }

    private void initGroups(SQLiteDatabase db){
        db.execSQL("INSERT INTO " + GROUPS_TABLE
                + " VALUES (1, 47), (2, 48), (3, 49), (4, 50), (5, 51);");
    }

    private void initStudents(SQLiteDatabase db){
        db.execSQL("INSERT INTO " + STUDENTS_TABLE
                + " VALUES (1, 'Trayan Muchev', '501214001', NULL, 1), "
                + "(2, 'Hristiyan Stoyanov', '501214002', NULL, 1), "
                + "(3, 'Borislav Borisov', '501214003', NULL, 2), "
                + "(4, 'Kamen Todorov', '501214004', NULL, 2), "
                + "(5, 'Antonina Petrova', '501214005', NULL, 3), "
                + "(6, 'Krasimira Dimova', '501214006', NULL, 3), "
                + "(7, 'Mariya Ivanova', '501214007', NULL, 4), "
                + "(8, 'Luiza Abraamyan', '501214008', NULL, 4), "
                + "(9, 'Maya Petrova', '501214009', NULL, 5), "
                + "(10, 'Elena Georgieva', '501214010', NULL, 5);");
    }

    private void initTeachersSubjectsGroups(SQLiteDatabase db){
        db.execSQL("INSERT INTO " + TEACHERS_SUBJECTS_GROUPS_TABLE
                + " VALUES (1, 1, 1), (1, 2, 2), (1, 3, 1), "
                + "(2, 3, 3), (2, 4, 5), (3, 4, 4), (3, 5, 5);");
    }

}
