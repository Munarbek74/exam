package peaksoft;

import jakarta.persistence.Query;
import org.hibernate.Session;
import peaksoft.configurations.HibernateConfig;
import peaksoft.entity.Car;
import peaksoft.entity.Person;
import peaksoft.entity.SocialMedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

//
//        Person person = new Person("Asan16", 24);
//        Person person1 = new Person("Uson56", 36);
//        Person person2 = new Person("Batma", 18);
//        Person person3 = new Person("Zuura", 17);
//        List<Person> personList = new ArrayList<>(Arrays.asList(person,person1));
//        List<Person> personList1 = new ArrayList<>(Arrays.asList(person2,person3));
//        System.out.println(personList1);
//        Car car = new Car("Mers2",person);
//        Car car1 = new Car("Tayota",person2);
//        Car car2 = new Car("BMW",person1);
//        Car car3 = new Car("Nissan",person3);
//
//        List<Car> cars = new ArrayList<>(Arrays.asList(car,car1));
//        List<Car> cars1 = new ArrayList<>(Arrays.asList(car2,car3));
//        Car.createCar(car);
//        Car.createCar(car1);
//        Car.createCar(car2);
//        Car.createCar(car3);
//        person.setCars(cars);
//        person.setCars(cars1);
//        SocialMedia socialMedia = new SocialMedia(1l,"222JS356");
//        SocialMedia socialMedia1 = new SocialMedia("Java", personList1);
//        SocialMedia.createSocialMedia(socialMedia);
//        SocialMedia.createSocialMedia(socialMedia1);
//SocialMedia.deleteSmById(7l);
getCarByPersonName("Uson56");
//        SocialMedia.updateSM(socialMedia);
//assignCarToPerson(car, 2l);
//        SocialMedia.getAllSm();
//        Car.getAllCars();
    }


    public static List<Car> getCarByPersonId(Long personId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Person person = session.get(Person.class, personId);
            List<Car> cars = person.getCars();
            session.getTransaction().commit();
            System.out.println("Found " + cars.size() + " cars");
            return cars;
        }
    }

    public static void assignCarToPerson(Car car, Long personId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            session.persist(car);
            Person person = session.get(Person.class, personId);
            person.addCarsToList(car);
            session.getTransaction().commit();
            System.out.println("A new Car with name: " + car.getName() + " was successfully assigned to Person id " + personId);
        }

   }

    public static List<Car> getCarByPersonName(String personName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("From Person  where  name =: personNameParam" );
            query.setParameter("personNameParam", personName);
            List list= query.getResultList();
            System.out.println(list);
            session.getTransaction().commit();
            System.out.println("Found " + list.get(0).toString() + " cars");
            return list;
        }
    }


        }

