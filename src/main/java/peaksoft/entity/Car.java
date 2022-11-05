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
@Table(name = "cars")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "car_name")
    private String name;


    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "person_id")
    private Person person;

    public Car(String name, Person person) {
        this.name = name;
        this.person = person;
    }

    public static void createCar(Car car) {

            try (Session session = HibernateConfig.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.save(car);
                session.getTransaction().commit();
                System.out.println("Car with name successfylly ");
            }

        }

    public static void updateCar(Car car) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Car car1 = session.get(Car.class, car.getId());
            session.merge(car);
            session.getTransaction().commit();
            System.out.println("Car with id: " + car.getId() + " was successfully updated");
        }
    }

    public static void deleteCarById(Long carId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Car car = session.get(Car.class, carId);
            session.remove(car);
            session.getTransaction().commit();
            System.out.println("Car id: " + carId + " was successfully deleted");
        }
    }

    public static List<Car> getAllCars() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<Car> cars = session.createQuery("select c from Car c").getResultList();
            session.getTransaction().commit();
            System.out.println("Found " + cars.size() + " carses");
            return cars.stream().sorted(Comparator.comparing(Car::getName)).toList();
        }
    }
    }

