package com.daimao;

import com.daimao.service.DBService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

@SpringBootApplication
public class Main extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private static DBService dbService;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        applicationContext = new SpringApplicationBuilder()
                .sources(Main.class)
                .run(args);
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
    }

    public void start(Stage primaryStage) throws Exception {
        URL url = Main.class.getClassLoader().getResource("app.fxml");
        if (url == null) {
            throw new RuntimeException("app.fxml文件没有找到");
        }

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        // Controller 对象的创建，由 Spring 的 ApplicationContext 管理创建了
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return applicationContext.getBean(param);
            }
        });
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("本地文件搜索工具");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(900);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Bean
    public DataSource dataSource() throws Exception {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();

        String classesPath = Main.class.getProtectionDomain()
                .getCodeSource().getLocation().getFile();
        File classesDir = new File(URLDecoder.decode(classesPath, "UTF-8"));
        String path = classesDir.getParent() + File.separator + "searcher.db";

        String url = "jdbc:sqlite://" + path;
        sqLiteDataSource.setUrl(url);

        return sqLiteDataSource;
    }
}