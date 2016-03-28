package com.playtech.librioniq.service.impl;

import com.playtech.librioniq.domain.Answer;
import com.playtech.librioniq.domain.Comment;
import com.playtech.librioniq.domain.Post;
import com.playtech.librioniq.domain.Question;
import com.playtech.librioniq.repository.AnswerRepository;
import com.playtech.librioniq.repository.CommentRepository;
import com.playtech.librioniq.repository.PostRepository;
import com.playtech.librioniq.repository.QuestionRepository;
import com.playtech.librioniq.service.AnswerService;
import com.playtech.librioniq.service.CommentService;
import com.playtech.librioniq.service.QuestionService;
import com.playtech.librioniq.web.rest.dto.AnswerDTO;
import com.playtech.librioniq.web.rest.dto.CommentDTO;
import com.playtech.librioniq.web.rest.dto.QuestionDTO;
import com.playtech.librioniq.web.rest.mapper.AnswerMapper;
import com.playtech.librioniq.web.rest.mapper.CommentMapper;
import com.playtech.librioniq.web.rest.mapper.QuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Post.
 */
@Service
@Transactional
public class PostServiceImpl implements QuestionService, AnswerService, CommentService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    @Inject
    private PostRepository postRepository;

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private QuestionRepository questionRepository;

    @Inject
    private AnswerRepository answerRepository;

    @Inject
    private QuestionMapper questionMapper;

    @Inject
    private CommentMapper commentMapper;

    @Inject
    private AnswerMapper answerMapper;

    @Override
    public AnswerDTO save(AnswerDTO answerDTO) {
        log.debug("Request to save answer: " + answerDTO);
        Answer answer = answerMapper.answerDTOToAnswer(answerDTO);
        answer = answerRepository.save(answer);
        AnswerDTO result = answerMapper.answerToAnswerDTO(answer);
        return result;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        log.debug("Request to save comment: " + commentDTO);
        Comment comment = commentMapper.commentDTOToComment(commentDTO);
        comment = commentRepository.save(comment);
        CommentDTO result = commentMapper.commentToCommentDTO(comment);
        return result;
    }

    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        log.debug("Request to save question: " + questionDTO);
        Question question = questionMapper.questionDTOToQuestion(questionDTO);
        question = questionRepository.save(question);
        QuestionDTO result = questionMapper.questionToQuestionDTO(question);
        return result;
    }

    @Override
    public List<AnswerDTO> findAllAnswersForQuestion(Long questionId) {
        log.debug("Request to fina all answers for question: " + questionId);
        Post parentPost = postRepository.findOne(questionId);
        List<Answer> answers = new LinkedList<>();
        for (Post child : parentPost.getPosts()) {
            Answer answer = answerRepository.findOne(child.getId());
            if (answer != null) {
                answers.add(answer);
            }
        }
        return answers
                .stream()
                .map(answerMapper::answerToAnswerDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<CommentDTO> findAllCommentsForPost(Long postId) {
        log.debug("Request to find all comments for post: " + postId);
        Post parentPost = postRepository.findOne(postId);
        List<Comment> comments = new LinkedList<>();
        for (Post child : parentPost.getPosts()) {
            Comment comment = commentRepository.findOne(child.getId());
            if (comment != null) {
                comments.add(comment);
            }
        }
        return comments
                .stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<QuestionDTO> findAllQuestions() {
        log.debug("Request to find all questions");
        List<QuestionDTO> result = questionRepository
                .findAll()
                .stream()
                .map(questionMapper::questionToQuestionDTO)
                .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete post with id: " + id);
        postRepository.delete(id);
    }

    @Override
    public QuestionDTO findQuestion(Long id) {
        log.debug("Request to find question with id: " + id);
        return questionMapper.questionToQuestionDTO(questionRepository.findOne(id));
    }

    // AUTO GENERATED STUFF

/**
     * Save a post.
     * @return the persisted entity
     *//*

    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.postDTOToPost(postDTO);
        post = postRepository.save(post);
        PostDTO result = postMapper.postToPostDTO(post);
        postSearchRepository.save(post);
        return result;
    }

    */
/**
     *  get all the posts.
     *  @return the list of entities
     *//*

    @Transactional(readOnly = true) 
    public List<PostDTO> findAll() {
        log.debug("Request to get all Posts");
        List<PostDTO> result = postRepository.findAll().stream()
            .map(postMapper::postToPostDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    */
/**
     *  get one post by id.
     *  @return the entity
     *//*

    @Transactional(readOnly = true) 
    public PostDTO findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        Post post = postRepository.findOne(id);
        PostDTO postDTO = postMapper.postToPostDTO(post);
        return postDTO;
    }

    */
/**
     *  delete the  post by id.
     *//*

    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.delete(id);
        postSearchRepository.delete(id);
    }

    */
/**
     * search for the post corresponding
     * to the query.
     *//*

    @Transactional(readOnly = true) 
    public List<PostDTO> search(String query) {
        
        log.debug("REST request to search Posts for query {}", query);
        return StreamSupport
            .stream(postSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(postMapper::postToPostDTO)
            .collect(Collectors.toList());
    }
*/
}
