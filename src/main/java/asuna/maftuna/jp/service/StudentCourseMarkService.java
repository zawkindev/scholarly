package asuna.maftuna.jp.service;

import asuna.maftuna.jp.dto.CourseDTO;
import asuna.maftuna.jp.dto.FilterCourseDTO;
import asuna.maftuna.jp.dto.FilterStudentCourseMarkDTO;
import asuna.maftuna.jp.dto.StudentCourseMarkDTO;
import asuna.maftuna.jp.entity.CourseEntity;
import asuna.maftuna.jp.entity.StudentCourseMarkEntity;
import asuna.maftuna.jp.exception.WaifuException;
import asuna.maftuna.jp.repository.CustomStudentCourseMarkRepository;
import asuna.maftuna.jp.repository.StudentCourseMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentCourseMarkService {
    @Autowired
    private StudentCourseMarkRepository studentCourseMarkRepository;
    @Autowired
    private CustomStudentCourseMarkRepository customStudentCourseMarkRepository;

    public StudentCourseMarkDTO create(StudentCourseMarkDTO dto) {
        StudentCourseMarkEntity entity = new StudentCourseMarkEntity();
        dto.setCreatedDate(LocalDateTime.now());
        entity.setStudentId(dto.getStudentId());
        entity.setCourseId(dto.getCourseId());
        entity.setMark(dto.getMark());
        entity.setCreatedDate(dto.getCreatedDate());

        studentCourseMarkRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

    public StudentCourseMarkDTO update(StudentCourseMarkDTO dto) {
        StudentCourseMarkEntity entity = studentCourseMarkRepository.getById(dto.getId());
        entity.setStudentId(dto.getStudentId());
        entity.setCourseId(dto.getCourseId());
        entity.setMark(dto.getMark());
        entity.setCreatedDate(dto.getCreatedDate());

        studentCourseMarkRepository.save(entity);
        return dto;
    }

    public StudentCourseMarkDTO getById(Integer id) {
        StudentCourseMarkEntity entity = studentCourseMarkRepository.getById(id);
        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();

        dto.setStudentId(entity.getStudentId());
        dto.setCourseId(entity.getCourseId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<StudentCourseMarkDTO> getAll() {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findAll();
        List<StudentCourseMarkDTO> dtoList = new ArrayList<>();

        for (StudentCourseMarkEntity entity : entityList) {
            StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
            dto.setId(entity.getId());
            dto.setStudentId(entity.getStudentId());
            dto.setCourseId(entity.getCourseId());
            dto.setMark(entity.getMark());
            dto.setCreatedDate(entity.getCreatedDate());
        }
        return dtoList;
    }

    public StudentCourseMarkDTO getByIdWithDetail(Integer id) {
        StudentCourseMarkDTO dto = getById(id);
        dto.setCourse(dto.getCourse());
        dto.setStudent(dto.getStudent());

        return dto;
    }

    public Boolean delete(Integer id) {
        if (studentCourseMarkRepository.existsById(id)) {
            studentCourseMarkRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<StudentCourseMarkDTO> getMarksByStudentOnDate(Integer studentId, LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);

        return studentCourseMarkRepository.findMarksByStudentOnDate(studentId, from, to).stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<StudentCourseMarkDTO> getMarksByStudentBetweenDates(Integer studentId, LocalDateTime start, LocalDateTime end) {
        return studentCourseMarkRepository.findMarksByStudentBetweenDates(studentId, start, end).stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<StudentCourseMarkDTO> getAllMarksSortedByDate(Integer studentId) {
        return studentCourseMarkRepository.findAllMarksByStudentSortedByDate(studentId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<StudentCourseMarkDTO> getMarksByStudentForCourseSortedByDate(Integer studentId, Integer courseId) {
        return studentCourseMarkRepository.findMarksByStudentForCourseSortedByDate(studentId, courseId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    public StudentCourseMarkDTO getLastMarkForStudent(Integer studentId) {
        return studentCourseMarkRepository.findFirstByStudentIdOrderByCreatedDateDesc(studentId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new WaifuException("No marks found for the student."));
    }

    public List<Integer> getTopThreeMarksForStudent(Integer studentId) {
        return studentCourseMarkRepository.findTop3ByStudentIdOrderByMarkDesc(studentId).stream()
                .map(StudentCourseMarkEntity::getMark)
                .collect(Collectors.toList());
    }

    public Integer getFirstMarkForStudent(Integer studentId) {
        return studentCourseMarkRepository.findFirstByStudentIdOrderByCreatedDateAsc(studentId)
                .map(StudentCourseMarkEntity::getMark)
                .orElseThrow(() -> new WaifuException("No marks found for the student."));
    }

    public Integer getFirstMarkForStudentInCourse(Integer studentId, Integer courseId) {
        return studentCourseMarkRepository.findFirstByStudentIdAndCourseIdOrderByCreatedDateAsc(studentId, courseId)
                .map(StudentCourseMarkEntity::getMark)
                .orElseThrow(() -> new WaifuException("No marks found for the student in the course."));
    }

    public Integer getHighestMarkForStudentInCourse(Integer studentId, Integer courseId) {
        return studentCourseMarkRepository.findTopByStudentIdAndCourseIdOrderByMarkDesc(studentId, courseId)
                .map(StudentCourseMarkEntity::getMark)
                .orElseThrow(() -> new WaifuException("No marks found for the student in the course."));
    }

    public Double getAverageMarksForStudent(Integer studentId) {
        return Optional.ofNullable(studentCourseMarkRepository.findAverageMarkByStudentId(studentId))
                .orElseThrow(() -> new WaifuException("No marks found for the student."));
    }

    public Double getAverageMarksForStudentInCourse(Integer studentId, Integer courseId) {
        return Optional.ofNullable(studentCourseMarkRepository.findAverageMarkByStudentIdAndCourseId(studentId, courseId))
                .orElseThrow(() -> new WaifuException("No marks found for the student in the course."));
    }

    public Long getCountOfMarksGreaterThan(Integer studentId, Double mark) {
        return studentCourseMarkRepository.countByStudentIdAndMarkGreaterThan(studentId, mark);
    }

    public Integer getHighestMarkForCourse(Integer courseId) {
        return studentCourseMarkRepository.findTopByCourseIdOrderByMarkDesc(courseId)
                .map(StudentCourseMarkEntity::getMark)
                .orElseThrow(() -> new WaifuException("No marks found for the course."));
    }

    public Long getCountOfMarksForCourse(Integer courseId) {
        return studentCourseMarkRepository.countByCourseId(courseId);
    }

    public Page<StudentCourseMarkDTO> getStudentCourseMarksPage(Pageable pageable) {
        Page<StudentCourseMarkEntity> page = studentCourseMarkRepository.findAll(pageable);
        return new PageImpl<>(page.stream().map(this::mapToDTO).toList(), pageable, page.getTotalElements());
    }

    public Page<StudentCourseMarkDTO> getStudentCourseMarksByStudentIdPage(Integer studentId, Pageable pageable) {
        Page<StudentCourseMarkEntity> page = studentCourseMarkRepository.findAllByStudentId(studentId, pageable);
        return new PageImpl<>(page.stream().map(this::mapToDTO).toList(), pageable, page.getTotalElements());
    }

    public Page<StudentCourseMarkDTO> getStudentCourseMarksByCourseIdPage(Integer courseId, Pageable pageable) {
        Page<StudentCourseMarkEntity> page = studentCourseMarkRepository.findAllByCourseId(courseId, pageable);
        return new PageImpl<>(page.stream().map(this::mapToDTO).toList(), pageable, page.getTotalElements());
    }

    private StudentCourseMarkEntity mapToEntity(StudentCourseMarkDTO dto) {
        StudentCourseMarkEntity entity = new StudentCourseMarkEntity();
        entity.setId(dto.getId());
        entity.setStudentId(dto.getStudentId());
        entity.setCourseId(dto.getCourseId());
        entity.setMark(dto.getMark());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }

    private StudentCourseMarkDTO mapToDTO(StudentCourseMarkEntity entity) {
        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudentId());
        dto.setCourseId(entity.getCourseId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<StudentCourseMarkDTO> filter(FilterStudentCourseMarkDTO filter, int page, int size) {
        Page<StudentCourseMarkEntity> result = customStudentCourseMarkRepository.filter(filter, page, size);

        long totalCount = result.getTotalElements();
        List<StudentCourseMarkEntity> entityList = result.getContent();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity entity : entityList) {
            StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
            dto.setId(entity.getId());
            dto.setStudentId(entity.getStudentId());
            dto.setCourseId(entity.getCourseId());
            dto.setMark(entity.getMark());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return new PageImpl<StudentCourseMarkDTO>(dtoList, pageRequest, totalCount);
    }
}
