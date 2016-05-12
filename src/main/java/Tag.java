import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Tag {
  private int id;
  private String name;


  public Tag(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Tag> all() {
    String sql = "SELECT id, name FROM tags";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  @Override
  public boolean equals(Object otherTag) {
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getName().equals(newTag.getName()) &&
             this.getId() == newTag.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags where id=:id";
      Tag tag = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  public void update(String newTag) {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE tags SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", newTag)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void addRecipe(Recipe recipe) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipe_tag (recipe_id, tag_id) VALUES (:recipe_id, :tag_id)";
      con.createQuery(sql)
      .addParameter("tag_id", this.getId())
      .addParameter("recipe_id", recipe.getId())
      .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT recipe_id FROM recipe_tag WHERE tag_id = :tag_id";
      List<Integer> recipeIds = con.createQuery(joinQuery)
        .addParameter("tag_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Recipe> recipes = new ArrayList<Recipe>();

      for (Integer recipeId : recipeIds) {
        String recipeQuery = "SELECT * FROM recipes WHERE id = :recipeId";
        Recipe recipe = con.createQuery(recipeQuery)
          .addParameter("recipeId", recipeId)
          .executeAndFetchFirst(Recipe.class);
        recipes.add(recipe);
      }
      // if (recipes.size() == 0) {
      //   return null;
      // } else {
        return recipes;
      // }
    }
  }

  public void delete() {
  try(Connection con = DB.sql2o.open()) {
    String deleteQuery = "DELETE FROM tags WHERE id = :id;";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

    String joinDeleteQuery = "DELETE FROM recipe_tag WHERE tag_id = :tag_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("tag_id", this.getId())
        .executeUpdate();
    }
  }

}
