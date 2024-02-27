package com.example.learntocode.services;

import com.example.learntocode.mapper.TagMapper;
import com.example.learntocode.models.Tag;
import com.example.learntocode.payload.DTOs.TagDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.learntocode.util.RepositoryUtil.findById;


@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public ResponseEntity<MessageResponse> createTag(TagDto data) {
        //todo: validate the data

        Tag tag = tagMapper.toEntity(data);
        tagRepository.save(tag);

        return ResponseEntity.ok(MessageResponse.from("Tag has been created successfully."));
    }

    public ResponseEntity<TagDto> getTagById(Long tagId) {
        Tag tag = findById(tagRepository, tagId);
        TagDto tagDto = tagMapper.toDto(tag);

        return ResponseEntity.ok(tagDto);
    }

    public ResponseEntity<List<TagDto>> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> tagDtos = tagMapper.toDto(tags);

        return ResponseEntity.ok(tagDtos);
    }

    public ResponseEntity<MessageResponse> updateTagName(Long id, String name) {
        //todo: validate the data

        Tag tag = findById(tagRepository, id);

        tag.setName(name);
        tagRepository.save(tag);

        return ResponseEntity.ok(MessageResponse.from("Tag name has been updated successfully."));
    }

    public ResponseEntity<MessageResponse> deleteTag(Long id) {
        tagRepository.deleteById(id);

        return ResponseEntity.ok(MessageResponse.from("Tag has been deleted successfully."));
    }
}
