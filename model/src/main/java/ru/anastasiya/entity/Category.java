package ru.anastasiya.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Mcc> mccCodes = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "category_detail",
            joinColumns = {@JoinColumn(name = "first_category_id")},
            inverseJoinColumns = {@JoinColumn(name = "second_category_id")}
    )
    private final List<Category> subcategories = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void addSubcategory(Category subcategory) {
        subcategories.add(subcategory);
    }

    public void addMcc(Mcc mcc) {
        mccCodes.add(mcc);
    }
}
