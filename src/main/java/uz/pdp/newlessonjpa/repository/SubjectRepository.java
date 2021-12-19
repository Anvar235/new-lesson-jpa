package uz.pdp.newlessonjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.newlessonjpa.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);

}
