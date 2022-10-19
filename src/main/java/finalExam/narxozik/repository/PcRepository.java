package finalExam.narxozik.repository;


import finalExam.narxozik.model.PC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcRepository extends JpaRepository<PC,Long> {
}
