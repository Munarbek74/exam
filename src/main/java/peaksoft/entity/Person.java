package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Session;
import peaksoft.configurations.HibernateConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "persons")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "person_age")
    private Integer age;

    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Car> cars;
    @ManyToMany
    @JoinTable(
            name = "person_sm",
            joinColumns = @JoinColumn(name = "sm_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<SocialMedia> socialMedia;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, Integer age,  List<SocialMedia> socialMedia) {
        this.name = name;
        this.age = age;
        this.socialMedia = socialMedia;
    }

    public void addCarsToList(Car car) {

        if (this.cars == null) {
            this.cars = new ArrayList<>();
        }
        this.cars.add(car);

    }
    public static void createPerson(Person person) {

        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();
            System.out.println("Person with name successfylly ");
        }

    }
    public static void updatePerson(Person person) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Person person1 = session.get(Person.class, person.getId());
            session.merge(person);
            session.getTransaction().commit();
            System.out.println("Person with id: " + person.getId() + " was successfully updated");
        }
    }

    public static void deletePersonById(Long personId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Person person = session.get(Person.class, personId);
            session.remove(person);
            session.getTransaction().commit();
            System.out.println("Person id: " + personId + " was successfully deleted");
        }
    }

    public static List<Person> getAllPersons() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<Person> personList = session.createQuery("select c from Person c").getResultList();
            session.getTransaction().commit();
            System.out.println("Found " + personList.size() + " persons");
            return personList.stream().sorted(Comparator.comparing(Person::getName)).toList();
        }
    }
}


