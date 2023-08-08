package app.quizbee.service;

import app.quizbee.controller.Connector;
import app.quizbee.entity.Question;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionService implements Service<Question> {

    private final Connector connector = new Connector();

    @Override
    public List<Question> select(String query, Object... args) {
        List<Question> list = new ArrayList<>();
        try {
            ResultSet rs = connector.executeQuery(query, args);
            while (rs.next()) {
                Question model = readFromResultSet(rs);
                list.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private Question readFromResultSet(ResultSet rs) throws SQLException {
//        Question modal = new Question();
//        modal.setValue(rs.getString(3));
//        modal.setType(rs.getBoolean(4)
//                ? Question.Type.MANY
//                : Question.Type.ONE);

//        Question.Answer ans = modal.getAns();
//        ans.setAns1(rs.getString(5));
//        ans.setAns2(rs.getString(6));
//        ans.setAns3(rs.getString(7));
//        ans.setAns4(rs.getString(8));
//        ans.setCorrects(convertToBool(rs.getString(9)));

        return null;
    }

    public List<Question> getQuestion() {
        final String query = "{call GETQUESTION}";
        return select(query);
    }

    @Override
    public void insert(Question modal) {
//        final String query = "{call INSERT_QUESTION(?,?,?,?,?,?,?)}";
//
//        try {
//            connector.executeUpdate(query, modal.toInsertData());
//        } catch (SQLException ex) {
//            Logger.getLogger(QuestionService.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void delete(Question modal) {

    }

    @Override
    public void update(Question modal) {

    }

    private Boolean[] convertToBool(String string) {
        if (string.length() <= 0) {
            return null;
        }

        String[] values = string.split(",|, ");

        Boolean[] bools = new Boolean[values.length];

        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            bools[i] = Boolean.valueOf(value);
        }

        return bools;
    }
}
