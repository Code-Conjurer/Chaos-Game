package sample;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    final int canvasSizeX = 700;
    final int canvasSizeY = 900;
    public Canvas canvas;
    public Pane pane;
    ArrayList<Point> points = new ArrayList<>();
    Point currentPoint;
    GraphicsContext gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        currentPoint = new Point(-1, -1);
        canvas.setFocusTraversable(true);
        createPoint();
        run();
    }

    private void createPoint(){

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double xPos = event.getSceneX();
                double yPos = event.getSceneY();

                points.add(new Point(xPos, yPos));

                gc.fillOval(xPos, yPos, 2, 2);
            }
        });

    }

    private void run(){

        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    if (currentPoint.x == -1 && currentPoint.y == -1) {
                        currentPoint = generateFirstPoint();
                    }
                    currentPoint = generatePoint();
                    gc.fillOval(currentPoint.x, currentPoint.y, 2, 2);
                }
                if (event.getCode() == KeyCode.ENTER) {
                    if (currentPoint.x == -1 && currentPoint.y == -1) {
                        currentPoint = generateFirstPoint();
                    }
                    for (int i = 0; i < 100; i++) {
                        currentPoint = generatePoint();
                        gc.fillOval(currentPoint.x, currentPoint.y, 2, 2);
                    }
                }
            }
        });
    }

    private Point generatePoint(){
        int pointSelection;
        double newX;
        double newY;

        pointSelection = (int) (Math.random() * points.size());
        newX =  currentPoint.x + ((points.get(pointSelection).x - currentPoint.x) / 2);
        newY =  currentPoint.y + ((points.get(pointSelection).y - currentPoint.y) / 2);

        return new Point(newX, newY);
    }

    private Point generateFirstPoint(){
        double maxX = -1;
        double maxY = -1;
        double minX = canvasSizeX + 1;
        double minY = canvasSizeY + 1;

        for(Point p: points){
            if(p.x > maxX)
                maxX = p.x;
            if (p.x < minX)
                minX = p.x;
            if(p.y > maxY)
                maxY = p.y;
            if(p.y < minY)
                minY = p.y;
        }

        double x = minX + (Math.random() * (maxX - minX));
        double y = minY + (Math.random() * (maxY - minY));

        return new Point(x, y);
    }
}
