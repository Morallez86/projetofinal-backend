package aor.paj.projetofinalbackend.service;


import aor.paj.projetofinalbackend.bean.InterestBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.InterestDto;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.InterestEntity;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.InterestMapper;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/interests")
public class InterestService {

    @Inject
    InterestBean interestBean;

    @Inject
    TokenDao tokenDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInterests(@HeaderParam("Authorization") String authorizationHeader) {
        List<InterestDto> interests = interestBean.getAllAttributes();
        if (interests != null) {
            return Response.ok(interests).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Interests not found").build();
        }
    }

    /* No front ele precisa de dar set das novas já com o id, então este método tem de enviar o dto com o id
     * o duplo for verifica quais os interesses novas e adiciona a uma nova lista que, tendo apenas as novas, é enviada para o front
     * o duplo for começa com a lista das novas para ser corrido o menor numero de vezes possiveis
     * o duplo for tem um counter para ele não continuar a correr desnecessariamente depois de já ter encontrado o id de todas as novas
     * */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addInterests(List<InterestDto> interestDtos, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

            // Add the interests
            interestBean.addAttributes(interestDtos, token);

            UserEntity userEntity = tokenDao.findUserByTokenValue(token);
            Hibernate.initialize(userEntity.getInterests());
            Set<InterestEntity> listInterestEntity = userEntity.getInterests();
            Set < InterestEntity> listNewInterests = new HashSet<>();
            List<Long> idsToLog = new ArrayList<>();

            int counterOfTotal  = 0;

            do {
                for (InterestDto dtoList : interestDtos) {
                for (InterestEntity list : listInterestEntity) {
                        if (list.getName().equals(dtoList.getName())) {
                            listNewInterests.add(list);
                            idsToLog.add(list.getId());
                            counterOfTotal++;
                        }
                    }
                }
            } while (counterOfTotal < interestDtos.size());




            Set <InterestDto> listNewInterestsConvertedDto = InterestMapper.listToDto(listNewInterests);
            LoggerUtil.logInfo("INTEREST WITH THIS ID'S: " + idsToLog + " CREATED" , "at" + LocalDateTime.now(), user.getEmail(), token);

            return Response.status(Response.Status.CREATED).entity(listNewInterestsConvertedDto).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeSkills(List<Long> interestIds, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

            // Remove the skills
            interestBean.removeAttributes(interestIds, token);

            LoggerUtil.logInfo("INTEREST WITH THIS ID'S: " + interestIds + " REMOVED"  , "at" + LocalDateTime.now(), user.getEmail(), token);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
