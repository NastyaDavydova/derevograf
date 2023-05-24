package derevo;

import java.util.ArrayList;
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

public class VisualFinal extends Application {
    private static final boolean mIns = false;
    private static final boolean mDel = true;

    public int initialValues[] = {70, 90, 30, 40, 20, 15, 78, 9, 8};

    Group root = new Group();

    VBox strings = new VBox();

    HBox OperUzelBox = new HBox();
    //  HBox DelUzelBox = new HBox();
    HBox sorts = new HBox();
    HBox graficder = new HBox();
    HBox izmen = new HBox();

    ComboBox<Uzel> uzelBox = new ComboBox<>();
    ComboBox<String> sortFilterBox = new ComboBox<>();

    final private int WIDTH = 1024;
    final private int HEIGHT = 768;

    derevo2<Integer> tree = new derevo2<Integer>();
    public ArrayList<Integer> preOrder = tree.preOrder();

    Graph<Uzel, Integer> graph = new GraphEdgeList<>();

    SmartGraphPanel<Uzel, Integer> graphView;

    LinkedList<Vertex<Uzel>> temp;
    TextField uzelInput = new TextField();
    Button buttonAddUzel = new Button("Добавить узел");

    Button buttonDeleteUzel = new Button("Удалить узел");
    Button buttonNext = new Button("После изменения");
    Button buttonLast = new Button("До изменения");
    Button sort = new Button("Сортировать");
    Text resultSort = new Text();

    int currentVersion = 0;
    SmartGraphProperties prop;

    //Назначение координат вершинам
    private void assignCoordsDerevo() {
        int h1;
        preOrder = tree.preOrder();
        double h = (Math.log(tree.uzelNumber() + 1) / Math.log(2)) - 1;
        System.out.println("Высота " + h);
        double x;
        Uzel uzelTemp;
        for (int i = 0; i < tree.uzelNumber(); i++) {
            h1 = 0;
            uzelTemp = tree.search(preOrder.get(i));
            while (uzelTemp.getParent() != null) {
                h1 = h1 + 1;
                uzelTemp = uzelTemp.getParent();
            }
            if ((tree.search(preOrder.get(i)).col) && (tree.search(preOrder.get(i)).getParent() == null)) {
                tree.search(preOrder.get(i)).setX(450);
                tree.search(preOrder.get(i)).setY(50);
            }
            x = 15;
            if (tree.search(preOrder.get(i)).getParent() != null) {
                x = x * (2 * h - h1);
            }
            if ((tree.search(preOrder.get(i)).getParent() != null) &&
                    ((tree.search(preOrder.get(i)).getParent()).getChilRight() == tree.search(preOrder.get(i))) && (tree.search(preOrder.get(i)).getParent().getParent() == null)) {
                tree.search(preOrder.get(i)).setX((tree.search(preOrder.get(i)).getParent()).getX() + 20 * (2 * h + 2));
                tree.search(preOrder.get(i)).setY((tree.search(preOrder.get(i)).getParent()).getY() + 50);

            } else if ((tree.search(preOrder.get(i)).getParent() != null) &&
                    ((tree.search(preOrder.get(i)).getParent()).getChilLeft() == tree.search(preOrder.get(i))) && (tree.search(preOrder.get(i)).getParent().getParent() == null)) {
                tree.search(preOrder.get(i)).setX((tree.search(preOrder.get(i)).getParent()).getX() - 25 * (2 * h - h1 + 2));
                tree.search(preOrder.get(i)).setY((tree.search(preOrder.get(i)).getParent()).getY() + 50);
            } else if ((tree.search(preOrder.get(i)).getParent() != null) &&
                    ((tree.search(preOrder.get(i)).getParent()).getChilRight() == tree.search(preOrder.get(i))) && (tree.search(preOrder.get(i)).getParent().getParent() != null)) {
                tree.search(preOrder.get(i)).setX((tree.search(preOrder.get(i)).getParent()).getX() + x);
                tree.search(preOrder.get(i)).setY((tree.search(preOrder.get(i)).getParent()).getY() + 75);

            } else if ((tree.search(preOrder.get(i)).getParent() != null) &&
                    ((tree.search(preOrder.get(i)).getParent()).getChilLeft() == tree.search(preOrder.get(i))) && (tree.search(preOrder.get(i)).getParent().getParent() != null)) {
                tree.search(preOrder.get(i)).setX((tree.search(preOrder.get(i)).getParent()).getX() - x);
                tree.search(preOrder.get(i)).setY((tree.search(preOrder.get(i)).getParent()).getY() + 75);
            }
        }
    }

