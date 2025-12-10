package prismify.user.infrastructure.controllers.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import prismify.user.domain.models.Role;
import prismify.user.infrastructure.controllers.dto.user.CreateUserRequestDTO;

class CreateUserRequestDTOTest {

        private Validator validator;

        @BeforeEach
        void setUp() {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                validator = factory.getValidator();
        }

        private CreateUserRequestDTO buildValidDTO() {
                return new CreateUserRequestDTO(
                                "juancito",
                                "juancito@correo.cl",
                                "strongPassword123",
                                "5551234567",
                                "Developer",
                                "20331214-8",
                                "Santiago",
                                "Blue",
                                "ACR",
                                Role.MANAGER);
        }

        @Test
        void testValidDTO_NoViolations() {
                CreateUserRequestDTO dto = buildValidDTO();
                Set<ConstraintViolation<CreateUserRequestDTO>> violations = validator.validate(dto);
                if (!violations.isEmpty()) {
                        violations.forEach(v -> System.out.println(v.getPropertyPath() + " -> " + v.getMessage()));
                }
                assertTrue(violations.isEmpty());
        }

        @Test
        void testRutTooLong_Violation() {
                CreateUserRequestDTO dto = buildValidDTO();
                dto.setRut("1234567890123");
                Set<ConstraintViolation<CreateUserRequestDTO>> violations = validator.validate(dto);
                assertTrue(violations.stream()
                                .anyMatch(v -> v.getPropertyPath().toString().equals("rut")));
        }

        @Test
        void testCityTooLong_Violation() {
                CreateUserRequestDTO dto = buildValidDTO();
                dto.setCity("A".repeat(31));
                Set<ConstraintViolation<CreateUserRequestDTO>> violations = validator.validate(dto);
                assertTrue(violations.stream()
                                .anyMatch(v -> v.getPropertyPath().toString().equals("city")));
        }

        @Test
        void testColorTooLong_Violation() {
                CreateUserRequestDTO dto = buildValidDTO();
                dto.setColor("X".repeat(21));
                Set<ConstraintViolation<CreateUserRequestDTO>> violations = validator.validate(dto);
                assertTrue(violations.stream()
                                .anyMatch(v -> v.getPropertyPath().toString().equals("color")));
        }

        @Test
        void testAcronymTooLong_Violation() {
                CreateUserRequestDTO dto = buildValidDTO();
                dto.setAcronym("ABCD");
                Set<ConstraintViolation<CreateUserRequestDTO>> violations = validator.validate(dto);
                assertTrue(violations.stream()
                                .anyMatch(v -> v.getPropertyPath().toString().equals("acronym")));
        }

        @Test
        void testPositionTooLong_Violation() {
                CreateUserRequestDTO dto = buildValidDTO();
                dto.setPosition("P".repeat(101));
                Set<ConstraintViolation<CreateUserRequestDTO>> violations = validator.validate(dto);
                assertTrue(violations.stream()
                                .anyMatch(v -> v.getPropertyPath().toString().equals("position")));
        }

        @Test
        void testValidRutAnnotation() {
                CreateUserRequestDTO dto = buildValidDTO();
                dto.setRut("INVALID-RUT");
                Set<ConstraintViolation<CreateUserRequestDTO>> violations = validator.validate(dto);
                assertTrue(violations.stream()
                                .anyMatch(v -> v.getPropertyPath().toString().equals("rut")));
        }

}
