package org.example.users;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class FileUploadService {
    private static final String DOWNLOAD_URL = "https://storage.googleapis.com/profile_pics_bucket/%s";

    public String uploadFile(File file, String fileName) throws IOException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("credentials.json");
        File credentialsFile;

        if (resource == null) {
            throw new IllegalArgumentException("Firebase credentials not found!");
        } else {
            credentialsFile = new File(resource.toURI());
        }

        BlobId blobId = BlobId.of("profile_pics_bucket", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFile));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
