package com.avenger.edu.stu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.avenger.edu.stu.model.MajoCourInfo;
import com.avenger.edu.stu.model.SeleCourInfo;
import com.avenger.edu.stu.model.Student;

@Mapper
public interface StuMapper {

	/**
	 * 登录 如果返回值不为null，表明登录成功
	 * 
	 * @param s
	 * @return 学生姓名
	 */
	@Select("select stu_name from student where stu_id=#{id} and stu_password=#{password}")
	@Results(value = { @Result(column = "stu_name", property = "name") })
	public String login(Student s);

	/**
	 * 通过学号获得学生个人信息
	 * 
	 * @param id
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
	public Student load(int id);

	/**
	 * 修改学生密码
	 * 
	 * @param id
	 * @param password
	 */
	@Update("update student set stu_password=#{password} where stu_id=#{id}")
	public void updateStuPW(int id, String password);

	/**
	 * 通过专业和入学时间查看大学期间所需学的必修课程
	 * 
	 * @param major
	 * @param time
	 * @return
	 */
	@Select("select * from v_majocourinfo where mcMajorName=#{major} and mcMajorTime=#{time}")
	public List<MajoCourInfo> findMCInfo(String major, String time);

	/**
	 * 查看可选修课程，进行选课
	 * @return
	 */
	@Select("select * from v_selecourinfo")
	public List<SeleCourInfo> findSeleCourAll();
	
	/**
	 * 获得一个学生的选修课程信息
	 * @param id
	 * @return
	 */
	@Select("select scId,scCourName,scTeacName,scCourCredit,scCourPeriod,scTeacTitle from selec as s inner join v_selecourinfo as v on  s.selecour_id=v.scId where s.stu_id=#{id};")
	public List<SeleCourInfo> findSeleCourByStu(int id);
}