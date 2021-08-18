package com.group.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group.board.dto.Board;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;
import com.group.sql.MyConnection;

public class BoardDAOOracle implements BoardDAO {
	@Override
	public List<Board> selectAll() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectAllSQL = "select*\r\n" + "FROM (SELECT rownum rn, a.* \r\n" + "    FROM ( SELECT *\r\n"
				+ "            FROM board b \r\n" + "            JOIN employee e ON b.employee_id = e.employee_id\r\n"
				+ "            ORDER BY bd_date desc\r\n" + "        ) a\r\n" + "    )\r\n";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> bdList = new ArrayList<Board>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board bd = new Board();

				bd.setBdNo(rs.getString("bd_no"));

				Employee emp = new Employee();
				emp.setEmployeeId(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				bd.setWriter(emp);

				bd.setBdTitle(rs.getString("bd_title"));
				bd.setBdDate(rs.getTimestamp("bd_date"));
				bdList.add(bd);
			}
			if (bdList.size() == 0) {
				throw new FindException("게시글이 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return bdList;
	}

	@Override
	public List<Board> selectAll(int currentPage) throws FindException {
		int cnt_per_page = 10;
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectAllPageSQL = "select * \r\n" + "FROM (SELECT rownum rn, a.* \r\n" + "    FROM ( SELECT *\r\n"
				+ "            FROM board b \r\n" + "            JOIN employee e ON b.employee_id = e.employee_id\r\n"
				+ "            ORDER BY  bd_date desc\r\n" + "        ) a\r\n" + "    )\r\n"
				+ "WHERE rn BETWEEN start_row(?,?) AND end_row(?,?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> bdList = new ArrayList<Board>();
		try {
			pstmt = con.prepareStatement(selectAllPageSQL);
			pstmt.setInt(1, currentPage);
			pstmt.setInt(2, cnt_per_page);
			pstmt.setInt(3, currentPage);
			pstmt.setInt(4, cnt_per_page);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board bd = new Board();
				bd.setBdNo(rs.getString("bd_no"));
				bd.setBdTitle(rs.getString("bd_title"));
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				bd.setWriter(emp);
				bd.setBdDate(rs.getTimestamp("bd_date"));

				bdList.add(bd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return bdList;
	}

	@Override
	public List<Board> selectByWord(String category, String word) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectByWordSQL = "SELECT * \r\n" + "FROM board b \r\n"
				+ "JOIN employee e ON b.employee_id = e.employee_id\r\n" + "WHERE " + category
				+ " LIKE ? ORDER BY bd_no desc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> bdList = new ArrayList<Board>();
		try {
			pstmt = con.prepareStatement(selectByWordSQL);
			pstmt.setString(1, "%" + word + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board bd = new Board();
				bd.setBdNo(rs.getString("bd_no"));
				bd.setBdTitle(rs.getString("bd_title"));
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				bd.setWriter(emp);
				bd.setBdDate(rs.getTimestamp("bd_date"));
				bdList.add(bd);
			}
			if (bdList.size() == 0) {
				throw new FindException("일치하는 내용이 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return bdList;
	}

	@Override
	public Board selectBdInfo(String bd_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectBdInfoSQL = "SELECT *\r\n" + "FROM board b \r\n"
				+ "JOIN employee e ON b.employee_id = e.employee_id\r\n" + "WHERE b.bd_no=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board bd = new Board();
		try {
			pstmt = con.prepareStatement(selectBdInfoSQL);
			pstmt.setString(1, bd_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				bd.setBdNo(rs.getString("bd_no"));
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				bd.setWriter(emp);
				bd.setBdTitle(rs.getString("bd_title"));
				bd.setBdContent(rs.getString("bd_content"));
				bd.setBdDate(rs.getTimestamp("bd_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}
		return bd;
	}

	@Override
	public void insert(Board bd) throws AddException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}

		String insertSQL = "INSERT INTO " + "board(bd_no,employee_id,bd_title,bd_content) "
				+ "VALUES('BD'||BD_SEQ.NEXTVAL,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setString(1, bd.getWriter().getEmployeeId());
			pstmt.setString(2, bd.getBdTitle());
			pstmt.setString(3, bd.getBdContent());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	@Override
	public void update(Board bd) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}

		String str = "";

		if (bd.getBdTitle() != null) {
			str += " bd_title='" + bd.getBdTitle() + "',";
		}

		if (bd.getBdContent() != null) {
			str += " bd_content='" + bd.getBdContent() + "',";
		}
		String updateSQL = "UPDATE board SET " + str.substring(0, str.length() - 1)
				+ " ,bd_date=SYSTIMESTAMP WHERE bd_no=? AND employee_id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1, bd.getBdNo());
			pstmt.setString(2, bd.getWriter().getEmployeeId());
			int rowcnt = pstmt.executeUpdate();
			if (rowcnt == 1) {
				System.out.println("내용이 변경되었습니다");
			} else {
				throw new ModifyException("내용이 변경되지 않았습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	@Override
	public void delete(Board bd) throws RemoveException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}

		String deleteSQL = "DELETE FROM board WHERE bd_no=? AND employee_id=?";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setString(1, bd.getBdNo());
			pstmt.setString(2, bd.getWriter().getEmployeeId());
			pstmt.executeUpdate();

			System.out.println("게시글을 삭제하였습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}
	}
}
