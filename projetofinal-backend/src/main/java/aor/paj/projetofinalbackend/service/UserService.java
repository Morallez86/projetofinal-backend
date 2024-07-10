package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.AuthBean;
import aor.paj.projetofinalbackend.bean.ImageBean;
import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.dto.*;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.pojo.ConfirmationRequest;
import aor.paj.projetofinalbackend.pojo.ResponseMessage;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.EmailSender;
import aor.paj.projetofinalbackend.utils.EncryptHelper;
import aor.paj.projetofinalbackend.utils.JsonUtils;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Service endpoints for managing users.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/users")
public class UserService {

    @Inject
    UserBean userBean;

    @Inject
    TokenBean tokenBean;

    @Inject
    ImageBean imageBean;

    @Inject
    AuthBean authBean;

    @Inject
    EmailSender emailSender;


    /**
     * Retrieves users based on optional search criteria.
     *
     * @param searchTerm Optional search term for filtering users.
     * @param workplace Optional workplace filter.
     * @param skills Optional skills filter.
     * @param interests Optional interests filter.
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @return Response with status OK and a list of users if successful, otherwise appropriate error response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("searchTerm") String searchTerm,
                             @QueryParam("workplace") String workplace,
                             @QueryParam("skills") String skills,
                             @QueryParam("interests") String interests,
                             @HeaderParam("Authorization") String authorizationHeader) {
        // Validate the token
        String token = authorizationHeader.substring("Bearer".length()).trim();
        Response validationResponse = authBean.validateUserToken(token);

        UserEntity user = tokenBean.findUserByToken(token);

        if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return validationResponse;
        }

        List<UserDto> users;

        // Apply filters based on provided query parameters
        if ((searchTerm == null || searchTerm.isEmpty()) && (workplace == null || workplace.isEmpty())
                && (skills == null || skills.isEmpty()) && (interests == null || interests.isEmpty())) {
            // If no search term or filters are provided, fetch all users
            users = userBean.getAllUsers();
        } else {
            users = userBean.searchUsers(searchTerm, workplace, skills, interests);
        }

        if (users == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to search User").build();
        }

        LoggerUtil.logInfo("SEARCH FOR USERS", "at " + LocalDateTime.now(), user.getEmail(), token);
        return Response.ok(users).build();
    }

    /**
     * Endpoint for user login.
     *
     * @param credentials UserCredentials object containing email and password.
     * @return Response with status OK and a token if login is successful, otherwise UNAUTHORIZED.
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response login(UserCredentials credentials) {

        UserEntity user = userBean.findUserByEmail(credentials.getEmail());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User not found").build();
        }

        if (!EncryptHelper.checkPassword(credentials.getPassword(), user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid password").build();
        }

        String tokenValue = userBean.createAndSaveToken(user);
        LoggerUtil.logInfo("LOGIN" , "at " + LocalDateTime.now(), user.getEmail() , tokenValue);
        return Response.ok(new TokenResponse(tokenValue)).build();
    }

    /**
     * Endpoint for user logout.
     *
     * @param authHeader Authorization header containing the bearer token.
     * @param jsonBody JSON body containing project timestamps for chat.
     * @return Response with status OK if logout is successful, otherwise appropriate error response.
     */
    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response logout(@HeaderParam("Authorization") String authHeader, String jsonBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        HashMap<String, HashMap<String, String>> outerMap;
        try {
            outerMap = objectMapper.readValue(jsonBody, new TypeReference<HashMap<String, HashMap<String, String>>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseMessage("Invalid request body"))
                    .build();
        }

        String token = authHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenBean.findUserByToken(token);

        HashMap<Long, LocalDateTime> mapTimersChat = new HashMap<>();
        if (outerMap.containsKey("projectTimestamps")) {
            HashMap<String, String> innerMap = outerMap.get("projectTimestamps");
            for (Map.Entry<String, String> entry : innerMap.entrySet()) {
                try {
                    Long key = Long.parseLong(entry.getKey());
                    LocalDateTime value = LocalDateTime.parse(entry.getValue(), DateTimeFormatter.ISO_DATE_TIME);
                    mapTimersChat.put(key, value);
                } catch (NumberFormatException | DateTimeParseException e) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ResponseMessage("Invalid key or date format in request body"))
                            .build();
                }
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseMessage("Missing projectTimestamps in request body"))
                    .build();
        }

        userBean.updateTimersChat(token, mapTimersChat);

        if (tokenBean.deactivateToken(token)) {
            LoggerUtil.logInfo("LOGOUT", "at " + LocalDateTime.now(), user.getEmail(), token);

            return Response.status(Response.Status.OK)
                    .entity(new ResponseMessage("User successfully logged out"))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ResponseMessage("Failed to log out user"))
                    .build();
        }
    }

    /**
     * Endpoint for user registration.
     *
     * @param userDto UserDto object containing user registration information.
     * @return Response with status CREATED if registration is successful, otherwise appropriate error response.
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDto userDto) {
        try {
            userBean.validateUserDto(userDto);
            userBean.registerUser(userDto);
            LoggerUtil.logInfo("REGISTERED" , "at " + LocalDateTime.now(), userDto.getEmail(), "not token");
            return Response.status(Response.Status.CREATED).entity("User registered successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An unexpected error occurred").build();
        }
    }

    /**
     * Sends an email for password recovery.
     *
     * @param email User's email for password recovery.
     * @return Response with status OK if email is sent successfully, otherwise appropriate error response.
     */
    @POST
    @Path("/emailRecoveryPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response emailRecoveryPassword(String email) {
        try {
            email = email.replace("\"", "").trim();
            UserEntity user = userBean.findUserByEmail(email);
            if (user != null) {
                String token = userBean.emailTokenCreationForLink(user);
                emailSender.sendRecoveryPassword(email, token);
                LoggerUtil.logInfo("Email SENT TO RECOVERY PASSWORD:" , "at " + LocalDateTime.now(), email, "not token");

                return Response.status(200).entity("Email sent").build();
            } else {
                return Response.status(400).entity("User not found").build();
            }
        } catch (Exception e) {
            return Response.status(500).entity("Internal server error").build();
        }
    }

    /**
     * Resets the password using a confirmation token.
     *
     * @param request ConfirmationRequest object containing token and new password.
     * @return Response with status OK if password reset is successful, otherwise appropriate error response.
     */
    @PUT
    @Path("/forgotPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response forgotPassword(ConfirmationRequest request) {
        String emailValidationToken = request.getToken();
        String password = request.getPassword();
        UserEntity user = userBean.getUserByEmailToken(emailValidationToken);
        if (user != null) {
            try {
                userBean.forgotPassword(user, password);
                LoggerUtil.logInfo("PASSWORD UPDATED" , "at " + LocalDateTime.now(), user.getEmail(), "not token");
                return Response.status(200).entity("New Password updated").build();
            } catch (Exception e) {
                return Response.status(500).entity("Failed to update Password").build();
            }
        } else {
            return Response.status(404).entity("User not found").build();
        }
    }

    /**
     * Confirms user registration via email token.
     *
     * @param emailToken Email token used for confirming registration.
     * @return Response with status OK if registration is confirmed, otherwise appropriate error response.
     */
    @GET
    @Path("/confirmRegistration")
    public Response confirmRegistration(@HeaderParam("emailToken") String emailToken){
        UserEntity user = userBean.getUserByEmailToken(emailToken);
        if (user != null) {
            try {
                userBean.confirmRegistration(user);
                LoggerUtil.logInfo("REGISTRATION CONFIRMED" , "at " + LocalDateTime.now(), user.getEmail(), "not token");

                return Response.status(200).entity("Registration confirmed").build();
            } catch (Exception e) {
                return Response.status(500).entity("Failed to confirm registration").build();
            }
        } else {
            return Response.status(404).entity("User not found").build();
        }


    }

    /**
     * Uploads a user profile image.
     *
     * @param imageData Input stream containing image data.
     * @param originalFileName Original filename of the uploaded image.
     * @param email User's email for associating the image.
     * @return Response with status OK if image upload is successful, otherwise appropriate error response.
     */
    @POST
    @Path("/image")
    @Consumes("image/*")
    public Response uploadImage(InputStream imageData, @HeaderParam("filename") String originalFileName, @HeaderParam("email") String email) {
        try {
            imageBean.saveUserProfileImage(email, imageData, originalFileName);
            } catch (IOException ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        LoggerUtil.logInfo("USER PROFILE IMAGE UPDATED" , "at " + LocalDateTime.now(), email, "not token");

        return Response.ok().build();
    }

    /**
     * Retrieves a user's profile picture by user ID.
     *
     * @param id User ID for retrieving the profile picture.
     * @return Response with the user's profile picture if successful, otherwise appropriate error response.
     */
    @GET
    @Path("{id}/image")
    @Produces("image/*")
    public Response getUserPicture(@PathParam("id") Long id){
        UserEntity userEntity = userBean.findUserById(id);
        if(userEntity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String imagePath = userEntity.getProfileImagePath();
        if(imagePath == null){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        byte[] imageData;
        try{
            imageData = imageBean.getImage(imagePath);
        }catch (IOException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        String imageType = userEntity.getProfileImageType();
        return Response.ok(new ByteArrayInputStream(imageData)).type(imageType).build();

    }

    /**
     * Retrieves profile pictures of multiple users.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param ids List of user IDs for retrieving profile pictures.
     * @return Response with a list of user profile pictures if successful, otherwise appropriate error response.
     */
    @POST
    @Path("/images")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUserPictures(@HeaderParam("Authorization") String authorizationHeader, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No user IDs provided").build();
        }

        List<Map<String, Object>> imagesList = new ArrayList<>();

        for (Long id : ids) {
            UserEntity userEntity = userBean.findUserById(id);
            if (userEntity == null) {
                continue; // Skipping non-existent user
            }

            String imagePath = userEntity.getProfileImagePath();
            if (imagePath == null) {
                continue; // Skipping users without a profile image
            }

            byte[] imageData;
            try {
                imageData = imageBean.getImage(imagePath);
            } catch (IOException e) {
                continue; // Skipping users whose image couldn't be read
            }

            String imageType = userEntity.getProfileImageType();

            // Create a map for the image data and metadata
            Map<String, Object> imageMap = new HashMap<>();
            imageMap.put("id", id);
            imageMap.put("image", Base64.getEncoder().encodeToString(imageData));
            imageMap.put("type", imageType);

            imagesList.add(imageMap);
        }

        if (imagesList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No valid user images found").build();
        }
        return Response.ok(imagesList).build();
    }

    /**
     * Retrieves user profile details by user ID.
     *
     * @param headers HTTP headers containing Authorization header.
     * @param userId  User ID for retrieving profile details.
     * @return Response with user profile details if successful, otherwise appropriate error response.
     */
    @GET
    @Path("/profile/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfileDetails(@Context HttpHeaders headers, @PathParam("userId") Long userId) {
        String authorizationHeader = headers.getHeaderString("Authorization");

        // Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenBean.findUserByToken(token);

        // Validate the token
        Response validationResponse = authBean.validateUserToken(token);
        if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return validationResponse;
        }

        // Extract user ID from the token
        Long tokenUserId;
        try {
            tokenUserId = JwtUtil.extractUserIdFromToken(token);
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        // Check if the requested user ID matches the user ID from the token
        if (tokenUserId.equals(userId)) {
            // Return the profile if the id and token is the sae
            ProfileDto profileDto = userBean.getProfileDtoById(userId);
            if (profileDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Profile not found").build();
            }
            LoggerUtil.logInfo("CHECK PROFILE USER: " + profileDto.getEmail()  , "at " + LocalDateTime.now(), user.getEmail(), token);
            return Response.ok(profileDto).build();
        } else {
            // Check the visibility of the requested user's profile
            ProfileDto profileDto = userBean.getProfileDtoById(userId);
            if (profileDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Profile not found").build();
            }
            if (!profileDto.getVisibility()) {
                return Response.status(Response.Status.FORBIDDEN).entity("Profile not visible").build();
            }
            LoggerUtil.logInfo("CHECK PROFILE USER: " + profileDto.getEmail()  , "at " + LocalDateTime.now(), user.getEmail(), token);

            return Response.ok(profileDto).build();
        }
    }

    /**
     * Updates user profile details by user ID.
     *
     * @param headers HTTP headers containing Authorization header.
     * @param userId User ID for updating profile details.
     * @param profileDto ProfileDto object containing updated profile information.
     * @return Response with status OK if profile update is successful, otherwise appropriate error response.
     */
    @PUT
    @Path("/profile/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserProfile(@Context HttpHeaders headers, @PathParam("userId") Long userId, ProfileDto profileDto) {
        String authorizationHeader = headers.getHeaderString("Authorization");

        // Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenBean.findUserByToken(token);

        // Validate the token
        Response validationResponse = authBean.validateUserToken(token);
        if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return validationResponse;
        }

        try {
            userBean.updateUserProfile(userId, profileDto);
            LoggerUtil.logInfo("UPDATE PROFILE " + profileDto.getEmail()  , "at " + LocalDateTime.now(), user.getEmail(), token);

            return Response.ok().entity("Profile updated successfully").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates user password.
     *
     * @param u UserPasswordUpdateDto object containing old and new passwords.
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @return Response with status OK if password update is successful, otherwise appropriate error response.
     */
    @PUT
    @Path("/updatePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePassword(UserPasswordUpdateDto u, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the Authorization header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenBean.findUserByToken(token);

            // Check if the old password and new password are the same
            if (u.getOldPassword().equals(u.getNewPassword())) {
                LoggerUtil.logError("ERROR - UPDATE PASSWORD: new password needs to be different from actual password " , "at " + LocalDateTime.now(), user.getEmail(), token);
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(JsonUtils.convertObjectToJson(new ResponseMessage("New password must be different from the old password")))
                        .build();
            }

            // Attempt to update the password
            boolean updateSuccessful = userBean.updatePassword(u, token);

            if (updateSuccessful) {
                LoggerUtil.logInfo("PASSWORD UPDATED ", "at " + LocalDateTime.now(), user.getEmail(), token);

                return Response.status(Response.Status.OK)
                        .entity(JsonUtils.convertObjectToJson(new ResponseMessage("Password is updated")))
                        .build();
            } else {
                LoggerUtil.logError("ERROR - UPDATE PASSWORD: actual password is incorrect " , "at " + LocalDateTime.now(), user.getEmail(), token);

                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(JsonUtils.convertObjectToJson(new ResponseMessage("Old password is incorrect")))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Retrieves projects associated with a user.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param userId User ID for retrieving associated projects.
     * @param limit Maximum number of projects to retrieve (pagination).
     * @return Response with projects associated with the user if successful, otherwise appropriate error response.
     */
    @GET
    @Path("/{userId}/myProjects")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyProjects(@HeaderParam("Authorization") String authorizationHeader,
                                  @PathParam("userId") Long userId,
                                  @QueryParam("limit") int limit) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenBean.findUserByToken(token);
        Response validationResponse = authBean.validateUserToken(token);
        if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return validationResponse;
        }

        Set<ProjectDto> projectDtos = userBean.getUserProjects(userId, limit);
        if (projectDtos == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No projects found for the user").build();
        }

        long totalProjects = userBean.getTotalProjectCount(userId);
        int totalPages = (int) Math.ceil((double) totalProjects / limit);

        // Include total number of pages in the response
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("projects", projectDtos);
        responseMap.put("totalPages", totalPages);

        LoggerUtil.logInfo("CHECK OWN PROJECTS", "at " + LocalDateTime.now(), user.getEmail(), token);

        return Response.ok(responseMap).build();
    }

    /**
     * Searches users based on a query string.
     *
     * @param query Query string for searching users.
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @return Response with users matching the query if successful, otherwise appropriate error response.
     */
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@QueryParam("query") String query, @HeaderParam("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenBean.findUserByToken(token);

        // Validate the token
        Response validationResponse = authBean.validateUserToken(token);
        if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return validationResponse;
        }

        List<UserDto> users = userBean.searchUsersByQuery(query);

        if (users == null || users.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Users not found").build();
        }

        LoggerUtil.logInfo("SEARCH FOR USERS WITH QUERY: " + query, "at " + LocalDateTime.now(), user.getEmail(), token);
        return Response.ok(users).build();
    }

    /**
     * Updates the role of a user identified by userId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token for authentication.
     * @param userId The ID of the user whose role is to be updated.
     * @param userDto UserDto object containing the updated role information.
     * @return Response with status OK and a success message if the role update is successful, otherwise appropriate error response.
     */
    @PUT
    @Path("/role/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserRole(@HeaderParam("Authorization") String authorizationHeader, @PathParam("userId") Long userId, UserDto userDto) {
        // Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenBean.findUserByToken(token);

        // Validate the token
        Response validationResponse = authBean.validateUserToken(token);
        if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            LoggerUtil.logInfo("ROLE UPDATED TO USER:" + userDto.getEmail(), "at " + LocalDateTime.now(), user.getEmail(), token);

            return validationResponse;
        }

        // Extract user ID from the token to ensure that only authorized users can change roles
        Long tokenUserId;
        try {
            tokenUserId = JwtUtil.extractUserIdFromToken(token);
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        // Check if the current user is an admin
        UserEntity currentUser = userBean.findUserById(tokenUserId);
        if (currentUser == null || currentUser.getRole().getValue() != 200) {
            return Response.status(Response.Status.FORBIDDEN).entity("Only admins can update roles").build();
        }

        // Update the role
        try {
            userBean.updateUserRole(userId, userDto);
            return Response.ok().entity("User role updated successfully").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
