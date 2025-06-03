package com.voting.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * description
 *
 * @author: zhic.tan
 * @date 2025/5/29 上午12:45
 */

@Entity
@Data
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

    private String voterIdentifier;

    private LocalDateTime createdAt = LocalDateTime.now();
}