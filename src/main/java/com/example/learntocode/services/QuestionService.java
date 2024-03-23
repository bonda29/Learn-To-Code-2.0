package com.example.learntocode.services;

import com.example.learntocode.mapper.QuestionMapper;
import com.example.learntocode.models.Question;
import com.example.learntocode.models.Reply;
import com.example.learntocode.models.Tag;
import com.example.learntocode.payload.DTOs.QuestionDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public ResponseEntity<MessageResponse> createQuestion(QuestionDto data) {
        validateQuestionData(data);

        Question question = questionMapper.toEntity(data);
        questionRepository.save(question);

        return ResponseEntity.ok(MessageResponse.from("The question has been created successfully!"));
    }

    public ResponseEntity<QuestionDto> getQuestionById(Long id) {
        var question = findById(questionRepository, id);
        System.out.println("Tags: " + question.getTags().stream().map(Tag::getId).toList());
        System.out.println("Replies: " + question.getReplies().stream().map(Reply::getId).toList());

        QuestionDto questionDto = questionMapper.toDto(question);

        return ResponseEntity.ok(questionDto);
    }

    public ResponseEntity<List<QuestionDto>> getQuestionsByTagIds(List<Long> tagIds) {
        List<Question> questions = questionRepository.findByTagIds(tagIds).orElse(List.of());

        List<QuestionDto> questionDtos = questionMapper.toDto(questions);

        return ResponseEntity.ok(questionDtos);
    }

    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        List<QuestionDto> questionDtos = questionMapper.toDto(questions);

        return ResponseEntity.ok(questionDtos);
    }

    public ResponseEntity<MessageResponse> updateQuestion(Long id, QuestionDto data) {
        validateQuestionData(data);

        Question question = findById(questionRepository, id);
        question = questionMapper.toEntity(data, question);

        questionRepository.save(question);

        return ResponseEntity.ok(MessageResponse.from("The question has been updated successfully!"));
    }

    public ResponseEntity<MessageResponse> deleteQuestion(Long id) {
        questionRepository.deleteById(id);
        return ResponseEntity.ok(MessageResponse.from("The question has been deleted successfully!"));
    }

    public void validateQuestionData(QuestionDto data) {
        if (data.getTitle() == null || data.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Question title cannot be null or empty");
        }
        if (data.getText() == null || data.getText().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty");
        }

        if (data.getTagIds() == null || data.getTagIds().isEmpty()) {
            throw new IllegalArgumentException("Question tags cannot be null or empty");
        }

        if (data.getAuthorId() == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
    }
}


