package com.avenger.edu.stu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.avenger.edu.stu.model.Grade;
import com.avenger.edu.stu.model.MainCourInfo;
import com.avenger.edu.stu.model.MajoCourInfo;
import com.avenger.edu.stu.model.PoinScor;
import com.avenger.edu.stu.model.Schedule;
import com.avenger.edu.stu.model.SeleCourInfo;
import com.avenger.edu.stu.model.Student;

@Mapper
public interface StuMapper {

	/**
	 * 通过学号获得学生个人信息
	 * 
	 * @param id
	 *            学生Id
	 * @return
	 */
	@Select("select stu_id,stu_name,stu_sex,stu_addr,stu_phone,"
			+ "stu_email,stu_pic,stu_clas,stu_majo,stu_col from v_stuInfo where stu_id=#{id}")
	@Results(value = { @Result(column = "stu_id", property = "stuId"),
			@Result(column = "stu_name", property = "stuName"), @Result(column = "stu_sex", property = "stuSex"),
			@Result(column = "stu_addr", property = "stuAddress"), @Result(column = "stu_phone", property = "stuPhone"),
			@Result(column = "stu_email", property = "stuEmail"), @Result(column = "stu_pic", property = "stuPic"),
			@Result(column = "stu_clas", property = "stuClazz"), @Result(column = "stu_majo", property = "stuMajor"),
			@Result(column = "stu_col", property = "stuCollege"),

	})
	public Student findStudentById(int id);

	/**
	 * 班级编号
	 * 
	 * @param id
	 * @return
	 */
	@Select("select clas_id from student where stu_id=#{id}")
	public int findClassByid(int id);

	/**
	 * 专业编号
	 * 
	 * @param id
	 * @return
	 */
	@Select("select majo_id from student where stu_id=#{id}")
	public int findMajorByid(int id);

	/**
	 * 通过班级获得届
	 * 
	 * @param clasId
	 *            班级编号
	 * @return
	 */
	@Select("select clas_period from clazz where clas_id=#{clasId}")
	public int findPeriodByClassId(int clasId);

	/**
	 * 得到学生密码
	 * 
	 * @param id
	 * @return
	 */
	@Select("select stu_password from student where stu_id=#{id}")
	public String findPassWordById(int id);

	/**
	 * 学生id是否存在
	 * 
	 * @param id
	 * @return
	 */
	@Select("select count(stu_id) from student where stu_id=#{id}")
	public int findStudentId(int id);

	/**
	 * 修改学生密码
	 * 
	 * @param id
	 *            学生Id
	 * @param password
	 *            新密码
	 */
	@Update("update student set stu_password=#{password} where stu_id=#{id}")
	public void updateStudentById(@Param("id") int id, @Param("password") String password);

	/**
	 * 查看大学期间major专业在time学期的所需学的必修课程
	 * 
	 * @param major
	 *            专业名
	 * @param time
	 *            学期
	 * @return
	 */
	@Select("select * from v_majocourinfo where mcMajorName=#{major} and mcSubTime=#{time}")
	public List<MajoCourInfo> findMajorCourseByTime(@Param("major") String major, @Param("time") String time);

	/**
	 * 查看可选修课程，进行选课
	 * 
	 * @param time
	 * @return
	 */
	@Select("select * from v_selecourinfo where scSubTime=#{time}")
	public List<SeleCourInfo> findSelectCourse(String time);

	/**
	 * 获得一个学生的选修课程信息
	 * 
	 * @param id
	 *            学生Id
	 * @return
	 */
	@Select("select scId,scCourName,scTeacName,scCourCredit,scCourNature,scCourPeriod,scSubTime,scTeacTitle"
			+ " from selec as s inner join v_selecourinfo as v on  s.selecour_id=v.scId where s.stu_id=#{id}")
	public List<SeleCourInfo> findSelectCourseById(int id);

	/**
	 * 获得一个学生在time学期的选修课程信息
	 * 
	 * @param id
	 *            学生Id
	 * @param time
	 *            学期
	 * @return
	 */
	@Select("select scId,scCourName,scTeacName,scCourCredit,scCourNature,scCourPeriod,scSubTime,scTeacTitle"
			+ " from selec as s inner join v_selecourinfo as v on s.selecour_id=v.scId where s.stu_id=#{id} and v.scSubTime=#{time}")
	public List<SeleCourInfo> findSelectCourseByTime(@Param("id") int id, @Param("time") String time);

	/**
	 * 获得一个学生的主修课程信息
	 * 
	 * @param id
	 *            学生Id
	 * @return
	 */
	@Select("select mcCourName,mcCourCredit,mcCourNature,mcCourPeriod,mcSubTime,mcTeacName,mcTeacTitle"
			+ " from student as s inner join v_maincourinfo as vm on s.clas_id=vm.clas_id where s.stu_id=#{id}")
	public List<MainCourInfo> findMainCourseById(int id);

