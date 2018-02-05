package Database;

public class DBTasks extends Database {

    void connect() throws Exception {
        conn = DBconnector.getconnector().getconnection();
    }

    private void checkConn() throws Exception {
        if (conn == null || conn.isClosed()) {
            connect();
        }
    }
}
