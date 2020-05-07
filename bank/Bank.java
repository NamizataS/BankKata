package bank;


import java.sql.*;
import java.util.Arrays;

public class Bank {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5439/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "1234";

    private static final String TABLE_NAME = "accounts";

    private Connection c;

    public Bank() {
        initDb();

        // TODO
    }

    private void initDb() {
        try {
            Class.forName(JDBC_DRIVER);
            c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Opened database successfully");

            // TODO Init DB

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void closeDb() {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println("Could not close the database : " + e);
        }
    }

    void dropAllTables() {
        try (Statement s = c.createStatement()) {
            s.executeUpdate(
                       "DROP SCHEMA public CASCADE;" +
                            "CREATE SCHEMA public;" +
                            "GRANT ALL ON SCHEMA public TO postgres;" +
                            "GRANT ALL ON SCHEMA public TO public;");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    public void createNewAccount(String name, int balance, int threshold) {
        // TODO
        try( Statement s = c.createStatement()) {
            s.executeUpdate("INSERT INTO " + TABLE_NAME + " " + " (name, balance, threshold,locked)" + " VALUES" + " ('"+name+"','"+balance+"','"+threshold+"',false )"); //query sql to create an account
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String printAllAccounts() {
        // TODO
        String query = "SELECT * FROM "+TABLE_NAME; //the query
        String result = "";
        try( PreparedStatement s = c.prepareStatement(query)) {
            ResultSet r = s.executeQuery();
            while (r.next()) {//checking if there's no more lines
                result += new Account(r.getString(1), r.getInt(2), r.getInt(3),r.getBoolean(4)).toString(); //getting the string

            }
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }

        return result; //printing the result
    }

    public void changeBalanceByName(String name, int balanceModifier) {
        // TODO
        String query = "SELECT balance FROM "+TABLE_NAME+" "+"WHERE name= "+name; //query to get the balance
        try( PreparedStatement s = c.prepareStatement(query) ) {
            ResultSet r = s.executeQuery();
            while (r.next()) {
                try(Statement m = c.createStatement()) {
                    m.executeUpdate("UPDATE "+ TABLE_NAME + " SET "+"balanceModifier= "+balanceModifier); //update the balance
                }
                catch (Exception e ){
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e ){
            System.out.println(e.getMessage());
        }


    }

    public void blockAccount(String name) {
        // TODO
        try(Statement s = c.createStatement()) {
            s.executeUpdate("UPDATE "+ TABLE_NAME + " SET " + " locked = true WHERE name = '"+name+"'"); //block the account with the name
        } catch ( Exception e ) {
            System.out.println( e.getMessage());
        }
    }

    // For testing purpose
    String getTableDump() {
        String query = "select * from " + TABLE_NAME;
        String res = "";

        try (PreparedStatement s = c.prepareStatement(query)) {
            ResultSet r = s.executeQuery();

            // Getting nb colmun from meta data
            int nbColumns = r.getMetaData().getColumnCount();

            // while there is a next row
            while (r.next()){
                String[] currentRow = new String[nbColumns];

                // For each column in the row
                for (int i = 1 ; i <= nbColumns ; i++) {
                    currentRow[i - 1] = r.getString(i);
                }
                res += Arrays.toString(currentRow);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return res;
    }
}
