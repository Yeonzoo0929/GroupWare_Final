package com.group.main.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;

@Repository("mainDAO")
public class MainDAOOracle implements MainDAO {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public Employee selectById(String id) throws FindException{
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
			Employee emp = session.selectOne("com.group.main.MainPageMapper.selectById", id);
			return emp;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
	}
		@Override
		public Employee selectByIdAdmin(String id) throws FindException{
			SqlSession session = null;
			
			try {
				session = sqlSessionFactory.openSession();
				Employee emp = session.selectOne("com.group.main.MainPageMapper.selectByIdAdmin", id);
				return emp;
			}catch (Exception e) {
				throw new FindException(e.getMessage());
			}finally {
				if(session!=null) {
					session.close();
				}
			}
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//		String selectByIdSQL = "SELECT employee_id, name, password, department_id \r\n" + "FROM employee\r\n" + "WHERE employee_id=?";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		Employee emp = new Employee();
//		try {
//			pstmt = con.prepareStatement(selectByIdSQL);
//			pstmt.setString(1, id);
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				emp.setPassword(rs.getString("password"));;
//				Department dept = new Department();
//				dept.setDepartment_id(rs.getString("department_id"));
//				emp.setDepartment(dept);
//			} else {
//				throw new FindException("해당 정보를 찾을 수 없습니다");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//		return emp;
	}

	@Override
	public Leave selectLeave(String id) throws FindException{
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
			Leave leave = session.selectOne("com.group.main.MainPageMapper.selectLeave", id);
			return leave;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//
//		String selectLeaveSQL = "SELECT employee_id, grant_days, (grant_days-remain_days), remain_days\r\n" + "FROM leave\r\n"
//				+ "WHERE employee_id=?";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		Leave leave = new Leave();
//		try {
//			pstmt = con.prepareStatement(selectLeaveSQL);
//			pstmt.setString(1, id);
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				leave.setEmployeeId(rs.getString("employee_id"));
//				leave.setGrantDays(rs.getInt("grant_days"));
//				leave.setUseDays(rs.getInt(2));
//				leave.setRemainDays(rs.getInt("remain_days"));
//			} else {
//				throw new FindException("휴가 정보를 찾을 수 없습니다");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//
//		return leave;
	}

	@Override
	public List<Document> selectDocument(String id) throws FindException {
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
			List<Document> docList = session.selectList("com.group.main.MainPageMapper.selectExpectedDoc", id);
			return docList;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//
//		String selectDocumentSQL = "SELECT document_no,document_title,draft_date\r\n"
//				+ "FROM (select rownum rn, a.*\r\n"
//				+ "    FROM ((SELECT d.document_title,d.document_no, draft_date, a.employee_id,ap_type \r\n"
//				+ "            FROM approval a JOIN document d ON a.document_no=d.document_no \r\n"
//				+ "            WHERE a.employee_id=? AND ap_type='대기')\r\n" + "            UNION ALL \r\n"
//				+ "          (SELECT d.document_title,d.document_no, draft_date, ag.employee_id,ap_type \r\n"
//				+ "           FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "           WHERE ag.employee_id=? AND ap_type='대기') ORDER BY draft_date asc)a\r\n" + "    )\r\n"
//				+ "WHERE rn BETWEEN 1 AND 5";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<Document> docList = new ArrayList<Document>();
//		try {
//			pstmt = con.prepareStatement(selectDocumentSQL);
//			pstmt.setString(1, id);
//			pstmt.setString(2, id);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				Document doc = new Document();
//				doc.setDocumentNo(rs.getString("document_no"));
//				doc.setDocumentTitle(rs.getString("document_title"));
//				doc.setDraftDate(rs.getDate("draft_date"));
//
//				docList.add(doc);
//			}
//			if(docList.size()==0) {
//				throw new FindException("결재 예정 문서가 없습니다");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//		return docList;
	}

	@Override
	public List<Board> selectBoard() throws FindException{
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
			List<Board> bdList = session.selectList("com.group.main.MainPageMapper.selectRecentBoard");
			return bdList;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//
//		String selectBoardSQL = "SELECT bd_no,bd_title,employee_id,bd_date\r\n" + "FROM (SELECT rownum rn, b.* \r\n"
//				+ "        FROM (SELECT * FROM board ORDER BY bd_date desc) b\r\n" + "        )\r\n"
//				+ "WHERE rn BETWEEN 1 AND 5";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<Board> bdList = new ArrayList<Board>();
//
//		try {
//			pstmt = con.prepareStatement(selectBoardSQL);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				Board bd = new Board();
//				bd.setBdNo(rs.getString("bd_no"));
//				bd.setBdTitle(rs.getString("bd_title"));
//				Employee emp = new Employee();
//				emp.setEmployeeId(rs.getString("employee_id"));
//				bd.setWriter(emp);
//				bd.setBdDate(rs.getTimestamp("bd_date"));
//
//				bdList.add(bd);
//			}
//			if(bdList.size()==0) {
//				throw new FindException("게시글이 없습니다");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//		return bdList;
	}

	@Override
	public List<Schedule> selectSchedule(Employee emp) throws FindException{
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
			List<Schedule> skdList = session.selectList("com.group.main.MainPageMapper.selectTodaySkd",emp);
			return skdList;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//
//		String selectScheduleSQL = "SELECT skd_start_date ,skd_title\r\n"
//				+ "FROM (select skd_start_date ,skd_title, \r\n"
//				+ "    row_number() OVER (PARTITION BY e.employee_id ORDER BY skd_start_date ASC) rn\r\n"
//				+ "    FROM schedule s\r\n" + "    JOIN employee e ON s.employee_id=e.employee_id\r\n"
//				+ "    JOIN department d ON e.department_id=d.department_id\r\n"
//				+ "    WHERE ((d.department_id=? AND s.skd_share='t') OR (s.employee_id=? AND s.skd_share='p'))\r\n"
//				+ "    AND to_char(skd_start_date,'yyyy-mm-dd') =to_char(sysdate,'yyyy-mm-dd'))\r\n"
//				+ "WHERE rn BETWEEN 1 AND 5";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<Schedule> skdList = new ArrayList<Schedule>();
//
//		try {
//			pstmt = con.prepareStatement(selectScheduleSQL);
//			pstmt.setString(1, emp.getDepartment().getDepartmentId());
//			pstmt.setString(2, emp.getEmployeeId());
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				Schedule skd = new Schedule();
//				skd.setSkd_start_date(rs.getTimestamp("skd_start_date"));
//				skd.setSkd_title(rs.getString("skd_title"));
//
//				skdList.add(skd);
//			}
//			if(skdList.size()==0) {
//				throw new FindException("일정이 없습니다");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//		return skdList;
	}
}
