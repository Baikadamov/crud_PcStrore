package finalExam.narxozik.repository;


import finalExam.narxozik.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BonusRepository  extends JpaRepository<Bonus,Long> {




}
