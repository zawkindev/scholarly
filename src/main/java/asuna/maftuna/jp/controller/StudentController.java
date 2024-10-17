package asuna.maftuna.jp.controller;

import asuna.maftuna.jp.dto.FilterStudentDTO;
import asuna.maftuna.jp.dto.StudentDTO;
import asuna.maftuna.jp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping({"", "/"})
    private ResponseEntity<StudentDTO> create(@RequestBody StudentDTO dto) {
        return ResponseEntity.ok(studentService.create(dto));
    }

    @PutMapping({"", "/"})
    private ResponseEntity<StudentDTO> update(@RequestBody StudentDTO dto) {
        return ResponseEntity.ok(studentService.update(dto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.delete(id));
    }

    @GetMapping("")
    private ResponseEntity<List<StudentDTO>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<StudentDTO> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping("/name/{name}")
    private ResponseEntity<List<StudentDTO>> getByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(studentService.getByName(name));
    }

    @GetMapping("/surname/{surname}")
    private ResponseEntity<List<StudentDTO>> getBySurname(@PathVariable("surname") String surname) {
        return ResponseEntity.ok(studentService.getBySurname(surname));
    }

    @GetMapping("/level/{level}")
    private ResponseEntity<List<StudentDTO>> getByLevel(@PathVariable("level") String level) {
        return ResponseEntity.ok(studentService.getByLevel(level));
    }

    @GetMapping("/age/{age}")
    private ResponseEntity<List<StudentDTO>> getByAge(@PathVariable("age") int age) {
        return ResponseEntity.ok(studentService.getByAge(age));
    }

    @GetMapping("/gender/{gender}")
    private ResponseEntity<List<StudentDTO>> getByGender(@PathVariable("gender") String gender) {
        return ResponseEntity.ok(studentService.getByGender(gender));
    }

    @GetMapping("/createdDate/{date}")
    public ResponseEntity<List<StudentDTO>> getByCreatedDate(@PathVariable String date) {
        return ResponseEntity.ok(studentService.getStudentsByCreatedDate(LocalDateTime.parse(date)));
    }

    @GetMapping("/createdDate/between/{startDate}/{endDate}")
    public ResponseEntity<List<StudentDTO>> getByDates(@PathVariable String startDate, @PathVariable String endDate) {
        return ResponseEntity.ok(studentService.getStudentsBetweenDates(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate)));
    }

    @GetMapping("/")
    public ResponseEntity<Page<StudentDTO>> getStudentsPage(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(studentService.getStudentsPage(PageRequest.of(page, size)));
    }

    @GetMapping("/level/")
    public ResponseEntity<Page<StudentDTO>> getStudentsByLevel(@RequestParam String level, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(studentService.getStudentsByLevel(level, PageRequest.of(page, size)));
    }

    @GetMapping("/")
    public ResponseEntity<Page<StudentDTO>> getStudentsByGender(@RequestParam String gender, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(studentService.getStudentsByGenderSortedByCreatedDate(gender, PageRequest.of(page, size)));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<StudentDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "30") int size, @RequestBody FilterStudentDTO filter) {
        PageImpl<StudentDTO> result = studentService.filter(filter, page - 1, size);
        return ResponseEntity.ok(result);
    }

}
