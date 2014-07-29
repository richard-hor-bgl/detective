package detective.core.runner;

import static org.junit.Assert.*

import org.junit.Test

import detective.core.dsl.table.Row
import detective.core.dsl.table.TableParser;

public class TableParserTest {

  @Test
  public void test() {
    List<Row> rows = TableParser.asListOfRows([:]) {
      fistname     |  lastname      |   job
      "Christian"  |  "Baranowski"  |  "Developer"
      "James"      |  "Bond"        |  "Agent"
    }
    
    assert rows.size() == 3;
  }
  
  @Test
  public void testNumbers() {
    List<Row> rows = TableParser.asListOfRows([:]) {
      fistname     |  lastname      |   job
      1            |  2             |   3
      "James"      |  "Bond"        |  "Agent"
    }
    
    assert rows.size() == 3;
    assert rows.get(1).values.getAt(0).equals(1);
    assert rows.get(1).values.getAt(1).equals(2);
    assert rows.get(1).values.getAt(2).equals(3);
  }
  
  @Test
  public void testPropertyChain() {
    List<Row> rows = TableParser.asListOfRows([:]) {
      test1.test2.fistname  |  test1.test2.lastname   |   test1.test2.job
      "Christian"           |  "Baranowski"           |  "Developer"
      "James"               |  "Bond"                 |  "Agent"
    }
    
    assert rows.size() == 3;
  }
  
  @Test
  public void testPropertyChainWithParameters() {
    List<Row> rows = TableParser.asListOfRows(["test1.test2.job":"The Real Name"]) {
      test1.test2.fistname  |  test1.test2.lastname   |   test1.test2.job
      "Christian"           |  "Baranowski"           |  "Developer"
      "James"               |  "Bond"                 |  "Agent"
    }
    
    assert rows.size() == 3;
    assert rows.get(0).values.getAt(2).equals("The Real Name");
  }
}