    private LinkedList<Vertex<Uzel>> buildGraph() {
        temp = new LinkedList<>();
        for (Edge e : graph.edges()) {
            graph.removeEdge(e);
        }
        for (Vertex v : graph.vertices()) {
            graph.removeVertex(v);
        }
        //Ввод вершин в граф
        for (int i = 0; i < tree.uzelNumber(); i++) {
            //System.out.println();
            Vertex<Uzel> tg = graph.insertVertex(tree.search(preOrder.get(i)));
            temp.add(tg);
        }
        //Создание ребер в графе
        for (int i = 0; i < tree.uzelNumber(); i++) {

            try {
                graph.insertEdge(tree.search(preOrder.get(i)), tree.search(preOrder.get(i)).getParent(), i);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
//        System.out.println("vertices: " + graph.vertices() + " edges: " + graph.edges());
        return temp;
    }

    private void prepareGraph() {
        assignCoordsDerevo();
        buildGraph();
        graphView = new SmartGraphPanel<>(graph, prop);
        graphView.setVertexDoubleClickAction(graphVertex -> {
            System.out.println("Vertex contains element: " + graphVertex.getUnderlyingVertex().element());
            System.out.println("coords: " + graphVertex.getUnderlyingVertex().element().x +
                    " " + graphVertex.getUnderlyingVertex().element().y);
        });
        setGraphStyles();
    }

    private void setGraphStyles() {
//        System.out.println("SGS");
        for (int i = 0; i < temp.size(); i++) {
            Vertex<Uzel> tg = temp.get(i);
//            System.out.println(graphView.getStylableVertex(tg));
            //Если вершина - красная?
            if (!tree.search(preOrder.get(i)).col) {
//                System.out.println("tg " + tg);
                graphView.getStylableVertex(tg).setStyle(
                        "-fx-stroke-width: 3;\n" + "-fx-stroke: red;\n"
                                + "-fx-stroke-type: inside;\n" + "-fx-fill: red;");
            }
        }
    }

    //Установка координат в graphView
    private void setCoordsGV() {
        for (int i = 0; i < tree.uzelNumber(); i++) {
            Vertex<Uzel> tg = temp.get(i);
            graphView.setVertexPosition(tg, tree.search(preOrder.get(i)).getX(), tree.search(preOrder.get(i)).getY());
//            System.out.println(tree.search(preOrder.get(i))
//                    + " " + tree.search(preOrder.get(i)).getX() + " " + tree.search(preOrder.get(i)).getY());
        }
    }

    private void initializeUI(Stage primaryStage) {
        sortFilterBox.getItems().addAll(
                "Инфиксный",
                "Префиксный",
                "Постфиксный"
        );

        root.getChildren().add(strings);

//        primaryStage.setHeight(512);
//        primaryStage.setWidth(1024);

        strings.setPadding(new Insets(10, 30, 10, 30));
        strings.setSpacing(20);

        strings.getChildren().add(new Text("Добавить новый узел"));
        strings.getChildren().add(OperUzelBox);
        strings.getChildren().add(sorts);
        strings.getChildren().add(resultSort);
        strings.getChildren().add(izmen);
        strings.getChildren().add(graficder);

        OperUzelBox.setSpacing(10);
        OperUzelBox.getChildren().add(buttonAddUzel);
        OperUzelBox.getChildren().add(buttonDeleteUzel);
        OperUzelBox.getChildren().add(uzelInput);

        izmen.setSpacing(10);
        izmen.getChildren().add(buttonLast);
        izmen.getChildren().add(buttonNext);

        graficder.setSpacing(10);
        graficder.getChildren().add(graphView);


        sorts.setSpacing(10);
        sorts.getChildren().add(new Text("Вид обхода дерева"));
        sorts.getChildren().add(sortFilterBox);
        sorts.getChildren().add(sort);

        Scene scene = new Scene(root, 1024, 576);
        primaryStage.setTitle("Красно-черное дерево");
        primaryStage.setScene(scene);
        primaryStage.show();
        graphView.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        for (int i = 0; i < initialValues.length; i++) {
            tree.insert(initialValues[i]);
            if (mIns) {
                System.out.printf(" Добавить узел: %uzelTemp\n", initialValues[i]);
                System.out.print(" Детали дерева: \n");
                tree.print();
                System.out.print("\n");
            }
        }
        ArrayList<derevo2> trees = new ArrayList<>();
        trees.add(tree.copy());
        graph = new GraphEdgeList<>();
        String gvProps = "edge.label = false" + "\n" + "edge.arrow = false";
        prop = new SmartGraphProperties(gvProps);
        prepareGraph();
        //Инициализация пользовательского интерфейса
        initializeUI(primaryStage);
        setCoordsGV();

        buttonAddUzel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("\n\n\n");
                if (tree.search(Integer.valueOf(uzelInput.getText())) == null) {
                    while (trees.size() > currentVersion + 1) {
                        System.out.println(trees.size() > currentVersion + 1);
                        trees.remove(trees.size() - 1);
                    }
                    tree.insert(Integer.valueOf(uzelInput.getText()));

                    derevo2 tr = tree.copy();
                    trees.add(tr);
                    currentVersion = trees.size() - 1;
                    System.out.println("trees: " + trees);
                    for (derevo2 t : trees) {
                        System.out.println();
                        t.print();
                    }
                    graficder.getChildren().clear();
                    prepareGraph();
                    //Инициализация пользовательского интерфейса
                    setCoordsGV();
                    graficder.getChildren().add(graphView);
                }
                uzelInput.clear();
            }
        });

