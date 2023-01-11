package pro.sky.whiskerspawstailtelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


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
    long fileSize;

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



}
