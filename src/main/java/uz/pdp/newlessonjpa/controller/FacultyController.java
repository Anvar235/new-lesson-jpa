package uz.pdp.newlessonjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.newlessonjpa.entity.Faculty;
import uz.pdp.newlessonjpa.entity.University;
import uz.pdp.newlessonjpa.payload.FacultyDto;
import uz.pdp.newlessonjpa.repository.FacultyRepository;
import uz.pdp.newlessonjpa.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    UniversityRepository universityRepository;

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {

        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "This University and Faculty exist";

        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());

        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if (!optionalUniversity.isPresent())
            return "University not found";
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);

        return "Faculty saved";
    }

    //VAZIRLIK UCHUN
    @GetMapping
    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    //UNIVERSITET HODIMI UCHUN 
    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId) {
        return facultyRepository.findAllByUniversityId(universityId);
    }

    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        try {
            facultyRepository.deleteById(id);
            return "Faculty deleted";

        } catch (Exception e) {
            return "Error in deleting";
        }
    }

    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (!optionalFaculty.isPresent())
            return "Faculty does not exist";
        Faculty faculty = optionalFaculty.get();
        faculty.setName(facultyDto.getName());

        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if (!optionalUniversity.isPresent())
            return "University not found";
        faculty.setUniversity(optionalUniversity.get());

        facultyRepository.save(faculty);
        return "Faculty edited";
    }
}
