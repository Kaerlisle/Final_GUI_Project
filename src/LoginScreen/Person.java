package LoginScreen;

import java.util.Date;

// do not make kalikot already as is (i think)
public class Person {
    private String name;
    private Date birthDay;
    private int age;

    public Person() {
    }

    public Person(String name, Date birthDay) {
        this.name = name;
        this.birthDay = birthDay;
        computeAge(birthDay);
    }

    public String getName() {
        return name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public int getAge() {
        return age;
    }

    public void computeAge(Date birthDay) {
        Date currentDate = new Date();
        long millisecondsPerYear = 365L * 24 * 60 * 60 * 1000;
        long ageInMillis = currentDate.getTime() - birthDay.getTime();
        age = (int) (ageInMillis / millisecondsPerYear);
    }
}
