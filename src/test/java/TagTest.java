import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class TagTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Tag_Instantiates_true() {
    Tag myTag = new Tag("American");
    assertEquals(true, myTag instanceof Tag);
  }

  @Test
  public void getTageInfo_TagInstantiates_String() {
    Tag myTag = new Tag("American");
    assertEquals("American", myTag.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Tag.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Tag firstTag = new Tag("American");
    Tag secondTag = new Tag("American");
    assertTrue(firstTag.equals(secondTag));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Tag myTag = new Tag("American");
    myTag.save();
    assertTrue(Tag.all().get(0).equals(myTag));
  }

  @Test
  public void save_assignsIdToObject() {
    Tag myTag = new Tag("American");
    myTag.save();
    Tag savedTag = Tag.all().get(0);
    assertEquals(myTag.getId(), savedTag.getId());
  }

  @Test
  public void find_findTagInDatabase_true() {
    Tag myTag = new Tag("American");
    myTag.save();
    Tag savedTag = Tag.find(myTag.getId());
    assertTrue(myTag.equals(savedTag));
  }

  @Test
  public void update_updatesTag_true() {
    Tag myTag = new Tag("American");
    myTag.save();
    myTag.update("French");
    assertEquals("French", Tag.find(myTag.getId()).getName());
  }

  @Test
  public void addRecipe_addsRecipeToTag_true() {
    Tag myTag = new Tag("Pop");
    myTag.save();
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    myTag.addRecipe(myRecipe);
    Recipe savedRecipe = myTag.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void getRecipes_returnsAllRecipes_List() {
    Tag myTag = new Tag("Pop");
    myTag.save();
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myRecipe.save();
    myTag.addRecipe(myRecipe);
    List savedRecipes = myTag.getRecipes();
    assertEquals(1, savedRecipes.size());
  }

  @Test
  public void delete_deletesAllRecipesAndTagsAssociations() {
    Tag myTag = new Tag("Pop");
    myTag.save();
    Recipe myRecipe = new Recipe("Lemon Pie", "1. Mix lemons with cake batter. 2. Bake at 350 degrees for 40 minutes. 3. Enjoy", 5);
    myTag.addRecipe(myRecipe);
    myTag.delete();
    assertEquals(0, myRecipe.getTags().size());
  }
}
