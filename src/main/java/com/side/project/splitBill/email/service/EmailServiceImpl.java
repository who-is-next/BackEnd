package com.side.project.splitBill.email.service;

import com.side.project.splitBill.email.dto.EmailAthenticationDTO;
import com.side.project.splitBill.utils.Utils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender sender;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;


    @Override
    public boolean emailAuthenticationSend(EmailAthenticationDTO dto) {

        Integer authenticationNumber = Utils.createAuthenticationNumber();
        dto.setAthenticationNumber(authenticationNumber);

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("cksgh1565@naver.com");
            message.setTo(dto.getUserEmail());
            message.setSubject("[SplitBill] 이메일 인증을 완료해주세요");
            message.setText("테스트 인증번호 : " + authenticationNumber);

            // TODO : db에 인증번호 저장 로직 추가 필요
            valueOperations.set(dto.getUserEmail(), authenticationNumber.toString());

            sender.send(message);
        } catch (MailException e){
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
