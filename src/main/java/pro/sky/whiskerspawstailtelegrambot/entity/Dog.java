package pro.sky.whiskerspawstailtelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;


/**
 * Собака. В база данных dog .
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    int age;


    /**
     * Описание собаки
     */
    String description;


    /**
     * Путь по которому будеть храниться фото
     */
    @Column(name = "file_path")
    String filePath;

    /**
     * Указывается размер файлов
     */
    @Column(name = "size")
    long fileSize;

    /**
     * Медиатип
     */
    @Column(name = "type")
    String mediaType;

    /**
     * Фото
     */
    @Lob
    @Column(name = "photo")
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dog dog = (Dog) o;
        return age == dog.age && Objects.equal(id, dog.id)
            && Objects.equal(fullName, dog.fullName)
            && Objects.equal(description, dog.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, fullName, age, description);
    }
}
