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
  public void getTageInfo_IngredientInstantiates_String() {
    Ingredient myIngredient = new Ingredient("Chili");
    assertEquals("Chili", myIngredient.getIngredient());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Ingredient.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Ingredient firstIngredient = new Ingredient("Chili");
    Ingredient secondIngredient = new Ingredient("Chili");
    assertTrue(firstIngredient.equals(secondIngredient));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Ingredient myIngredient = new Ingredient("Chili");
    myIngredient.save();
    assertTrue(Ingredient.all().get(0).equals(myIngredient));
  }

  @Test
  public void save_assignsIdToObject() {
    Ingredient myIngredient = new Ingredient("American");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.all().get(0);
    assertEquals(myIngredient.getId(), savedIngredient.getId());
  }

  @Test
  public void find_findIngredientInDatabase_true() {
    Ingredient myIngredient = new Ingredient("American");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.find(myIngredient.getId());
    assertTrue(myIngredient.equals(savedIngredient));
  }

  @Test
  public void update_updatesIngredient_true() {
    Ingredient myIngredient = new Ingredient("Chili");
    myIngredient.save();
    myIngredient.update("Pepper");
    assertEquals("Pepper", Ingredient.find(myIngredient.getId()).getIngredient());
  }

  @Test
  public void addRecipe_addsRecipeToIngredient_true() {
    Ingredient myIngredient = new Ingredient("Chili");
    myIngredient.save();
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    myIngredient.addRecipe(myRecipe);
    Recipe savedRecipe = myIngredient.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void getRecipes_returnsAllRecipes_List() {
    Ingredient myIngredient = new Ingredient("Pop");
    myIngredient.save();
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    myIngredient.addRecipe(myRecipe);
    List savedRecipes = myIngredient.getRecipes();
    assertEquals(1, savedRecipes.size());
  }

  @Test
  public void delete_deletesAllRecipesAndIngredientsAssociations() {
    Ingredient myIngredient = new Ingredient("Pop");
    myIngredient.save();
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myIngredient.addRecipe(myRecipe);
    myIngredient.delete();
    assertEquals(0, myRecipe.getIngredients().size());
  }
}
