import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class TagTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Tag_Instantiates_true() {
    Tag myTag = new Tag("Pop");
    assertEquals(true, myTag instanceof Tag);
  }
}
