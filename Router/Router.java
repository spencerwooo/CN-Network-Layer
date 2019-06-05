package Router;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Router {
  // 路由表文件位置（初始值）
  private static File routerTablePrefFile = new File("Router/routerTable.txt");
  // 路由表数据结构
  private static List<List<String>> routerTable;
  // 路由节点名称对应表
  private static Map<String, Integer> routerNodeMap = Map.of("A", 0, "B", 1, "C", 2, "D", 3, "E", 4);

  private static String iterationStatus = "Converging";

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

    String[] routerName = { "A", "B", "C", "D", "E" };

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

  public static List<String> printNodeInfo(String node) {
    return routerTable.get(routerNodeMap.get(node));
  }

  public static void refreshNodes() {

  }

  public static void main(String[] args) throws FileNotFoundException, IOException {
    System.out.println("[Usage] Input name of node to get node attributes.");
    System.out.println("        Input \"ALL\" to get router table.");
    System.out.println("        Input \"Q\" to quit.");
    System.out.println("        Available nodes: \"A, B, C, D, E\".\n");
    System.out.println("[INFO] Router Initialize\n");

    routerTable = Router.readRouterTable(routerTablePrefFile);
    printRouterTable(routerTable);

    Scanner scanner = new Scanner(System.in);
    int iterationIndex = 0;
    while (true) {
      iterationIndex++;
      System.out
          .println("\n[SYSTEM] Interactive console | Iterations: " + iterationIndex + " | Status: " + iterationStatus);

      refreshNodes();

      String input = scanner.nextLine();

      if (input.equals("q") || input.equals("Q")) {
        break;
      }

      if ("ABCDEabcde".contains(input)) {
        List<String> nodeInfo = printNodeInfo(input.toUpperCase());
        System.out.println(nodeInfo);
      } else if (input.equals("ALL") || input.equals("all")) {
        printRouterTable(routerTable);
      } else {
        System.out.println("[INFO] All nodes have been refreshed.");
        System.out.println("... Input name of node or \"ALL\" to get table of node(s).");
      }
    }
    scanner.close();
  }
}