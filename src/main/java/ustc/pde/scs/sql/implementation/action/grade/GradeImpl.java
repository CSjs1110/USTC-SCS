package ustc.pde.scs.sql.implementation.action.grade;

import ustc.pde.scs.entity.relation.Score;
import ustc.pde.scs.sql.base.BaseDAO;
import ustc.pde.scs.sql.implementation.course.CourseSelectImpl;
import ustc.pde.scs.sql.implementation.user.StudentDAOImpl;
import ustc.pde.scs.sql.inter.action.Grade;

//import java.awt.image.BaseMultiResolutionImage;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeImpl extends BaseDAO implements Grade {
    @Override
    public boolean insert(Score score) throws SQLException {
        CourseSelectImpl courseSelect = new CourseSelectImpl();
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        if(courseSelect.isExist(score.getCourseId()) && studentDAO.isExist(score.getId())){
            if(isExist(score.getId()+" "+score.getCourseId())){
                return false;   //已存在
            }
            String sql = "insert into selectGrade values(?,?,?)";
            executeUpdate(sql,score.getId(),score.getCourseId(),score.getGrade());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String primaryKey) throws SQLException {
        if(!isExist(primaryKey)){
            return false;   //不存在
        }
        String [] pk = primaryKey.split(" ");
        String id = pk[0];
        String courseId = pk[1];
        String sql = "delete from selectGrade where id = ? and courseId = ?";
        executeUpdate(sql,id,courseId);
        return true;
    }

    @Override
    public boolean update(Score score) throws SQLException {
        if(!isExist(score.getId()+ " " + score.getCourseId())){
            return false;
        }
        String sql = "update selectGrade set grade = ? where id = ? and courseId = ?";
        executeUpdate(sql,score.getGrade(),score.getId(),score.getCourseId());
        return true;
    }

    @Override
    public Score getObject(String primaryKey) {
        String sql = "select * from selectGrade where id = ? and courseId = ?";
        String [] pk = primaryKey.split(" ");
        String id = pk[0];
        String courseId = pk[1];
        return getInstance(Score.class,sql,id,courseId);
    }

    @Override
    public ArrayList<Score> getAll() {
        String sql = "select * from selectGrade";
        return getInstance2(Score.class,sql);
    }

    @Override
    public Long getCount() {
        String sql = "select count(*) from selectGrade";
        return getValue(sql);
    }

    @Override
    public boolean isExist(String primaryKey) {
        if(getObject(primaryKey)==null) return false;
        return true;
    }
}