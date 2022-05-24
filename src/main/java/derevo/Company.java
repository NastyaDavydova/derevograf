package derevo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
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
public class Company extends Application {
    private static final boolean mIns = false;
    private static final boolean mDel = true;
    @Override
    public void start(Stage primaryStage) throws Exception {
        int a[] = {70, 90, 30, 40, 20, 15, 78, 9, 8};
        int i;
        int n = a.length;
        derevo2<Integer> t = new derevo2<Integer>();

    System.out.printf(" Исходные данные: ");
        for ( i = 0; i < n; i++) {
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


        ArrayList<Integer> s = t.preOrder();
        uzelBox.getItems().addAll(s);

        sortFilterBox.getItems().addAll(
                "По значениям",
                "От корня",
                "По уровням"
        );

        root.getChildren().add(strings);

        strings.setPadding(new Insets(10, 30, 10, 30));
        strings.setSpacing(20);

        strings.getChildren().add(new Text("Select the user"));
        strings.getChildren().add(buttonBox);
        strings.getChildren().add(new Text("Add new Uzel"));
        strings.getChildren().add(addUzelBox);
        strings.getChildren().add(filters);
//        strings.getChildren().add(resultFilter);

        buttonBox.setSpacing(10);
        buttonBox.getChildren().add(uzelBox);
        buttonBox.getChildren().add(buttonGetInfo);
        buttonBox.getChildren().add(textInfo);

        addUzelBox.setSpacing(10);
      //  addUserBox.getChildren().add(t.insert(1));
        addUzelBox.getChildren().add(uzel);
        addUzelBox.getChildren().add(buttonAddUzel);

        filters.setSpacing(10);
        filters.getChildren().add(new Text("Uzel sort"));
        filters.getChildren().add(sortFilterBox);
        filters.getChildren().add(sort);


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Company");
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonAddUzel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int index = sortFilterBox.getSelectionModel().getSelectedIndex();
                t.insert(a[index]);


            }
        });

    }
    public Graph<Uzel,Integer> graph(derevo2<Integer> t, int n) {
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
            if ((t.getCol(t.search(s.get(i))) == true) && (t.search(s.get(i)).getParent() == null)) {
                t.search(s.get(i)).setX(400);
                t.search(s.get(i)).setY(50);
            }
            x = 50;
            if ((t.search(s.get(i)).getParent() != null) &&
                    (t.search(s.get(i)).getParent().getParent() == null)) {
                x = x * (h1 + 1);
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

        LinkedList<Vertex<Uzel>> temp = new LinkedList<>();


        for (i = 0; i < n; i++) {
            Vertex<Uzel> tg = dis.insertVertex(t.search(s.get(i)));
            temp.add(tg);
        }

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

        String customProps = "edge.label = true" + "\n" + "edge.arrow = false";

        SmartGraphProperties prop = new SmartGraphProperties(customProps);

        SmartGraphPanel<Uzel, Integer> graphView = new SmartGraphPanel<>(dis, prop);

        //graphView.getStylableLabel()

        for (i = 0; i < n; i++) {
            Vertex<Uzel> tg = temp.get(i);
            if (t.getCol(t.search(s.get(i))) == false) {
                graphView.getStylableVertex(tg).setStyle("-fx-stroke-width: 3;\n" + "-fx-stroke: red;\n" + "-fx-stroke-type: inside;\n" + "-fx-fill: red;");
            }


        }

    return dis;}
    public static void main(String[] args) {
        launch(args);
    }

    Group root = new Group();

    VBox strings = new VBox();

    HBox buttonBox = new HBox();

    HBox addUzelBox = new HBox();
    HBox filters = new HBox();

    ComboBox<Integer> uzelBox = new ComboBox<>();
    ComboBox<String> sortFilterBox = new ComboBox<>();
    final private int WIDTH = 1000;
    final private int HEIGHT = 600;

    private ArrayList<User> tg = new ArrayList<>();

    Button buttonGetInfo = new Button("Info");
    Text textInfo = new Text();

    Button buttonAddUzel = new Button("Add User");
    TextField uzel = new TextField();

    Button buttonDeleteUzel = new Button("Delete User");
    TextField uzeld = new TextField();
    Button Grafic = new Button("Graph");
    Button sort = new Button("sort");
    derevo2<Integer> resultFilter = new derevo2<Integer>();

}