        buttonDeleteUzel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("\n\n\n");
                if (tree.search(Integer.valueOf(uzelInput.getText())) != null) {
                    while (trees.size() > currentVersion + 1) {
                        trees.remove(trees.size() - 1);
                        System.out.println(trees.size() > currentVersion + 1);
                        trees.remove(trees.size() - 1);
                    }
                    tree.remove(Integer.valueOf(uzelInput.getText()));
                    trees.add(tree.copy());
                    currentVersion = trees.size() - 1;
                    System.out.println("trees " + trees);
                    for (derevo2 tree1 : trees) {
                        System.out.println();
                        tree1.print();
                    }
                    graficder.getChildren().clear();
                    prepareGraph();
                    //Инициализация пользовательского интерфейса
                    setCoordsGV();
                    graficder.getChildren().add(graphView);
                }
                uzelInput.clear();
            }
        });

        buttonNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (currentVersion < trees.size() - 1) {
                    currentVersion++;
                    System.out.println("next " +currentVersion);
                    for (derevo2 tree1 : trees) {
                        System.out.println();
                        tree1.print();
                    }
                    tree = trees.get(currentVersion).copy();
                    graficder.getChildren().clear();
                    prepareGraph();
                    //Инициализация пользовательского интерфейса
                    setCoordsGV();
                    graficder.getChildren().add(graphView);
                }
            }
        });
        buttonLast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (currentVersion > 0) {
                    currentVersion--;
                    System.out.println("last " +currentVersion);
                    for (derevo2 tree1 : trees) {
                        System.out.println();
                        tree1.print();
                    }
                    tree = trees.get(currentVersion).copy();
                    graficder.getChildren().clear();
                    prepareGraph();
                    //Инициализация пользовательского интерфейса
                    setCoordsGV();
                    graficder.getChildren().add(graphView);
                }

            }
        });
        sort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int index = sortFilterBox.getSelectionModel().getSelectedIndex();
                ArrayList<Integer> d;
                if (index == 0) {
                    d = tree.inOrder();
                } else if (index == 2) {
                    d = tree.postOrder();
                } else {
                    d = tree.preOrder();
                }
                resultSort.setText(d.toString());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}