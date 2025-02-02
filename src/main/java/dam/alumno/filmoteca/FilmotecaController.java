package dam.alumno.filmoteca;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class FilmotecaController {
   // private static ObservableList<Pelicula> filmoteca=DatosFilmoteca.getListaPeliculas();
    @FXML
    private Label welcomeText;
    @FXML
    private HBox infoBox;
    @FXML
    private Text info;
    @FXML
    private TableView<Pelicula> table;
    @FXML
    private TableColumn<Pelicula, String> title;
    @FXML
    private TableColumn<Pelicula, String> year;
    @FXML
    private TableColumn<Pelicula, String> id;
    @FXML
    private TableColumn<Pelicula, String> rating;





    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void initialize() {
        table.setItems(DatosFilmoteca.getListaPeliculas());
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        onRowSelected(newValue);
                    }
                }
        );



    }
    @FXML
    public void onRowSelected(Pelicula pelicula) {
        if (infoBox.getChildren().getFirst()!=infoBox.getChildren().getLast())
        {
            infoBox.getChildren().removeLast();
        }
        String movieInfo=(
                "Título: "+
                pelicula.getTitle()+
                ". Año de estreno: "+ pelicula.getYear()+
                        ". Descripción: "+pelicula.getDescription()+
                        "  Valoración media: "+pelicula.getRating()+
                        ".  ID: "+pelicula.getId()
        );
            info.setText(movieInfo);
            ImageView poster=new ImageView(pelicula.getPoster());
            poster.setPreserveRatio(true);
            poster.setFitHeight(200);
            infoBox.getChildren().add(poster);

    }
    @FXML
    public void quitDialog() {
       openQuitDialog();
    }
    //no carga el fxml y no he conseguido arreglarlo, así que he hecho apaños
    //porque no me carga ningún otro fxml tampoco, solo el main
    public void openQuitDialog() {
        Stage quit =new Stage();
        Scene scene;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("quit-view.fxml"));
            //System.out.println(fxmlLoader.getLocation());
            scene = new Scene(fxmlLoader.load(), 320, 240);

        } catch (IOException e) {
            System.err.println(e);
            VBox root=new VBox();
            root.getChildren().add(new Text("¿Estás seguro de que quieres cerrar la aplicación?"));
            HBox buttons=new HBox();
            Button exit=new Button("Cerrar");
            exit.setOnMouseClicked(event -> {System.exit(0);});
            buttons.getChildren().add(exit);
            Button cancel=new Button("Cancelar");
            cancel.setOnMouseClicked(event -> {quit.close();});
            buttons.getChildren().add(cancel);
            root.getChildren().add(buttons);
            scene=new Scene(root, 320, 240);
        }

        quit.initModality(Modality.APPLICATION_MODAL);
        quit.setScene(scene);
        quit.setTitle("¿Cerrar aplicación?");
        quit.show();
    }
}