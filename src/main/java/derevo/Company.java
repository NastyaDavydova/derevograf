package derevo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
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
import javafx.stage.StageStyle;

public class Company extends Application {
    private static final boolean mIns = false;
    private static final boolean mDel = true;

    public int a[] = {70, 90, 30, 40, 20, 15, 78, 9, 8};
    public Integer n = new Integer(a.length);

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
        int h1;
        ArrayList<Integer> s = t.preOrder();
        double h = (Math.log(n + 1) / Math.log(2)) - 1;
        System.out.println("Высота" + h);
        double x;
        Uzel d;
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
            if (t.search(s.get(i)).getParent() != null ) {
                x = x * (2*h-h1+2);
            }
            if ((t.search(s.get(i)).getParent() != null) && ((t.search(s.get(i)).getParent()).getChilRight() == t.search(s.get(i)))) {
                t.search(s.get(i)).setX((t.search(s.get(i)).getParent()).getX() + x);
                t.search(s.get(i)).setY((t.search(s.get(i)).getParent()).getY() + 50);

            } else if ((t.search(s.get(i)).getParent() != null) && ((t.search(s.get(i)).getParent()).getChilLeft() == t.search(s.get(i)))) {
                t.search(s.get(i)).setX((t.search(s.get(i)).getParent()).getX() - x);
                t.search(s.get(i)).setY((t.search(s.get(i)).getParent()).getY() + 50 );

            }


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

        String customProps = "edge.label = false" + "\n" + "edge.arrow = false";

        SmartGraphProperties prop = new SmartGraphProperties(customProps);

        SmartGraphPanel<Uzel, Integer> graphView = new SmartGraphPanel<>(dis, prop);

        //graphView.getStylableLabel()

        for (i = 0; i < n; i++) {
            Vertex<Uzel> tg = temp.get(i);
            if (t.search(s.get(i)).col == false) {
                graphView.getStylableVertex(tg).setStyle(
                        "-fx-stroke-width: 3;\n" + "-fx-stroke: red;\n"
                                + "-fx-stroke-type: inside;\n" + "-fx-fill: red;");
            }
        }

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
        OperUzelBox.getChildren().add(ind);


        graficder.setSpacing(10);
        graficder.getChildren().add(graphView);

        sorts.setSpacing(10);
        sorts.getChildren().add(new Text("Вид сортировки"));
        sorts.getChildren().add(sortFilterBox);
        sorts.getChildren().add(sort);


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Красно-черное дерево");
        primaryStage.setScene(scene);
        primaryStage.show();
        graphView.init();
        for (i = 0; i < n; i++) {
            Vertex<Uzel> tg = temp.get(i);
            graphView.setVertexPosition(tg, t.search(s.get(i)).getX(), t.search(s.get(i)).getY());

            System.out.println(t.search(s.get(i))
                    + " " + t.search(s.get(i)).getX() + " " + t.search(s.get(i)).getY());

        }
        buttonAddUzel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                t.insert(Integer.valueOf(ind.getText()));
                Uzel u = new Uzel(Integer.valueOf(ind.getText()), t.search(Integer.valueOf(ind.getText())).col, t.search(Integer.valueOf(ind.getText())).getParent(), t.search(Integer.valueOf(ind.getText())).getChilLeft(), t.search(Integer.valueOf(ind.getText())).getChilRight());

                uzelBox.getItems().addAll(u);
                ind.clear();
                graphView.update();

            }
        });

        buttonDeletUzel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                t.remove(Integer.valueOf(ind.getText()));
                ind.clear();
                graphView.update();

            }
        });


        sort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
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

    TextField ind = new TextField();
    Button buttonAddUzel = new Button("Добавить узел");

    Button buttonDeletUzel = new Button("Удалить узел");
    Button sort = new Button("Сортировать");
    Text resultSort = new Text();



}