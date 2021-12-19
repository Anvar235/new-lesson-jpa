package uz.pdp.newlessonjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.newlessonjpa.entity.Faculty;
import uz.pdp.newlessonjpa.entity.Group;
import uz.pdp.newlessonjpa.payload.GroupDto;
import uz.pdp.newlessonjpa.repository.FacultyRepository;
import uz.pdp.newlessonjpa.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @GetMapping
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId) {
        return groupRepository.findAllByFaculty_University_Id(universityId);
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());

        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (!optionalFaculty.isPresent())
            return "Faculty not found";
        group.setFaculty(optionalFaculty.get());
        groupRepository.save(group);

        return "Group added";
    }
}
