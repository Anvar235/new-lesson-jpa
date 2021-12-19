package uz.pdp.newlessonjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.newlessonjpa.entity.Address;
import uz.pdp.newlessonjpa.entity.University;
import uz.pdp.newlessonjpa.payload.UniversityDto;
import uz.pdp.newlessonjpa.repository.AddressRepository;
import uz.pdp.newlessonjpa.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;


@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AddressRepository addressRepository;

    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities(){
        return universityRepository.findAll();
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.GET)
    public University getUniversityById(@PathVariable Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        return optionalUniversity.orElseGet(University::new);
    }


    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto){
        Address address=new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        Address saveAddress = addressRepository.save(address);

        University university=new University();
        university.setName(universityDto.getName());
        university.setAddress(saveAddress);
        universityRepository.save(university);
        return "Successfully saved";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()){
            University university = optionalUniversity.get();
            university.setName(universityDto.getName());

            Address address = university.getAddress();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());

            addressRepository.save(address);
            universityRepository.save(university);

            return "University edited";
        }
        return "University not found";
    }

    @RequestMapping(value = "/university/{id}")
    public String deleteUniversity(@PathVariable Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (!optionalUniversity.isPresent())
            return "University does not exist";

        universityRepository.deleteById(id);
        return "University deleted";
    }
}
