package com.ufro.dci.etransparency.etransparency_api_user.models.maturity;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Blob;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import javax.sql.rowset.serial.SerialBlob;

class MaturityModelFileTest {

    @Test
    void testMaturityModelFileConstructor() {
        MaturityModelFile file = new MaturityModelFile();
        assertNull(file.getId());
        assertNull(file.getFileData());
        assertNull(file.getFileName());
        assertNull(file.getFileType());
        assertNull(file.getMaturityModel());
    }

    @Test
    void testSettersAndGetters() throws SQLException {
        MaturityModelFile file = new MaturityModelFile();
        file.setId(1L);
        Blob blobData = new SerialBlob(new byte[]{1, 2, 3});
        file.setFileData(blobData);
        file.setFileName("sample.pdf");
        file.setFileType("application/pdf");
        MaturityModel maturityModel = new MaturityModel();
        file.setMaturityModel(maturityModel);
        assertEquals(1L, file.getId());
        assertEquals(blobData, file.getFileData());
        assertEquals("sample.pdf", file.getFileName());
        assertEquals("application/pdf", file.getFileType());
        assertEquals(maturityModel, file.getMaturityModel());
    }

    @Test
    void testBlobData() throws SQLException {
        byte[] sampleData = {10, 20, 30};
        Blob blobData = new SerialBlob(sampleData);
        MaturityModelFile file = new MaturityModelFile();
        file.setFileData(blobData);
        byte[] retrievedData = file.getFileData().getBytes(1, (int) file.getFileData().length());
        assertArrayEquals(sampleData, retrievedData);
    }

    @Test
    void testAllArgsConstructor() throws SQLException {
        Blob blobData = new SerialBlob(new byte[]{4, 5, 6});
        MaturityModel maturityModel = new MaturityModel();
        MaturityModelFile file = new MaturityModelFile(2L, blobData, "example.docx", "application/msword", maturityModel);
        assertEquals(2L, file.getId());
        assertEquals(blobData, file.getFileData());
        assertEquals("example.docx", file.getFileName());
        assertEquals("application/msword", file.getFileType());
        assertEquals(maturityModel, file.getMaturityModel());
    }

    @Test
    void testBuilder() throws SQLException {
        Blob blobData = new SerialBlob(new byte[]{7, 8, 9});
        MaturityModel maturityModel = new MaturityModel();
        MaturityModelFile file = MaturityModelFile.builder()
                .id(3L)
                .fileData(blobData)
                .fileName("file.txt")
                .fileType("text/plain")
                .maturityModel(maturityModel)
                .build();
        assertEquals(3L, file.getId());
        assertEquals(blobData, file.getFileData());
        assertEquals("file.txt", file.getFileName());
        assertEquals("text/plain", file.getFileType());
        assertEquals(maturityModel, file.getMaturityModel());
    }

    @Test
    void testToString() throws SQLException {
        Blob blobData = new SerialBlob(new byte[]{1, 2, 3});
        MaturityModelFile file = MaturityModelFile.builder()
                .id(4L)
                .fileData(blobData)
                .fileName("document.pdf")
                .fileType("application/pdf")
                .build();
        assertEquals(4L, file.getId());
        assertEquals("document.pdf", file.getFileName());
        assertEquals("application/pdf", file.getFileType());
    }
}
