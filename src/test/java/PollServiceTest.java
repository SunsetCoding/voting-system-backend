import com.voting.model.*;
import com.voting.repository.*;
import com.voting.service.PollService;
import com.voting.websocket.PollWebSocketHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PollServiceTest {

    @Mock
    private PollRepository pollRepository;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private PollWebSocketHandler webSocketHandler;

    private PollService pollService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pollService = new PollService(pollRepository, optionRepository,
                voteRepository, webSocketHandler);
    }

    @Test
    void testGetCurrentPoll() {
        Poll poll = new Poll();
        poll.setId(1L);
        poll.setQuestion("Test Question");

        when(pollRepository.findById(1L)).thenReturn(Optional.of(poll));

        Poll result = pollService.getCurrentPoll();

        assertEquals(1L, result.getId());
        assertEquals("Test Question", result.getQuestion());
    }

    @Test
    void testVoteSuccess() {
        String voterIdentifier = "test_voter";
        Long optionId = 1L;

        Option option = new Option();
        option.setId(optionId);
        option.setVoteCount(0);

        Poll poll = new Poll();
        poll.setId(1L);
        poll.setOptions(Arrays.asList(option));

        when(voteRepository.existsByVoterIdentifier(voterIdentifier)).thenReturn(false);
        when(optionRepository.findById(optionId)).thenReturn(Optional.of(option));
        when(pollRepository.findById(1L)).thenReturn(Optional.of(poll));

        Poll result = pollService.vote(optionId, voterIdentifier);

        assertEquals(1, option.getVoteCount());
        verify(optionRepository).save(option);
        verify(voteRepository).save(any(Vote.class));
        verify(webSocketHandler).broadcastUpdate(poll);
    }

    @Test
    void testVoteAlreadyVoted() {
        String voterIdentifier = "test_voter";
        Long optionId = 1L;

        when(voteRepository.existsByVoterIdentifier(voterIdentifier)).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> pollService.vote(optionId, voterIdentifier));
    }
}