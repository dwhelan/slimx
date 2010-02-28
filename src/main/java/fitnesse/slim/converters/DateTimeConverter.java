package fitnesse.slim.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateTimeConverter extends BaseConverter {

    // TODO: Ideally we would like to specify our own format here ... something
    // like:
    // DateTimeFormat.forPattern("MMMM d, YYYY h:mm:dd a");
    //
    // Unfortunately, Slim serializes data using an objects.toString() in query
    // tables so the formatter defined here should be consistent with that which
    // means using the
    // ISO format.
    //
    // See Yahoo FitNesse group for more info on this issue:
    // http://tech.groups.yahoo.com/group/fitnesse/message/14921
    private DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    public DateTimeConverter() {
        super(DateTime.class);
    }

    @Override
    public Object fromString(String dateTimeString) {
        return formatter.parseDateTime(dateTimeString);
    }

    @Override
    public String toString(Object dateTime) {
        return ((DateTime) dateTime).toString(formatter);
    }
}
