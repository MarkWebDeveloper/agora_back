package de.stella.agora_web.replys.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.replys.controller.dto.ReplyDTO;
import de.stella.agora_web.replys.model.Reply;
import de.stella.agora_web.replys.services.IReplyService;
import io.micrometer.common.lang.NonNull;

@RestController
@RequestMapping(path = "${api-endpoint}/replies")
public class ReplysController {

    private final IReplyService replyService;

    public ReplysController(IReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER','ADMIN')")
    public ResponseEntity<Reply> createReply(@SuppressWarnings("rawtypes") @RequestBody ReplyDTO replyDTO) {
        Reply reply = replyService.createReply(replyDTO, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reply> show(@NonNull @PathVariable Long id) {
        Reply reply = replyService.getReplyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @PostMapping("/store")
    public ResponseEntity<Reply> store(@SuppressWarnings("rawtypes") @RequestBody ReplyDTO replyDTO) {
        Reply reply = replyService.save(replyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        replyService.deleteReply(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Reply> update(@PathVariable Long id, @SuppressWarnings("rawtypes") @RequestBody ReplyDTO replyDTO) {
        Reply reply = replyService.updateReply(id, replyDTO);
        return ResponseEntity.accepted().body(reply);
    }

}
