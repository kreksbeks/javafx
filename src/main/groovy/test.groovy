import groovyx.javafx.beans.FXBindable
import javafx.scene.control.TextField

import static groovyx.javafx.GroovyFX.start

/**
 *
 * @author jimclarke
 */
class QuickTest {
    @FXBindable String qtText = "Quick Test"
    private int clickCount = 0

    def onClick = {
        qtText = "Quick Test ${++clickCount}"
    }
}

start {
    def qt = new QuickTest()

    stage(title: "GroovyFX Bind Demo", x: 100, y: 100, width: 400, height: 400, visible: true,
            style: "decorated", onHidden: { println "Close"}) {
        scene(fill: GROOVYBLUE) {
            vbox(spacing: 10, padding: 10) {
                TextField tf = textField(text: 'Change Me!')
                //button(text: bind(source: tf, sourceProperty: 'text'), onAction: {qt.onClick()})
                button(text: bind(tf, 'text'), onAction: {qt.onClick()})
                label(text: bind(tf.textProperty()))
                label(text: bind({tf.text}))
                label(text: bind(tf.text()))
                label(text: bind(tf.text()))

                // Bind to POGO fields annotated with @FXBindable
                // These three bindings are equivalent
                label(text: bind(qt,'qtText'))
                label(text: bind(qt.qtText()))
                label(text: bind(qt.qtTextProperty()).using({"<<< ${it} >>>"}) )
                label(id: "bind2")
                label(id: "bind1");

                // bidirectional bind
                textField(id: "tf2", promptText: 'Change Me!')
                textField(text: bind(tf2.textProperty()))
            }
        }

    }
    bind bind1.text() to bind2.text() to qt,"qtText"  using {"Converted: ${it}"}
}
