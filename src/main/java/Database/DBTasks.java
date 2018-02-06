package Database;

import entity.Staff;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBTasks extends Database {

    void connect() throws Exception {
        conn = DBconnector.getconnector().getconnection();
    }

    private void checkConn() throws Exception {
        if (conn == null || conn.isClosed()) {
            connect();
        }
    }
//    public List<Staff> getStaffs(){
//        List<Staff> ret = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        final String sql = "select * from staff";
//        List<Object> params = new ArrayList<Object>();
//        try {
//            checkConn();
//            ps = preparedStatement(sql, params.toArray());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                if (ret == null) {
//                    ret = new ArrayList();
//                }
//                Staff staff = new Staff(rs.getInt("ID"), rs.getString("staffName"));
//                ret.add(staff);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            close(ps);
//            close(rs);
//        }
//        return ret;
//    }
}
