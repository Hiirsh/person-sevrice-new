package telran.java2022.personsevrice.person.dto;

import lombok.Getter;

@Getter
public class EmployeeDto extends PersonDto {
  private static final long serialVersionUID = -437991492884005072L;
  String company;
  Integer salary;

}
