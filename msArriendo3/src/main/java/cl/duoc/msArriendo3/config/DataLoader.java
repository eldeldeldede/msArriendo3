package cl.duoc.msArriendo3.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msArriendo3.model.Arriendo;
import cl.duoc.msArriendo3.repository.ArriendoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(ArriendoRepository arriendoRepo){
        return args -> {
            if(arriendoRepo.count() > 0){
                System.out.println("La base de datos ya fue inicializada");
            }else{
                Arriendo arriendo1 = new Arriendo(null, new java.util.Date(), new java.util.Date(), 1, 1);
                Arriendo arriendo2 = new Arriendo(null, new java.util.Date(), new java.util.Date(), 2, 2);
                arriendoRepo.save(arriendo1);
                arriendoRepo.save(arriendo2);

                System.out.println("Base de datos inicializada con datos de ejemplo");
            }
        };
    }
}
