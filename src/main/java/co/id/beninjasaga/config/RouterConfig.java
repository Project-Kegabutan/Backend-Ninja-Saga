package co.id.beninjasaga.config;

import co.id.beninjasaga.model.dto.*;
import co.id.beninjasaga.service.amfService.characterService.CharacterService;
import co.id.beninjasaga.service.amfService.characterSystem.SystemData;
import co.id.beninjasaga.util.router.ServiceRouter;
import co.id.beninjasaga.service.amfService.characterSystem.SystemService;
import co.id.beninjasaga.amf.AmfBinders.MethodSig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Bean
    public ServiceRouter serviceRouter(SystemService systemService, CharacterService characterDAO, SystemData systemData/*, CharacterService characterService */) {
        return new ServiceRouter()
                // register services (nama bebas, router lower-case di dalam)
                .register("SystemService", systemService)
                .register("CharacterDAO", characterDAO)
                .register("SystemData", systemData)

                // SystemService.login(LoginDto.Request)
                // payload list: ["username","password","buildNo","buildReview"]
                .registerSignature(
                        "SystemService.login",
                        new MethodSig(
                                new Class<?>[]{ LoginDto.Request.class },
                                new String[][]{ new String[]{ "username","password","buildNo","buildReview" } }
                        )
                )

                // CharacterDAO.get(int accountId)
                // payload list: [25]
                .registerSignature(
                        "CharacterDAO.getCharactersList",
                        new MethodSig(
                                new Class<?>[]{ CharacterListDto.Request.class },
                                new String[][]{ new String[]{ "sessionKey"} }
                        )
                )
                .registerSignature(
                        "CharacterDAO.createCharacter",
                        new MethodSig(
                                new Class<?>[]{ CreateCharacterDto.Request.class },
                                new String[][]{ new String[]{ "sessionKey", "characterName", "characterGender", "characterHairColor", "characterSkinColor", "characterHair", "characterFace"} }
                        )
                )

                .registerSignature(
                        "SystemData.getCreateCharacter",
                        new MethodSig(
                                new Class<?>[]{ GetCreateCharacterDto.Request.class },
                                new String[][]{ new String[]{ "sessionKey", "testVersion"} }
                        )
                )

                .registerSignature(
                        "CharacterDAO.deleteCharacter",
                        new MethodSig(
                                new Class<?>[]{ DeleteCharacterDto.Request.class },
                                new String[][]{ new String[]{ "sessionKey", "characterId"} }
                        )
                );
    }
}
