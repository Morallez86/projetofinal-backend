package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.AuthBean;
import aor.paj.projetofinalbackend.bean.ImageBean;
import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.dto.*;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.pojo.ConfirmationRequest;
import aor.paj.projetofinalbackend.pojo.ResponseMessage;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.EmailSender;
import aor.paj.projetofinalbackend.utils.EncryptHelper;
import aor.paj.projetofinalbackend.utils.JsonUtils;
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
import java.util.*;
import java.util.stream.Collectors;

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

        return Response.ok(users).build();
    }




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
        return Response.ok(new TokenResponse(tokenValue)).build();
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("Authorization") String authHeader, HashMap<Long, LocalDateTime> mapTimersChat) {

        String token = authHeader.substring("Bearer".length()).trim();

        userBean.updateTimersChat(token, mapTimersChat);

        if (tokenBean.deactivateToken(token)) {
            return Response.status(Response.Status.OK)
                    .entity(new ResponseMessage("User successfully logged out"))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ResponseMessage("Failed to log out user"))
                    .build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDto userDto) {
        try {
            userBean.validateUserDto(userDto);
            userBean.registerUser(userDto);
            return Response.status(Response.Status.CREATED).entity("User registered successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An unexpected error occurred").build();
        }
    }

    //Ask for a new Password. Requires email and sends an email to an existing user.
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
                return Response.status(200).entity("Email sent").build();
            } else {
                return Response.status(400).entity("User not found").build();
            }
        } catch (Exception e) {
            return Response.status(500).entity("Internal server error").build();
        }
    }

    // Confirmation via link to register a new password. Receives the token from the URL and the new password
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
                return Response.status(200).entity("New Password updated").build();
            } catch (Exception e) {
                return Response.status(500).entity("Failed to update Password").build();
            }
        } else {
            return Response.status(404).entity("User not found").build();
        }
    }

    @GET
    @Path("/confirmRegistration")
    public Response confirmRegistration(@HeaderParam("emailToken") String emailToken){
        UserEntity user = userBean.getUserByEmailToken(emailToken);
        if (user != null) {
            try {
                userBean.confirmRegistration(user);
                return Response.status(200).entity("Registration confirmed").build();
            } catch (Exception e) {
                return Response.status(500).entity("Failed to confirm registration").build();
            }
        } else {
            return Response.status(404).entity("User not found").build();
        }


    }

    @POST
    @Path("/image")
    @Consumes("image/*")
    public Response uploadImage(InputStream imageData, @HeaderParam("filename") String originalFileName, @HeaderParam("email") String email) {
        try {
            imageBean.saveUserProfileImage(email, imageData, originalFileName);
            } catch (IOException ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

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

    @POST
    @Path("/images")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUserPictures(@HeaderParam("Authorization") String authorizationHeader, List<Long> ids) {
        System.out.println(ids);
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


    @GET
    @Path("/profile/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfileDetails(@Context HttpHeaders headers, @PathParam("userId") Long userId) {
        String authorizationHeader = headers.getHeaderString("Authorization");

        // Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();

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
            System.out.println("1 " + userId);
            // Return the profile if the id and token is the sae
            ProfileDto profileDto = userBean.getProfileDtoById(userId);
            if (profileDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Profile not found").build();
            }
            return Response.ok(profileDto).build();
        } else {
            System.out.println("2 " + userId);
            // Check the visibility of the requested user's profile
            ProfileDto profileDto = userBean.getProfileDtoById(userId);
            if (profileDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Profile not found").build();
            }
            if (!profileDto.getVisibility()) {
                return Response.status(Response.Status.FORBIDDEN).entity("Profile not visible").build();
            }
            return Response.ok(profileDto).build();
        }
    }


    @PUT
    @Path("/profile/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserProfile(@Context HttpHeaders headers, @PathParam("userId") Long userId, ProfileDto profileDto) {
        System.out.println(profileDto.toString());
        String authorizationHeader = headers.getHeaderString("Authorization");

        // Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();

        // Validate the token
        Response validationResponse = authBean.validateUserToken(token);
        if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return validationResponse;
        }

        try {
            userBean.updateUserProfile(userId, profileDto);
            return Response.ok().entity("Profile updated successfully").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/updatePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePassword(UserPasswordUpdateDto u, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the Authorization header
            String token = authorizationHeader.substring("Bearer".length()).trim();

            // Check if the old password and new password are the same
            if (u.getOldPassword().equals(u.getNewPassword())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(JsonUtils.convertObjectToJson(new ResponseMessage("New password must be different from the old password")))
                        .build();
            }

            // Attempt to update the password
            boolean updateSuccessful = userBean.updatePassword(u, token);

            if (updateSuccessful) {
                return Response.status(Response.Status.OK)
                        .entity(JsonUtils.convertObjectToJson(new ResponseMessage("Password is updated")))
                        .build();
            } else {
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

    @GET
    @Path("/{userId}/myProjects")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyProjects(@HeaderParam("Authorization") String authorizationHeader,
                                  @PathParam("userId") Long userId,
                                  @QueryParam("limit") int limit) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
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

        return Response.ok(responseMap).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@QueryParam("query") String query, @HeaderParam("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer".length()).trim();

        // Validate the token
        Response validationResponse = authBean.validateUserToken(token);
        if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return validationResponse;
        }

        List<UserDto> users = userBean.searchUsersByQuery(query);

        if (users == null || users.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Users not found").build();
        }

        return Response.ok(users).build();
    }

}
