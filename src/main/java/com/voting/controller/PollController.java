package com.voting.controller;

import com.voting.model.Poll;
import com.voting.service.PollService;
import org.springframework.web.bind.annotation.*;

/**
 * description
 *
 * @author: zhic.tan
 * @date 2025/5/29 上午12:44
 */

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/poll")
    public Poll getPoll() {
        return pollService.getCurrentPoll();
    }

    @PostMapping("/poll/vote")
    public Poll vote(@RequestBody VoteRequest request) {
        return pollService.vote(request.getOptionId(), request.getVoterIdentifier());
    }

    static class VoteRequest {
        private Long optionId;
        private String voterIdentifier;
        // getters and setters
        public Long getOptionId() { return optionId; }
        public void setOptionId(Long optionId) { this.optionId = optionId; }
        public String getVoterIdentifier() { return voterIdentifier; }
        public void setVoterIdentifier(String voterIdentifier) { this.voterIdentifier = voterIdentifier; }
    }
}