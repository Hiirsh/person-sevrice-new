package telran.java2022.personsevrice.person.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee extends Person {

  String company;
  Integer salary;
  private static final long serialVersionUID = -687991492884005372L;

  public Employee(Integer id, String name, LocalDate birthDate, Address address, String company, int salary) {
    super(id, name, birthDate, address);
    this.company = company;
    this.salary = salary;
  }

}
