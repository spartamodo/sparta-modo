package com.example.sparta_modo.domain.card;

import com.example.sparta_modo.domain.card.dto.CardCreateDto;
import com.example.sparta_modo.domain.card.dto.CardUpdateDto;
import com.example.sparta_modo.global.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardCreateDto> createCard(
            @RequestBody CardCreateDto requestDto,
            HttpServletRequest httpServletRequest){

        HttpSession session = httpServletRequest.getSession(false);
        User loginUser = (User) session.getAttribute("loginUser");

        CardCreateDto createDto = cardService.createCard(loginUser, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createDto);
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<CardUpdateDto> updateCard(
            @PathVariable Long cardId,
            @RequestBody CardUpdateDto requestDto,
            HttpServletRequest httpServletRequest){

        HttpSession session = httpServletRequest.getSession(false);
        User loginUser = (User) session.getAttribute("loginUser");

        CardUpdateDto updateDto = cardService.updateCard(loginUser, cardId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }
}
