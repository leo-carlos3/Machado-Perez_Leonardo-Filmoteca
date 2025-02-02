package dam.alumno.filmoteca.dialogs;

import dam.alumno.filmoteca.DatosFilmoteca;
import dam.alumno.filmoteca.Pelicula;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class Dialogs {
    public static void quitDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Cerrar aplicación?");
        alert.setHeaderText("Si sales, luego podrás volver a iniciar la aplicación.");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.exit(0));
    }
    public static void removeDialog(int id){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Eliminar película?");
        alert.setHeaderText("Si la eliminas, desaparecerá para siempre");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> removeMovie(id));
    }

    private static void removeMovie(int id){
        DatosFilmoteca.getListaPeliculas().removeIf(Pelicula -> Pelicula.getId() == id);
    }

    //no carga el fxml y no he conseguido arreglarlo, así que he hecho apaños
    public static void addMovie(){
        Stage add=new Stage();
         VBox root=new VBox();
         root.setPadding(new Insets(20));
         root.setSpacing(10);
         Text id=new Text(DatosFilmoteca.getListaPeliculas().size()+"");
         TextField title=new TextField();
         title.setPromptText("Titulo de la película");
        TextField year=new TextField();
        year.setPromptText("Año de estreno");
        TextField description=new TextField();
        description.setPromptText("Sinopsis");
        Slider rating=new Slider();
        rating.setMin(0); rating.setMax(10);
        rating.setShowTickLabels(true);
        TextField url=new TextField();
        url.setPromptText("URL de la carátula");
        ImageView image=new ImageView();
        image.setPreserveRatio(true);
        image.setFitHeight(200);
        url.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try
                {
                    image.setImage(new Image(url.getText()));
                }
                catch (Exception e){
                    image.setImage(null);
                }

            }
        });
        Button confirmar=new Button("Confirmar");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Añadir película?");
        alert.setContentText("¿Son correctos estos datos?");
        confirmar.setOnAction(event -> {
            if (title.getText().isEmpty()||year.getText().isEmpty()||
                    description.getText().isEmpty()||url.getText().isEmpty())
            {return;}
            //comprueba que haya valores en todos los campos
            int release=2025;
            try {
                release = Integer.parseInt(year.getText());
            }catch (Exception e){
                System.err.println("Año no válido");
            }
            alert.showAndWait().filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        Pelicula movie=new Pelicula();
                        movie.setId(DatosFilmoteca.getListaPeliculas().size());
                        movie.setTitle(title.getText());
                        movie.setDescription(description.getText());
                        movie.setYear(Integer.parseInt(year.getText()));
                        movie.setRating((float) rating.getValue());
                        DatosFilmoteca.getListaPeliculas().add(movie);
                        add.close();
                    });

        });
        root.getChildren().addAll(id,title,year,description,rating,url, image, confirmar);
        root.setAlignment(Pos.CENTER);
        Scene scene=new Scene(root);
        add.initModality(Modality.APPLICATION_MODAL);
        add.setScene(scene);
        add.show();
    }
    public static void editMovie(Pelicula movie){
        Stage edit=new Stage();
        VBox root=new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);
        Text id=new Text(movie.getId()+"");
        TextField title=new TextField();
        title.setPromptText("Titulo de la película");
        title.setText(movie.getTitle());
        TextField year=new TextField();
        year.setPromptText("Año de estreno");
        year.setText(movie.getYear()+"");
        TextField description=new TextField();
        description.setPromptText("Sinopsis");
        description.setText(movie.getDescription());
        Slider rating=new Slider();
        rating.setMin(0); rating.setMax(10);
        rating.setShowTickLabels(true);
        rating.setValue(movie.getRating());
        TextField url=new TextField();
        url.setPromptText("URL de la carátula");
        url.setText(movie.getPoster());
        ImageView image=new ImageView();
        try
        {
            image.setImage(new Image(movie.getPoster()));
        }
        catch (Exception e){
            image.setImage(null);
        }
        image.setPreserveRatio(true);
        image.setFitHeight(200);
        url.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try
                {
                    image.setImage(new Image(url.getText()));
                }
                catch (Exception e){
                    image.setImage(null);
                }

            }
        });
        Button confirmar=new Button("Confirmar");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Modificar datos de esta película?");
        alert.setContentText("¿Son correctos estos datos?");
        confirmar.setOnAction(event -> {
            if (title.getText().isEmpty()||year.getText().isEmpty()||
                    description.getText().isEmpty()||url.getText().isEmpty())
            {return;}
            //comprueba que haya valores en todos los campos

            alert.showAndWait().filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        movie.setId(movie.getId());
                        movie.setTitle(title.getText());
                        movie.setDescription(description.getText());
                        movie.setYear(Integer.parseInt(year.getText()));
                        movie.setRating((float) rating.getValue());
                        DatosFilmoteca.getListaPeliculas().remove(movie);
                        DatosFilmoteca.getListaPeliculas().add(movie);
                        edit.close();
                    });

        });
        root.getChildren().addAll(id,title,year,description,rating,url, image, confirmar);
        root.setAlignment(Pos.CENTER);
        Scene scene=new Scene(root);
        edit.initModality(Modality.APPLICATION_MODAL);
        edit.setScene(scene);
        edit.show();
    }
    public static void noMovieSelected(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setContentText("Para usar esta función debes seleccionar una película de la lista");
        alert.showAndWait();
    }


}
