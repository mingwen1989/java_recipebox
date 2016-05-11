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
}
