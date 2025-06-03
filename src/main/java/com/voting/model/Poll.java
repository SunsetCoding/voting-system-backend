package com.voting.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/**
 * description
 *
 * @author: zhic.tan
 * @date 2025/5/29 上午12:44
 */
@Entity
@Data
@Table(name = "polls")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Option> options;
}
