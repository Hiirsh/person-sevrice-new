package telran.java2022.personsevrice.person.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import telran.java2022.personsevrice.person.dto.CityPopulationDto;
import telran.java2022.personsevrice.person.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface PersonRepository extends CrudRepository<Person, Integer> {

  @Query("select p from Person p where p.name=?1")
  Stream<Person> findByName(String name);

  @Query("select p from Person p where p.address.city=:city")
  Stream<Person> findByAddressCity(@Param("city") String city);

  Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);
  
  @Query("select new telran.java2022.personsevrice.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
  List<CityPopulationDto> getCitiesPopulation();
  
  @Query(value = "SELECT * FROM PERSONS p WHERE p.DTYPE ='Employee' AND p.SALARY BETWEEN ?1 AND ?2", nativeQuery = true)
  List<Person> findEmployeeBySalary(int min, int max);

  @Query(value = "SELECT * FROM PERSONS p WHERE p.DTYPE ='Child'", nativeQuery = true)
  List<Person> getChildren();
}
