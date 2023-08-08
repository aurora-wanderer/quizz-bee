package app.quizbee.service;

import app.quizbee.controller.Connector;
import app.quizbee.entity.Quiz;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizService implements Service<Quiz> {

    private final Connector connector = new Connector();

    @Override
    public List<Quiz> select(String query, Object... args) {
        return new ArrayList<>();
    }

    @Override
    public void insert(Quiz modal) {
        final String query = "insert into Quiz values(?,?,?)";
        try {
            connector.executeUpdate(query, modal.toInsertData());
        } catch (SQLException ex) {
            Logger.getLogger(QuizService.class.getName())
                    .log(Level.SEVERE, "Can't insert into database", ex);
        }
    }

    @Override
    public void delete(Quiz modal) {
        final String query = "DELETE FROM [dbo].[QUIZ]"
                + "WHERE ID = ?";
        try {
            connector.executeUpdate(query, modal.toDeleteData());
        } catch (SQLException ex) {
            Logger.getLogger(QuizService.class.getName())
                    .log(Level.SEVERE, "Can't insert into database", ex);
        }
    }

    @Override
    public void update(Quiz modal) {
        final String query = "UPDATE [dbo].[QUIZ] "
                + "SET [NAME] = ?,"
                + "[CREATED_DATE] = ?,"
                + "[TOTAL_QUESTIONS] = ?,"
                + "[COLLECTIONS_ID] = ?, "
                + "WHERE ID = ?";
        try {
            connector.executeUpdate(query, modal.toUpdateData());
        } catch (SQLException ex) {
            Logger.getLogger(QuizService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

}
