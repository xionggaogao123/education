package com.avenger.edu.teac.service;

import java.util.List;

import com.avenger.edu.teac.model.CourseTable;
import com.avenger.edu.teac.model.Grade;
import com.avenger.edu.teac.model.Grade1;
import com.avenger.edu.teac.model.ReStudy;
import com.avenger.edu.teac.model.Student;
import com.avenger.edu.teac.model.Teacher;

public interface TeacService {

	/**
	 * 查找某个老师的信息
	 * @return
	 */
	public Teacher findOne(int id);
	
	/**
	 * 查找所有教师的信息
	 * @return
	 */
	public List<Teacher> findAll();
	
	/**
	 * 修改密码
	 * @param 教师编号
	 */
	public void changePwd(String pwd,int id);
	
	/**
	 * 登录
	 * @param 教师编号
	 * @param 教师密码
	 * @return
	 */
	public Teacher adminTeac(int id,String pwd);

	/**
	 * 找回密码
	 * @param 教师编号
	 * @param 身份证后六位
	 * @return
	 */
	//public boolean findPwd(int id,String card);
	
	/**
	 * 录入必修成绩
	 */
	public void resultInput(Grade gra);
	
	/**
	 * 录入选修成绩
	 */
	public void resultInput2(Grade1 gra);
	
	/**
	 * 查看教师课表
	 * @return
	 */
	public List<CourseTable> findTable(int id);
	
	/**
	 * 根据id查找某个重修的学生
	 * @param id
	 * @return
	 */
	public ReStudy[] queryStudy(int id,int off);
	
}
