package app.quizbee.service;

import app.quizbee.controller.Connector;
import app.quizbee.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService implements Service<User> {

    private final Connector connector = new Connector();

    public List<User> selectAll() {
        final String query = "select * from [USER]";
        return select(query);
    }

    public Boolean find(User user) {
        return selectAll().stream()
                .anyMatch(data -> data.equals(user));
    }

    public User find(String username) {
        return selectAll().stream()
                .filter(row -> row.getUsername()
                .equalsIgnoreCase(username))
                .findAny()
                .orElse(null);
    }

    public String generateCode() {
        DecimalFormat df = new DecimalFormat("0000");
        Random ran = new Random();
        return df.format(ran.nextInt(10000));
    }

    @Override
    public List<User> select(String query, Object... args) {
        List<User> list = new ArrayList<>();
        try {
            ResultSet rs = connector.executeQuery(query, args);
            while (rs.next()) {
                User model = readFromResultSet(rs);
                list.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private User readFromResultSet(ResultSet rs) throws SQLException {
        User modal = new User();

        modal.setID(rs.getInt(1));
        modal.setUsername(rs.getNString(2));
        modal.setPassword(rs.getNString(3));
        modal.setEmail(rs.getNString(4));
        modal.setFullname(rs.getNString(5));
        modal.setGender(rs.getBoolean(6));
        modal.setStatus(rs.getString(7));
        modal.setActive(rs.getString(8));

        return modal;
    }

    @Override
    public void insert(User modal) {
        final String insertQuery = "INSERT INTO [dbo].[USER] "
                + "([USERNAME],[_PASSWORD],[EMAIL],[FULLNAME],[_STATUS],[_ACTIVE])"
                + "VALUES(?,?,?,?,?,?)";

        try {
            connector.executeUpdate(insertQuery, modal.toInsertData());
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(User modal) {
        final String query = "DELETE FROM [dbo].[USER] where ID = ?, [USERNAME] = ?";
        try {

            connector.executeUpdate(query, modal.toDeleteData());
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(User modal) {
        final String query = "update [USER] "
                + "set [USERNAME] = ?, [_PASSWORD] = ?, [EMAIL] = ?, [FULLNAME] = ?, [GENDER] = ?"
                + "where ID = ?";
        try {

            connector.executeUpdate(query, modal.toUpdateData());
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void login(User t) {
        String setOnline = "UPDATE [dbo].[USER] "
                + " SET [_ACTIVE] = 'Online' "
                + " WHERE  [USERNAME] = ? ";
        try {
            connector.executeUpdate(setOnline, t.getUsername());
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void logout(User user) {
        String offline = "UPDATE [dbo].[USER] "
                + " SET [_ACTIVE] = 'Offline' "
                + " WHERE  [USERNAME] = ? ";
        try {
            connector.executeUpdate(offline, user.getUsername());
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
