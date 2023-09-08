package org.prizrakk.db;

import org.prizrakk.Main;
import org.prizrakk.manager.ConfigManager;
import org.prizrakk.manager.GDBV;

import java.sql.*;


public class Database {
    private static Main main;
    public Database(Main main) {
        Database.main = main;
    }

    private static Connection connection;




    public Connection getConnection() throws  SQLException{

        if(connection != null){
            return connection;
        }

        ConfigManager config = new ConfigManager();
        String url = config.getProperty("jdbc");
        String user = config.getProperty("login");
        String password = config.getProperty("password");


        connection = DriverManager.getConnection(url, user, password);
        main.log.info("Подключение успешно произведено!");

        return connection;
    }

    public void initializeDatabase() throws SQLException{
        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE `user_info` (\n" +
                "\t`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "\t`UUID` INT,\n" +
                "\t`name` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "\t`balance` INT,\n" +
                "\t`xp` INT,\n" +
                "\t`level` INT,\n" +
                "\t`warn_count` INT,\n" +
                "\t`ban` INT\n" +
                ");";

        statement.execute(sql);

        statement.close();
        main.log.info("Проверка базы данных прошло успешно!");
    }
    public GDBV findPlayerStatsByNICK(String UUID) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM user_info WHERE UUID = ?");
        statement.setInt(1, Integer.parseInt(UUID));

        ResultSet resultSet = statement.executeQuery();

        GDBV GDBV;
        if(resultSet.next()){

            //GDBV = new GDBV(resultSet.getString("nick"), resultSet.getInt(), resultSet.getString("prefix"), resultSet.getInt("rep"), resultSet.getInt("deaths"), resultSet.getInt("kills"), resultSet.getLong("blocks_broken"), resultSet.getDouble("balance"), resultSet.getDate("last_login"), resultSet.getDate("last_logout"));
            GDBV = new GDBV(resultSet.getString("name"), resultSet.getInt("balance"), resultSet.getInt("warn_count"), resultSet.getInt("level"), resultSet.getInt("xp"), resultSet.getInt("ban") ,resultSet.getString("UUID"));
            //String name, int id, int balance, int warn_count, int level, int xp, int ban, int UUID
            statement.close();

            return GDBV;
        }


        statement.close();

        return null;
    }

    /*
                "\t`id` INT,\n" +
                "\t`UUID` INT,\n" +
                "\t`name` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "\t`balance` INT,\n" +
                "\t`xp` INT,\n" +
                "\t`level` INT,\n" +
                "\t`warn_count` INT,\n" +
                "\t`ban` INT\n" +
                ");";
     */
    public void createUserStats(GDBV GDBV) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO player_stats( UUID, name, balance, xp, level, warn_count, ban) VALUES (?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, GDBV.getUUID());
        statement.setString(2, GDBV.getName());
        statement.setDouble(3, GDBV.getBalance());
        statement.setInt(4, GDBV.getXp());
        statement.setInt(5, GDBV.getLevel());
        statement.setInt(6, GDBV.getWarn_count());
        statement.setInt(7, GDBV.getBan());

        statement.executeUpdate();

        statement.close();

    }

    public void updateUserStats(GDBV GDBV) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE user_info SET warn_count = ?, xp = ?, level = ?, name = ?, balance = ?, ban = ? WHERE UUID = ?");
        statement.setInt(1, GDBV.getWarn_count());
        statement.setInt(2, GDBV.getXp());
        statement.setInt(3, GDBV.getLevel());
        statement.setString(4, GDBV.getName());
        statement.setDouble(5, GDBV.getBalance());
        statement.setInt(6, GDBV.getBan());
        statement.setString(7, GDBV.getUUID());

        statement.executeUpdate();

        statement.close();

    }
}