package com.group.main.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;
import com.group.main.dao.MainDAO;

public class MainService {
	private MainDAO dao;
	private static MainService service;
	public static String envProp;

	private MainService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("mainDAO");
//			System.out.println(className);
			/*
			 * 리플랙션 기법 이용하여 객체 생성 소스코드를 재컴파일하지 않기 위해 리플랙션 기법 이용하는 것임!
			 */
			Class c = Class.forName(className); // JVM에 로드
			dao = (MainDAO) c.newInstance(); // 객체 생성
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static MainService getInstance() {
		if(service==null) {
			service = new MainService();
		}
		return service;
	}

	/**
	 * 로그인한 사원의 프로필을 조회한다
	 * @param id 로그인한 사원의 아이디
	 * @return 사원의 프로필(이름과 사번)
	 * @throws FindException
	 */
	public Employee showProfile(String id) throws FindException {
		return dao.selectById(id);
	}
	
	/**
	 * 로그인한다
	 * @param id 로그인할 아이디
	 * @param pwd 로그인할 비밀번호
	 * @return 로그인한 사원의 정보(사번,비밀번호,이름)
	 * @throws FindException
	 */
	public Employee login(String id, String pwd) throws FindException {
		Employee emp = dao.selectById(id);
		if (!emp.getPassword().equals(pwd)) {
			throw new FindException("로그인에 실패하였습니다");
		}
		return emp;
	}

	/**
	 * 기안일이 오래된 순으로 결재예정 문서 5건을 조회한다
	 * @param id 로그인한 사원의 아이디
	 * @return 기안일이 오래된 순의 결재 예쩡 문서 5건
	 * @throws FindException
	 */
	public List<Document> showDocExpected(String id) throws FindException {
		return dao.selectDocument(id);
	}

	/**
	 * 최근 올라온 게시글 순으로 게시글 5건을 조회한다
	 * @return 최근 올라온 게시글 순의 게시글 5건
	 * @throws FindException
	 */
	public List<Board> showRecentBd() throws FindException {
		return dao.selectBoard();
	}

	/**
	 * 로그인한 사원의 휴가 정보를 조회한다
	 * @param id 로그인한 사원의 아이디
	 * @return 로그인한 사원의 휴가 정보
	 * @throws FindException
	 */
	public Leave showLeave(String id) throws FindException {
		return dao.selectLeave(id);
	}

	/**
	 * 오늘의 일정 중 시작 시간 순으로 5건을 조회한다
	 * @param emp 로그인한 사원의 사번 및 부서번호를 담고 있는 객체
	 * @return 오늘의 일정 중 시작 시간 순으로 5건
	 * @throws FindException
	 */
	public List<Schedule> showTodaySkd(Employee emp) throws FindException {
		return dao.selectSchedule(emp);
	}
}
