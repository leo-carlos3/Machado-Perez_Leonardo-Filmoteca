package dam.alumno.filmoteca.dialogs;

import dam.alumno.filmoteca.DatosFilmoteca;
import dam.alumno.filmoteca.MainApp;
import dam.alumno.filmoteca.Pelicula;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class Dialogs {
    public static void quitDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Cerrar aplicación?");
        alert.setHeaderText("Si sales, luego podrás volver a iniciar la aplicación.");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> Platform.exit());
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
         //podría calcular ya el id sin ocuparlo pero no estoy seguro de si
        //eso cumple "Se asigna id a una película solo si se inserta a la lista"
        //así que por si acaso lo dejo así
         Text id=new Text("ID no asignado");
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
        File img=new File("movie.jpg");
        final Image placeholder=new Image(img.toURI().toString());
        url.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try
                {
                    image.setImage(new Image(url.getText()));
                }
                catch (Exception e){
                    image.setImage(placeholder);

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
            //y controla que se dé un int para el año
            int release=2025;
            try {
                release = Integer.parseInt(year.getText());
            }catch (Exception e){
                System.err.println("Año no válido");
            }
            final int releaseYear=release;
            alert.showAndWait().filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        Pelicula movie=new Pelicula();
                        movie.setId(DatosFilmoteca.getListaPeliculas().getLast().getId()+1);
                        movie.setTitle(title.getText());
                        movie.setDescription(description.getText());
                        movie.setYear(releaseYear);
                        movie.setRating((float) rating.getValue());
                        movie.setPoster(image.getImage().getUrl());
                        DatosFilmoteca.getListaPeliculas().add(movie);
                        add.close();
                    });

        });
        root.getChildren().addAll(id,title,year,description,rating,url, image, confirmar);
        root.setAlignment(Pos.CENTER);
        Scene scene=new Scene(root, 400, 500);
        add.initModality(Modality.APPLICATION_MODAL);
        add.setTitle("Añadir película");
        add.setScene(scene);
        add.show();
    }
    //para modificar una película
    //es casi idéntico al addMovie, salvo porque necesita un objeto movie que usa para
    //dar su valor inicial a los campos
    public static void editMovie(Pelicula movie){
        Stage edit=new Stage();
        VBox root=new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);
        Text id=new Text("ID: "+movie.getId());
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
        File img=new File("movie.jpg");
        final Image placeholder=new Image(img.toURI().toString());
        try
        {
            image.setImage(new Image(movie.getPoster()));
        }
        catch (Exception e){
            image.setImage(placeholder);
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
                    image.setImage(placeholder);
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
                        movie.setPoster(image.getImage().getUrl());
                        DatosFilmoteca.getListaPeliculas().remove(movie);
                        DatosFilmoteca.getListaPeliculas().add(movie);
                        edit.close();
                    });

        });
        root.getChildren().addAll(id,title,year,description,rating,url, image, confirmar);
        root.setAlignment(Pos.CENTER);
        Scene scene=new Scene(root, 400, 600);
        edit.initModality(Modality.APPLICATION_MODAL);
        edit.setTitle("Modificar película");
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
