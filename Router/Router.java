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
  // 节点的收敛状态：Converging / Steady
  private static String iterationStatus = "Converging";

  /**
   * Read router table
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
   * @param routerTable
   */
  public static void printRouterTable(List<List<String>> routerTable) {
    int routerTableRow = routerTable.size();
    int routerTableCol = routerTable.get(0).size();

    String[] routerName = { "A", "B", "C", "D", "E" };

    System.out.println("-------- Router Table ---------");
    System.out.println("|    | A  | B  | C  | D  | E  |");
    for (int i = 0; i < routerTableRow; i++) {
      System.out.print(String.format("| %-2s |", routerName[i]));
      for (int j = 0; j < routerTableCol; j++) {
        System.out.print(String.format(" %-2s |", routerTable.get(i).get(j)));
      }
      System.out.println();
    }
    System.out.println("-------- Router Table ---------");
  }

  public static List<String> getNodeDistances(String node) {
    return routerTable.get(routerNodeMap.get(node));
  }

  /**
   * 更新路由表某一节点的最短路径信息
   * @param source
   */
  public static void updateNode(int source) {
    int routerTableCol = routerTable.get(0).size();
    // 当前节点所存储的数据表
    List<String> sourceNodeInfo = routerTable.get(source);

    for (int i = 0; i < routerTableCol; i++) {
      if (i != source) {
        if (!sourceNodeInfo.get(i).equals("-")) {
          if (!sourceNodeInfo.get(i).equals("0")) {
            int toAdjNodeDist = Integer.parseInt(sourceNodeInfo.get(i));

            // 邻居节点发来的数据表
            List<String> adjacentNodeInfo = routerTable.get(i);
            for (int j = 0; j < adjacentNodeInfo.size(); j++) {
              if (!adjacentNodeInfo.get(j).equals("-")) {
                if (!adjacentNodeInfo.get(j).equals("0")) {
                  if (j != source) {
                    int nodeToNextHopDist = Integer.parseInt(adjacentNodeInfo.get(j));
                    int newDist = nodeToNextHopDist + toAdjNodeDist;

                    if (sourceNodeInfo.get(j).equals("-")) {
                      routerTable.get(source).set(j, Integer.toString(newDist));
                    } else {
                      if (newDist < Integer.parseInt(sourceNodeInfo.get(j))) {
                        routerTable.get(source).set(j, Integer.toString(newDist));
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * 更新整个路由表
   * Refresh whole routing table
   */
  public static void refreshNodes() {
    List<List<String>> originalRouterTable = routerTable;
    int routerTableRow = routerTable.size();

    for (int i = 0; i < routerTableRow; i++) {
      updateNode(i);
    }

    if (originalRouterTable.equals(routerTable)) {
      iterationStatus = "Steady";
    }
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
        List<String> nodeInfo = getNodeDistances(input.toUpperCase());
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