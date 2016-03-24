package com.dmd.zsb;

import android.app.Application;
import android.test.ApplicationTestCase;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

/*    public void testAddUser() throws SQLException {
        DatabaseHelper helper = DatabaseHelper.getDatabaseHelper();
        for (int i = 0; i < 50; i++) {
            GradeEntity gradeEntity = new GradeEntity();
            gradeEntity.setGrade_id("我们是" + i);
            gradeEntity.setGrade_name("你们是" + i);
            helper.getGradeDao().create(gradeEntity);
        }
        for (int i = 0; i < 50; i++) {
            SubjectEntity subjectEntity = new SubjectEntity();
            subjectEntity.setGrade_id("我们是"+ i);
            subjectEntity.setSub_id("科目"+ i);
            subjectEntity.setSub_img("图片"+ i);
            subjectEntity.setSub_name("名称"+ i);
            helper.getSubjectDao().create(subjectEntity);
        }
        testList();
    }

    public void testList() {
        DatabaseHelper helper = DatabaseHelper.getDatabaseHelper();
        try {
            List<GradeEntity> gradeEntities = helper.getGradeDao().queryForAll();
            List<SubjectEntity> subjectEntities = helper.getSubjectDao().queryForAll();
            Gson gson = new Gson();
            Log.e("TAG", gson.toJson(gradeEntities));
            Log.e("TAG", gson.toJson(subjectEntities.get(45)));
            Log.e("TAG", gson.toJson(subjectEntities.get(46)));
            Log.e("TAG", gson.toJson(subjectEntities.get(47)));
            Log.e("TAG", gson.toJson(subjectEntities.get(48)));
            Log.e("TAG", gson.toJson(subjectEntities.get(49)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}