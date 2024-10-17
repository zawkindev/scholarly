package asuna.maftuna.jp.service;

import asuna.maftuna.jp.dto.CourseDTO;
import asuna.maftuna.jp.dto.FilterCourseDTO;
import asuna.maftuna.jp.dto.FilterStudentDTO;
import asuna.maftuna.jp.dto.StudentDTO;
import asuna.maftuna.jp.entity.CourseEntity;
import asuna.maftuna.jp.entity.StudentEntity;
import asuna.maftuna.jp.repository.CourseRepository;
import asuna.maftuna.jp.repository.CustomCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CustomCourseRepository customCourseRepository;

    public CourseDTO create(CourseDTO dto) {
        CourseEntity course = new CourseEntity();
        course.setName(dto.getName());
        course.setDuration(dto.getDuration());
        course.setPrice(dto.getPrice());

        courseRepository.save(course);
        return dto;
    }

    public CourseDTO update(CourseDTO dto) {
        CourseEntity course = courseRepository.getById(dto.getId());
        course.setName(dto.getName());
        course.setPrice(dto.getPrice());
        course.setDuration(dto.getDuration());
        course.setCreatedDate(dto.getCreatedDate());
        courseRepository.save(course);

        dto.setId(course.getId());
        return dto;
    }

    public Boolean delete(Integer id) {
        courseRepository.delete(courseRepository.getById(id));
        return true;
    }

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> courses = courseRepository.findAll();
        return mapEntitiesToDTOs(courses);
    }

    public CourseDTO getById(Integer id) {
        CourseEntity courseEntity = courseRepository.getById(id);
        CourseDTO dto = new CourseDTO();
        dto.setId(courseEntity.getId());
        dto.setName(courseEntity.getName());
        dto.setDuration(courseEntity.getDuration());
        dto.setPrice(courseEntity.getPrice());
        dto.setCreatedDate(courseEntity.getCreatedDate());

        return dto;
    }

    public List<CourseDTO> getByName(String name) {
        Iterable<CourseEntity> courses = courseRepository.findByName(name);
        return mapEntitiesToDTOs(courses);
    }

    public List<CourseDTO> getByPrice(double price) {
        Iterable<CourseEntity> courses = courseRepository.findByPrice(price);
        return mapEntitiesToDTOs(courses);
    }

    public List<CourseDTO> getByDuration(LocalTime duration) {
        Iterable<CourseEntity> courses = courseRepository.findByDuration(duration);
        return mapEntitiesToDTOs(courses);
    }

    public List<CourseDTO> getByPriceBetween(Double minPrice, Double maxPrice) {
        return mapEntitiesToDTOs(courseRepository.findByPriceBetween(minPrice, maxPrice));
    }

    public List<CourseDTO> getCoursesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return mapEntitiesToDTOs(courseRepository.findByCreatedDateBetween(startDate, endDate));
    }

    public Page<CourseDTO> getCoursesPage(Pageable pageable) {
        Page<CourseEntity> page = courseRepository.findAll(pageable);
        return new PageImpl<>(page.stream().map(this::mapToDTO).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    public Page<CourseDTO> getCoursesSortedByCreatedDate(Pageable pageable) {
        Page<CourseEntity> page = courseRepository.findAllOrderByCreatedDate(pageable);
        return new PageImpl<>(page.stream().map(this::mapToDTO).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    public Page<CourseDTO> getCoursesByPriceSortedByCreatedDate(Double price, Pageable pageable) {
        Page<CourseEntity> page = courseRepository.findByPriceOrderByCreatedDate(price, pageable);
        return new PageImpl<>(page.stream().map(this::mapToDTO).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    public Page<CourseDTO> getCoursesByPriceRangeSortedByCreatedDate(Double minPrice, Double maxPrice, Pageable pageable) {
        Page<CourseEntity> page = courseRepository.findByPriceBetweenOrderByCreatedDate(minPrice, maxPrice, pageable);
        return new PageImpl<>(page.stream().map(this::mapToDTO).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    private CourseDTO mapToDTO(CourseEntity entity) {
        CourseDTO dto = new CourseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private List<CourseDTO> mapEntitiesToDTOs(Iterable<CourseEntity> courses) {
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (CourseEntity ce : courses) {
            CourseDTO dto = new CourseDTO();
            dto.setId(ce.getId());
            dto.setName(ce.getName());
            dto.setDuration(ce.getDuration());
            dto.setCreatedDate(ce.getCreatedDate());
            dto.setPrice(ce.getPrice());

            courseDTOList.add(dto);
        }
        return courseDTOList;
    }

    public PageImpl<CourseDTO> filter(FilterCourseDTO filter, int page, int size) {
        Page<CourseEntity> result = customCourseRepository.filter(filter, page, size);

        long totalCount = result.getTotalElements();
        List<CourseEntity> entityList = result.getContent();

        List<CourseDTO> dtoList = new LinkedList<>();
        for (CourseEntity entity : entityList) {
            CourseDTO dto = new CourseDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDuration(entity.getDuration());
            dto.setPrice(entity.getPrice());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return new PageImpl<CourseDTO>(dtoList, pageRequest, totalCount);
    }
}
