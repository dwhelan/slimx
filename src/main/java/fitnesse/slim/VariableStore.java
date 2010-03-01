package fitnesse.slim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableStore {
  private Map<String, Object> variables = new HashMap<String, Object>();
  private Matcher symbolMatcher;

  public void setVariable(String name, Object value) {
    variables.put(name, value);
  }

  public Object[] replaceVariables(Object[] args) {
    Object result[] = new Object[args.length];
    for (int i = 0; i < args.length; i++)
      result[i] = replaceVariable(args[i]);

    return result;
  }

  private List<Object> replaceArgsInList(List<Object> objects) {
    List<Object> result = new ArrayList<Object>();
    for (Object object : objects)
      result.add(replaceVariable(object));

    return result;
  }

  @SuppressWarnings("unchecked")
  private Object replaceVariable(Object object) {
    if (object instanceof List)
      return (replaceArgsInList((List<Object>) object));
    else
      return (replaceVariablesInString((String) object));
  }

  private Object replaceVariablesInString(String arg) {
    // DPW SlimX changes: if matches a single variable look it up and return variable
    Pattern singleSymbolPattern = Pattern.compile("^\\$([a-zA-Z]\\w*)$");
    symbolMatcher = singleSymbolPattern.matcher(arg);
    if (symbolMatcher.matches()) {
      return variables.get(symbolMatcher.group(1));
    }
    // end of DPW SlimX changes

    Pattern symbolPattern = Pattern.compile("\\$([a-zA-Z]\\w*)");
    int startingPosition = 0;
    while (true) {
      if ("".equals(arg) || null == arg) {
        break;
      }
      symbolMatcher = symbolPattern.matcher(arg);
      if (symbolMatcher.find(startingPosition)) {
        String symbolName = symbolMatcher.group(1);
        arg = replaceSymbolInArg(arg, symbolName);
        startingPosition = symbolMatcher.start(1);
      } else {
        break;
      }
    }
    return arg;
  }

  private String replaceSymbolInArg(String arg, String symbolName) {
    if (variables.containsKey(symbolName)) {
      String replacement = (String) variables.get(symbolName);
      if (replacement == null)
        replacement = "null";
      arg = arg.substring(0, symbolMatcher.start()) + replacement + arg.substring(symbolMatcher.end());
    }
    return arg;
  }
}
