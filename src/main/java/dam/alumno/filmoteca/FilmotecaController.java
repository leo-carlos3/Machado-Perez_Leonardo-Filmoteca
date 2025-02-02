package dam.alumno.filmoteca;

import dam.alumno.filmoteca.dialogs.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class FilmotecaController {
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
       Dialogs.quitDialog();
    }
    @FXML
    public void removeDialog() {
        if (table.getSelectionModel().getSelectedItem() != null)
        {
            Dialogs.removeDialog(table.getSelectionModel().getSelectedItem().getId());
        }
        else{
            Dialogs.noMovieSelected();
        }
    }
    @FXML
    public void addMovie(){
        Dialogs.addMovie();
    }
    @FXML
    public void editMovie(){
        if (table.getSelectionModel().getSelectedItem() != null)
        {
            Dialogs.editMovie(table.getSelectionModel().getSelectedItem());
        }
        else {
            Dialogs.noMovieSelected();
        }
    }


}