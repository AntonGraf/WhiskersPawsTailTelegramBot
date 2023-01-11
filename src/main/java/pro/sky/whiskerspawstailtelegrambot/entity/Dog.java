package pro.sky.whiskerspawstailtelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;


/**
 * Собака. В база данных dog .
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    /**
     * Кличка собаки
     */
    @Column(name = "name")
    String fullName;

    /**
     * При создании таблицы будет установлено ограничение: age > 0
     */
    String age;


    /**
     * Описание собаки
     */
    String description;


    /**
     * Путь по которому будеть храниться фото
     */
    @Column(name = "file_path")
    @JsonIgnore
    String filePath;

    /**
     * Указывается размер файлов
     */
    @Column(name = "size")
    @JsonIgnore
    Long fileSize;

    /**
     * Медиатип
     */
    @Column(name = "type")
    @JsonIgnore
    String mediaType;

    /**
     * Фото
     */
    @Lob
    @Column(name = "photo")
    @JsonIgnore
    byte[] photo;


    /**
     * Приют, к которому принадлежит собака
     */
//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shelter_id")
    Shelter shelter;

    /**
     * Хозяин собаки
     */
//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "adoptive_parent_id")
    AdoptiveParent adoptiveParent;


    /**
     * Отчеты хозяина для данной собаки
     */
    @OneToMany(mappedBy = "dog", fetch=FetchType.EAGER)
//    @JsonManagedReference // Мешает заполнению через сваггер
    @JsonIgnore
    List<Report> reports;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Dog dog = (Dog) o;
        return id != null && Objects.equals(id, dog.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
