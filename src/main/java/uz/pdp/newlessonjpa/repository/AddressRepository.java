package uz.pdp.newlessonjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.newlessonjpa.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
