// package senai.saep.estoque.security;

// import java.security.Key;
// import java.sql.Date;
// import java.util.function.Function;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;
// import lombok.RequiredArgsConstructor;
// @Service
// public class JwtService {
//     // Use uma chave secreta forte (geralmente colocada no application.properties)
//     private static final String SECRET_KEY = "suaChaveSecretaMuitoLongaEComplexaAquiParaGarantirSeguranca";

//     public String generateToken(UserDetails userDetails) {
//         return Jwts.builder()
//                 .setSubject(userDetails.getUsername())
//                 .setIssuedAt(new Date(System.currentTimeMillis()))
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) 
//                 .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public String extractUsername(String token) {
//         return extractClaim(token, Claims::getSubject);
//     }

//     public boolean isTokenValid(String token, UserDetails userDetails) {
//         final String username = extractUsername(token);
//         return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         return extractClaim(token, Claims::getExpiration).before(new Date());
//     }

//     private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//         final Claims claims = Jwts.parserBuilder()
//                 .setSigningKey(getSignInKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//         return claimsResolver.apply(claims);
//     }

//     private Key getSignInKey() {
//         byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//         return Keys.hmacShaKeyFor(keyBytes);
//     }
// }