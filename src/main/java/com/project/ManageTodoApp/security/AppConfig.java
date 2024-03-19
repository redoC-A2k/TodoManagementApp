package com.project.ManageTodoApp.security;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.project.ManageTodoApp.entity.User;
import com.project.ManageTodoApp.filters.ModifyRequestAttributeFilter;
import com.project.ManageTodoApp.service.UserService;

// import com.project.ManageTodoApp.filters.JWTTokenGeneratorFilter;
// import com.project.ManageTodoApp.filters.JWTTokenValidatorFilter;

@Configuration
public class AppConfig {

	// private final String privateKey = System.getenv("privateKey");
	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;

	public AppConfig() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

		// Save the private/public key file like this
		// String encodedBase64 =
		// Base64.getEncoder().encodeToString(this.publicKey.replaceAll("\\s+",
		// "").getBytes());
		// System.out.println("Public key : " + encodedBase64);

		byte[] privateByte = Base64.getDecoder().decode(Base64.getDecoder().decode(System.getenv("PRIVATE_KEY")));
		PKCS8EncodedKeySpec privatekeySpec = new PKCS8EncodedKeySpec(privateByte);

		byte[] publicByte = Base64.getDecoder().decode(Base64.getDecoder().decode(System.getenv("PUBLIC_KEY")));
		X509EncodedKeySpec publickeySpec = new X509EncodedKeySpec(publicByte);

		KeyFactory kf = KeyFactory.getInstance("RSA");
		privateKey = (RSAPrivateKey) kf.generatePrivate(privatekeySpec);
		publicKey = (RSAPublicKey) kf.generatePublic(publickeySpec);
	}

	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterchain(HttpSecurity httpSecurity) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
		httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
		httpSecurity.exceptionHandling(exception -> {
			exception.defaultAuthenticationEntryPointFor(
					new LoginUrlAuthenticationEntryPoint("/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML));
		});
		httpSecurity.oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));
		httpSecurity.cors((cors) -> cors.configurationSource(request -> {
			var corsconfig = new CorsConfiguration();
			corsconfig.addAllowedOrigin(System.getenv("REACT_FRONTEND"));
			corsconfig.addAllowedHeader("*");
			corsconfig.addAllowedMethod("*");
			return corsconfig;
		}));
		return httpSecurity.build();
	}
	// }

	// http://localhost:8080/oauth2/authorize?client_id=todo&response_type=code&redirect_uri=http://localhost:3000/redirect&scope=openid&code_challenge_method=S256&code_challenge=zJv7Zphgpvbkhk6CbkS-_2Wl_VGvThPjJiNdRiNR96g
	// without scope
	// http://localhost:8080/oauth2/authorize?client_id=todo&response_type=code&redirect_uri=http://localhost:3000/redirect&code_challenge_method=S256&code_challenge=zJv7Zphgpvbkhk6CbkS-_2Wl_VGvThPjJiNdRiNR96g

	@Bean
	@Order(2)
	public SecurityFilterChain loginSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// .csrf(csrf -> csrf.disable())
				.cors((cors) -> cors.configurationSource(request -> {
					var corsconfig = new CorsConfiguration();
					corsconfig.addAllowedOrigin(System.getenv("REACT_FRONTEND"));
					corsconfig.addAllowedHeader("*");
					corsconfig.addAllowedMethod("*");
					return corsconfig;
				}))
				.securityMatcher("/login", "/logout")
				.authorizeHttpRequests(
						(authorizeRequest) -> authorizeRequest.requestMatchers("/login").authenticated())
				.logout((logout) -> logout.logoutSuccessUrl(System.getenv("REACT_FRONTEND") + "/signup")
						.invalidateHttpSession(true).deleteCookies("JSESSIONID"))
				.formLogin(Customizer.withDefaults());
		return httpSecurity.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
			throws Exception {
		httpSecurity
				.sessionManagement((session) -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf((csrf) -> csrf.disable())
				.cors((cors) -> cors.configurationSource(request -> {
					var corsconfig = new CorsConfiguration();
					corsconfig.addAllowedOrigin(System.getenv("REACT_FRONTEND"));
					corsconfig.addAllowedHeader("*");
					corsconfig.addAllowedMethod("*");
					return corsconfig;
				}))
				// .addFilterAfter(new JWTTokenGeneratorFilter(),
				// BasicAuthenticationFilter.class)
				// .addFilterBefore(new JWTTokenValidatorFilter(),
				// BasicAuthenticationFilter.class)
				.addFilterAfter(new ModifyRequestAttributeFilter(), BearerTokenAuthenticationFilter.class)
				.authorizeHttpRequests((authorizeRequest) -> authorizeRequest.requestMatchers("/home", "/error")
						.permitAll())
				.authorizeHttpRequests((authorizeRequest) -> authorizeRequest
						.requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll())
				.authorizeHttpRequests(
						(authorizeRequest) -> authorizeRequest.anyRequest().authenticated())
				// .httpBasic(Customizer.withDefaults())
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
		return httpSecurity.build();
	}
	// }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("todo")
				.clientSecret("$2a$10$SAuJE2o7tLdTtyNpbK0wWehSa72vpdGCIzBUf9ZSa6tcRmg3cqa.S") // using bcrypt
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri(System.getenv("REACT_FRONTEND") + "/redirect")
				.build();

		return new InMemoryRegisteredClientRepository(oidcClient);
	}

	// // REFER :
	// //
	// https://stackoverflow.com/questions/24756420/how-to-get-the-rsa-public-key-from-private-key-object-in-java
	// RSAPrivateKeySpec privKeySpec = kf.getKeySpec(privateKey,
	// RSAPrivateKeySpec.class);
	// RSAPublicKeySpec pubkeySpec = new RSAPublicKeySpec(privKeySpec.getModulus(),
	// BigInteger.valueOf(65537));
	// RSAPublicKey publicKey = (RSAPublicKey) kf.generatePublic(pubkeySpec);
	// return NimbusJwtDecoder.withPublicKey(publicKey).build();
	// }

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID("todo")
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UserService userService) {
		return context -> {
			// JwsHeader.Builder headers = context.getJwsHeader();
			JwtClaimsSet.Builder claims = context.getClaims();
			UsernamePasswordAuthenticationToken authentication = context.getPrincipal();
			System.out.println("name : " + authentication.getName());
			User user = userService.findUserByEmail(authentication.getName());
			claims.claim("uId", user.getId());
			if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
				// Customize headers/claims for access_token
				claims.expiresAt(Instant.ofEpochSecond(
						(int) Math.floor((Date.from(Instant.now()).getTime()) / 1000)
								// expire after 3 days
								// + (3 * 24 * 60 * 60)));
								// expire after 3 hours
								+ (3 * 60 * 60)));

			}
		};
	}
}
