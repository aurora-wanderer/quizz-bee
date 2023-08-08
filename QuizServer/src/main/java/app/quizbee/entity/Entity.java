package app.quizbee.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    public abstract Object[] toInsertData();

    public abstract Object[] toUpdateData();

    public abstract Object[] toDeleteData();
}
