package com.example.learntocode.services;

import com.example.learntocode.mapper.QuestionMapper;
import com.example.learntocode.models.Question;
import com.example.learntocode.models.Tag;
import com.example.learntocode.payload.DTOs.QuestionDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.QuestionRepository;
import com.example.learntocode.repository.TagRepository;
import com.example.learntocode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    //todo: there is a bug here, the tags are not being fetched

    public ResponseEntity<QuestionDto> getQuestionById(Long id) {
        var question = questionRepository.findByIdWithTags(id).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        var tags = question.getTags();


        System.out.println("Tags: " + tags);

        QuestionDto questionDto = questionMapper.toDto(question);

        return ResponseEntity.ok(questionDto);
    }

    //todo: there is a bug here, the tags are not being fetched
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


