// package senai.saep.estoque.security;

// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtService jwtService;
//     private final UserDetailsService userDetailsService;

//     public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//         this.jwtService = jwtService;
//         this.userDetailsService = userDetailsService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
        
//         final String authHeader = request.getHeader("Authorization");
//         final String jwt;
//         final String username;

//         // Verifica se o cabeçalho possui o token Bearer
//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             filterChain.doFilter(request, response);
//             return;
//         }

//         jwt = authHeader.substring(7);
//         username = jwtService.extractUsername(jwt);

//         // Se o usuário não estiver autenticado no contexto atual
//         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//             UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

//             if (jwtService.isTokenValid(jwt, userDetails)) {
//                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                         userDetails, null, userDetails.getAuthorities()
//                 );
//                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authToken);
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }