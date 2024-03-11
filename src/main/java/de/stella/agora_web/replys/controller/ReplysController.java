package de.stella.agora_web.replys.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.replys.controller.dto.ReplyDTO;
import de.stella.agora_web.replys.services.IReplyService;


@RestController
@RequestMapping(path = "${api-endpoint}/replies") 
public class ReplysController {

    @SuppressWarnings("unused")
    private final IReplyService replyService;

    public ReplysController(IReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public void createReply(@SuppressWarnings("rawtypes") @RequestBody ReplyDTO replyDTO) {
        // Implementación del método
    }
}
