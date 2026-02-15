package sg.edu.iss.cats.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import sg.edu.iss.cats.helper.CourseEventEnum;
import sg.edu.iss.cats.model.Course;
import sg.edu.iss.cats.model.Employee;

/**
 * Integration tests for CourseRepository using @DataJpaTest
 * These tests use an in-memory database (H2) and test actual database operations
 */
@DataJpaTest
@ActiveProfiles("test") // Use test profile with H2 database
class CourseRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    private Employee dilbert;
    private Employee pointy;
    private Course javaCourse;
    private Course springCourse;
    private Course reactCourse;

    @BeforeEach
    void setUp() {
        // Create and persist employees (needed for FK constraints)
        dilbert = Employee.builder()
                .employeeId("dilbert")
                .name("Dilbert")
                .managerId("pointy")
                .build();
        entityManager.persist(dilbert);

        pointy = Employee.builder()
                .employeeId("pointy")
                .name("Pointy")
                .managerId("dogbert")
                .build();
        entityManager.persist(pointy);

        // Create and persist courses
        javaCourse = Course.builder()
                .courseName("Java Programming")
                .employeeId("dilbert")
                .organiser("Tech Academy")
                .fromDate(LocalDate.of(2024, 3, 1))
                .toDate(LocalDate.of(2024, 3, 5))
                .fees(1500.0)
                .gstIncluded(true)
                .justification("Need to learn Java")
                .status(CourseEventEnum.SUBMITTED)
                .build();
        entityManager.persist(javaCourse);

        springCourse = Course.builder()
                .courseName("Spring Boot Masterclass")
                .employeeId("dilbert")
                .organiser("Spring Academy")
                .fromDate(LocalDate.of(2024, 4, 1))
                .toDate(LocalDate.of(2024, 4, 10))
                .fees(2000.0)
                .gstIncluded(true)
                .justification("Advanced Spring skills needed")
                .status(CourseEventEnum.UPDATED)
                .build();
        entityManager.persist(springCourse);

        reactCourse = Course.builder()
                .courseName("React.js Fundamentals")
                .employeeId("pointy")
                .organiser("Frontend Masters")
                .fromDate(LocalDate.of(2024, 5, 1))
                .toDate(LocalDate.of(2024, 5, 3))
                .fees(1200.0)
                .gstIncluded(false)
                .justification("Frontend development")
                .status(CourseEventEnum.APPROVED)
                .build();
        entityManager.persist(reactCourse);

        entityManager.flush();
    }

    @Test
    void testFindByEmployeeId_Success() {
        // Act
        List<Course> courses = courseRepository.findByEmployeeId("dilbert");

        // Assert
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(2);
        assertThat(courses).extracting(Course::getTitle)
                .containsExactlyInAnyOrder("Java Programming", "Spring Boot Masterclass");
        assertThat(courses).allMatch(c -> c.getEmployeeId().equals("dilbert"));
    }

    @Test
    void testFindByEmployeeId_NoResults() {
        // Act
        List<Course> courses = courseRepository.findByEmployeeId("nonexistent");

        // Assert
        assertThat(courses).isEmpty();
    }

    @Test
    void testFindByEmployeeIdAndStatusIn_Success() {
        // Arrange
        List<String> statuses = Arrays.asList("SUBMITTED", "UPDATED");

        // Act
        List<Course> courses = courseRepository.findByEmployeeIdAndStatusIn("dilbert", statuses);

        // Assert
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(2);
        assertThat(courses).allMatch(c -> 
            c.getEmployeeId().equals("dilbert") && 
            (c.getStatus().equals(CourseEventEnum.SUBMITTED) || c.getStatus().equals(CourseEventEnum.UPDATED))
        );
    }

    @Test
    void testFindByEmployeeIdAndStatusIn_PartialMatch() {
        // Arrange
        List<String> statuses = Arrays.asList("APPROVED");

        // Act
        List<Course> courses = courseRepository.findByEmployeeIdAndStatusIn("dilbert", statuses);

        // Assert
        assertThat(courses).isEmpty();
    }

    @Test
    void testFindByStatus_Success() {
        // Act
        List<Course> courses = courseRepository.findByStatus("SUBMITTED");

        // Assert
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getTitle().c("Java Programming");
        assertThat(courses.get(0).getStatus()).isEqualTo("SUBMITTED");
    }

    @Test
    void testFindByStatusJPQL_Success() {
        // Act
        List<Course> courses = courseRepository.findByStatusJPQL("APPROVED");

        // Assert
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getTitle()).isEqualTo("React.js Fundamentals");
    }

    @Test
    void testFindCoursesByStatus_Success() {
        // Act
        List<Course> courses = courseRepository.findCoursesByStatus("UPDATED");

        // Assert
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getTitle()).isEqualTo("Spring Boot Masterclass");
    }

    @Test
    void testFindPendingCoursesByEmployeeId_Success() {
        // Act
        List<Course> courses = courseRepository.findPendingCoursesByEmployeeId("dilbert");

        // Assert
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(2);
        assertThat(courses).allMatch(c -> 
            c.getStatus().equals("SUBMITTED") || c.getStatus().equals("UPDATED")
        );
    }

    @Test
    void testFindPendingCoursesByStatus_Success() {
        // Act
        List<Course> courses = courseRepository.findPendingCoursesByStatus("SUBMITTED");

        // Assert
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getStatus()).isEqualTo("SUBMITTED");
    }

    @Test
    void testSaveCourse() {
        // Arrange
        Course newCourse = Course.builder()
                .title("Docker and Kubernetes")
                .employeeId("dilbert")
                .organiser("DevOps Academy")
                .fromDate(LocalDate.of(2024, 6, 1))
                .toDate(LocalDate.of(2024, 6, 7))
                .fees(2500.0)
                .gstIncluded(true)
                .justification("DevOps skills")
                .status("SUBMITTED")
                .comments("Container orchestration")
                .build();

        // Act
        Course savedCourse = courseRepository.save(newCourse);

        // Assert
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getCourseId()).isNotNull();
        assertThat(savedCourse.getTitle()).isEqualTo("Docker and Kubernetes");
        
        // Verify it's in the database
        Course foundCourse = entityManager.find(Course.class, savedCourse.getCourseId());
        assertThat(foundCourse).isNotNull();
        assertThat(foundCourse.getTitle()).isEqualTo("Docker and Kubernetes");
    }

    @Test
    void testUpdateCourse() {
        // Arrange
        javaCourse.setStatus("APPROVED");
        javaCourse.setComments("Approved by manager");

        // Act
        Course updatedCourse = courseRepository.save(javaCourse);

        // Assert
        assertThat(updatedCourse.getStatus()).isEqualTo("APPROVED");
        assertThat(updatedCourse.getComments()).isEqualTo("Approved by manager");
        
        // Verify in database
        entityManager.flush();
        entityManager.clear();
        Course foundCourse = entityManager.find(Course.class, javaCourse.getCourseId());
        assertThat(foundCourse.getStatus()).isEqualTo("APPROVED");
    }

    @Test
    void testDeleteCourse() {
        // Arrange
        Integer courseId = javaCourse.getCourseId();

        // Act
        courseRepository.delete(javaCourse);
        entityManager.flush();

        // Assert
        Course foundCourse = entityManager.find(Course.class, courseId);
        assertThat(foundCourse).isNull();
    }

    @Test
    void testFindById_Found() {
        // Act
        Optional<Course> foundCourse = courseRepository.findById(javaCourse.getCourseId());

        // Assert
        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getTitle()).isEqualTo("Java Programming");
    }

    @Test
    void testFindById_NotFound() {
        // Act
        Optional<Course> foundCourse = courseRepository.findById(99999);

        // Assert
        assertThat(foundCourse).isEmpty();
    }

    @Test
    void testFindAll() {
        // Act
        List<Course> allCourses = courseRepository.findAll();

        // Assert
        assertThat(allCourses).isNotNull();
        assertThat(allCourses).hasSize(3);
    }

    @Test
    void testExistsById() {
        // Act & Assert
        assertThat(courseRepository.existsById(javaCourse.getCourseId())).isTrue();
        assertThat(courseRepository.existsById(99999)).isFalse();
    }

    @Test
    void testCount() {
        // Act
        long count = courseRepository.count();

        // Assert
        assertThat(count).isEqualTo(3);
    }

    @Test
    void testQueryByMultipleStatuses() {
        // Arrange
        Course withdrawnCourse = Course.builder()
                .title("Withdrawn Course")
                .employeeId("pointy")
                .organiser("Some Academy")
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now().plusDays(5))
                .fees(1000.0)
                .gstIncluded(true)
                .justification("Test")
                .status("WITHDRAWN")
                .build();
        entityManager.persist(withdrawnCourse);
        entityManager.flush();

        // Act
        List<String> statuses = Arrays.asList("APPROVED", "WITHDRAWN");
        List<Course> courses = courseRepository.findByEmployeeIdAndStatusIn("pointy", statuses);

        // Assert
        assertThat(courses).hasSize(2);
        assertThat(courses).extracting(Course::getStatus)
                .containsExactlyInAnyOrder("APPROVED", "WITHDRAWN");
    }
}