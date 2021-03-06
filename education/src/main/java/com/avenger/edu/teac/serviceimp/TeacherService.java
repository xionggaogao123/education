package com.avenger.edu.teac.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenger.edu.teac.mapper.TeacherMapper;
import com.avenger.edu.teac.model.CourseTable;
import com.avenger.edu.teac.model.Grade;
import com.avenger.edu.teac.model.Grade1;
import com.avenger.edu.teac.model.Notice;
import com.avenger.edu.teac.model.ReStudy;
import com.avenger.edu.teac.model.Student;
import com.avenger.edu.teac.model.TeacTable;
import com.avenger.edu.teac.model.Teacher;
import com.avenger.edu.teac.service.TeacService;

@Service
public class TeacherService implements TeacService{

	@Autowired
	TeacherMapper mapper;
	
	public int findTeacId(int id) {
		return this.mapper.findTeacId(id);
	}
	
	public int findTeacPassword(String pwd) {
		return this.mapper.findTeacPassword(pwd);
	}
	
	public String[] findMajorName() {
		return this.mapper.findMajorName();
	}
	
	public String[] findGradeNum() {
		return this.mapper.findGradeNum();
	}
	
	public TeacTable[] findTeacTable(String gradeNum,int period,String major) {
		return this.mapper.findTeacTable(gradeNum, period, major);
	}
	
	public int findStuId(int id) {
		return this.mapper.findStuId(id);
	}
	
	public int findTeaId(int id) {
		return this.mapper.findTeaId(id);
	}
	
	public Notice[] findNotice() {
		return this.mapper.findNotice();
	}
	
	@Override
	public Teacher findOne(int id) {
		
		return this.mapper.findOne(id);
	}

	@Override
	public List<Teacher> findAll() {
		return this.mapper.findAll();
	}

	@Override
	public void changePwd(String pwd,int id) {

		this.mapper.changePwd(pwd, id);
	}

	@Override
	public Teacher adminTeac(int id, String pwd) {
		if(id<=0||"".equals(id)) {
			return null;
		}
		if(pwd==null||"".equals(pwd)) {
			return null;
		}
		return this.mapper.adminTeac(id, pwd);
	}
	
	@Override
	public void resultInput(Grade gra) {

		this.mapper.resultInput(gra);
	}

	@Override
	public void resultInput2(Grade1 gra) {

		this.mapper.resultInput2(gra);
	}

	@Override
	public List<CourseTable> findTable(int id) {
		return this.mapper.findTable(id);
	}
	
	@Override
	public ReStudy[] queryStudy(int id,int off) {
		return this.mapper.queryStudy(id,off);
	}
	
	public int getLength(int id) {
		return this.mapper.getLength(id);
	}
	
}
