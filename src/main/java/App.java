import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {

  public static void main (String[] args){
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model,layout);
    }, new VelocityTemplateEngine());

    get("/recipes",(request, response) -> {
      HashMap<String, Object> model = new HashMap<String,Object>();
      model.put("recipes", Recipe.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tags", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("taglist", Tag.all());
      model.put("template", "templates/tags.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes", (request, response) -> {
      String title = request.queryParams("recipeTitle");
      String instructions = request.queryParams("recipeInstructions");
      int rating = Integer.parseInt(request.queryParams("recipeRating"));
      Recipe newRecipe = new Recipe(title, instructions, rating);
      newRecipe.save();
      response.redirect("/recipes");
      return null;
    });

    post("/tags", (request, response) -> {
      String tagTitle = request.queryParams("tagTitle");
      Tag newTag = new Tag(tagTitle);
      newTag.save();
      response.redirect("/tags");
      return null;
    });

    get("/recipes/:id", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      model.put("recipe", recipe);
      model.put("allTags", Tag.all());
      model.put("allIngredients", Ingredient.all());
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes/:id/add_tag", (request, response) -> {
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      int newTag = Integer.parseInt(request.queryParams("tag_id"));
      recipe.addTag(Tag.find(newTag));
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    post("/recipes/:id/update", (request, response) -> {
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      String updateRecipeTitle = request.queryParams("updateRecipeTitle");
      String updateRecipeInstruction = request.queryParams("updateRecipeInstruction");
      int updateRecipeRating = Integer.parseInt(request.queryParams("updateRecipeRating"));
      recipe.update(updateRecipeTitle, updateRecipeInstruction, updateRecipeRating);
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    post("/recipes/:id/delete", (request, response) -> {
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      recipe.delete();
      response.redirect("/recipes");
      return null;
    });

    get("/tags/:id", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Tag tag = Tag.find(Integer.parseInt(request.params(":id")));
      model.put("tag", tag);
      model.put("allRecipes", Recipe.all());
      model.put("template", "templates/tag.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tags/:id/update", (request, response) -> {
      Tag tag = Tag.find(Integer.parseInt(request.params(":id")));
      String updateTag = request.queryParams("updateTagName");
      tag.update(updateTag);
      response.redirect("/tags/" + tag.getId());
      return null;
    });

    post("/tags/:id/delete", (request, response) -> {
      Tag tag = Tag.find(Integer.parseInt(request.params(":id")));
      tag.delete();
      response.redirect("/tags");
      return null;
    });

    get("/ingredients", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("ingredientlist", Ingredient.all());
      model.put("template", "templates/ingredients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/ingredients", (request, response) -> {
      String ingredientTitle = request.queryParams("ingredientTitle");
      Ingredient newIngredient = new Ingredient(ingredientTitle);
      newIngredient.save();
      response.redirect("/ingredients");
      return null;
    });

    post("/recipes/:id/add_ingredient", (request, response) -> {
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      int newIngredient = Integer.parseInt(request.queryParams("ingredient_id"));
      recipe.addIngredient(Ingredient.find(newIngredient));
      response.redirect("/recipes/" + recipe.getId());
      return null;
    });

    get("/ingredients/:id", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Ingredient ingredient = Ingredient.find(Integer.parseInt(request.params(":id")));
      model.put("ingredient", ingredient);
      model.put("allRecipes", Recipe.all());
      model.put("template", "templates/ingredient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/ingredients/:id/update", (request, response) -> {
      Ingredient ingredient = Ingredient.find(Integer.parseInt(request.params(":id")));
      String updateIngredient = request.queryParams("updateIngredientName");
      ingredient.update(updateIngredient);
      response.redirect("/ingredients/" + ingredient.getId());
      return null;
    });

    post("/ingredients/:id/delete", (request, response) -> {
      Ingredient ingredient = Ingredient.find(Integer.parseInt(request.params(":id")));
      ingredient.delete();
      response.redirect("/ingredients");
      return null;
    });

    // post("/sort", (request, response) -> {
    //   Recipe.sortRating();
    //   response.redirect("/recipes");
    //   return null;
    // });

  }
}
