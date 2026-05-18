package cl.duoc.msArriendo3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msArriendo3.model.Arriendo;

@Repository
public interface ArriendoRepository extends JpaRepository<Arriendo, Integer> {

}
