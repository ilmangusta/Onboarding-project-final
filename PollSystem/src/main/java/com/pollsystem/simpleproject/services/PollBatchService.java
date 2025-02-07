package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.repositories.WinnerOptionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.domain.PollWinnerMessage;
import com.pollsystem.simpleproject.domain.WinnerOption;
import com.pollsystem.simpleproject.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;


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
    @Autowired
    private WinnerOptionRepository winnerOptionRepository;


    @Scheduled(cron = "59 59 23 * * ?") // Ogni giorno a mezzanotte
    public void processExpiredPolls() {
        System.out.println("Partito scheduler per segnalare expired i poll e inviare mail...");
        List<Poll> expiringPolls = pollRepository.findExpiringPollsToday();
        System.out.println("poll trovati: " + expiringPolls);
        for (Poll poll : expiringPolls) {
            this.processPoll(poll);
        }
    }

    //consente di gestire le transazioni in modo dichiarativo, senza dover scrivere codice esplicito per iniziare, commettere o annullare una transazione.
    @Transactional
    private void processPoll(Poll poll) {
        //setta expired, calcola la % vittoria e triggera invio messaggio
        WinnerOption winnerOption = new WinnerOption(poll.getId(),pollService.GetWinningOption(poll.getId()) , pollService.GetPercentWinner(poll.getId()));
        winnerOptionRepository.save(winnerOption);
        poll.setStatus("EXPIRED");
        pollRepository.save(poll);
        sendPollWinnerMessage(poll, winnerOption);
    }

    private void sendPollWinnerMessage(Poll poll, WinnerOption winnerOption) {
        //costruisce il messaggio ed invia
        System.out.println(winnerOption);
        if (winnerOption == null) return;
        PollWinnerMessage message = new PollWinnerMessage(
                poll.getQuestion(),
                optionService.findById(winnerOption.getOptionId()).getMessage(),
                winnerOption.getPercentOfWinner(),
                poll.getExpiresAt(),
                userService.GetUserbyUsername(poll.getOwner()).getEmail()
        );
        rabbitMQProducer.sendMessage("poll.winner.mail", message.toString());
    }










}
