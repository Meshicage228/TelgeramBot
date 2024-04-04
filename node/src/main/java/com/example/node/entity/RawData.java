package com.example.node.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.telegram.telegrambots.meta.api.objects.Update;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "raw_table")
public class RawData {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @JdbcTypeCode(SqlTypes.JSON)
    private Update update;
}
