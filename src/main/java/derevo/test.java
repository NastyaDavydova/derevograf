package derevo;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphProperties;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.LinkedList;

public class test extends Application {
    private static int a[] = {70, 90, 30, 40, 20, 15, 78, 9, 8};
    private static final boolean mIns = false;
    private static final boolean mDel = true;

    @Override
    public void start(Stage ignored) throws Exception {
        HashMap<Uzel, Uzel> ParentAndChild = new HashMap<>();
        int i, n = a.length;
        derevo2<Integer> t = new derevo2<Integer>();

        System.out.printf(" Исходные данные: ");
        for (i = 0; i < n; i++) {
            System.out.printf("%d ", a[i]);
            System.out.printf("\n");
        }

        for (i = 0; i < n; i++) {
            t.insert(a[i]);
            if (mIns) {
                System.out.printf(" Добавить узел: %d\n", a[i]);
                System.out.printf(" Детали дерева: \n");
                t.print();
                System.out.printf("\n");
            }
        }
        System.out.printf(" Обход дерева от корня и левых потомков к правому потомку и листам: " + t.preOrder());
        System.out.printf("\n Обход по значению узла от минимального к максимальному: ");
        t.inOrder();
        System.out.printf("\n Обход по уровням: ");
        t.postOrder();
        System.out.printf("\n");

        System.out.printf(" Минимальное значение: %s\n", t.min());
        System.out.printf(" Максимальное значение: %s\n", t.max());
        System.out.printf(" Данные о узлах: \n");
        t.print();
        System.out.printf("\n");

        t.remove(70);

        t.insert(65);

        int h1;
        ArrayList<Integer> s = t.preOrder();
        double h = (Math.log(n + 1) / Math.log(2)) - 1;
        System.out.println("Высота" + h);
        double x, l;
        Uzel d, k;
        for (i = 0; i < n; i++) {
            h1 = 0;
            d = t.search(s.get(i));
            while (d.getParent() != null) {
                h1 = h1 + 1;
                d = d.getParent();
            }
            System.out.println(t.search(s.get(i)));
            if ((t.getCol(t.search(s.get(i))) == true) && (t.search(s.get(i)).getParent() == null)) {
                t.search(s.get(i)).setX(400);
                t.search(s.get(i)).setY(50);
            }
            x = 50;
            if ((t.search(s.get(i)).getParent() != null) && (t.search(s.get(i)).getParent().getParent() == null)) {
                x = x * (h1 + 1);
            }
            if ((t.search(s.get(i)).getParent() != null) && ((t.search(s.get(i)).getParent()).getChilRight() == t.search(s.get(i)))) {
                t.search(s.get(i)).setX((t.search(s.get(i)).getParent()).getX() + x);
                t.search(s.get(i)).setY((t.search(s.get(i)).getParent()).getY() + 50);
                System.out.println("Right");
            } else if ((t.search(s.get(i)).getParent() != null) && ((t.search(s.get(i)).getParent()).getChilLeft() == t.search(s.get(i)))) {
                t.search(s.get(i)).setX((t.search(s.get(i)).getParent()).getX() - x);
                t.search(s.get(i)).setY((t.search(s.get(i)).getParent()).getY() + 50);
                System.out.println("Left");
            }

            System.out.println(t.search(s.get(i)) + " " + t.search(s.get(i)).getX() + " " + t.search(s.get(i)).getY());
        }


        Graph<Uzel, Integer> dis = new GraphEdgeList<>();

        LinkedList<Vertex<Uzel>> temp = new LinkedList<>();


        for (i = 0; i < n; i++) {
            Vertex<Uzel> tg = dis.insertVertex(t.search(s.get(i)));
            temp.add(tg);
        }

        for (i = 0; i < n; i++) {
            try {
                dis.insertEdge(t.search(s.get(i)), t.search(s.get(i)).getParent(), i);
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        String customProps = "edge.label = true" + "\n" + "edge.arrow = false";

        SmartGraphProperties prop = new SmartGraphProperties(customProps);

        SmartGraphPanel<Uzel, Integer> graphView = new SmartGraphPanel<>(dis, prop);

        //graphView.getStylableLabel()

        for (i = 0; i < n; i++) {
            Vertex<Uzel> tg = temp.get(i);
            if (t.getCol(t.search(s.get(i))) == false) {
                graphView.getStylableVertex(tg).setStyle(
                        "-fx-stroke-width: 3;\n" + "-fx-stroke: red;\n"
                                + "-fx-stroke-type: inside;\n" + "-fx-fill: red;");
            }
        }

        Scene scene = new Scene(new SmartGraphDemoContainer(graphView) ,1024, 512);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph Red-Black Tree");
        stage.setScene(scene);
        stage.show();


        graphView.init();


        for (i = 0; i < n; i++) {
            Vertex<Uzel> tg = temp.get(i);
            graphView.setVertexPosition(tg, t.search(s.get(i)).getX(), t.search(s.get(i)).getY());

            System.out.println(t.search(s.get(i))
                    + " " + t.search(s.get(i)).getX() + " " + t.search(s.get(i)).getY());

        }


        //  Visual<Integer> b = new Visual(a).init();
        //    i = 5;
        //   if (mDel) {

        //     t.remove(s.get(i));

        //      System.out.printf(" Удалить узел: %d\n", s.get(i));
        //      System.out.printf(" Данные о узлах: \n");
        //      t.print();
        //        System.out.printf("\n");

        //  }
        graphView.update();
        t.clear();

    }


    public static void main(String[] args) {
        launch(args);

    }
}
/*
 Исходные данные: 70
90
30
40
20
15
78
9
8
 Обход дерева от корня и левых потомков к правому потомку и листам: 30 15 9 8 20 70 40 90 78
 Обход по значению узла от минимального к максимальному: 8 9 15 20 30 40 70 78 90
 Обход по уровням: 8 9 20 15 40 78 90 70 30
 Минимальное значение: 8
 Максимальное значение: 90
 Данные о узлах:
30(B) корень

Ребенок 70(R)
Родитель 30B
---------------------------------
Ребенок 90B
Родитель 70(R)
---------------------------------
Ребенок 30B
Родитель null
---------------------------------
com.brunomnsilva.smartgraph.graph.InvalidVertexException: No vertex contains null
Ребенок 40B
Родитель 70(R)
---------------------------------
Ребенок 20B
Родитель 15(R)
---------------------------------
Ребенок 15(R)
Родитель 30B
---------------------------------
Ребенок 78(R)
Родитель 90B
---------------------------------
Ребенок 9B
Родитель 15(R)
---------------------------------
Ребенок 8(R)
Родитель 9B
---------------------------------
апр. 02, 2022 4:33:45 PM com.sun.javafx.css.StyleManager loadStylesheetUnPrivileged
INFO: Could not find stylesheet: file:/C:/Users/rak12/IdeaProjects/derevograf/smartgraph.css
 Удалить узел: 40
 Данные о узлах:
30(B) корень


Process finished with exit code 0
*/