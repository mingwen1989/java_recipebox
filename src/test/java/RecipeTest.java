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

  @Test
  public void getRecipe_recipleInstantiates_String() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    assertEquals("Lemon Pie", myRecipe.getTitle());
    assertEquals("1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", myRecipe.getInstructions());
    assertEquals(5, myRecipe.getRating());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Recipe firstRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    Recipe secondRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    assertTrue(firstRecipe.equals(secondRecipe));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    assertTrue(Recipe.all().get(0).equals(myRecipe));
  }

  @Test
  public void save_assignsIdToObject() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(myRecipe.getId(), savedRecipe.getId());
  }

  @Test
  public void find_findRecipeInDatabase_true() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    Recipe savedRecipe = Recipe.find(myRecipe.getId());
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void update_updatesTitle_true() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    myRecipe.update("Apple Pie", "1. Mix apples with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 4);
    assertEquals("Apple Pie", Recipe.find(myRecipe.getId()).getTitle());
    assertEquals("1. Mix apples with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", Recipe.find(myRecipe.getId()).getInstructions());
    assertEquals(4, Recipe.find(myRecipe.getId()).getRating());
  }

  @Test
  public void addTag_addsTagToAlbum_true() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    Tag myTag = new Tag("American");
    myTag.save();
    myRecipe.addTag(myTag);
    Tag savedTag = myRecipe.getTags().get(0);
    assertTrue(myTag.equals(savedTag));
  }
  @Test
  public void getTags_returnsAllTags_List() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    Tag myTag = new Tag("American");
    myTag.save();
    myRecipe.addTag(myTag);
    List savedTags = myRecipe.getTags();
    assertEquals(1, savedTags.size());
  }

  @Test
  public void delete_deletesAllTagsAndRecipesAssociations() {
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    Tag myTag = new Tag("American");
    myRecipe.addTag(myTag);
    myRecipe.delete();
    assertEquals(0, myTag.getRecipes().size());
  }

}
