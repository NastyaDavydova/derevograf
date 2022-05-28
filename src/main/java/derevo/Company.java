package derevo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.brunomnsilva.smartgraph.graph.*;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphProperties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Company extends Application {
    private static final boolean mIns = false;
    private static final boolean mDel = true;

    public int a[] = {70, 90, 30, 40, 20, 15, 78, 9, 8};
     public Integer n=new Integer(a.length);

    @Override
    public void start(Stage primaryStage) throws Exception {

        int i, q;

        String l = null;

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

        String[] li = new String[n];
        li = s.toArray(new String[n]);

        //  uzelBox.getItems().addAll(s);

        sortFilterBox.getItems().addAll(
                "По значениям",
                "От корня",
                "По уровням"
        );

        root.getChildren().add(strings);

        strings.setPadding(new Insets(10, 30, 10, 30));
        strings.setSpacing(20);

        strings.getChildren().add(new Text("Добавить новый узел"));
        strings.getChildren().add(OperUzelBox);
        strings.getChildren().add(sorts);
        strings.getChildren().add(resultSort);
        strings.getChildren().add(graficder);

        OperUzelBox.setSpacing(10);
        OperUzelBox.getChildren().add(buttonAddUzel);
        OperUzelBox.getChildren().add(buttonDeletUzel);
        OperUzelBox.getChildren().add(Print);
        OperUzelBox.getChildren().add(printder);
        OperUzelBox.getChildren().add(new TextField(ind.getText()));


        graficder.setSpacing(10);
        graficder.getChildren().add(new SmartGraphPanel<Uzel, Integer> (t, n));
        graficder.getChildren().add(buttonGrafic);

        sorts.setSpacing(10);
        sorts.getChildren().add(new Text("Вид сортировки"));
        sorts.getChildren().add(sortFilterBox);
        sorts.getChildren().add(sort);


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Красно-черное дерево");
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonAddUzel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String l = uzelind.getText();
                t.insert(Integer.valueOf(l));
                Uzel u = new Uzel(Integer.valueOf(l), t.search(Integer.valueOf(l)).col, t.search(Integer.valueOf(l)).getParent(), t.search(Integer.valueOf(l)).getChilLeft(), t.search(Integer.valueOf(l)).getChilRight());

                uzelBox.getItems().addAll(u);
                ind.clear();

            }
        });

        buttonDeletUzel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String l = uzeli.getText();
                Uzel u = t.search(Integer.valueOf(l));
                t.remove(Integer.valueOf(l));
                uzelBox.getItems().addAll(u);
                uzeli.clear();

            }
        });

        buttonGrafic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                graph(t, n);

            }
        });
        Print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //   Integer [] pri=t.toArray(new Integer() [n]);
                //      printder= toString;

            }
        });

        sort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int uzelind;
                int index = sortFilterBox.getSelectionModel().getSelectedIndex();
                ArrayList<Integer> d;
                if (index == 0) {
                    d = t.inOrder();
                } else if (index == 2) {
                    d = t.postOrder();
                } else {
                    d = t.preOrder();
                }

                resultSort.setText(d.toString());

            }
        });


    }

    public SmartGraphPanel<Uzel, Integer> graph(derevo2<Integer> t, int n) {
        int h1, i;
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
            if ((t.search(s.get(i)).col == true) && (t.search(s.get(i)).getParent() == null)) {
                t.search(s.get(i)).setX(400);
                t.search(s.get(i)).setY(50);
            }
            x = 25;
            if (t.search(s.get(i)).getParent() != null) {
                x = x * (2 * h - h1 + 2);
            }
            if ((t.search(s.get(i)).getParent() != null) &&
                    ((t.search(s.get(i)).getParent()).getChilRight() == t.search(s.get(i)))) {
                t.search(s.get(i)).setX((t.search(s.get(i)).getParent()).getX() + x);
                t.search(s.get(i)).setY((t.search(s.get(i)).getParent()).getY() + 50);
                System.out.println("Right");
            } else if ((t.search(s.get(i)).getParent() != null) &&
                    ((t.search(s.get(i)).getParent()).getChilLeft() == t.search(s.get(i)))) {
                t.search(s.get(i)).setX((t.search(s.get(i)).getParent()).getX() - x);
                t.search(s.get(i)).setY((t.search(s.get(i)).getParent()).getY() + 50);
                System.out.println("Left");
            }

            System.out.println(t.search(s.get(i)) + " " + t.search(s.get(i)).getX() + " " + t.search(s.get(i)).getY());
        }


        Graph<Uzel, Integer> dis = new GraphEdgeList<>();



        for (i = 0; i < n; i++) {
            try {
                dis.insertEdge(
                        t.search(s.get(i)),
                        t.search(s.get(i)).getParent(),
                        i);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
       // return temp;
    //}
  //  Graph<Uzel, Integer> di = new GraphEdgeList<>();

  //  public SmartGraphPanel<Uzel, Integer> View(Graph<Uzel, Integer> di){
    String customProps = "edge.label = false" + "\n" + "edge.arrow = false";
    //    di=graph(t, n);
    SmartGraphProperties prop = new SmartGraphProperties(customProps);

   // SmartGraphPanel<Uzel, Integer> gra = new SmartGraphPanel<>(dis, prop);

    SmartGraphPanel<Uzel, Integer> graphView = new SmartGraphPanel<>(dis, prop);

    //graphView.getStylableLabel();
        LinkedList<Vertex<Uzel>> temp = new LinkedList<>();


        for ( i = 0; i < n; i++) {
            Vertex<Uzel> tg = dis.insertVertex(t.search(s.get(i)));
            temp.add(tg);
        }
       for ( i = 0; i < n; i++) {
          Vertex<Uzel> tg = temp.get(i);
          if (t.search(s.get(i)).col == false) {
       graphView.getStylableVertex(tg).setStyle("-fx-stroke-width: 3;\n" + "-fx-stroke: red;\n" + "-fx-stroke-type: inside;\n" + "-fx-fill: red;");
        }


      }

      return graphView;
     }

    public static void main(String[] args) {
        launch(args);
    }

    Group root = new Group();

    VBox strings = new VBox();

    HBox OperUzelBox = new HBox();
    //  HBox DelUzelBox = new HBox();
    HBox sorts = new HBox();
    HBox graficder = new HBox();

    ComboBox<Uzel> uzelBox = new ComboBox<>();
    ComboBox<String> sortFilterBox = new ComboBox<>();

    final private int WIDTH = 1000;
    final private int HEIGHT = 600;

    derevo2<Integer> t = new derevo2<Integer>();
    public ArrayList<Integer> s = t.preOrder();
    //Graph<Uzel, Integer> g = graph(t,n);
    TextField ind = new TextField();
    Button buttonGrafic = new Button("Отобразить график");
    Button buttonAddUzel = new Button("Добавить узел");
    Button Print = new Button("Вывести дерево");
    TextField uzelind = new TextField();

    Button buttonDeletUzel = new Button("Удалить узел");
    TextField uzeli = new TextField();
    Button sort = new Button("Сортировать");
    Text resultSort = new Text();
    Text printder = new Text();


}