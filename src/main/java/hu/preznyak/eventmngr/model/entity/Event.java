package hu.preznyak.eventmngr.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "EVENTS")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Integer id;
    @Column(name = "TITLE")
    String title;
    @Column(name = "DESCRIPTION")
    String description;
    @Column(name = "PRICE")
    Integer price;
    @Column(name = "DEPOSIT")
    Integer deposit;
    @Column(name = "CONTACT")
    String contact;
    @Column(name = "LOCATION")
    String location;

    public Event(String title, String description, Integer price, Integer deposit) {
        super();
        this.title = title;
        this.description = description;
        this.price = price;
        this.deposit = deposit;
    }

    public Event(String title, String description, Integer price, Integer deposit, String contact, String location) {
        super();
        this.title = title;
        this.description = description;
        this.price = price;
        this.deposit = deposit;
        this.contact = contact;
        this.location = location;
    }

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", deposit=" + deposit +
                '}';
    }
}
