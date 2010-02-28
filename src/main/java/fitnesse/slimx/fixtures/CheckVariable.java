package fitnesse.slimx.fixtures;

import java.util.List;

public class CheckVariable extends ObjectTable {

    public CheckVariable(Object object) {
        super(object);
    }

    public CheckVariable(List<Object> objectList, String qualifier) {
        super(objectList, qualifier);
    }
}