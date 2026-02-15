package sg.edu.iss.cats.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sg.edu.iss.cats.helper.CourseEventEnum;
import sg.edu.iss.cats.model.Course;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CourseRepository using Mockito
 */
@ExtendWith(MockitoExtension.class)
class CourseRepositoryTest {

    @Mock
    private CourseRepository courseRepository;

    private Course course1;
    private Course course2;
    private Course course3;

    @BeforeEach
    void setUp() {
        course1 = Course.builder()
                .courseId(1)
                .courseName("Java Programming")
                .employeeId("EMP001")
                .status(CourseEventEnum.SUBMITTED)
                .build();

        course2 = Course.builder()
                .courseId(2)
                .courseName("Spring Boot")
                .employeeId("EMP001")
                .status(CourseEventEnum.UPDATED)
                .build();

        course3 = Course.builder()
                .courseId(3)
                .courseName("React.js")
                .employeeId("EMP002")
                .status(CourseEventEnum.APPROVED)
                .build();
    }

    @Test
    void testFindByEmployeeId_Success() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(course1, course2);
        when(courseRepository.findByEmployeeId("EMP001")).thenReturn(expectedCourses);

        // Act
        List<Course> actualCourses = courseRepository.findByEmployeeId("EMP001");

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).hasSize(2);
        assertThat(actualCourses).containsExactly(course1, course2);
        assertThat(actualCourses).allMatch(c -> c.getEmployeeId().equals("EMP001"));
        
        verify(courseRepository, times(1)).findByEmployeeId("EMP001");
    }

    @Test
    void testFindByEmployeeId_EmptyList() {
        // Arrange
        when(courseRepository.findByEmployeeId("EMP999")).thenReturn(Arrays.asList());

        // Act
        List<Course> actualCourses = courseRepository.findByEmployeeId("EMP999");

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).isEmpty();
        
        verify(courseRepository, times(1)).findByEmployeeId("EMP999");
    }

    @Test
    void testFindByEmployeeIdAndStatusIn_Success() {
        // Arrange
        List<String> statuses = Arrays.asList("SUBMITTED", "UPDATED");
        List<Course> expectedCourses = Arrays.asList(course1, course2);
        when(courseRepository.findByEmployeeIdAndStatusIn("EMP001", statuses))
                .thenReturn(expectedCourses);

        // Act
        List<Course> actualCourses = courseRepository.findByEmployeeIdAndStatusIn("EMP001", statuses);

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).hasSize(2);
        assertThat(actualCourses).allMatch(c -> 
            c.getEmployeeId().equals("EMP001") && 
            statuses.contains(c.getStatus().toString())
        );
        
        verify(courseRepository, times(1)).findByEmployeeIdAndStatusIn("EMP001", statuses);
    }

    @Test
    void testFindByStatus_Success() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(course1);
        when(courseRepository.findByStatus("SUBMITTED")).thenReturn(expectedCourses);

        // Act
        List<Course> actualCourses = courseRepository.findByStatus("SUBMITTED");

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).hasSize(1);
        assertThat(actualCourses.get(0).getStatus()).isEqualTo("SUBMITTED");
        
        verify(courseRepository, times(1)).findByStatus("SUBMITTED");
    }

    @Test
    void testFindByStatusJPQL_Success() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(course3);
        when(courseRepository.findByStatusJPQL("APPROVED")).thenReturn(expectedCourses);

        // Act
        List<Course> actualCourses = courseRepository.findByStatusJPQL("APPROVED");

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).hasSize(1);
        assertThat(actualCourses.get(0).getStatus()).isEqualTo("APPROVED");
        
        verify(courseRepository, times(1)).findByStatusJPQL("APPROVED");
    }

    @Test
    void testFindCoursesByStatus_Success() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(course1);
        when(courseRepository.findCoursesByStatus("SUBMITTED")).thenReturn(expectedCourses);

        // Act
        List<Course> actualCourses = courseRepository.findCoursesByStatus("SUBMITTED");

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).hasSize(1);
        
        verify(courseRepository, times(1)).findCoursesByStatus("SUBMITTED");
    }

    @Test
    void testFindPendingCoursesByEmployeeId_Success() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(course1, course2);
        when(courseRepository.findPendingCoursesByEmployeeId("EMP001"))
                .thenReturn(expectedCourses);

        // Act
        List<Course> actualCourses = courseRepository.findPendingCoursesByEmployeeId("EMP001");

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).hasSize(2);
        assertThat(actualCourses).allMatch(c -> 
            c.getStatus().equals("SUBMITTED") || c.getStatus().equals("UPDATED")
        );
        
        verify(courseRepository, times(1)).findPendingCoursesByEmployeeId("EMP001");
    }

    @Test
    void testFindPendingCoursesByStatus_Success() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(course1);
        when(courseRepository.findPendingCoursesByStatus("SUBMITTED"))
                .thenReturn(expectedCourses);

        // Act
        List<Course> actualCourses = courseRepository.findPendingCoursesByStatus("SUBMITTED");

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).hasSize(1);
        assertThat(actualCourses.get(0).getStatus()).isEqualTo("SUBMITTED");
        
        verify(courseRepository, times(1)).findPendingCoursesByStatus("SUBMITTED");
    }

    @Test
    void testSaveCourse() {
        // Arrange
        when(courseRepository.save(any(Course.class))).thenReturn(course1);

        // Act
        Course savedCourse = courseRepository.save(course1);

        // Assert
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getCourseId()).isEqualTo(1);
        assertThat(savedCourse.getCourseName().contains("Java Programming"));
        
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testFindById_Found() {
        // Arrange
        when(courseRepository.findById(1)).thenReturn(Optional.of(course1));

        // Act
        Optional<Course> foundCourse = courseRepository.findById(1);

        // Assert
        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getCourseId()).isEqualTo(1);
        
        verify(courseRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(courseRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Course> foundCourse = courseRepository.findById(999);

        // Assert
        assertThat(foundCourse).isEmpty();
        
        verify(courseRepository, times(1)).findById(999);
    }

    @Test
    void testDeleteCourse() {
        // Arrange
        doNothing().when(courseRepository).delete(course1);

        // Act
        courseRepository.delete(course1);

        // Assert
        verify(courseRepository, times(1)).delete(course1);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Course> allCourses = Arrays.asList(course1, course2, course3);
        when(courseRepository.findAll()).thenReturn(allCourses);

        // Act
        List<Course> actualCourses = courseRepository.findAll();

        // Assert
        assertThat(actualCourses).isNotNull();
        assertThat(actualCourses).hasSize(3);
        
        verify(courseRepository, times(1)).findAll();
    }
}