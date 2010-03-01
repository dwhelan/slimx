package fitnesse.slimx.fixtures;

import static fitnesse.slimx.reflection.TableUtil.report;
import static fitnesse.slimx.reflection.TableUtil.reportTable;
import static util.ListUtility.list;

import java.util.ArrayList;
import java.util.List;

import fitnesse.slimx.reflection.PropertyList;

public class CreateCheckVariable extends ObjectTable {

  private final static String variable = "<b><i>$variable</b><i>";
  final static String instructions = "Copy and paste the contents of the next cell into a test wiki page.<br/><br/>"
      + "Then replace " + variable + " with the appropriate variable name";

  public CreateCheckVariable(Object object) {
    super(object);
  }

  public CreateCheckVariable(List<Object> objectList, String qualifier) {
    super(objectList, qualifier);
  }

  public List<List<String>> doTable(List<List<String>> table) {
    List<List<String>> result = reportTable(table);

    result.add(list(report("instructions"), report("wiki text")));
    result.add(list(report(instructions), report(wikify(table))));

    return result;
  }

  @SuppressWarnings("unchecked")
  private String wikify(List<List<String>> table) {
    StringBuilder wikiText = new StringBuilder();

    List tableHeader = new ArrayList<String>();
    tableHeader.add("query: check variable");
    tableHeader.add(variable);
    if (getQualifier() != null)
      tableHeader.add(getQualifier());

    wikifyRow(wikiText, tableHeader);

    PropertyList properties = new PropertyList(getObjectClass(), table);
    wikifyRow(wikiText, properties.getNames());

    for (List<Object> values : properties.getValues(objectList()))
      wikifyRow(wikiText, values);

    return wikiText.toString();
  }

  @SuppressWarnings("unchecked")
  private static void wikifyRow(StringBuilder wikiText, List row) {
    wikiText.append('|');
    for (Object cell : row)
      wikiText.append(cell).append('|');
    wikiText.append("<br/>");
  }
}