package Database;

public class DBSingletonFactory {
    private static final DBSingleton connDB =
//            new DBSingleton("jdbc:sqlserver://10.10.11.165:1433;databaseName=telephoneCompany;user=dbms;password=Dbms_123");
            new DBSingleton("jdbc:sqlserver://10.0.0.26:1433;databaseName=telephoneCompany;user=dbms;password=Dbms_123");

    private DBSingletonFactory() {
    }

    public static DBSingleton getInstanceDB() {
        return connDB;
    }
}
