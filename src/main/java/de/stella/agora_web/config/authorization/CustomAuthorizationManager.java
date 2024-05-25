package de.stella.agora_web.config.authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.rowset.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import de.stella.agora_web.config.authorization.model.GrantedPermission;
import de.stella.agora_web.config.authorization.model.Operation;
import de.stella.agora_web.config.authorization.repository.OperationRepository;
import de.stella.agora_web.exception.ObjectNotFoundException;
import de.stella.agora_web.user.model.User;
import de.stella.agora_web.user.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthorizationManager.class);
    private final OperationRepository operacionRepository;
    private final UserServiceImpl userService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();

        String url = extractUrl(request);
        String httpMethod = request.getMethod();

        boolean isPublic = isPublic(url, httpMethod);

        if(isPublic){
            return new AuthorizationDecision(true);
        }


        boolean isGranted = isGranted(url, httpMethod, authentication.get());

        return new AuthorizationDecision(isGranted);
    }

    private boolean isGranted(String url, String httpMethod, Authentication authentication) {
        if(!(authentication instanceof JwtAuthenticationToken)){
           throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }

        List<Operation> operations = obtainedOperations(authentication);

        boolean isGranted = operations.stream().anyMatch(getOperationPredicate(url, httpMethod));
        LOGGER.info("IS GRANTED: {}",isGranted);
        return isGranted;
    }

    private static Predicate<Operation> getOperationPredicate(String url, String httpMethod) {
        return operation -> {
            String basePath = operation.getModule().getBasePath();

            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);
            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        };
    }

    private List<Operation> obtainedOperations(Authentication authentication) {
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;

        Jwt jwt = authToken.getToken();

        String username = jwt.getSubject();
        User user = userService.findByUsername(username)
                .orElseThrow(()-> new ObjectNotFoundException("User not found: "+username));

        List<Operation> operations= user.getRole().getPermissions().stream()
                .map(GrantedPermission::getOperation)
                .toList();

        List<String> scopes = extractScopes(jwt);

        if(!scopes.contains("ALL")){
           operations = operations.stream()
                    .filter(operation -> scopes.contains(operation.getName()))
                    .toList();

        }

        return operations;

    }

    @SuppressWarnings("unchecked")
    private List<String> extractScopes(Jwt jwt) {
        List<String> scopes = new ArrayList<>();
        try{
          scopes = (List<String>)  jwt.getClaims().get("scope");

        }catch (Exception e) {
            LOGGER.error("Hubo un problema al extraer los scopes del cliente", e);
            return List.of();
        }
        return scopes;
    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndpoints = operacionRepository
                .findByPublicAccess();

        boolean isPublic = publicAccessEndpoints.stream().anyMatch(getOperationPredicate(url, httpMethod));
        LOGGER.info("IS PUBLIC: {}",isPublic);

        return isPublic;
    }

    private String extractUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.replace(contextPath, "");
        LOGGER.info(url);
        return url;
    }
}