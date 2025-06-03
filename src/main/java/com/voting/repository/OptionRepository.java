package com.voting.repository;

import com.voting.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * description
 *
 * @author: zhic.tan
 * @date 2025/5/29 上午12:45
 */

public interface OptionRepository extends JpaRepository<Option, Long> {
}
