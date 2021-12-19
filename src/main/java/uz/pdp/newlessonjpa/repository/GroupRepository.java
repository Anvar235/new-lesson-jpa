package uz.pdp.newlessonjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.newlessonjpa.entity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    //JPA Query
    List<Group> findAllByFaculty_University_Id(Integer faculty_university_id);

    @Query("SELECT gr from groups gr where gr.faculty.university.id=:universityId")
    List<Group> getGroupsByUniversityId(Integer universityId);



}
