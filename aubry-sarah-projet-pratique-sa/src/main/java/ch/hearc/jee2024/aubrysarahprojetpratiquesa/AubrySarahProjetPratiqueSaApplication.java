package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AubrySarahProjetPratiqueSaApplication {

    public static void main(String[] args) {
        System.out.println("Before");
        SpringApplication.run(AubrySarahProjetPratiqueSaApplication.class, args);
        System.out.println("After");
    }

}
