package be.uclouvain.launcher;

import UDPMessages.UDP_Message_StopSimulation;
import be.uclouvain.launcher.UDPSender;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("forbidden_reference")
@SarlSpecification("0.11")
@SarlElementType(10)
public class GUI extends Application {
  private UDPSender clientSender = new UDPSender();
  
  @SuppressWarnings({ "forbidden_reference", "forbidden_reference", "forbidden_reference" })
  protected FXMLLoader doApplicationStart(final Stage arg0) {
    abstract class __GUI_0 implements EventHandler<ActionEvent> {
      public abstract void handle(final ActionEvent eve);
    }
    
    arg0.setTitle("Application");
    Button btn = new Button();
    btn.setText("Exit Program\'");
    __GUI_0 ___GUI_0 = new __GUI_0() {
      public void handle(final ActionEvent eve) {
        UDP_Message_StopSimulation _uDP_Message_StopSimulation = new UDP_Message_StopSimulation(true);
        GUI.this.clientSender.<UDP_Message_StopSimulation>SendData(_uDP_Message_StopSimulation, "192.168.0.104", 65000);
      }
    };
    btn.setOnAction(___GUI_0);
    StackPane root = new StackPane();
    root.getChildren().add(btn);
    Scene _scene = new Scene(root, 300, 250);
    arg0.setScene(_scene);
    arg0.show();
    return null;
  }
  
  public void start(final Stage primaryStage) throws Exception {
    this.doApplicationStart(primaryStage);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  @SyntheticMember
  public GUI() {
    super();
  }
}
