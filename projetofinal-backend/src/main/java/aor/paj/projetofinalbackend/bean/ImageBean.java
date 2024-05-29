package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.entity.UserEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@ApplicationScoped
public class ImageBean {

    @EJB
    UserDao userDao;

    private static final String IMAGE_DIRECTORY = System.getenv("IMAGE_DIRECTORY");

    public String saveImage(InputStream imageData, String originalFileName, String email) throws IOException{
        String fileExtension = getFileExtension(originalFileName);
        String fileName = "profile." + fileExtension;
        String directory = IMAGE_DIRECTORY + "/" + email;
        Path imagePath = Paths.get(directory, fileName);

        if(!Files.exists(imagePath.getParent())){
            Files.createDirectories(imagePath.getParent());
        }
        Files.copy(imageData, imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imagePath.toString();
    }

    public byte[] getImage(String imagePath) throws IOException{
        Path path = Paths.get(imagePath);
        return Files.readAllBytes(path);
    }

    private String getFileExtension(String filename){
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public void saveUserProfileImage(String email, InputStream imageData, String originalFileName) throws IOException {
        UserEntity userEntity = userDao.findUserByEmail(email);
        if (userEntity == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        try {
            String imageType = "image/" + getFileExtension(originalFileName);
            String imagePath = saveImage(imageData, originalFileName, email);

            userEntity.setProfileImagePath(imagePath);
            userEntity.setProfileImageType(imageType);
            userDao.merge(userEntity);
        } finally {
            if (imageData != null) {
                imageData.close();
            }
        }
    }
}
