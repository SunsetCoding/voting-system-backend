package com.voting.service;

import com.voting.model.*;
import com.voting.repository.*;
import com.voting.websocket.PollWebSocketHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * description
 *
 * @author: zhic.tan
 * @date 2025/5/29 上午12:45
 */

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final OptionRepository optionRepository;
    private final VoteRepository voteRepository;
    private final PollWebSocketHandler webSocketHandler;

    public PollService(PollRepository pollRepository,
                       OptionRepository optionRepository,
                       VoteRepository voteRepository,
                       PollWebSocketHandler webSocketHandler) {
        this.pollRepository = pollRepository;
        this.optionRepository = optionRepository;
        this.voteRepository = voteRepository;
        this.webSocketHandler = webSocketHandler;
    }

    public Poll getCurrentPoll() {
        return pollRepository.findById(1L).orElseThrow();
    }

    @Transactional
    public Poll vote(Long optionId, String voterIdentifier) {
        if (voteRepository.existsByVoterIdentifier(voterIdentifier)) {
            throw new RuntimeException("已经投过票了");
        }

        Option option = optionRepository.findById(optionId).orElseThrow();
        option.setVoteCount(option.getVoteCount() + 1);
        optionRepository.save(option);

        Vote vote = new Vote();
        vote.setOption(option);
        vote.setVoterIdentifier(voterIdentifier);
        voteRepository.save(vote);

        Poll poll = getCurrentPoll();
        webSocketHandler.broadcastUpdate(poll);

        return poll;
    }
}
