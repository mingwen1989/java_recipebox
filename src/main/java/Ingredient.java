import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Ingredient {
    private int id;
    private String ingredient;

public Ingredient (String ingredient) {
  this.ingredient = ingredient;
}

public String getIngredient() {
  return ingredient;
}

public int getId() {
  return id;
}

public static List<Ingredient> all() {
  String sql = "SELECT id, ingredient FROM ingredients";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Ingredient.class);
  }
}

@Override
public boolean equals(Object otherIngredient) {
  if (!(otherIngredient instanceof Ingredient)) {
    return false;
  } else {
    Ingredient newIngredient = (Ingredient) otherIngredient;
    return this.getIngredient().equals(newIngredient.getIngredient()) &&
           this.getId() == newIngredient.getId();
  }
}

public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO ingredients(ingredient) VALUES (:ingredient)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("ingredient", this.ingredient)
      .executeUpdate()
      .getKey();
  }
}

public static Ingredient find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM ingredients where id=:id";
    Ingredient ingredient = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Ingredient.class);
    return ingredient;
  }
}

public void update(String newIngredient) {
  try(Connection con = DB.sql2o.open()){
    String sql = "UPDATE ingredients SET ingredient = :ingredient WHERE id = :id";
    con.createQuery(sql)
    .addParameter("ingredient", newIngredient)
    .addParameter("id", this.id)
    .executeUpdate();
  }
}

  public void addRecipe(Recipe recipe) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES (:recipe_id, :ingredient_id)";
      con.createQuery(sql)
      .addParameter("ingredient_id", this.getId())
      .addParameter("recipe_id", recipe.getId())
      .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT recipe_id FROM recipe_ingredient WHERE ingredient_id = :ingredient_id";
      List<Integer> recipeIds = con.createQuery(joinQuery)
        .addParameter("ingredient_id", this.getId())
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
    String deleteQuery = "DELETE FROM ingredients WHERE id = :id;";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

    String joinDeleteQuery = "DELETE FROM recipe_ingredient WHERE ingredient_id = :ingredient_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("ingredient_id", this.getId())
        .executeUpdate();
    }
  }


}
