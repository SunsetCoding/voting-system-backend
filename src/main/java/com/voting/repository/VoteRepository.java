package com.voting.repository;

import com.voting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * description
 *
 * @author: zhic.tan
 * @date 2025/5/29 上午12:45
 */

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVoterIdentifier(String voterIdentifier);
}
