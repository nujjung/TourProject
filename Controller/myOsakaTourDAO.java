package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.myOsakaTour;




public class myOsakaTourDAO {
	public static ArrayList<myOsakaTour> dbArrayMyList = new ArrayList<>();
	public static int insertMyTourList(myOsakaTour myList){
		StringBuffer insertMyTour = new StringBuffer();
		insertMyTour.append("insert into myTourList ");
		insertMyTour.append("(tourID, tourName) ");
		insertMyTour.append("values ");
		insertMyTour.append("(?,?) ");
		
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;		
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(insertMyTour.toString());
			ps.setString(1, myList.getMyOsakaTourID());
			ps.setString(2, myList.getMyOsakaTourName());
			count = ps.executeUpdate();
			if(count == 0) {
				osakaController.callAlert("insert query Error : 쿼리문삽입실패");
				return count;
			}
		} catch (SQLException e) {
			osakaController.callAlert("insert Error : 데이터베이스 삽입실패");
		} finally {
	         // 1.6 자원객체를 닫아야한다.
	         try {
	            if (ps != null) {
	               ps.close();
	            }
	            if (connection != null) {
	               connection.close();
	            }
	         } catch (SQLException e) {
	        	 osakaController.callAlert("자원닫기실패:실패");
	         }
	      }
		return count;
	}

	public static int deleteMyTourList(String myOsakaTourID) {
		String deleteMyTour = "delete from myTourList where tourID = ? ";
		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(deleteMyTour);
			ps.setString(1, myOsakaTourID);
			count = ps.executeUpdate();
			if(count == 0) {
				osakaController.callAlert("delete query Error : 삭제 쿼리문 실패");
				return count;
			}
		} catch (SQLException e) {
			osakaController.callAlert("delete Error : 데이터베이스 삭제 실패");
		} finally {
			try {
	            if (ps != null) {
	               ps.close();
	            }
	            if (connection != null) {
	               connection.close();
	            }
	         } catch (SQLException e) {
	        	 osakaController.callAlert("자원닫기실패:실패");
	         }
		}
		
		return count;
	}
	
	
	public static ArrayList<myOsakaTour> getOsakaData(){
		String selectOsaka = "select * from mytourlist ";
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectOsaka);
			rs = ps.executeQuery();
			if(rs == null) {
				osakaController.callAlert("select 실패:select 쿼리문 실패");
				return null;
			}
			dbArrayMyList.clear();
			while (rs.next()) {
				myOsakaTour myosakatour = new myOsakaTour(rs.getString(1), rs.getString(2));
				dbArrayMyList.add(myosakatour);
			}
		} catch(SQLException e) {
			osakaController.callAlert("삽입실패:데이터베이스 삽입실패");
		} finally {
	         try {
	             if (ps != null) {
	                ps.close();
	             }
	             if (connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             osakaController.callAlert("자원닫기실패:실패");
	          }
		}		
		return dbArrayMyList;
	}
}
