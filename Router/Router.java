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
  // 路由表文件位置（初始值）
  private static File routerTablePrefFile = new File("Router/routerTable.txt");

  /**
   * Read router table
   *
   * @param file 路由表文件
   * @return List<List<String>> 路由表数据结构（二维列表）
   */
  public static List<List<String>> readRouterTable(File file) throws FileNotFoundException, IOException {
    List<List<String>> routerTable = new ArrayList<List<String>>();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

    String fileString;
    while ((fileString = bufferedReader.readLine()) != null) {
      List<String> strRow = Arrays.asList(fileString.split(" "));
      routerTable.add(strRow);
    }
    bufferedReader.close();

    return routerTable;
  }

  /**
   * 打印路由表
   *
   * @param routerTable
   */
  public static void printRouterTable(List<List<String>> routerTable) {
    int routerTableRow = routerTable.size();
    int routerTableCol = routerTable.get(0).size();

    String[] routerName = {"A", "B", "C", "D", "E"};

    System.out.println("------ Router Table -----------");
    System.out.println(String.format("|    | %-2s | %-2s | %-2s | %-2s | %-2s |", "A", "B", "C", "D", "E"));
    for (int i = 0; i < routerTableRow; i++) {
      System.out.print(String.format("| %-2s |", routerName[i]));
      for (int j = 0; j < routerTableCol; j++) {
        System.out.print(String.format(" %-2s |", routerTable.get(i).get(j)));
      }
      System.out.println();
    }
    System.out.println("------ Router Table -----------");
  }

  public static void main(String[] args) throws FileNotFoundException, IOException {
    System.out.println("------ Router Initialize ------");

    List<List<String>> routerTable = Router.readRouterTable(routerTablePrefFile);
    System.out.println(routerTable);

    printRouterTable(routerTable);
  }
}