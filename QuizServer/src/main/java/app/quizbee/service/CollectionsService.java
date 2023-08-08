package app.quizbee.service;

import app.quizbee.controller.Connector;
import app.quizbee.entity.Collections;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollectionsService implements Service<Collections> {

    private Connector connector = new Connector();

    @Override
    public List<Collections> select(String query, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Collections modal) {
        final String query = "insert into [COLLECTIONS] "
                + "values(?,?)";
        try {
            connector.executeUpdate(query, modal.toInsertData());
        } catch (SQLException ex) {
            Logger.getLogger(CollectionsService.class.getName())
                    .log(Level.SEVERE, "Can't insert to database", ex);
        }
    }

    @Override
    public void delete(Collections modal) {
        String query = "delete from [COLLECTIONS] where ID=?";
        try {
            connector.executeUpdate(query, modal.getID());
        } catch (SQLException ex) {
            Logger.getLogger(CollectionsService.class.getName())
                    .log(Level.SEVERE, "Can't delete collections", ex);
        }
    }

    @Override
    public void update(Collections modal) {
        final String query = "update [COLLECTIONS] set [_USER_ID] = ?, [TOTAL_COLLECTION] = ?"
                + "where ID = ?";

        try {
            connector.executeUpdate(query,
                    modal.getUserID(),
                    modal.getTotal_collections(),
                    modal.getID());
        } catch (SQLException ex) {
            Logger.getLogger(CollectionsService.class.getName())
                    .log(Level.SEVERE, "Can't delete collections", ex);
        }
    }

}
