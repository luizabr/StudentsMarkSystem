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
                + " VALUES (1, '11111'), (2, '22222'), (3, '33333'), " +
                " (4, 44444), (5, 55555);");
    }

    private void initSubjects(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + SUBJECTS_TABLE
                + " VALUES (1, 'Химия / Курс 1'), (2, 'Английски език / Курс 1'), "
                + "(3, 'Мениджмънт / Курс 2'), "
                + "(4, 'Бази от данни / Курс 2'), "
                + "(5, 'Операционни системи / Курс 3'), "
                + "(6, 'Компютърна архитектура / Курс 3'),"
                + "(7, 'Обектно ориентирано програмиране / Курс 3'),"
                + "(8, 'Информационни системи / Курс 4'),"
                + "(9, 'Мрежова и информационна сигурност / Курс 4'),"
                + "(10, 'Програмни среди / Курс 4')");
    }

    private void initGroups(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + GROUPS_TABLE
                + " VALUES (1, 47), (2, 48), (3, 49), (4, 50), (5, 51);");
    }

    private void initStudents(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + STUDENTS_TABLE
                + " VALUES (1, 'Траян Мучев', '501214001', 1, NULL, NULL, 0), "
                + "(2, 'Християн Стоянов', '501214002', 1, NULL, NULL, 0), "
                + "(3, 'Борислав Борисов', '501214003', 1, NULL, NULL, 0), "
                + "(4, 'Камен Тодоров', '501214004', 1, NULL, NULL, 0), "
                + "(5, 'Антонина Петрова', '501214005', 1, NULL, NULL, 0), "
                + "(6, 'Красимира Димова', '501214006', 1, NULL, NULL, 0), "
                + "(7, 'Мария Иванова', '501214007', 1, NULL, NULL, 0), "
                + "(8, 'Луиза Абраамян', '501214008', 1, NULL, NULL, 0), "
                + "(9, 'Мая Петрова', '501214009', 1, NULL, NULL, 0), "
                + "(10, 'Елена Георгиева', '501214010', 1, NULL, NULL, 0), "

                + "(11, 'Паулина Такова', '501214011', 2, NULL, NULL, 0), "
                + "(12, 'Тодор Андонов', '501214012', 2, NULL, NULL, 0), "
                + "(13, 'Тихомир Петков', '501214013', 2, NULL, NULL, 0), "
                + "(14, 'Кристиян Стоянов', '501214014', 2, NULL, NULL, 0), "
                + "(15, 'Тодор Димов', '501214015', 2, NULL, NULL, 0), "
                + "(16, 'Венцислав Копринков', '501214016', 2, NULL, NULL, 0), "
                + "(17, 'Николай Добрев', '501214017', 2, NULL, NULL, 0), "
                + "(18, 'Моника Маринова', '501214018', 2, NULL, NULL, 0), "
                + "(19, 'Ивайло Димитров', '501214019', 2, NULL, NULL, 0), "
                + "(20, 'Ина Григорова', '501214020', 2, NULL, NULL, 0), "

                + "(21, 'Силва Топузян', '501214021', 3, NULL, NULL, 0), "
                + "(22, 'Симеон Ангелов', '501214022', 3, NULL, NULL, 0), "
                + "(23, 'Пламена Атанасова', '501214023', 3, NULL, NULL, 0), "
                + "(24, 'Деница Дянкова', '501214024', 3, NULL, NULL, 0), "
                + "(25, 'Добрина Александрова', '501214025', 3, NULL, NULL, 0), "
                + "(26, 'Тихомира Паричкова', '501214026', 3, NULL, NULL, 0), "
                + "(27, 'Петър Попов', '501214027', 3, NULL, NULL, 0), "
                + "(28, 'Александър Ковачев', '501214028', 3, NULL, NULL, 0), "
                + "(29, 'Георги Стоилов', '501214029', 3, NULL, NULL, 0), "
                + "(30, 'Ванеса Тодорова', '501214030', 3, NULL, NULL, 0), "

                + "(31, 'Матронка Димова', '501214031', 4, NULL, NULL, 0), "
                + "(32, 'Христо Христов', '501214032', 4, NULL, NULL, 0), "
                + "(33, 'Ангел Метаксов', '501214033', 4, NULL, NULL, 0), "
                + "(34, 'Явор Младенов', '501214034', 4, NULL, NULL, 0), "
                + "(35, 'Станимир Трюфенев', '501214035', 4, NULL, NULL, 0), "
                + "(36, 'Иван Станев', '501214036', 4, NULL, NULL, 0), "
                + "(37, 'Георг Атанасов', '501214037', 4, NULL, NULL, 0), "
                + "(38, 'Михаил Златев', '501214038', 4, NULL, NULL, 0), "
                + "(39, 'Симона Павлова', '501214039', 4, NULL, NULL, 0), "
                + "(40, 'Величка Мучева', '501214040', 4, NULL, NULL, 0), "

                + "(41, 'Димитър Киряков', '501214041', 5, NULL, NULL, 0), "
                + "(42, 'Илия Мучев', '501214042', 5, NULL, NULL, 0), "
                + "(43, 'Десислава Кирова', '501214043', 5, NULL, NULL, 0), "
                + "(44, 'Петя Стефанова', '501214044', 5, NULL, NULL, 0), "
                + "(45, 'Димо Димов', '501214045', 5, NULL, NULL, 0), "
                + "(46, 'Радостин Георгиев', '501214046', 5, NULL, NULL, 0), "
                + "(47, 'Александър Иванов', '501214047', 5, NULL, NULL, 0), "
                + "(48, 'Таня Димитрова', '501214048', 5, NULL, NULL, 0), "
                + "(49, 'Данаил Златанов', '501214049', 5, NULL, NULL, 0), "
                + "(50, 'Живка Кондева', '501214050', 5, NULL, NULL, 0);");
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
                + "(35, 'Упражнение 7', 5), "

                + "(36, 'Упражнение 1', 6), "
                + "(37, 'Упражнение 2', 6), "
                + "(38, 'Упражнение 3', 6), "
                + "(39, 'Упражнение 4', 6), "
                + "(40, 'Упражнение 5', 6), "
                + "(41, 'Упражнение 6', 6), "
                + "(42, 'Упражнение 7', 6), "

                + "(43, 'Упражнение 1', 7), "
                + "(44, 'Упражнение 2', 7), "
                + "(45, 'Упражнение 3', 7), "
                + "(46, 'Упражнение 4', 7), "
                + "(47, 'Упражнение 5', 7), "
                + "(48, 'Упражнение 6', 7), "
                + "(49, 'Упражнение 7', 7), "

                + "(50, 'Упражнение 1', 8), "
                + "(51, 'Упражнение 2', 8), "
                + "(52, 'Упражнение 3', 8), "
                + "(53, 'Упражнение 4', 8), "
                + "(54, 'Упражнение 5', 8), "
                + "(55, 'Упражнение 6', 8), "
                + "(56, 'Упражнение 7', 8), "

                + "(57, 'Упражнение 1', 9), "
                + "(58, 'Упражнение 2', 9), "
                + "(59, 'Упражнение 3', 9), "
                + "(60, 'Упражнение 4', 9), "
                + "(61, 'Упражнение 5', 9), "
                + "(62, 'Упражнение 6', 9), "
                + "(63, 'Упражнение 7', 9), "

                + "(64, 'Упражнение 1', 10), "
                + "(65, 'Упражнение 2', 10), "
                + "(66, 'Упражнение 3', 10), "
                + "(67, 'Упражнение 4', 10), "
                + "(68, 'Упражнение 5', 10), "
                + "(69, 'Упражнение 6', 10), "
                + "(70, 'Упражнение 7', 10);");
    }

    private void initMarks(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + MARKS_TABLE
                + " VALUES (1, 2), (2, 3), (3, 4), "
                + "(4, 5), (5, 6);");
    }

    private void initTeachersSubjectsGroups(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TEACHERS_SUBJECTS_GROUPS_TABLE
                + " VALUES (1, 1, 1), (1, 1, 2), (1, 1, 3), "
                + "(2, 2, 1), (2, 2, 2), (2, 2, 4), "
                + "(3, 3, 1), (3, 4, 3), (3, 5, 4), "
                + "(4, 6, 3), (4, 7, 4), (4, 8, 5), "
                + "(5, 9, 1), (5, 10, 3), (5, 10, 5); ");
    }

    private void initStudentsLabs(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + STUDENTS_LABS_TABLE
                + " VALUES (1, 1, 0), (1, 2, 0), (1, 3, 0), (1, 4, 0), (1, 5, 0), (1, 6, 0), (1, 7, 0),"
                + "(2, 1, 0), (2, 2, 0), (2, 3, 0), (2, 4, 0), (2, 5, 0), (2, 6, 0), (2, 7, 0),"
                + "(1, 15, 0), (1, 16, 0), (1, 17, 0), (1, 18, 0), (1, 19, 0), (1, 20, 0), (1, 21, 0),"
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
                + " (10, 29, 0), (10, 30, 0), (10, 31, 0), (10, 32, 0), (10, 33, 0), (10, 34, 0), (10, 35, 0),"

                + "(11, 1, 0), (11, 2, 0), (11, 3, 0), (11, 4, 0), (11, 5, 0), (11, 6, 0), (11, 7, 0),"
                + "(12, 1, 0), (12, 2, 0), (12, 3, 0), (12, 4, 0), (12, 5, 0), (12, 6, 0), (12, 7, 0),"
                + "(1, 15, 0), (1, 16, 0), (1, 17, 0), (1, 18, 0), (1, 19, 0), (1, 20, 0), (1, 21, 0),"
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
                + " (10, 29, 0), (10, 30, 0), (10, 31, 0), (10, 32, 0), (10, 33, 0), (10, 34, 0), (10, 35, 0),"

                + "(1, 1, 0), (1, 2, 0), (1, 3, 0), (1, 4, 0), (1, 5, 0), (1, 6, 0), (1, 7, 0),"
                + "(2, 1, 0), (2, 2, 0), (2, 3, 0), (2, 4, 0), (2, 5, 0), (2, 6, 0), (2, 7, 0),"
                + "(1, 15, 0), (1, 16, 0), (1, 17, 0), (1, 18, 0), (1, 19, 0), (1, 20, 0), (1, 21, 0),"
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
                + " (10, 29, 0), (10, 30, 0), (10, 31, 0), (10, 32, 0), (10, 33, 0), (10, 34, 0), (10, 35, 0),"

                + "(1, 1, 0), (1, 2, 0), (1, 3, 0), (1, 4, 0), (1, 5, 0), (1, 6, 0), (1, 7, 0),"
                + "(2, 1, 0), (2, 2, 0), (2, 3, 0), (2, 4, 0), (2, 5, 0), (2, 6, 0), (2, 7, 0),"
                + "(1, 15, 0), (1, 16, 0), (1, 17, 0), (1, 18, 0), (1, 19, 0), (1, 20, 0), (1, 21, 0),"
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
                + " (10, 29, 0), (10, 30, 0), (10, 31, 0), (10, 32, 0), (10, 33, 0), (10, 34, 0), (10, 35, 0),"

                + "(1, 1, 0), (1, 2, 0), (1, 3, 0), (1, 4, 0), (1, 5, 0), (1, 6, 0), (1, 7, 0),"
                + "(2, 1, 0), (2, 2, 0), (2, 3, 0), (2, 4, 0), (2, 5, 0), (2, 6, 0), (2, 7, 0),"
                + "(1, 15, 0), (1, 16, 0), (1, 17, 0), (1, 18, 0), (1, 19, 0), (1, 20, 0), (1, 21, 0),"
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
                + " (10, 29, 0), (10, 30, 0), (10, 31, 0), (10, 32, 0), (10, 33, 0), (10, 34, 0), (10, 35, 0),"
        );
    }

    private void initStudentsMarks(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + STUDENTS_MARKS_TABLE
                + " VALUES (NULL, NULL);");
    }

}
