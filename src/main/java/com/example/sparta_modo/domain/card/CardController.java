package com.example.sparta_modo.domain.card;

import com.example.sparta_modo.domain.card.dto.CardCreateDto;
import com.example.sparta_modo.domain.card.dto.CardFindDto;
import com.example.sparta_modo.domain.card.dto.CardUpdateDto;
import com.example.sparta_modo.global.dto.MsgDto;
import com.example.sparta_modo.global.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    // 카드 생성
    @PostMapping
    public ResponseEntity<CardCreateDto> createCard(
            @RequestBody CardCreateDto requestDto,
            @AuthenticationPrincipal User loginUser){

        CardCreateDto createDto = cardService.createCard(loginUser, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createDto);
    }

    // 카드 수정
    @PatchMapping("/{cardId}")
    public ResponseEntity<CardUpdateDto.Response> updateCard(
            @PathVariable Long cardId,
            @RequestBody CardUpdateDto.Request requestDto,
            @AuthenticationPrincipal User loginUser){

        CardUpdateDto.Response updateDto = cardService.updateCard(loginUser, cardId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // 카드 조회
    @GetMapping("/{cardId}")
    public ResponseEntity<CardFindDto> findCard(@PathVariable Long cardId){

        CardFindDto findDto = cardService.findCard(cardId);

        return ResponseEntity.status(HttpStatus.OK).body(findDto);
    }

    // 카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<MsgDto> deleteCard(@PathVariable Long cardId,
        @AuthenticationPrincipal User loginUser) {

        MsgDto msgDto = cardService.deleteCard(cardId, loginUser);

        return ResponseEntity.status(HttpStatus.OK).body(msgDto);
    }
}
