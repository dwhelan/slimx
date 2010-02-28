package fitnesse.slimx.fixtures;

import java.util.List;

public class ShowVariable extends ObjectTable {

    public ShowVariable(Object object) {
        super(object);
    }

    public ShowVariable(List<Object> objectList, String qualifier) {
        super(objectList, qualifier);
    }
}