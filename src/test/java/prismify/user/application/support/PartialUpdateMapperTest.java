package prismify.user.application.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PartialUpdateMapperTest {

    static class TestDTO {
        private String name;
        private Integer age;
        private String nullField;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getNullField() {
            return nullField;
        }

        public void setNullField(String nullField) {
            this.nullField = nullField;
        }
    }

    @Test
    void copyNonNullFields_ShouldCopyOnlyNonNullFields() {
        TestDTO source = new TestDTO();
        source.setName("Juancito");
        source.setAge(42);
        source.setNullField(null);
        TestDTO target = new TestDTO();
        target.setName("Pepe");
        target.setAge(0);
        target.setNullField("Null");
        PartialUpdateMapper.copyNonNullFields(source, target);
        assertEquals("Juancito", target.getName());
        assertEquals(42, target.getAge());
        assertEquals("Null", target.getNullField());
    }

}
