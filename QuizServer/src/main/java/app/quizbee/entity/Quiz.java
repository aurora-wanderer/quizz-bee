package app.quizbee.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quiz extends Entity {

    private Integer ID;
    private String name;
    private Date createdDate;
    private Integer totalQuestion;
    private Integer collections_ID;

    @Override
    public Object[] toInsertData() {
        return new Object[]{
            name,
            createdDate,
            totalQuestion
        };
    }

    @Override
    public Object[] toUpdateData() {
        return new Object[] {
            name,
            createdDate,
            totalQuestion,
            collections_ID,
            ID
        };
    }

    @Override
    public Object[] toDeleteData() {
        return new Object[]{ID};
    }

    @Override
    public String toString() {
        return name + '\n'
                + new SimpleDateFormat("dd-mm-yyyy")
                        .format(createdDate)
                + totalQuestion.toString() + '\n';
    }
}
