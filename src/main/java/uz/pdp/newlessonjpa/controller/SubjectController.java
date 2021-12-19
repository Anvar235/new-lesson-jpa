package uz.pdp.newlessonjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.newlessonjpa.entity.Subject;
import uz.pdp.newlessonjpa.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject){
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName)
            return "Subject already exist";
        subjectRepository.save(subject);
        return "Subject added";
    }

    @GetMapping
    public List<Subject> getSubject(){
        return subjectRepository.findAll();
    }

//    @PutMapping(value = "/subject/{id}")
//    public String editSubject(@PathVariable Integer id){
//        Optional<Subject> optionalSubject = subjectRepository.findById(id);
//        if (!optionalSubject.isPresent())
//            return "Subject not found";
//
//    }

}
