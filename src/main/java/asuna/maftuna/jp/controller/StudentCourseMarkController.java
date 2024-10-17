package asuna.maftuna.jp.controller;

import asuna.maftuna.jp.dto.FilterStudentCourseMarkDTO;
import asuna.maftuna.jp.dto.FilterStudentDTO;
import asuna.maftuna.jp.dto.StudentCourseMarkDTO;
import asuna.maftuna.jp.dto.StudentDTO;
import asuna.maftuna.jp.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/mark")
public class StudentCourseMarkController {
    @Autowired
    private StudentCourseMarkService studentCourseMarkService;

    @PostMapping({"", "/"})
    public ResponseEntity<StudentCourseMarkDTO> create(@RequestBody StudentCourseMarkDTO dto) {
        return ResponseEntity.ok(studentCourseMarkService.create(dto));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<StudentCourseMarkDTO>> getAll() {
        return ResponseEntity.ok(studentCourseMarkService.getAll());
    }

    @PutMapping({"", "/"})
    public ResponseEntity<StudentCourseMarkDTO> update(@RequestBody StudentCourseMarkDTO dto) {
        return ResponseEntity.ok(studentCourseMarkService.update(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseMarkDTO> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getById(id));
    }

    // 4. Get StudentCourseMark by id with detail
    @GetMapping("/{id}/detail")
    public ResponseEntity<StudentCourseMarkDTO> getByIdWithDetail(@PathVariable Integer id) {
        StudentCourseMarkDTO detailedMark = studentCourseMarkService.getByIdWithDetail(id);
        return ResponseEntity.ok(detailedMark);
    }

    // 5. Delete StudentCourseMark by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        studentCourseMarkService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 7. Get Student's mark on a given date
    @GetMapping("/student/{studentId}/on-date")
    public ResponseEntity<List<StudentCourseMarkDTO>> getMarksByStudentOnDate(@PathVariable Integer studentId, @RequestParam LocalDate date) {
        LocalDateTime fromDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(date, LocalTime.MAX);
        List<StudentCourseMarkDTO> marks = studentCourseMarkService.getMarksByStudentOnDate(studentId, date);
        return ResponseEntity.ok(marks);
    }

    // 8. Get Student's marks between two dates
    @GetMapping("/student/{studentId}/between-dates")
    public ResponseEntity<List<StudentCourseMarkDTO>> getMarksByStudentBetweenDates(@PathVariable Integer studentId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        LocalDateTime fromDate = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(endDate, LocalTime.MAX);
        List<StudentCourseMarkDTO> marks = studentCourseMarkService.getMarksByStudentBetweenDates(studentId, fromDate, toDate);
        return ResponseEntity.ok(marks);
    }

    // 9. Get Student's marks sorted by createdDate (descending)
    @GetMapping("/student/{studentId}/sorted")
    public ResponseEntity<List<StudentCourseMarkDTO>> getMarksSortedByDate(@PathVariable Integer studentId) {
        List<StudentCourseMarkDTO> marks = studentCourseMarkService.getAllMarksSortedByDate(studentId);
        return ResponseEntity.ok(marks);
    }

    // 10. Get Student's marks for a course sorted by createdDate (descending)
    @GetMapping("/student/{studentId}/course/{courseId}/sorted")
    public ResponseEntity<List<StudentCourseMarkDTO>> getMarksByStudentForCourseSortedByDate(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        List<StudentCourseMarkDTO> marks = studentCourseMarkService.getMarksByStudentForCourseSortedByDate(studentId, courseId);
        return ResponseEntity.ok(marks);
    }

    // 11. Get Student's latest mark and course name
    @GetMapping("/student/{studentId}/latest")
    public ResponseEntity<StudentCourseMarkDTO> getLastMarkForStudent(@PathVariable Integer studentId) {
        StudentCourseMarkDTO latestMark = studentCourseMarkService.getLastMarkForStudent(studentId);
        return ResponseEntity.ok(latestMark);
    }

    // 12. Get Student's top 3 highest marks
    @GetMapping("/student/{studentId}/top-3")
    public ResponseEntity<List<Integer>> getTopThreeMarksForStudent(@PathVariable Integer studentId) {
        List<Integer> topMarks = studentCourseMarkService.getTopThreeMarksForStudent(studentId);
        return ResponseEntity.ok(topMarks);
    }

    // 13. Get Student's first mark
    @GetMapping("/student/{studentId}/first")
    public ResponseEntity<Integer> getFirstMarkForStudent(@PathVariable Integer studentId) {
        Integer firstMark = studentCourseMarkService.getFirstMarkForStudent(studentId);
        return ResponseEntity.ok(firstMark);
    }

    // 14. Get Student's first mark in a course
    @GetMapping("/student/{studentId}/course/{courseId}/first")
    public ResponseEntity<Integer> getFirstMarkForStudentInCourse(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        Integer firstMark = studentCourseMarkService.getFirstMarkForStudentInCourse(studentId, courseId);
        return ResponseEntity.ok(firstMark);
    }

    // 15. Get Student's highest mark in a course
    @GetMapping("/student/{studentId}/course/{courseId}/highest")
    public ResponseEntity<Integer> getHighestMarkForStudentInCourse(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        Integer highestMark = studentCourseMarkService.getHighestMarkForStudentInCourse(studentId, courseId);
        return ResponseEntity.ok(highestMark);
    }

    // 16. Get Student's average mark
    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<Double> getAverageMarksForStudent(@PathVariable Integer studentId) {
        Double averageMark = studentCourseMarkService.getAverageMarksForStudent(studentId);
        return ResponseEntity.ok(averageMark);
    }

    // 17. Get Student's average mark in a course
    @GetMapping("/student/{studentId}/course/{courseId}/average")
    public ResponseEntity<Double> getAverageMarksForStudentInCourse(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        Double averageMark = studentCourseMarkService.getAverageMarksForStudentInCourse(studentId, courseId);
        return ResponseEntity.ok(averageMark);
    }

    // 18. Get Student's marks greater than a given mark
    @GetMapping("/student/{studentId}/greater-than")
    public ResponseEntity<Long> getCountOfMarksGreaterThan(@PathVariable Integer studentId, @RequestParam Double mark) {
        Long count = studentCourseMarkService.getCountOfMarksGreaterThan(studentId, mark);
        return ResponseEntity.ok(count);
    }

    // 19. Get highest mark in a given course
    @GetMapping("/course/{courseId}/highest")
    public ResponseEntity<Integer> getHighestMarkForCourse(@PathVariable Integer courseId) {
        Integer highestMark = studentCourseMarkService.getHighestMarkForCourse(courseId);
        return ResponseEntity.ok(highestMark);
    }

    // 20. Get total number of marks in a course
    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<Long> getCountOfMarksForCourse(@PathVariable Integer courseId) {
        Long count = studentCourseMarkService.getCountOfMarksForCourse(courseId);
        return ResponseEntity.ok(count);
    }

    // 21. Pagination of StudentCourseMark
    @GetMapping("/pagination")
    public ResponseEntity<Page<StudentCourseMarkDTO>> getPaginatedMarks(@RequestParam int page, @RequestParam int size) {
        Page<StudentCourseMarkDTO> paginatedMarks = studentCourseMarkService.getStudentCourseMarksPage(PageRequest.of(page, size));
        return ResponseEntity.ok(paginatedMarks);
    }

    // 22. Pagination by studentId with sorting by createdDate
    @GetMapping("/pagination/student/{studentId}")
    public ResponseEntity<Page<StudentCourseMarkDTO>> getPaginatedMarksByStudentId(@PathVariable Integer studentId, @RequestParam int page, @RequestParam int size) {
        Page<StudentCourseMarkDTO> paginatedMarks = studentCourseMarkService.getStudentCourseMarksByStudentIdPage(studentId, PageRequest.of(page, size, Sort.by("createdDate").descending()));
        return ResponseEntity.ok(paginatedMarks);
    }

    // 23. Pagination by courseId with sorting by createdDate
    @GetMapping("/pagination/course/{courseId}")
    public ResponseEntity<Page<StudentCourseMarkDTO>> getPaginatedMarksByCourseId(@PathVariable Integer courseId, @RequestParam int page, @RequestParam int size) {
        Page<StudentCourseMarkDTO> paginatedMarks = studentCourseMarkService.getStudentCourseMarksByCourseIdPage(courseId, PageRequest.of(page, size, Sort.by("createdDate").descending()));
        return ResponseEntity.ok(paginatedMarks);
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "30") int size, @RequestBody FilterStudentCourseMarkDTO filter) {
        PageImpl<StudentCourseMarkDTO> result = studentCourseMarkService.filter(filter, page - 1, size);
        return ResponseEntity.ok(result);
    }
}
