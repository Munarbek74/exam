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
@Table(name = "sm")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "sm_name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "person_sm",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "sm_id")
    )
    private List<Person> people;

    public SocialMedia(String name, List<Person> people) {
        this.name = name;
        this.people = people;
    }

    public SocialMedia(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void createSocialMedia(SocialMedia socialMedia) {

        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(socialMedia);
            session.getTransaction().commit();
            System.out.println("SM with name successfylly ");
        }

    }

    public static void updateSM(SocialMedia sm) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            SocialMedia sm1 = session.get(SocialMedia.class, sm.getId());
            session.merge(sm);
            session.getTransaction().commit();
            System.out.println("SM with id: " + sm.getId() + " was successfully updated");
        }
    }

    public static void deleteSmById(Long smId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            SocialMedia sm = session.get(SocialMedia.class, smId);
            session.remove(sm);
            session.getTransaction().commit();
            System.out.println("SM id: " + smId + " was successfully deleted");
        }
    }

    public static List<SocialMedia> getAllSm() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<SocialMedia> smList = session.createQuery("select c from SocialMedia c").getResultList();
            session.getTransaction().commit();
            System.out.println("Found " + smList.size() + " SM");
            return smList.stream().sorted(Comparator.comparing(SocialMedia::getName)).toList();
        }
    }

}
