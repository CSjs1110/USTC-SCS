package ustc.pde.scs.sql.implementation.user;

import ustc.pde.scs.entity.user.Student;
import ustc.pde.scs.entity.user.User;


import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAOImpl extends UserDAOImpl {

    public boolean insert(Student student) throws SQLException {

        if(super.insert(student)) System.out.println("父表插入成功");      //插入父表
        else System.out.println("父表插入失败");
        try{
            String sql = "insert into Student(ID,majorId,stuType,stuName," +
                    "studyDate,curSemester) values(?,?,?,?,?,?)";
            executeUpdate(sql,student.getID(),student.getMajor().getMajorId(),student.getStuType(),
                    student.getStuName(),student.getStudyDate(), student.getCurSemester());
            return true;
        }catch (SQLException e){
            return false;
        }

    }

    @Override
    public boolean delete(String id) throws SQLException {
        try{
            String sql = "delete from student where ID = ?";
            executeUpdate(sql,id);
            return true;
        }catch(SQLException e){
            return false;
        }

    }
    public boolean update(Student student) throws SQLException {
        try{
            String sql = "update student set majorId = ?,stuName =?,studyDate = ?,curSemester = ?," +
                    "stuType = ? where ID = ?";
            executeUpdate(sql,student.getMajor().getMajorId(),student.getStuName(),
                    student.getStudyDate(),student.getCurSemester(),student.getStuType(),student.getID());
            return true;
        }catch (SQLException e){
            return false;
        }

    }

    @Override
    public Student getObject(String id) {
        String sql = "select username,password,idCard,student.ID as ID,email, " +
                "stuName,studyDate,curSemester,stuType from users,student " +
                "where student.ID = users.ID and student.id = ?";
        return getInstance(Student.class,sql,id);
    }

    public ArrayList<Student> getAllStudent() {
        String sql = "select username,password,idCard,student.id as ID,email, " +
                "stuName,studyDate,curSemester,stuType from users,student" +
                "where student.id = users.id";
        return getInstance2(Student.class,sql);
    }

    @Override
    public Long getCount() {
        String sql = "select count(*) from student";
        return getValue(sql);
    }

    @Override
    public boolean isExist(String id) {
        if(getObject(id) == null) return false;
        return true;
    }
}
