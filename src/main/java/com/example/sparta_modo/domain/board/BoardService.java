package com.example.sparta_modo.domain.board;

import com.amazonaws.SdkClientException;
import com.example.sparta_modo.domain.board.dto.BoardDto;
import com.example.sparta_modo.domain.workspace.WorkspaceRepository;
import com.example.sparta_modo.global.entity.Board;
import com.example.sparta_modo.global.entity.BoardImage;
import com.example.sparta_modo.global.entity.Workspace;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.ImageException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import com.example.sparta_modo.global.exception.errorcode.ImageErrorCode;
import com.example.sparta_modo.global.util.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final S3Service s3Service;
    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public BoardDto.ResponseBaseDto createBoard(BoardDto.Request boardDto, Long workspaceId) {
        Workspace findWorkspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "워크스페이스 찾을 수 없음"));

        Board board = new Board(findWorkspace, boardDto);
        Board savedBoard = boardRepository.save(board);

        if (boardDto.getImageActivated() == 1) {
            try {
                String imageUrl = s3Service.uploadImage(boardDto, savedBoard.getId());
                BoardImage boardImage = new BoardImage(savedBoard, imageUrl);
                boardImageRepository.save(boardImage);
                return new BoardDto.ExistImageResponse(savedBoard.getId(), savedBoard.getTitle(), boardImage.getUrl());
            } catch (SdkClientException | IOException e ) {
                throw new ImageException(ImageErrorCode.FAILED_UPLOAD_IMAGE);
            }
        }

        return new BoardDto.generalResponse(savedBoard.getId(), savedBoard.getTitle(), savedBoard.getBackgroundColor());
    }
}
