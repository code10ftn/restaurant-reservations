package com.code10.isa.service;

import com.code10.isa.model.Reservation;
import com.code10.isa.model.ReservationGuest;
import com.code10.isa.model.user.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailSender mailSender;

    @Autowired
    public MailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendVerificationMail(String url, String confirmationCode, String emailAddress) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Verify Account");
        message.setFrom("Email address hidden in public repository."); // Email address hidden in public repository.
        message.setTo(emailAddress);
        message.setText(url + "/" + confirmationCode);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            System.out.println(e);
        }
    }

    @Async
    public void sendReservationMail(String url, Guest host, ReservationGuest reservationGuest, String emailAddress) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Restaurant Reservation Invitation");
        message.setFrom("Email address hidden in public repository."); // Email address hidden in public repository.
        message.setTo(emailAddress);

        final Reservation reservation = reservationGuest.getReservation();
        final StringBuilder stringBuilder = new StringBuilder(host.getFirstName())
                .append(" ")
                .append(host.getLastName())
                .append(" invited you to ")
                .append(reservation.getRestaurant().getName())
                .append(" at ")
                .append(reservation.getDate())
                .append(". Follow the link to accept the reservation:\n")
                .append(url).append("/accept/").append(reservationGuest.getInvitationCode());
        message.setText(stringBuilder.toString());

        try {
            mailSender.send(message);
        } catch (MailException e) {
            System.out.println(e);
        }
    }
}
