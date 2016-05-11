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
}
