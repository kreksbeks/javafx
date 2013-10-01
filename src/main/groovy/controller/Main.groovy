package controller

import groovy.transform.Field
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import javafx.util.converter.NumberStringConverter
import model.Table

import static groovyx.javafx.GroovyFX.start

@Field
Delta dragDelta = new Delta()

start {
    stage(title: "Prototype", visible: true) {
        scene {
            fxml (new File('../../resources/demo.fxml').text)
        }
    }

    Scene sc = primaryStage.scene

    Table table = new Table()
    def t1 = sc.lookup('#table1')
    def xView = sc.lookup('#xView')
    def yView = sc.lookup('#yView')

    bind t1.layoutX() to table.x()
    bind t1.layoutY() to table.y()
    Bindings.bindBidirectional(xView.text(), table.x(), new NumberStringConverter())
    Bindings.bindBidirectional(yView.text(), table.y(), new NumberStringConverter())


    t1.onMousePressed = { MouseEvent mouseEvent ->
        // record a delta distance for the drag and drop operation.
        dragDelta.x = t1.layoutX - mouseEvent.sceneX;
        dragDelta.y = t1.layoutY - mouseEvent.sceneY;
        t1.cursor = Cursor.MOVE;
    } as EventHandler;

    t1.onMouseReleased = { MouseEvent mouseEvent ->
        t1.cursor = Cursor.HAND;
    } as EventHandler;

    t1.onMouseDragged = { MouseEvent mouseEvent ->
        t1.layoutX = mouseEvent.sceneX + dragDelta.x;
        t1.layoutY = mouseEvent.sceneY + dragDelta.y;
    } as EventHandler;

    t1.onMouseEntered = { MouseEvent mouseEvent ->
        t1.cursor = Cursor.HAND;
    } as EventHandler;
}


class Delta{def x; def y;}