	/**
	 * 获得一个学生在time学期的主修课程信息
	 * 
	 * @param id
	 *            学生Id
	 * @param time
	 *            学期
	 * @return
	 */
	@Select("select mcCourName,mcCourCredit,mcCourNature,mcCourPeriod,mcSubTime,mcTeacName,mcTeacTitle"
			+ " from student as s inner join v_maincourinfo as vm on s.clas_id=vm.clas_id where s.stu_id=#{id} and vm.mcSubTime=#{time}")
	List<MainCourInfo> findMainCourseByTime(@Param("id") int id, @Param("time") String time);

	/**
	 * 获得一个学生的课表信息
	 * 
	 * @param id
	 *            学生Id
	 * @return
	 */
	@Select("Select courName, teacName ,subTime, day, week, part, site from v_persche as vp where vp.stu_id=#{id}"
			+ " Union Select courName, teacName ,subTime, day, week, part, site from v_schedule as vs inner join student s"
			+ " on s.clas_id=vs.clas_id where s.stu_id=#{id}")
	public List<Schedule> findScheduleById(int id);

	/**
	 * 获得一个学生在time学期的课表信息
	 * 
	 * @param id
	 *            学生Id
	 * @param time
	 *            学期
	 * @return
	 */
	@Select("Select courName, teacName ,subTime, day, week, part, site from v_persche as vp where vp.stu_id=#{id} and vp.subTime=#{time}"
			+ " Union Select courName, teacName ,subTime, day, week, part, site from v_schedule as vs inner join student s"
			+ " on s.clas_id=vs.clas_id where s.stu_id=#{id} and vs.subTime=#{time}")
	public List<Schedule> findScheduleByTime(@Param("id") int id, @Param("time") String time);

	@Select("Select courName, teacName ,subTime, day, week, part, site from v_persche as vp where vp.stu_id=#{id} and vp.subTime=#{time} and vp.week=#{week} and vp.day=#{day}"
			+ " Union Select courName, teacName ,subTime, day, week, part, site from v_schedule as vs inner join student s"
			+ " on s.clas_id=vs.clas_id where s.stu_id=#{id} and vs.subTime=#{time} and vs.week=#{week} and vs.day=#{day}")
	public List<Schedule> findSchedule(@Param("id") int id, @Param("time") String time, @Param("week") int week,
			@Param("day") int day);

	@Select("Select courName, teacName ,subTime, day, week, part, site from v_persche as vp where vp.stu_id=#{id} and vp.subTime=#{time} and vp.week=#{week}"
			+ " Union Select courName, teacName ,subTime, day, week, part, site from v_schedule as vs inner join student s"
			+ " on s.clas_id=vs.clas_id where s.stu_id=#{id} and vs.subTime=#{time} and vs.week=#{week}")
	public List<Schedule> findScheduleByTimeWeek(@Param("id") int id, @Param("time") String time,
			@Param("week") int week);
	
	@Select("Select courName, teacName ,subTime, day, week, part, site from v_persche as vp where vp.stu_id=#{id} and vp.subTime=#{time} and vp.week=#{week} and vp.day=#{day} and vp.part=#{part}"
			+ " Union Select courName, teacName ,subTime, day, week, part, site from v_schedule as vs inner join student s"
			+ " on s.clas_id=vs.clas_id where s.stu_id=#{id} and vs.subTime=#{time} and vs.week=#{week} and vs.day=#{day} and vs.part=#{part}")
	public Schedule findScheduleBy(@Param("id") int id, @Param("time") String time, @Param("week") int week,
			@Param("day") int day,@Param("part") String part);

	/**
	 * 获得一个学生的成绩
	 * 
	 * @param id
	 *            学生Id
	 * @return
	 */
	@Select("select * from v_grade where stuId=#{id}")
	public List<Grade> findGradeById(int id);

	/**
	 * 在time学期的一个学生成绩
	 * 
	 * @param id
	 *            学生Id
	 * @param time
	 *            学期
	 * @return
	 */
	@Select("select * from v_grade where stuId=#{id} and subTime=#{time}")
	public List<Grade> findGradeByTime(@Param("id") int id, @Param("time") String time);

	/**
	 * time学期的班级排名
	 * 
	 * @param clasId
	 *            班级Id
	 * @param time
	 *            学期
	 * @return
	 */
	@Select("select * from v_poinscor vp where clasId=#{clasId} and subTime=#{time}  order by sum desc")
	public List<PoinScor> findClassRank(@Param("clasId") int clasId, @Param("time") String time);

	/**
	 * period届majoId专业time学期的排名
	 * 
	 * @param majoId
	 *            专业Id
	 * @param time
	 *            学期
	 * @param period
	 *            届
	 * @return
	 */
	@Select("select * from v_poinscor vp where majoId=#{majoId} and period=#{period} and subTime=#{time} order by sum desc")
	public List<PoinScor> findMajorRank(@Param("majoId") int majoId, @Param("time") String time,
			@Param("period") int period);

	/**
	 * 获得一个学生的挂科情况
	 * 
	 * @param id
	 *            学生Id
	 * @return
	 */
	@Select("select * from v_grade where stuId=#{id} and judge='没过'")
	public List<Grade> findFailCourse(int id);

	/**
	 * 重修过
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from v_grade where stuId=#{id} and judge='重修过'")
	public List<Grade> findFailCourseOver(int id);

}
