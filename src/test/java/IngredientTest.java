import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class IngredientTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Ingredient_Instantiates_true() {
    Ingredient myIngredient = new Ingredient("Chili");
    assertEquals(true, myIngredient instanceof Ingredient);
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Ingredient myIngredient = new Ingredient("Chili");
    myIngredient.save();
    assertTrue(Ingredient.all().get(0).getIngredient().equals(myIngredient.getIngredient()));
  }
}
