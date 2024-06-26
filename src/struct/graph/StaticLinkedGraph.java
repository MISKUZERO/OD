package struct.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class StaticLinkedGraph {

    static class Node {
        Node next;
        int id;
        boolean red;

        Node(int id) {
            this.id = id;
        }
    }

    final ArrayList<Node> vertexes;
    final ArrayList<int[]> edges;
    final HashMap<Integer, ArrayList<Node>> caches;

    public StaticLinkedGraph(int vertexCount) {
        ArrayList<Node> vertexes = new ArrayList<>();
        for (int i = 0; i <= vertexCount; i++)
            vertexes.add(new Node(i));
        this.vertexes = vertexes;
        edges = new ArrayList<>();
        caches = new HashMap<>();
    }


    public boolean addEdge(Integer node1, Integer node2, int cost, boolean directed) {
        int size = vertexes.size();
        if (node1 >= size || node2 >= size) return false;
        HashMap<Integer, ArrayList<Node>> caches = this.caches;
        //不检查重复添加边，头插法
        Node n2 = new Node(node2);
        n2.next = vertexes.get(node1).next;
        vertexes.get(node1).next = n2;
        caches.computeIfAbsent(node2, k -> new ArrayList<>()).add(n2);
        if (!directed) {
            //对应边添加
            Node n1 = new Node(node1);
            n1.next = vertexes.get(node2).next;
            vertexes.get(node2).next = n1;
            caches.computeIfAbsent(node1, k -> new ArrayList<>()).add(n1);
        }
        edges.add(new int[]{node1, node2, cost});
        return true;
    }


    public void paint(int[] vIds, int size, Boolean red) {
        HashMap<Integer, ArrayList<Node>> caches = this.caches;
        ArrayList<Node> vertexes = this.vertexes;
        for (int i = 0; i < size; i++) {
            int id = vIds[i];
            vertexes.get(id).red = red;
            caches.forEach((vid, nodes) -> {
                if (vid == id)
                    for (Node node : nodes)
                        node.red = red;
            });
        }
    }

    public boolean test() {
        ArrayList<Node> vertexes = this.vertexes;
        int size = vertexes.size();
        for (int i = 1; i < size; i++)
            if (vertexes.get(i).red) {
                Node cur = vertexes.get(i).next;
                while (cur != null) {
                    if (cur.red)
                        return false;
                    cur = cur.next;
                }
            }
        return true;
    }


    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ArrayList<Node> vertexes = this.vertexes;
        int size = vertexes.size();
        for (int i = 1; i < size; i++) {
            Node cur = vertexes.get(i);
            loadStringBuilder(ret, cur);
        }
        return ret.toString();
    }

    public static void loadStringBuilder(StringBuilder ret, StaticLinkedGraph.Node cur) {
        while (cur != null) {
            if (cur.red)
                ret.append("(").append(cur.id).append(")").append("->");
            else
                ret.append(" ").append(cur.id).append(" ").append("->");
            cur = cur.next;
        }
        ret.append("\b\b\n");
    }
}
