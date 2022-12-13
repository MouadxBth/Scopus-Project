package us.elite;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "scopus-project")
@PWA(name = "Scopus-Project", shortName = "Scopus-Project", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application implements AppShellConfigurator {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
