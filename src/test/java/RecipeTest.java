import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class RecipeTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_Instantiates_true() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    assertEquals(true, myRecipe instanceof Recipe);
  }
}
