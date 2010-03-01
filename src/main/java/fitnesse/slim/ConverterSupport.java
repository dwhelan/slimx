package fitnesse.slim;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.List;

import fitnesse.slim.converters.PropertyEditorConverter;

class ConverterSupport {

  public static Converter getConverter(Class<?> k) {
    Converter c = Slim.converters.get(k);
    if (c != null)
      return c;
    PropertyEditor pe = PropertyEditorManager.findEditor(k);
    if (pe != null) {
      return new PropertyEditorConverter(pe);
    }
    return null;
  }

  // todo refactor this mess
  @SuppressWarnings("unchecked")
  public static Object[] convertArgs(Object[] args, Class<?>[] argumentTypes) {
    Object[] convertedArgs = new Object[args.length];
    for (int i = 0; i < argumentTypes.length; i++) {
      Class<?> argumentType = argumentTypes[i];
      if (argumentType == List.class && args[i] instanceof List) {
        convertedArgs[i] = args[i];
      }
      
      // DPW SlimX changes: should be able to remove above List check with this change
      // Note this code is duplicate of MethodExecutor.convertArgs(...)
      else if (args[i] == null) {
        convertedArgs[i] = args[i];
      } else if (argumentTypes[i].isAssignableFrom(args[i].getClass())){
        convertedArgs[i] = args[i];
      // end of DPW SlimX changes

      } else {
        Converter converter = getConverter(argumentType);
        if (converter != null)
          convertedArgs[i] = converter.fromString((String) args[i]);
        else
          throw new SlimError(String.format("message:<<NO_CONVERTER_FOR_ARGUMENT_NUMBER %s.>>",
              argumentType.getName()));
      }
    }
    return convertedArgs;
  }

}
