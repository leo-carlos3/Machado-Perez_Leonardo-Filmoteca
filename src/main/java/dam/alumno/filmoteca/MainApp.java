package dam.alumno.filmoteca;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setMinHeight(600);
        stage.setMinWidth(628);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void init() {
        System.out.println("Cargando datos desde fichero datos/peliculas.json");
        DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();
        loadMovies();


        System.out.println(datosFilmoteca.getListaPeliculas());
    }

    public void stop() {
        ObservableList<Pelicula> listaPeliculas = DatosFilmoteca.getInstancia().getListaPeliculas();
       // System.out.println(listaPeliculas);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File("datos/peliculas2.json"),listaPeliculas);
        }catch (IOException e) {
            System.out.println("ERROR no se ha podido guardar los datos de la aplicación");
            e.printStackTrace();
        }

    }
    public void loadMovies(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Pelicula> lista = mapper.readValue(new File("datos/peliculas.json"),
                    mapper.getTypeFactory().constructCollectionType(List.class, Pelicula.class));

            DatosFilmoteca.getListaPeliculas().setAll(lista);
        } catch (IOException e){
            System.err.println("ERROR al cargar los datos. La aplicación no puede iniciarse");
            e.printStackTrace();
            System.exit(1);
        }

    }
}



