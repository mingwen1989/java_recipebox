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

}
