package fitnesse.slim;

public class SlimExtensions {

    public static String convertToString(Object object) {
        if (object == null)
            return "null";

        Converter converter = ConverterSupport.getConverter(object.getClass());
        if (converter != null)
            return converter.toString(object);

        return object.toString();
    }

    public static Object convertFromString(String string, Class<?> clazz) {
        Converter converter = ConverterSupport.getConverter(clazz);
        if (converter != null)
            return converter.fromString(string);

        return string.toString();
    }
}
