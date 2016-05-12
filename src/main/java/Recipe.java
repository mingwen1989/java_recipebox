import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Recipe {
  private int id;
  private String title;
  private String instructions;
  private int rating;

  public Recipe(String title, String instructions, int rating) {
    this.title = title;
    this.instructions = instructions;
    this.rating = rating;
  }

  public String getTitle() {
    return title;
  }

  public String getInstructions() {
    return instructions;
  }

  public int getRating() {
    return rating;
  }

  public int getId() {
    return id;
  }

  public static List<Recipe> all() {
    String sql = "SELECT id, title, instructions, rating FROM recipes";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if(!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getTitle().equals(newRecipe.getTitle()) &&
             this.getInstructions().equals(newRecipe.getInstructions()) &&
             this.getRating() == (newRecipe.getRating());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes(title, instructions, rating) VALUES (:title, :instructions, :rating)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("title", this.title)
          .addParameter("instructions", this.instructions)
          .addParameter("rating", this.rating)
          .executeUpdate()
          .getKey();
     }
   }

   public static Recipe find(int id) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM recipes WHERE id=:id";
       Recipe recipe = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetchFirst(Recipe.class);
       return recipe;
     }
   }

   public void update(String newTitle, String newInstructions, int newRating) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "UPDATE recipes SET title = :title, instructions = :instructions, rating = :rating WHERE id = :id";
       con.createQuery(sql)
       .addParameter("title", newTitle)
       .addParameter("instructions", newInstructions)
       .addParameter("rating", newRating)
       .addParameter("id", this.id)
       .executeUpdate();
     }
   }

   public void addTag(Tag tag) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO recipe_tag (recipe_id, tag_id) VALUES (:recipe_id, :tag_id)";
       con.createQuery(sql)
       .addParameter("recipe_id", this.getId())
       .addParameter("tag_id", tag.getId())
       .executeUpdate();
     }
   }

   public List<Tag> getTags() {
     try(Connection con = DB.sql2o.open()) {
       String joinQuery = "SELECT tag_id FROM recipe_tag WHERE recipe_id = :recipe_id";
       List<Integer> tagIds = con.createQuery(joinQuery)
         .addParameter("recipe_id", this.getId())
         .executeAndFetch(Integer.class);

       List<Tag> tags = new ArrayList<Tag>();

       for (Integer tagId : tagIds) {
         String tagQuery = "SELECT * FROM tags WHERE id = :tagId";
         Tag tag = con.createQuery(tagQuery)
           .addParameter("tagId", tagId)
           .executeAndFetchFirst(Tag.class);
         tags.add(tag);
       }
      //  if (tags.size() == 0) {
      //    return null;
      //  } else {
         return tags;
      //  }
     }
   }

   public void delete() {
     try(Connection con = DB.sql2o.open()) {
       String deleteQuery = "DELETE FROM recipes WHERE id = :id";
       con.createQuery(deleteQuery)
         .addParameter("id", this.id)
         .executeUpdate();
       String joinDeleteQuery = "DELETE FROM recipe_tag WHERE recipe_id = :recipe_id";
         con.createQuery(joinDeleteQuery)
           .addParameter("recipe_id", this.getId())
           .executeUpdate();
     }
   }

    // public static void sortRating() {
    //   try(Connection con = DB.sql2o.open()) {
    //   String sortRating = "DROP TABLE IF EXISTS recipes2";
    //   con.createQuery(sortRating)
    //     .executeUpdate();
    //   sortRating = "CREATE TABLE recipes2 AS SELECT title, instructions, rating FROM recipes ORDER BY rating DESC";
    //   con.createQuery(sortRating)
    //     .executeUpdate();
    //   sortRating = "ALTER TABLE recipes2 ADD COLUMN id SERIAL";
    //   con.createQuery(sortRating)
    //       .executeUpdate();
    //   sortRating = "ALTER TABLE recipes2 ADD PRIMARY KEY (id)";
    //   con.createQuery(sortRating)
    //       .executeUpdate();
    //   sortRating = "DROP TABLE recipes";
    //   con.createQuery(sortRating)
    //     .executeUpdate();
    //   sortRating = "ALTER TABLE recipes2 RENAME TO recipes";
    //   con.createQuery(sortRating)
    //     .executeUpdate();
    //   }
    // }

    public void addIngredient(Ingredient ingredient) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES (:recipe_id, :ingredient_id)";
        con.createQuery(sql)
        .addParameter("recipe_id", this.getId())
        .addParameter("ingredient_id", ingredient.getId())
        .executeUpdate();
      }
    }

    public List<Ingredient> getIngredients() {
      try(Connection con = DB.sql2o.open()) {
        String joinQuery = "SELECT ingredient_id FROM recipe_ingredient WHERE recipe_id = :recipe_id";
        List<Integer> ingredientIds = con.createQuery(joinQuery)
          .addParameter("recipe_id", this.getId())
          .executeAndFetch(Integer.class);

        List<Ingredient> ingredients = new ArrayList<Ingredient>();

        for (Integer ingredientId : ingredientIds) {
          String ingredientQuery = "SELECT * FROM ingredients WHERE id = :ingredientId";
          Ingredient ingredient = con.createQuery(ingredientQuery)
            .addParameter("ingredientId", ingredientId)
            .executeAndFetchFirst(Ingredient.class);
          ingredients.add(ingredient);
        }
        // if (ingredients.size() == 0) {
        //   return null;
        // } else {
          return ingredients;
        // }
      }
    }
}
