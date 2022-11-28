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
public class Child extends Person{

  String kindergarten;
  private static final long serialVersionUID = -687991492884005072L;  

  public Child(Integer id, String name, LocalDate birthDate, Address address, String kindergarten) {
    super(id, name, birthDate, address);
    this.kindergarten = kindergarten;
  }

}
