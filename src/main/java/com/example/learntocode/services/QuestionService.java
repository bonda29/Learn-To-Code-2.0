package com.example.learntocode.services;

import com.example.learntocode.mapper.QuestionMapper;
import com.example.learntocode.models.Question;
import com.example.learntocode.payload.DTOs.QuestionDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public ResponseEntity<MessageResponse> createQuestion(QuestionDto data) {
        //todo: validate the data

        Question question = questionMapper.toEntity(data);
        questionRepository.save(question);

        return ResponseEntity.ok(MessageResponse.from("The question has been created successfully!"));
    }

    public ResponseEntity<QuestionDto> getQuestionById(Long id) {
        Question question = findById(questionRepository, id);
        System.out.println("question with id: " + id + " tag: " + question.getTag());
        QuestionDto questionDto = questionMapper.toDto(question);

        return ResponseEntity.ok(questionDto);
    }

    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        //todo: there is a bug here, the tags are not being fetched

        questions.forEach(question -> System.out.println("question with id: " + question.getId() + " tag: " + question.getTag()));

        List<QuestionDto> questionDtos = questionMapper.toDto(questions);
        return ResponseEntity.ok(questionDtos);
    }

    public ResponseEntity<MessageResponse> updateQuestion(Long id, QuestionDto data) {
        Question question = findById(questionRepository, id);
        question = modelMapper.map(data, Question.class);

        questionRepository.save(question);

        return ResponseEntity.ok(MessageResponse.from("The question has been updated successfully!"));
    }

    public ResponseEntity<MessageResponse> deleteQuestion(Long id) {
        questionRepository.deleteById(id);
        return ResponseEntity.ok(MessageResponse.from("The question has been deleted successfully!"));
    }
}