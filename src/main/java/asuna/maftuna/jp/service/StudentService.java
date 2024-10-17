package asuna.maftuna.jp.service;

import asuna.maftuna.jp.dto.FilterStudentDTO;
import asuna.maftuna.jp.dto.StudentDTO;
import asuna.maftuna.jp.entity.StudentEntity;
import asuna.maftuna.jp.repository.CustomStudentRepository;
import asuna.maftuna.jp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CustomStudentRepository customStudentRepository;

    public StudentDTO create(StudentDTO dto) {
        StudentEntity student = new StudentEntity();
        student.setAge(dto.getAge());
        student.setName(dto.getName());
        student.setSurname(dto.getSurname());
        student.setCreatedDate(LocalDateTime.now());

        studentRepository.save(student);

        dto.setId(student.getId());
        dto.setCreatedDate(student.getCreatedDate());
        return dto;
    }

    public StudentDTO update(StudentDTO dto) {
        StudentEntity student = studentRepository.getById(dto.getId());
        System.out.println(student);
        student.setName(dto.getName());
        student.setSurname(dto.getSurname());
        student.setLevel(dto.getLevel());
        student.setAge(dto.getAge());
        student.setGender(dto.getGender());
        studentRepository.save(student);

        dto.setId(student.getId());
        return dto;
    }

    public Boolean delete(Integer id) {
        studentRepository.delete(studentRepository.getById(id));
        return true;
    }


    public List<StudentDTO> getAll() {
        Iterable<StudentEntity> studentEntities = studentRepository.findAll();
        return mapEntitiesToDTOs(studentEntities);
    }

    public StudentDTO getById(int id) {
        return mapToDTO(studentRepository.getById(id));
    }

    public List<StudentDTO> getByName(String name) {
        return mapEntitiesToDTOs(studentRepository.findByName(name));
    }

    public List<StudentDTO> getBySurname(String surname) {
        return mapEntitiesToDTOs(studentRepository.findBySurname(surname));
    }

    public List<StudentDTO> getByLevel(String level) {
        return mapEntitiesToDTOs(studentRepository.findByLevel(level));
    }

    public List<StudentDTO> getByAge(int age) {
        return mapEntitiesToDTOs(studentRepository.findByAge(age));
    }

    public List<StudentDTO> getByGender(String gender) {
        return mapEntitiesToDTOs(studentRepository.findByGender(gender));
    }

    public List<StudentDTO> getAllByGivenDate(LocalDate localDate) {
        LocalDateTime fromDate = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(localDate, LocalTime.MAX);
        List<StudentEntity> entityList = studentRepository.findByCreatedDateBetween(fromDate, toDate);

        return mapEntitiesToDTOs(entityList);
    }

    public List<StudentDTO> getStudentsByCreatedDate(LocalDateTime createdDate) {
        return mapEntitiesToDTOs(studentRepository.findByCreatedDate(createdDate.toLocalDate()));
    }

    public List<StudentDTO> getStudentsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return mapEntitiesToDTOs(studentRepository.findByCreatedDateBetween(startDate, endDate));
    }

    public Page<StudentDTO> getStudentsPage(Pageable pageable) {
        // Fetch page of StudentEntity from the repository
        Page<StudentEntity> page = studentRepository.findAll(pageable);

        // Convert the list of StudentEntity to StudentDTO
        List<StudentDTO> dtoList = page.stream()
                .map(this::mapToDTO) // Use the mapping function here
                .collect(Collectors.toList());

        // Return a new PageImpl containing the list of StudentDTO and the original Pageable
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    public Page<StudentDTO> getStudentsByLevel(String level, Pageable pageable) {
        Page<StudentEntity> page = studentRepository.findByLevel(level, pageable);

        List<StudentDTO> dtoList = page.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    public Page<StudentDTO> getStudentsByGenderSortedByCreatedDate(String gender, Pageable pageable) {
        Page<StudentEntity> page = studentRepository.findByGender(gender, pageable);

        List<StudentDTO> dtoList = page.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    private List<StudentDTO> mapEntitiesToDTOs(Iterable<StudentEntity> students) {
        List<StudentDTO> studentDTOList = new LinkedList<>();
        for (StudentEntity se : students) {
            StudentDTO dto = new StudentDTO();
            dto.setId(se.getId());
            dto.setName(se.getName());
            dto.setSurname(se.getSurname());
            dto.setLevel(se.getLevel());
            dto.setAge(se.getAge());
            dto.setGender(se.getGender());

            studentDTOList.add(dto);
        }
        return studentDTOList;
    }

    private StudentDTO mapToDTO(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setLevel(entity.getLevel());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<StudentDTO> filter(FilterStudentDTO filter, int page, int size) {
        Page<StudentEntity> result = customStudentRepository.filter(filter, page, size);

        long totalCount = result.getTotalElements();
        List<StudentEntity> entityList = result.getContent();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            StudentDTO dto = new StudentDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setAge(entity.getAge());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return new PageImpl<StudentDTO>(dtoList, pageRequest, totalCount);
    }
}


