package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.SkillBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.SkillMapper;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/skills")
public class SkillService {

    @Inject
    SkillBean skillBean;

    @Inject
    TokenDao tokenDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSkills(@HeaderParam("Authorization") String authorizationHeader) {
        List<SkillDto> skills = skillBean.getAllAttributes();
        if (skills != null) {
            return Response.ok(skills).build();
        } else return Response.status(Response.Status.NOT_FOUND).entity("Skills not found").build();
    }


    /* No front ele precisa de dar set das novas já com o id, então este método tem de enviar o dto com o id
    * o duplo for verifica quais as skils novas e adiciona a uma nova lista que, tendo apenas as novas, é enviada para o front
    * o duplo for começa com a lista das novas para ser corrido o menor numero de vezes possiveis
    * o duplo for tem um counter para ele não continuar a correr desnecessariamente depois de já ter encontrado o id de todas as novas
    * */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addSkills(List<SkillDto> skillDtos, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();



            // Add the skills
            skillBean.addAttributes(skillDtos, token);


            UserEntity userEntity = tokenDao.findUserByTokenValue(token);
            Hibernate.initialize(userEntity.getSkills());
            Set< SkillEntity> listSkillEntity = userEntity.getSkills();
            Set<SkillEntity> listNewSkills = new HashSet<>();

            int counterOfTotal  = 0;
            do {
                for (SkillDto dtoList : skillDtos) {
                for (SkillEntity list : listSkillEntity) {
                        if (list.getName().equals(dtoList.getName())) {
                            listNewSkills.add(list);
                            counterOfTotal++;
                        }
                    }
                }
            } while (counterOfTotal<skillDtos.size());


            Set<SkillDto> listNewSkillsConvertedDto = SkillMapper.listToDto(listNewSkills);
            LoggerUtil.logInfo("ADD SKILLS", "at " + LocalDateTime.now(), userEntity.getEmail(), token);
            return Response.status(Response.Status.CREATED).entity(listNewSkillsConvertedDto).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeSkills(List<Long> skillIds, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);
            System.out.println(skillIds);

            // Remove the skills
            skillBean.removeAttributes(skillIds, token);
            LoggerUtil.logInfo("SKILLS REMOVE WITH THIS ID'S " + skillIds, "at " + LocalDateTime.now(), user.getEmail(), token);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
