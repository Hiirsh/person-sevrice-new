package telran.java2022.personsevrice.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;
// import java.util.Map;
// import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java2022.personsevrice.person.dao.PersonRepository;
import telran.java2022.personsevrice.person.dto.AddressDto;
import telran.java2022.personsevrice.person.dto.ChildDto;
import telran.java2022.personsevrice.person.dto.CityPopulationDto;
import telran.java2022.personsevrice.person.dto.EmployeeDto;
import telran.java2022.personsevrice.person.dto.PersonDto;
import telran.java2022.personsevrice.person.dto.exeptions.PersonNotFoundExeption;
import telran.java2022.personsevrice.person.model.Address;
import telran.java2022.personsevrice.person.model.Child;
import telran.java2022.personsevrice.person.model.Employee;
import telran.java2022.personsevrice.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

  final PersonRepository personRepository;
  final ModelMapper modelMapper;

  @Override
  @Transactional
  public Boolean addPerson(PersonDto personDto) {
    if (personRepository.existsById(personDto.getId())) {
      return false;
    }
    personRepository.save(modelMapper.map(personDto, getModelClass(personDto)));
    return true;
  }

  @Override
  public PersonDto findPersonById(Integer id) {
    Person person = personRepository.findById(id).orElseThrow(PersonNotFoundExeption::new);
    return modelMapper.map(person, getDtoClass(person));
  }

  @Override
  @Transactional
  public PersonDto removePerson(Integer id) {
    Person person = personRepository.findById(id).orElseThrow(PersonNotFoundExeption::new);
    personRepository.delete(person);
    return modelMapper.map(person, getDtoClass(person));
  }

  @Override
  @Transactional
  public PersonDto updatePersonName(Integer id, String name) {
    Person person = personRepository.findById(id).orElseThrow(PersonNotFoundExeption::new);
    person.setName(name);
    // personRepository.save(person);
    return modelMapper.map(person, getDtoClass(person));
  }

  @Override
  @Transactional
  public PersonDto updatePersonAddress(Integer id, AddressDto address) {
    Person person = personRepository.findById(id).orElseThrow(PersonNotFoundExeption::new);
    person.setAddress(modelMapper.map(address, Address.class));
    return modelMapper.map(person, getDtoClass(person));
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<PersonDto> findPersonsByCity(String city) {
    return personRepository.findByAddressCity(city)
        .map(p -> modelMapper.map(p, PersonDto.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<PersonDto> findPersonsByName(String name) {
    return personRepository.findByName(name)
        .map(p -> modelMapper.map(p, PersonDto.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
    LocalDate from = LocalDate.now().minusYears(minAge);
    LocalDate to = LocalDate.now().minusYears(maxAge);
    return personRepository.findByBirthDateBetween(from, to)
        .map(p -> modelMapper.map(p, PersonDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public Iterable<CityPopulationDto> getCitiesPopulation() {
    /*
     * Map<String, Long> population =
     * StreamSupport.stream(personRepository.findAll().spliterator(), false)
     * .collect(Collectors.groupingBy(p -> p.getAddress().getCity(),
     * Collectors.counting()));
     * return population.entrySet().stream()
     * .map(e -> new CityPopulationDto(e.getKey(), e.getValue()))
     * .collect(Collectors.toList());
     */
    return personRepository.getCitiesPopulation();
  }

  @Override
  public Iterable<PersonDto> findEmployeeBySalary(int min, int max) {
    return personRepository.findEmployeeBySalary(min, max).stream()
        .map(e -> modelMapper.map(e, getDtoClass(e)))
        .collect(Collectors.toList());
  }

  @Override
  public Iterable<PersonDto> getChildren() {
    return personRepository.getChildren().stream()
        .map(c -> modelMapper.map(c, getDtoClass(c)))
        .collect(Collectors.toList());
  }

  @Override
  public void run(String... args) throws Exception {
    // Person person = new Person(1000, "Vasya Pupkin", LocalDate.of(1985, 8, 02),
    // new Address("Tel Aviv", "Ben Gvirol ", 87));
    // Child child = new Child(2000, "Vasya Pupkin jr.", LocalDate.of(2017, 3, 15),
    // new Address("Ashkelon", "Bar Kohva", 21), "Shalom");
    // Employee employee = new Employee(3000, "John Doe", LocalDate.of(1994, 7, 21),
    // new Address("Rehovot", "HaRav Meltzer", 213), "Motorolla", 20000);
    // personRepository.save(person);
    // personRepository.save(child);
    // personRepository.save(employee);
  }

  private Class<? extends Person> getModelClass(PersonDto personDto) {
    if (personDto instanceof EmployeeDto) {
      return Employee.class;
    }
    if (personDto instanceof ChildDto) {
      return Child.class;
    }
    return Person.class;
  }

  private Class<? extends PersonDto> getDtoClass(Person person) {
    if (person instanceof Employee) {
      return EmployeeDto.class;
    }
    if (person instanceof Child) {
      return ChildDto.class;
    }
    return PersonDto.class;
  }

}
