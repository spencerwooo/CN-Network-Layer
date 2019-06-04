package Router;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Router {
  private static File preferenceFile = new File("Router/preference.txt");

  public static List<List<String>> readPreference(File file) throws FileNotFoundException, IOException {
    List<List<String>> preference = new ArrayList<List<String>>();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

    String fileString;
    while ((fileString = bufferedReader.readLine()) != null) {
      List<String> strRow = Arrays.asList(fileString.split(" "));
      preference.add(strRow);
    }
    bufferedReader.close();

    return preference;
  }

  public static void main(String[] args) throws FileNotFoundException, IOException {
    List<List<String>> preference = Router.readPreference(preferenceFile);
    System.out.println(preference);
  }
}