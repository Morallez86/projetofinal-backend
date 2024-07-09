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

/**
 * Bean responsible for handling image operations, such as saving and retrieving user profile images.
 *
 * @see UserDao
 * @see UserEntity
 * @see InputStream
 * @see Files
 * @see Path
 * @see Paths
 * @see StandardCopyOption
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class ImageBean {

    @EJB
    UserDao userDao;

    private static final String IMAGE_DIRECTORY = System.getenv("IMAGE_DIRECTORY");

    /**
     * Saves the provided image data to the user's profile directory and returns the image path.
     *
     * @param imageData the image data as an InputStream
     * @param originalFileName the original file name of the image
     * @param email the email of the user
     * @return the path where the image is saved
     * @throws IOException if an I/O error occurs
     */
    public String saveImage(InputStream imageData, String originalFileName, String email) throws IOException {
        String fileExtension = getFileExtension(originalFileName);
        String fileName = "profile." + fileExtension;
        String directory = IMAGE_DIRECTORY + "/" + email;
        Path imagePath = Paths.get(directory, fileName);

        if (!Files.exists(imagePath.getParent())) {
            Files.createDirectories(imagePath.getParent());
        }
        Files.copy(imageData, imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imagePath.toString();
    }

    /**
     * Retrieves the image as a byte array from the provided image path.
     *
     * @param imagePath the path to the image
     * @return the image as a byte array
     * @throws IOException if an I/O error occurs
     */
    public byte[] getImage(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        return Files.readAllBytes(path);
    }

    /**
     * Extracts the file extension from the provided file name.
     *
     * @param filename the file name to extract the extension from
     * @return the file extension
     */
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * Saves the user's profile image by updating the user's entity with the image path and type.
     *
     * @param email the email of the user
     * @param imageData the image data as an InputStream
     * @param originalFileName the original file name of the image
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if the user is not found
     */
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
