package com.pollsystem.simpleproject.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.domain.PollWinnerMessage;
import com.pollsystem.simpleproject.domain.WinnerOption;
import com.pollsystem.simpleproject.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


import java.util.List;

@Service
public class PollBatchService {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PollService pollService;
    @Autowired
    private UserService userService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private RabbitMQProducer rabbitMQProducer;


    @Scheduled(cron = "0 0 0 * * ?") // Ogni giorno a mezzanotte
    public void processExpiredPolls() {
        List<Poll> expiringPolls = pollRepository.findExpiringPollsToday();

        for (Poll poll : expiringPolls) {
            processPoll(poll);
        }
    }

    private void processPoll(Poll poll) {
        //setta expired, calcola la % vittoria e triggera invio messaggio
        poll.setStatus("EXPIRED");
        WinnerOption winnerOption = new WinnerOption(poll.getId(),pollService.GetWinningOption(poll.getId()) , pollService.GetPercentWinner(poll.getId()));
        pollRepository.save(poll);
        sendPollWinnerMessage(poll, winnerOption);
    }

    private void sendPollWinnerMessage(Poll poll, WinnerOption winnerOption) {
        //costruisce il messaggio ed invia
        if (winnerOption == null) return;
        PollWinnerMessage message = new PollWinnerMessage(
                poll.getQuestion(),
                optionService.findById(winnerOption.getOptionId()).getMessage(),
                winnerOption.getPercentOfWinner(),
                poll.getExpiresAt(),
                userService.GetUserbyUsername(poll.getOwner()).getEmail()
        );
        rabbitMQProducer.sendMessage("poll.winner.mail", message);
    }










}
