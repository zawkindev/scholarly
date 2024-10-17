package asuna.maftuna.jp.controller;

import asuna.maftuna.jp.dto.CourseDTO;
import asuna.maftuna.jp.dto.FilterCourseDTO;
import asuna.maftuna.jp.dto.FilterStudentDTO;
import asuna.maftuna.jp.dto.StudentDTO;
import asuna.maftuna.jp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping({"", "/"})
    private ResponseEntity<CourseDTO> create(@RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.create(dto));
    }

    @PutMapping({"", "/"})
    private ResponseEntity<CourseDTO> update(@RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.update(dto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(courseService.delete(id));
    }

    @GetMapping({"", "/"})
    private ResponseEntity<List<CourseDTO>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    private CourseDTO getById(@PathVariable Integer id) {
        return courseService.getById(id);
    }

    @GetMapping({"", "/"})
    private ResponseEntity<List<CourseDTO>> getByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(courseService.getByName(name));
    }

    @GetMapping("/price")
    public ResponseEntity<List<CourseDTO>> getByPrice(@RequestParam("price") double price) {
        return ResponseEntity.ok(courseService.getByPrice(price));
    }

    @GetMapping("/duration")
    public ResponseEntity<List<CourseDTO>> getByDuration(@RequestParam("duration") LocalTime duration) {
        return ResponseEntity.ok(courseService.getByDuration(duration));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<CourseDTO>> getByPriceRange(@RequestParam("min") double minPrice, @RequestParam("max") double maxPrice) {
        return ResponseEntity.ok(courseService.getByPriceBetween(minPrice, maxPrice));
    }

    @GetMapping("/createdDate/between")
    public ResponseEntity<List<CourseDTO>> getCoursesBetweenDates(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(courseService.getCoursesBetweenDates(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate)));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<CourseDTO>> getCoursesPage(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(courseService.getCoursesPage(PageRequest.of(page, size)));
    }

    @GetMapping("/pagination/sorted")
    public ResponseEntity<Page<CourseDTO>> getCoursesSortedByCreatedDate(@RequestParam int page, @RequestParam int size, @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(courseService.getCoursesSortedByCreatedDate(PageRequest.of(page, size)));
    }

    @GetMapping("/pagination/price")
    public ResponseEntity<Page<CourseDTO>> getCoursesByPriceSortedByCreatedDate(@RequestParam double price, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(courseService.getCoursesByPriceSortedByCreatedDate(price, PageRequest.of(page, size)));
    }

    @GetMapping("/pagination/price-range")
    public ResponseEntity<Page<CourseDTO>> getCoursesByPriceRangeSortedByCreatedDate(@RequestParam double minPrice, @RequestParam double maxPrice, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(courseService.getCoursesByPriceRangeSortedByCreatedDate(minPrice, maxPrice, PageRequest.of(page, size)));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<CourseDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "30") int size, @RequestBody FilterCourseDTO filter) {
        PageImpl<CourseDTO> result = courseService.filter(filter, page - 1, size);
        return ResponseEntity.ok(result);
    }

}
