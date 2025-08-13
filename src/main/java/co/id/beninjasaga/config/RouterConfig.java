package co.id.beninjasaga.config;

import co.id.beninjasaga.model.dto.CharacterListDto;
import co.id.beninjasaga.model.dto.LoginDto;
import co.id.beninjasaga.service.character.CharacterService;
import co.id.beninjasaga.util.router.ServiceRouter;
import co.id.beninjasaga.service.SystemService;
import co.id.beninjasaga.amf.AmfBinders.MethodSig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Bean
    public ServiceRouter serviceRouter(SystemService systemService, CharacterService characterDAO/*, CharacterService characterService */) {
        return new ServiceRouter()
                // register services (nama bebas, router lower-case di dalam)
                .register("SystemService", systemService)
                .register("CharacterDAO", characterDAO)

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
                );
    }
}
